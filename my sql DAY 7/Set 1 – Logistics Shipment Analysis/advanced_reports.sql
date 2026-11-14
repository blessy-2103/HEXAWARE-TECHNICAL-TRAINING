

CREATE DATABASE IF NOT EXISTS logistics_db;
USE logistics_db;

-- 1. Hubs Table
CREATE TABLE hubs (
    hub_id INT AUTO_INCREMENT PRIMARY KEY,
    hub_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 2. Carriers Table
CREATE TABLE carriers (
    carrier_id INT AUTO_INCREMENT PRIMARY KEY,
    carrier_name VARCHAR(100) NOT NULL,
    rating DECIMAL(3,2) NOT NULL
) ENGINE=InnoDB;

-- 3. Customers Table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 4. Employees (Self-Join for Supervisor hierarchy)
CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(100) NOT NULL,
    supervisor_id INT NULL,
    CONSTRAINT fk_emp_sup FOREIGN KEY (supervisor_id) REFERENCES employees(employee_id)
) ENGINE=InnoDB;

-- 5. Shipments Table
CREATE TABLE shipments (
    shipment_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    carrier_id INT NOT NULL,
    origin_hub_id INT NOT NULL,
    destination_hub_id INT NOT NULL,
    shipping_cost DECIMAL(10,2) NOT NULL,
    scheduled_date DATE NOT NULL,
    delivery_date DATE NOT NULL,
    CONSTRAINT fk_shp_cust FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_shp_car FOREIGN KEY (carrier_id) REFERENCES carriers(carrier_id),
    CONSTRAINT fk_shp_orig FOREIGN KEY (origin_hub_id) REFERENCES hubs(hub_id),
    CONSTRAINT fk_shp_dest FOREIGN KEY (destination_hub_id) REFERENCES hubs(hub_id)
) ENGINE=InnoDB;

-- Seed Data Insertion
INSERT INTO hubs (hub_name, city) VALUES 
('North Hub', 'Delhi'), ('South Hub', 'Bangalore'), 
('West Hub', 'Mumbai'), ('East Hub', 'Kolkata');

INSERT INTO carriers (carrier_name, rating) VALUES 
('Express Logistics', 4.5), ('Swift Cargo', 4.2), ('Global Freight', 4.8);

INSERT INTO customers (customer_name, city) VALUES 
('Alpha Corp', 'Delhi'), ('Beta Retail', 'Bangalore'), ('Gamma Tech', 'Mumbai');

INSERT INTO employees (employee_name, supervisor_id) VALUES 
('Aarav Mehta', NULL), ('Priya Sharma', 1), ('Rohan Rao', 1), ('Neha Gupta', 2);

INSERT INTO shipments (customer_id, carrier_id, origin_hub_id, destination_hub_id, shipping_cost, scheduled_date, delivery_date) VALUES 
(1, 1, 1, 2, 4500.00, '2026-03-01', '2026-03-04'), -- Delayed by 3 days
(2, 2, 2, 3, 2800.00, '2026-03-05', '2026-03-06'), -- On time
(3, 3, 3, 1, 6200.00, '2026-03-02', '2026-03-07'), -- Delayed by 5 days
(1, 3, 1, 4, 3500.00, '2026-03-10', '2026-03-11'); -- On time


-- ==========================================
-- TASK 1: TEN ADVANCED JOIN QUERIES
-- ==========================================

-- 1. Shipments delayed by more than two days with carrier names (INNER JOIN)
SELECT s.shipment_id, c.carrier_name, s.scheduled_date, s.delivery_date,
       DATEDIFF(s.delivery_date, s.scheduled_date) AS delay_days
FROM shipments s
JOIN carriers c ON s.carrier_id = c.carrier_id
WHERE DATEDIFF(s.delivery_date, s.scheduled_date) > 2;

-- 2. Hubs with no outbound shipments (LEFT JOIN)
SELECT h.hub_id, h.hub_name
FROM hubs h
LEFT JOIN shipments s ON h.hub_id = s.origin_hub_id
WHERE s.shipment_id IS NULL;

