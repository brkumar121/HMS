# Functional Requirements For Final Review

## Document Purpose

This document lists the functional requirements for the Hospital Appointment Management SaaS in a human-readable format for final review and correction.

It focuses on what the product must do. Technical stack, architecture, implementation chunks, and engineering details are intentionally excluded.

Source document: [requirements.md](requirements.md)

## Product Overview

The product is a full-launch SaaS platform for hospitals. Each hospital can subscribe to the platform, configure its own account, manage doctors and appointments, and publish a configurable hospital website with social media integration.

The product includes:

- A SaaS platform owner area for managing hospitals, plans, billing, support, and platform operations.
- A hospital admin area for configuring hospital profile, branches, users, doctors, schedules, website, content, and appointment rules.
- A staff area for appointment handling, patient check-in, and queue/token management.
- A doctor area for daily appointment and queue visibility.
- A content/marketing area for website content and social media content management.
- A public hospital website for patients and visitors.

## User Roles

### FR-001: SaaS Platform Owner

The system must allow a platform owner to manage hospital tenants, subscription plans, billing status, plan usage, platform settings, and support access.

### FR-002: Hospital Owner Or Super Admin

The system must allow a hospital owner or super admin to manage the hospital account, profile, branches, branding, users, website, departments, doctors, schedules, appointment settings, and subscription details.

### FR-003: Hospital Administrator

The system must allow hospital administrators to manage hospital operations such as departments, services, doctors, availability, appointment rules, users, reports, and website content based on assigned permissions.

### FR-004: Reception Or Front Desk Staff

The system must allow reception or front desk staff to create, confirm, reschedule, cancel, check in, and complete appointments, and to manage queue tokens where permitted.

### FR-005: Doctor

The system must allow doctors to view assigned appointment schedules, patient queues, appointment reasons, permitted notes, and token order.

### FR-006: Content Or Marketing Staff

The system must allow content or marketing staff to manage website content, social links, social cards, published pages, and media assets based on assigned permissions.

### FR-007: Patient Or Website Visitor

The system must allow patients and website visitors to browse hospital information, search doctors/services, request appointments, use contact actions, and view patient-safe token status where enabled.

## SaaS Platform Management

### FR-008: Tenant Creation

The system must allow the platform owner to create a separate tenant account for each subscribed hospital.

### FR-009: Tenant Isolation

The system must keep each hospital's data separate from other hospitals, including patients, appointments, doctors, departments, users, website content, uploaded assets, settings, billing records, and reports.

### FR-009A: Cross-Tenant Data Prevention

Cross-tenant users such as platform support users, group administrators, or doctors working across hospital branches or tenants must never see blended patient data unless a specific cross-tenant view is intentionally built and permissioned.

### FR-010: Tenant Status Management

The system must allow the platform owner to activate, suspend, reactivate, cancel, or expire hospital tenant accounts.

### FR-011: Subscription Plans

The system must support subscription plans for hospitals.

### FR-012: Plan Limits

The system must allow subscription plans to define limits for doctors, users, branches, website pages, storage, social integrations, custom domains, and notification volume.

### FR-013: Feature Flags

The system must allow selected features to be enabled or disabled by subscription plan or by tenant override.

### FR-014: Subscription Status

The system must track subscription status such as trial, active, overdue, suspended, cancelled, and expired.

### FR-015: Inactive Subscription Rules

The system must apply configured rules for inactive subscriptions, such as read-only access, disabled publishing, disabled new appointment intake, or grace-period access.

### FR-016: Plan Usage Tracking

The system must track each hospital's usage against its plan limits.

### FR-017: SaaS Billing

The system must support subscription billing for hospitals, including invoices, payment status, tax details, failed-payment handling, plan upgrades, plan downgrades, and renewal reminders.

### FR-018: Hospital Billing Visibility

The system must allow hospital owners to view current subscription plan, usage limits, invoices, payment status, and upgrade options.

### FR-019: Tenant-Safe Support Access

The system must support tenant-safe support access for troubleshooting without allowing inappropriate access to hospital or patient data.

## Hospital Onboarding And Profile

