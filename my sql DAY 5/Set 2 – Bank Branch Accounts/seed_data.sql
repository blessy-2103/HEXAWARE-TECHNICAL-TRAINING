USE bank_branch_db;

-- Insert Branches
INSERT INTO branches (branch_name, branch_location) VALUES
('MG Road Branch', 'Bangalore'),
('Connaught Place Branch', 'New Delhi'),
('Bandra West Branch', 'Mumbai');

-- Insert Customers
INSERT INTO customers (customer_name, address, phone, kyc_status) VALUES
('Aarav Sharma', '12 Park Street, Bangalore', '9876543210', 'Verified'),
('Priya Nair', '45 MG Road, New Delhi', '9123456780', 'Verified'),
('Rohan Verma', '78 Linking Road, Mumbai', '9988776655', 'Pending'),
('Neha Gupta', '12 Sector 18, Noida', '9811223344', 'Verified'),
('Vikram Singh', '56 Civil Lines, Jaipur', '9722334455', 'Verified');

-- Insert Relationship Managers
INSERT INTO relationship_managers (manager_name, email) VALUES
('Amit Patel', 'amit.patel@bank.com'),
('Sunita Rao', 'sunita.rao@bank.com'),
('Rahul Sen', 'rahul.sen@bank.com');

-- Insert Accounts
INSERT INTO accounts (account_number, customer_id, branch_id, manager_id, account_type, balance, opened_date) VALUES
('ACC-5001', 1, 1, 1, 'Savings', 45000.50, '2026-01-15'),
('ACC-5002', 2, 2, 2, 'Current', 125000.00, '2026-02-10'),
('ACC-5003', 3, 3, 3, 'Savings', 15000.75, '2025-11-20'),
('ACC-5004', 4, 2, 2, 'Salary', 85000.00, '2026-01-05'),
('ACC-5005', 5, 1, 1, 'Fixed Deposit', 500000.00, '2025-10-12');
