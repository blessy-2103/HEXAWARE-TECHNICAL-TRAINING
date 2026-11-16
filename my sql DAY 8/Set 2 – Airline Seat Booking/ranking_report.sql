-- ranking_report.sql: Rankings, CTEs, running totals, and correlated subqueries

-- Part 1: Ranked report using RANK and DENSE_RANK
SELECT 
    passenger_name,
    flight_id,
    fare,
    RANK() OVER (PARTITION BY flight_id ORDER BY fare DESC) AS fare_rank,
    DENSE_RANK() OVER (PARTITION BY flight_id ORDER BY fare DESC) AS fare_dense_rank
FROM bookings;

-- Part 2: CTE report with running total of revenue per flight
WITH FlightBookings AS (
    SELECT 
        passenger_name,
        flight_id,
        fare,
        booking_date
    FROM bookings
)
SELECT 
    passenger_name,
    flight_id,
    fare,
    SUM(fare) OVER (PARTITION BY flight_id ORDER BY booking_date ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS running_total_revenue
FROM FlightBookings;

-- Part 3: Correlated subquery for passengers with fares above their flight's average
SELECT 
    b1.passenger_name,
    b1.flight_id,
    b1.fare
FROM bookings b1
WHERE b1.fare > (
    SELECT AVG(b2.fare)
    FROM bookings b2
    WHERE b2.flight_id = b1.flight_id
);
