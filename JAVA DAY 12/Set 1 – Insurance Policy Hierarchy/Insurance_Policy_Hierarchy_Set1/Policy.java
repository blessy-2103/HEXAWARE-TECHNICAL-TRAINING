public abstract class Policy {
    private final String policyNumber;
    private final String holderName;
    private final double baseAmount;
    private PolicyStatus status;

    protected Policy(String policyNumber, String holderName, double baseAmount, PolicyStatus status) {
        if (policyNumber == null || !policyNumber.matches("POL-[A-Z0-9]{4,10}")) {
            throw new IllegalArgumentException(
                "Invalid policy number '" + policyNumber + "'. Expected format: POL-XXXX (4-10 alphanumeric chars)."
            );
        }
        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException("Policy holder name cannot be empty.");
        }
        if (baseAmount <= 0) {
            throw new IllegalArgumentException("Base coverage amount must be greater than zero.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Policy status cannot be null.");
        }

        this.policyNumber = policyNumber;
        this.holderName = holderName.trim();
        this.baseAmount = baseAmount;
        this.status = status;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Policy status cannot be null.");
        }
        this.status = status;
    }

    public abstract double calculatePremium();

    public String getPolicyType() {
        return getClass().getSimpleName().replace("Policy", "");
    }

    public String summaryLine() {
        return String.format(
            "%-10s | %-10s | %-18s | %-9s | Premium: ₹%,.2f",
            policyNumber,
            getPolicyType(),
            holderName,
            status,
            calculatePremium()
        );
    }
}
