package com.hms.doctors;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hms.departments.Department;
import com.hms.departments.DepartmentRepository;
import com.hms.tenancy.Tenant;
import com.hms.tenancy.TenantRepository;
import com.hms.tenancy.TenantStatus;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DoctorControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired TenantRepository tenants;
    @Autowired DepartmentRepository departments;

    @BeforeEach
    void setUp() {
        departments.deleteAll();
        tenants.deleteAll();
        Tenant tenant = tenants.save(new Tenant(UUID.randomUUID(), "city-care", "City Care Hospital", null,
                TenantStatus.ACTIVE, "admin@citycare.example", "+91-9000000000"));
        departments.save(new Department(tenant.getId(), "CARDIO", "Cardiology", null));
    }

    @Test
    void createsDoctorAndReturnsDepartmentMapping() throws Exception {
        UUID departmentId = departments.findAll().getFirst().getId();
        mockMvc.perform(post("/api/hospitals/city-care/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"displayName\":\"Dr. Anika Rao\",\"specialty\":\"Cardiology\","
                                + "\"qualifications\":\"MBBS, MD\",\"experienceYears\":12,"
                                + "\"languages\":\"English, Hindi\",\"publicVisible\":true,"
                                + "\"departmentIds\":[\"" + departmentId + "\"]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.displayName").value("Dr. Anika Rao"))
                .andExpect(jsonPath("$.publicVisible").value(true))
                .andExpect(jsonPath("$.departmentIds", hasSize(1)));
    }

    @Test
    void rejectsDepartmentFromAnotherTenant() throws Exception {
        Tenant other = tenants.save(new Tenant(UUID.randomUUID(), "other-care", "Other Care", null,
                TenantStatus.ACTIVE, null, null));
        UUID otherDepartment = departments.save(new Department(other.getId(), "NEURO", "Neurology", null)).getId();
        mockMvc.perform(post("/api/hospitals/city-care/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"displayName\":\"Dr. Wrong\",\"departmentIds\":[\"" + otherDepartment + "\"]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"));
    }

    @Test
    void validatesRequiredDoctorName() throws Exception {
        mockMvc.perform(post("/api/hospitals/city-care/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"displayName\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    }
}
