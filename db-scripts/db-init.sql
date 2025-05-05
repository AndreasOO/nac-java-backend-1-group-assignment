DROP SCHEMA IF EXISTS `backend_db`;
CREATE SCHEMA IF NOT EXISTS `backend_db`;
USE `backend_db`;

DROP USER IF EXISTS 'backend_app'@'localhost';
DROP ROLE IF EXISTS  'backend_app';
CREATE ROLE 'backend_app';
GRANT ALL ON `backend_db`.* TO 'backend_app';
CREATE USER 'backend_app'@'localhost' IDENTIFIED BY 'test1234';
GRANT 'backend_app' TO 'backend_app'@'localhost';
SET DEFAULT ROLE ALL TO 'backend_app'@'localhost';