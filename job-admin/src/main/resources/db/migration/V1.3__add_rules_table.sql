CREATE TABLE rule (
  id       INT(11) AUTO_INCREMENT,
  script VARCHAR(45),
  pattern     VARCHAR(45),
  retry_times INT(11),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;