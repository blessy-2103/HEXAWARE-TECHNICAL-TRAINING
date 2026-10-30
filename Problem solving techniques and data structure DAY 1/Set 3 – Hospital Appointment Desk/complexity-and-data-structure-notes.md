# Complexity and Data Structure – Short Notes

## 1. Searching
Linear search checks patient records one by one, so it takes O(n) time. Binary search is faster with sorted patient IDs because it reduces the search area by half in every step, taking O(log n) time.

## 2. Sorting
Selection sort takes O(n²) time because it compares elements repeatedly to find the next appointment. Quick sort takes O(n log n) time on average, so it is more suitable for larger appointment lists.

## 3. Duplicate Detection
I used a HashSet to check whether the same patient is booked for the same time slot. It provides O(1) average lookup and insertion, making it faster than checking every record using a list.

## 4. Queue and Stack
- **Queue:** I used an array-based queue for walk-in patients because patients should be served in the order they arrive (FIFO).
- **Stack:** I used an array-based stack to store recently viewed patients because the latest viewed patient should appear first (LIFO).

Both support insertion and deletion in O(1) time.

## 5. Dynamic Programming
Dynamic programming is used to find the maximum number of appointments a doctor can attend without overlapping. It stores previous results to avoid repeating calculations. The implementation takes O(n log n) time, including sorting the appointments.

## Conclusion
Each data structure and algorithm was selected based on the task. Using binary search, hashing, queues, stacks, and dynamic programming makes appointment management easier and more efficient.
