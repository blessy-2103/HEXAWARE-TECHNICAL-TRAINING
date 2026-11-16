### ACID Properties in the Retail Inventory Scenario

1. **Atomicity:**
   - *Example:* The entire reservation workflow (stock deduction, order insertion, and order line creation) must succeed or fail as a single unit. If the system decrements stock but crashes before inserting the order line, Atomicity ensures the entire transaction rolls back so inventory isn't lost.

2. **Consistency:**
   - *Example:* Database constraints (such as `CHECK (stock_quantity >= 0)` or foreign keys between `orders` and `customers`) ensure that the database moves from one valid state to another, maintaining inventory integrity.

3. **Isolation:**
   - *Example:* When two customers concurrently purchase the last item of a product, `FOR UPDATE` or row-level locking ensures transactions are isolated. One transaction completes its reservation and check before the next concurrent transaction is allowed to read and decrement the stock.

4. **Durability:**
   - *Example:* Once a payment and reservation transaction issues a `COMMIT`, the changes are permanently saved to disk (via InnoDB redo logs). Even if a server power failure occurs immediately after, the confirmed order and updated stock levels will not be lost.

---

### Case Where a Non-Transactional Design Would Break It
- **Scenario:** If the inventory system used a non-transactional storage engine or omitted explicit transaction blocks (`START TRANSACTION` / `COMMIT`), individual statements would auto-commit independently.
- **How it breaks:** If a customer places an order for 2 items, the stock is decremented successfully, but the server crashes right before the `order_lines` record is written. Because it lacks atomicity, the inventory is permanently reduced by 2 units on disk, but no corresponding order or audit trail is recorded. This leaves the database in an inconsistent state, causing stock discrepancies and phantom inventory loss[cite: 1].