### FR-020: Guided Hospital Onboarding

The system must guide a new hospital through required setup steps.

### FR-021: Hospital Profile

The system must allow hospitals to manage hospital name, legal name, contact numbers, email, address, business hours, emergency contact, logo, favicon, and social media handles.

### FR-022: Initial Admin User

The system must support setup of the first hospital admin or owner user during onboarding.

### FR-023: Setup Checklist

The system must provide a setup checklist covering profile, logo, theme, departments, doctors, availability, website pages, social links, and appointment settings.

### FR-024: Setup Health Indicators

The system must show setup health indicators for missing items such as no doctors, no appointment slots, missing phone number, missing logo, incomplete social handles, or unpublished website.

### FR-025: Default Templates And Settings

The system must provide default website templates and default appointment settings so a hospital can go live quickly.

### FR-026: Self-Service Configuration

The system must allow hospitals to update configuration without developer support.

### FR-027: Data Import

The system must support imports for doctors, departments, services, and existing patient lists.

### FR-028: Import Validation

The system must validate imported data, detect duplicates, preserve tenant isolation, and provide import error reports.

## Branch And Location Management

### FR-029: Multiple Branches

The system must support hospitals with multiple branches or locations.

### FR-030: Branch Details

The system must allow each branch to have its own address, contact details, business hours, map information, services, and website location page.

### FR-031: Branch-Specific Scheduling

The system must support branch-specific doctors, departments, appointment slots, and queue views.

### FR-032: Branch Filtering

The system must allow users to filter users, doctors, appointments, reports, and queue views by branch where applicable.

## Users, Roles, And Permissions

### FR-033: User Management

The system must allow authorized users to create, update, deactivate, and manage hospital staff users.

### FR-034: Role Assignment

The system must allow authorized users to assign roles such as hospital owner, administrator, reception, doctor, and content/marketing staff.

### FR-035: Permission-Based Access

The system must restrict features and data based on user role and assigned permissions.

### FR-036: Multi-Hospital User Access

The system may allow one user to access multiple hospitals only when explicitly configured and authorized, and every session must require a selected active tenant context before accessing hospital data.

### FR-037: Platform Owner Separation

The system must separate platform owner capabilities from hospital user capabilities.

## Department, Service, And Doctor Management

### FR-038: Department Management

The system must allow hospitals to create and manage departments or specialties.

### FR-039: Service Management

The system must allow hospitals to create and manage services or treatments under departments.

### FR-040: Doctor Management

The system must allow hospitals to create and manage doctor profiles.

### FR-041: Doctor Profile Details

Doctor profiles must support name, photo, qualifications, experience, languages, departments, services, consultation timings, branch/location, and public visibility.

### FR-042: Doctor Website Visibility

The system must allow hospitals to control whether a doctor profile appears on the public website.

## Doctor Availability And Scheduling

### FR-043: Doctor Availability

The system must allow hospitals to maintain doctor availability by date, day, branch, department, session, room, and time.

### FR-044: Slot Duration

The system must support configurable appointment slot duration by doctor or department.

### FR-045: Leave And Unavailable Periods

The system must support doctor leave, blocked dates, and unavailable periods.

### FR-046: Available Slot Display

The system must show available slots before an appointment is created or confirmed.

### FR-047: Double Booking Prevention

The system must prevent duplicate or conflicting bookings for the same doctor and slot.

### FR-048: Rescheduling

The system must support appointment rescheduling while preserving appointment history.

### FR-049: Walk-In Appointments

The system must support walk-in appointments where enabled by the hospital.

### FR-050: Emergency Or Priority Appointments

The system must support emergency or priority appointments using controlled token prioritization.

## Patient And Appointment Management

### FR-051: Patient Capture

The system must capture patient name, age or date of birth, gender, phone number, email, address, and optional patient identifiers.

### FR-051A: Existing Hospital Patient ID

The system must support existing hospital patient identifiers such as Hospital Patient ID, UHID, MRN, registration number, or other hospital-defined ID labels.

### FR-051B: Hospital Patient ID Configuration

The system must allow each hospital to configure the patient identifier label, format, uniqueness rules, and whether the identifier is required, optional, or hidden during appointment booking.

