create table subscription_invoices (
 id uuid primary key,
 tenant_id uuid not null references tenants(id) on delete cascade,
 invoice_number varchar(80) not null unique,
 plan_key varchar(80) not null,
 subtotal numeric(12,2) not null,
 tax_amount numeric(12,2) not null,
 total_amount numeric(12,2) not null,
 currency varchar(3) not null,
 status varchar(30) not null,
 issued_on date not null,
 due_on date not null,
 paid_on date,
 failure_reason varchar(500),
 created_at timestamp with time zone not null
);
create index idx_subscription_invoices_tenant on subscription_invoices(tenant_id, issued_on desc);
