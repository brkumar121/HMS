create table hospital_services (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    department_id uuid references departments(id) on delete set null,
    code varchar(40) not null,
    name varchar(180) not null,
    description varchar(500),
    consultation_fee numeric(12,2),
    active boolean not null default true,
    public_visible boolean not null default true,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, code),
    unique (tenant_id, name)
);
create table doctor_services (
    doctor_id uuid not null references doctors(id) on delete cascade,
    service_id uuid not null references hospital_services(id) on delete cascade,
    primary key (doctor_id, service_id)
);
