# Requirements Review For Full Launch

## Review Summary

Reviewed on 2026-09-20 against the core product idea:

- A subscription SaaS platform for multiple hospitals.
- Each hospital can onboard, configure operations, and use appointment management.
- Each hospital receives a configurable website with logo, theme, layout, categorized content, and social media integration.
- The target is full launch, not MVP.

The current requirements already covered the core concept well, including tenancy, appointment workflows, doctor schedules, queue tokens, website customization, content, social links, analytics, audit, and role-based workflows.

## Gaps Found And Updated

- Full-launch wording: changed requirements from general/MVP-style language to full-launch product scope.
- Subscription billing: added billing, invoice history, payment status, failed-payment handling, plan upgrades/downgrades, tax details, and renewal reminders.
- Tenant access rules: added controlled inactive subscription behavior such as read-only mode, disabled publishing, disabled intake, and grace-period access.
- Multi-branch support: changed from future consideration to launch requirement.
- Data imports: added required import tools for doctors, departments, services, and patient lists with validation and duplicate detection.
- Custom domains: added platform subdomains, custom domains, DNS verification, SSL/TLS status, and fallback behavior.
- Website publishing: added draft/published version isolation.
- Notifications: changed SMS/email/WhatsApp from TBD to required provider-backed launch channels.
- Social media: clarified supported platforms and launch behavior for embeds, feeds, manual cards, and graceful fallback.
- Multilingual support: changed from consideration to available capability.
- Production operations: added monitoring, logs, provider health checks, backups, disaster recovery, privacy documents, and production readiness.
- Administration: added exports, billing management, invoice/payment visibility, and operational data portability.
- Implementation plan: removed MVP/defer language and added full-launch chunks for billing, custom domains, imports/exports, and launch operations.

## Remaining Product Decisions

These are not missing requirements, but decisions needed before implementation:

- Exact subscription plans, prices, and limits.
- Billing provider.
- SMS, email, and WhatsApp providers.
- Launch languages.
- Number of initial website templates.
- Level of direct theme customization hospitals can control.
- Whether patient appointment payments are required at launch.
- Whether patients need login accounts at launch.
- Which social platforms need real feed APIs at launch versus manual cards and embeds.
- Deployment provider and production SLA target.

## Recommendation

The requirements are now strong enough to start technical implementation planning for a full-launch product. The next useful step is to freeze launch decisions, then begin implementation chunk by chunk from the foundation, tenancy, RBAC, and database schema.
