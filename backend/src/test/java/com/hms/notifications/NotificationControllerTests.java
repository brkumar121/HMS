package com.hms.notifications;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hms.tenancy.Tenant;
import com.hms.tenancy.TenantRepository;
import com.hms.tenancy.TenantStatus;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NotificationControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired NotificationMessageRepository messages;
    @Autowired TenantRepository tenants;

    @BeforeEach
    void setup() {
        messages.deleteAll();
        tenants.deleteAll();
        tenants.save(new Tenant(UUID.randomUUID(), "notify-care", "Notify Care", null, TenantStatus.ACTIVE, null, null));
    }

    @Test
    void rejectsUnknownTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/unknown/notifications")).andExpect(status().isNotFound());
    }

    @Test
    void returnsEmptyNotificationInboxForValidTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/notify-care/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
