# Hospital Appointment Management SaaS And Website Requirements

## Document Status

This document captures the current project requirements for a full-launch SaaS hospital appointment management platform and a complimentary public website with social media integration for each subscribed hospital.

Source review performed on 2026-09-14:

- Current workspace checked for existing requirement notes, exports, and project files.
- `shared-thread.html` was downloaded from the available shared ChatGPT URL, but that thread is titled "App Compliance for Food Service" and does not contain the hospital appointment management requirements.
- Requirements from earlier private chat threads are not directly available in this workspace. Items that depend on those missing details are marked `TBD`.

## Product Scope

Build a multi-tenant SaaS platform that can be subscribed to by multiple hospitals. Each hospital should be able to start using the appointment management solution with its own data, users, branding, appointment rules, and public website.

The system should help patients discover a hospital, view services and doctors, request or book appointments, and connect through the hospital's social media and messaging channels. It should also help hospital staff manage appointment operations from a simple, reliable dashboard.

The product must be designed as a complete operating experience, not only as appointment CRUD screens. A hospital should be able to subscribe, complete guided setup, configure its website and appointment rules, invite staff, and begin receiving appointment requests without developer support.

The solution has three major parts:

- Appointment management application for hospital staff and administrators.
- Public hospital website for patients, visitors, and prospective patients.
- SaaS subscription, onboarding, tenant, and configuration management for multiple hospitals.

## Goals

- Provide a subscription-based SaaS platform that can serve different hospitals independently.
- Allow patients to request or book appointments with doctors or departments.
- Help hospital staff manage appointment schedules, confirmations, cancellations, and patient queues.
- Give every hospital a configurable public website that can match the hospital's logo, theme, layout preference, and content strategy.
- Integrate hospital social media presence and contact channels so visitors can connect easily.
- Provide a foundation that can later expand into broader hospital management workflows.
- Provide production-ready SaaS operations including subscription billing, plan enforcement, onboarding, support, auditability, monitoring, backups, and tenant-safe administration.

## User Roles

- SaaS platform owner: manages subscriptions, tenants, plans, billing configuration, platform settings, and platform-level support.
- Hospital owner or super admin: manages the subscribed hospital account, branding, users, website, departments, doctors, and appointment settings.
- Patient or visitor: browses hospital information, finds doctors/services, submits appointment requests, and uses social links/contact channels.
- Reception or front-desk staff: creates, updates, confirms, reschedules, and cancels appointments.
- Doctor: views assigned appointment schedule and patient visit queue.
- Hospital administrator: manages doctors, departments, services, schedules, website content, and system users.
- Content or marketing staff: manages website content and social media links or embedded feeds, if this responsibility is separate from admin.

## SaaS And Tenant Requirements

### Tenant Model

- Each subscribed hospital must have an isolated tenant account.
- Hospital data must be separated by tenant, including patients, appointments, doctors, departments, website content, users, settings, and uploaded assets.
- Users must only access hospitals they are assigned to.
- A user may belong to more than one hospital when explicitly assigned, but every session must require a selected active tenant context before accessing hospital data.
- Cross-tenant users such as platform support users, group administrators, or doctors working across hospital branches/tenants must never see blended patient data unless a specific cross-tenant view is intentionally built and permissioned.
- Each hospital should have configurable profile information such as hospital name, legal name, contact numbers, email, address, business hours, emergency contact, logo, favicon, and social media handles.

### Subscription Management

- Support subscription plans for hospitals.
- Subscription plan capabilities should control limits such as number of doctors, users, branches, website pages, storage, social integrations, custom domains, and notification volume.
- Track subscription status such as trial, active, overdue, suspended, cancelled, and expired.
- Hospitals with inactive subscriptions should have controlled access such as read-only mode, disabled publishing, disabled new appointment intake, or grace-period access based on configured plan rules.
- Platform owner should be able to view subscribed hospitals, subscription status, and plan usage.
- Platform owner should be able to create, suspend, reactivate, and cancel hospital tenants.
- Platform owner should be able to configure feature flags so capabilities can vary by subscription plan.
- Platform should track plan usage such as doctors, staff users, branches, appointment volume, content volume, storage, and notifications.
- Platform should provide tenant-safe support access for troubleshooting without exposing data across hospitals.
- Platform should support subscription billing, invoice history, payment status, tax details, failed-payment handling, plan upgrades, plan downgrades, and renewal reminders.
- Hospital owners should be able to view current plan, usage limits, invoices, payment status, and upgrade options.

