create database Bank;
use Bank;

create table customer(
ac_no int not null auto_increment,
char_name varchar(45) unique not null,
balance int default 1000,
pass_code int not null,
primary key(ac_no)
);
USE bank;
SELECT * FROM customer;