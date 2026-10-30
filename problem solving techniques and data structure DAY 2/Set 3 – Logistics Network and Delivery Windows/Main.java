
import java.util.*;

// TASK 1: Binary Search Tree
class BST {
    class Node {
        int code;
        Node left, right;

        Node(int code) {
            this.code = code;
        }
    }

    Node root;

    Node insert(Node root, int code) {
        if (root == null)
            return new Node(code);

        if (code < root.code)
            root.left = insert(root.left, code);
        else if (code > root.code)
            root.right = insert(root.right, code);

        return root;
    }

    boolean search(Node root, int code) {
        if (root == null)
            return false;

        if (root.code == code)
            return true;

        if (code < root.code)
            return search(root.left, code);

        return search(root.right, code);
    }

    int height(Node root) {
        if (root == null)
            return 0;

        return 1 + Math.max(height(root.left), height(root.right));
    }

    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.code + " ");
            inorder(root.right);
        }
    }
}

// TASK 2: Duplicate Parcel Scans
class ParcelTracker {
    HashMap<String, String> tracking = new HashMap<>();
    HashSet<String> scanned = new HashSet<>();

    void addParcel(String trackingNo, String hub) {
        tracking.put(trackingNo, hub);
    }

    void scanParcel(String trackingNo, String hub) {
        String key = trackingNo.toUpperCase() + "|" + hub.toUpperCase();

        if (scanned.contains(key)) {
            System.out.println("Duplicate scan: " + trackingNo
                    + " at " + hub);
        } else {
            scanned.add(key);
            tracking.put(trackingNo, hub);
            System.out.println("Scan recorded: " + trackingNo
                    + " at " + hub);
        }
    }
}

// TASK 3: Graph
class HubGraph {
    HashMap<String, ArrayList<String>> graph = new HashMap<>();

    void addHub(String hub) {
        graph.putIfAbsent(hub, new ArrayList<>());
    }

    void addRoute(String from, String to) {
        addHub(from);
        addHub(to);
        graph.get(from).add(to);
    }

    // BFS: Fewest hops
    void bfs(String start, String destination) {
        if (!graph.containsKey(start) || !graph.containsKey(destination)) {
            System.out.println("Hub not found.");
            return;
        }

        Queue<String> queue = new LinkedList<>();
        HashMap<String, String> parent = new HashMap<>();

        queue.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(destination))
                break;

            for (String next : graph.get(current)) {
                if (!parent.containsKey(next)) {
                    parent.put(next, current);
                    queue.add(next);
                }
            }
        }

        if (!parent.containsKey(destination)) {
            System.out.println("No route found.");
            return;
        }

        ArrayList<String> path = new ArrayList<>();
        String current = destination;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        System.out.println("Shortest route: " + path);
        System.out.println("Hops: " + (path.size() - 1));
    }

    // DFS: Reachable hubs
    void dfs(String start) {
        if (!graph.containsKey(start)) {
            System.out.println("Hub not found.");
            return;
        }

        HashSet<String> visited = new HashSet<>();
        System.out.print("Reachable hubs: ");
        dfsHelper(start, visited);
        System.out.println();
    }

    void dfsHelper(String hub, HashSet<String> visited) {
        visited.add(hub);
        System.out.print(hub + " ");

        for (String next : graph.get(hub)) {
            if (!visited.contains(next)) {
                dfsHelper(next, visited);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {

        // TASK 1
        System.out.println("===== TASK 1: HUB BST =====");

        BST tree = new BST();

        int[] hubs = {50, 30, 70, 20, 40, 60, 80};

        for (int code : hubs)
            tree.root = tree.insert(tree.root, code);

        System.out.print("Inorder: ");
        tree.inorder(tree.root);

        System.out.println("\nHeight: " + tree.height(tree.root));
        System.out.println("Search 60: " +
                tree.search(tree.root, 60));

        BST sortedTree = new BST();
        int[] sorted = {10, 20, 30, 40, 50};

        for (int code : sorted)
            sortedTree.root = sortedTree.insert(sortedTree.root, code);

        System.out.println("Height of sorted BST: "
                + sortedTree.height(sortedTree.root));

        // TASK 2
        System.out.println("\n===== TASK 2: PARCEL SCANS =====");

        ParcelTracker tracker = new ParcelTracker();

        tracker.scanParcel("PKG101", "HUB-A");
        tracker.scanParcel("PKG102", "HUB-B");
        tracker.scanParcel("PKG101", "HUB-A");
        tracker.scanParcel("PKG101", "HUB-C");

        // TASK 3
        System.out.println("\n===== TASK 3: HUB ROUTING =====");

        HubGraph network = new HubGraph();

        String[] names = {
            "HUB-A", "HUB-B", "HUB-C", "HUB-D", "HUB-E",
            "HUB-F", "HUB-G", "HUB-H", "HUB-I", "HUB-J"
        };

        for (String hub : names)
            network.addHub(hub);

        network.addRoute("HUB-A", "HUB-B");
        network.addRoute("HUB-A", "HUB-C");
        network.addRoute("HUB-B", "HUB-D");
        network.addRoute("HUB-C", "HUB-E");
        network.addRoute("HUB-D", "HUB-F");
        network.addRoute("HUB-E", "HUB-F");
        network.addRoute("HUB-F", "HUB-G");
        network.addRoute("HUB-G", "HUB-H");
        network.addRoute("HUB-I", "HUB-J");

        System.out.println("Adjacency List:");
        for (String hub : network.graph.keySet()) {
            System.out.println(hub + " -> " + network.graph.get(hub));
        }

        network.bfs("HUB-A", "HUB-H");
        network.dfs("HUB-A");

        // TASK 4
        System.out.println("\n===== TASK 4: SLIDING WINDOW =====");

        int[] parcels = {
            100, 120, 150, 180, 200, 250,
            300, 350, 400, 280, 220, 180
        };

        int window = 4;
        int sum = 0;

        for (int i = 0; i < window; i++)
            sum += parcels[i];

        int maxSum = sum;
        int startIndex = 0;

        for (int i = window; i < parcels.length; i++) {
            sum = sum - parcels[i - window] + parcels[i];

            if (sum > maxSum) {
                maxSum = sum;
                startIndex = i - window + 1;
            }
        }

        double average = (double) maxSum / window;

        System.out.println("Best 4-hour window: Hour "
                + (startIndex + 1) + " to Hour "
                + (startIndex + window));

        System.out.println("Highest average: " + average);

        // TASK 5
        System.out.println("\n===== TASK 5: PALINDROME ROUTE =====");

        String route = "HUB-A -> HUB-B -> HUB-A";

        String cleaned = route.replaceAll("[^a-zA-Z0-9]", "")
                               .toUpperCase();

        int left = 0;
        int right = cleaned.length() - 1;
        boolean palindrome = true;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                palindrome = false;
                break;
            }

            left++;
            right--;
        }

        System.out.println("Route: " + route);
        System.out.println("Is palindrome: " + palindrome);
    }
}
