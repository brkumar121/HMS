# Implementation Progress Log

This log records completed implementation slices and verification status.

## 2026-09-27

- Foundation: Spring Boot backend, Next.js frontend shell, PostgreSQL/Flyway setup, tenant foundation, security baseline. Commit `0e337ad`.
- Departments: tenant-scoped department schema and API. Commit `b300c48`.
- Doctors: doctor profiles, department mapping, public visibility, cross-tenant validation. Commit `ff31bc6`.
- Scheduling rules: recurring doctor availability and leave periods. Commit `ca7aac9`.
- Slot calculation: available-slot query with leave exclusion. Commit `438b545`.
- Patient and appointments: patient capture with hospital patient ID and optional ABHA ID, appointment creation, requested status, patient reuse by tenant phone, and duplicate doctor-slot protection.
- Appointment lifecycle: controlled status transitions for confirmation, check-in, completion, cancellation, no-show, and reschedule. Full suite stabilized at 17 passing tests.
- Queue foundation: token generation for checked-in appointments, doctor/date queue listing, and priority marking with required configurable reason enum and note.
- Queue operations: patient-safe queue DTOs and token state actions for held, called, skipped, completed, cancelled, and no-show workflows.
- Queue audit: priority changes now create an audit record containing previous state, reason, note, and appointment reference.
- Verification: full clean backend suite passes with 18 tests after tenant-owned audit cleanup was aligned with cascade rules.
- Appointment operations: staff-facing appointment list now returns patient context and supports doctor, status, and phone filters within the tenant.
- Appointment auditability: lifecycle timestamps are persisted for request, confirmation, check-in, completion, and cancellation.
- Follow-ups: tenant-scoped follow-up creation, worklist listing, validation, priority, date range, responsible team, and status updates.
- Notifications: tenant-scoped queued notification records, channel abstraction for SMS/email/WhatsApp, consent flag, and delivery status model.
- Website foundation: tenant-owned branding, theme colors, typography, contact details, social handles, and publish state settings.
- Website content: categorized tenant content items with draft, published, archived states and patient-safe public filtering.
- Social integration foundation: tenant-scoped platform connections, handles, profile links, enabled state, and provider abstraction for future feed synchronization.
- Hospital services: tenant-scoped service catalog with department mapping, fees, active state, and public visibility.
- Hospital branches: tenant-scoped branch/location records with code, address, phone, and active state.
- Doctor services: validated tenant-scoped doctor-to-service assignment endpoint.
- Public directory: patient-safe published doctors, active departments, and public services endpoints with tenant isolation.
- Patient identifiers: tenant configuration for hospital-defined patient ID labels, visibility/required rules, formats, ABHA enablement, requirement, and consent text.
- Staff memberships: tenant-scoped staff invitations with hospital roles and invitation status.
- Appointment payments: hospital-owned payment mode and bank, UPI, payment-link, or manual instructions for hospitals without a gateway API.
- Website pages: tenant-managed draft/published/archived pages with public published-page endpoints.
- Public appointment booking: patient-facing booking endpoint restricted to active, public doctors and accepting hospitals.
- Appointment payment tracking: appointment-level payment status and reference updates for manual hospital collection workflows.
- Staff lifecycle: invitation, activation, suspension, and tenant ownership validation for staff status updates.
- Appointment rescheduling: confirmed appointments can move to a collision-checked future slot with rescheduled status.
- Notification operations: tenant-safe status updates for queued, sent, failed, and cancelled messages.
- Social connection lifecycle: tenant-safe enable/disable controls for hospital social handles.
- Branch operations: tenant-safe branch activation and deactivation controls.
- Reporting foundation: tenant-scoped appointment status summaries, queue status counts, and audit-log browsing endpoints.
- Public availability: patient-facing slot lookup for accepting hospitals and active, public doctors.
- Appointment search: staff search by patient name, appointment date, and status.
- Patient search: tenant-scoped lookup by name, phone, hospital patient ID, or ABHA ID.
- Availability correctness: generated slots now exclude existing non-cancelled appointments as well as leave periods.
- Queue board: tenant-wide date-based queue view ordered by priority and token number.
- Public hospital profile: published contact/profile data is available only for accepting hospitals.
- Public content detail: published website content can be opened by tenant and slug.
- Notification retry: failed messages can be safely returned to the queued state.
- Social sync state: enabled connections expose a tenant-safe sync trigger and timestamp update.
- SaaS subscriptions: tenant-scoped plan, status, period-end, and appointment-limit configuration.
- Appointment reminders: tenant-scoped scheduled SMS, email, or WhatsApp reminder records with future-time validation.
- Booking policy enforcement: subscription pause/cancellation/appointment limits now block new bookings.
- Patient intake enforcement: configured hospital ID and ABHA required rules now apply to staff and public booking.
- Tenant onboarding: platform tenant creation endpoint with slug and contact validation.
- Frontend operations dashboard: role-oriented hospital workspace entry point with overview, appointments, queue, and patient views.
- Public hospital frontend: profile/contact page with appointment entry point.
- Public booking frontend: patient-facing appointment request form route.
- Verification: clean full backend suite passes with 18 tests after queue-token cascade cleanup was aligned with tenant-owned lifecycle data.

## Verification

- Appointment controller tests pass independently: 2 tests.
- Full backend suite passes: 18 tests, 0 failures, 0 errors.
- Frontend build verification pending because `frontend/node_modules` is not installed in the workspace.
