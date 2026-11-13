CREATE DATABASE IF NOT EXISTS insurance_db;
USE insurance_db;

-- 1. Customers Table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL
) ENGINE=InnoDB;

-- 2. Agents Table
CREATE TABLE agents (
    agent_id INT AUTO_INCREMENT PRIMARY KEY,
    agent_name VARCHAR(100) NOT NULL,
    region VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 3. Policies Table
CREATE TABLE policies (
    policy_number VARCHAR(50) PRIMARY KEY,
    customer_id INT NOT NULL,
    agent_id INT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    premium_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'Active',
    start_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    CONSTRAINT fk_pol_cust FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_pol_agt FOREIGN KEY (agent_id) REFERENCES agents(agent_id)
) ENGINE=InnoDB;

-- 4. Claims Table
CREATE TABLE claims (
    claim_id INT AUTO_INCREMENT PRIMARY KEY,
    policy_number VARCHAR(50) NOT NULL,
    claim_date DATE NOT NULL,
    claim_amount DECIMAL(10,2) NOT NULL,
    claim_status VARCHAR(20) DEFAULT 'Pending',
    CONSTRAINT fk_claim_pol FOREIGN KEY (policy_number) REFERENCES policies(policy_number)
) ENGINE=InnoDB;

-- Seed Data Insertion
INSERT INTO customers (customer_name, dob, phone) VALUES 
('Rajesh Kumar', '1985-06-15', '9876543210'),
('Sunita Sharma', '1990-11-20', '9123456780'),
('Amit Patel', '1978-03-10', '9988776655'),
('Priya Nair', '1995-07-05', '9811223344');

INSERT INTO agents (agent_name, region) VALUES 
('Vikram Singh', 'North'),
('Ananya Roy', 'South'),
('Rohan Verma', 'West');

INSERT INTO policies (policy_number, customer_id, agent_id, product_name, premium_amount, status, start_date, expiry_date) VALUES 
('POL-1001', 1, 1, 'Life Shield', 25000.00, 'Active', '2025-04-01', '2026-04-01'),
('POL-1002', 2, 2, 'Health Care Plus', 15000.00, 'Active', '2025-05-10', '2026-06-15'),
('POL-1003', 3, 1, 'Auto Secure', 12000.00, 'Active', '2025-06-01', '2026-04-10'),
('POL-1004', 1, 3, 'Life Shield', 30000.00, 'Active', '2024-01-15', '2027-01-15'),
('POL-1005', 4, 2, 'Health Care Plus', 18000.00, 'Active', '2025-03-20', '2026-03-20');

INSERT INTO claims (policy_number, claim_date, claim_amount, claim_status) VALUES 
('POL-1002', '2026-01-10', 10000.00, 'Approved'),
('POL-1003', '2026-02-15', 5000.00, 'Approved'),
('POL-1005', '2026-02-20', 20000.00, 'Pending');


-- ==========================================
-- TASK 1 & 2 & 3: EIGHT REPORTING QUERIES
-- ==========================================

-- 1. Policies expiring in the next 30 days
SELECT policy_number, product_name, expiry_date, DATEDIFF(expiry_date, CURRENT_DATE()) AS days_to_expiry
FROM policies
WHERE expiry_date BETWEEN CURRENT_DATE() AND DATE_ADD(CURRENT_DATE(), INTERVAL 30 DAY);

-- 2. Total premium per product
SELECT product_name, SUM(premium_amount) AS total_premium
FROM policies
GROUP BY product_name;

-- 3. Customers with more than one active policy (GROUP BY & HAVING)
SELECT c.customer_name, COUNT(p.policy_number) AS active_policies
FROM customers c
JOIN policies p ON c.customer_id = p.customer_id
WHERE p.status = 'Active'
GROUP BY c.customer_id, c.customer_name
HAVING COUNT(p.policy_number) > 1;

-- 4. Claim ratio per product (> 60% filter using HAVING)
SELECT 
    p.product_name,
    SUM(p.premium_amount) AS total_premium,
    COALESCE(SUM(c.claim_amount), 0) AS total_claims,
    ROUND((COALESCE(SUM(c.claim_amount), 0) / SUM(p.premium_amount)) * 100, 2) AS claim_ratio_pct
FROM policies p
LEFT JOIN claims c ON p.policy_number = c.policy_number
GROUP BY p.product_name
HAVING claim_ratio_pct > 60;

-- 5. Policies without claims (LEFT JOIN)
SELECT p.policy_number, p.product_name, p.status
FROM policies p
LEFT JOIN claims c ON p.policy_number = c.policy_number
WHERE c.claim_id IS NULL;

-- 6. Average premium by age band (CASE, DATEDIFF, SUBSTRING, CONCAT)
SELECT 
    CASE 
        WHEN TIMESTAMPDIFF(YEAR, c.dob, CURRENT_DATE()) < 30 THEN 'Under 30'
        WHEN TIMESTAMPDIFF(YEAR, c.dob, CURRENT_DATE()) BETWEEN 30 AND 50 THEN '30-50'
        ELSE 'Above 50'
    END AS age_band,
    ROUND(AVG(p.premium_amount), 2) AS avg_premium,
    CONCAT('Band: ', 
           CASE 
               WHEN TIMESTAMPDIFF(YEAR, c.dob, CURRENT_DATE()) < 30 THEN '<30' 
               ELSE '30+' 
           END) AS band_label,
    SUBSTRING(c.phone, 7, 4) AS masked_phone_suffix
FROM customers c
JOIN policies p ON c.customer_id = p.customer_id
GROUP BY age_band;

-- 7. Monthly new policies (DATE_FORMAT)
SELECT DATE_FORMAT(start_date, '%Y-%m') AS policy_month, COUNT(*) AS new_policies_count
FROM policies
GROUP BY policy_month
ORDER BY policy_month;

-- 8. Top-5 agents by total premium
SELECT a.agent_name, a.region, SUM(p.premium_amount) AS total_premium_generated
FROM agents a
JOIN policies p ON a.agent_id = p.agent_id
GROUP BY a.agent_id, a.agent_name, a.region
ORDER BY total_premium_generated DESC
LIMIT 5;
