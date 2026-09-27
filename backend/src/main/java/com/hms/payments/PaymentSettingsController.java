package com.hms.payments;
import com.hms.tenancy.*; import jakarta.validation.Valid; import java.util.UUID; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/hospitals/{tenantSlug}/appointment-payments") public class PaymentSettingsController {
 private final TenantRepository tenants; private final AppointmentPaymentSettingsRepository settings; public PaymentSettingsController(TenantRepository t,AppointmentPaymentSettingsRepository s){tenants=t;settings=s;}
 @GetMapping public AppointmentPaymentSettings get(@PathVariable String tenantSlug){return settings.findById(tenant(tenantSlug)).orElseThrow(()->new IllegalArgumentException("Appointment payment settings not configured"));}
 @PutMapping public AppointmentPaymentSettings update(@PathVariable String tenantSlug,@Valid @RequestBody UpdatePaymentSettingsRequest r){UUID t=tenant(tenantSlug);AppointmentPaymentSettings s=settings.findById(t).orElseGet(()->new AppointmentPaymentSettings(t,r));s.apply(r);return settings.save(s);}
 private UUID tenant(String slug){return tenants.findBySlug(slug).map(Tenant::getId).orElseThrow(()->new TenantNotFoundException(slug));}
}
