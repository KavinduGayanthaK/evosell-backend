CREATE DATABASE evosell;
USE evosell;

CREATE TABLE customer(
    id VARCHAR(10) PRIMARY KEY ,
    name VARCHAR(50) NOT NULL ,
    address VARCHAR(100) NOT NULL ,
    email VARCHAR(50),
    nic VARCHAR(20) NOT NULL
);