### Hospital Onboarding

- New hospital onboarding should collect required hospital profile, admin user, branding, domain/subdomain preference, departments, doctors, appointment rules, and social media handles.
- The platform should provide default website templates and default appointment settings so a hospital can go live quickly.
- Hospitals should be able to update configuration later without developer involvement.
- Hospital onboarding should include a guided setup checklist covering profile, logo, theme, departments, doctors, availability, website pages, social links, and appointment settings.
- Hospital admins should see setup health indicators for missing required items such as no doctors, no appointment slots, missing phone number, missing logo, incomplete social handles, or unpublished website.
- Import tools must support doctors, departments, services, and existing patient lists with validation, duplicate detection, tenant scoping, and import error reports.

### Multi-Branch Support

- The platform must support multiple branches or locations for hospitals that operate across sites.
- Branch-specific doctors, departments, appointment slots, contact details, business hours, maps, services, and website location pages must be supported.
- Users, doctors, reports, appointments, and queue views should be filterable by branch where applicable.

## Appointment Management Requirements

### Appointment Booking Policy

- The default appointment flow should be staff-confirmed booking: public website requests enter the hospital staff queue as requested or pending confirmation.
- Hospitals should be able to enable instant booking for selected doctors, departments, branches, or appointment types when slots are available.
- Hospitals should be able to configure cancellation and reschedule rules, including who can cancel/reschedule, allowed time windows, notification behavior, and payment impact where appointment payments are enabled.

### Patient And Appointment Capture

- Capture patient name, age or date of birth, gender, phone number, email, address, and optional patient identifiers.
- Support existing hospital patient identifiers such as Hospital Patient ID, UHID, MRN, registration number, or other hospital-defined ID labels.
- Allow each hospital to configure the patient identifier label, format, uniqueness rules, and whether the identifier is required, optional, or hidden during appointment booking.
- Support optional ABHA ID capture when the hospital enables ABHA support.
- ABHA ID capture should be configurable separately from hospital patient ID and should not be mandatory unless the hospital explicitly requires it for its workflow.
- ABHA ID handling must include appropriate consent, privacy messaging, access control, and masking where required.
- Capture appointment department, doctor, preferred date, preferred time slot, reason for visit, appointment type, and notes.
- Support new patient and existing patient appointment flows.
- Reuse existing patient records when a matching phone number, hospital patient identifier, ABHA ID, or other configured identifier is found, subject to staff confirmation and permitted access.
- Provide a duplicate patient review workflow for authorized staff to resolve likely duplicate patient records without automatically merging sensitive records.
- Prevent duplicate or conflicting bookings for the same doctor and slot.
- Track appointment status such as requested, confirmed, checked in, completed, cancelled, no-show, and rescheduled.
- Store appointment creation and update timestamps.
- Capture appointment source such as staff-created, public website, social media referral, WhatsApp, phone call, or imported source where available.

### Patient Booking Experience

- Public booking should provide a simple journey: choose specialty or doctor, select date and slot, enter patient details, confirm request, and receive clear next steps.
- Patients should be able to search or filter doctors by name, department, specialty, symptoms, language, availability, and location where data is available.
- Doctor profile pages should show qualifications, experience, department, consultation timings, location, and appointment action.
- Public appointment forms should remain short and collect only the details required to confirm or request the appointment.
- Appointment actions should display trust-building information nearby, such as hospital contact number, emergency note, operating hours, and privacy assurance.
- Mobile booking should support click-to-call, click-to-WhatsApp, map navigation, and compact appointment forms.
- The system should show clear patient-facing messages for unavailable slots, successful submissions, reschedules, cancellations, and staff follow-up expectations.
- Multilingual website and booking support should be available for hospitals serving multiple language groups.
- Public booking should include consent language, privacy policy access, and spam protection.
- Patient login should not be required for public appointment booking at launch.
- Patients should be able to access appointment request status, payment instructions, and token status through secure appointment reference links or OTP verification where enabled.

