ALTER TABLE job ADD COLUMN max_retry_times INT (11) DEFAULT 0;
ALTER TABLE job ADD COLUMN retry_times INT (11) DEFAULT 0;
ALTER TABLE job ADD COLUMN retry_regex VARCHAR(2555) DEFAULT NULL;
ALTER TABLE job ADD COLUMN retry_flag BOOL DEFAULT FALSE ;
