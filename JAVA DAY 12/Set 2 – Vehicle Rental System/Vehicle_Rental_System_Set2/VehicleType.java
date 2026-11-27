public enum VehicleType {
    CAR(2500.0),
    BIKE(800.0),
    VAN(4200.0);

    private final double dailyRate;

    VehicleType(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public double getDailyRate() {
        return dailyRate;
    }
}
