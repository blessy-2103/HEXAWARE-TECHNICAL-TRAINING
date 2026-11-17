

-- 1. Create vw_order_summary hiding cost price and supplier columns
CREATE OR REPLACE VIEW vw_order_summary AS
SELECT 
    o.order_id,
    o.customer_id,
    o.order_date,
    o.status,
    oi.product_id,
    oi.quantity,
    oi.line_total
FROM orders o
JOIN order_items oi ON o.order_id = oi.order_id;

-- 2. Create vw_low_stock for products running low on inventory
CREATE OR REPLACE VIEW vw_low_stock AS
SELECT 
    product_id,
    product_name,
    selling_price,
    stock_qty
FROM products
WHERE stock_qty < 10;

-- 3. Demonstrate an updatable view with WITH CHECK OPTION
-- Base view for active products
CREATE OR REPLACE VIEW vw_active_products AS
SELECT product_id, product_name, selling_price, stock_qty, status
FROM products
WHERE status = 'ACTIVE'
WITH CHECK OPTION;

-- Example test for WITH CHECK OPTION:
-- This update will succeed because status remains 'ACTIVE'
UPDATE vw_active_products SET stock_qty = 50 WHERE product_id = 101;

-- This update will FAIL with an error because it violates the WITH CHECK OPTION (status != 'ACTIVE')
-- UPDATE vw_active_products SET status = 'DISCONTINUED' WHERE product_id = 101;