### FR-051C: Optional ABHA ID Support

The system must support optional ABHA ID capture when a hospital enables ABHA support.

### FR-051D: ABHA ID Configuration

The system must allow ABHA ID capture to be configured separately from the hospital's own patient ID.

### FR-051E: ABHA ID Consent And Privacy

The system must handle ABHA ID with appropriate consent, privacy messaging, access control, and masking where required.

### FR-052: Appointment Capture

The system must capture department, doctor, branch, preferred date, preferred time slot, reason for visit, appointment type, source, and notes.

### FR-053: New And Existing Patients

The system must support both new patient and existing patient appointment flows.

### FR-054: Patient Record Reuse

The system must reuse existing patient records when a matching phone number, hospital patient identifier, ABHA ID, or other configured identifier is found, subject to staff confirmation and permitted access.

### FR-054A: Duplicate Patient Review

The system must provide a duplicate patient review workflow for authorized staff to resolve likely duplicate patient records without automatically merging sensitive records.

### FR-055: Appointment Statuses

The system must track appointment statuses such as requested, confirmed, checked in, completed, cancelled, no-show, and rescheduled.

### FR-056: Appointment Timestamps

The system must store appointment creation and update timestamps.

### FR-057: Appointment Source

The system must capture appointment source such as staff-created, public website, social media referral, WhatsApp, phone call, or imported source.

### FR-058: Appointment Search

The system must allow staff to search appointments by patient name, phone number, doctor, department, date, status, appointment ID, and source.

### FR-059: Staff Appointment Actions

The system must allow permitted staff to create, confirm, cancel, reschedule, check in, complete, and mark appointments as no-show.

### FR-060: Internal Staff Notes

The system must allow staff to add internal notes visible only to authorized hospital users.

### FR-061: Patient-Facing Messages

The system must keep internal notes separate from patient-facing messages.

### FR-062: Appointment Lists

The system must show daily appointment lists by doctor, department, branch, date, and status.

### FR-063: Staff Dashboard

The system must provide a daily staff dashboard showing appointment counts, pending requests, checked-in patients, waiting patients, priority tokens, and operational status.

### FR-064: Calendar, List, And Queue Views

The system must support calendar, list, and queue views for appointment operations.

### FR-065: Print And Export Schedules

The system must allow authorized staff to print or export daily appointment schedules for reception and doctors.

### FR-065A: Booking Rule Controls

The system must support configurable booking windows, cancellation windows, waitlists, and controlled overbooking rules per hospital, department, doctor, or session where enabled.

## Follow-Up Management

### FR-065B: Follow-Up Creation

The system must allow permitted staff or doctors to create a follow-up requirement from an appointment or patient profile.

### FR-065C: Follow-Up Details

Follow-ups must capture recommended date or date range, doctor or department, reason, priority, notes, and responsible team.

### FR-065D: Follow-Up Worklist

The system must allow staff to view upcoming and overdue follow-ups.

### FR-065E: Follow-Up Status

The system must track follow-up statuses such as open, contacted, deferred, converted to appointment, completed, dismissed, or expired.

### FR-065F: Follow-Up Reminders

The system should send follow-up reminders to patients where the hospital enables reminder workflows and required consent exists.

### FR-065G: Follow-Up Conversion

The system must allow staff to convert a follow-up into a new appointment while preserving the link to the original appointment or patient history.

## Queue Token Management

### FR-066: Token Generation

The system must generate queue tokens for confirmed appointments, walk-ins, or checked-in patients where token management is enabled.

### FR-067: Token Scope

Token numbers must be unique within the configured scope, such as doctor, department, branch, date, or session.

### FR-068: Token Actions

The system must allow permitted staff to assign, reorder, hold, skip, call, complete, cancel, or mark a token as no-show.

### FR-069: Token Status Display

Queue views must clearly show normal tokens, priority tokens, held tokens, skipped tokens, and completed tokens.

### FR-070: Token Prioritization

The system must allow permitted staff to prioritize a token only when they provide an appropriate reason.

### FR-071: Priority Reasons

