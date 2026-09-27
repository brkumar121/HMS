# HMS SaaS Implementation Plan

## Purpose

This document converts the requirements into a practical full-launch implementation plan. It defines the recommended technical stack, architecture, modules, data model, API shape, and small implementation chunks so the application can be built incrementally with minimal rework and lower credit usage.

Primary requirement source: [requirements.md](requirements.md)

Prototype reference: [prototype/index.html](../prototype/index.html)

## Architecture Decision

Use a hybrid architecture:

- Next.js owns the frontend experience: admin dashboards, public hospital websites, SEO pages, website theme rendering, and patient booking UI.
- Spring Boot owns the backend: authentication, authorization, tenant isolation, appointments, queue tokens, billing, notifications, reports, imports, exports, audit logs, and provider integrations.

Reason:

- The public website benefits from Next.js rendering and SEO.
- The SaaS backend benefits from Spring Security, mature transaction management, structured service layers, strong testing patterns, and predictable long-running backend workflows.
- Healthcare SaaS risks are concentrated in backend concerns such as tenant isolation, auditability, billing, patient data privacy, appointment conflicts, and role permissions.

## Recommended Technical Stack

### Frontend

- Framework: Next.js with React and TypeScript.
- UI: Tailwind CSS plus a small internal component library.
- Forms: React Hook Form with Zod validation.
- Data fetching: TanStack Query against Spring Boot REST APIs.
- Tables: TanStack Table for appointment lists, reports, tenant lists, and content lists.
- Calendar and scheduling UI: FullCalendar or a robust custom schedule grid that supports doctor, department, branch, session, queue, and availability workflows.
- Icons: Lucide React.

Reason:

- Next.js supports admin screens and SEO-friendly public hospital websites in the same frontend codebase.
- TypeScript and Zod reduce mistakes in a workflow-heavy healthcare product.
- Tailwind keeps theme customization practical while still allowing hospital-specific colors and templates.

### Backend

- Framework: Spring Boot with Java 21.
- Security: Spring Security with JWT/session integration, tenant-aware authorization, and method-level permission checks.
- API style: REST for core CRUD and workflow actions.
- Validation: Jakarta Bean Validation on backend request DTOs; Zod remains useful for frontend form validation.
- Persistence: Spring Data JPA/Hibernate with explicit service-layer tenant scoping.
- Database migrations: Flyway or Liquibase.
- Background jobs: Spring scheduled jobs plus a queue/worker approach using Redis, RabbitMQ, or managed queue service for notifications, reminders, imports, publish jobs, and analytics aggregation.
- API documentation: OpenAPI/Swagger generated from backend controllers.
- Recommended launch path: Spring Boot modular monolith for the backend, with clear module boundaries so high-volume services can be extracted later if needed.

Reason:

- Spring Boot is stronger for tenant isolation, role-based authorization, audit logging, transactional workflows, billing, imports, notification jobs, and long-term backend maintainability.
- REST is easier to implement, secure, document, and test for appointment workflows than GraphQL at this stage.
- Keeping the backend separate prevents business-critical healthcare workflows from being scattered across frontend routes or server actions.

### Database And Storage

- Primary database: PostgreSQL.
- ORM: Spring Data JPA/Hibernate.
- Migration tool: Flyway or Liquibase.
- Cache and queue: Redis.
- File storage: S3-compatible storage for logos, content images, doctor photos, gallery assets, payment proofs, generated reports, and future patient documents.
- Tenant file isolation: every stored object must use tenant-owned metadata and a tenant namespace or prefix; authorization must be enforced by the backend, not by predictable paths alone.
- Search: PostgreSQL full-text search for launch; consider Meilisearch/OpenSearch when content, doctor search, and tenant volume justify it.

Reason:

- PostgreSQL handles relational scheduling, tenancy, audit, reporting, and content well.
- JPA/Hibernate provides mature enterprise persistence patterns for Java/Spring applications.
- Flyway/Liquibase keeps schema changes reviewable and repeatable across environments.

### Authentication And Authorization

