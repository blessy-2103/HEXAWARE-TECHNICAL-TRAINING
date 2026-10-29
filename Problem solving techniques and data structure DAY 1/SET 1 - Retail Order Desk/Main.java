
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Order> orders = loadOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders loaded. Check orders.csv.");
            return;
        }

        System.out.println("\n===== TASK 1: SEARCH =====");
        benchmarkSearch(orders);

        System.out.println("\n===== TASK 2: SORTING =====");
        benchmarkSorting(orders);

        System.out.println("\n===== TASK 3: DUPLICATES =====");
        findDuplicates(orders);

        System.out.println("\n===== TASK 4: STACK AND QUEUE =====");
        demonstrateStacksAndQueues();

        System.out.println("\n===== TASK 5: REFUND =====");

        int[] coins = {1, 3, 4};
        int refundAmount = 6;

        System.out.println("Refund amount: " + refundAmount);
        System.out.println("Greedy coins: "
                + greedyRefund(refundAmount, coins));
        System.out.println("DP coins: "
                + dpRefund(refundAmount, coins));
    }

    // Load orders from CSV
    static ArrayList<Order> loadOrders() throws IOException {

        ArrayList<Order> orders = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader("orders.csv"))) {

            br.readLine(); // Skip header

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length < 4) {
                    System.out.println("Invalid row: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(data[0].trim());
                    String customer = data[1].trim();
                    double amount = Double.parseDouble(data[2].trim());
                    String date = data[3].trim();

                    orders.add(new Order(
                            id, customer, amount, date));

                } catch (NumberFormatException e) {
                    System.out.println("Invalid row: " + line);
                }
            }
        }

        System.out.println("Orders loaded: " + orders.size());

        if (!orders.isEmpty()) {
            System.out.println("First order: " + orders.get(0));
        }

        return orders;
    }

    // TASK 1: Linear Search
    static Order linearSearch(ArrayList<Order> orders, int id) {

        for (Order order : orders) {
            if (order.orderId == id) {
                return order;
            }
        }

        return null;
    }

    // TASK 1: Binary Search
    // The list must be sorted by orderId.
    static Order binarySearch(ArrayList<Order> orders, int id) {

        int low = 0;
        int high = orders.size() - 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;
            int currentId = orders.get(mid).orderId;

            if (currentId == id) {
                return orders.get(mid);
            } else if (currentId < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return null;
    }

    static void benchmarkSearch(ArrayList<Order> orders) {

        int target = orders.get(orders.size() - 1).orderId;

        long start = System.nanoTime();
        Order result1 = linearSearch(orders, target);
        long linearTime = System.nanoTime() - start;

        ArrayList<Order> sorted = new ArrayList<>(orders);
        sorted.sort(Comparator.comparingInt(o -> o.orderId));

        start = System.nanoTime();
        Order result2 = binarySearch(sorted, target);
        long binaryTime = System.nanoTime() - start;

        System.out.println("Target ID: " + target);
        System.out.println("Linear result: " + result1);
        System.out.println("Linear time: " + linearTime + " ns");
        System.out.println("Binary result: " + result2);
        System.out.println("Binary time: " + binaryTime + " ns");
        System.out.println("Binary-search sorting time excluded.");
    }

    // TASK 2: Bubble Sort, descending amount
    static void bubbleSort(ArrayList<Order> orders) {

        int n = orders.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - 1 - i; j++) {

                if (orders.get(j).amount
                        < orders.get(j + 1).amount) {

                    Order temp = orders.get(j);

                    orders.set(j, orders.get(j + 1));
                    orders.set(j + 1, temp);
                }
            }
        }
    }

    // TASK 2: Merge Sort, descending amount
    static void mergeSort(ArrayList<Order> orders) {

        if (orders.size() <= 1) {
            return;
        }

        int mid = orders.size() / 2;

        ArrayList<Order> left = new ArrayList<>(
                orders.subList(0, mid));

        ArrayList<Order> right = new ArrayList<>(
                orders.subList(mid, orders.size()));

        mergeSort(left);
        mergeSort(right);

        merge(orders, left, right);
    }

    static void merge(ArrayList<Order> result,
                      ArrayList<Order> left,
                      ArrayList<Order> right) {

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.size() && j < right.size()) {

            if (left.get(i).amount >= right.get(j).amount) {
                result.set(k++, left.get(i++));
            } else {
                result.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            result.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            result.set(k++, right.get(j++));
        }
    }

    // TASK 2: Compare sorting algorithms on 10,000 records
    static void benchmarkSorting(ArrayList<Order> orders) {

        int count = Math.min(10000, orders.size());

        ArrayList<Order> original = new ArrayList<>(
                orders.subList(0, count));

        ArrayList<Order> bubble = new ArrayList<>(original);
        ArrayList<Order> merge = new ArrayList<>(original);
        ArrayList<Order> builtIn = new ArrayList<>(original);

        long start = System.nanoTime();
        bubbleSort(bubble);
        long bubbleTime = System.nanoTime() - start;

        start = System.nanoTime();
        mergeSort(merge);
        long mergeTime = System.nanoTime() - start;

        start = System.nanoTime();
        builtIn.sort(
                (a, b) -> Double.compare(b.amount, a.amount));
        long builtInTime = System.nanoTime() - start;

        System.out.println("Records sorted: " + count);
        System.out.println("Bubble Sort: " + bubbleTime + " ns");
        System.out.println("Merge Sort: " + mergeTime + " ns");
        System.out.println("Built-in Sort: " + builtInTime + " ns");

        System.out.println("Top 5 orders:");

        for (int i = 0; i < Math.min(5, count); i++) {
            System.out.println(builtIn.get(i));
        }

        System.out.println("Bubble and Merge results correct: "
                + (sameAmounts(bubble, builtIn)
                && sameAmounts(merge, builtIn)));
    }

    static boolean sameAmounts(ArrayList<Order> a,
                               ArrayList<Order> b) {

        if (a.size() != b.size()) {
            return false;
        }

        for (int i = 0; i < a.size(); i++) {
            if (Double.compare(
                    a.get(i).amount, b.get(i).amount) != 0) {
                return false;
            }
        }

        return true;
    }

    // TASK 3: Find duplicate orders using HashSet
    static void findDuplicates(ArrayList<Order> orders) {

        Set<String> seen = new HashSet<>();
        int duplicateCount = 0;

        for (Order order : orders) {

            if (order.createdAt.length() < 16) {
                continue;
            }

            String minute = order.createdAt.substring(0, 16);
            long cents = Math.round(order.amount * 100);

            String key = order.customerId
                    + "|" + cents
                    + "|" + minute;

            if (!seen.add(key)) {

                System.out.println("Duplicate order: "
                        + order.orderId);

                duplicateCount++;
            }
        }

        System.out.println("Total duplicates: " + duplicateCount);
    }

    // TASK 4: Custom Stack
    static class MyStack {

        String[] items = new String[100];
        int top = -1;

        void push(String value) {
            if (top == items.length - 1) {
                System.out.println("Stack full");
                return;
            }

            items[++top] = value;
        }

        String pop() {
            if (top == -1) {
                return null;
            }

            return items[top--];
        }

        String peek() {
            if (top == -1) {
                return null;
            }

            return items[top];
        }
    }

    // TASK 4: Custom circular Queue
    static class MyQueue {

        String[] items = new String[100];
        int front = 0;
        int rear = -1;
        int size = 0;

        void enqueue(String value) {

            if (size == items.length) {
                System.out.println("Queue full");
                return;
            }

            rear = (rear + 1) % items.length;
            items[rear] = value;
            size++;
        }

        String dequeue() {

            if (size == 0) {
                return null;
            }

            String value = items[front];
            front = (front + 1) % items.length;
            size--;

            return value;
        }
    }

    // Compare custom and built-in Stack and Queue
    static void demonstrateStacksAndQueues() {

        System.out.println("-- Custom Stack --");

        MyStack stack = new MyStack();
        stack.push("Order 1001 edited");
        stack.push("Order 1002 edited");

        System.out.println("Undo: " + stack.pop());

        System.out.println("-- Built-in Stack --");

        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("Order 1001 edited");
        undoStack.push("Order 1002 edited");

        System.out.println("Undo: " + undoStack.pop());

        System.out.println("-- Custom Queue --");

        MyQueue queue = new MyQueue();
        queue.enqueue("Order 1001");
        queue.enqueue("Order 1002");

        System.out.println("Dispatch: " + queue.dequeue());

        System.out.println("-- Built-in Queue --");

        Queue<String> dispatchQueue = new ArrayDeque<>();
        dispatchQueue.offer("Order 1001");
        dispatchQueue.offer("Order 1002");

        System.out.println("Dispatch: " + dispatchQueue.poll());
    }

    // TASK 5: Greedy refund
    static int greedyRefund(int amount, int[] coins) {

        int[] sortedCoins = Arrays.copyOf(coins, coins.length);
        Arrays.sort(sortedCoins);

        int count = 0;

        for (int i = sortedCoins.length - 1; i >= 0; i--) {

            int coin = sortedCoins[i];

            if (coin <= 0) {
                continue;
            }

            count += amount / coin;
            amount %= coin;
        }

        return amount == 0 ? count : -1;
    }

    // TASK 5: Dynamic Programming refund
    static int dpRefund(int amount, int[] coins) {

        if (amount < 0) {
            return -1;
        }

        int[] dp = new int[amount + 1];

        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int value = 1; value <= amount; value++) {

            for (int coin : coins) {

                if (coin > 0 && coin <= value) {

                    dp[value] = Math.min(
                            dp[value],
                            dp[value - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }
}
