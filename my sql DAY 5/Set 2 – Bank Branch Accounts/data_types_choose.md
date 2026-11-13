---

###  Data Types Justification & Operational Commands Notes


* **Why `DECIMAL(15, 2)` for money?** Floating-point data types (`FLOAT`, `DOUBLE`) can introduce rounding errors due to binary representation, which is critical and unacceptable in financial calculations. `DECIMAL` stores exact numeric values with fixed precision and scale.
* **Candidate & Composite Keys:** 
  * `account_number` is the primary candidate key for accounts.
  * A composite key could be formed using `(customer_id, branch_id)` if a customer is mapped across multiple branch contexts, though surrogate integer keys (`customer_id`, `branch_id`) keep relations clean.
