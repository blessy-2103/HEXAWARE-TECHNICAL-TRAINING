# E-Commerce Catalogue and Traffic Analysis – Decision Table

| Problem | Data Structure / Algorithm Used | Reason for Choosing It |
|---|---|---|
| Product category insertion and search | Binary Search Tree (BST) | It stores categories in an ordered way and makes searching easier. |
| Displaying product categories | Inorder, Preorder and Postorder Traversal | These traversals help display the categories in different orders. |
| SKU lookup | Hash Table with Chaining | It helps find SKUs quickly. Chaining also handles collisions when multiple SKUs get the same index. |
| Comparing SKU lookup performance | Java HashMap | I used the built-in HashMap to compare its lookup performance with my custom hash table. |
| Microservice deployment dependencies | Graph | A graph represents the relationship between services and their dependencies. |
| Finding deployment order | BFS and DFS | Both methods help find a valid order for deploying services. |
| Detecting circular dependencies | Cycle Detection | It identifies whether services depend on each other in a loop. |
| Finding peak traffic | Sliding Window Technique | It finds the busiest continuous 3-hour period without calculating every window from the beginning. |
| Finding gift-card pairs | Two Pointers Technique | Since the order values are sorted, two pointers help find matching pairs with fewer comparisons. |

## Conclusion

I selected each data structure and algorithm based on the problem it needed to solve. This helped me understand how BST, hashing, graphs, sliding windows and two pointers can be used in real-world e-commerce applications.
