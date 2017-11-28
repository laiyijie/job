CREATE TABLE executor_group (
  id          INT(11) AUTO_INCREMENT,
  name        VARCHAR(45),
  description VARCHAR(450),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE executor (
  id                INT(11) AUTO_INCREMENT,
  name              VARCHAR(45),
  executor_group_id INT(11),
  ip_address        VARCHAR(200),
  online_status     VARCHAR(45),
  PRIMARY KEY (id),
  CONSTRAINT fk_executor_to_executor_group_id FOREIGN KEY (executor_group_id) REFERENCES executor_group (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE work_flow (
  id           INT(11) AUTO_INCREMENT,
  name         VARCHAR(45),
  description  VARCHAR(200),
  run_interval INT(11),
  status       VARCHAR(45),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE job_group (
  id               INT(11) AUTO_INCREMENT,
  name             VARCHAR(45),
  description      VARCHAR(200),
  work_flow_id     INT(11),
  pre_job_group_id INT(11),
  status           VARCHAR(45),
  PRIMARY KEY (id),
  CONSTRAINT fk_job_group_to_work_flow_id FOREIGN KEY (id) REFERENCES work_flow (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_pre_job_group_to_job_group_id FOREIGN KEY (pre_job_group_id) REFERENCES job_group (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE job (
  id                INT(11) AUTO_INCREMENT,
  name              VARCHAR(45),
  description       VARCHAR(200),
  job_group_id      INT(10),
  status            VARCHAR(45),
  executor_group_id INT(11),
  PRIMARY KEY (id),
  CONSTRAINT fk_job_to_job_group_id FOREIGN KEY (job_group_id) REFERENCES job_group (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_job_to_executor_group_id FOREIGN KEY (executor_group_id) REFERENCES executor_group (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

