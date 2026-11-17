
-- 1. Rank users by total bookings within each category using RANK()
WITH user_booking_stats AS (
    SELECT 
        b.user_id,
        u.user_name,
        r.category_id,
        COUNT(b.booking_id) AS total_bookings,
        SUM(b.quantity) AS total_quantity_booked
    FROM bookings b
    JOIN users u ON b.user_id = u.user_id
    JOIN resources r ON b.resource_id = r.resource_id
    GROUP BY b.user_id, u.user_name, r.category_id
),
ranked_category_users AS (
    SELECT 
        user_id,
        user_name,
        category_id,
        total_bookings,
        RANK() OVER (PARTITION BY category_id ORDER BY total_bookings DESC) AS booking_rank,
        DENSE_RANK() OVER (PARTITION BY category_id ORDER BY total_bookings DESC) AS booking_dense_rank
    FROM user_booking_stats
)
SELECT * 
FROM ranked_category_users 
WHERE booking_rank <= 5;


-- 2. Running Totals and Moving Averages of Bookings over Time
WITH daily_booking_summary AS (
    SELECT 
        DATE(booking_date) AS booking_day,
        COUNT(booking_id) AS daily_bookings,
        SUM(quantity) AS daily_quantity
    FROM bookings
    GROUP BY DATE(booking_date)
)
SELECT 
    booking_day,
    daily_bookings,
    SUM(daily_bookings) OVER (ORDER BY booking_day ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS cumulative_bookings,
    AVG(daily_bookings) OVER (ORDER BY booking_day ROWS BETWEEN 6 PRECEDING AND CURRENT ROW) AS moving_avg_7day
FROM daily_booking_summary;
