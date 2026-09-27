package com.hms.health;

import java.time.OffsetDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/health")
public class ApiHealthController {

    @GetMapping
    public ApiHealthResponse health() {
        return new ApiHealthResponse("ok", OffsetDateTime.now());
    }

    public record ApiHealthResponse(String status, OffsetDateTime timestamp) {
    }
}
