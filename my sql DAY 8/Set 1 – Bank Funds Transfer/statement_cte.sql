-- statement_cte.sql: Monthly statement using CTE, ROW_NUMBER, and LAG

WITH MonthlyTransactions AS (
    SELECT 
        transaction_id,
        account_id,
        transaction_date,
        amount,
        balance_after,
        ROW_NUMBER() OVER (PARTITION BY account_id ORDER BY transaction_date, transaction_id) AS rn
    FROM transactions
    WHERE account_id = 101 
      AND transaction_date >= '2026-10-01' 
      AND transaction_date < '2026-11-01'
)
SELECT 
    transaction_id,
    transaction_date,
    amount,
    balance_after,
    LAG(balance_after, 1, balance_after - amount) OVER (ORDER BY transaction_date, rn) AS previous_balance
FROM MonthlyTransactions;
