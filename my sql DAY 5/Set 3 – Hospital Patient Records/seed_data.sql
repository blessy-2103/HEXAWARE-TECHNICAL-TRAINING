USE hospital_db;

-- Insert Departments
INSERT INTO departments (department_name) VALUES
('Cardiology'),
('Neurology'),
('Orthopedics'),
('General Medicine');

-- Insert Doctors
INSERT INTO doctors (doctor_name, department_id) VALUES
('Dr. Ramesh Kumar', 1),
('Dr. Ananya Roy', 2),
('Dr. Suresh Menon', 3),
('Dr. Kavitha Sharma', 4);

-- Insert Patients
INSERT INTO patients (patient_name, address, phone) VALUES
('Rajesh Verma', '10 MG Road, Bangalore', '9876543210'),
('Sunita Patel', '22 Brigade Road, Bangalore', '9123456780'),
('Kiran Rao', '45 Linking Road, Mumbai', '9988776655');

-- Insert Visits
INSERT INTO visits (patient_id, doctor_id, visit_date, diagnosis, prescription, visit_status) VALUES
(1, 1, '2026-03-01', 'Hypertension', 'Amlodipine 5mg', 'CLOSED'),
(2, 3, '2026-03-05', 'Lower back pain', 'Pain relief gel & Rest', 'OPEN'),
(3, 4, '2026-03-10', 'Viral fever', 'Paracetamol 650mg', 'CLOSED');

-- Insert Billing
INSERT INTO billing (visit_id, bill_amount) VALUES
(1, 1500.00),
(2, 2500.00),
(3, 800.00);