- Authentication: Spring Security using JWT/session cookies, or integration with a managed identity provider.
- Frontend auth handling: Next.js stores/forwards secure session tokens and protects client routes based on backend session/role data.
- Authorization: role-based and permission-based access control enforced in Spring Boot.
- Session model: every authenticated backend request must resolve tenant, user, roles, and permissions.
- Public website routes are read-only and use tenant/site slug/domain resolution rather than staff sessions.

Required roles:

- Platform owner
- Hospital owner / super admin
- Hospital administrator
- Reception / front desk
- Doctor
- Content / marketing staff

### Notifications

- Email: provider abstraction, initially SMTP/SendGrid-compatible.
- SMS: provider abstraction.
- WhatsApp: provider abstraction.
- Build notification template management, provider abstractions, mock provider for testing, and production providers for launch.

### Deployment

- Frontend hosting: Vercel, Netlify, Cloudflare Pages, or containerized Next.js hosting.
- Backend hosting: containerized Spring Boot service on Render, Fly.io, ECS, Kubernetes, Azure App Service, Google Cloud Run, or similar.
- Database: managed PostgreSQL.
- Redis: managed Redis.
- Storage: S3-compatible bucket.
- Environments: local, staging, production.

## Architecture Principles

- Use Spring Boot as a modular backend monolith for the full launch unless scale requires separate services.
- Use Next.js as the frontend application for admin dashboards and public hospital websites.
- Keep strict tenant isolation from the first database migration.
- Every tenant-owned table must include `tenant_id`.
- Every backend staff/admin query must be scoped by tenant unless it is a platform-owner query.
- Keep public website rendering read-only and separated from internal admin mutation flows.
- Build provider abstractions for notifications, storage, analytics, and social integrations.
- Start with controlled website templates instead of free-form page builders.
- Frontend must not contain trusted business authorization logic; backend permissions are the source of truth.

## Plan Maintenance Rules

Use this document as the execution source of truth. To keep implementation efficient and avoid wasting credits, maintain it with these rules:

- Keep requirements in [requirements.md](requirements.md); keep implementation sequencing, stack, chunk scope, and quality gates here.
- Do not rewrite the full implementation plan for every new decision. Update only the affected section, chunk, data model, API area, or decision log.
- Every new feature must be assigned to one implementation chunk before coding starts.
- Every implementation chunk must have a clear goal, build list, done criteria, and test expectations.
- Keep launch-critical decisions in the decision log so future implementation prompts do not need to re-explain context.
- If a chunk grows too large, split it into smaller sub-chunks instead of asking an agent to implement it all at once.
- Prefer adding a new chunk or sub-chunk over mixing unrelated changes into an existing chunk.
- Keep generated code aligned with the prototype screen map, but do not use the prototype as production code.

## Critical Decisions To Confirm

Implementation can start with the defaults in the requirements. Only these decisions should be confirmed before production configuration:

- Final SaaS plan names, prices, and exact limits.
- First production providers for SaaS billing, SMS, email, WhatsApp, hosting, storage, and managed database.
- First launch region legal/compliance requirements, including privacy policy, consent text, data retention, and ABHA handling.
- Whether the first launch hospital needs appointment payment enabled, or whether launch starts with pay-at-hospital/manual payment only.
- Any additional requirements from earlier chat threads or private documents that should be merged before sign-off.

## Implementation Method

Use a contracts-first, backend-first workflow for most modules:

1. Define or update database migration.
2. Define backend entity, DTO, validation, service, repository, controller, and permissions.
3. Add backend tests for tenant scoping, permissions, validation, and workflow rules.
4. Generate or update OpenAPI contract.
5. Update frontend API client/types.
6. Build frontend screens using existing UI patterns.
7. Add frontend validation and role-based navigation.
8. Run tests and update documentation for the chunk.

Exceptions:

- Public website visual template work can start from frontend components once the read-only API contract is defined.
- Provider integrations should start with provider interfaces and mock providers, then add real providers after workflows are stable.

## Suggested Repository Structure

