
ACID PROPERTIES IN MYSQL INNODB TRANSACTIONS
============================================================

1. Atomicity (All or Nothing):
   - Definition: Ensures that all operations within a transaction unit succeed completely, or the entire transaction is aborted.
   - Implementation in MySQL: InnoDB uses undo logs. If a statement fails or `ROLLBACK` is invoked, InnoDB uses the undo logs 
     to reverse any partial data modifications, returning the database to its exact prior state.

2. Consistency (Database Invariants & Constraints):
   - Definition: Ensures that a transaction transitions the database from one valid state to another, preserving all 
     defined rules (foreign keys, unique constraints, check constraints, triggers).
   - Implementation in MySQL: Constraints are enforced at the statement and transaction boundary. If any constraint is violated, 
     the transaction triggers an error and is rolled back.

3. Isolation (Concurrency Control):
   - Definition: Determines how and when changes made by concurrent transactions become visible to other transactions.
   - Implementation in MySQL: InnoDB achieves isolation using Multi-Version Concurrency Control (MVCC) along with locking 
     mechanisms (Record Locks, Gap Locks, and Next-Key Locks) to prevent anomalies like dirty reads and phantom reads.

4. Durability (Persistence of Committed Data):
   - Definition: Guarantees that once a transaction is committed, its changes are permanent, even in the event of a system crash or power failure.
   - Implementation in MySQL: InnoDB uses the Redo Log (write-ahead logging) and the doublewrite buffer. Committed transactions 
     are flushed to disk securely, ensuring they survive system crashes.
