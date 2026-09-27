package com.hms.website;
import jakarta.persistence.*; import java.time.OffsetDateTime; import java.util.UUID;
@Entity @Table(name="website_pages") public class WebsitePage {
 @Id private UUID id; @Column(nullable=false) private UUID tenantId; @Column(nullable=false,length=120) private String slug; @Column(nullable=false,length=180) private String title; @Column(nullable=false,length=20000) private String body; @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private PageStatus status; @Column(nullable=false) private OffsetDateTime updatedAt;
 protected WebsitePage() {} public WebsitePage(UUID tenantId,CreatePageRequest r){id=UUID.randomUUID();this.tenantId=tenantId;apply(r);} public void apply(CreatePageRequest r){slug=r.slug();title=r.title();body=r.body();status=r.status();updatedAt=OffsetDateTime.now();}
 public UUID getId(){return id;} public UUID getTenantId(){return tenantId;} public String getSlug(){return slug;} public String getTitle(){return title;} public String getBody(){return body;} public PageStatus getStatus(){return status;} public OffsetDateTime getUpdatedAt(){return updatedAt;}
}
