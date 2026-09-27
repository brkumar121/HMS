create table hospital_staff (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    email varchar(180) not null,
    display_name varchar(160) not null,
    role varchar(80) not null,
    status varchar(30) not null,
    invited_at timestamp with time zone not null,
    unique (tenant_id, email, role)
);

create index idx_hospital_staff_tenant on hospital_staff(tenant_id);
