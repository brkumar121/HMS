create table website_content_items (
    id uuid primary key,
    tenant_id uuid not null references tenants(id) on delete cascade,
    category varchar(40) not null,
    title varchar(240) not null,
    slug varchar(240) not null,
    summary varchar(500),
    body varchar(12000) not null,
    image_url varchar(500),
    status varchar(30) not null,
    published_at timestamp with time zone,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    unique (tenant_id, slug)
);
create index idx_website_content_public on website_content_items(tenant_id, category, status, published_at);
