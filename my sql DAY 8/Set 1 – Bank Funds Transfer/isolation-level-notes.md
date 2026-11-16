### Isolation Level and Autocommit Experiment Notes

1. **Autocommit Effect:**
   - By default, MySQL operates with `autocommit = 1`, where every single DML statement automatically commits upon execution. 
   - For multi-statement transactions (like transferring funds), `START TRANSACTION` explicitly disables autocommit for that session until an explicit `COMMIT` or `ROLLBACK` is issued.

2. **READ COMMITTED vs. REPEATABLE READ (InnoDB Two-Session Experiment):**
   - **REPEATABLE READ (Default):** When Session 1 starts a transaction and reads an account balance, it takes a consistent snapshot. If Session 2 updates and commits a new balance, Session 1 will continue to see the *original* snapshot value on subsequent reads within the same transaction (preventing non-repeatable reads).
   - **READ COMMITTED:** If the isolation level is changed (`SET TRANSACTION ISOLATION LEVEL READ COMMITTED;`), Session 1 will see the updated and committed balance immediately if Session 2 updates it during Session 1's transaction window (allowing non-repeatable reads, but reducing lock contention in high-concurrency systems).
```[cite: 1]