```text
frontend/
  app/
    (public)/
      [siteSlug]/
    (admin)/
      admin/
  components/
    ui/
    admin/
    website/
    appointments/
    tokens/
  lib/
    api/
    auth/
    validation/
    theme/

backend/
  src/main/java/com/hms/
    auth/
    tenancy/
    subscriptions/
    billing/
    hospitals/
    users/
    departments/
    doctors/
    appointments/
    queuetokens/
    website/
    content/
    social/
    notifications/
    reports/
    audit/
    analytics/
    imports/
    storage/
    common/
  src/main/resources/
    db/migration/
    application.yml
```

## Core Data Model

### SaaS And Tenancy

- `Tenant`: subscribed hospital account.
- `SubscriptionPlan`: plan name, limits, feature flags.
- `Subscription`: tenant, plan, status, billing dates.
- `FeatureFlag`: feature key, plan availability, tenant override.
- `UsageMetric`: tenant usage counts for doctors, users, branches, content, notifications, storage.
- `Invoice`: subscription invoice, tax details, amount, status, due date.
- `Payment`: payment attempt, provider reference, status, failure reason.

### Hospital Configuration

- `HospitalProfile`: tenant name, legal name, contact details, emergency number, address, hours, domain settings.
- `Branch`: branch name, address, contact, active status.
- `UploadedAsset`: tenant-owned files such as logo, favicon, doctor images, gallery images.
- `CustomDomain`: tenant domain, DNS verification status, SSL status, fallback subdomain.
- `ImportJob`: import type, source file, validation summary, duplicate summary, status, error report.

### Users And Permissions

- `User`: login identity.
- `TenantUser`: user-to-tenant membership.
- `Role`: platform owner, hospital owner, admin, reception, doctor, content.
- `Permission`: granular permission key.
- `RolePermission`: permissions assigned to each role.

### Clinical Directory

- `Department`: tenant department/specialty.
- `Service`: service/treatment under department.
- `Doctor`: doctor profile, qualifications, experience, languages, department, photo.
- `DoctorAvailability`: doctor, branch, day/date, session, start/end, slot duration, room.
- `DoctorLeave`: blocked dates or sessions.

### Appointments And Queue

- `Patient`: tenant patient record.
- `PatientIdentifierConfig`: tenant configuration for hospital patient ID label, format, uniqueness, visibility, and required/optional behavior.
- `PatientIdentifier`: patient identifier value by type, such as hospital patient ID, UHID, MRN, registration number, ABHA ID, or other configured identifier.
- `AbhaConfig`: tenant setting for whether ABHA ID capture is enabled, required, masked, and consent-controlled.
- `Appointment`: patient, doctor, department, branch, slot, status, source, reason, notes.
- `FollowUp`: patient, source appointment, target date/date range, doctor or department, reason, priority, responsible team, status, notes.
- `AppointmentPaymentSetting`: tenant configuration for pay-at-hospital, manual UPI/bank/payment-link instructions, hospital-owned gateway, and payment requirement rules.
- `AppointmentPayment`: appointment payment status, amount, mode, provider reference, verification status, and receipt reference.
- `PaymentProof`: patient-submitted transaction reference, note, or uploaded proof for manual payment verification.
- `PatientReceipt`: receipt number, payment reference, verified amount, issued timestamp, and hospital receipt details.
- `AppointmentStatusHistory`: status transitions and actor.
- `QueueToken`: appointment or walk-in token, token number, scope, status, position, priority flag, active-token rule, current service point.
- `TokenPriorityReason`: tenant-configured reason.
- `TokenPriorityAudit`: old position, new position, reason, note, actor, timestamp.
- `TokenTransfer`: token, from/to department or service point, reason, actor, timestamp.
- `QrCodeAction`: generated QR action, tenant, scope, signed payload metadata, expiry, allowed action.
- `QrScanEvent`: scan result, actor or anonymous session, tenant, action, status, device context, timestamp.

### Website And Content

- `WebsiteTheme`: logo, favicon, colors, typography, selected template, layout options.
- `WebsitePage`: page type, slug, SEO metadata, draft/published state.
- `WebsitePublishVersion`: published snapshot/version for rollback and draft isolation.
- `ContentCategory`: services, articles, announcements, events, gallery, videos, FAQs.
- `ContentItem`: title, body, category, featured flag, image, SEO fields, social image, status.
- `SocialLink`: platform, URL/handle, display location.
- `SocialCard`: manual card linking to a social media post.

