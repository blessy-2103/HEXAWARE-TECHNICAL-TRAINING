public class Car extends Vehicle implements Insurable {
    private final boolean luxurySegment;

    public Car(String registrationNumber, String model, boolean luxurySegment) {
        super(registrationNumber, model, VehicleType.CAR);
        this.luxurySegment = luxurySegment;
    }

    public boolean isLuxurySegment() {
        return luxurySegment;
    }

    @Override
    public double lateFee(int lateDays) {
        if (lateDays <= 0) {
            return 0.0;
        }
        double perDayPenalty = luxurySegment ? 1200.0 : 750.0;
        return lateDays * perDayPenalty;
    }

    @Override
    public double calculateInsuranceFee(int days) {
        double rate = luxurySegment ? 450.0 : 250.0;
        return Math.max(1, days) * rate;
    }

    @Override
    public String getCoverageDetails() {
        return luxurySegment ? "Comprehensive Zero-Dep Luxury Cover" : "Standard Comprehensive Car Cover";
    }
}
