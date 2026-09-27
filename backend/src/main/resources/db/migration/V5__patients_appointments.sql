create table patients (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    full_name varchar(180) not null,
    phone varchar(40) not null,
    hospital_patient_id varchar(100),
    abha_id varchar(100),
    abha_consent_at timestamp with time zone,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, phone, hospital_patient_id),
    unique (tenant_id, hospital_patient_id),
    unique (tenant_id, abha_id)
);
create table appointments (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    patient_id uuid not null references patients(id) on delete cascade,
    doctor_id uuid not null references doctors(id) on delete cascade,
    starts_at timestamp with time zone not null,
    ends_at timestamp with time zone not null,
    status varchar(30) not null,
    source varchar(30) not null,
    reason varchar(500),
    notes varchar(1000),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, doctor_id, starts_at)
);
create index idx_patients_tenant_phone on patients(tenant_id, phone);
create index idx_appointments_tenant_date on appointments(tenant_id, starts_at, status);
