
import java.util.*;

public class Main {

    // ---------------- TASK 1: BINARY SEARCH TREE ----------------

    static class CategoryNode {
        String name;
        CategoryNode left, right;

        CategoryNode(String name) {
            this.name = name;
        }
    }

    static CategoryNode insert(CategoryNode root, String name) {
        if (root == null) {
            return new CategoryNode(name);
        }

        if (name.compareToIgnoreCase(root.name) < 0) {
            root.left = insert(root.left, name);
        } else if (name.compareToIgnoreCase(root.name) > 0) {
            root.right = insert(root.right, name);
        }

        return root;
    }

    static boolean search(CategoryNode root, String name) {
        if (root == null) {
            return false;
        }

        if (root.name.equalsIgnoreCase(name)) {
            return true;
        }

        if (name.compareToIgnoreCase(root.name) < 0) {
            return search(root.left, name);
        }

        return search(root.right, name);
    }

    static void inorder(CategoryNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.name + " ");
            inorder(root.right);
        }
    }

    static void preorder(CategoryNode root) {
        if (root != null) {
            System.out.print(root.name + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    static void postorder(CategoryNode root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.name + " ");
        }
    }


    // ---------------- TASK 2: HASH TABLE WITH CHAINING ----------------

    static class SKUNode {
        String sku;
        SKUNode next;

        SKUNode(String sku) {
            this.sku = sku;
        }
    }

    static class MyHashTable {
        SKUNode[] table;
        int size;

        MyHashTable(int size) {
            this.size = size;
            table = new SKUNode[size];
        }

        // Simple hash function for demonstrating chaining
        int getIndex(String sku) {
            int sum = 0;

            for (int i = 0; i < sku.length(); i++) {
                sum += sku.charAt(i);
            }

            return sum % size;
        }

        void insert(String sku) {
            int index = getIndex(sku);

            SKUNode newNode = new SKUNode(sku);
            newNode.next = table[index];
            table[index] = newNode;
        }

        boolean search(String sku) {
            int index = getIndex(sku);
            SKUNode current = table[index];

            while (current != null) {
                if (current.sku.equals(sku)) {
                    return true;
                }
                current = current.next;
            }

            return false;
        }
    }


    // ---------------- TASK 3: GRAPH ----------------

    static class Graph {
        Map<String, List<String>> graph = new LinkedHashMap<>();

        void addService(String service) {
            graph.putIfAbsent(service, new ArrayList<>());
        }

        void addDependency(String from, String to) {
            addService(from);
            addService(to);
            graph.get(from).add(to);
        }

        // BFS-based topological sorting (Kahn's algorithm)
        List<String> bfsOrder() {
            Map<String, Integer> indegree = new HashMap<>();

            for (String service : graph.keySet()) {
                indegree.put(service, 0);
            }

            for (String service : graph.keySet()) {
                for (String next : graph.get(service)) {
                    indegree.put(next, indegree.get(next) + 1);
                }
            }

            Queue<String> queue = new LinkedList<>();

            for (String service : indegree.keySet()) {
                if (indegree.get(service) == 0) {
                    queue.add(service);
                }
            }

            List<String> order = new ArrayList<>();

            while (!queue.isEmpty()) {
                String current = queue.poll();
                order.add(current);

                for (String next : graph.get(current)) {
                    indegree.put(next, indegree.get(next) - 1);

                    if (indegree.get(next) == 0) {
                        queue.add(next);
                    }
                }
            }

            if (order.size() != graph.size()) {
                return new ArrayList<>(); // Cycle exists
            }

            return order;
        }

        // DFS-based topological sorting
        boolean dfs(String node, Map<String, Integer> state,
                    List<String> order) {

            state.put(node, 1); // Currently visiting

            for (String next : graph.get(node)) {
                if (state.getOrDefault(next, 0) == 1) {
                    return false; // Cycle found
                }

                if (state.getOrDefault(next, 0) == 0) {
                    if (!dfs(next, state, order)) {
                        return false;
                    }
                }
            }

            state.put(node, 2); // Completed
            order.add(node);
            return true;
        }

        List<String> dfsOrder() {
            Map<String, Integer> state = new HashMap<>();
            List<String> order = new ArrayList<>();

            for (String service : graph.keySet()) {
                if (state.getOrDefault(service, 0) == 0) {
                    if (!dfs(service, state, order)) {
                        return new ArrayList<>();
                    }
                }
            }

            Collections.reverse(order);
            return order;
        }

        boolean hasCycle() {
            return bfsOrder().size() != graph.size();
        }
    }


    // ---------------- TASK 4: SLIDING WINDOW ----------------

    static void busiestThreeHours(int[] requests) {
        int windowSum = 0;

        // Calculate the first 3-hour window
        for (int i = 0; i < 3; i++) {
            windowSum += requests[i];
        }

        int maxRequests = windowSum;
        int startHour = 0;

        // Move the window one hour at a time
        for (int i = 3; i < requests.length; i++) {
            windowSum = windowSum - requests[i - 3] + requests[i];

            if (windowSum > maxRequests) {
                maxRequests = windowSum;
                startHour = i - 2;
            }
        }

        System.out.println("Busiest 3-hour window: "
                + startHour + ":00 to " + (startHour + 3) + ":00");

        System.out.println("Total requests: " + maxRequests);
    }


    // ---------------- TASK 5: TWO POINTERS ----------------

    static void findGiftCardPairs(int[] values, int target) {
        int left = 0;
        int right = values.length - 1;
        boolean found = false;

        while (left < right) {
            int sum = values[left] + values[right];

            if (sum == target) {
                System.out.println(values[left] + " + "
                        + values[right] + " = " + target);
                found = true;

                int leftValue = values[left];
                int rightValue = values[right];

                while (left < right && values[left] == leftValue) {
                    left++;
                }

                while (left < right && values[right] == rightValue) {
                    right--;
                }
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        if (!found) {
            System.out.println("No matching pairs found.");
        }
    }


    // ---------------- MAIN METHOD ----------------

    public static void main(String[] args) {

        // TASK 1
        System.out.println("TASK 1: PRODUCT CATEGORIES");

        String[] categories = {
            "Electronics", "Clothing", "Books", "Furniture",
            "Beauty", "Sports", "Toys", "Groceries",
            "Footwear", "Jewellery", "Mobile", "Laptops",
            "Kitchen", "Gaming", "Watches"
        };

        CategoryNode root = null;

        for (String category : categories) {
            root = insert(root, category);
        }

        System.out.print("Inorder: ");
        inorder(root);

        System.out.print("\nPreorder: ");
        preorder(root);

        System.out.print("\nPostorder: ");
        postorder(root);

        System.out.println("\nSearch Electronics: "
                + search(root, "Electronics"));


        // TASK 2
        System.out.println("\n\nTASK 2: SKU LOOK-UP");

        MyHashTable customTable = new MyHashTable(10);

        // These two SKUs have the same character sum
        customTable.insert("SKU-100");
        customTable.insert("SKU-001");

        System.out.println("Collision index: "
                + customTable.getIndex("SKU-100"));

        System.out.println("First SKU found: "
                + customTable.search("SKU-100"));

        System.out.println("Second SKU found: "
                + customTable.search("SKU-001"));

        // Compare lookup time for 100,000 SKUs
        MyHashTable largeTable = new MyHashTable(200003);
        Map<String, Integer> builtInMap = new HashMap<>();

        String[] skuList = new String[100000];

        for (int i = 0; i < 100000; i++) {
            skuList[i] = String.format("SKU%06d", i);
            largeTable.insert(skuList[i]);
            builtInMap.put(skuList[i], i);
        }

        long start = System.nanoTime();

        for (String sku : skuList) {
            largeTable.search(sku);
        }

        long customTime = System.nanoTime() - start;

        start = System.nanoTime();

        for (String sku : skuList) {
            builtInMap.get(sku);
        }

        long mapTime = System.nanoTime() - start;

        System.out.println("Custom hash table lookup time: "
                + customTime + " ns");

        System.out.println("Built-in HashMap lookup time: "
                + mapTime + " ns");


        // TASK 3
        System.out.println("\nTASK 3: MICROSERVICE DEPENDENCIES");

        Graph services = new Graph();

        // Direction: service that must be deployed first -> dependent service
        services.addDependency("inventory", "pricing");
        services.addDependency("pricing", "cart");
        services.addDependency("payment", "cart");
        services.addDependency("cart", "notification");

        System.out.println("BFS deployment order: "
                + services.bfsOrder());

        System.out.println("DFS deployment order: "
                + services.dfsOrder());

        System.out.println("Circular dependency: "
                + services.hasCycle());

        // Add a dependency that creates a cycle
        services.addDependency("notification", "inventory");

        System.out.println("After adding circular dependency: "
                + services.hasCycle());


        // TASK 4
        System.out.println("\nTASK 4: PEAK TRAFFIC");

        int[] requests = {
            120, 150, 180, 200, 250, 300,
            450, 500, 550, 600, 580, 400,
            350, 300, 280, 250, 220, 200,
            180, 160, 140, 130, 120, 100
        };

        busiestThreeHours(requests);


        // TASK 5
        System.out.println("\nTASK 5: GIFT CARD PAIRS");

        int[] orderValues = {
            100, 150, 200, 250, 300, 350, 400, 450
        };

        int giftCardValue = 500;

        findGiftCardPairs(orderValues, giftCardValue);
    }
}
