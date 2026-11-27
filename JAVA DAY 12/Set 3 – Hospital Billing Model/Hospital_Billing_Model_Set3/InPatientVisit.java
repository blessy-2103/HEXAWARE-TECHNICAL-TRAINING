public class InPatientVisit extends Visit {
    private final int daysAdmitted;
    private final double perDayRoomCharge;
    private final double procedureCharges;

    public InPatientVisit(String visitId, Patient patient, Doctor doctor,
                          double consultationFee, int daysAdmitted,
                          double perDayRoomCharge, double procedureCharges) {
        super(visitId, VisitType.INPATIENT, patient, doctor, consultationFee);
        if (daysAdmitted <= 0) {
            throw new IllegalArgumentException("Days admitted must be at least 1.");
        }
        if (perDayRoomCharge < 0 || procedureCharges < 0) {
            throw new IllegalArgumentException("Room and procedure charges cannot be negative.");
        }
        this.daysAdmitted = daysAdmitted;
        this.perDayRoomCharge = perDayRoomCharge;
        this.procedureCharges = procedureCharges;
    }

    @Override
    public double calculateBill() {
        return getConsultationFee() + (daysAdmitted * perDayRoomCharge) + procedureCharges;
    }
}
