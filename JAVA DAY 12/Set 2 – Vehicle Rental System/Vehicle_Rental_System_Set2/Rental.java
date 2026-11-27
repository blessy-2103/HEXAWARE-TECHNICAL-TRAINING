// Task 3 & Task 5: Holds rental state and composition (HAS-A Vehicle and Customer), separated from billing & printing
public class Rental {
    private final String rentalId;
    private final Vehicle vehicle;
    private final Customer customer;
    private final int daysUsed;
    private final int lateDays;

    public Rental(String rentalId, Vehicle vehicle, Customer customer, int daysUsed, int lateDays) {
        if (rentalId == null || rentalId.isBlank()) {
            throw new IllegalArgumentException("Rental ID cannot be empty.");
        }
        if (vehicle == null || customer == null) {
            throw new IllegalArgumentException("Vehicle and Customer must not be null.");
        }
        if (daysUsed <= 0) {
            throw new IllegalArgumentException("Days used must be at least 1.");
        }
        if (lateDays < 0) {
            throw new IllegalArgumentException("Late days cannot be negative.");
        }
        this.rentalId = rentalId.trim();
        this.vehicle = vehicle;
        this.customer = customer;
        this.daysUsed = daysUsed;
        this.lateDays = lateDays;
    }

    public String getRentalId() {
        return rentalId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDaysUsed() {
        return daysUsed;
    }

    public int getLateDays() {
        return lateDays;
    }

    public double calculateBaseRentalAmount() {
        return vehicle.getDailyRate() * daysUsed;
    }
}
