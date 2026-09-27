package com.hms.website;

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
class WebsiteContentControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired WebsiteContentRepository content;
    @Autowired TenantRepository tenants;

    @BeforeEach
    void setup() {
        content.deleteAll();
        tenants.deleteAll();
        tenants.save(new Tenant(UUID.randomUUID(), "content-care", "Content Care", null, TenantStatus.ACTIVE, null, null));
    }

    @Test
    void rejectsUnknownContentTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/unknown/website/content")).andExpect(status().isNotFound());
    }

    @Test
    void publicContentOnlyReturnsPublishedItems() throws Exception {
        mockMvc.perform(get("/api/hospitals/content-care/website/content/public"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
