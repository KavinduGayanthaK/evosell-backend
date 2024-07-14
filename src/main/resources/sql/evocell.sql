CREATE DATABASE evosell;
USE evosell;

CREATE TABLE customer(
    id VARCHAR(10) PRIMARY KEY ,
    name VARCHAR(50) NOT NULL ,
    address VARCHAR(100) NOT NULL ,
    email VARCHAR(50),
    nic VARCHAR(20) NOT NULL
);

CREATE TABLE orders(
    id VARCHAR(10) PRIMARY KEY ,
    customerId VARCHAR(10) NOT NULL,
    customerName VARCHAR(50) NOT NULL,
    FOREIGN KEY (customerId) REFERENCES customer(id)
);

CREATE TABLE item(
    itemCode VARCHAR(10) PRIMARY KEY ,
    name VARCHAR(50) NOT NULL ,
    quantityOnHand INT NOT NULL ,
    price DOUBLE(5,2) NOT NULL
);

CREATE TABLE orderDetails(
    orderId VARCHAR(10) NOT NULL ,
    itemCode varchar(10) NOT NULL ,
    itemName VARCHAR(50) NOT NULL ,
    qty INT NOT NULL ,
    total DOUBLE(8,2) NOT NULL ,
    discount DOUBLE(5,2) NOT NULL ,
    netTotal DOUBLE(8,2) NOT NULL ,
    date DATE,
    FOREIGN KEY(orderId) REFERENCES orders(id),
    FOREIGN KEY(itemCode) REFERENCES item(itemCode)
)