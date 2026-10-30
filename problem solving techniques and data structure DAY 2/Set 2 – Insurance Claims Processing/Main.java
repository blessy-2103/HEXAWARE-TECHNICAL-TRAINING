
import java.util.*;

public class Main {

    // ---------------- TASK 1: CLAIM ID BST ----------------

    static class Node {
        int id;
        Node left, right;

        Node(int id) {
            this.id = id;
        }
    }

    static Node insert(Node root, int id) {
        if (root == null) {
            return new Node(id);
        }

        if (id < root.id) {
            root.left = insert(root.left, id);
        } else if (id > root.id) {
            root.right = insert(root.right, id);
        }

        return root;
    }

    static boolean search(Node root, int id) {
        if (root == null) {
            return false;
        }

        if (root.id == id) {
            return true;
        }

        if (id < root.id) {
            return search(root.left, id);
        }

        return search(root.right, id);
    }

    static void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.id + " ");
            inorder(root.right);
        }
    }

    static void preorder(Node root) {
        if (root != null) {
            System.out.print(root.id + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    static void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.id + " ");
        }
    }


    // ---------------- TASK 2: DUPLICATE CLAIM CHECK ----------------

    static String makeClaimKey(String policy, String date) {
        // Ignore letter case and spaces in the policy number
        String cleanPolicy = policy.replaceAll("\\s+", "")
                                   .toUpperCase();

        String cleanDate = date.trim();

        return cleanPolicy + "|" + cleanDate;
    }

    static void checkDuplicates(String[][] claims) {
        Set<String> claimSet = new HashSet<>();

        if (claims.length == 0) {
            System.out.println("No claims available.");
            return;
        }

        int duplicates = 0;

        for (String[] claim : claims) {
            String key = makeClaimKey(claim[0], claim[1]);

            if (claimSet.contains(key)) {
                System.out.println("Duplicate claim: "
                        + claim[0] + " | " + claim[1]);
                duplicates++;
            } else {
                claimSet.add(key);
            }
        }

        System.out.println("Total duplicate claims: " + duplicates);
    }


    // ---------------- TASK 3: APPROVAL GRAPH ----------------

    static class Graph {
        Map<String, List<String>> graph = new LinkedHashMap<>();

        void addStage(String stage) {
            graph.putIfAbsent(stage, new ArrayList<>());
        }

        void addPath(String from, String to) {
            addStage(from);
            addStage(to);
            graph.get(from).add(to);
        }

        // BFS finds the shortest path in an unweighted graph
        List<String> shortestPath(String start, String end) {
            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                return new ArrayList<>();
            }

            Queue<String> queue = new LinkedList<>();
            Map<String, String> parent = new HashMap<>();
            Set<String> visited = new HashSet<>();

            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty()) {
                String current = queue.poll();

                if (current.equals(end)) {
                    break;
                }

                for (String next : graph.get(current)) {
                    if (!visited.contains(next)) {
                        visited.add(next);
                        parent.put(next, current);
                        queue.add(next);
                    }
                }
            }

            if (!visited.contains(end)) {
                return new ArrayList<>();
            }

            List<String> path = new ArrayList<>();
            String current = end;

            while (current != null) {
                path.add(current);
                current = parent.get(current);
            }

            Collections.reverse(path);
            return path;
        }

        // DFS cycle detection for a directed graph
        boolean dfsCycle(String node, Map<String, Integer> state) {
            state.put(node, 1); // Visiting

            for (String next : graph.get(node)) {
                if (state.getOrDefault(next, 0) == 1) {
                    return true;
                }

                if (state.getOrDefault(next, 0) == 0) {
                    if (dfsCycle(next, state)) {
                        return true;
                    }
                }
            }

            state.put(node, 2); // Completed
            return false;
        }

        boolean hasCycle() {
            Map<String, Integer> state = new HashMap<>();

            for (String stage : graph.keySet()) {
                if (state.getOrDefault(stage, 0) == 0) {
                    if (dfsCycle(stage, state)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }


    // ---------------- TASK 4: CLAIM SPIKE ANALYSIS ----------------

    static void longestClaimRun(int[] dailyClaims) {
        if (dailyClaims.length == 0) {
            System.out.println("No daily claim data available.");
            return;
        }

        int currentRun = 0;
        int longestRun = 0;
        int startDay = -1;
        int bestStartDay = -1;

        for (int i = 0; i < dailyClaims.length; i++) {

            if (dailyClaims[i] > 100) {
                if (currentRun == 0) {
                    startDay = i + 1;
                }

                currentRun++;

                if (currentRun > longestRun) {
                    longestRun = currentRun;
                    bestStartDay = startDay;
                }
            } else {
                currentRun = 0;
            }
        }

        if (longestRun == 0) {
            System.out.println("No day exceeded 100 claims.");
        } else {
            System.out.println("Longest run: " + longestRun + " days");
            System.out.println("From day " + bestStartDay
                    + " to day " + (bestStartDay + longestRun - 1));
        }
    }


    // ---------------- TASK 5: MERGE REGIONAL CLAIM LISTS ----------------

    static void mergeClaims(int[] officeA, int[] officeB) {
        int i = 0;
        int j = 0;

        System.out.println("Merged claim IDs:");

        while (i < officeA.length && j < officeB.length) {
            if (officeA[i] <= officeB[j]) {
                System.out.print(officeA[i] + " ");
                i++;
            } else {
                System.out.print(officeB[j] + " ");
                j++;
            }
        }

        while (i < officeA.length) {
            System.out.print(officeA[i] + " ");
            i++;
        }

        while (j < officeB.length) {
            System.out.print(officeB[j] + " ");
            j++;
        }

        System.out.println();
    }


    // ---------------- MAIN METHOD ----------------

    public static void main(String[] args) {

        // TASK 1
        System.out.println("===== TASK 1: CLAIM ID BST =====");

        int[] claimIds = {50, 30, 70, 20, 40, 60, 80};

        Node root = null;

        for (int id : claimIds) {
            root = insert(root, id);
        }

        System.out.print("Inorder: ");
        inorder(root);

        System.out.print("\nPreorder: ");
        preorder(root);

        System.out.print("\nPostorder: ");
        postorder(root);

        System.out.println("\nSearch claim 60: " + search(root, 60));
        System.out.println("Search claim 90: " + search(root, 90));

        Node emptyRoot = null;
        System.out.println("Search in empty tree: "
                + search(emptyRoot, 10));


        // TASK 2
        System.out.println("\n===== TASK 2: DUPLICATE CLAIMS =====");

        String[][] claims = {
            {"POL 1001", "2026-10-20"},
            {"POL 1002", "2026-10-21"},
            {"pol1001", "2026-10-20"},
            {"POL1003", "2026-10-22"},
            {"POL 1002", "2026-10-21"}
        };

        checkDuplicates(claims);

        System.out.println("Empty input test:");
        checkDuplicates(new String[0][0]);


        // TASK 3
        System.out.println("\n===== TASK 3: CLAIM APPROVAL GRAPH =====");

        Graph approval = new Graph();

        approval.addPath("Registration", "Assessment");
        approval.addPath("Registration", "Surveyor");
        approval.addPath("Assessment", "Surveyor");
        approval.addPath("Assessment", "Settlement");
        approval.addPath("Surveyor", "Settlement");

        System.out.println("Shortest path:");
        System.out.println(approval.shortestPath(
                "Registration", "Settlement"));

        System.out.println("Circular dependency: "
                + approval.hasCycle());

        // Add a path that creates a loop
        approval.addPath("Settlement", "Assessment");

        System.out.println("After adding a loop: "
                + approval.hasCycle());


        // TASK 4
        System.out.println("\n===== TASK 4: CLAIM SPIKE ANALYSIS =====");

        int[] dailyClaims = {
            80, 120, 130, 150, 90,
            110, 115, 105, 70, 140
        };

        longestClaimRun(dailyClaims);

        System.out.println("Empty input test:");
        longestClaimRun(new int[0]);


        // TASK 5
        System.out.println("\n===== TASK 5: MERGE REGIONAL CLAIMS =====");

        int[] officeA = {101, 105, 110, 120};
        int[] officeB = {102, 105, 115, 125};

        mergeClaims(officeA, officeB);

        System.out.println("Empty list test:");
        mergeClaims(new int[0], new int[0]);
    }
}
