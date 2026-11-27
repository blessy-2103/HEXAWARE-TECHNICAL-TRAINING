import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class CourseWaitlistApp {

    static class Course {
        private final String courseCode;
        private final String title;
        private final int capacity;

        public Course(String courseCode, String title, int capacity) {
            this.courseCode = courseCode;
            this.title = title;
            this.capacity = capacity;
        }

        public String getCourseCode() { return courseCode; }
        public String getTitle() { return title; }
        public int getCapacity() { return capacity; }

        @Override
        public String toString() {
            return String.format("%s (%s, cap=%d)", courseCode, title, capacity);
        }
    }

    static class BootcampCourse extends Course {
        private final int labSeats;

        public BootcampCourse(String courseCode, String title, int capacity, int labSeats) {
            super(courseCode, title, capacity);
            this.labSeats = labSeats;
        }

        public int getLabSeats() { return labSeats; }
    }

    static class Registration implements Comparable<Registration> {
        private final String learnerId;
        private final String learnerName;
        private final String courseCode;
        private final LocalDateTime registeredAt;
        private final boolean seniorLearner;
        private final int score;

        public Registration(String learnerId, String learnerName, String courseCode,
                            LocalDateTime registeredAt, boolean seniorLearner, int score) {
            this.learnerId = learnerId;
            this.learnerName = learnerName;
            this.courseCode = courseCode;
            this.registeredAt = registeredAt;
            this.seniorLearner = seniorLearner;
            this.score = score;
        }

        public String getLearnerId() { return learnerId; }
        public String getLearnerName() { return learnerName; }
        public String getCourseCode() { return courseCode; }
        public LocalDateTime getRegisteredAt() { return registeredAt; }
        public boolean isSeniorLearner() { return seniorLearner; }
        public int getScore() { return score; }

        // Duplicate registration: same learnerId and same courseCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Registration other)) return false;
            return Objects.equals(learnerId, other.learnerId)
                    && Objects.equals(courseCode, other.courseCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(learnerId, courseCode);
        }

        // Natural order for final roster: course (asc), score (desc), learnerName (asc)
        @Override
        public int compareTo(Registration other) {
            return Comparator.comparing(Registration::getCourseCode)
                    .thenComparing(Comparator.comparingInt(Registration::getScore).reversed())
                    .thenComparing(Registration::getLearnerName)
                    .compare(this, other);
        }

        @Override
        public String toString() {
            return String.format("[%s | %-10s | %-8s | %s | senior=%-5b | score=%-2d]",
                    learnerId, learnerName, courseCode, registeredAt, seniorLearner, score);
        }
    }

    // Task 4: Wildcard method summing capacity for any List of Course or subclass
    public static int totalCapacity(List<? extends Course> courses) {
        int sum = 0;
        for (Course c : courses) {
            sum += c.getCapacity();
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Registration> sampleData = createSampleData();

        System.out.println("=== SAMPLE DATA (" + sampleData.size() + " registrations) ===");
        sampleData.forEach(r -> System.out.println("  " + r));

        // Task 2: Deduplicate with HashSet and report count removed
        System.out.println("\n=== TASK 2: DEDUPLICATE WITH HashSet ===");
        Set<Registration> uniqueSet = new HashSet<>(sampleData);
        int duplicatesRemoved = sampleData.size() - uniqueSet.size();
        List<Registration> uniqueRegistrations = new ArrayList<>(uniqueSet);
        System.out.println("  Duplicate registrations removed: " + duplicatesRemoved);
        System.out.println("  Unique registrations remaining: " + uniqueRegistrations.size());

        // Task 1: Waitlist PriorityQueue -> earlier registration first, then senior learners first
        System.out.println("\n=== TASK 1: WAITLIST PRIORITY QUEUE (Earlier registered, then Senior) ===");
        Comparator<Registration> waitlistComparator = Comparator
                .comparing(Registration::getRegisteredAt)
                .thenComparing((Registration r) -> r.isSeniorLearner() ? 0 : 1)
                .thenComparing(Registration::getLearnerId);

        PriorityQueue<Registration> waitlistQueue = new PriorityQueue<>(waitlistComparator);
        waitlistQueue.addAll(uniqueRegistrations);

        int pos = 1;
        while (!waitlistQueue.isEmpty()) {
            System.out.printf("  %2d. %s%n", pos++, waitlistQueue.poll());
        }

        // Task 3: Final roster sorted by course, score (desc), name (Comparable vs Comparator)
        System.out.println("\n=== TASK 3: FINAL ROSTER (Course ASC, Score DESC, Name ASC) ===");
        List<Registration> rosterComparable = new ArrayList<>(uniqueRegistrations);
        Collections.sort(rosterComparable); // uses Registration.compareTo
        rosterComparable.forEach(r -> System.out.println("  " + r));

        Comparator<Registration> byNameThenScoreDesc = Comparator
                .comparing(Registration::getLearnerName)
                .thenComparing(Comparator.comparingInt(Registration::getScore).reversed());
        List<Registration> rosterComparator = new ArrayList<>(uniqueRegistrations);
        rosterComparator.sort(byNameThenScoreDesc);
        System.out.println("  Alternative order via Comparator (Name ASC, Score DESC):");
        rosterComparator.stream().limit(4).forEach(r -> System.out.println("    " + r));

        // Task 4: TreeSet sorted by score + List<? extends Course> capacity sum
        System.out.println("\n=== TASK 4: TreeSet BY SCORE & WILDCARD CAPACITY SUM ===");
        TreeSet<Registration> byScoreTreeSet = new TreeSet<>(
                Comparator.comparingInt(Registration::getScore).reversed()
                        .thenComparing(Registration::getLearnerId)
        );
        byScoreTreeSet.addAll(uniqueRegistrations);
        System.out.println("  Top 4 learners by score (TreeSet):");
        byScoreTreeSet.stream().limit(4).forEach(r -> System.out.println("    " + r));

        List<BootcampCourse> bootcamps = Arrays.asList(
                new BootcampCourse("JAVA-101", "Core Java & Collections", 25, 25),
                new BootcampCourse("SQL-201", "SQL & Query Tuning", 30, 30)
        );
        System.out.println("  Total capacity of List<BootcampCourse> via <? extends Course>: "
                + totalCapacity(bootcamps));

        // Task 5: Arrays & Collections utilities in 4+ places
        System.out.println("\n=== TASK 5: ARRAYS & COLLECTIONS UTILITIES ===");
        String[] priorityCodes = {"C-SQL", "A-JAVA", "B-CLOUD", "D-DEVOPS"};
        Arrays.sort(priorityCodes); // 1. Arrays.sort
        System.out.println("  1. Arrays.sort -> " + Arrays.toString(priorityCodes));

        int idx = Arrays.binarySearch(priorityCodes, "C-SQL"); // 2. Arrays.binarySearch
        System.out.println("  2. Arrays.binarySearch('C-SQL') -> index " + idx);

        List<String> codeList = new ArrayList<>(Arrays.asList(priorityCodes));
        Collections.reverse(codeList); // 3. Collections.reverse
        System.out.println("  3. Collections.reverse -> " + codeList);

        List<String> readOnlyCodes = Collections.unmodifiableList(codeList); // 4. Collections.unmodifiableList
        System.out.println("  4. Collections.unmodifiableList -> " + readOnlyCodes);
    }

    private static List<Registration> createSampleData() {
        LocalDateTime t0 = LocalDateTime.of(2026, 11, 10, 10, 0);
        return Arrays.asList(
                new Registration("L-101", "Ananya", "JAVA-101", t0.plusMinutes(5), false, 88),
                new Registration("L-102", "Rohan", "JAVA-101", t0.plusMinutes(5), true, 82),
                new Registration("L-103", "Priya", "SQL-201", t0.plusMinutes(12), false, 95),
                new Registration("L-104", "Karthik", "SQL-201", t0.plusMinutes(18), true, 91),
                new Registration("L-105", "Meera", "JAVA-101", t0.plusMinutes(25), false, 79),
                new Registration("L-106", "Vikram", "SQL-201", t0.plusMinutes(30), false, 95),
                new Registration("L-107", "Divya", "JAVA-101", t0.plusMinutes(35), true, 88),
                new Registration("L-108", "Arjun", "SQL-201", t0.plusMinutes(40), false, 74),
                // Duplicates (same learnerId + courseCode)
                new Registration("L-101", "Ananya", "JAVA-101", t0.plusMinutes(50), true, 90),
                new Registration("L-103", "Priya", "SQL-201", t0.plusMinutes(55), false, 96)
        );
    }
}