The system must allow hospitals to configure token prioritization reasons.

### FR-072: Default Priority Reason Examples

The system should support reasons such as emergency case, senior citizen, disability assistance, child patient, pregnancy, hospital staff approval, doctor request, delayed patient recovery, or operational adjustment.

### FR-073: Priority Notes

The system must allow staff to add a short note when prioritizing a token if the selected reason requires explanation.

### FR-074: Priority Audit Trail

The system must record token priority changes with previous position, new position, reason, note, user, timestamp, doctor or department, and appointment reference.

### FR-075: Shared Token Order

Doctors and reception staff must see the same current token order for their permitted department or doctor queue.

### FR-076: Patient-Safe Token Display

Patient-facing token displays must show current token and estimated waiting position where enabled, without exposing private patient information.

### FR-077: Priority Behavior Rules

The system must allow hospitals to configure whether priority tokens move to the front, move up by a defined number of positions, or are inserted after the current consultation.

### FR-078: Token Reports

The system must report token prioritization counts by date, department, doctor, staff user, and reason.

### FR-079: Token Permissions

Token prioritization permissions must be restricted to authorized roles.

### FR-079A: Token Transfers

The system must allow permitted staff to transfer a token between configured departments, service points, doctors, or visit stages while preserving visit history.

### FR-079B: Multiple Active Token Rules

The system must allow hospitals to configure whether one patient can hold multiple active department tokens or only one active token per visit.

### FR-079C: Public Queue Displays

The system must allow hospitals to enable public queue displays, kiosk flows, or mobile token views where appropriate, with patient-safe display rules.

## QR Check-In And Token Flows

### FR-079D: QR Check-In

The system should support QR-based appointment check-in and department queue joining where enabled by the hospital.

### FR-079E: QR Tenant Scope

QR codes must resolve only within the correct hospital tenant, branch, department, appointment, or token context.

### FR-079F: QR Payload Security

QR payloads must use opaque, non-guessable identifiers or signed time-limited payloads and must not expose sensitive patient or clinical information.

### FR-079G: QR Confirmation

Consequential QR actions such as check-in, token creation, token transfer, or cancellation must require a clear confirmation step.

### FR-079H: QR Error Handling

The system must handle invalid, expired, already-used, wrong-hospital, and offline or network-failure QR cases gracefully.

### FR-079I: QR Fallback

Manual entry or staff-assisted fallback must be available when camera access, device, or network support is unavailable.

## Doctor Workspace

### FR-080: Doctor Schedule

The system must allow doctors to view their upcoming appointments.

### FR-081: Doctor Filters

The system must allow doctors to filter appointments by date and appointment status.

### FR-082: Doctor Queue View

The system must provide doctors with daily queue or schedule views optimized for consultation flow.

### FR-083: Doctor Patient Context

Doctors must be able to view appointment reason and permitted staff notes.

### FR-084: Doctor Completion Actions

Doctors must be able to mark appointments as completed or no-show if permitted.

### FR-085: Doctor Token Visibility

Doctors must be able to view current token order and priority reasons when permitted by hospital policy.

### FR-086: Future Clinical Extensions

The appointment workflow should preserve extension points for future consultation notes, prescriptions, clinical billing, and EMR integration.

## Patient Public Booking Experience

### FR-087: Public Appointment Journey

The public website must allow patients to choose specialty or doctor, select date and slot, enter patient details, confirm request, and receive clear next steps.

### FR-087A: Default Booking Mode

The default public appointment flow must be staff-confirmed booking, where public appointment requests enter the hospital staff queue as requested or pending confirmation.

### FR-087B: Optional Instant Booking

Hospitals must be able to enable instant booking for selected doctors, departments, branches, or appointment types when slots are available.

### FR-088: Doctor Search

Patients must be able to search or filter doctors by name, department, specialty, symptoms, language, availability, and location where data is available.

### FR-089: Doctor Public Profile

Doctor profile pages must show qualifications, experience, department, consultation timings, location, and appointment action.

### FR-090: Short Appointment Form

The public appointment form must collect only the details required to request or confirm an appointment.

### FR-091: Trust Information

