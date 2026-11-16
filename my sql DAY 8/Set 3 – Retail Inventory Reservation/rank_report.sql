-- rank_report.sql: Correlated subqueries, CTE rankings, and window functions (LAG/LEAD)

-- Part 1: Correlated subquery to list products whose stock is below category average
p1.product_name,
    p1.category_id,
    p1.stock_quantity
FROM products p1
WHERE p1.stock_quantity < (
    SELECT AVG(p2.stock_quantity)
    FROM products p2
    WHERE p2.category_id = p1.category_id
);

-- Part 2: CTE report ranking products by sales within each category (Top 3)
WITH RankedProducts AS (
    SELECT 
        p.category_id,
        p.product_id,
        p.product_name,
        SUM(ol.quantity * ol.price) AS total_sales,
        ROW_NUMBER() OVER (PARTITION BY p.category_id ORDER BY SUM(ol.quantity * ol.price) DESC) AS sales_rank
    FROM products p
    JOIN order_lines ol ON p.product_id = ol.product_id
    GROUP BY p.category_id, p.product_id, p.product_name
)
SELECT 
    category_id,
    product_id,
    product_name,
    total_sales,
    sales_rank
FROM RankedProducts
WHERE sales_rank <= 3;

-- Part 3: Using LEAD and LAG to show month-on-month sales change for each product
WITH MonthlyProductSales AS (
    SELECT 
        product_id,
        DATE_FORMAT(order_date, '%Y-%m') AS sales_month,
        SUM(quantity * price) AS monthly_revenue
    FROM order_lines ol
    JOIN orders o ON ol.order_id = o.order_id
    GROUP BY product_id, sales_month
)
SELECT 
    product_id,
    sales_month,
    monthly_revenue,
    LAG(monthly_revenue, 1) OVER (PARTITION BY product_id ORDER BY sales_month) AS previous_month_revenue,
    LEAD(monthly_revenue, 1) OVER (PARTITION BY product_id ORDER BY sales_month) AS next_month_revenue
FROM MonthlyProductSales;
