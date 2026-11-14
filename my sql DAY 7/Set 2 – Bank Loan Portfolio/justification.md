

| Query / Task Type | Selected Strategy | Rationale & Justification |
| --- | --- | --- |
| **Overdue Loans (> 3 instalments)** | `INNER JOIN` | Requires strict matching between `loans`, `customers`, and `branches` to report actionable credit risk items. |
| **Multi-branch Borrowers** | `JOIN` with `GROUP BY` & `HAVING` | Aggregates distinct branch identifiers per customer to flag clients borrowing across multiple regions. |
| **Guarantor-Borrower Cross Analysis** | Self Join (`customers` aliased twice) | Resolves dual roles where a customer record acts both as the primary borrower on a loan and as a guarantor for another. |
| **Branches with Zero Loans** | `LEFT JOIN` with `IS NULL` | Preserves all branch records from the master table while filtering out unmatched rows to audit inactive branches. |
| **Correlated Exposure Analysis** | Subquery with Aggregate Derivation | Compares individual customer total liabilities against comparative branch benchmarks dynamically. |
| **Inactive Repayment Tracking** | `EXISTS` & `NOT EXISTS` | Efficiently checks the presence of loans combined with the strict absence of recent repayment timestamps within a rolling window. |
| **Nested Branch Maximums** | Derived Table Aggregation | Computes multi-level nested maxima and averages to benchmark branch peak loan volumes against portfolio averages. |
