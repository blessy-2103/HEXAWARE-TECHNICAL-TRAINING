public class OutPatientVisit extends Visit {
    private final double diagnosticCharges;

    public OutPatientVisit(String visitId, Patient patient, Doctor doctor,
                           double consultationFee, double diagnosticCharges) {
        super(visitId, VisitType.OUTPATIENT, patient, doctor, consultationFee);
        if (diagnosticCharges < 0) {
            throw new IllegalArgumentException("Diagnostic charges cannot be negative.");
        }
        this.diagnosticCharges = diagnosticCharges;
    }

    @Override
    public double calculateBill() {
        return getConsultationFee() + diagnosticCharges;
    }
}