Appointment actions should display hospital contact number, emergency note, operating hours, and privacy assurance nearby.

### FR-092: Mobile Booking Actions

The public website must support mobile-friendly click-to-call, click-to-WhatsApp, map navigation, and compact appointment forms.

### FR-093: Patient Messages

The system must show clear patient-facing messages for unavailable slots, successful submissions, reschedules, cancellations, and staff follow-up expectations.

### FR-094: Alternate Slot Suggestions

The website should suggest alternate dates, doctors, or departments when a selected slot is unavailable.

### FR-095: Multilingual Booking

The system must support multilingual website and booking content where enabled by the hospital.

### FR-096: Consent And Spam Protection

Public booking must include consent language, privacy policy access, and spam protection.

### FR-096A: Patient Login Not Required For Launch

Public appointment booking must not require patient login at launch.

### FR-096B: Patient Appointment Access

Patients should be able to access appointment request status, payment instructions, and token status through secure appointment reference links or OTP verification where enabled.

## Appointment Payment Options

### FR-096C: Appointment Payment Configuration

Appointment payment must be configurable per hospital and separate from SaaS subscription billing.

### FR-096D: Default Payment Mode

The default appointment payment mode must be pay at hospital.

### FR-096E: Manual Payment Support

Hospitals must be able to enable manual payment instructions such as UPI ID, UPI QR code, bank transfer details, or hospital-provided payment link.

### FR-096F: Manual Payment Verification

Manual payment must remain pending until hospital staff verifies it.

### FR-096G: Patient Payment Proof

The system should support patient-submitted transaction reference or payment proof where enabled by the hospital.

### FR-096H: Hospital-Owned Gateway Support

Hospitals with their own payment gateway accounts should be able to connect the gateway through provider configuration when supported.

### FR-096I: Appointment Payment Settlement

Patient appointment payment settlement should preferably go directly to the hospital's own account when using hospital-owned payment provider configuration.

### FR-096J: Appointment Payment Status

Appointment payment records must support statuses such as not required, pending, submitted, verified, rejected, paid at counter, refunded, and failed.

### FR-096K: Patient Receipt

Appointment payment receipts must be generated only after payment is verified or marked paid at counter.

## Notifications

### FR-097: Appointment Notifications

The system must send appointment confirmation, cancellation, and reschedule notifications.

### FR-098: Reminder Notifications

The system must send appointment reminder notifications before the appointment where configured.

### FR-099: Notification Channels

The system must support email, SMS, and WhatsApp notification channels.

### FR-100: Notification Templates

Hospital administrators must be able to configure notification templates.

### FR-101: Branded Notifications

Notification templates must support hospital-specific branding and contact details.

### FR-102: Notification Content

Patient notifications should include appointment status, date, time, doctor or department, hospital contact details, and next steps where appropriate.

### FR-103: Notification Delivery Tracking

The system must show notification delivery status, failures, retries, and provider errors to authorized admins.

### FR-103A: Notification Consent And Preferences

Notification workflows must respect patient consent, opt-out status, channel preference, language preference, and configured quiet hours where applicable.

### FR-103B: Sensitive Notification Content

Notifications should avoid sensitive clinical details unless the hospital has enabled that content and the required consent and policy controls exist.

## Public Hospital Website

### FR-104: Website Provisioning

Each subscribed hospital must receive a configurable public website.

### FR-105: Home Page

The website must support a home page with hospital name, brand identity, key services, appointment call to action, and contact information.

### FR-106: About Page

The website must support an about page covering hospital overview, mission, facilities, and care philosophy.

### FR-107: Departments And Services Pages

The website must support departments and services pages listing specialties and treatments.

### FR-108: Doctors Page

The website must support a doctors page with doctor profiles, qualifications, departments, availability summary, and appointment actions.

### FR-109: Appointment Page

The website must support an appointment page or form that allows visitors to request or book appointments.

### FR-110: Contact Page

The website must support a contact page with address, phone, email, map, business hours, and emergency contact where applicable.

### FR-111: Additional Website Sections

The website must support configurable sections such as gallery, testimonials, health articles, careers, insurance information, and FAQs.

