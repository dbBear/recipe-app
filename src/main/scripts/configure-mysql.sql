-- Use to run mysql db docker image, optional if you're not using a local mysqldb
-- docker run --name db-mysql -v /home/drumblum/dockerdata/mysql/:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password -p 3307:3306 -d mysql

-- connect to mysql and run as root user
-- Create Databases
CREATE DATABASE ambear_dev;
CREATE DATABASE ambear_prod;

-- Create database service accounts
CREATE USER 'ambear_dev_user'@'localhost' IDENTIFIED BY 'devPassword';
CREATE USER 'ambear_prod_user'@'localhost' IDENTIFIED BY 'prodPassword';
-- % means user from any host
CREATE USER 'ambear_dev_user'@'%' IDENTIFIED BY 'devPassword';
CREATE USER 'ambear_prod_user'@'%' IDENTIFIED BY 'prodPassword';

-- Database grants
GRANT INSERT ON  ambear_dev.* to 'ambear_dev_user'@'localhost';
GRANT SELECT ON  ambear_dev.* to 'ambear_dev_user'@'localhost';
GRANT UPDATE ON  ambear_dev.* to 'ambear_dev_user'@'localhost';
GRANT DELETE ON  ambear_dev.* to 'ambear_dev_user'@'localhost';

GRANT INSERT ON  ambear_prod.* to 'ambear_prod_user'@'localhost';
GRANT SELECT ON  ambear_prod.* to 'ambear_prod_user'@'localhost';
GRANT UPDATE ON  ambear_prod.* to 'ambear_prod_user'@'localhost';
GRANT DELETE ON  ambear_prod.* to 'ambear_prod_user'@'localhost';

GRANT INSERT ON  ambear_dev.* to 'ambear_dev_user'@'%';
GRANT SELECT ON  ambear_dev.* to 'ambear_dev_user'@'%';
GRANT UPDATE ON  ambear_dev.* to 'ambear_dev_user'@'%';
GRANT DELETE ON  ambear_dev.* to 'ambear_dev_user'@'%';

GRANT INSERT ON  ambear_prod.* to 'ambear_prod_user'@'%';
GRANT SELECT ON  ambear_prod.* to 'ambear_prod_user'@'%';
GRANT UPDATE ON  ambear_prod.* to 'ambear_prod_user'@'%';
GRANT DELETE ON  ambear_prod.* to 'ambear_prod_user'@'%';
