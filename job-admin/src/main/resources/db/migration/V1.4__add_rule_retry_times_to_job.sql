ALTER TABLE job ADD COLUMN rule_retry_times INT (11) DEFAULT 0;
ALTER TABLE job ADD COLUMN rule_retry_flag BOOL DEFAULT FALSE ;
ALTER TABLE job ADD COLUMN rule_max_retry_times INT (11) DEFAULT 0;
