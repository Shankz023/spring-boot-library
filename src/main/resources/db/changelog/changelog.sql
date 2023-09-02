--liquibase formatted sql

--changeset shankz:1
CREATE TABLE example_table(id INT PRIMARY KEY,name VARCHAR(255));
--rollback DROP TABLE example_table;