public class Bike extends Vehicle {
    private final int engineCc;

    public Bike(String registrationNumber, String model, int engineCc) {
        super(registrationNumber, model, VehicleType.BIKE);
        if (engineCc <= 0) {
            throw new IllegalArgumentException("Engine CC must be positive.");
        }
        this.engineCc = engineCc;
    }

    public int getEngineCc() {
        return engineCc;
    }

    @Override
    public double lateFee(int lateDays) {
        if (lateDays <= 0) {
            return 0.0;
        }
        // Flat grace-free Rs. 300/day penalty for bikes
        return lateDays * 300.0;
    }
}
