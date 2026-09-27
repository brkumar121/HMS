package com.hms.staff;
import com.hms.tenancy.*; import jakarta.validation.Valid; import java.util.*; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/hospitals/{tenantSlug}/staff") public class StaffController {
 private final TenantRepository tenants; private final HospitalStaffRepository staff;
 public StaffController(TenantRepository t,HospitalStaffRepository s){tenants=t;staff=s;}
 @GetMapping public List<HospitalStaff> list(@PathVariable String tenantSlug){return staff.findAllByTenantIdOrderByDisplayNameAsc(tenant(tenantSlug));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public HospitalStaff invite(@PathVariable String tenantSlug,@Valid @RequestBody InviteStaffRequest r){UUID t=tenant(tenantSlug);if(staff.existsByTenantIdAndEmailAndRole(t,r.email(),r.role()))throw new IllegalArgumentException("Staff invitation already exists");return staff.save(new HospitalStaff(t,r.email(),r.displayName(),r.role()));}
 @PatchMapping("/{staffId}/status") public HospitalStaff updateStatus(@PathVariable String tenantSlug,@PathVariable UUID staffId,@Valid @RequestBody UpdateStaffStatusRequest r){HospitalStaff member=staff.findById(staffId).orElseThrow(()->new IllegalArgumentException("Staff member not found"));if(!member.getTenantId().equals(tenant(tenantSlug)))throw new IllegalArgumentException("Staff member does not belong to hospital");member.updateStatus(r.status());return staff.save(member);}
 private UUID tenant(String slug){return tenants.findBySlug(slug).map(Tenant::getId).orElseThrow(()->new TenantNotFoundException(slug));}
}
