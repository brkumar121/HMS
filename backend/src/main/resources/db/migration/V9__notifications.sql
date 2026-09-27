create table notification_messages (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    patient_id uuid references patients(id) on delete set null,
    appointment_id uuid references appointments(id) on delete set null,
    channel varchar(20) not null,
    template_key varchar(100) not null,
    recipient varchar(180) not null,
    subject varchar(180),
    body varchar(4000) not null,
    status varchar(30) not null,
    consent_required boolean not null default true,
    sent_at timestamp with time zone,
    failure_reason varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);
create index idx_notifications_queue on notification_messages(tenant_id, status, created_at);
