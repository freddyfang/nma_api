-- MySQL Script generated by MySQL Workbench
-- Sat Sep 22 18:46:06 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema nma
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `nma` ;

-- -----------------------------------------------------
-- Schema nma
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `nma` DEFAULT CHARACTER SET utf8 ;
USE `nma` ;

-- -----------------------------------------------------
-- Table `nma`.`allergy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`allergy` ;

CREATE TABLE IF NOT EXISTS `nma`.`allergy` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(128) NOT NULL,
  `name` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`seq`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`employee` ;

CREATE TABLE IF NOT EXISTS `nma`.`employee` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `emp_no` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(512) NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `address` VARCHAR(2048) NULL DEFAULT NULL,
  `tel` VARCHAR(64) NOT NULL,
  `ssn` CHAR(9) NOT NULL,
  `staff_type` TINYINT(4) NOT NULL,
  `disable` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`seq`),
  UNIQUE INDEX `ssn_UNIQUE` (`ssn` ASC),
  UNIQUE INDEX `emp_no_UNIQUE` (`emp_no` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`physician`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`physician` ;

CREATE TABLE IF NOT EXISTS `nma`.`physician` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `annual_salary` DECIMAL(10,2) NULL DEFAULT NULL,
  `specialty` VARCHAR(1024) NOT NULL,
  `employee` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_physician_employee_emp_idx` (`employee` ASC),
  CONSTRAINT `fk_physician_employee_emp`
    FOREIGN KEY (`employee`)
    REFERENCES `nma`.`employee` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`patient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`patient` ;

CREATE TABLE IF NOT EXISTS `nma`.`patient` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `ssn` CHAR(9) NOT NULL,
  `patient_no` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(512) NOT NULL,
  `birth` DATE NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `address` VARCHAR(2048) NULL DEFAULT NULL,
  `tel` VARCHAR(64) NOT NULL,
  `blood_type` VARCHAR(5) NOT NULL,
  `blood_surger` DECIMAL(10,1) NULL DEFAULT '0.0',
  `ldl` DECIMAL(10,1) NULL DEFAULT '0.0',
  `hdl` DECIMAL(10,1) NULL DEFAULT '0.0',
  `triglyceride` DECIMAL(10,1) NULL DEFAULT '0.0',
  `primary_doctor` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`seq`),
  UNIQUE INDEX `ssn_UNIQUE` (`ssn` ASC),
  UNIQUE INDEX `patient_num_UNIQUE` (`patient_no` ASC),
  INDEX `fk_physician_patient_doctor_idx` (`primary_doctor` ASC),
  CONSTRAINT `fk_physician_patient_doctor`
    FOREIGN KEY (`primary_doctor`)
    REFERENCES `nma`.`physician` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`consultation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`consultation` ;

CREATE TABLE IF NOT EXISTS `nma`.`consultation` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL,
  `doctor` INT(11) NOT NULL,
  `patient` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_physician_consultation_doctor_idx` (`doctor` ASC),
  INDEX `fk_patient_consultation_patient_idx` (`patient` ASC),
  CONSTRAINT `fk_patient_consultation_patient`
    FOREIGN KEY (`patient`)
    REFERENCES `nma`.`patient` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_physician_consultation_doctor`
    FOREIGN KEY (`doctor`)
    REFERENCES `nma`.`physician` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`illness`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`illness` ;

