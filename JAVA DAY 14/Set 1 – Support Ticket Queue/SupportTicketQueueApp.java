import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class SupportTicketQueueApp {

    enum Priority {
        LOW(1), MEDIUM(2), HIGH(3), CRITICAL(4);

        private final int level;

        Priority(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    enum Status {
        OPEN, IN_PROGRESS, RESOLVED
    }

    static class Ticket {
        private final String ticketNumber;
        private final String user;
        private final String category;
        private final String summary;
        private final Priority priority;
        private final LocalDateTime createdAt;
        private Status status;

        public Ticket(String ticketNumber, String user, String category, String summary,
                      Priority priority, LocalDateTime createdAt, Status status) {
            this.ticketNumber = ticketNumber;
            this.user = user;
            this.category = category;
            this.summary = summary;
            this.priority = priority;
            this.createdAt = createdAt;
            this.status = status;
        }

        public String getTicketNumber() { return ticketNumber; }
        public String getUser() { return user; }
        public String getCategory() { return category; }
        public String getSummary() { return summary; }
        public Priority getPriority() { return priority; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public Status getStatus() { return status; }

        // Duplicate definition: same user and same summary (case-insensitive)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Ticket other)) return false;
            return Objects.equals(user.toLowerCase(), other.user.toLowerCase())
                    && Objects.equals(summary.toLowerCase(), other.summary.toLowerCase());
        }

        @Override
        public int hashCode() {
            return Objects.hash(user.toLowerCase(), summary.toLowerCase());
        }

        @Override
        public String toString() {
            return String.format("[%s | %s | %-8s | %-8s | %-10s | %s | %s]",
                    ticketNumber, createdAt, priority, status, category, user, summary);
        }
    }

    public static void main(String[] args) {
        List<Ticket> rawTickets = generate30Tickets();

        System.out.println("=== 0. RAW TEST DATA (" + rawTickets.size() + " tickets) ===");
        rawTickets.forEach(System.out::println);

        // Task 1: Deduplicate using LinkedHashSet (keeps earliest ticket if sorted by createdAt first)
        System.out.println("\n=== TASK 1: DEDUPLICATED TICKETS (LinkedHashSet, earliest kept) ===");
        List<Ticket> chronologicalTickets = new ArrayList<>(rawTickets);
        chronologicalTickets.sort(Comparator.comparing(Ticket::getCreatedAt)
                .thenComparing(Ticket::getTicketNumber));

        Set<Ticket> uniqueTicketSet = new LinkedHashSet<>(chronologicalTickets);
        List<Ticket> uniqueTickets = new ArrayList<>(uniqueTicketSet);
        System.out.println("Removed duplicates: " + (rawTickets.size() - uniqueTickets.size()));
        uniqueTickets.forEach(System.out::println);

        // Task 2: Sort by priority (descending), then creation time, then ticket number
        System.out.println("\n=== TASK 2: SORTED BY PRIORITY DESC, CREATED_AT ASC, TICKET_NO ASC ===");
        Comparator<Ticket> ticketComparator = Comparator
                .comparingInt((Ticket t) -> t.getPriority().getLevel()).reversed()
                .thenComparing(Ticket::getCreatedAt)
                .thenComparing(Ticket::getTicketNumber);

        List<Ticket> sortedTickets = new ArrayList<>(uniqueTickets);
        sortedTickets.sort(ticketComparator);
        sortedTickets.forEach(System.out::println);

        // Task 3: PriorityQueue serving highest priority first; ties broken by createdAt then ticketNumber
        System.out.println("\n=== TASK 3: PRIORITY QUEUE SERVE ORDER ===");
        PriorityQueue<Ticket> workQueue = new PriorityQueue<>(ticketComparator);
        workQueue.addAll(uniqueTickets);

        int rank = 1;
        while (!workQueue.isEmpty()) {
            System.out.printf("%2d. %s%n", rank++, workQueue.poll());
        }

        // Task 4: Report per category and per user using HashMap and TreeMap
        System.out.println("\n=== TASK 4: REPORTS USING HashMap AND TreeMap ===");
        Map<String, Integer> categoryHashMap = new HashMap<>();
        Map<String, Integer> userHashMap = new HashMap<>();

        for (Ticket t : uniqueTickets) {
            categoryHashMap.merge(t.getCategory(), 1, Integer::sum);
            userHashMap.merge(t.getUser(), 1, Integer::sum);
        }

        Map<String, Integer> categoryTreeMap = new TreeMap<>(categoryHashMap);
        Map<String, Integer> userTreeMap = new TreeMap<>(userHashMap);

        System.out.println("Tickets per Category (HashMap - hash/bucket order): " + categoryHashMap);
        System.out.println("Tickets per Category (TreeMap - natural/alphabetical order): " + categoryTreeMap);
        System.out.println("Tickets per User (HashMap - hash/bucket order): " + userHashMap);
        System.out.println("Tickets per User (TreeMap - natural/alphabetical order): " + userTreeMap);

        // Task 5: Safe removal of RESOLVED tickets using Iterator
        System.out.println("\n=== TASK 5: REMOVE RESOLVED TICKETS USING ITERATOR ===");
        List<Ticket> activeTickets = new ArrayList<>(uniqueTickets);
        Iterator<Ticket> iterator = activeTickets.iterator();
        int removedCount = 0;

        while (iterator.hasNext()) {
            Ticket current = iterator.next();
            if (current.getStatus() == Status.RESOLVED) {
                iterator.remove();
                removedCount++;
            }
        }

        System.out.println("Resolved tickets removed: " + removedCount);
        System.out.println("Remaining active tickets: " + activeTickets.size());
        activeTickets.forEach(System.out::println);
    }

    private static List<Ticket> generate30Tickets() {
        LocalDateTime base = LocalDateTime.of(2026, 10, 14, 9, 0);
        List<Ticket> list = new ArrayList<>();

        // 24 distinct (user, summary) tickets + 6 duplicate (user, summary) tickets = 30 tickets
        list.add(new Ticket("TCK-1001", "Ananya", "Network", "VPN disconnects every 15 mins", Priority.HIGH, base.plusMinutes(5), Status.OPEN));
        list.add(new Ticket("TCK-1002", "Rohan", "Access", "Need production DB read role", Priority.MEDIUM, base.plusMinutes(10), Status.IN_PROGRESS));
        list.add(new Ticket("TCK-1003", "Priya", "Billing", "Invoice shows duplicate charge", Priority.CRITICAL, base.plusMinutes(12), Status.OPEN));
        list.add(new Ticket("TCK-1004", "Karthik", "Hardware", "Laptop battery drains in 40 mins", Priority.LOW, base.plusMinutes(18), Status.RESOLVED));
        list.add(new Ticket("TCK-1005", "Meera", "Software", "IDE crashes on large project index", Priority.HIGH, base.plusMinutes(22), Status.OPEN));
        list.add(new Ticket("TCK-1006", "Vikram", "Network", "Office Wi-Fi captive portal down", Priority.CRITICAL, base.plusMinutes(25), Status.IN_PROGRESS));
        list.add(new Ticket("TCK-1007", "Ananya", "Software", "Git push rejected due to SSH key", Priority.MEDIUM, base.plusMinutes(30), Status.RESOLVED));
        list.add(new Ticket("TCK-1008", "Divya", "Access", "SSO login loop on HR portal", Priority.HIGH, base.plusMinutes(35), Status.OPEN));
        list.add(new Ticket("TCK-1009", "Arjun", "Hardware", "External monitor no signal via HDMI", Priority.LOW, base.plusMinutes(40), Status.OPEN));
        list.add(new Ticket("TCK-1010", "Sneha", "Billing", "GST number missing on receipt", Priority.MEDIUM, base.plusMinutes(45), Status.RESOLVED));

        // Duplicate 1: Ananya + "VPN disconnects every 15 mins" (later than TCK-1001)
        list.add(new Ticket("TCK-1011", "Ananya", "Network", "VPN disconnects every 15 mins", Priority.CRITICAL, base.plusMinutes(50), Status.OPEN));

        list.add(new Ticket("TCK-1012", "Rohan", "Software", "Docker daemon fails to start", Priority.HIGH, base.plusMinutes(55), Status.IN_PROGRESS));
        list.add(new Ticket("TCK-1013", "Priya", "Access", "Shared drive permission denied", Priority.MEDIUM, base.plusMinutes(60), Status.OPEN));
        list.add(new Ticket("TCK-1014", "Karthik", "Network", "Packet loss to staging endpoint", Priority.CRITICAL, base.plusMinutes(65), Status.OPEN));
        list.add(new Ticket("TCK-1015", "Meera", "Billing", "Refund pending for cancelled plan", Priority.MEDIUM, base.plusMinutes(70), Status.IN_PROGRESS));

        // Duplicate 2: Priya + "Invoice shows duplicate charge" (later than TCK-1003)
        list.add(new Ticket("TCK-1016", "Priya", "Billing", "Invoice shows duplicate charge", Priority.HIGH, base.plusMinutes(75), Status.OPEN));

        list.add(new Ticket("TCK-1017", "Vikram", "Hardware", "Mechanical keyboard key chattering", Priority.LOW, base.plusMinutes(80), Status.RESOLVED));
        list.add(new Ticket("TCK-1018", "Divya", "Software", "Email client search index corrupted", Priority.MEDIUM, base.plusMinutes(85), Status.OPEN));
        list.add(new Ticket("TCK-1019", "Arjun", "Access", "MFA token reset required", Priority.HIGH, base.plusMinutes(90), Status.OPEN));
        list.add(new Ticket("TCK-1020", "Sneha", "Network", "DNS resolution slow on custom domain", Priority.HIGH, base.plusMinutes(95), Status.IN_PROGRESS));

        // Duplicate 3: Vikram + "Office Wi-Fi captive portal down" (later than TCK-1006)
        list.add(new Ticket("TCK-1021", "Vikram", "Network", "Office Wi-Fi captive portal down", Priority.MEDIUM, base.plusMinutes(100), Status.OPEN));

        list.add(new Ticket("TCK-1022", "Ananya", "Hardware", "USB-C dock stops charging mouse", Priority.LOW, base.plusMinutes(105), Status.OPEN));
        list.add(new Ticket("TCK-1023", "Rohan", "Billing", "Upgrade seat count to 25 users", Priority.MEDIUM, base.plusMinutes(110), Status.RESOLVED));
        list.add(new Ticket("TCK-1024", "Priya", "Software", "Export to CSV truncates long text", Priority.LOW, base.plusMinutes(115), Status.OPEN));

        // Duplicate 4: Divya + "SSO login loop on HR portal" (later than TCK-1008)
        list.add(new Ticket("TCK-1025", "Divya", "Access", "SSO login loop on HR portal", Priority.CRITICAL, base.plusMinutes(120), Status.OPEN));

        list.add(new Ticket("TCK-1026", "Karthik", "Software", "Build pipeline times out on tests", Priority.HIGH, base.plusMinutes(125), Status.IN_PROGRESS));
        list.add(new Ticket("TCK-1027", "Meera", "Access", "Requesting VPN split-tunnel access", Priority.LOW, base.plusMinutes(130), Status.OPEN));

        // Duplicate 5: Arjun + "MFA token reset required" (later than TCK-1019)
        list.add(new Ticket("TCK-1028", "Arjun", "Access", "MFA token reset required", Priority.LOW, base.plusMinutes(135), Status.RESOLVED));

        list.add(new Ticket("TCK-1029", "Sneha", "Hardware", "Webcam mic muffled on calls", Priority.LOW, base.plusMinutes(140), Status.OPEN));

        // Duplicate 6: Karthik + "Packet loss to staging endpoint" (later than TCK-1014)
        list.add(new Ticket("TCK-1030", "Karthik", "Network", "Packet loss to staging endpoint", Priority.HIGH, base.plusMinutes(145), Status.OPEN));

        return list;
    }
}