### Notifications, Analytics, Audit

- `NotificationTemplate`: tenant, channel, event type, message body.
- `NotificationEvent`: appointment event, recipient, channel, status.
- `AnalyticsEvent`: privacy-safe event such as form start, submission, page view, source.
- `AuditLog`: tenant, actor, entity, action, before/after summary, timestamp.
- `OutcomeMetricSnapshot`: tenant, metric type, reporting period, formula version, observed value, estimated value, attribution window.

## API Areas

Use REST endpoints grouped by module.

```text
/api/platform/tenants
/api/platform/plans
/api/platform/support-access
/api/admin/hospital-profile
/api/admin/branches
/api/admin/users
/api/admin/departments
/api/admin/doctors
/api/admin/availability
/api/appointments
/api/appointments/:id/status
/api/queue-tokens
/api/queue-tokens/:id/prioritize
/api/website/theme
/api/website/pages
/api/content/categories
/api/content/items
/api/social-links
/api/notifications/templates
/api/reports/*
/api/public/:siteSlug/*
```

## Standard Chunk Template

When implementing a chunk, copy this template into the working prompt or issue and fill only the chunk-specific details.

```text
Chunk:
Goal:
Files/modules likely affected:
Backend tasks:
Frontend tasks:
Database/migration tasks:
Permissions:
Audit/logging:
Tests required:
Done criteria:
Do not change:
```

## Quality Gates

Every completed chunk should pass these gates before starting the next chunk:

- Backend compiles.
- Frontend compiles.
- Database migrations apply cleanly from an empty database.
- Tenant-owned data is scoped by `tenant_id`.
- Role/permission checks are enforced in the backend.
- Validation exists on backend request DTOs.
- Audit log is written for sensitive actions.
- Tests are added for core rules touched by the chunk.
- No sensitive patient data is written to analytics, public URLs, or logs.
- README or relevant docs are updated only when setup, commands, or behavior changed.

Extra gates for high-risk modules:

- Appointment scheduling: conflict tests are required.
- Queue token prioritization: reason-required and audit-history tests are required.
- Billing: payment webhook idempotency tests are required.
- Notifications: retry/failure handling tests are required.
- Public website: draft content must not be publicly visible.
- Imports: invalid row, duplicate row, and tenant isolation tests are required.

## Implementation Chunks

Chunk dependency rule:

- Chunks 0 and 1 are mandatory foundations and must be completed first.
- Chunk 2 should be completed before hospital onboarding so tenants, plans, and feature flags exist.
- Chunks 3 through 8 build the core hospital operations.
- Chunks 9 through 14 build website, content, social, notifications, reports, and patient experience.
- Chunks 16 through 19 complete launch operations, billing, domains, data portability, and hardening.
- If implementation needs to pause, stop only at a chunk boundary where tests pass.

### Chunk 0: Project Foundation

Goal: create the app skeleton and shared conventions.

Build:

- Next.js + TypeScript app.
- Spring Boot + Java 21 backend app.
- Tailwind CSS and base design tokens.
- PostgreSQL connection from Spring Boot.
- Flyway or Liquibase database migrations.
- Environment variable validation for frontend and backend.
- OpenAPI/Swagger setup for backend APIs.
- Basic layout shells for platform owner, hospital admin, staff, doctor, content, and public website.
- Shared UI components: button, input, select, table, badge, panel, modal, toast.
- Shared API client in the frontend generated or typed from backend contracts.

Done when:

- Frontend and backend run locally.
- Database migration runs from the backend.
- README has setup commands.
- One functional shell exists for each role.

### Chunk 1: Authentication, Tenancy, And RBAC

Goal: secure the app before business data grows.

Build:

- User login through Spring Security.
- Tenant membership.
- Role assignment.
- Backend permission services, filters/interceptors, and method-level authorization.
- Tenant resolver for authenticated backend routes.
- Platform owner route separation.
- Seed users and sample tenant.
- Frontend route guards based on backend session/role response.

