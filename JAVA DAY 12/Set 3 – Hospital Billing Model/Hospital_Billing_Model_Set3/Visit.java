public abstract class Visit {
    // Task 2: Immutable visit identifier
    private final String visitId;
    private final VisitType visitType;
    // Task 3: Composition (HAS-A Patient and Doctor)
    private final Patient patient;
    private final Doctor doctor;
    private final double consultationFee;

    protected Visit(String visitId, VisitType visitType, Patient patient, Doctor doctor, double consultationFee) {
        if (visitId == null || !visitId.matches("VST-[0-9]{4,6}")) {
            throw new IllegalArgumentException(
                "Invalid visit ID '" + visitId + "'. Expected format: VST-XXXX"
            );
        }
        if (visitType == null || patient == null || doctor == null) {
            throw new IllegalArgumentException("VisitType, Patient, and Doctor must not be null.");
        }
        if (consultationFee < 0) {
            throw new IllegalArgumentException("Consultation fee cannot be negative.");
        }
        this.visitId = visitId;
        this.visitType = visitType;
        this.patient = patient;
        this.doctor = doctor;
        this.consultationFee = consultationFee;
    }

    public String getVisitId() {
        return visitId;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    // Overridden by concrete visit subclasses
    public abstract double calculateBill();

    // Task 3: Overloaded calculateBill variants (discount % and insurance coverage amount)
    public double calculateBill(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
        }
        double gross = calculateBill();
        return gross - (gross * (discountPercent / 100.0));
    }

    public double calculateBill(double discountPercent, double insuranceCoverage) {
        if (insuranceCoverage < 0) {
            throw new IllegalArgumentException("Insurance coverage cannot be negative.");
        }
        double afterDiscount = calculateBill(discountPercent);
        return Math.max(0.0, afterDiscount - insuranceCoverage);
    }

    public String formatInvoiceLine() {
        return String.format(
            "%-9s | %-10s | %-18s | %-18s (%-11s) | Bill: ₹%,.2f",
            visitId,
            visitType,
            patient.getName(),
            doctor.getName(),
            doctor.getDepartment(),
            calculateBill()
        );
    }
}
