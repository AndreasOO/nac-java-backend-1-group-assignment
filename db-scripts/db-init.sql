DROP SCHEMA IF EXISTS `backend_db`;
CREATE SCHEMA IF NOT EXISTS `backend_db`;
USE `backend_db`;

DROP USER IF EXISTS 'backend_app'@'%';
DROP ROLE IF EXISTS  'backend_app_role';
CREATE ROLE 'backend_app_role';
GRANT ALL ON `backend_db`.* TO 'backend_app_role';
CREATE USER 'backend_app'@'%' IDENTIFIED BY 'test1234';
GRANT 'backend_app_role' TO 'backend_app'@'%';
SET DEFAULT ROLE ALL TO 'backend_app'@'%';