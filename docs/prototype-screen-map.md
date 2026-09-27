# Prototype Screen Map

## Purpose

This document maps the clickable prototype to the requirements. The prototype is available at [prototype/index.html](../prototype/index.html).

## Roles Covered

- SaaS Platform Owner
- Hospital Owner / Admin
- Reception / Front Desk
- Doctor
- Content / Marketing Staff
- Patient / Public Website Visitor

## Screens Covered

### SaaS Platform Owner

- Platform Dashboard: hospitals, ARR, usage alerts, support flags, tenant lifecycle controls.
- Plans And Feature Flags: subscription plans, plan limits, queue token feature flag, social feed feature flag, patient login flag, multi-branch flag.
- Support And Audit: tenant-safe support access request and platform audit trail.

### Hospital Owner / Admin

- Hospital Command Center: setup health, appointments, website status, social referrals, setup checklist, operational snapshot.
- Hospital Profile And Branches: hospital identity, contact details, emergency number, hours, address, branch list.
- Doctors, Departments, Availability: department setup, doctor schedule, rooms, sessions, leave.
- Website Theme Builder: template, logo, colors, hero layout, preview, draft, publish.
- Token Rules And Priority Reasons: token scope, display board, priority behavior, configurable priority reasons.
- Reports And Analytics: appointment sources, no-show rate, priority token report, content views, audit events.

### Reception / Front Desk

- Front Desk Dashboard: waiting, pending requests, checked-in patients, priority tokens, appointment workboard.
- Create Or Confirm Appointment: patient lookup, booking form, source capture, doctor availability, slot conflicts.
- Queue And Token Board: token order, priority token, hold, skip, no-show, required prioritization reason and note.
- Website And Social Requests: requests from website, WhatsApp, and Instagram with confirmation actions.

### Doctor

- Doctor Schedule And Queue: next token, waiting count, completed count, no-shows, current patient context.
- Appointments By Date And Status: appointment table with token, status, patient, and reason.

### Content / Marketing Staff

- Website Content Dashboard: categorized content, drafts, social cards, popular page, social handles.
- Content Editor And Social Card: article editing, SEO fields, social image, manual social post card.

### Patient / Public Website Visitor

- Hospital Website Home: branded website preview, services, doctors, appointment call to action, social/contact actions.
- Find Doctor And Book: doctor search/filter, profile snippets, appointment request form.
- Patient Token Status: patient-safe token display, now-serving token, waiting estimate, next steps.

## Mobile Coverage

The prototype includes a persistent mobile preview panel for every role and screen. It demonstrates compact role-based actions, queue/token cards, and quick actions such as call, WhatsApp, and map.

## Requirement Areas Represented

- Multi-tenant SaaS management
- Subscription plans and feature flags
- Hospital onboarding and setup health
- Role-based access and workflows
- Appointment creation, confirmation, rescheduling, check-in, completion, no-show
- Doctor availability and slot conflict handling
- Queue token generation and prioritization with required reason
- Token audit and priority reports
- Configurable hospital website
- Theme, logo, layout, and publish workflow
- Categorized website content
- Social media handles and fallback social cards
- Patient booking journey
- Mobile-first patient actions
- Analytics, audit trail, and operational reports
