CREATE TABLE customer (
  id         BIGINT(20) AUTO_INCREMENT,
  first_name VARCHAR(45),
  last_name  VARCHAR(45),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;