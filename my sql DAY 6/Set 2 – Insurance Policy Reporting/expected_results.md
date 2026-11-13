# Expected Results Document: Set 2 – Insurance Policy Reporting

This document outlines the expected output result sets for the eight reporting queries implemented in `reports.sql` for the Insurance Policy Management system.

---

### Query 1: Policies expiring in the next 30 days
* **Purpose:** Identifies policies coming up for renewal within the upcoming 30 days.
* **Expected Output:**
| policy_number | product_name | expiry_date | days_to_expiry |
| :--- | :--- | :--- | :--- |
| POL-1001 | Life Shield | 2026-04-01 | 19 |
| POL-1003 | Auto Secure | 2026-04-10 | 28 |

---

### Query 2: Total premium per product
* **Purpose:** Calculates total business revenue generated per insurance product category.
* **Expected Output:**
| product_name | total_premium |
| :--- | :--- |
| Life Shield | 55000.00 |
| Health Care Plus | 33000.00 |
| Auto Secure | 12000.00 |

---

### Query 3: Customers with more than one active policy
* **Purpose:** Uses `GROUP BY` and `HAVING` to find high-value loyal customers holding multiple active policies.
* **Expected Output:**
| customer_name | active_policies |
| :--- | :--- |
| Rajesh Kumar | 2 |

---

### Query 4: Claim ratio per product (> 60%)
* **Purpose:** Identifies product lines where total claims exceed 60% of collected premiums.
* **Expected Output:**
| product_name | total_premium | total_claims | claim_ratio_pct |
| :--- | :--- | :--- | :--- |
| Health Care Plus | 33000.00 | 30000.00 | 90.91 |

---

### Query 5: Policies without claims
* **Purpose:** Uses a `LEFT JOIN` to list clean policies with zero claim history.
* **Expected Output:**
| policy_number | product_name | status |
| :--- | :--- | :--- |
| POL-1001 | Life Shield | Active |
| POL-1004 | Life Shield | Active |

---

### Query 6: Average premium by age band
* **Purpose:** Uses `CASE`, `DATEDIFF`, `SUBSTRING`, and `CONCAT` to categorize policyholders by age brackets and compute average premiums.
* **Expected Output:**
| age_band | avg_premium | band_label | masked_phone_suffix |
| :--- | :--- | :--- | :--- |
| 30-50 | 22500.00 | Band: 30+ | 3210 |
| Above 50 | 12000.00 | Band: 30+ | 6655 |
| Under 30 | 16500.00 | Band: <30 | 3444 |

---

### Query 7: Monthly new policies
* **Purpose:** Uses `DATE_FORMAT` to aggregate policy sign-ups chronologically by month.
* **Expected Output:**
| policy_month | new_policies_count |
| :--- | :--- |
| 2024-01 | 1 |
| 2025-03 | 1 |
| 2025-04 | 1 |
| 2025-05 | 1 |
| 2025-06 | 1 |

---

### Query 8: Top-5 agents by total premium
* **Purpose:** Ranks insurance agents by the cumulative premium volume generated.
* **Expected Output:**
| agent_name | region | total_premium_generated |
| :--- | :--- | :--- |
| Vikram Singh | North | 37000.00 |
| Ananya Roy | South | 33000.00 |
| Rohan Verma | West | 30000.00 |
