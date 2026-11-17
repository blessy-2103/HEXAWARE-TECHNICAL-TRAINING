DELIMITER //

-- Approach A: Cursor-based procedure walking through orders pending > 3 days
CREATE PROCEDURE sp_process_pending_orders_cursor()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_order_id INT;
    
    -- Declare cursor for orders pending for more than 3 days
    DECLARE cur_pending CURSOR FOR 
        SELECT order_id 
        FROM orders 
        WHERE status = 'PENDING' 
          AND order_date < (NOW() - INTERVAL 3 DAY);
          
    -- Declare NOT FOUND handler to exit the loop
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur_pending;

    read_loop: LOOP
        FETCH cur_pending INTO v_order_id;
        
        IF done = 1 THEN
            LEAVE read_loop;
        END IF;

        -- Insert a follow-up action record for each pending order
        INSERT INTO order_followups (order_id, followup_date, remarks)
        VALUES (v_order_id, NOW(), 'Automated cursor follow-up: Order pending > 3 days');
        
    END LOOP;

    CLOSE cur_pending;
END //

DELIMITER ;

-- Approach B: Set-based Statement (Equivalent and highly optimized)
-- Instead of looping row-by-row via cursor, perform a single set-based INSERT:
INSERT INTO order_followups (order_id, followup_date, remarks)
SELECT order_id, NOW(), 'Automated set-based follow-up: Order pending > 3 days'
FROM orders
WHERE status = 'PENDING'
  AND order_date < (NOW() - INTERVAL 3 DAY);