Done when:

- Platform owner can access platform routes.
- Hospital users can only access assigned tenant.
- Unauthorized roles are blocked.
- Tenant ID is required for tenant-owned queries.
- Backend tests prove cross-tenant access is blocked.

### Chunk 2: SaaS Platform Management

Goal: allow platform owner to manage hospitals and subscriptions.

Build:

- Tenant list and tenant detail.
- Create/suspend/reactivate/cancel tenant.
- Subscription plans.
- Feature flags and limits.
- Usage metric display.
- Tenant-safe support access request screen.

Done when:

- Platform owner can onboard a tenant shell.
- Plan and feature flag values are available to tenant modules.

### Chunk 3: Hospital Onboarding And Profile

Goal: let a hospital configure itself without developer help.

Build:

- Hospital profile form.
- Branch management.
- Logo/favicon upload.
- Contact and emergency details.
- Business hours.
- Onboarding checklist and setup health indicators.

Done when:

- Hospital admin can complete setup checklist items.
- Missing setup items are visible.

### Chunk 4: Users, Departments, Doctors, Services

Goal: create the operational directory needed for appointments.

Progress: Department, doctor profile, and service catalog sub-chunks are completed. Tenant-scoped department, doctor, and service APIs are available.

Build:

- Staff/user management.
- Department CRUD.
- Service CRUD.
- Doctor profile CRUD.
- Doctor photo upload.
- Doctor-to-department mapping.
- Doctor languages, qualifications, experience, profile visibility.

Done when:

- Hospital admin can create departments and doctors.
- Public-ready doctor profiles can be marked visible.

### Chunk 5: Doctor Availability And Scheduling Rules

Goal: create reliable appointment slots.

Progress: Recurring weekly availability, doctor leave periods, and available-slot calculation are implemented. The APIs are available under `/api/hospitals/{tenantSlug}/doctors/{doctorId}/availability`, `/leave-periods`, and `/available-slots?date=YYYY-MM-DD`.

Build:

- Availability by doctor, branch, day/date, session, room.
- Slot duration.
- Leave/unavailable periods.
- Conflict prevention.
- Available slot query.
- Schedule preview.

Done when:

- System can calculate available slots.
- Double booking is blocked.

### Chunk 6: Appointment Core Workflow

Goal: support appointment request, confirmation, reschedule, cancellation, check-in, completion, and no-show.

Progress: Patient capture, appointment creation, and controlled status transitions are implemented. The current slice supports tenant-scoped patient reuse by phone, hospital patient ID and optional ABHA capture, requested status, source, reason, notes, doctor-slot duplicate protection, confirmation, check-in, completion, cancellation, no-show, and reschedule transitions.

Build:

- Patient record creation and lookup.
- Hospital patient ID and ABHA ID capture according to tenant configuration.
- Appointment creation by staff.
- Public appointment request intake.
- Staff-confirmed booking as the default public booking mode.
- Optional instant booking rules by tenant, doctor, department, branch, or appointment type.
- Configurable booking windows, cancellation windows, waitlist, and controlled overbooking rules.
- Appointment status lifecycle.
- Appointment source capture.
- Appointment payment setting support for pay-at-hospital, manual UPI/bank/payment-link instructions, and hospital-owned gateway mode.
- Manual payment submission, staff verification, rejection, paid-at-counter marking, and receipt generation.
- Internal notes separated from patient-facing messages.
- Search by patient, phone, doctor, department, date, status, appointment ID, and source.
- Search by configured hospital patient ID or ABHA ID where permitted.

Done when:

- Reception can create and manage appointments end to end.
- Public appointment requests appear in staff queue.

### Chunk 6A: Follow-Up Management

Goal: support repeat consultation and missed-care recovery workflows without introducing full EMR scope.

Progress: Follow-up creation, tenant validation, worklist listing, date range, priority, responsible team, notes, and status updates are implemented.

Build:

