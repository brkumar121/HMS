# HMS SaaS

SaaS hospital appointment management platform with a complimentary configurable public website for each subscribed hospital.

## Documentation

- [Requirements](docs/requirements.md)
- [Functional Requirements For Final Review](docs/functional-requirements-final-review.md)
- [Requirements Review](docs/requirements-review.md)
- [Implementation Plan](docs/implementation-plan.md)
- [Implementation Plan Review](docs/implementation-plan-review.md)
- [Improvement Opportunities](docs/improvement-opportunities.md)
- [Prototype Screen Map](docs/prototype-screen-map.md)

## Prototype

- [Clickable role-based prototype](prototype/index.html)

The prototype is a static HTML/CSS/JavaScript artifact and can be opened directly in a browser.

## Project Structure

- `backend/`: Spring Boot API, tenant foundation, Flyway migrations, tests.
- `frontend/`: Next.js admin/public website shell.
- `docs/`: requirements, implementation plan, prototype screen map, review notes.
- `prototype/`: static clickable prototype used as a UX reference only.

## Technical Direction

- Frontend and public hospital websites: Next.js, React, TypeScript.
- Core backend: Spring Boot, Java 21, Spring Security.
- Database: PostgreSQL.
- Cache/jobs: Redis or a queue-backed worker setup.
- Storage: S3-compatible object storage.

## Local Development

Start PostgreSQL:

```powershell
docker compose up -d postgres
```

Run backend tests:

```powershell
cd backend
mvn test
```

Run the backend API:

```powershell
cd backend
mvn spring-boot:run
```

Install and run the frontend:

```powershell
cd frontend
npm.cmd install
npm.cmd run dev
```

The PowerShell `npm` shim may be blocked by execution policy on Windows; use `npm.cmd` instead.

## Current Implementation Status

- Backend foundation exists with Spring Boot, Flyway, PostgreSQL config, H2 test profile, tenant/user/audit tables, public health endpoint, and tenant list/detail endpoints.
- Frontend foundation exists with Next.js, Tailwind, a role-based landing shell, and API health check wiring.
- Backend tests pass with `mvn test`.
- Frontend dependency install still needs to be completed locally; the first `npm.cmd install` attempt was stopped after running silently for several minutes.