### Appointment Payment Options

- Appointment payment should be configurable per hospital and should be separate from SaaS subscription billing.
- The default payment mode should be pay at hospital.
- Hospitals should be able to enable manual payment instructions such as UPI ID, UPI QR code, bank transfer details, or hospital-provided payment link.
- Manual payment should support patient-submitted transaction reference or proof where enabled.
- Manual payment must remain pending until hospital staff verifies it.
- Hospitals with payment gateway accounts should be able to connect their own payment gateway through provider configuration when supported.
- Patient payment settlement should preferably go directly to the hospital's own account when using hospital-owned payment provider configuration.
- Platform-managed collection and payout may be supported later or as an advanced business model, but it should not be the default appointment payment mode.
- Appointment payment records should support statuses such as not required, pending, submitted, verified, rejected, paid at counter, refunded, and failed.
- Appointment payment receipts should be generated only after payment is verified or marked paid at counter.

### Scheduling

- Maintain doctor availability by day, time slot, department, and leave/unavailable periods.
- Support configurable slot duration by doctor or department.
- Show available slots before creating or confirming an appointment.
- Support rescheduling while preserving appointment history.
- Support walk-in appointments and queue token generation where enabled by the hospital.
- Support emergency or priority appointments with controlled token prioritization.
- Support configurable booking windows, cancellation windows, waitlists, and controlled overbooking rules per hospital, department, doctor, or session where enabled.

### Follow-Up Management

- Staff or doctors should be able to create a follow-up requirement from an appointment or patient profile where permitted.
- Follow-ups should capture recommended date or date range, doctor or department, reason, priority, notes, and responsible team.
- Staff should be able to view upcoming and overdue follow-ups.
- Follow-ups should support status tracking such as open, contacted, deferred, converted to appointment, completed, dismissed, or expired.
- The system should send follow-up reminders to patients where the hospital enables reminder workflows and required consent exists.
- Staff should be able to convert a follow-up into a new appointment while preserving the link to the original appointment or patient history.
- Reports should show upcoming follow-ups, overdue follow-ups, conversion rate, and repeat consultations generated from follow-up workflows.

### Queue Token And Prioritization

- The system should generate queue tokens for confirmed appointments, walk-ins, or checked-in patients where the hospital enables token-based queue management.
- Token numbers should be unique within the configured scope, such as doctor, department, branch, date, or session.
- Staff can assign, reorder, hold, skip, call, complete, cancel, or mark a token as no-show.
- Staff should be able to transfer a token between configured departments, service points, doctors, or visit stages while preserving visit history.
- Hospitals should be able to configure whether one patient can hold multiple active department tokens or only one active token per visit.
- Staff can prioritize a token only when they provide an appropriate reason.
- Token prioritization reasons should be configurable by hospital admins and may include emergency case, senior citizen, disability assistance, child patient, pregnancy, hospital staff approval, doctor request, delayed patient recovery, or operational adjustment.
- Staff should be able to add a short note when prioritizing a token if the configured reason requires explanation.
- Token priority changes must be recorded in the audit log with previous position, new position, reason, note, user, timestamp, doctor or department, and appointment reference.
- Queue views should clearly show normal tokens, priority tokens, held tokens, skipped tokens, and completed tokens.
- Doctors and reception staff should see the same current token order for their permitted department or doctor queue.
- Patient-facing token displays should show current token and estimated waiting position where enabled, without exposing private patient information.
- Hospitals should be able to enable public queue displays, kiosk flows, or mobile token views where appropriate, with patient-safe display rules.
- Hospitals should be able to configure whether priority tokens move to the front, move up by a defined number of positions, or are inserted after the current consultation.
- Reports should include token prioritization counts by date, department, doctor, staff user, and reason to support operational review.
- Token prioritization permissions should be restricted to authorized roles.

### QR Check-In And Token Flows