- Follow-up creation from appointment and patient profile.
- Follow-up date/date-range, reason, priority, responsible team, notes, and status.
- Upcoming and overdue follow-up worklist.
- Follow-up reminder events using the notification abstraction.
- Convert follow-up to appointment while preserving source link.
- Follow-up reports for overdue items and conversion.

Done when:

- Staff can create, track, remind, and convert follow-ups from one focused workflow.

### Chunk 7: Queue Tokens And Prioritization

Goal: support hospital queue operations with audit-safe prioritization.

Progress: Checked-in appointment token creation, doctor/date queue listing, required-reason priority marking, patient-safe queue DTOs, and token state actions are implemented.

Build:

- Token generation for checked-in appointments and walk-ins.
- Token scope settings: doctor/department/branch/session.
- Queue board.
- Token actions: assign, reorder, hold, skip, call, complete, cancel, no-show, transfer.
- Rules for single active visit token versus multiple department tokens.
- Configurable priority reasons.
- Required reason and optional note for prioritization.
- Priority behavior setting: front, move up count, after current consultation.
- Token priority audit.
- Patient-safe token display.
- Optional public queue display, kiosk, and mobile token views with patient-safe fields.
- QR-based appointment check-in and department queue joining.
- Signed/time-limited QR payload validation and manual fallback.

Done when:

- Staff can prioritize only with reason.
- Audit log captures old/new position, reason, note, actor, timestamp, doctor/department, appointment reference.
- QR check-in and queue joining cannot expose sensitive data or cross tenant boundaries.

### Chunk 8: Doctor Workspace

Goal: give doctors a clean consultation queue view.

Build:

- Doctor daily schedule.
- Queue view.
- Current patient context.
- Appointment reason and permitted notes.
- Mark complete/no-show.
- Date and status filters.

Done when:

- Doctor can manage daily appointment flow without seeing unrelated admin settings.

### Chunk 9: Website Theme And Publishing

Goal: give each hospital a configurable public website.

Progress: Tenant-owned website branding, theme settings, social handles, contact details, and publish state are implemented.

Build:

- Website theme model.
- Template selection.
- Logo, favicon, colors, typography, button style.
- Controlled layout options.
- Preview and publish workflow.
- Public website route by site slug/subdomain.
- Custom domain verification, SSL status, and fallback behavior.

Done when:

- Hospital admin can change theme, preview, and publish.
- Public pages render with tenant-specific theme.

### Chunk 10: Website Content Management

Goal: support categorized hospital website content.

Progress: Categorized tenant content items, draft/published/archived status, slug validation, and public category filtering are implemented.

Build:

- Website pages.
- Content categories.
- Content items with draft/published state.
- Featured content.
- Ordering.
- Images.
- SEO title/description.
- Social sharing image.
- Public category listing and detail pages.

Done when:

- Content staff can publish categorized content.
- Public website shows only published content for that tenant.

### Chunk 11: Social Media Integration

Goal: provide social presence without making the website fragile.

Progress: Tenant-scoped social platform connections, handles, profile links, enabled state, and provider abstraction are implemented.

Build:

- Social handles and links.
- Display locations: header, footer, contact page, mobile actions.
- Manual social cards.
- Social feed provider interface.
- Real launch provider or approved fallback mode for each supported platform.
- Graceful fallback for blocked embeds or provider failure.

Done when:

- Website can show social links and social cards.
- Website works even if feeds fail.

### Chunk 12: Notifications

Goal: notify patients without hard-coding providers.

Build:

- Notification templates by tenant, event, and channel.
- Mock provider.
- Email/SMS/WhatsApp provider interfaces.
- Confirmation, cancellation, reschedule, reminder events.
- Notification event log.
- Spring background jobs or queue workers.

Done when:

- Appointment lifecycle actions create notification events.
- Mock notifications can be reviewed in admin.

### Chunk 13: Reports, Analytics, Audit

Goal: make operations measurable and auditable.

Build:

- Appointment reports by date, department, doctor, status, source.
- Cancellation and no-show trends.
- Queue token reports.
- Priority reason report.
- Website analytics: form starts, submissions, traffic source, popular doctors/departments/content.
- Audit log viewer.

Done when:

- Hospital admin can review appointment and token operations.
- Sensitive patient details are not sent to analytics.

