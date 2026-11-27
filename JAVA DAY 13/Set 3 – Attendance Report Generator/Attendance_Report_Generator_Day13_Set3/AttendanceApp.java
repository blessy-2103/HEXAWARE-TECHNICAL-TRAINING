import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceApp {

    public static void main(String[] args) throws InterruptedException {
        // Task 1: Learner and Course as HashMap keys
        Learner l1 = new Learner("L101", "Asha");
        Learner l2 = new Learner("L102", "Vikram");
        Learner l1DuplicateKey = new Learner("L101", "Asha Sharma"); // same ID -> equals() & hashCode() match l1

        Course c1 = new Course("JAVA101", "Core Java & OOP");
        Course c2 = new Course("SQL102", "Relational Databases");
        Course c1DuplicateKey = new Course("JAVA101", "Core Java");

        Map<Learner, Map<Course, Double>> attendanceScores = new HashMap<>();
        attendanceScores.computeIfAbsent(l1, k -> new HashMap<>()).put(c1, 92.5);
        attendanceScores.computeIfAbsent(l2, k -> new HashMap<>()).put(c1, 85.0);
        attendanceScores.computeIfAbsent(l2, k -> new HashMap<>()).put(c2, 95.0);

        System.out.println("=== 1. HashMap Lookup Using Equal Keys ===");
        System.out.println("Lookup l1DuplicateKey in c1: " + attendanceScores.get(l1DuplicateKey).get(c1DuplicateKey) + "%");

        // Task 3: Parse attendance lines and report malformed entries
        List<String> rawLines = List.of(
            "L101|Asha|2024-03-01|P",
            "L101|Asha|2024-03-02|P",
            "L102|Vikram|2024-03-01|P",
            "L102|Vikram|2024-03-02|A",
            "BADLINE_NO_PIPES",
            "L99|TooShortId|2024-03-01|P",
            "L103|Neha|not-a-date|P",
            "L104|Kiran|2024-03-01|X"
        );

        List<String> malformedLines = new ArrayList<>();
        List<AttendanceRecord> validRecords = AttendanceReportGenerator.parseLines(rawLines, malformedLines);

        System.out.println("\n=== 2. Malformed Line Report ===");
        malformedLines.forEach(System.out::println);

        // Task 2: Formatted report
        System.out.println("\n=== 3. Formatted Attendance Report ===");
        System.out.println(AttendanceReportGenerator.buildReport(c1, validRecords));

        // Task 4: Immutable record verification
        AttendanceRecord sampleRecord = validRecords.get(0);
        System.out.println("=== 4. Immutable AttendanceRecord ===");
        System.out.println("Record: " + sampleRecord);
        System.out.println("Accessor values -> id=" + sampleRecord.learnerId()
                + ", date=" + sampleRecord.sessionDate()
                + ", status=" + sampleRecord.status());
        // Note: sampleRecord has no setter methods (e.g., sampleRecord.setStatus(...) does not exist)
        // and its components are final, preventing reassignment of fields on the record instance.

        // Task 5: StringBuilder vs StringBuffer under concurrent threads
        System.out.println("\n=== 5. Thread-Safety Comparison ===");
        StringBufferSafetyDemo.runConcurrentAppendDemo();
    }
}
