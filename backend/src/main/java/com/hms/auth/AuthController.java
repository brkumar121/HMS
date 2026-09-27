package com.hms.auth;
import com.hms.staff.*; import jakarta.validation.Valid; import java.util.UUID; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") public class AuthController {private final StaffAuthService auth;public AuthController(StaffAuthService a){auth=a;} @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return auth.login(r);} @PostMapping("/staff/{staffId}/activate") public void activate(@PathVariable UUID staffId,@Valid @RequestBody ActivateStaffRequest r){auth.activate(staffId,r.password());}}
