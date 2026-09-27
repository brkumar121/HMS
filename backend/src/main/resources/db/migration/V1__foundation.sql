create table tenants (
    id uuid primary key,
    slug varchar(80) not null unique,
    name varchar(180) not null,
    legal_name varchar(180),
    status varchar(30) not null,
    primary_email varchar(180),
    primary_phone varchar(40),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table users (
    id uuid primary key,
    email varchar(180) not null unique,
    display_name varchar(160) not null,
    status varchar(30) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table tenant_users (
    id uuid primary key,
    tenant_id uuid not null references tenants(id),
    user_id uuid not null references users(id),
    role_key varchar(80) not null,
    status varchar(30) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, user_id, role_key)
);

create table audit_logs (
    id uuid primary key,
    tenant_id uuid references tenants(id),
    actor_user_id uuid references users(id),
    action varchar(120) not null,
    entity_type varchar(120) not null,
    entity_id varchar(120),
    summary varchar(1000),
    created_at timestamp with time zone not null
);

create index idx_tenant_users_tenant_id on tenant_users(tenant_id);
create index idx_tenant_users_user_id on tenant_users(user_id);
create index idx_audit_logs_tenant_id_created_at on audit_logs(tenant_id, created_at);
