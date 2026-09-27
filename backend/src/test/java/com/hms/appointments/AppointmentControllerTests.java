package com.hms.appointments;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.hms.doctors.*; import com.hms.tenancy.*; import java.util.*;
import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.context.ActiveProfiles; import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test")
class AppointmentControllerTests {
 @Autowired MockMvc mockMvc; @Autowired TenantRepository tenants; @Autowired DoctorRepository doctors; @Autowired PatientRepository patients; @Autowired AppointmentRepository appointments; private Doctor doctor;
 @BeforeEach void setUp(){appointments.deleteAll();patients.deleteAll();doctors.deleteAll();tenants.deleteAll();Tenant t=tenants.save(new Tenant(UUID.randomUUID(),"city-care","City Care Hospital",null,TenantStatus.ACTIVE,null,null));doctor=doctors.save(new Doctor(t.getId(),new CreateDoctorRequest("Dr. A Rao","Cardiology",null,null,5,null,null,true,Set.of())));}
 private String request(){return "{\"fullName\":\"Ravi Kumar\",\"phone\":\"+919000000000\",\"hospitalPatientId\":\"UHID-1\",\"doctorId\":\""+doctor.getId()+"\",\"startsAt\":\"2026-09-28T09:00:00Z\",\"endsAt\":\"2026-09-28T09:20:00Z\",\"source\":\"PUBLIC\",\"reason\":\"Review\"}";}
 @Test void createsAppointmentAndReusesPatient(){try{mockMvc.perform(post("/api/hospitals/city-care/appointments").contentType(MediaType.APPLICATION_JSON).content(request())).andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("REQUESTED"));mockMvc.perform(get("/api/hospitals/city-care/appointments")).andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(1)));Assertions.assertEquals(1,patients.count());}catch(Exception e){throw new RuntimeException(e);}}
 @Test void rejectsDuplicateDoctorSlot() throws Exception {mockMvc.perform(post("/api/hospitals/city-care/appointments").contentType(MediaType.APPLICATION_JSON).content(request())).andExpect(status().isCreated());mockMvc.perform(post("/api/hospitals/city-care/appointments").contentType(MediaType.APPLICATION_JSON).content(request())).andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value("BAD_REQUEST"));}
}
