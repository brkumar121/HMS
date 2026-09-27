create table departments (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    code varchar(40) not null,
    name varchar(160) not null,
    description varchar(500),
    active boolean not null default true,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, code),
    unique (tenant_id, name)
);

create index idx_departments_tenant_id on departments(tenant_id);
