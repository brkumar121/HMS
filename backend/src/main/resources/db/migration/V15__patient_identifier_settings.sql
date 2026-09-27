create table patient_identifier_settings (
    id uuid primary key,
    tenant_id uuid not null unique references tenants(id) on delete cascade,
    hospital_identifier_label varchar(80) not null default 'Hospital Patient ID',
    hospital_identifier_required boolean not null default false,
    hospital_identifier_visible boolean not null default true,
    hospital_identifier_format varchar(120),
    abha_enabled boolean not null default false,
    abha_required boolean not null default false,
    abha_consent_text varchar(1000),
    updated_at timestamp with time zone not null
);
