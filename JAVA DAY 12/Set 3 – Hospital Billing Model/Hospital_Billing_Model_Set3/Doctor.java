public class Doctor {
    private final String doctorId;
    private final String name;
    private final String department;

    public Doctor(String doctorId, String name, String department) {
        if (doctorId == null || doctorId.isBlank()) {
            throw new IllegalArgumentException("Doctor ID cannot be empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Doctor name cannot be empty.");
        }
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }
        this.doctorId = doctorId.trim();
        this.name = name.trim();
        this.department = department.trim();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}
