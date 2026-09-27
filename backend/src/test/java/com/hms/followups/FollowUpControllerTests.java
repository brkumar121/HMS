package com.hms.followups;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hms.appointments.PatientRepository;
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
class FollowUpControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired FollowUpRepository followUps;
    @Autowired PatientRepository patients;
    @Autowired TenantRepository tenants;

    @BeforeEach
    void setup() {
        followUps.deleteAll();
        patients.deleteAll();
        tenants.deleteAll();
        tenants.save(new Tenant(UUID.randomUUID(), "followup-care", "Followup Care", null, TenantStatus.ACTIVE, null, null));
    }

    @Test
    void rejectsUnknownTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/unknown/follow-ups")).andExpect(status().isNotFound());
    }

    @Test
    void returnsEmptyWorklistForValidTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/followup-care/follow-ups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
