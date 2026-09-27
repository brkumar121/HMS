create table doctors (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    display_name varchar(180) not null,
    specialty varchar(160),
    photo_url varchar(500),
    qualifications varchar(500),
    experience_years integer,
    languages varchar(500),
    consultation_timings varchar(1000),
    public_visible boolean not null default false,
    active boolean not null default true,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table doctor_departments (
    doctor_id uuid not null references doctors(id) on delete cascade,
    department_id uuid not null references departments(id) on delete cascade,
    primary key (doctor_id, department_id)
);

create index idx_doctors_tenant_id on doctors(tenant_id);
create index idx_doctor_departments_department_id on doctor_departments(department_id);
