ALTER TABLE executor
  ADD COLUMN cpu_load DOUBLE DEFAULT 0;
ALTER TABLE executor
  ADD COLUMN free_memory BIGINT(20) DEFAULT 0;
ALTER TABLE executor
  ADD COLUMN total_memory BIGINT(20) DEFAULT 0;


CREATE TABLE job_error_log (
  id           BIGINT(20) AUTO_INCREMENT,
  job_group_id INT(10),
  job_id       INT(10),
  workflow_id  INT(10),
  content      VARCHAR(20000),
  log_time     BIGINT(20),
  executor_name VARCHAR(255),
  PRIMARY KEY (id)
)
  ENGINE = MyISAM
  DEFAULT CHARSET = utf8;

CREATE INDEX job_log_job_id_index ON job_error_log(job_id);
CREATE INDEX job_log_log_time ON job_error_log(log_time);
