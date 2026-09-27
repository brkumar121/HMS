package com.hms.tenancy;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class TenantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TenantRepository tenantRepository;

    @BeforeEach
    void setUp() {
        tenantRepository.deleteAll();
        tenantRepository.save(new Tenant(
                UUID.randomUUID(),
                "city-care",
                "City Care Hospital",
                "City Care Hospital Pvt Ltd",
                TenantStatus.ACTIVE,
                "admin@citycare.example",
                "+91-9000000000"
        ));
    }

    @Test
    void listsTenants() throws Exception {
        mockMvc.perform(get("/api/platform/tenants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].slug").value("city-care"));
    }

    @Test
    void returnsTenantBySlug() throws Exception {
        mockMvc.perform(get("/api/platform/tenants/city-care"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("City Care Hospital"));
    }

    @Test
    void returnsNotFoundForUnknownTenant() throws Exception {
        mockMvc.perform(get("/api/platform/tenants/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("TENANT_NOT_FOUND"));
    }
}