- The system should support QR-based appointment check-in and department queue joining where enabled by the hospital.
- QR codes should resolve only within the correct hospital tenant, branch, department, appointment, or token context.
- QR payloads must use opaque, non-guessable identifiers or signed time-limited payloads and must not expose sensitive patient or clinical information.
- Consequential QR actions such as check-in, token creation, token transfer, or cancellation should require a clear confirmation step.
- The system should handle invalid, expired, already-used, wrong-hospital, and offline or network-failure QR cases gracefully.
- Manual entry or staff-assisted fallback should be available when camera access, device, or network support is unavailable.

### Staff Workflow

- Staff can search appointments by patient name, phone number, doctor, department, date, and status.
- Staff can search appointments by appointment ID and source where available.
- Staff can confirm, cancel, reschedule, check in, and mark appointments completed.
- Staff can add internal notes visible only to authorized hospital users.
- Staff can view daily appointment lists by doctor and department.
- Staff can view a daily appointment dashboard by department, doctor, branch, and status.
- Staff can switch between calendar, list, and queue views.
- Staff can use clear status labels for pending, confirmed, waiting, completed, cancelled, no-show, and urgent appointments.
- Staff can export or print daily appointment schedules for reception and doctors.
- Staff should see doctor availability and booking conflicts before confirming a slot.
- Staff notes must be separated from patient-facing messages.
- Staff can manage queue tokens and prioritize tokens only with a required reason when permitted by role.

### Doctor Workflow

- Doctors can view their upcoming appointments.
- Doctors can filter by date and appointment status.
- Doctors can mark appointments as completed or no-show if permitted.
- Doctors can view daily queue or schedule views optimized for consultation flow.
- Doctors can see appointment reason and staff notes when permitted by role.
- Doctors can view current token order and priority reasons when permitted by hospital policy.
- Consultation notes, prescriptions, clinical billing, and full EMR are outside the core launch scope, but the appointment workflow should preserve extension points for future integration.

### Notifications

- Send appointment confirmation, cancellation, and reschedule notifications.
- Notification channels must include email, SMS, and WhatsApp through provider abstractions.
- Reminder notifications before the appointment must be supported.
- Notification templates should be configurable by administrators.
- Notification templates should support hospital-specific branding and contact details.
- Patient notifications should include appointment confirmation or request status, date, time, doctor or department, hospital contact details, and next steps where appropriate.
- Notification delivery status, failures, retries, and provider errors must be visible to authorized admins.
- Notification workflows must respect patient consent, opt-out status, channel preference, language preference, and configured quiet hours where applicable.
- Notifications should avoid sensitive clinical details unless the hospital has enabled that content and the required consent and policy controls exist.

### File Uploads And Tenant Storage

- Uploaded files should be tenant-owned and stored under tenant-specific logical namespaces with backend authorization checks.
- Website media, doctor photos, logos, payment proofs, generated reports, and future patient documents must use separate access policies appropriate to their sensitivity.
- File uploads should validate permitted file type, size, and purpose before storage.
- Public website assets may be served publicly only after validation and publish approval; private operational files must require authenticated and authorized access.
- Storage usage should be measured per hospital and contribute to subscription plan usage where applicable.
- File paths, object keys, logs, and URLs must not expose patient names, ABHA IDs, hospital patient IDs, phone numbers, or other sensitive details.

## Public Website Requirements

Each subscribed hospital should receive a configurable public website that presents its brand, services, doctors, appointment flow, and social content.

### Core Website Pages

- Home page with hospital name, brand identity, key services, appointment call to action, and contact information.
- About page covering hospital overview, mission, facilities, and care philosophy.
- Departments or services page listing specialties and available treatments.
- Doctors page with doctor profiles, qualifications, departments, availability summary, and appointment action.
- Appointment page or form that allows visitors to request/book appointments.
- Contact page with address, phone, email, map embed, business hours, and emergency contact if applicable.
- Gallery, testimonials, health articles, careers, insurance information, and FAQ sections should be supported as configurable content sections.
- Appointment calls to action should be visible from relevant pages, especially doctor, department, service, article, and contact pages.

### Website Branding And Theme

