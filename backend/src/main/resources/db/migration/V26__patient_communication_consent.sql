alter table patients add column sms_consent boolean not null default true;
alter table patients add column email_consent boolean not null default true;
alter table patients add column whatsapp_consent boolean not null default true;
alter table patients add column consent_updated_at timestamp with time zone;
