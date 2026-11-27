public class HealthPolicy extends Policy {
    private final int insuredMembers;
    private final int eldestMemberAge;

    public HealthPolicy(String policyNumber, String holderName, double baseAmount,
                        PolicyStatus status, int insuredMembers, int eldestMemberAge) {
        super(policyNumber, holderName, baseAmount, status);
        if (insuredMembers <= 0 || eldestMemberAge <= 0) {
            throw new IllegalArgumentException("Members count and age must be positive.");
        }
        this.insuredMembers = insuredMembers;
        this.eldestMemberAge = eldestMemberAge;
    }

    @Override
    public double calculatePremium() {
        double rate = 0.015;
        if (eldestMemberAge >= 45) {
            rate += 0.008;
        }
        double memberLoading = (insuredMembers - 1) * 1200.0;
        return (getBaseAmount() * rate) + Math.max(0.0, memberLoading);
    }
}
