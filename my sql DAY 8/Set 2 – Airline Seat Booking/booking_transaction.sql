-- booking_transaction.sql: Atomic seat booking with row-level locking

START TRANSACTION;

-- Step 1: Check seat availability and lock the row to block concurrent attempts on the same seat
SELECT seat_status, fare 
FROM seats 
WHERE flight_id = 'AI-101' AND seat_number = '12A' FOR UPDATE;

-- (Application logic: Check if seat_status = 'AVAILABLE' before proceeding)

-- Step 2: Insert the booking record
INSERT INTO bookings (booking_id, flight_id, seat_number, passenger_name, fare, booking_date)
VALUES (DEFAULT, 'AI-101', '12A', 'Aarav Sharma', 12500.00, NOW());

-- Step 3: Update the seat status to booked
UPDATE seats 
SET seat_status = 'BOOKED' 
WHERE flight_id = 'AI-101' AND seat_number = '12A';

COMMIT;
