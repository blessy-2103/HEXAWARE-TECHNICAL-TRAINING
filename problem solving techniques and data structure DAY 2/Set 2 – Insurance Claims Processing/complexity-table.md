# Insurance Claim Processing System – Complexity Analysis

| Task | Algorithm / Data Structure Used | Time Complexity | Space Complexity | Reason |
|---|---|---|---|---|
| Insert claim IDs | Binary Search Tree (BST) | Average: O(log n), Worst: O(n) | O(h) | Each new claim ID is placed in the correct position based on its value. |
| Search claim IDs | Binary Search Tree (BST) | Average: O(log n), Worst: O(n) | O(h) | It compares the claim ID and moves left or right until the required ID is found. |
| Display claim IDs | Inorder, Preorder and Postorder | O(n) | O(h) | Each node is visited once during traversal. |
| Detect duplicate claims | HashSet | Average: O(n) overall, Worst: O(n²) overall | O(n) | It checks whether a normalized policy number and incident date already exist. |
| Find shortest approval path | BFS | O(V + E) | O(V) | It visits the graph level by level to find the shortest path. |
| Detect circular dependencies | DFS | O(V + E) | O(V) | It checks whether a stage leads back to a stage that is currently being visited. |
| Find longest consecutive claim spike | Sliding Window / Linear Scan | O(n) | O(1) | It checks each day's claim count once and keeps track of the longest run. |
| Merge regional claim lists | Two Pointers | O(n + m) | O(1) auxiliary space | It compares values from both sorted lists and adds the smaller one to the output. |

## Complexity Explanation

- **n** – Number of claim records.
- **h** – Height of the Binary Search Tree.
- **V** – Number of approval stages (vertices).
- **E** – Number of connections between approval stages (edges).
- **m** – Number of claims in the second regional list.

## Conclusion

From this analysis, I understood that the time taken by an algorithm depends on the number of records and the way data is stored.

BST searching is efficient when the tree is balanced, while hashing helps in checking duplicate claims quickly. BFS and DFS are useful for handling approval dependencies. Sliding Window and Two Pointers reduce unnecessary repeated operations.

Choosing the right approach helps the claim processing system handle records more efficiently.
