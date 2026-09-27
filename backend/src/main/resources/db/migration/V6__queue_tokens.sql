create table queue_tokens (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    appointment_id uuid not null references appointments(id) on delete cascade,
    doctor_id uuid not null references doctors(id) on delete cascade,
    token_date date not null,
    token_number integer not null,
    status varchar(30) not null,
    priority boolean not null default false,
    priority_reason varchar(120),
    priority_note varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, doctor_id, token_date, token_number),
    unique (appointment_id)
);
create index idx_queue_tokens_order on queue_tokens(tenant_id, doctor_id, token_date, status, priority, token_number);
