
CREATE TABLE work_flow(
    id INT(11) AUTO_INCREMENT,
    name VARCHAR(45),
    description VARCHAR(200),
    run_interval INT(11),
    PRIMARY KEY(id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE job (
  id         BIGINT(20) AUTO_INCREMENT,
  first_name VARCHAR(45),
  last_name  VARCHAR(45),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;