

CREATE DATABASE IF NOT EXISTS retail_returns_db;
USE retail_returns_db;

-- 1. Stores Table
CREATE TABLE stores (
    store_id INT AUTO_INCREMENT PRIMARY KEY,
    store_name VARCHAR(100) NOT NULL,
    region VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 2. Employees (Self-Join for Manager hierarchy)
CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(100) NOT NULL,
    store_id INT NOT NULL,
    manager_id INT NULL,
    CONSTRAINT fk_emp_store FOREIGN KEY (store_id) REFERENCES stores(store_id),
    CONSTRAINT fk_emp_mgr FOREIGN KEY (manager_id) REFERENCES employees(employee_id)
) ENGINE=InnoDB;

-- 3. Customers Table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 4. Products Table
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB;

-- 5. Returns Table
CREATE TABLE returns_log (
    return_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    store_id INT NOT NULL,
    return_date DATE NOT NULL,
    return_reason VARCHAR(100) NOT NULL,
    CONSTRAINT fk_ret_cust FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_ret_prod FOREIGN KEY (product_id) REFERENCES products(product_id),
    CONSTRAINT fk_ret_store FOREIGN KEY (store_id) REFERENCES stores(store_id)
) ENGINE=InnoDB;

-- 6. Refunds Table
CREATE TABLE refunds (
    refund_id INT AUTO_INCREMENT PRIMARY KEY,
    return_id INT NOT NULL UNIQUE,
    refund_amount DECIMAL(10,2) NOT NULL,
    refund_status VARCHAR(20) DEFAULT 'Processed',
    CONSTRAINT fk_ref_ret FOREIGN KEY (return_id) REFERENCES returns_log(return_id)
) ENGINE=InnoDB;

-- Seed Data Insertion
INSERT INTO stores (store_name, region) VALUES 
('Central Mall Store', 'North'),
('High Street Outlet', 'North'),
('Metro Plaza', 'South'),
('Grand Avenue', 'West');

INSERT INTO employees (employee_name, store_id, manager_id) VALUES 
('Aarav Mehta', 1, NULL),
('Priya Sharma', 1, 1),
('Rohan Rao', 2, NULL),
('Neha Gupta', 3, NULL);

INSERT INTO customers (customer_name, city) VALUES 
('Amit Verma', 'Delhi'),
('Sunita Patel', 'Bangalore'),
('Kiran Rao', 'Mumbai'),
('Vikram Singh', 'Delhi');

INSERT INTO products (product_name, category, unit_price) VALUES 
('Smart TV', 'Electronics', 45000.00),
('Running Shoes', 'Footwear', 3500.00),
('Blender', 'Appliances', 2500.00);

INSERT INTO returns_log (customer_id, product_id, store_id, return_date, return_reason) VALUES 
(1, 1, 1, '2026-02-01', 'Defective screen'),
(2, 2, 2, '2026-02-05', 'Size issue'),
(3, 3, 3, '2026-02-10', 'Not working'),
(4, 2, 1, '2026-02-12', 'Wrong item delivered');

INSERT INTO refunds (return_id, refund_amount, refund_status) VALUES 
(1, 45000.00, 'Processed'),
(2, 3500.00, 'Processed'),
(4, 3500.00, 'Pending');


-- ==========================================
-- TASK 1: TEN COMPLEX QUERIES
-- ==========================================

-- 1. Products with a return rate (return counts vs products)
SELECT p.product_name, COUNT(r.return_id) AS return_count
FROM products p
JOIN returns_log r ON p.product_id = r.product_id
GROUP BY p.product_id, p.product_name;

-- 2. Customers with returns but no refund
SELECT DISTINCT c.customer_name, r.return_id
FROM customers c
JOIN returns_log r ON c.customer_id = r.customer_id
LEFT JOIN refunds ref ON r.return_id = ref.return_id
WHERE ref.refund_id IS NULL;

-- 3. Stores with the highest refund value
SELECT s.store_name, SUM(ref.refund_amount) AS total_refund_value
FROM stores s
JOIN returns_log r ON s.store_id = r.store_id
JOIN refunds ref ON r.return_id = ref.return_id
GROUP BY s.store_id, s.store_name
ORDER BY total_refund_value DESC
LIMIT 1;

-- 4. Employees and their managers (Self Join)
SELECT e.employee_name AS employee, COALESCE(m.employee_name, 'Store Head') AS manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.employee_id;

-- 5. Total returns handled per store
SELECT s.store_name, COUNT(r.return_id) AS total_returns
FROM stores s
LEFT JOIN returns_log r ON s.store_id = r.store_id
GROUP BY s.store_id, s.store_name;

-- 6. Refunds with status 'Processed' and amount above 3000
SELECT ref.refund_id, r.return_reason, ref.refund_amount
FROM refunds ref
JOIN returns_log r ON ref.return_id = r.return_id
WHERE ref.refund_status = 'Processed' AND ref.refund_amount > 3000;

-- 7. Customers who returned items from 'Central Mall Store'
SELECT DISTINCT c.customer_name
FROM customers c
JOIN returns_log r ON c.customer_id = r.customer_id
JOIN stores s ON r.store_id = s.store_id
WHERE s.store_name = 'Central Mall Store';

-- 8. Average refund amount per category
SELECT p.category, ROUND(AVG(ref.refund_amount), 2) AS avg_refund
FROM products p
JOIN returns_log r ON p.product_id = r.product_id
JOIN refunds ref ON r.return_id = ref.return_id
GROUP BY p.category;

-- 9. Stores with zero returns (LEFT JOIN + IS NULL)
SELECT s.store_id, s.store_name
FROM stores s
LEFT JOIN returns_log r ON s.store_id = r.store_id
WHERE r.return_id IS NULL;

-- 10. Top-3 most frequently returned products
SELECT p.product_name, COUNT(r.return_id) AS return_frequency
FROM products p
JOIN returns_log r ON p.product_id = r.product_id
GROUP BY p.product_id, p.product_name
ORDER BY return_frequency DESC
LIMIT 3;


-- ==========================================
-- TASK 2: NULL PITFALL WITH NOT IN VS NOT EXISTS
-- ==========================================
SELECT s.store_id, s.store_name
FROM stores s
WHERE NOT EXISTS (
    SELECT 1 FROM returns_log r WHERE r.store_id = s.store_id
);


-- ==========================================
-- TASK 3: ANY AND ALL OPERATORS
-- ==========================================
SELECT s.store_name, s.region, SUM(ref.refund_amount) AS store_refunds
FROM stores s
JOIN returns_log r ON s.store_id = r.store_id
JOIN refunds ref ON r.return_id = ref.return_id
GROUP BY s.store_id, s.store_name, s.region
HAVING SUM(ref.refund_amount) > ALL (
    SELECT SUM(ref2.refund_amount)
    FROM stores s2
    JOIN returns_log r2 ON s2.store_id = r2.store_id
    JOIN refunds ref2 ON r2.return_id = ref2.return_id
    WHERE s2.region = s.region AND s2.store_id != s.store_id
);


-- ==========================================
-- TASK 4: NESTED SUBQUERIES
-- ==========================================
SELECT return_id, refund_amount
FROM refunds
WHERE refund_amount > (
    SELECT AVG(ref_inner.refund_amount)
    FROM refunds ref_inner
);

SELECT c.customer_name
FROM customers c
WHERE c.customer_id IN (
    SELECT r.customer_id
    FROM returns_log r
    JOIN products p ON r.product_id = p.product_id
    WHERE p.unit_price > (SELECT AVG(unit_price) FROM products)
);
