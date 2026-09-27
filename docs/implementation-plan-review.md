# Implementation Plan Review

## Review Summary

Reviewed on 2026-09-21 for implementation quality, sequencing, maintainability, and low credit consumption.

The implementation plan is now better suited for incremental delivery because it:

- Uses a clear hybrid architecture: Next.js frontend and Spring Boot backend.
- Separates requirements from implementation execution details.
- Defines chunk boundaries and dependency order.
- Adds quality gates for every chunk.
- Adds a standard chunk prompt/template.
- Adds low-credit prompt guidance.
- Adds context budget rules so future implementation work does not need to reload every document.

## Improvements Made

- Added plan maintenance rules.
- Added contracts-first implementation method.
- Added standard chunk template.
- Added chunk quality gates.
- Added high-risk module gates for scheduling, token priority, billing, notifications, website publishing, and imports.
- Added chunk dependency rules.
- Corrected full-launch build sequence to include Chunk 2: SaaS Platform Management before hospital onboarding.
- Added low-credit implementation prompt pattern.
- Added context budget rules.
- Added implementation progress checklist.
- Cleaned duplicate uptime/SLA wording in requirements.

## Best Way To Use The Plan

For coding work, do not ask an implementation agent to build multiple distant modules at once. Use one chunk or one sub-chunk per prompt.

Recommended prompt shape:

```text
Implement Chunk X from docs/implementation-plan.md.
Use docs/requirements.md only for requirements related to this chunk.
Do not work on unrelated chunks.
Before editing, inspect existing files and summarize the smallest change set.
After editing, run relevant tests/build checks.
```

## When To Split A Chunk

Split a chunk if it touches too many unrelated concerns.

Examples:

- Split appointment workflow into patient lookup, appointment creation, status transitions, and search.
- Split queue tokens into token generation, queue board, prioritization, and token reports.
- Split website into theme model, preview/publish, public rendering, and SEO.
- Split billing into plan model, provider integration, invoice UI, and webhook handling.

## Quality Priorities

The highest-risk areas should always receive tests before UI polish:

- Tenant isolation.
- RBAC and permission enforcement.
- Appointment conflict prevention.
- Token prioritization reason and audit history.
- Billing webhook idempotency.
- Notification failure and retry handling.
- Draft versus published website visibility.
- Import duplicate detection and validation.
