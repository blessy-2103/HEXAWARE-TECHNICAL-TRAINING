
NOTE ON OBSERVED LOCKING IN MYSQL INNODB
============================================================

1. Record Locks (`FOR UPDATE`):
   - When `SELECT ... FOR UPDATE` is executed in `sp_book_resource`, InnoDB acquires exclusive (X) locks on the specific 
     index records for the targeted `resource_id`.
   - Other concurrent transactions attempting to read with `FOR UPDATE` or modify the same row are blocked until the 
     active transaction commits or rolls back. This prevents race conditions and "lost update" anomalies (overselling inventory).

2. Gap Locks and Next-Key Locking:
   - Under the default `REPEATABLE READ` isolation level, InnoDB utilizes Next-Key Locking (Record Lock + Gap Lock) on range queries.
   - If a transaction performs a range search or non-unique index scan, InnoDB locks the index record as well as the "gap" 
     before it to prevent other transactions from inserting new "phantom" rows into that range.

3. Deadlock Management:
   - When two concurrent transactions acquire shared locks and attempt to upgrade to exclusive locks in reverse order, 
     a deadlock can occur. 
   - InnoDB's internal deadlock detector automatically identifies the cycle, rolls back the lighter/younger transaction, 
     and raises an error (handled gracefully by our `DECLARE EXIT HANDLER FOR SQLEXCEPTION`).
