# Expected Results Document – Day 6 Assignment: Retail Sales Reporting

This document records the expected output result sets for the eight core reporting queries, string/date function usages, and NULL behavior analyses executed in `reports.sql`.

---

## Task 1: Core Reporting Query Results

### 1. Top-5 Products by Revenue
| product_name | total_revenue |
| :--- | :--- |
| Laptop | 110000.00 |
| Smartphone | 75000.00 |
| Running Shoes | 10500.00 |
| Backpack | 6000.00 |

### 2. Month-Wise Sales
| sales_month | monthly_sales |
| :--- | :--- |
| 2026-03 | 201500.00 |

### 3. Customers with More Than Three Orders (`HAVING`)
| customer_name | total_orders |
| :--- | :--- |
| Amit Sharma | 4 |
| Priya Nair | 4 |

### 4. Average Basket Value Per City
| city | avg_basket_value |
| :--- | :--- |
| Bangalore | 35875.00 |
| Mumbai | 50375.00 |

### 5. Orders Without Payment Items (`LEFT JOIN`)
| order_id | order_date | payment_status |
| :--- | :--- | :--- |
| 4 | 2026-03-15 | Pending |
| 9 | 2026-03-04 | Pending |

### 6. Customers with No Orders (`LEFT JOIN`)
| customer_id | customer_name |
| :--- | :--- |
| 5 | Vikram Singh |

### 7. Product-Category Revenue Share (%)
| category | category_revenue | revenue_share_percentage |
| :--- | :--- | :--- |
| Electronics | 185000.00 | 91.81 |
| Footwear | 10500.00 | 5.21 |
| Accessories | 6000.00 | 2.98 |

### 8. Day with the Highest Sales
| order_date | daily_sales |
| :--- | :--- |
| 2026-03-02 | 55000.00 |

---

## Task 2: String, Date, and Math Functions Output Sample
| customer_upper | customer_details | days_since_order | formatted_date | price_with_gst |
| :--- | :--- | :--- | :--- | :--- |
| AMIT SHARMA | Amit Sharma from Bangalore | ~100+ days | 01-Mar-2026 | 64900.00 |

---

## Task 3: NULL Behavior Demonstration Output
| total_rows_counted | non_null_product_ids | sum_of_prices | average_price |
| :--- | :--- | :--- | :--- |
| 4 | 4 | 85000.00 | 21250.00 |
*Note: `COUNT(*)` counts table rows, whereas aggregate functions like `SUM()` and `AVG()` correctly ignore `NULL` entries.*