### FR-112: Website Appointment Calls To Action

Appointment calls to action must be visible from relevant pages such as doctor, department, service, article, and contact pages.

## Website Branding And Theme

### FR-113: Logo And Favicon

Hospitals must be able to configure website logo and favicon.

### FR-114: Brand Colors And Typography

Hospitals must be able to configure brand colors, typography preference, button style, and common visual theme.

### FR-115: Website Templates

Hospitals must be able to choose from approved healthcare website templates or controlled layout settings.

### FR-115A: Initial Template Set

Initial launch should provide a small controlled set of healthcare templates, recommended as multispecialty hospital, clinic/specialty center, and multi-branch hospital.

### FR-116: Theme Preview

Hospitals must be able to preview theme and layout changes before publishing.

### FR-117: Theme Consistency

Theme changes must apply consistently across home, doctor, department, article, appointment, and contact pages.

### FR-118: Asset Management

Hospitals must be able to manage uploaded logos, photos, images, videos, and website media assets.

### FR-119: Controlled Layout Customization

Theme settings must support hero layout, page sections, imagery, and common components while preventing broken or unprofessional layouts.

## Website Content Management

### FR-120: Categorized Content

The system must allow hospital admins or content staff to manage categorized website content.

### FR-121: Content Categories

Content categories may include services, specialties, doctors, health articles, announcements, events, offers, testimonials, gallery, videos, and FAQs.

### FR-122: Draft And Published Content

Content must support draft and published states.

### FR-123: Content Ordering And Featuring

Content must support ordering and featured items.

### FR-124: Content Media

Content must support images and social sharing images.

### FR-125: Content SEO

Content must support SEO title and SEO description.

### FR-126: Content Filtering

Public website visitors must be able to filter content by category where applicable.

### FR-127: Tenant-Specific Content

Hospital-specific content must not appear on another hospital's website.

### FR-128: Localized Content

Content must support localization fields where multilingual websites are enabled.

## Social Media Integration

### FR-129: Social Links

The system must allow each hospital to configure official social media links or handles.

### FR-130: Supported Social Channels

The system must support Facebook, Instagram, LinkedIn, YouTube, X/Twitter, WhatsApp, and an extensible model for additional platforms.

### FR-131: Social Display Locations

Social links must be displayable in the website header, footer, contact page, and mobile action areas.

### FR-132: Social Sharing Metadata

Website pages and content must support social sharing metadata.

### FR-133: Social Feeds And Embeds

The system must embed or display social feeds, videos, reviews, or posts where technically supported and approved by the hospital.

### FR-134: Manual Social Cards

The system must support manual social cards that link to social media posts when direct feed integration is unavailable or unreliable.

### FR-135: Social Feed Fallback

The website must continue working if social embeds are blocked, rate-limited, expired, or unavailable.

### FR-135A: Launch Social Integration Priority

Launch behavior should prioritize reliable social links, WhatsApp actions, YouTube/video embeds, and manual social cards. Real-time feed integrations should be added only where provider access is stable and approved by the hospital.

### FR-136: WhatsApp Actions

The website must support click-to-WhatsApp actions where configured.

## Website Domains And Publishing

### FR-137: Platform Subdomain

Each hospital website must support a platform-provided subdomain.

### FR-138: Custom Domain

Each hospital website must support custom domain configuration.

### FR-139: Domain Verification

Custom domains must support DNS verification, SSL/TLS provisioning, domain status checks, and fallback to platform subdomain if verification fails.

### FR-140: Preview And Publish

Website changes must support preview and publish workflow.

### FR-141: Draft And Published Versions

Website publishing must maintain draft and published versions so unpublished edits do not affect the live website.

### FR-142: SEO-Friendly Publishing

Published websites must be SEO-friendly and mobile-friendly.

## Analytics And Reports

### FR-143: Appointment Reports

The system must provide reports by appointment date, department, doctor, branch, status, source, cancellation trends, and no-show trends.

### FR-144: Queue Token Reports

The system must provide reports for token volume, wait time, service time, transfers, skipped tokens, no-show tokens, and priority changes by reason.

