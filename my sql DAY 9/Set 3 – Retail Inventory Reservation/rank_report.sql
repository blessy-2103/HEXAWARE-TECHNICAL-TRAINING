

-- 1. Rank guests by total spending per room category
WITH guest_spending AS (
    SELECT 
        g.guest_id,
        g.guest_name,
        r.category_id,
        SUM(res.total_amount) AS total_spent
    FROM reservations res
    JOIN guests g ON res.guest_id = g.guest_id
    JOIN rooms r ON res.room_id = r.room_id
    GROUP BY g.guest_id, g.guest_name, r.category_id
),
ranked_guests AS (
    SELECT 
        guest_id,
        guest_name,
        category_id,
        total_spent,
        RANK() OVER (PARTITION BY category_id ORDER BY total_spent DESC) AS spending_rank,
        DENSE_RANK() OVER (PARTITION BY category_id ORDER BY total_spent DESC) AS spending_dense_rank
    FROM guest_spending
)
SELECT * 
FROM ranked_guests 
WHERE spending_rank <= 3;


-- 2. Monthly Revenue Trend with Window Functions (Running Total)
WITH monthly_revenue AS (
    SELECT 
        DATE_FORMAT(check_in_date, '%Y-%m') AS booking_month,
        SUM(total_amount) AS monthly_revenue
    FROM reservations
    GROUP BY DATE_FORMAT(check_in_date, '%Y-%m')
)
SELECT 
    booking_month,
    monthly_revenue,
    SUM(monthly_revenue) OVER (ORDER BY booking_month ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS cumulative_revenue,
    AVG(monthly_revenue) OVER (ORDER BY booking_month ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) AS rolling_3month_avg
FROM monthly_revenue;
