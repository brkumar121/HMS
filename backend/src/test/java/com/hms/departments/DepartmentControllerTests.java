package com.hms.departments;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DepartmentControllerTests {

    @Autowired MockMvc mockMvc;
    @Autowired TenantRepository tenantRepository;
    @Autowired DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();
        tenantRepository.deleteAll();
        tenantRepository.save(new Tenant(UUID.randomUUID(), "city-care", "City Care Hospital", null,
                TenantStatus.ACTIVE, "admin@citycare.example", "+91-9000000000"));
    }

    @Test
    void createsAndListsDepartmentsWithinTenant() throws Exception {
        mockMvc.perform(post("/api/hospitals/city-care/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"cardio\",\"name\":\"Cardiology\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("CARDIO"));

        mockMvc.perform(get("/api/hospitals/city-care/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Cardiology"));
    }

    @Test
    void rejectsInvalidDepartment() throws Exception {
        mockMvc.perform(post("/api/hospitals/city-care/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"\",\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    }
}
