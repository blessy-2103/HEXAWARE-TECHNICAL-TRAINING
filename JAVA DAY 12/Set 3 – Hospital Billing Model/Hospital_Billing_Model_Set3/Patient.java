public class Patient {
    private final String patientId;
    private final String name;
    private final int age;

    public Patient(String patientId, String name, int age) {
        if (patientId == null || patientId.isBlank()) {
            throw new IllegalArgumentException("Patient ID cannot be empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Patient name cannot be empty.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Patient age cannot be negative.");
        }
        this.patientId = patientId.trim();
        this.name = name.trim();
        this.age = age;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