- Hospital logo, favicon, brand colors, typography preference, button style, and common visual theme should be configurable.
- Website layout should be changeable through approved healthcare templates or controlled layout settings.
- Initial launch should provide a small controlled set of healthcare templates, recommended as three templates: multispecialty hospital, clinic/specialty center, and multi-branch hospital.
- Theme changes should not require code deployment for each hospital.
- Website pages should use the selected hospital theme consistently.
- The platform should support previewing website theme and layout changes before publishing.
- Uploaded logo and imagery should be manageable from the hospital admin area.
- Theme settings should support hero layout, page sections, imagery, and common visual components while preventing broken or unprofessional layouts.
- Theme changes must remain consistent across home, doctor, department, article, appointment, and contact pages.

### Website Content Management

- Hospital admins or content staff should be able to manage categorized content for the public website.
- Content categories may include services, specialties, doctors, health articles, announcements, events, offers, testimonials, gallery, videos, and FAQs.
- Content should support draft and published states.
- Content should support ordering, featured items, images, SEO title, SEO description, and social sharing metadata.
- Content should be filterable by category on the website.
- Hospital-specific content must not be visible on other hospitals' websites.
- Content can be featured on the home page and category listing pages.
- Content management should support social sharing images per page or content item.
- Content should support basic localization fields where multilingual websites are enabled.

### Appointment Website Flow

- Website visitors can submit an appointment request without logging in.
- Form should validate required fields and phone/email formats.
- Submitted requests should enter the appointment management system as requested or pending confirmation.
- The website should display a success message and next steps after submission.
- Captcha or anti-spam protection should be available for public forms.
- The website should support patient-friendly unavailable-slot handling and suggest alternate dates, doctors, or departments where possible.

### Social Media Integration

- Display links to the hospital's official social media profiles.
- Supported channels should include Facebook, Instagram, LinkedIn, YouTube, X/Twitter, and WhatsApp, with an extensible model for additional platforms.
- Include social sharing metadata for website pages.
- Embed or display social feeds, videos, reviews, or posts where technically supported and approved by the hospital.
- Provide click-to-call and click-to-WhatsApp actions on mobile where appropriate.
- Allow each hospital to configure its own social media handles and display preferences.
- Social content displayed on the website should be categorized or placed in relevant sections where possible.
- The website should continue to work gracefully if a social media feed is unavailable.
- Social links should be displayable in the website header, footer, contact page, and mobile action areas.
- Manual content cards linking to social media posts should be supported when direct feed integration is unavailable or unreliable.
- Social feed integrations should handle blocked embeds, rate limits, expired authentication, or platform downtime without breaking the website.
- Launch behavior should prioritize reliable social links, WhatsApp actions, YouTube/video embeds, and manual social cards. Real-time feed integrations should be added only where provider access is stable and approved by the hospital.

### Analytics And Conversion Tracking

- Track appointment form starts, appointment submissions, traffic sources, and social media referrals where privacy rules allow.
- Track popular doctors, departments, services, and content pages for hospital admins.
- Analytics must avoid storing sensitive patient details in third-party tools or public logs.

### Domain And Publishing

- Each hospital website must support a platform subdomain and custom domain configuration.
- Custom domains should support DNS verification, SSL/TLS provisioning, domain status checks, and fallback to platform subdomain if verification fails.
- Website changes should support preview and publish workflow.
- Published websites should be SEO-friendly and mobile-friendly.
- Website publishing should maintain draft and published versions so unpublished edits do not affect the live website.

## Administration Requirements

- Platform owner can manage hospital tenants, subscriptions, plans, and platform settings.
- Platform owner can view a dashboard of subscribed hospitals, plan status, active users, usage limits, and support indicators.
- Hospital owner can manage hospital profile, branding, website settings, social media handles, and subscription details.
- Manage departments and services.
- Manage doctors, qualifications, photos, department mapping, and availability.
- Manage appointment slots and scheduling rules.
- Manage public website content.
- Manage users, roles, and permissions.
- View operational reports such as appointments by date, department, doctor, status, source, cancellation trends, and no-show trends.
- View follow-up reports such as upcoming follow-ups, overdue follow-ups, contact attempts, and follow-up-to-appointment conversions.
- View queue token reports such as token volume, wait time, service time, transfers, skipped tokens, no-show tokens, and priority changes by reason.
- View hospital value metrics such as appointments recovered, leads converted, repeat consultations generated, staff time saved estimate, and outstanding appointment payments collected where data is available.
- Maintain audit logs for tenant setup, subscription changes, user changes, website publishing, and important appointment actions.
- Manage billing, invoices, subscription upgrades/downgrades, payment status, and failed-payment recovery where permitted.
- Export reports, appointment lists, patient lists, audit logs, and content inventories according to role permissions.

