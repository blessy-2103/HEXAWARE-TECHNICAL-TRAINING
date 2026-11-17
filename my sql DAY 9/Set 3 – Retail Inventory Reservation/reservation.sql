
DELIMITER //

CREATE PROCEDURE sp_make_reservation(
    IN p_guest_id INT,
    IN p_room_id INT,
    IN p_check_in DATE,
    IN p_check_out DATE,
    OUT p_response_msg VARCHAR(255)
)
BEGIN
    DECLARE v_room_status VARCHAR(50);
    
    -- Error handler for automatic rollback and clean exit
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_response_msg = 'RESERVATION FAILED: Transaction rolled back due to database error or conflict.';
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Lock room record to prevent concurrent double-booking
    SELECT status INTO v_room_status 
    FROM rooms 
    WHERE room_id = p_room_id 
    FOR UPDATE;

    -- Validate room existence and availability
    IF v_room_status IS NULL THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Room does not exist.';
    ELSEIF v_room_status != 'AVAILABLE' THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Room is currently not available for reservation.';
    END IF;

    -- Update room status to reserved/occupied
    UPDATE rooms 
    SET status = 'RESERVED' 
    WHERE room_id = p_room_id;

    -- Insert reservation record
    INSERT INTO reservations (guest_id, room_id, check_in_date, check_out_date, booking_status)
    VALUES (p_guest_id, p_room_id, p_check_in, p_check_out, 'CONFIRMED');

    COMMIT;
    
    SET p_response_msg = 'RESERVATION SUCCESSFUL: Room booked and confirmed.';
END //

DELIMITER ;
