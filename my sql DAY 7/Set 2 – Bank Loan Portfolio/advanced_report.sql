

CREATE DATABASE IF NOT EXISTS bank_loan_db;
USE bank_loan_db;

-- 1. Branches Table
CREATE TABLE branches (
    branch_id INT AUTO_INCREMENT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 2. Customers Table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL
) ENGINE=InnoDB;

-- 3. Loans Table
CREATE TABLE loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    branch_id INT NOT NULL,
    loan_amount DECIMAL(15,2) NOT NULL,
    total_instalments INT NOT NULL,
    instalments_overdue INT DEFAULT 0,
    guarantor_id INT NULL,
    disbursement_date DATE NOT NULL,
    CONSTRAINT fk_loan_cust FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_loan_branch FOREIGN KEY (branch_id) REFERENCES branches(branch_id),
    CONSTRAINT fk_loan_guar FOREIGN KEY (guarantor_id) REFERENCES customers(customer_id)
) ENGINE=InnoDB;

-- 4. Repayments Table
CREATE TABLE repayments (
    repayment_id INT AUTO_INCREMENT PRIMARY KEY,
    loan_id INT NOT NULL,
    repayment_date DATE NOT NULL,
    amount_paid DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_rep_loan FOREIGN KEY (loan_id) REFERENCES loans(loan_id)
) ENGINE=InnoDB;

-- Seed Data Insertion
INSERT INTO branches (branch_name, city) VALUES 
('MG Road Branch', 'Bangalore'), 
('Connaught Place', 'New Delhi'), 
('Bandra West', 'Mumbai'), 
('Park Street', 'Kolkata');

INSERT INTO customers (customer_name, address, phone) VALUES 
('Amit Sharma', '12 Park Street, Bangalore', '9876543210'),
('Priya Nair', '45 MG Road, New Delhi', '9123456780'),
('Rohan Verma', '78 Linking Road, Mumbai', '9988776655'),
('Neha Gupta', '12 Sector 18, Noida', '9811223344'),
('Vikram Singh', '56 Civil Lines, Jaipur', '9722334455');

INSERT INTO loans (customer_id, branch_id, loan_amount, total_instalments, instalments_overdue, guarantor_id, disbursement_date) VALUES 
(1, 1, 500000.00, 36, 4, 2, '2025-01-10'), -- Overdue > 3 instalments
(2, 2, 1200000.00, 60, 1, 3, '2025-03-15'),
(3, 3, 300000.00, 24, 5, 1, '2025-06-01'), -- Overdue > 3 instalments, guarantor is customer 1
(4, 2, 850000.00, 48, 0, 5, '2025-08-20'),
(5, 1, 600000.00, 36, 2, NULL, '2025-09-10');

-- Add a loan for customer 1 in another branch to test multi-branch loans
INSERT INTO loans (customer_id, branch_id, loan_amount, total_instalments, instalments_overdue, guarantor_id, disbursement_date) VALUES 
(1, 2, 400000.00, 24, 0, NULL, '2025-10-05');

INSERT INTO repayments (loan_id, repayment_date, amount_paid) VALUES 
(1, '2026-01-10', 15000.00),
(2, '2026-02-15', 25000.00),
(4, '2026-02-20', 20000.00);


-- ==========================================
-- TASK 1: TEN COMPLEX QUESTIONS (JOINS & SUBQUERIES)
-- ==========================================

-- 1. Loans overdue by more than three instalments with customer and branch details
SELECT l.loan_id, c.customer_name, b.branch_name, l.instalments_overdue
FROM loans l
JOIN customers c ON l.customer_id = c.customer_id
JOIN branches b ON l.branch_id = b.branch_id
WHERE l.instalments_overdue > 3;

-- 2. Customers with loans in more than one branch
SELECT c.customer_id, c.customer_name, COUNT(DISTINCT l.branch_id) AS branch_count
FROM customers c
JOIN loans l ON c.customer_id = l.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING COUNT(DISTINCT l.branch_id) > 1;

