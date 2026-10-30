# E-Commerce Catalogue and Traffic Analysis – Decision Table

| Problem | Data Structure / Pattern Used | Reason for Choosing It |
|---|---|---|
| Inserting and searching product categories | Binary Search Tree (BST) | I used a BST to store categories in an ordered way. It makes it easier to search for a particular category. |
| Displaying product categories | Tree Traversals | I used inorder, preorder and postorder to see how the categories are arranged in different ways. |
| Finding a product using its SKU | Hash Table with Chaining | I used chaining to handle collisions when two SKUs get the same index. It also helps in finding products quickly. |
| Comparing SKU search performance | Java HashMap | I used the built-in HashMap to compare its lookup time with my own hash table implementation. |
| Representing microservice dependencies | Graph | I used a graph because each service may depend on another service before it can be deployed. |
| Finding the deployment order | BFS and DFS | I used BFS and DFS to explore the service dependencies and find a valid deployment order. |
| Checking circular dependencies | Cycle Detection | This helps me find whether the services depend on each other in a loop, which can prevent proper deployment. |
| Finding the busiest 3-hour period | Sliding Window Technique | Instead of adding the requests again for every three-hour period, I update the total by removing the previous hour and adding the next one. |
| Finding gift-card value pairs | Two Pointers Technique | Since the order values are already sorted, I used two pointers to find matching pairs without checking every possible combination. |

## Conclusion

While working on this task, I understood that choosing the right data structure can make a program easier and faster. Each technique was selected based on the problem, whether it was searching for products, handling service dependencies or analysing customer traffic.
