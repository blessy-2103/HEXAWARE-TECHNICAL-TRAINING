public class Van extends Vehicle implements Insurable {
    private final int seatingCapacity;

    public Van(String registrationNumber, String model, int seatingCapacity) {
        super(registrationNumber, model, VehicleType.VAN);
        if (seatingCapacity < 6) {
            throw new IllegalArgumentException("Van seating capacity must be at least 6.");
        }
        this.seatingCapacity = seatingCapacity;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    @Override
    public double lateFee(int lateDays) {
        if (lateDays <= 0) {
            return 0.0;
        }
        // Escalating penalty: Rs. 1,000/day for first 2 days, Rs. 1,500/day thereafter
        if (lateDays <= 2) {
            return lateDays * 1000.0;
        }
        return (2 * 1000.0) + ((lateDays - 2) * 1500.0);
    }

    @Override
    public double calculateInsuranceFee(int days) {
        return Math.max(1, days) * 600.0;
    }

    @Override
    public String getCoverageDetails() {
        return "Commercial Passenger & Cargo Transit Cover (" + seatingCapacity + " seats)";
    }
}