CREATE TABLE IF NOT EXISTS `nma`.`illness` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(128) NOT NULL,
  `name` VARCHAR(512) NOT NULL,
  `description` VARCHAR(2048) NULL DEFAULT NULL,
  PRIMARY KEY (`seq`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`in_patient_room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`in_patient_room` ;

CREATE TABLE IF NOT EXISTS `nma`.`in_patient_room` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `room_num` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  UNIQUE INDEX `number_UNIQUE` (`room_num` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`in_patient_bed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`in_patient_bed` ;

CREATE TABLE IF NOT EXISTS `nma`.`in_patient_bed` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `bed_id` CHAR(1) NOT NULL,
  `room` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_room_bed_room_idx` (`room` ASC),
  CONSTRAINT `fk_room_bed_room`
    FOREIGN KEY (`room`)
    REFERENCES `nma`.`in_patient_room` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`surgery_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`surgery_type` ;

CREATE TABLE IF NOT EXISTS `nma`.`surgery_type` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(128) NOT NULL,
  `category` VARCHAR(5) NOT NULL,
  `special_needs` VARCHAR(2048) NULL DEFAULT NULL,
  `anatomical_location` VARCHAR(2048) NULL DEFAULT NULL,
  PRIMARY KEY (`seq`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`nurse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`nurse` ;

CREATE TABLE IF NOT EXISTS `nma`.`nurse` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `annual_salary` DECIMAL(10,2) NULL DEFAULT NULL,
  `grade` INT(11) NOT NULL,
  `year` INT(11) NOT NULL,
  `employee` INT(11) NOT NULL,
  `surgery_type` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_nurse_employee_emp_idx` (`employee` ASC),
  INDEX `fk_nurse_surgery_type_type_idx` (`surgery_type` ASC),
  CONSTRAINT `fk_nurse_employee_emp`
    FOREIGN KEY (`employee`)
    REFERENCES `nma`.`employee` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_nurse_surgery_type_type`
    FOREIGN KEY (`surgery_type`)
    REFERENCES `nma`.`surgery_type` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`in_patient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`in_patient` ;

CREATE TABLE IF NOT EXISTS `nma`.`in_patient` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `nursing_unit` VARCHAR(45) NOT NULL,
  `bed` INT(11) NULL DEFAULT NULL,
  `patient` INT(11) NOT NULL,
  `nurse` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_nurse_in_patient_nurse_idx` (`nurse` ASC),
  INDEX `fk_inpatient_bed_in_patient_bed_idx` (`bed` ASC),
  INDEX `fk_patient_in_patient_patient_idx` (`patient` ASC),
  CONSTRAINT `fk_inpatient_bed_in_patient_bed`
    FOREIGN KEY (`bed`)
    REFERENCES `nma`.`in_patient_bed` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_nurse_in_patient_nurse`
    FOREIGN KEY (`nurse`)
    REFERENCES `nma`.`nurse` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_in_patient_patient`
    FOREIGN KEY (`patient`)
    REFERENCES `nma`.`patient` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`medicine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`medicine` ;

CREATE TABLE IF NOT EXISTS `nma`.`medicine` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(128) NOT NULL,
  `name` VARCHAR(512) NOT NULL,
  `year` INT(11) NOT NULL,
  `qty_on_hand` INT(11) NOT NULL,
  `qty_on_order` INT(11) NOT NULL,
  `unit_cost` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`seq`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`interaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`interaction` ;

CREATE TABLE IF NOT EXISTS `nma`.`interaction` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `medicine1` INT(11) NOT NULL,
  `medicine2` INT(11) NOT NULL,
  `severity` TINYINT(4) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_medicine_interaction_med1_idx` (`medicine1` ASC),
  INDEX `fk_medicine_interaction_med2_idx` (`medicine2` ASC),
  CONSTRAINT `fk_medicine_interaction_med1`
    FOREIGN KEY (`medicine1`)
    REFERENCES `nma`.`medicine` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_medicine_interaction_med2`
    FOREIGN KEY (`medicine2`)
    REFERENCES `nma`.`medicine` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`medicine_record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`medicine_record` ;

CREATE TABLE IF NOT EXISTS `nma`.`medicine_record` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `doctor` INT(11) NOT NULL,
  `patient` INT(11) NOT NULL,
  `medicine` INT(11) NOT NULL,
  `dosage` DECIMAL(10,1) NOT NULL,
  `frequency` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_physician_record_doctor_idx` (`doctor` ASC),
  INDEX `fk_patient_record_patient_idx` (`patient` ASC),
  INDEX `fk_medicine_record_medicine_idx` (`medicine` ASC),
  CONSTRAINT `fk_medicine_record_medicine`
    FOREIGN KEY (`medicine`)
    REFERENCES `nma`.`medicine` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_record_patient`
    FOREIGN KEY (`patient`)
    REFERENCES `nma`.`patient` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_physician_record_doctor`
    FOREIGN KEY (`doctor`)
    REFERENCES `nma`.`physician` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`surgery_skill`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`surgery_skill` ;

CREATE TABLE IF NOT EXISTS `nma`.`surgery_skill` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(128) NOT NULL,
  `description` VARCHAR(2048) NULL DEFAULT NULL,
  PRIMARY KEY (`seq`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`nurse_skill`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`nurse_skill` ;

CREATE TABLE IF NOT EXISTS `nma`.`nurse_skill` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `nurse` INT(11) NOT NULL,
  `skill` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_nurse_nurse_skill_nurse_idx` (`nurse` ASC),
  INDEX `fk_surgery_skill_nurse_skill_skill_idx` (`skill` ASC),
  CONSTRAINT `fk_nurse_nurse_skill_nurse`
    FOREIGN KEY (`nurse`)
    REFERENCES `nma`.`nurse` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_surgery_skill_nurse_skill_skill`
    FOREIGN KEY (`skill`)
    REFERENCES `nma`.`surgery_skill` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`patient_allergy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`patient_allergy` ;

CREATE TABLE IF NOT EXISTS `nma`.`patient_allergy` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `patient` INT(11) NOT NULL,
  `doctor` INT(11) NULL DEFAULT NULL,
  `allergy` INT(11) NOT NULL,
  `date_diagnosis` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_patient_patient_allergy_patient_idx` (`patient` ASC),
  INDEX `fk_allergy_patient_allergy_allergy_idx` (`allergy` ASC),
  INDEX `fk_physician_allergy_doctor_idx` (`doctor` ASC),
  CONSTRAINT `fk_allergy_patient_allergy_allergy`
    FOREIGN KEY (`allergy`)
    REFERENCES `nma`.`allergy` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_patient_allergy_patient`
    FOREIGN KEY (`patient`)
    REFERENCES `nma`.`patient` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_physician_allergy_doctor`
    FOREIGN KEY (`doctor`)
    REFERENCES `nma`.`physician` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`patient_illness`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`patient_illness` ;

CREATE TABLE IF NOT EXISTS `nma`.`patient_illness` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `patient` INT(11) NOT NULL,
  `doctor` INT(11) NULL DEFAULT NULL,
  `illness` INT(11) NOT NULL,
  `date_diagnosis` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_patient_patient_illness_patient_idx` (`patient` ASC),
  INDEX `fk_illness_patient_illness_illness_idx` (`illness` ASC),
  INDEX `fk_physician_illness_doctor_idx` (`doctor` ASC),
  CONSTRAINT `fk_illness_patient_illness_illness`
    FOREIGN KEY (`illness`)
    REFERENCES `nma`.`illness` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_patient_illness_patient`
    FOREIGN KEY (`patient`)
    REFERENCES `nma`.`patient` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_physician_illness_doctor`
    FOREIGN KEY (`doctor`)
    REFERENCES `nma`.`physician` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`support_staff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`support_staff` ;

CREATE TABLE IF NOT EXISTS `nma`.`support_staff` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `annual_salary` DECIMAL(10,2) NULL DEFAULT NULL,
  `employee` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_employee_support_staff_emp_idx` (`employee` ASC),
  CONSTRAINT `fk_employee_support_staff_emp`
    FOREIGN KEY (`employee`)
    REFERENCES `nma`.`employee` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`surgeon`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`surgeon` ;

CREATE TABLE IF NOT EXISTS `nma`.`surgeon` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `specialty` VARCHAR(1024) NOT NULL,
  `contract_type` VARCHAR(5) NOT NULL,
  `contract_year` INT(11) NOT NULL,
  `employee` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_surgeon_employee_emp_idx` (`employee` ASC),
  CONSTRAINT `fk_surgeon_employee_emp`
    FOREIGN KEY (`employee`)
    REFERENCES `nma`.`employee` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`surgery_room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`surgery_room` ;

CREATE TABLE IF NOT EXISTS `nma`.`surgery_room` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `room_num` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  UNIQUE INDEX `room_num_UNIQUE` (`room_num` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`surgery_schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`surgery_schedule` ;

CREATE TABLE IF NOT EXISTS `nma`.`surgery_schedule` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `start_datetime` DATETIME NOT NULL,
  `end_datetime` DATETIME NOT NULL,
  `room` INT(11) NOT NULL,
  `surgeon` INT(11) NOT NULL,
  `patient` INT(11) NOT NULL,
  `type` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_surgeon_schedule_surgeon_idx` (`surgeon` ASC),
  INDEX `fk_patient_schedule_patient_idx` (`patient` ASC),
  INDEX `fk_type_schedule_type_idx` (`type` ASC),
  INDEX `fk_surgery_room_schedule_room_idx` (`room` ASC),
  CONSTRAINT `fk_patient_schedule_patient`
    FOREIGN KEY (`patient`)
    REFERENCES `nma`.`patient` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_surgeon_schedule_surgeon`
    FOREIGN KEY (`surgeon`)
    REFERENCES `nma`.`surgeon` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_surgery_room_schedule_room`
    FOREIGN KEY (`room`)
    REFERENCES `nma`.`surgery_room` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_type_schedule_type`
    FOREIGN KEY (`type`)
    REFERENCES `nma`.`surgery_type` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nma`.`surgery_type_skill`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nma`.`surgery_type_skill` ;

CREATE TABLE IF NOT EXISTS `nma`.`surgery_type_skill` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT,
  `surgery_type` INT(11) NOT NULL,
  `surgery_skill` INT(11) NOT NULL,
  PRIMARY KEY (`seq`),
  INDEX `fk_surgery_skill_type_skill_skill_idx` (`surgery_skill` ASC),
  INDEX `fk_surgery_type_type_skill_type_idx` (`surgery_type` ASC),
  CONSTRAINT `fk_surgery_skill_type_skill_skill`
    FOREIGN KEY (`surgery_skill`)
    REFERENCES `nma`.`surgery_skill` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_surgery_type_type_skill_type`
    FOREIGN KEY (`surgery_type`)
    REFERENCES `nma`.`surgery_type` (`seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

