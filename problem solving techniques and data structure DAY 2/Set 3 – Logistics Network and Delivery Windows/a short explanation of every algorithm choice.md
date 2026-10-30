# Courier Hub Routing – Algorithm Choices and Explanations

## 1. Binary Search Tree (BST)
I used a BST to store hub codes and perform insertion and searching. Inorder traversal displays the hub codes in sorted order. I also checked the tree height to understand how sorted input can create an unbalanced tree and make searching slower.

## 2. HashMap and HashSet
I used a HashMap to store parcel tracking numbers and a HashSet to maintain the hubs where each parcel was scanned. This helps identify whether the same parcel was scanned more than once at the same hub.

## 3. Graph – BFS and DFS
I used an adjacency list to represent the direct routes between 10 hubs. BFS finds the route with the fewest hops between two hubs, while DFS visits all hubs reachable from the central depot.

## 4. Sliding Window
I used the Sliding Window technique to find the four-hour period with the highest average parcel count. Instead of calculating the total again for every window, I remove the previous hour's count and add the next hour's count.

## 5. Two Pointers
I used two pointers to check whether a delivery route is a palindrome. One pointer starts at the beginning and the other starts at the end. Both move towards the centre while comparing the hub codes after removing separators and ignoring case.

## Conclusion

Through this project, I understood how different data structures and algorithms solve practical courier management problems. Choosing the right approach helps reduce unnecessary operations and improves efficiency.
