import java.util.*;

public class Main {

    // TASK 1: BINARY SEARCH
    static int binarySearch(int[] a, int target) {
        int low = 0, high = a.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (a[mid] == target) {
                System.out.println("Binary comparisons: " + comparisons);
                return mid;
            }

            if (a[mid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }

        System.out.println("Binary comparisons: " + comparisons);
        return -1;
    }

    static int linearSearch(int[] a, int target) {
        int comparisons = 0;

        for (int i = 0; i < a.length; i++) {
            comparisons++;

            if (a[i] == target) {
                System.out.println("Linear comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear comparisons: " + comparisons);
        return -1;
    }

    // TASK 2: INSERTION SORT
    static void insertionSort(Transaction[] a) {
        for (int i = 1; i < a.length; i++) {
            Transaction key = a[i];
            int j = i - 1;

            while (j >= 0 && a[j].date.compareTo(key.date) > 0) {
                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = key;
        }
    }

    // MERGE SORT
    static void mergeSort(Transaction[] a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    static void merge(Transaction[] a, int l, int m, int r) {
        Transaction[] temp = new Transaction[r - l + 1];

        int i = l, j = m + 1, k = 0;

        while (i <= m && j <= r) {
            if (a[i].date.compareTo(a[j].date) <= 0)
                temp[k++] = a[i++];
            else
                temp[k++] = a[j++];
        }

        while (i <= m) temp[k++] = a[i++];
        while (j <= r) temp[k++] = a[j++];

        for (i = 0; i < temp.length; i++)
            a[l + i] = temp[i];
    }

    // TASK 3: DUPLICATE DETECTION
    static void findDuplicates(Transaction[] a) {
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        for (Transaction t : a) {
            String key = t.account + "|" + t.amount + "|" + t.merchant;

            if (!seen.add(key))
                duplicates.add(key);
        }

        System.out.println("Duplicate groups: " + duplicates.size());
        for (String d : duplicates)
            System.out.println(d);
    }

    // TASK 4: CUSTOM QUEUE
    static class MyQueue {
        int[] q = new int[10];
        int front = 0, rear = -1;

        void enqueue(int x) {
            if (rear == q.length - 1) {
                System.out.println("Queue full");
                return;
            }
            q[++rear] = x;
        }

        void dequeue() {
            if (front > rear)
                System.out.println("Queue empty");
            else
                System.out.println("Review: " + q[front++]);
        }
    }

    // CUSTOM STACK
    static class MyStack {
        int[] s = new int[10];
        int top = -1;

        void push(int x) {
            if (top == s.length - 1) {
                System.out.println("Stack full");
                return;
            }
            s[++top] = x;
        }

        void pop() {
            if (top == -1)
                System.out.println("Stack empty");
            else
                System.out.println("Reversed adjustment: " + s[top--]);
        }
    }

    // TASK 5: BRUTE FORCE
    static void bruteForce(int[] a) {
        int min = a[0], max = a[0];

        for (int x : a) {
            if (x < min) min = x;
            if (x > max) max = x;
        }

        System.out.println("Brute Min: " + min);
        System.out.println("Brute Max: " + max);
    }

    // DIVIDE AND CONQUER
    static int[] minMax(int[] a, int l, int r) {
        if (l == r)
            return new int[]{a[l], a[l]};

        if (r == l + 1)
            return new int[]{
                Math.min(a[l], a[r]),
                Math.max(a[l], a[r])
            };

        int mid = (l + r) / 2;

        int[] left = minMax(a, l, mid);
        int[] right = minMax(a, mid + 1, r);

        return new int[]{
            Math.min(left[0], right[0]),
            Math.max(left[1], right[1])
        };
    }

    public static void main(String[] args) {

        System.out.println("TASK 1: SEARCH");

        int[] ids = new int[100000];
        for (int i = 0; i < ids.length; i++)
            ids[i] = 100001 + i;

        linearSearch(ids, 150000);
        binarySearch(ids, 150000);

        System.out.println("\nTASK 2: SORTING");

        Transaction[] records = {
            new Transaction(1, "A101", 500, "Amazon", "2026-10-29T10:00:00"),
            new Transaction(2, "A102", 300, "Flipkart", "2026-10-29T09:00:00"),
            new Transaction(3, "A103", 700, "Amazon", "2026-10-29T10:00:00"),
            new Transaction(4, "A104", 200, "Store", "2026-10-29T11:00:00")
        };

        Transaction[] insertion = records.clone();
        Transaction[] merge = records.clone();

        insertionSort(insertion);
        mergeSort(merge, 0, merge.length - 1);

        System.out.println("Insertion Sort:");
        for (Transaction t : insertion) System.out.println(t);

        System.out.println("Merge Sort:");
        for (Transaction t : merge) System.out.println(t);

        System.out.println("\nTASK 3: DUPLICATES");

        Transaction[] duplicateData = {
            new Transaction(1, "A101", 500, "Amazon", "2026-10-29T10:00:00"),
            new Transaction(2, "A101", 500, "Amazon", "2026-10-29T10:00:45"),
            new Transaction(3, "A102", 300, "Store", "2026-10-29T11:00:00")
        };

        findDuplicates(duplicateData);

        System.out.println("\nTASK 4: QUEUE AND STACK");

        MyQueue q = new MyQueue();
        q.enqueue(101);
        q.enqueue(102);
        q.dequeue();

        MyStack s = new MyStack();
        s.push(201);
        s.push(202);
        s.pop();

        System.out.println("\nTASK 5: MINIMUM AND MAXIMUM");

        int[] amounts = {500, 200, 900, 100, 700};

        bruteForce(amounts);

        int[] result = minMax(amounts, 0, amounts.length - 1);

        System.out.println("Divide Min: " + result[0]);
        System.out.println("Divide Max: " + result[1]);
    }
}
