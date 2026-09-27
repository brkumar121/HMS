create table hospital_branches (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    code varchar(40) not null,
    name varchar(180) not null,
    address varchar(500),
    phone varchar(40),
    active boolean not null default true,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, code),
    unique (tenant_id, name)
);
