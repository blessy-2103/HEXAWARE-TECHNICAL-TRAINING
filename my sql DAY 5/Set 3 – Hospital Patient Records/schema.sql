-- Create Database
CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

-- 1. Departments Table
CREATE TABLE departments (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- 2. Doctors Table
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_name VARCHAR(100) NOT NULL,
    department_id INT NOT NULL,
    CONSTRAINT fk_doctor_dept FOREIGN KEY (department_id) 
        REFERENCES departments(department_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 3. Patients Table
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL
) ENGINE=InnoDB;

-- 4. Visits Table (Tracks patient visits, diagnosis, and default status 'OPEN')
CREATE TABLE visits (
    visit_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    visit_date DATE NOT NULL CHECK (visit_date <= CURDATE()),
    diagnosis TEXT NOT NULL,
    prescription TEXT,
    visit_status VARCHAR(20) DEFAULT 'OPEN',
    CONSTRAINT fk_visit_patient FOREIGN KEY (patient_id) 
        REFERENCES patients(patient_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_visit_doctor FOREIGN KEY (doctor_id) 
        REFERENCES doctors(doctor_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 5. Billing Table (Tracks visit bills with non-negative constraint)
CREATE TABLE billing (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    visit_id INT NOT NULL UNIQUE,
    bill_amount DECIMAL(10, 2) NOT NULL CHECK (bill_amount >= 0),
    CONSTRAINT fk_billing_visit FOREIGN KEY (visit_id) 
        REFERENCES visits(visit_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
) ENGINE=InnoDB;
