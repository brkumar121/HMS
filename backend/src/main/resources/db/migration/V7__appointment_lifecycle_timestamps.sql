alter table appointments add column requested_at timestamp with time zone;
alter table appointments add column confirmed_at timestamp with time zone;
alter table appointments add column checked_in_at timestamp with time zone;
alter table appointments add column completed_at timestamp with time zone;
alter table appointments add column cancelled_at timestamp with time zone;
