
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main {

    static class Patient {
        int id;
        String name;

        Patient(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class Appointment {
        int id;
        int patientId;
        int urgency; // 1 = highest priority
        LocalDateTime arrival;
        LocalDateTime start;
        LocalDateTime end;

        Appointment(int id, int patientId, int urgency,
                    String arrival, String start, String end) {
            this.id = id;
            this.patientId = patientId;
            this.urgency = urgency;
            this.arrival = LocalDateTime.parse(arrival);
            this.start = LocalDateTime.parse(start);
            this.end = LocalDateTime.parse(end);
        }

        public String toString() {
            return "Appointment " + id +
                   " | Patient " + patientId +
                   " | Urgency " + urgency +
                   " | Arrival " + arrival.toLocalTime();
        }
    }

    static int linearSearch(Patient[] patients, int target) {
        for (int i = 0; i < patients.length; i++) {
            if (patients[i].id == target)
                return i;
        }
        return -1;
    }

    static int binarySearch(Patient[] patients, int target) {
        int low = 0;
        int high = patients.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (patients[mid].id == target)
                return mid;
            else if (patients[mid].id < target)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return -1;
    }

    // Lower urgency number first, then earlier arrival
    static int compare(Appointment a, Appointment b) {
        if (a.urgency != b.urgency)
            return Integer.compare(a.urgency, b.urgency);

        return a.arrival.compareTo(b.arrival);
    }

    // Selection Sort
    static int selectionSort(Appointment[] a) {
        int swaps = 0;

        for (int i = 0; i < a.length - 1; i++) {
            int min = i;

            for (int j = i + 1; j < a.length; j++) {
                if (compare(a[j], a[min]) < 0)
                    min = j;
            }

            if (min != i) {
                Appointment temp = a[i];
                a[i] = a[min];
                a[min] = temp;
                swaps++;
            }
        }
        return swaps;
    }

    // Quick Sort
    static int quickSort(Appointment[] a, int low, int high) {
        if (low >= high)
            return 0;

        int[] result = partition(a, low, high);
        int pivot = result[0];
        int swaps = result[1];

        swaps += quickSort(a, low, pivot - 1);
        swaps += quickSort(a, pivot + 1, high);

        return swaps;
    }

    static int[] partition(Appointment[] a, int low, int high) {
        Appointment pivot = a[high];
        int i = low - 1;
        int swaps = 0;

        for (int j = low; j < high; j++) {
            if (compare(a[j], pivot) <= 0) {
                i++;

                if (i != j) {
                    Appointment temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    swaps++;
                }
            }
        }

        if (i + 1 != high) {
            Appointment temp = a[i + 1];
            a[i + 1] = a[high];
            a[high] = temp;
            swaps++;
        }

        return new int[]{i + 1, swaps};
    }

    // Duplicate booking detection using HashSet
    static void findDuplicates(Appointment[] appointments) {
        Set<String> bookedSlots = new HashSet<>();
        int duplicates = 0;

        for (Appointment a : appointments) {
            String key = a.patientId + "|" + a.start;

            if (!bookedSlots.add(key)) {
                System.out.println(
                    "Duplicate booking found: Appointment " + a.id
                );
                duplicates++;
            }
        }

        System.out.println("Total duplicate bookings: " + duplicates);
    }

    // Custom Queue - FIFO
    static class MyQueue {
        int[] queue = new int[10];
        int front = 0, rear = 0, size = 0;

        void enqueue(int value) {
            if (size == queue.length) {
                System.out.println("Queue full");
                return;
            }

            queue[rear] = value;
            rear = (rear + 1) % queue.length;
            size++;
        }

        void dequeue() {
            if (size == 0) {
                System.out.println("Queue empty");
                return;
            }

            System.out.println("Walk-in patient: " + queue[front]);
            front = (front + 1) % queue.length;
            size--;
        }
    }

    // Custom Stack - LIFO
    static class MyStack {
        int[] stack = new int[10];
        int top = -1;

        void push(int value) {
            if (top == stack.length - 1) {
                System.out.println("Stack full");
                return;
            }

            stack[++top] = value;
        }

        void pop() {
            if (top == -1) {
                System.out.println("History empty");
                return;
            }

            System.out.println("Recently viewed patient: " + stack[top--]);
        }
    }

    // Find previous appointment that finishes before current one starts
    static int findPrevious(Appointment[] a, int current) {
        int low = 0;
        int high = current - 1;
        int answer = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (!a[mid].end.isAfter(a[current].start)) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }

    // Dynamic Programming: Maximum number of non-overlapping appointments
    static int maxAppointmentsDP(Appointment[] input) {
        Appointment[] a = input.clone();

        // Sort appointments by ending time
        Arrays.sort(a, Comparator.comparing(x -> x.end));

        int n = a.length;
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int previous = findPrevious(a, i - 1);

            int include = 1 + dp[previous + 1];
            int exclude = dp[i - 1];

            dp[i] = Math.max(include, exclude);
        }

        return dp[n];
    }

    // Greedy: Select the appointment that finishes earliest
    static int maxAppointmentsGreedy(Appointment[] input) {
        Appointment[] a = input.clone();

        Arrays.sort(a, Comparator.comparing(x -> x.end));

        int count = 0;
        LocalDateTime lastEnd = null;

        for (Appointment appointment : a) {
            if (lastEnd == null ||
                !appointment.start.isBefore(lastEnd)) {

                count++;
                lastEnd = appointment.end;
            }
        }

        return count;
    }

    public static void main(String[] args) {

        // TASK 1: SEARCHING
        System.out.println("TASK 1: SEARCHING");

        Patient[] patients = new Patient[20000];

        for (int i = 0; i < patients.length; i++) {
            patients[i] = new Patient(100001 + i, "Patient" + (i + 1));
        }

        int[] targets = new int[1000];

        for (int i = 0; i < targets.length; i++) {
            targets[i] = patients[(i * 19) % patients.length].id;
        }

        int found = 0;
        long startTime = System.nanoTime();

        for (int target : targets) {
            if (linearSearch(patients, target) != -1)
                found++;
        }

        long linearTime = System.nanoTime() - startTime;

        found = 0;
        startTime = System.nanoTime();

        for (int target : targets) {
            if (binarySearch(patients, target) != -1)
                found++;
        }

        long binaryTime = System.nanoTime() - startTime;

        System.out.println("Linear Search: " + linearTime + " ns");
        System.out.println("Binary Search: " + binaryTime + " ns");
        System.out.println("Successful lookups: " + found + "/1000");

        // TASK 2: SORTING
        System.out.println("\nTASK 2: SORTING");

        Appointment[] appointments = {
            new Appointment(1, 101, 3, "2026-10-30T10:00:00",
                "2026-10-30T10:00:00", "2026-10-30T11:00:00"),

            new Appointment(2, 102, 1, "2026-10-30T09:00:00",
                "2026-10-30T11:00:00", "2026-10-30T12:00:00"),

            new Appointment(3, 103, 2, "2026-10-30T08:00:00",
                "2026-10-30T12:00:00", "2026-10-30T13:00:00"),

            new Appointment(4, 104, 1, "2026-10-30T08:30:00",
                "2026-10-30T13:00:00", "2026-10-30T14:00:00")
        };

        Appointment[] selection = appointments.clone();
        Appointment[] quick = appointments.clone();

        startTime = System.nanoTime();
        int selectionSwaps = selectionSort(selection);
        long selectionTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        int quickSwaps = quickSort(quick, 0, quick.length - 1);
        long quickTime = System.nanoTime() - startTime;

        System.out.println("Selection Sort Swaps: " + selectionSwaps);
        System.out.println("Selection Sort Time: " + selectionTime + " ns");

        System.out.println("Quick Sort Swaps: " + quickSwaps);
        System.out.println("Quick Sort Time: " + quickTime + " ns");

        System.out.println("Sorted Appointments:");
        for (Appointment a : selection)
            System.out.println(a);

        // TASK 3: DUPLICATE DETECTION
        System.out.println("\nTASK 3: DUPLICATE DETECTION");

        Appointment[] bookingData = {
            new Appointment(10, 201, 1, "2026-10-30T09:00:00",
                "2026-10-30T10:00:00", "2026-10-30T11:00:00"),

            new Appointment(11, 201, 2, "2026-10-30T09:30:00",
                "2026-10-30T10:00:00", "2026-10-30T11:00:00"),

            new Appointment(12, 202, 1, "2026-10-30T10:00:00",
                "2026-10-30T11:00:00", "2026-10-30T12:00:00")
        };

        findDuplicates(bookingData);

        // TASK 4: QUEUE AND STACK
        System.out.println("\nTASK 4: QUEUE AND STACK");

        MyQueue q = new MyQueue();
        q.enqueue(301);
        q.enqueue(302);
        q.enqueue(303);
        q.dequeue();

        MyStack s = new MyStack();
        s.push(401);
        s.push(402);
        s.push(403);
        s.pop();

        // TASK 5: DYNAMIC PROGRAMMING AND GREEDY
        System.out.println("\nTASK 5: APPOINTMENT SCHEDULING");

        Appointment[] shift = {
            new Appointment(21, 501, 1, "2026-10-30T08:00:00",
                "2026-10-30T09:00:00", "2026-10-30T11:00:00"),

            new Appointment(22, 502, 1, "2026-10-30T08:00:00",
                "2026-10-30T09:00:00", "2026-10-30T10:00:00"),

            new Appointment(23, 503, 1, "2026-10-30T08:00:00",
                "2026-10-30T10:00:00", "2026-10-30T12:00:00"),

            new Appointment(24, 504, 1, "2026-10-30T08:00:00",
                "2026-10-30T11:00:00", "2026-10-30T12:00:00"),

            new Appointment(25, 505, 1, "2026-10-30T08:00:00",
                "2026-10-30T12:00:00", "2026-10-30T14:00:00"),

            new Appointment(26, 506, 1, "2026-10-30T08:00:00",
                "2026-10-30T13:00:00", "2026-10-30T14:00:00"),

            new Appointment(27, 507, 1, "2026-10-30T08:00:00",
                "2026-10-30T14:00:00", "2026-10-30T15:00:00")
        };

        System.out.println("DP Maximum Appointments: " +
            maxAppointmentsDP(shift));

        System.out.println("Greedy Maximum Appointments: " +
            maxAppointmentsGreedy(shift));
    }
}
