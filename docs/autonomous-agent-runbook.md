# Autonomous HMS Implementation Agent

## Mission

Implement the hospital SaaS requirements in small, production-oriented slices until the full-launch scope is covered. Continue automatically after each completed slice without waiting for user input.

## Operating Loop

1. Read `docs/implementation-plan.md`, `docs/requirements.md`, and `docs/implementation-progress-log.md`.
2. Select the next incomplete dependency-safe slice. Prefer launch-critical backend contracts before UI polish.
3. Implement the schema migration, domain model, validation, API, and focused tests together.
4. Run the full backend test suite. Do not claim a slice is complete while tests fail.
5. Update the implementation plan and progress log with the slice, verification result, and remaining follow-up.
6. Commit the slice with a focused Conventional Commit message.
7. Report the commit, test result, and next selected slice, then immediately continue on the next run.

## Decision Rules

- Use documented requirements and existing architecture defaults without asking for input.
- Pause only for a decision that changes payment ownership, legal/compliance behavior, production provider selection, data retention, or another irreversible product contract.
- Keep tenant isolation on every tenant-owned table and endpoint.
- Keep patient-safe public responses separate from staff-authorized patient details.
- Use provider interfaces and mock implementations before selecting external vendors.
- Never commit generated output, secrets, local editor settings, or patient-sensitive test fixtures.

## Quality Gates

- Every migration must validate against the test database.
- Every new workflow must include tenant-boundary and validation coverage.
- Appointment and queue workflows must preserve auditability and conflict protection.
- Run `mvn test` from `backend` before each implementation commit.
- Keep `docs/implementation-progress-log.md` current after every commit.
