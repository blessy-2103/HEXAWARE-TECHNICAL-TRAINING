public class TravelPolicy extends Policy {
    private final int tripDays;
    private final boolean international;

    public TravelPolicy(String policyNumber, String holderName, double baseAmount,
                        PolicyStatus status, int tripDays, boolean international) {
        super(policyNumber, holderName, baseAmount, status);
        if (tripDays <= 0) {
            throw new IllegalArgumentException("Trip duration must be at least 1 day.");
        }
        this.tripDays = tripDays;
        this.international = international;
    }

    @Override
    public double calculatePremium() {
        double dailyRate = international ? 350.0 : 120.0;
        double baseFee = getBaseAmount() * 0.002;
        return baseFee + (tripDays * dailyRate);
    }
}
