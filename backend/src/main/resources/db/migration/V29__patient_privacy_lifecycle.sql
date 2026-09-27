alter table patients add column anonymized boolean not null default false;
alter table patients add column anonymized_at timestamp with time zone;
