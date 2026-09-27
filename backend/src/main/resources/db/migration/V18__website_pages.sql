create table website_pages (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    slug varchar(120) not null,
    title varchar(180) not null,
    body varchar(20000) not null,
    status varchar(30) not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, slug)
);
create index idx_website_pages_tenant_status on website_pages(tenant_id, status);
