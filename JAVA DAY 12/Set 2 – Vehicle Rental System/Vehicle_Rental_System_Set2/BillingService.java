// Task 5: Extracted from God class (Single Responsibility Principle - handles billing calculations)
public class BillingService {

    public double calculateInsuranceAmount(Rental rental) {
        Vehicle vehicle = rental.getVehicle();
        if (vehicle instanceof Insurable insurable) {
            return insurable.calculateInsuranceFee(rental.getDaysUsed());
        }
        return 0.0;
    }

    public double calculateLateFeeAmount(Rental rental) {
        return rental.getVehicle().lateFee(rental.getLateDays());
    }

    public double calculateTotalPayable(Rental rental) {
        return rental.calculateBaseRentalAmount()
             + calculateInsuranceAmount(rental)
             + calculateLateFeeAmount(rental);
    }
}
