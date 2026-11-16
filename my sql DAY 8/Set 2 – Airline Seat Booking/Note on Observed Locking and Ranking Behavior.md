### Locking and Ranking Notes

1. **Observed Locking Behavior (Two-Session Simulation):**
   - When **Session 1** executes `SELECT ... FOR UPDATE` on seat `12A` for flight `AI-101`, InnoDB places an exclusive lock on that specific row index.
   - If **Session 2** attempts to run the same query concurrently for seat `12A`, it gets blocked and waits until Session 1 completes its transaction (`COMMIT` or `ROLLBACK`). 
   - This ensures that two customers attempting to book the same seat simultaneously cannot both read it as available at the same time, preventing double bookings. Once Session 1 commits, Session 2 unblocks and discovers the seat status is now 'BOOKED'.

2. **`RANK()` vs. `DENSE_RANK()` Difference:**
   - **`RANK()`**: Skips rank numbers after ties. If two passengers share the highest fare (both ranked `1`), the next distinct fare receives rank `3` (skipping `2`).
   - **`DENSE_RANK()`**: Does not leave gaps. If two passengers share rank `1`, the next distinct fare immediately receives rank `2`.
