create table hospital_website_settings (
    id uuid primary key,
    tenant_id uuid not null unique references tenants(id) on delete cascade,
    logo_url varchar(500),
    primary_color varchar(20),
    secondary_color varchar(20),
    font_family varchar(100),
    tagline varchar(300),
    contact_phone varchar(40),
    contact_email varchar(180),
    address varchar(500),
    facebook_url varchar(500),
    instagram_url varchar(500),
    youtube_url varchar(500),
    published boolean not null default false,
    updated_at timestamp with time zone not null
);
