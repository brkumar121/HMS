package com.hms.billing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hms.payments.AppointmentPaymentSettingsRepository;
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
class BillingControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired TenantRepository tenants;
    @Autowired TenantSubscriptionRepository subscriptions;
    @Autowired AppointmentPaymentSettingsRepository paymentSettings;

    @BeforeEach
    void setup() {
        paymentSettings.deleteAll();
        subscriptions.deleteAll();
        tenants.deleteAll();
        tenants.save(new Tenant(UUID.randomUUID(), "billing-care", "Billing Care", null, TenantStatus.ACTIVE, null, null));
    }

    @Test
    void rejectsUnknownSubscriptionTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/unknown/subscription")).andExpect(status().isNotFound());
    }

    @Test
    void rejectsUnknownPaymentSettingsTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/unknown/appointment-payments")).andExpect(status().isNotFound());
    }

    @Test
    void reportsMissingConfigurationForValidTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/billing-care/subscription")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/api/hospitals/billing-care/appointment-payments")).andExpect(status().isBadRequest());
    }
}
