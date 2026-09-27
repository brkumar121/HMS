package com.hms.reports;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hms.appointments.AppointmentRepository;
import com.hms.audit.AuditLogRepository;
import com.hms.queue.QueueTokenRepository;
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
class ReportingControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired AppointmentRepository appointments;
    @Autowired QueueTokenRepository tokens;
    @Autowired AuditLogRepository audit;
    @Autowired TenantRepository tenants;

    @BeforeEach
    void setup() {
        tokens.deleteAll();
        appointments.deleteAll();
        audit.deleteAll();
        tenants.deleteAll();
        tenants.save(new Tenant(UUID.randomUUID(), "report-care", "Report Care", null, TenantStatus.ACTIVE, null, null));
    }

    @Test
    void rejectsUnknownReportTenant() throws Exception {
        mockMvc.perform(get("/api/hospitals/unknown/reports/appointments")).andExpect(status().isNotFound());
    }

    @Test
    void returnsEmptyTenantReports() throws Exception {
        mockMvc.perform(get("/api/hospitals/report-care/reports/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(0));
        mockMvc.perform(get("/api/hospitals/report-care/reports/audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