-- 3. Employees with their supervisors (Self Join)
SELECT e.employee_name AS employee, COALESCE(sup.employee_name, 'Top Management') AS supervisor
FROM employees e
LEFT JOIN employees sup ON e.supervisor_id = sup.employee_id;

-- 4. All shipments with origin and destination hub names (Multiple INNER JOINs)
SELECT s.shipment_id, cust.customer_name, orig.hub_name AS origin, dest.hub_name AS destination, s.shipping_cost
FROM shipments s
JOIN customers cust ON s.customer_id = cust.customer_id
JOIN hubs orig ON s.origin_hub_id = orig.hub_id
JOIN hubs dest ON s.destination_hub_id = dest.hub_id;

-- 5. Carriers and their handled shipments (RIGHT JOIN)
SELECT c.carrier_name, s.shipment_id, s.shipping_cost
FROM shipments s
RIGHT JOIN carriers c ON s.carrier_id = c.carrier_id;

-- 6. Customers who have placed shipments above 5000 (INNER JOIN)
SELECT DISTINCT cust.customer_name, cust.city
FROM customers cust
JOIN shipments s ON cust.customer_id = s.customer_id
WHERE s.shipping_cost > 5000;

-- 7. Hubs involved as destination in any shipment (INNER JOIN / DISTINCT)
SELECT DISTINCT h.hub_id, h.hub_name
FROM hubs h
JOIN shipments s ON h.hub_id = s.destination_hub_id;

-- 8. Carrier performance check: average cost per carrier
SELECT c.carrier_name, ROUND(AVG(s.shipping_cost), 2) AS avg_cost
FROM carriers c
JOIN shipments s ON c.carrier_id = s.carrier_id
GROUP BY c.carrier_id, c.carrier_name;

-- 9. Cross Join between Hubs and Carriers (Matrix mapping)
SELECT h.hub_name, c.carrier_name
FROM hubs h
CROSS JOIN carriers c;

-- 10. Shipments handled by carriers with rating > 4.4
SELECT s.shipment_id, c.carrier_name, c.rating, s.shipping_cost
FROM shipments s
JOIN carriers c ON s.carrier_id = c.carrier_id
WHERE c.rating > 4.4;


-- ==========================================
-- TASK 2: SUBQUERIES (IN, EXISTS, ANY, ALL)
-- ==========================================

-- 1. Customers using IN subquery who shipped via 'Express Logistics'
SELECT customer_name, city
FROM customers
WHERE customer_id IN (
    SELECT s.customer_id 
    FROM shipments s 
    JOIN carriers c ON s.carrier_id = c.carrier_id 
    WHERE c.carrier_name = 'Express Logistics'
);

-- 2. Customers using EXISTS for active shipments
SELECT customer_name
FROM customers cust
WHERE EXISTS (
    SELECT 1 FROM shipments s WHERE s.customer_id = cust.customer_id
);

-- 3. Shipments with cost greater than ANY shipment originating from Hub 2
SELECT shipment_id, shipping_cost
FROM shipments
WHERE shipping_cost > ANY (
    SELECT shipping_cost FROM shipments WHERE origin_hub_id = 2
);

-- 4. Shipments with cost greater than ALL shipments handled by carrier 2
SELECT shipment_id, shipping_cost
FROM shipments
WHERE shipping_cost > ALL (
    SELECT shipping_cost FROM shipments WHERE carrier_id = 2
);


-- ==========================================
-- TASK 3: REWRITING SUBQUERIES AS JOINS
-- ==========================================
SELECT DISTINCT c.customer_name
FROM customers c
JOIN shipments s ON c.customer_id = s.customer_id;


-- ==========================================
-- TASK 4: NESTED SUBQUERY (Second-highest shipping cost per hub)
-- ==========================================
SELECT h.hub_name, s.shipping_cost
FROM shipments s
JOIN hubs h ON s.origin_hub_id = h.hub_id
WHERE s.shipping_cost = (
    SELECT DISTINCT shipping_cost 
    FROM shipments s2 
    WHERE s2.origin_hub_id = s.origin_hub_id 
    ORDER BY shipping_cost DESC 
    LIMIT 1 OFFSET 1
);
