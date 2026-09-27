package com.hms.notifications;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ConditionalOnProperty(name = "hms.notifications.dispatch-enabled", havingValue = "true")
public class NotificationDispatchService {
    private final NotificationMessageRepository messages;
    private final Map<NotificationChannel, NotificationProvider> providers;
    private final NotificationProvider fallbackProvider;

    public NotificationDispatchService(NotificationMessageRepository messages, List<NotificationProvider> providerList) {
        this.messages = messages;
        this.providers = new EnumMap<>(NotificationChannel.class);
        this.fallbackProvider = providerList.stream().filter(provider -> provider.channel() == null).findFirst().orElse(null);
        providerList.forEach(provider -> { if (provider.channel() != null) providers.put(provider.channel(), provider); });
    }

    @Scheduled(fixedDelayString = "${hms.notifications.dispatch-interval-ms:30000}")
    @Transactional
    public void dispatchQueuedMessages() {
        for (NotificationMessage message : messages.findTop100ByStatusOrderByCreatedAtAsc(NotificationStatus.QUEUED)) {
            NotificationProvider provider = providers.getOrDefault(message.getChannel(), fallbackProvider);
            try {
                if (provider == null) throw new IllegalStateException("No provider configured for " + message.getChannel());
                provider.send(message);
                message.markSent();
                messages.save(message);
            } catch (RuntimeException failure) {
                message.markFailed(failure.getMessage() == null ? "Provider delivery failed" : failure.getMessage());
                messages.save(message);
            }
        }
    }
}
