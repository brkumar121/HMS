create table follow_ups (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    patient_id uuid not null references patients(id) on delete cascade,
    source_appointment_id uuid references appointments(id) on delete set null,
    doctor_id uuid references doctors(id) on delete set null,
    recommended_from date,
    recommended_to date,
    reason varchar(500) not null,
    priority varchar(30) not null,
    responsible_team varchar(160),
    notes varchar(1000),
    status varchar(30) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);
create index idx_follow_ups_worklist on follow_ups(tenant_id, status, recommended_from, priority);
