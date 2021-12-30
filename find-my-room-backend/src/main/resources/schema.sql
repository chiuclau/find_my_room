-- create user for servlets to use
DROP USER IF EXISTS 'servlet'@'%';
CREATE USER 'servlet'@'%' IDENTIFIED BY 'FMRServlet!8080';
GRANT ALL PRIVILEGES ON findmyroom.* TO 'servlet'@'%';
FLUSH PRIVILEGES;

-- create database schema
DROP DATABASE IF EXISTS findmyroom;
CREATE DATABASE findmyroom;
USE findmyroom;

-- create tables
CREATE TABLE users (
    userID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(63),
    about VARCHAR(1000),
    phone VARCHAR(12)
);

CREATE TABLE subleases (
    subleaseID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    authorID INT NOT NULL,
    address VARCHAR(255),
    direction VARCHAR(15),
    sqFootage INT,
    price INT,
    numBeds INT,
    dateAvailability DATETIME,
    datePosted DATETIME,
    FOREIGN KEY fk1(authorID) REFERENCES users(userID)
);

CREATE TABLE images (
    imageID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    subleaseID INT NOT NULL,
    image LONGBLOB NOT NULL,
    FOREIGN KEY fk1(subleaseID) REFERENCES subleases(subleaseID)
);

CREATE TABLE favorites (
    userID INT NOT NULL,
    subleaseID INT NOT NULL,
    FOREIGN KEY fk1(userID) REFERENCES users(userID),
    FOREIGN KEY fk2(subleaseID) REFERENCES subleases(subleaseID)
);
