package com.hms.social;

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
class SocialConnectionControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired SocialConnectionRepository connections;
    @Autowired TenantRepository tenants;

    @BeforeEach
    void setup() {
        connections.deleteAll();
        tenants.deleteAll();
        tenants.save(new Tenant(UUID.randomUUID(), "social-care", "Social Care", null, TenantStatus.ACTIVE, null, null));
    }

    @Test
    void rejectsUnknownSocialTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/unknown/social/connections")).andExpect(status().isNotFound());
    }

    @Test
    void returnsEmptySocialDirectoryForValidTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/social-care/social/connections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
