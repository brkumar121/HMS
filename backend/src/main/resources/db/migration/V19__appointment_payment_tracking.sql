alter table appointments add column payment_status varchar(30) not null default 'NOT_REQUIRED';
alter table appointments add column payment_reference varchar(180);
