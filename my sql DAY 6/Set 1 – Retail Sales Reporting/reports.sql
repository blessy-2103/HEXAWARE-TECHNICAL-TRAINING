-- Create database if not exists
CREATE DATABASE IF NOT EXISTS retail_sales_db;
USE retail_sales_db;

-- ==========================================
-- SETUP SAMPLE TABLES & DATA FOR QUERIES
-- ==========================================
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date DATE NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'Paid',
    CONSTRAINT fk_ord_cust FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
) ENGINE=InnoDB;

CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_item_ord FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_item_prod FOREIGN KEY (product_id) REFERENCES products(product_id)
) ENGINE=InnoDB;

-- Insert Seed Data
INSERT INTO customers (customer_name, city) VALUES 
('Amit Sharma', 'Bangalore'), ('Priya Nair', 'Mumbai'), 
('Rohan Verma', 'Bangalore'), ('Neha Gupta', 'Delhi'), ('Vikram Singh', 'Mumbai');

INSERT INTO products (product_name, category, price) VALUES 
('Laptop', 'Electronics', 55000.00), ('Smartphone', 'Electronics', 25000.00), 
('Running Shoes', 'Footwear', 3500.00), ('Backpack', 'Accessories', 1500.00);

INSERT INTO orders (customer_id, order_date, payment_status) VALUES 
(1, '2026-03-01', 'Paid'), (1, '2026-03-05', 'Paid'), 
(1, '2026-03-10', 'Paid'), (1, '2026-03-15', 'Pending'),
(2, '2026-03-02', 'Paid'), (2, '2026-03-08', 'Paid'), 
(2, '2026-03-12', 'Paid'), (2, '2026-03-20', 'Paid'),
(3, '2026-03-04', 'Pending'), (4, '2026-03-06', 'Paid');

INSERT INTO order_items (order_id, product_id, quantity) VALUES 
(1, 1, 1), (2, 2, 2), (3, 3, 1), (5, 1, 1), 
(6, 4, 3), (7, 2, 1), (8, 3, 2), (10, 4, 1);


-- ==========================================
-- TASK 1: EIGHT CORE REPORTING QUERIES
-- ==========================================

-- 1. Top-5 products by revenue
SELECT 
    p.product_name, 
    SUM(oi.quantity * p.price) AS total_revenue
FROM products p
JOIN order_items oi ON p.product_id = oi.product_id
GROUP BY p.product_id, p.product_name
ORDER BY total_revenue DESC
LIMIT 5;

-- 2. Month-wise sales
SELECT 
    DATE_FORMAT(o.order_date, '%Y-%m') AS sales_month,
    SUM(oi.quantity * p.price) AS monthly_sales
FROM orders o
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
GROUP BY sales_month
ORDER BY sales_month;

-- 3. Customers with more than three orders (using HAVING)
SELECT 
    c.customer_name, 
    COUNT(o.order_id) AS total_orders
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING COUNT(o.order_id) > 3;

-- 4. Average basket value per city
SELECT 
    c.city, 
    ROUND(AVG(oi.quantity * p.price), 2) AS avg_basket_value
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
GROUP BY c.city;

-- 5. Orders without payment (LEFT JOIN)
SELECT 
    o.order_id, 
    o.order_date, 
    o.payment_status
FROM orders o
LEFT JOIN order_items oi ON o.order_id = oi.order_id
WHERE o.payment_status = 'Pending';

-- 6. Customers with no orders (LEFT JOIN)
SELECT 
    c.customer_id, 
    c.customer_name
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
WHERE o.order_id IS NULL;

-- 7. Product-category revenue share (%)
SELECT 
    p.category,
    SUM(oi.quantity * p.price) AS category_revenue,
    ROUND((SUM(oi.quantity * p.price) * 100.0) / 
          (SELECT SUM(sub_oi.quantity * sub_p.price) 
           FROM order_items sub_oi 
           JOIN products sub_p ON sub_oi.product_id = sub_p.product_id), 2) AS revenue_share_percentage
FROM products p
JOIN order_items oi ON p.product_id = oi.product_id
GROUP BY p.category;

-- 8. Day with the highest sales
SELECT 
    o.order_date,
    SUM(oi.quantity * p.price) AS daily_sales
FROM orders o
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
GROUP BY o.order_date
ORDER BY daily_sales DESC
LIMIT 1;


-- ==========================================
-- TASK 2: STRING, DATE, & MATH FUNCTIONS
-- ==========================================
SELECT 
    UPPER(c.customer_name) AS customer_upper,
    CONCAT(c.customer_name, ' from ', c.city) AS customer_details,
    DATEDIFF(CURRENT_DATE(), o.order_date) as days_since_order,
    DATE_FORMAT(o.order_date, '%d-%b-%Y') AS formatted_date,
    ROUND(p.price * 1.18, 2) AS price_with_gst
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id;


-- ==========================================
-- TASK 3: NULL BEHAVIOR DEMONSTRATION
-- ==========================================
-- Demonstration using a table/column with optional fields (e.g., product discount/reorder level)
SELECT 
    COUNT(*) AS total_rows_counted,
    COUNT(product_id) AS non_null_product_ids,
    SUM(price) AS sum_of_prices,
    AVG(price) AS average_price
FROM products;
