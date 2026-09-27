package com.hms.website;
import com.hms.tenancy.*; import jakarta.validation.Valid; import java.util.*; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/hospitals/{tenantSlug}/website/pages") public class WebsitePageController {
 private final TenantRepository tenants; private final WebsitePageRepository pages; public WebsitePageController(TenantRepository t,WebsitePageRepository p){tenants=t;pages=p;}
 @GetMapping public List<WebsitePage> list(@PathVariable String tenantSlug){return pages.findAllByTenantIdOrderByTitleAsc(tenant(tenantSlug));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public WebsitePage create(@PathVariable String tenantSlug,@Valid @RequestBody CreatePageRequest r){UUID t=tenant(tenantSlug);if(pages.findByTenantIdAndSlug(t,r.slug()).isPresent())throw new IllegalArgumentException("Website page slug already exists");return pages.save(new WebsitePage(t,r));}
 private UUID tenant(String slug){return tenants.findBySlug(slug).map(Tenant::getId).orElseThrow(()->new TenantNotFoundException(slug));}
}
