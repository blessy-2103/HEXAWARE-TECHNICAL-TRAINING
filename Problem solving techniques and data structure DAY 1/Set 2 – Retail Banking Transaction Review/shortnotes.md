# Retail Bank Transaction Processing – Short Notes

## 1. Searching
Linear search checks transaction IDs one by one. Binary search repeatedly divides a sorted list into halves. Binary search is more efficient for large sorted datasets.

## 2. Sorting
Insertion sort inserts each transaction into its correct position. Merge sort divides the statement into smaller parts and merges them in sorted order.

Both algorithms are stable, meaning transactions with the same timestamp retain their original relative order. Stability is important when transactions share the same posting time.

## 3. Duplicate Detection
A HashMap groups transactions using account number, amount, and merchant. Their timestamps are checked to identify repeated postings within 60 seconds.

## 4. Queue and Stack
- **Queue:** Follows FIFO (First In, First Out) and manages the manual-review backlog.
- **Stack:** Follows LIFO (Last In, First Out) and helps reverse the latest adjustment.

Both are implemented using arrays without library collections.

## 5. Divide and Conquer
The transaction amounts are divided into smaller groups to find the minimum and maximum values. Their results are combined.

This is compared with brute force, which scans every amount.

## Conclusion
The project demonstrates how searching, sorting, hashing, stacks, queues, and divide-and-conquer techniques help improve banking transaction processing.
