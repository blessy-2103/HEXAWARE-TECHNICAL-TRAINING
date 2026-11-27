public class EmergencyVisit extends Visit {
    private final double emergencySurcharge;
    private final double traumaCareCharges;

    public EmergencyVisit(String visitId, Patient patient, Doctor doctor,
                          double consultationFee, double emergencySurcharge, double traumaCareCharges) {
        super(visitId, VisitType.EMERGENCY, patient, doctor, consultationFee);
        if (emergencySurcharge < 0 || traumaCareCharges < 0) {
            throw new IllegalArgumentException("Surcharge and trauma charges cannot be negative.");
        }
        this.emergencySurcharge = emergencySurcharge;
        this.traumaCareCharges = traumaCareCharges;
    }

    @Override
    public double calculateBill() {
        return getConsultationFee() + emergencySurcharge + traumaCareCharges;
    }
}
