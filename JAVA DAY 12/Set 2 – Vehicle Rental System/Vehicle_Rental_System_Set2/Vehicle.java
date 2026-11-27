public abstract class Vehicle {
    private final String registrationNumber;
    private final String model;
    private final VehicleType type;

    protected Vehicle(String registrationNumber, String model, VehicleType type) {
        if (registrationNumber == null || !registrationNumber.matches("[A-Z]{2}-[0-9]{2}-[A-Z]{1,2}-[0-9]{4}")) {
            throw new IllegalArgumentException(
                "Invalid registration number '" + registrationNumber + "'. Expected format: TN-01-AB-1234"
            );
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Vehicle model cannot be empty.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }
        this.registrationNumber = registrationNumber;
        this.model = model.trim();
        this.type = type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getType() {
        return type;
    }

    public double getDailyRate() {
        return type.getDailyRate();
    }

    public abstract double lateFee(int lateDays);
}
