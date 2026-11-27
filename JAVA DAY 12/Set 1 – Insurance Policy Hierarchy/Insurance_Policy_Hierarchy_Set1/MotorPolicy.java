public class MotorPolicy extends Policy {
    private final int vehicleAgeYears;
    private final boolean zeroDepreciation;

    public MotorPolicy(String policyNumber, String holderName, double baseAmount,
                       PolicyStatus status, int vehicleAgeYears, boolean zeroDepreciation) {
        super(policyNumber, holderName, baseAmount, status);
        if (vehicleAgeYears < 0) {
            throw new IllegalArgumentException("Vehicle age cannot be negative.");
        }
        this.vehicleAgeYears = vehicleAgeYears;
        this.zeroDepreciation = zeroDepreciation;
    }

    @Override
    public double calculatePremium() {
        double rate = 0.025;
        if (vehicleAgeYears > 5) {
            rate += 0.005;
        }
        double premium = getBaseAmount() * rate;
        if (zeroDepreciation) {
            premium += 1800.0;
        }
        return premium;
    }
}
