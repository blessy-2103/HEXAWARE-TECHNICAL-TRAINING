The NOT IN vs NOT EXISTS Null Pitfall:
When using NOT IN (subquery), if any row returned by the subquery contains a NULL value, the entire conditional check evaluates to UNKNOWN, causing the outer query to return zero rows. Switching to NOT EXISTS avoids this pitfall entirely because it evaluates row existence correctly under three-valued logic.

100x Scaling Strategy:

Indexing: Ensure foreign keys (customer_id, product_id, store_id, return_id) have appropriate B-tree indexes to maintain rapid joins.

Query Optimization: Replace correlated subqueries with indexed joins or window functions (RANK(), ROW_NUMBER()) as data volume grows.

Partitioning: Partition historical transaction logs like returns_log and refunds by return_date to prevent full table scans.