## Data Entities

- Patient
- Patient identifier
- Hospital patient identifier configuration
- ABHA identifier configuration
- Appointment
- Follow-up
- Doctor
- Department
- Service
- Doctor availability
- User
- Role or permission
- Notification
- Website content item
- Social media link
- Tenant or hospital account
- Subscription plan
- Subscription
- Branch or location
- Website theme
- Website template
- Uploaded asset
- Content category
- Feature flag
- Audit log
- Analytics event
- Appointment source
- Queue token
- Token prioritization reason
- Token priority audit
- QR code action
- QR scan event
- Invoice
- Payment
- Appointment payment setting
- Appointment payment
- Payment proof
- Patient receipt
- Import job
- Custom domain
- Website publish version
- Notification provider
- Notification delivery log

## Non-Functional Requirements

- Responsive design for desktop, tablet, and mobile.
- Clear, accessible forms with validation and readable error messages.
- Role-based access control for administrative and hospital staff areas.
- Protect patient and appointment data with authentication, authorization, and secure transport.
- Maintain audit-friendly timestamps for appointment changes.
- Back up database and uploaded content.
- Keep the public website fast, SEO-friendly, accessible, responsive, professional, and easy to navigate.
- Use a professional healthcare visual style with trustworthy colors, readable typography, and simple navigation.
- Scale to support multiple hospitals without data leakage across tenants.
- Support hospital-specific customization without custom code for every hospital.
- Provide reliable uptime expectations suitable for hospital appointment operations, with a target SLA defined before production launch.
- Common staff actions should be efficient enough for front-desk use during busy hospital hours.
- Website and admin interfaces should degrade gracefully when social media integrations or notification providers are temporarily unavailable.
- Support observability through application logs, error tracking, uptime checks, background job monitoring, and notification provider health checks.
- Support data export and import for tenant onboarding, backups, and customer offboarding.

## Compliance And Privacy Considerations

- Treat patient appointment details as sensitive personal data.
- Collect only required patient information on public forms.
- Show privacy consent language before appointment submission if required.
- Avoid exposing appointment or patient data through public URLs, logs, analytics, or social integrations.
- Confirm applicable local healthcare privacy rules before production deployment.
- Use secure authentication and role-based authorization for all hospital and platform admin areas.
- Support backup, restore, and disaster recovery planning before production.
- Confirm data retention, deletion, and export requirements before production.
- Support consent capture for public appointment requests where required.
- Support privacy policy, terms, cookie notice, and data processing language per tenant where required.

## Critical Decisions Before Implementation

- Final SaaS plan names, prices, and exact limits.
- First production providers for SaaS billing, SMS, email, WhatsApp, hosting, storage, and managed database.
- First launch region legal/compliance requirements, including privacy policy, consent text, data retention, and ABHA handling.
- Whether the first launch hospital needs appointment payment enabled, or whether launch starts with pay-at-hospital/manual payment only.
- Any additional requirements from earlier chat threads or private documents that should be merged before sign-off.

## Suggested Initial Milestones

1. Confirm full-launch scope, subscription plans, billing provider, notification providers, social integration approach, languages, and deployment target.
2. Create domain model and database schema for tenants, subscriptions, billing, branches, users, doctors, departments, patients, appointments, queue tokens, website themes, domains, content, notifications, analytics, and audit logs.
3. Build authentication, tenant isolation, RBAC, and platform owner controls.
4. Build hospital onboarding, imports, profile, branch, user, doctor, department, availability, and scheduling workflows.
5. Build appointment management, queue token prioritization, doctor workspace, notifications, and reports.
6. Build configurable public website, custom domains, publishing, categorized content, social integrations, SEO, and analytics.
7. Complete billing, monitoring, backups, privacy/compliance, security hardening, production deployment, and launch readiness.
