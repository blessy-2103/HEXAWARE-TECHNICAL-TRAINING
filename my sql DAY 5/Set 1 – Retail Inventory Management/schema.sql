-- Create Database
CREATE DATABASE IF NOT EXISTS retail_inventory_db;
USE retail_inventory_db;

-- 1. Suppliers Table (Normalized from original sheet to avoid supplier redundancy)
CREATE TABLE suppliers (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL,
    supplier_phone VARCHAR(15) UNIQUE NOT NULL
) ENGINE=InnoDB;

-- 2. Warehouses Table
CREATE TABLE warehouses (
    warehouse_id INT AUTO_INCREMENT PRIMARY KEY,
    warehouse_location VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- 3. Products Table (3NF Schema with Foreign Keys and Constraints)
CREATE TABLE products (
    sku VARCHAR(50) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    supplier_id INT NOT NULL,
    warehouse_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    reorder_level INT DEFAULT 10,
    CONSTRAINT fk_supplier FOREIGN KEY (supplier_id) 
        REFERENCES suppliers(supplier_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_warehouse FOREIGN KEY (warehouse_id) 
        REFERENCES warehouses(warehouse_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
) ENGINE=InnoDB;
