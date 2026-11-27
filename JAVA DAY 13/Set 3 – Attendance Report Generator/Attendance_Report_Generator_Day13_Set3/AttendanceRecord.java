import java.time.LocalDate;

// Task 4: Immutable record (components are implicitly private final with no setters)
public record AttendanceRecord(
    String learnerId,
    String learnerName,
    LocalDate sessionDate,
    String status
) {
    public AttendanceRecord {
        learnerId = learnerId.strip();
        learnerName = learnerName.strip();
        status = status.strip().toUpperCase();
        if (!status.equals("P") && !status.equals("A")) {
            throw new IllegalArgumentException("Status must be 'P' or 'A', got: " + status);
        }
    }
}
