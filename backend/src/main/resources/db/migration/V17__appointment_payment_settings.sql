create table appointment_payment_settings (
    tenant_id uuid primary key references tenants(id) on delete cascade,
    mode varchar(40) not null,
    currency varchar(3) not null,
    instructions varchar(2000),
    bank_account_name varchar(180),
    bank_account_number varchar(80),
    bank_ifsc varchar(20),
    upi_id varchar(120),
    payment_link varchar(500),
    updated_at timestamp with time zone not null
);
