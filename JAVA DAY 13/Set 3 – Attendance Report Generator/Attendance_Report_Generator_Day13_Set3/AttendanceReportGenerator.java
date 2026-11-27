import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AttendanceReportGenerator {

    public static List<AttendanceRecord> parseLines(List<String> rawLines, List<String> malformedReport) {
        List<AttendanceRecord> validRecords = new ArrayList<>();
        for (int i = 0; i < rawLines.size(); i++) {
            String raw = rawLines.get(i);
            if (raw == null || raw.isBlank()) {
                malformedReport.add("Line " + (i + 1) + " [EMPTY]: '" + raw + "'");
                continue;
            }
            String cleaned = raw.strip();
            // Task 3: Parse 'L101|Asha|2024-03-01|P' using split
            String[] parts = cleaned.split("\\|");
            if (parts.length != 4) {
                malformedReport.add("Line " + (i + 1) + " [INVALID FIELD COUNT]: '" + cleaned + "'");
                continue;
            }
            String id = parts[0].strip();
            String name = parts[1].strip();
            String dateStr = parts[2].strip();
            String status = parts[3].strip().toUpperCase();

            if (!id.matches("L[0-9]{3,5}")) {
                malformedReport.add("Line " + (i + 1) + " [INVALID LEARNER ID]: '" + cleaned + "'");
                continue;
            }
            if (name.isEmpty()) {
                malformedReport.add("Line " + (i + 1) + " [MISSING NAME]: '" + cleaned + "'");
                continue;
            }
            LocalDate date;
            try {
                date = LocalDate.parse(dateStr);
            } catch (DateTimeParseException ex) {
                malformedReport.add("Line " + (i + 1) + " [INVALID DATE]: '" + cleaned + "'");
                continue;
            }
            if (!status.equals("P") && !status.equals("A")) {
                malformedReport.add("Line " + (i + 1) + " [INVALID STATUS]: '" + cleaned + "'");
                continue;
            }

            validRecords.add(new AttendanceRecord(id, name, date, status));
        }
        return validRecords;
    }

    // Task 2: Generate report using String.format, repeat, strip, substring, and String.join
    public static String buildReport(Course course, List<AttendanceRecord> records) {
        Map<String, String> learnerNames = new LinkedHashMap<>();
        Map<String, int[]> countsById = new LinkedHashMap<>(); // [presentCount, totalSessions]

        for (AttendanceRecord r : records) {
            learnerNames.putIfAbsent(r.learnerId(), r.learnerName());
            int[] stats = countsById.computeIfAbsent(r.learnerId(), k -> new int[2]);
            stats[1]++;
            if ("P".equals(r.status())) {
                stats[0]++;
            }
        }

        String border = "=".repeat(68);
        String divider = "-".repeat(68);
        String headerTitle = String.join(" — ", List.of("ATTENDANCE REPORT", course.getCourseCode().strip(), course.getTitle().strip()));

        StringBuilder sb = new StringBuilder();
        sb.append(border).append(System.lineSeparator());
        sb.append(headerTitle).append(System.lineSeparator());
        sb.append(border).append(System.lineSeparator());
        sb.append(String.format("%-8s | %-18s | %-12s | %10s | %9s%n",
                "ID", "LEARNER", "SESSIONS", "PRESENT", "ATT %"));
        sb.append(divider).append(System.lineSeparator());

        for (Map.Entry<String, int[]> entry : countsById.entrySet()) {
            String id = entry.getKey();
            int present = entry.getValue()[0];
            int total = entry.getValue()[1];
            double pct = (total == 0) ? 0.0 : (present * 100.0) / total;

            String fullName = learnerNames.get(id);
            String shortName = fullName.length() > 18 ? fullName.substring(0, 15).strip() + "..." : fullName;
            String sessionRatio = String.join("/", String.valueOf(present), String.valueOf(total));

            sb.append(String.format("%-8s | %-18s | %-12s | %10d | %8.1f%%%n",
                    id, shortName, sessionRatio, present, pct));
        }

        sb.append(divider).append(System.lineSeparator());
        return sb.toString();
    }
}
