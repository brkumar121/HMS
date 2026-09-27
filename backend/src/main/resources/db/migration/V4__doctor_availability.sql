create table doctor_availability (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    doctor_id uuid not null references doctors(id) on delete cascade,
    day_of_week smallint not null check (day_of_week between 1 and 7),
    start_time time not null,
    end_time time not null,
    slot_duration_minutes integer not null check (slot_duration_minutes between 5 and 480),
    session_name varchar(100),
    location varchar(180),
    active boolean not null default true,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    check (end_time > start_time)
);

create table doctor_leave_periods (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    doctor_id uuid not null references doctors(id) on delete cascade,
    starts_at timestamp with time zone not null,
    ends_at timestamp with time zone not null,
    reason varchar(300),
    created_at timestamp with time zone not null,
    check (ends_at > starts_at)
);

create index idx_doctor_availability_doctor on doctor_availability(tenant_id, doctor_id, day_of_week);
create index idx_doctor_leave_doctor on doctor_leave_periods(tenant_id, doctor_id, starts_at, ends_at);
