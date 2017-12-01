CREATE TABLE admin_user (
  id       INT(11) AUTO_INCREMENT,
  username VARCHAR(45),
  name     VARCHAR(45),
  password VARCHAR(450),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE executor_group (
  name        VARCHAR(256),
  description VARCHAR(450),
  PRIMARY KEY (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE executor (
  name                 VARCHAR(256),
  executor_group_name  VARCHAR(45),
  ip_address           VARCHAR(200),
  online_status        VARCHAR(45),
  last_heart_beat_time BIGINT(20),
  PRIMARY KEY (name),
  CONSTRAINT fk_executor_to_executor_group_id FOREIGN KEY (executor_group_name) REFERENCES executor_group (name)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE work_flow (
  id            INT(11) AUTO_INCREMENT,
  name          VARCHAR(45),
  description   VARCHAR(200),
  run_interval  INT(11),
  status        VARCHAR(45),
  last_run_time BIGINT(20),
  scheduled     BOOLEAN,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE job_group (
  id               INT(11) AUTO_INCREMENT,
  name             VARCHAR(45),
  description      VARCHAR(200),
  work_flow_id     INT(11),
  step INT(11),
  status           VARCHAR(45),
  PRIMARY KEY (id),
  CONSTRAINT fk_job_group_to_work_flow_id FOREIGN KEY (work_flow_id) REFERENCES work_flow (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE job (
  id                    INT(11) AUTO_INCREMENT,
  name                  VARCHAR(45),
  description           VARCHAR(200),
  job_group_id          INT(10),
  status                VARCHAR(45),
  executor_group_name   VARCHAR(256),
  script                VARCHAR(2048),
  current_executor_name VARCHAR(256),
  PRIMARY KEY (id),
  CONSTRAINT fk_job_to_job_group_id FOREIGN KEY (job_group_id) REFERENCES job_group (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_job_to_executor_group_id FOREIGN KEY (executor_group_name) REFERENCES executor_group (name)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_job_to_executor__id FOREIGN KEY (current_executor_name) REFERENCES executor (name)
    ON DELETE SET NULL
    ON UPDATE SET NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

