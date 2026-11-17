DELIMITER //

-- Task 2: sp_place_order with IN parameters and an OUT parameter for order number
CREATE PROCEDURE sp_place_order(
    IN p_customer_id INT,
    IN p_product_id INT,
    IN p_quantity INT,
    OUT p_order_id INT
)
BEGIN
    -- Declare variables for stock check
    DECLARE v_current_stock INT;
    
    -- Declare error handler for transaction rollback
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error occurred while placing order. Transaction rolled back.';
    END;

    START TRANSACTION;

    -- Validate stock availability
    SELECT stock_qty INTO v_current_stock 
    FROM products 
    WHERE product_id = p_product_id 
    FOR UPDATE;

    IF v_current_stock IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Product not found.';
    ELSEIF v_current_stock < p_quantity THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient stock available for this product.';
    END IF;

    -- Insert into orders table
    INSERT INTO orders (customer_id, order_date, status) 
    VALUES (p_customer_id, NOW(), 'PENDING');
    
    -- Capture the generated order ID
    SET p_order_id = LAST_INSERT_ID();

    -- Insert order line item
    INSERT INTO order_items (order_id, product_id, quantity) 
    VALUES (p_order_id, p_product_id, p_quantity);

    -- Update product stock
    UPDATE products 
    SET stock_qty = stock_qty - p_quantity 
    WHERE product_id = p_product_id;

    COMMIT;
END //

-- Task 3: Create stored function fn_order_total_with_tax
CREATE FUNCTION fn_order_total_with_tax(p_order_id INT) 
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE v_subtotal DECIMAL(10,2);
    DECLARE v_tax_rate DECIMAL(4,2) DEFAULT 0.05; -- 5% tax
    
    SELECT SUM(line_total) INTO v_subtotal
    FROM order_items
    WHERE order_id = p_order_id;
    
    IF v_subtotal IS NULL THEN
        RETURN 0.00;
    END IF;
    
    RETURN v_subtotal + (v_subtotal * v_tax_rate);
END //

-- Task 5: Raise business error using SIGNAL SQLSTATE '45000'
CREATE PROCEDURE sp_validate_discount(IN p_discount_pct DECIMAL(5,2))
BEGIN
    IF p_discount_pct > 50.00 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Business Rule Violation: Discount cannot exceed 50%.';
    END IF;
    
    SELECT 'Discount applied successfully' AS message;
END //

DELIMITER ;

-- Usage example for function inside a SELECT:
-- SELECT order_id, fn_order_total_with_tax(order_id) AS total_with_tax FROM orders;
