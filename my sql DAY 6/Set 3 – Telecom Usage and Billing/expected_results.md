# Expected Results Document: Set 3 – Telecom Usage and Billing

This document outlines the expected output result sets for the eight reporting queries implemented in `reports.sql` for the Telecom Operator reporting system.

---

### Query 1: Total call minutes per plan
* **Purpose:** Aggregates total call duration consumed by subscribers across different tariff plans.
* **Expected Output:**
| plan_name | total_call_minutes |
| :--- | :--- |
| Basic 30GB | 600 |
| Unlimited 150GB | 2000 |
| Enterprise 500GB | 300 |

---

### Query 2: Subscribers exceeding their data limit
* **Purpose:** Identifies heavy data users who crossed their assigned monthly plan quota.
* **Expected Output:**
| subscriber_name | plan_name | data_limit_gb | data_used_gb |
| :--- | :--- | :--- | :--- |
| Rohan Verma | Enterprise 500GB | 500 | 480.00 *(Note: if testing overflow, e.g. Priya Nair or Rohan Verma)* |
| Vikram Singh | Unlimited 150GB | 150 | 155.00 |

---

### Query 3: Month-wise revenue
* **Purpose:** Calculates total billing revenue categorized by month.
* **Expected Output:**
| revenue_month | total_revenue |
| :--- | :--- |
| 2026-01 | 2270.00 |
| 2026-02 | 1420.00 |

---

### Query 4: Invoices overdue by more than 15 days
* **Purpose:** Filters unpaid invoices where the due date has passed by over 15 days as of current system date.
* **Expected Output:**
| invoice_number | subscriber_id | amount_due | due_date | days_overdue |
| :--- | :--- | :--- | :--- | :--- |
| INV-2026-003 | 3 | 1550.00 | 2026-01-15 | ~302 |
| INV-2026-005 | 5 | 720.00 | 2026-01-20 | ~297 |

---

### Query 5: Subscribers with no usage
* **Purpose:** Uses a `LEFT JOIN` to locate inactive subscribers with zero call records.
* **Expected Output:** *(Empty if all seed subscribers have records, or lists any unseeded new subscribers)*

---

### Query 6: ARPU (Average Revenue Per User) by circle
* **Purpose:** Computes Average Revenue Per User segmented by telecom circle/region.
* **Expected Output:**
| circle | arpu |
| :--- | :--- |
| Karnataka | 950.00 |
| Maharashtra | 735.00 |
| Delhi | 320.00 |

---

### Query 7: Plan popularity
* **Purpose:** Counts active subscriber adoption per plan.
* **Expected Output:**
| plan_name | subscriber_count |
| :--- | :--- |
| Basic 30GB | 2 |
| Unlimited 150GB | 2 |
| Enterprise 500GB | 1 |

---

### Query 8: Top-10 data consumers
* **Purpose:** Ranks the highest data-consuming subscribers.
* **Expected Output:**
| subscriber_name | circle | data_used_gb |
| :--- | :--- | :--- |
| Rohan Verma | Karnataka | 480.00 |
| Vikram Singh | Maharashtra | 155.00 |
| Priya Nair | Maharashtra | 140.00 |
| Amit Sharma | Karnataka | 25.50 |
| Neha Gupta | Delhi | 32.00 |
