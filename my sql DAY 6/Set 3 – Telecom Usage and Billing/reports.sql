reports
-- =====================================================================
-- Day 6 Assignment: Set 3 – Telecom Usage and Billing (MySQL 8.4 LTS)
-- =====================================================================

CREATE DATABASE IF NOT EXISTS telecom_db;
USE telecom_db;

-- 1. Plans Table
CREATE TABLE plans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(100) NOT NULL,
    data_limit_gb INT NOT NULL,
    monthly_rental DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB;

-- 2. Subscribers Table
CREATE TABLE subscribers (
    subscriber_id INT AUTO_INCREMENT PRIMARY KEY,
    subscriber_name VARCHAR(100) NOT NULL,
    circle VARCHAR(50) NOT NULL,
    plan_id INT NOT NULL,
    CONSTRAINT fk_sub_plan FOREIGN KEY (plan_id) REFERENCES plans(plan_id)
) ENGINE=InnoDB;

-- 3. Call Records Table
CREATE TABLE call_records (
    call_id INT AUTO_INCREMENT PRIMARY KEY,
    subscriber_id INT NOT NULL,
    call_minutes INT NOT NULL,
    data_used_gb DECIMAL(6,2) NOT NULL,
    record_date DATE NOT NULL,
    CONSTRAINT fk_call_sub FOREIGN KEY (subscriber_id) REFERENCES subscribers(subscriber_id)
) ENGINE=InnoDB;

-- 4. Invoices Table
CREATE TABLE invoices (
    invoice_id INT AUTO_INCREMENT PRIMARY KEY,
    subscriber_id INT NOT NULL,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    amount_due DECIMAL(10,2) NOT NULL,
    due_date DATE NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'Unpaid',
    CONSTRAINT fk_inv_sub FOREIGN KEY (subscriber_id) REFERENCES subscribers(subscriber_id)
) ENGINE=InnoDB;

-- Seed Data Insertion
INSERT INTO plans (plan_name, data_limit_gb, monthly_rental) VALUES 
('Basic 30GB', 30, 299.00),
('Unlimited 150GB', 150, 699.00),
('Enterprise 500GB', 500, 1499.00);

INSERT INTO subscribers (subscriber_name, circle, plan_id) VALUES 
('Amit Sharma', 'Karnataka', 1),
('Priya Nair', 'Maharashtra', 2),
('Rohan Verma', 'Karnataka', 3),
('Neha Gupta', 'Delhi', 1),
('Vikram Singh', 'Maharashtra', 2);

INSERT INTO call_records (subscriber_id, call_minutes, data_used_gb, record_date) VALUES 
(1, 450, 25.50, '2026-02-10'),
(2, 1200, 140.00, '2026-02-12'),
(3, 300, 480.00, '2026-02-15'),
(4, 150, 32.00, '2026-02-18'),
(5, 800, 155.00, '2026-02-20');

INSERT INTO invoices (subscriber_id, invoice_number, amount_due, due_date, payment_status) VALUES 
(1, 'INV-2026-001', 350.00, '2026-02-25', 'Paid'),
(2, 'INV-2026-002', 750.00, '2026-02-10', 'Unpaid'),
(3, 'INV-2026-003', 1550.00, '2026-01-15', 'Unpaid'),
(4, 'INV-2026-004', 320.00, '2026-02-28', 'Paid'),
(5, 'INV-2026-005', 720.00, '2026-01-20', 'Unpaid');


-- ==========================================
-- TASK 1: EIGHT REPORTING QUERIES
-- ==========================================

-- 1. Total call minutes per plan
SELECT p.plan_name, SUM(c.call_minutes) AS total_call_minutes
FROM plans p
JOIN subscribers s ON p.plan_id = s.plan_id
JOIN call_records c ON s.subscriber_id = c.subscriber_id
GROUP BY p.plan_id, p.plan_name;

-- 2. Subscribers exceeding their data limit
SELECT s.subscriber_name, p.plan_name, p.data_limit_gb, c.data_used_gb
FROM subscribers s
JOIN plans p ON s.plan_id = p.plan_id
JOIN call_records c ON s.subscriber_id = c.subscriber_id
WHERE c.data_used_gb > p.data_limit_gb;

-- 3. Month-wise revenue
SELECT DATE_FORMAT(due_date, '%Y-%m') AS revenue_month, SUM(amount_due) AS total_revenue
FROM invoices
GROUP BY revenue_month
ORDER BY revenue_month;

-- 4. Invoices overdue by more than 15 days
SELECT invoice_number, subscriber_id, amount_due, due_date,
       DATEDIFF(CURRENT_DATE(), due_date) AS days_overdue
FROM invoices
WHERE payment_status = 'Unpaid' AND DATEDIFF(CURRENT_DATE(), due_date) > 15;

-- 5. Subscribers with no usage (LEFT JOIN)
SELECT s.subscriber_id, s.subscriber_name
FROM subscribers s
LEFT JOIN call_records c ON s.subscriber_id = c.subscriber_id
WHERE c.call_id IS NULL;

-- 6. ARPU (Average Revenue Per User) by circle
SELECT s.circle, ROUND(AVG(i.amount_due), 2) AS arpu
FROM subscribers s
JOIN invoices i ON s.subscriber_id = i.subscriber_id
GROUP BY s.circle;

-- 7. Plan popularity (subscriber count per plan)
SELECT p.plan_name, COUNT(s.subscriber_id) AS subscriber_count
FROM plans p
LEFT JOIN subscribers s ON p.plan_id = s.plan_id
GROUP BY p.plan_id, p.plan_name
ORDER BY subscriber_count DESC;

-- 8. Top-10 data consumers
SELECT s.subscriber_name, s.circle, c.data_used_gb
FROM subscribers s
JOIN call_records c ON s.subscriber_id = c.subscriber_id
ORDER BY c.data_used_gb DESC
LIMIT 10;


-- ==========================================
-- TASK 2 & 3: FUNCTIONS, NULL HANDLING & COALESCE
-- ==========================================
SELECT 
    UPPER(i.invoice_number) AS formatted_invoice,
    COALESCE(c.call_minutes, 0) AS safe_call_minutes,
    COALESCE(c.data_used_gb, 0.00) AS safe_data_used,
    CASE 
        WHEN i.due_date < CURRENT_DATE() AND i.payment_status = 'Unpaid' THEN 'Overdue'
        ELSE 'Current / Paid'
    END AS invoice_status
FROM invoices i
LEFT JOIN subscribers s ON i.subscriber_id = s.subscriber_id
LEFT JOIN call_records c ON s.subscriber_id = c.subscriber_id;


-- ==========================================
-- TASK 4: JOIN WITH GROUP BY AND AGGREGATE
-- ==========================================
SELECT 
    s.circle,
    p.plan_name,
    SUM(i.amount_due) AS total_revenue
FROM subscribers s
JOIN plans p ON s.plan_id = p.plan_id
JOIN invoices i ON s.subscriber_id = i.subscriber_id
GROUP BY s.circle, p.plan_name;
