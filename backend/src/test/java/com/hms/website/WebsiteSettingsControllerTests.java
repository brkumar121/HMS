package com.hms.website;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class WebsiteSettingsControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired WebsiteSettingsRepository settings;
    @Autowired TenantRepository tenants;

    @BeforeEach
    void setup() {
        settings.deleteAll();
        tenants.deleteAll();
        tenants.save(new Tenant(UUID.randomUUID(), "website-care", "Website Care", null, TenantStatus.ACTIVE, null, null));
    }

    @Test
    void rejectsUnknownPublicWebsiteTenant() throws Exception {
        mockMvc.perform(get("/api/public/hospitals/unknown/website/settings")).andExpect(status().isNotFound());
    }

    @Test
    void doesNotExposeUnpublishedWebsiteSettings() throws Exception {
        mockMvc.perform(get("/api/public/hospitals/website-care/website/settings")).andExpect(status().isBadRequest());
    }
}