### Chunk 14: Public Patient Experience

Goal: polish the patient-facing website and booking path.

Build:

- Home, about, departments, doctors, appointment, contact pages.
- Doctor search/filter by department, specialty, symptoms, language, availability, location.
- Doctor profile pages.
- Short appointment form.
- Alternate slot suggestions.
- Success message and next steps.
- Click-to-call, click-to-WhatsApp, map navigation.
- Patient-safe token status page.

Done when:

- Patient can discover a doctor, request appointment, and view safe token status.

### Chunk 15: Hardening And Production Readiness

Goal: prepare for real hospital usage.

Build:

- Backups and restore procedure.
- Rate limiting for public forms.
- Captcha/anti-spam where needed.
- Security review for tenant isolation.
- Error logging.
- Monitoring.
- Accessibility pass.
- Performance pass.
- Data retention/export decisions.

Done when:

- App is ready for launch-readiness review with paying hospitals.

### Chunk 16: Billing And Subscription Operations

Goal: support full SaaS launch billing and plan enforcement.

Build:

- Billing provider integration.
- Subscription checkout or platform-managed subscription assignment.
- Invoice history.
- Payment status.
- Failed-payment retries and alerts.
- Plan upgrades and downgrades.
- Grace-period and suspension rules.
- Tenant plan limit enforcement.

Done when:

- Hospital owner can view plan, usage, invoices, and payment status.
- Platform owner can manage billing state and plan changes.
- Feature limits are enforced consistently.

### Chunk 17: Custom Domains And Website Delivery

Goal: support production public websites for each hospital.

Build:

- Platform subdomain routing.
- Custom domain configuration.
- DNS verification status.
- SSL/TLS provisioning status.
- Domain fallback behavior.
- Published website version snapshots.
- SEO sitemap and robots configuration per tenant.

Done when:

- Hospital websites work on platform subdomains and verified custom domains.
- Draft changes do not affect published websites.

### Chunk 18: Imports, Exports, And Data Portability

Goal: support real hospital onboarding and offboarding.

Build:

- Doctor import.
- Department import.
- Service import.
- Patient import.
- Validation reports and duplicate detection.
- Appointment/report/audit/content exports.
- Tenant data export package for offboarding where permitted.

Done when:

- Hospital admins can import core operational data safely.
- Authorized users can export required data.

### Chunk 19: Launch Operations

Goal: make the product production-ready for paying hospitals.

Build:

- Error tracking.
- Uptime monitoring.
- Background job monitoring.
- Provider health checks.
- Backup and restore runbook.
- Disaster recovery runbook.
- Security review.
- Accessibility review.
- Performance review.
- Privacy policy, terms, and cookie notice support.

Done when:

- The platform has operational visibility and launch runbooks.
- Production risks are documented and tested.

## Credit-Saving Implementation Strategy

- Build one chunk at a time.
- For each chunk, ask the coding agent to modify only the files needed for that chunk.
- Keep a running checklist in the chunk section instead of asking for broad rewrites.
- Reuse the prototype screen names as implementation targets.
- Implement provider interfaces first, then connect production providers in the provider-specific chunks.
- Keep launch-critical features in scope, but sequence expensive integrations after core workflows are stable.
- Prefer CRUD screens generated from shared patterns after the first working example.
- Write database schema before UI-heavy implementation for each module.
- Add tests around tenant scoping, appointment conflicts, token prioritization, and permissions before adding polish.

### Low-Credit Prompt Pattern

Use this pattern for implementation prompts:

```text
Implement Chunk X from docs/implementation-plan.md.
Use docs/requirements.md only for requirements related to this chunk.
Do not work on unrelated chunks.
Before editing, inspect existing files and summarize the smallest change set.
After editing, run relevant tests/build checks.
Update docs only if setup, commands, API contracts, or behavior changed.
```

Avoid prompts like:

```text
Build the whole HMS SaaS.
Implement all screens.
Read all docs and start coding everything.
Refactor the project while implementing appointments.
```

### Context Budget Rules

