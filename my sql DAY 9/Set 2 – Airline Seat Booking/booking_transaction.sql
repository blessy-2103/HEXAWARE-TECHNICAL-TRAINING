
DELIMITER //

CREATE PROCEDURE sp_book_resource(
    IN p_user_id INT,
    IN p_resource_id INT,
    IN p_quantity INT,
    OUT p_status_msg VARCHAR(255)
)
BEGIN
    DECLARE v_available_qty INT;
    
    -- Error handler to rollback on any SQL exception/deadlock
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status_msg = 'BOOKING FAILED: Transaction rolled back due to error or concurrency conflict.';
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Lock the specific resource row for update to prevent race conditions (overselling)
    SELECT available_qty INTO v_available_qty 
    FROM resources 
    WHERE resource_id = p_resource_id 
    FOR UPDATE;

    -- Validate availability
    IF v_available_qty IS NULL THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Resource not found.';
    ELSEIF v_available_qty < p_quantity THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Insufficient available quantity for booking.';
    END IF;

    -- Deduct quantity from resource inventory
    UPDATE resources 
    SET available_qty = available_qty - p_quantity 
    WHERE resource_id = p_resource_id;

    -- Insert booking record
    INSERT INTO bookings (user_id, resource_id, quantity, booking_date, status)
    VALUES (p_user_id, p_resource_id, p_quantity, NOW(), 'CONFIRMED');

    COMMIT;
    
    SET p_status_msg = 'BOOKING SUCCESSFUL: Resource reserved and committed.';
END //

DELIMITER ;
