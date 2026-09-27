package com.hms.notifications;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "hms.notifications.provider", havingValue = "mock", matchIfMissing = true)
public class MockNotificationProvider implements NotificationProvider {
    @Override public NotificationChannel channel() { return null; }
    @Override public void send(NotificationMessage message) { }
}
