package com.hms.availability;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.hms.doctors.CreateDoctorRequest;
import com.hms.doctors.Doctor;
import com.hms.doctors.DoctorRepository;
import com.hms.tenancy.Tenant;
import com.hms.tenancy.TenantRepository;
import com.hms.tenancy.TenantStatus;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test")
class AvailabilityControllerTests {
    @Autowired MockMvc mockMvc; @Autowired TenantRepository tenants; @Autowired DoctorRepository doctors;
    private Doctor doctor;
    @BeforeEach void setUp() {
        doctors.deleteAll(); tenants.deleteAll();
        Tenant tenant = tenants.save(new Tenant(UUID.randomUUID(), "city-care", "City Care Hospital", null, TenantStatus.ACTIVE, null, null));
        doctor = doctors.save(new Doctor(tenant.getId(), new CreateDoctorRequest("Dr. A Rao", "Cardiology", null, null, 5, null, null, true, Set.of())));
    }
    @Test void addsRecurringAvailability() throws Exception {
        mockMvc.perform(post("/api/hospitals/city-care/doctors/" + doctor.getId() + "/availability")
                .contentType(MediaType.APPLICATION_JSON).content("{\"dayOfWeek\":1,\"startTime\":\"09:00\",\"endTime\":\"13:00\",\"slotDurationMinutes\":20}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.dayOfWeek").value(1)).andExpect(jsonPath("$.slotDurationMinutes").value(20));
    }
    @Test void rejectsInvalidTimeRange() throws Exception {
        mockMvc.perform(post("/api/hospitals/city-care/doctors/" + doctor.getId() + "/availability")
                .contentType(MediaType.APPLICATION_JSON).content("{\"dayOfWeek\":1,\"startTime\":\"13:00\",\"endTime\":\"09:00\",\"slotDurationMinutes\":20}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value("BAD_REQUEST"));
    }
    @Test void rejectsCrossTenantDoctor() throws Exception {
        mockMvc.perform(post("/api/hospitals/unknown-care/doctors/" + doctor.getId() + "/availability")
                .contentType(MediaType.APPLICATION_JSON).content("{\"dayOfWeek\":1,\"startTime\":\"09:00\",\"endTime\":\"10:00\",\"slotDurationMinutes\":20}"))
                .andExpect(status().isNotFound());
    }

    @Test void calculatesSlotsAndSkipsLeavePeriods() throws Exception {
        mockMvc.perform(post("/api/hospitals/city-care/doctors/" + doctor.getId() + "/availability")
                .contentType(MediaType.APPLICATION_JSON).content("{\"dayOfWeek\":1,\"startTime\":\"09:00\",\"endTime\":\"10:00\",\"slotDurationMinutes\":20}"))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/hospitals/city-care/doctors/" + doctor.getId() + "/leave-periods")
                .contentType(MediaType.APPLICATION_JSON).content("{\"startsAt\":\"2026-09-28T09:20:00Z\",\"endsAt\":\"2026-09-28T09:40:00Z\",\"reason\":\"Meeting\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(
                "/api/hospitals/city-care/doctors/" + doctor.getId() + "/available-slots?date=2026-09-28"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].startsAt").value("2026-09-28T09:00:00Z"))
                .andExpect(jsonPath("$[1].startsAt").value("2026-09-28T09:40:00Z"));
    }
}
