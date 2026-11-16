-- reservation.sql: Inventory reservation, order processing, and stock check rollback

START TRANSACTION;

-- Step 1: Check available stock and lock the product row
SELECT stock_quantity 
FROM products 
WHERE product_id = 501 FOR UPDATE;

-- (Application validation check: Ensure stock_quantity >= ordered_quantity, e.g., 2 units)

-- Step 2: Decrement product stock
UPDATE products 
SET stock_quantity = stock_quantity - 2 
WHERE product_id = 501;

-- Step 3: Insert into orders table
INSERT INTO orders (order_id, customer_id, order_date, status)
VALUES (DEFAULT, 1001, NOW(), 'PENDING');

-- (Suppose order_id generated is 9001)

-- Step 4: Insert into order lines table
INSERT INTO order_lines (order_line_id, order_id, product_id, quantity, price)
VALUES (DEFAULT, 9001, 501, 2, 450.00);

-- Step 5: Simulate stock exhaustion / insufficient stock check & rollback (Uncomment to test)
-- If stock drops below 0 or validation fails:
-- ROLLBACK; 
-- Result: Stock count remains completely unchanged, and order/order_lines insertions are aborted.

COMMIT;