- For foundation work, include only the stack section, repository structure, Chunk 0, and quality gates.
- For backend feature work, include the relevant data model subsection, API area, chunk, quality gates, and high-risk areas.
- For frontend screen work, include the prototype screen map, role/screen names, API contract, and UI conventions.
- For bug fixes, include the failing behavior, related file paths, and expected result rather than the full project docs.
- For provider integrations, include only the provider abstraction rules, selected provider decision, affected chunk, and test expectations.

### Implementation Tracking

Maintain a short progress checklist as chunks are completed:

```text
[ ] Chunk 0 - Project Foundation
[ ] Chunk 1 - Authentication, Tenancy, And RBAC
[ ] Chunk 2 - SaaS Platform Management
[ ] Chunk 3 - Hospital Onboarding And Profile
[~] Chunk 4 - Users, Departments, Doctors, Services (departments and doctor profiles complete)
[~] Chunk 5 - Doctor Availability And Scheduling Rules (availability, leave periods, and slot calculation complete)
[~] Chunk 6 - Appointment Core Workflow (patient capture and initial appointment creation complete)
[ ] Chunk 7 - Queue Tokens And Prioritization
[ ] Chunk 8 - Doctor Workspace
[ ] Chunk 9 - Website Theme And Publishing
[ ] Chunk 10 - Website Content Management
[ ] Chunk 11 - Social Media Integration
[ ] Chunk 12 - Notifications
[ ] Chunk 13 - Reports, Analytics, Audit
[ ] Chunk 14 - Public Patient Experience
[ ] Chunk 15 - Hardening And Production Readiness
[ ] Chunk 16 - Billing And Subscription Operations
[ ] Chunk 17 - Custom Domains And Website Delivery
[ ] Chunk 18 - Imports, Exports, And Data Portability
[ ] Chunk 19 - Launch Operations
```

## Full-Launch Build Sequence

Build in this order:

1. Chunk 0: Project Foundation.
2. Chunk 1: Authentication, Tenancy, And RBAC.
3. Chunk 2: SaaS Platform Management.
4. Chunk 3: Hospital Onboarding And Profile.
5. Chunk 4: Users, Departments, Doctors, Services.
6. Chunk 5: Doctor Availability And Scheduling Rules.
7. Chunk 6: Appointment Core Workflow.
8. Chunk 7: Queue Tokens And Prioritization.
9. Chunk 8: Doctor Workspace.
10. Chunk 9: Website Theme And Publishing.
11. Chunk 10: Website Content Management.
12. Chunk 11: Social Media Integration.
13. Chunk 12: Notifications.
14. Chunk 13: Reports, Analytics, Audit.
15. Chunk 14: Public Patient Experience.
16. Chunk 16: Billing And Subscription Operations.
17. Chunk 17: Custom Domains And Website Delivery.
18. Chunk 18: Imports, Exports, And Data Portability.
19. Chunk 15: Hardening And Production Readiness.
20. Chunk 19: Launch Operations.

Full launch should include:

- Subscription billing and invoice visibility.
- Platform subdomain and custom domain support.
- Email, SMS, and WhatsApp notification providers.
- Multi-branch support.
- Import/export workflows.
- Social links, manual social cards, and approved feed/fallback behavior.
- Reports, analytics, audit logs, monitoring, backups, and production runbooks.

## High-Risk Areas To Implement Carefully

- Tenant isolation in every query.
- Appointment slot conflict prevention.
- Token prioritization audit history.
- Role permissions for token priority and support access.
- Public website data exposure.
- Notification content containing patient information.
- Hospital patient ID and ABHA ID privacy, masking, consent, and access control.
- Social media embeds failing or leaking data.
- Analytics accidentally capturing patient details.

## Suggested Test Coverage

- Tenant users cannot access another tenant's data.
- Platform owner can access platform routes but hospital staff cannot.
- Appointment double booking is blocked.
- Reschedule preserves appointment history.
- Staff cannot prioritize token without reason.
- Token priority audit records old/new positions.
- Doctor can only see permitted appointments.
- Public website shows only published content.
- Draft content is not public.
- Analytics payload does not include patient name, phone, email, or reason for visit.
