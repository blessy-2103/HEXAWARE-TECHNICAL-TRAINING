# Hospital Appointment System – Sorting Trace

## Selection Sort – Step-by-Step Trace

### Objective
Sort appointments based on:
1. Urgency level (1 = highest priority).
2. Arrival time (earliest first) when urgency levels are equal.

### Initial Test Data

| Appointment ID | Patient ID | Urgency | Arrival Time |
|---|---|---|---|
| A | 101 | 3 | 10:00 AM |
| B | 102 | 1 | 09:00 AM |
| C | 103 | 2 | 08:00 AM |
| D | 104 | 1 | 08:30 AM |

**Initial order:**

```text
A → B → C → D
```

### Pass 1

- Start with A.
- Compare A with B: B has higher priority.
- Compare B with C: B has higher priority.
- Compare B with D: D has higher priority because both have urgency 1, but D arrived earlier.
- Minimum element: D.
- Swap A and D.

```text
Before: A → B → C → D
After:  D → B → C → A
```

### Pass 2

- Consider the remaining elements: B, C, A.
- Compare B with C: B has higher priority.
- Compare B with A: B has higher priority.
- Minimum element: B.
- No swap required.

```text
D → B → C → A
```

### Pass 3

- Consider the remaining elements: C, A.
- Compare C with A: C has higher priority.
- Minimum element: C.
- No swap required.

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

```text
D → B → C → A
```

### Swap Count

| Pass | Swap |
|---|---|
| Pass 1 | A ↔ D |
| Pass 2 | No swap |
| Pass 3 | No swap |
| **Total** | **1 swap** |

### Time and Space Complexity

| Case | Time Complexity |
|---|---|
| Best Case | O(n²) |
| Average Case | O(n²) |
| Worst Case | O(n²) |

**Space Complexity:** O(1), because Selection Sort sorts the array in place.

### Conclusion

Selection Sort repeatedly finds the highest-priority appointment from the unsorted portion and places it in the correct position. In this example, it sorts four appointments using one swap.

It is simple to implement but not suitable for very large appointment datasets because its time complexity is O(n²).