### FR-144A: Follow-Up Reports

The system must provide reports for upcoming follow-ups, overdue follow-ups, contact attempts, follow-up conversions, and repeat consultations generated from follow-up workflows.

### FR-144B: Hospital Value Metrics

The system should report hospital value metrics such as appointments recovered, leads converted, repeat consultations generated, staff time saved estimate, and outstanding appointment payments collected where data is available.

### FR-144C: Outcome Metric Transparency

Outcome metrics must include documented formulas, eligibility rules, attribution windows, deduplication rules, reporting period, and labels that distinguish directly observed results from estimated impact.

### FR-145: Website Analytics

The system must track appointment form starts, appointment submissions, traffic sources, social media referrals, popular doctors, departments, services, and content pages where privacy rules allow.

### FR-146: Privacy-Safe Analytics

Analytics must not store sensitive patient details in third-party tools or public logs.

### FR-147: Report Export

The system must allow authorized users to export reports, appointment lists, patient lists, audit logs, and content inventories.

## Audit And Compliance Support

### FR-148: Audit Logs

The system must maintain audit logs for tenant setup, subscription changes, user changes, website publishing, appointment changes, token priority changes, billing actions, and other important actions.

### FR-149: Sensitive Data Protection

The system must treat patient appointment details as sensitive personal data.

### FR-150: Minimum Data Collection

Public forms must collect only the patient information required for appointment handling.

### FR-151: Privacy Consent

Public appointment forms must support privacy consent language.

### FR-152: Privacy Documents

Each hospital website should support privacy policy, terms, cookie notice, and data processing language where required.

### FR-153: Data Exposure Prevention

The system must avoid exposing appointment or patient data through public URLs, logs, analytics, or social integrations.

### FR-154: Data Retention, Export, And Deletion

The system must support confirmed data retention, export, and deletion requirements before production launch.

## File Uploads And Tenant Storage

### FR-154A: Tenant-Owned Files

Uploaded files must be tenant-owned and stored under tenant-specific logical namespaces with backend authorization checks.

### FR-154B: File Access Policy Separation

Website media, doctor photos, logos, payment proofs, generated reports, and future patient documents must use separate access policies appropriate to their sensitivity.

### FR-154C: Upload Validation

File uploads must validate permitted file type, size, and purpose before storage.

### FR-154D: Public And Private File Access

Public website assets may be served publicly only after validation and publish approval; private operational files must require authenticated and authorized access.

### FR-154E: Storage Usage Tracking

Storage usage must be measured per hospital and contribute to subscription plan usage where applicable.

### FR-154F: Sensitive Data In File References

File paths, object keys, logs, and URLs must not expose patient names, ABHA IDs, hospital patient IDs, phone numbers, or other sensitive details.

## Data Export, Backup, And Portability

### FR-155: Operational Data Export

The system must allow authorized users to export operational data according to role permissions.

### FR-156: Tenant Offboarding Export

The system must support tenant data export packages for customer offboarding where permitted.

### FR-157: Backup Support

The system must support backup of database and uploaded content.

### FR-158: Restore Support

The system must support restoration planning for database and uploaded content.

## Production Operations

### FR-159: Observability

The system must support application logs, error tracking, uptime checks, background job monitoring, and notification provider health checks.

### FR-160: Provider Failure Handling

The system must degrade gracefully when social media integrations, notification providers, or payment providers are temporarily unavailable.

### FR-161: Availability Target

The product must define a target uptime/SLA before production launch.

### FR-162: Front-Desk Efficiency

Common staff actions must be efficient enough for front-desk use during busy hospital hours.

## Critical Decisions Before Implementation

Only the following decisions should remain open before implementation starts:

1. Final SaaS plan names, prices, and exact limits.
2. First production providers for SaaS billing, SMS, email, WhatsApp, hosting, storage, and managed database.
3. First launch region legal/compliance requirements, including privacy policy, consent text, data retention, and ABHA handling.
4. Whether the first launch hospital needs appointment payment enabled, or whether launch starts with pay-at-hospital/manual payment only.
5. Any additional requirements from earlier chat threads or private documents that should be merged before sign-off.
