-- Create Database
CREATE DATABASE IF NOT EXISTS bank_branch_db;
USE bank_branch_db;

-- 1. Branches Table
CREATE TABLE branches (
    branch_id INT AUTO_INCREMENT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    branch_location VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- 2. Customers Table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL,
    kyc_status VARCHAR(20) DEFAULT 'Pending'
) ENGINE=InnoDB;

-- 3. Relationship Managers Table
CREATE TABLE relationship_managers (
    manager_id INT AUTO_INCREMENT PRIMARY KEY,
    manager_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
) ENGINE=InnoDB;

-- 4. Accounts Table (3NF Schema with Constraints)
CREATE TABLE accounts (
    account_number VARCHAR(50) PRIMARY KEY,
    customer_id INT NOT NULL,
    branch_id INT NOT NULL,
    manager_id INT NOT NULL,
    account_type VARCHAR(30) NOT NULL CHECK (account_type IN ('Savings', 'Current', 'Salary', 'Fixed Deposit')),
    balance DECIMAL(15, 2) NOT NULL CHECK (balance >= 0),
    opened_date DATE NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) 
        REFERENCES customers(customer_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_branch FOREIGN KEY (branch_id) 
        REFERENCES branches(branch_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_manager FOREIGN KEY (manager_id) 
        REFERENCES relationship_managers(manager_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
) ENGINE=InnoDB;
