public class Customer {
    private final String customerId;
    private final String name;
    private final String licenseNumber;

    public Customer(String customerId, String name, String licenseNumber) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID is required.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException("Driving license number is required.");
        }
        this.customerId = customerId.trim();
        this.name = name.trim();
        this.licenseNumber = licenseNumber.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