-- 3. Guarantors who are also borrowers (Self Join / Inner Join)
SELECT DISTINCT 
    c_guar.customer_id AS borrower_id, 
    c_guar.customer_name AS person_name
FROM loans l
JOIN customers c_guar ON l.guarantor_id = c_guar.customer_id
WHERE l.guarantor_id IN (SELECT customer_id FROM loans);

-- 4. Branches with no loans (LEFT JOIN)
SELECT b.branch_id, b.branch_name, b.city
FROM branches b
LEFT JOIN loans l ON b.branch_id = l.branch_id
WHERE l.loan_id IS NULL;

-- 5. Customers with their loan details and guarantor names (Self Join on customers)
SELECT l.loan_id, c.customer_name AS primary_borrower, 
       COALESCE(g.customer_name, 'No Guarantor') AS guarantor_name, l.loan_amount
FROM loans l
JOIN customers c ON l.customer_id = c.customer_id
LEFT JOIN customers g ON l.guarantor_id = g.customer_id;

-- 6. Total loan exposure per branch
SELECT b.branch_name, SUM(l.loan_amount) AS total_branch_exposure
FROM branches b
JOIN loans l ON b.branch_id = l.branch_id
GROUP BY b.branch_id, b.branch_name;

-- 7. Loans associated with Park Street branch (using JOIN)
SELECT l.loan_id, c.customer_name, l.loan_amount
FROM loans l
JOIN branches b ON l.branch_id = b.branch_id
WHERE b.branch_name = 'Park Street';

-- 8. Customers with maximum loan amount in the bank
SELECT c.customer_name, l.loan_amount
FROM customers c
JOIN loans l ON c.customer_id = l.customer_id
WHERE l.loan_amount = (SELECT MAX(loan_amount) FROM loans);

-- 9. Repayments made against loans from 'MG Road Branch'
SELECT r.repayment_id, c.customer_name, r.amount_paid, r.repayment_date
FROM repayments r
JOIN loans l ON r.loan_id = l.loan_id
JOIN branches b ON l.branch_id = b.branch_id
WHERE b.branch_name = 'MG Road Branch';

-- 10. Active loans with zero overdue instalments
SELECT l.loan_id, c.customer_name, l.loan_amount
FROM loans l
JOIN customers c ON l.customer_id = c.customer_id
WHERE l.instalments_overdue = 0;


-- ==========================================
-- TASK 2: CORRELATED & UNCORRELATED SUBQUERIES
-- ==========================================
SELECT c.customer_name, SUM(l.loan_amount) AS customer_total_loan
FROM customers c
JOIN loans l ON c.customer_id = l.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING SUM(l.loan_amount) > (
    SELECT AVG(branch_total) FROM (
        SELECT SUM(loan_amount) AS branch_total 
        FROM loans 
        GROUP BY branch_id
    ) AS branch_avgs
);


-- ==========================================
-- TASK 3: EXISTS AND NOT EXISTS
-- ==========================================
SELECT c.customer_name
FROM customers c
WHERE EXISTS (
    SELECT 1 FROM loans l WHERE l.customer_id = c.customer_id
)
AND NOT EXISTS (
    SELECT 1 FROM loans l 
    JOIN repayments r ON l.loan_id = r.loan_id 
    WHERE l.customer_id = c.customer_id 
      AND r.repayment_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 60 DAY)
);


-- ==========================================
-- TASK 4: NESTED SUBQUERY
-- ==========================================
SELECT b.branch_name, MAX(l.loan_amount) AS max_branch_loan
FROM branches b
JOIN loans l ON b.branch_id = l.branch_id
GROUP BY b.branch_id, b.branch_name
HAVING MAX(l.loan_amount) > (
    SELECT AVG(max_amt) FROM (
        SELECT MAX(loan_amount) AS max_amt 
        FROM loans 
        GROUP BY branch_id
    ) AS branch_maxes
);
