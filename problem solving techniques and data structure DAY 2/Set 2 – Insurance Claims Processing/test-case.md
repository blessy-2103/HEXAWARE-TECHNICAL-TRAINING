# Insurance Claim Processing System – Test Cases

## 1. Binary Search Tree – Claim ID

| Test Case | Input | Expected Output | Purpose |
|---|---|---|---|
| TC01 | 50, 30, 70, 20, 40, 60, 80 | Inorder: 20 30 40 50 60 70 80 | Check whether claim IDs are stored correctly. |
| TC02 | Search for 60 | `true` | Check whether an existing claim can be found. |
| TC03 | Search for 90 | `false` | Check whether the system handles a missing claim ID. |
| TC04 | Empty BST, search for 10 | `false` | Check searching when no claims are stored. |
| TC05 | Insert duplicate ID 50 | Tree remains unchanged | Check whether duplicate claim IDs are ignored. |

## 2. Duplicate Claim Detection – HashSet

| Test Case | Policy Number | Incident Date | Expected Result | Purpose |
|---|---|---|---|---|
| TC06 | POL 1001, 2026-10-20 | First entry | New claim | Check a normal claim entry. |
| TC07 | pol1001, 2026-10-20 | Duplicate | Detect duplicate | Check whether letter case is ignored. |
| TC08 | POL1001, 2026-10-20 | Duplicate | Detect duplicate | Check whether spaces are ignored. |
| TC09 | POL 1002, 2026-10-21 | First entry | New claim | Check a different policy and date. |
| TC10 | Empty claim list | No records | No claims available | Check empty input handling. |

## 3. Claim Approval – Graph

| Test Case | Input | Expected Output | Purpose |
|---|---|---|---|
| TC11 | Registration → Assessment → Settlement | Shortest path found | Check BFS path finding. |
| TC12 | Registration → Surveyor → Settlement | Valid path | Check another possible route. |
| TC13 | Settlement → Assessment added | Cycle detected | Check circular dependency detection. |
| TC14 | Unknown starting stage | Empty path | Check when a stage does not exist. |
| TC15 | Graph with no stages | No path and no cycle | Check empty graph handling. |

## 4. Claim Spike Analysis – Sliding Window

| Test Case | Daily Claim Counts | Expected Result | Purpose |
|---|---|---|---|
| TC16 | 80, 120, 130, 150, 90, 110, 115, 105, 70, 140 | Longest run: 3 days (Day 6–8) | Check consecutive days above 100. |
| TC17 | 50, 60, 70, 80 | No day exceeded 100 | Check when no day crosses the limit. |
| TC18 | 120, 130, 140, 150 | Longest run: 4 days | Check when all days exceed 100. |
| TC19 | Empty array | No daily claim data available | Check empty input handling. |
| TC20 | 100, 100, 100 | No day exceeded 100 | Check that exactly 100 does not count. |

## 5. Merge Regional Claim Lists – Two Pointers

| Test Case | Office A | Office B | Expected Output | Purpose |
|---|---|---|---|---|
| TC21 | 101, 105, 110, 120 | 102, 105, 115, 125 | 101, 102, 105, 105, 110, 115, 120, 125 | Check normal merging. |
| TC22 | Empty list | 102, 105, 110 | 102, 105, 110 | Check when the first list is empty. |
| TC23 | 101, 105, 110 | Empty list | 101, 105, 110 | Check when the second list is empty. |
| TC24 | Empty list | Empty list | Empty output | Check when both lists are empty. |
| TC25 | 101, 101, 105 | 101, 105, 105 | 101, 101, 101, 105, 105, 105 | Check whether repeated claim IDs are retained. |

## Conclusion

I tested the program with normal inputs, empty inputs and duplicate values to check how it behaves in different situations. These test cases helped me verify that searching, duplicate detection, graph operations, claim-spike analysis and merging work as expected.

The duplicate claim test checks policy numbers without considering letter case or extra spaces. The merge test keeps repeated claim IDs because the task is to merge both regional lists, not remove duplicates.
