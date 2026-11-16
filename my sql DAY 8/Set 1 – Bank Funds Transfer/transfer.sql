-- transfer.sql: Funds transfer, validation, failure rollback, and savepoint demonstration

START TRANSACTION;

-- Step 1: Validate sender account status and balance before debiting
SELECT status, balance 
FROM accounts 
WHERE account_id = 101 FOR UPDATE;

-- (Application validation check: Ensure status = 'ACTIVE' and balance >= 500)

-- Step 2: Debit sender and credit receiver
UPDATE accounts 
SET balance = balance - 500 
WHERE account_id = 101;

UPDATE accounts 
SET balance = balance + 500 
WHERE account_id = 102;

-- Step 3: Demonstrate deliberate failure and rollback (Uncomment to test)
-- SIGNAL SQLSTATE '45000' SET MESSAGE_TITLE = 'Simulated Failure';
-- ROLLBACK;
-- SELECT * FROM accounts WHERE account_id IN (101, 102); -- Balances remain unchanged

-- Step 4: Use a SAVEPOINT to undo only the fee-posting step while retaining the transfer
SAVEPOINT transfer_complete;

-- Attempt to post a transfer fee
UPDATE accounts 
SET balance = balance - 25 
WHERE account_id = 101;

-- If fee-posting encounters an issue, rollback only to the savepoint
ROLLBACK TO SAVEPOINT transfer_complete;

-- Commit the main fund transfer transaction
COMMIT;
