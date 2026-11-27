public class HomePolicy extends Policy {
    private final double propertyValue;
    private final boolean floodCover;

    public HomePolicy(String policyNumber, String holderName, double baseAmount,
                      PolicyStatus status, double propertyValue, boolean floodCover) {
        super(policyNumber, holderName, baseAmount, status);
        if (propertyValue <= 0) {
            throw new IllegalArgumentException("Property value must be greater than zero.");
        }
        this.propertyValue = propertyValue;
        this.floodCover = floodCover;
    }

    @Override
    public double calculatePremium() {
        double premium = (getBaseAmount() * 0.004) + (propertyValue * 0.0012);
        if (floodCover) {
            premium += 2500.0;
        }
        return premium;
    }
}
