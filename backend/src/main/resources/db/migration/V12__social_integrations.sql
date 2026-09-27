create table social_connections (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    platform varchar(30) not null,
    handle varchar(180) not null,
    profile_url varchar(500),
    enabled boolean not null default true,
    last_synced_at timestamp with time zone,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, platform)
);
create table social_posts (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    connection_id uuid not null references social_connections(id) on delete cascade,
    external_id varchar(240) not null,
    title varchar(240),
    excerpt varchar(500),
    post_url varchar(500) not null,
    media_url varchar(500),
    published_at timestamp with time zone,
    visible boolean not null default true,
    created_at timestamp with time zone not null,
    unique (tenant_id, connection_id, external_id)
);
