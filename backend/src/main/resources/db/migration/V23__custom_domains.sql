create table custom_domains (
 id uuid primary key,
 tenant_id uuid not null references tenants(id),
 domain varchar(253) not null unique,
 verification_token varchar(120) not null,
 status varchar(30) not null,
 ssl_status varchar(30) not null,
 verified_at timestamp with time zone,
 created_at timestamp with time zone not null,
 updated_at timestamp with time zone not null
);
create index idx_custom_domains_tenant on custom_domains(tenant_id);
