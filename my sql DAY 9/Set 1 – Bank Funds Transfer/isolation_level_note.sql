[cite: 2]-- ==========================================================
-- Deliverable 3: isolation-level-note.sql (Isolation Level Documentation)
-- Description: Analysis of MySQL InnoDB transaction isolation levels,
-- concurrency anomalies prevented, and practical use cases.
-- ==========================================================

/*
TRANSACTION ISOLATION LEVELS IN MYSQL (InnoDB)
============================================================

MySQL InnoDB supports four standard isolation levels defined by the SQL:92 standard. 
Isolation levels control the degree of visibility of uncommitted changes made by concurrent transactions.

1. READ UNCOMMITTED
   - Behavior: Transactions can read data that is uncommitted by other concurrent transactions (Dirty Reads).
   - Anomalies Possible: Dirty Reads, Non-repeatable Reads, Phantom Reads.
   - Use Case: Extremely rare; used for approximate aggregations where precision is secondary to raw speed.

2. READ COMMITTED
   - Behavior: Each consistent read sets and reads its own fresh snapshot. Prevents dirty reads.
   - Anomalies Possible: Non-repeatable Reads, Phantom Reads.
   - Use Case: Commonly used in systems with heavy concurrent writes where reading slightly stale data within the same transaction is acceptable.

3. REPEATABLE READ (Default in MySQL InnoDB)
   - Behavior: All consistent reads within the same transaction read the snapshot established at the first read. 
     InnoDB uses Next-Key Locking to prevent Phantom Reads on range queries.
   - Anomalies Possible: None under standard InnoDB implementation ( díky MVCC and gap locks).
   - Use Case: Default transactional safety for financial, inventory, and order management applications.

4. SERIALIZABLE
   - Behavior: Forces all transactions to execute serially (one after another) by implicitly locking every 
     row read (`SELECT ... LOCK IN SHARE MODE`).
   - Anomalies Possible: None (fully isolated, but highly prone to lock contention and deadlocks).
   - Use Case: High-precision financial transfers or auditing routines requiring absolute concurrency control.


SUMMARY OF CONCURRENCY ANOMALIES PREVENTED:
------------------------------------------------------------
- Dirty Read: Reading uncommitted data that later gets rolled back. (Prevented at READ COMMITTED and above).
- Non-repeatable Read: Reading the same row twice within a transaction and getting different values because another transaction modified and committed it. (Prevented at REPEATABLE READ and above).
- Phantom Read: Executing a range search twice and getting different sets of rows because another transaction inserted new rows matching the criteria. (Prevented in InnoDB at REPEATABLE READ via Next-Key locking).
*/isolation_level_note.sql
