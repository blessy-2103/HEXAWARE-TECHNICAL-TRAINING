# Order Processing System – Performance Analysis

## Performance Results

| Task | Result | Time Complexity |
|---|---|---|
| Linear Search | Found order 150000 in 5,773,300 ns | O(n) |
| Binary Search | Found the same order in 15,200 ns | O(log n) |
| Bubble Sort | Sorted 10,000 records in 284,778,900 ns | O(n²) |
| Merge Sort | Sorted 10,000 records in 7,395,600 ns | O(n log n) |
| Built-in Sort | Sorted 10,000 records in 7,192,400 ns | O(n log n) |
| Duplicate Detection | Detected 172 duplicate orders | O(n) expected |
| Stack and Queue | Custom and built-in implementations worked correctly | O(1) per operation |
| Refund Calculation | Greedy: 3 coins; DP: 2 coins | DP: O(A × d) |

**Note:**  
- `n` is the number of records.
- `A` is the refund amount.
- `d` is the number of coin denominations.
- The search benchmark excludes the time required to sort the records before binary search.
- Duplicate detection has O(n) expected time with hashing, assuming efficient grouping.

## Key Observations

1. **Searching:** Binary search was much faster than linear search in this run. Its sorting time was excluded from the benchmark.

2. **Sorting:** Built-in sort was the fastest, followed closely by merge sort. Bubble sort was significantly slower.

3. **Duplicate Detection:** The program identified 172 duplicate orders using a hash-based approach.

4. **Stack and Queue:** Both custom and built-in implementations produced the expected undo and dispatch results.

5. **Greedy vs DP:** For amount 6 with denominations `{1, 3, 4}`, greedy used 3 coins (4 + 1 + 1), while dynamic programming used only 2 coins (3 + 3).

## Conclusion

The performance results demonstrate that choosing the right algorithm and data structure can significantly improve the efficiency of order processing. Binary search, merge sort, hashing, stacks, queues, and dynamic programming each solve different processing problems efficiently.
