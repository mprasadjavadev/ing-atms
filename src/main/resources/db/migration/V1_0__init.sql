-- -----------------------------------------------------
-- Schema ing-atms
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ing_atms`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_bin;
USE `ing_atms`;

-- -----------------------------------------------------
-- Table `ing_atms`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ing_atms`.`users` (
  `id`       INT(11)          NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50)
             COLLATE utf8_bin NOT NULL,
  `password` VARCHAR(256)
             COLLATE utf8_bin NOT NULL,
  `role`     VARCHAR(50)
             COLLATE utf8_bin NOT NULL,
  `active`   INT(1)           NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;