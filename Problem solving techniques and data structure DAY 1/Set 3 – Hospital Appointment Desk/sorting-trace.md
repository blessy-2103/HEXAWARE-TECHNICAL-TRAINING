# Hospital Appointment System – Sorting Trace

## Selection Sort – Step-by-Step Explanation

### Objective

The aim is to arrange the appointments based on their priority.

- First, appointments with higher urgency should come first (Urgency 1 has the highest priority).
- If two appointments have the same urgency, the one with the earlier arrival time should come first.

### Initial Test Data

| Appointment ID | Patient ID | Urgency | Arrival Time |
|---|---|---|---|
| A | 101 | 3 | 10:00 AM |
| B | 102 | 1 | 09:00 AM |
| C | 103 | 2 | 08:00 AM |
| D | 104 | 1 | 08:30 AM |

**Initially, the appointments are in this order:**

```text
A → B → C → D
```

### Pass 1

I start by taking appointment A and comparing it with the remaining appointments.

- B has higher priority than A because its urgency is 1.
- C has lower priority than B.
- D also has urgency 1, but it arrived earlier than B.
- So, D should be placed first.

I swap A and D.

```text
Before: A → B → C → D
After:  D → B → C → A
```

### Pass 2

Now I check the remaining appointments B, C and A.

- B has higher priority than C.
- B also has higher priority than A.
- So, B is already in the correct position.

No swap is needed.

```text
D → B → C → A
```

### Pass 3

Only C and A are left to compare.

- C has higher priority than A.
- So, C stays before A.

No swap is needed.

```text
D → B → C → A
```

### Final Sorted Output

| Position | Appointment ID | Patient ID | Urgency | Arrival Time |
|---|---|---|---|---|
| 1 | D | 104 | 1 | 08:30 AM |
| 2 | B | 102 | 1 | 09:00 AM |
| 3 | C | 103 | 2 | 08:00 AM |
| 4 | A | 101 | 3 | 10:00 AM |

**Final order:**

```text
D → B → C → A
```

### Number of Swaps

| Pass | Result |
|---|---|
| Pass 1 | A and D swapped |
| Pass 2 | No swap |
| Pass 3 | No swap |
| **Total** | **1 swap** |

### Time and Space Complexity

| Case | Time Complexity |
|---|---|
| Best Case | O(n²) |
| Average Case | O(n²) |
| Worst Case | O(n²) |

**Space Complexity:** O(1), because Selection Sort uses the same array and does not need another array for sorting.

### Conclusion

Through this example, I understood how Selection Sort arranges appointments by checking their priority one by one. It finds the correct appointment and places it in its position.

For this small set of four appointments, only one swap was needed. However, Selection Sort performs many comparisons when the number of appointments increases, so it may not be the best choice for a large hospital system.
