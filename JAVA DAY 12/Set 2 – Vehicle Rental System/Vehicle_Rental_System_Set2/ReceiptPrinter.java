// Task 5: Extracted from God class (Single Responsibility Principle - handles presentation/printing)
public class ReceiptPrinter {

    public void printReceipt(Rental rental, BillingService billingService) {
        Vehicle v = rental.getVehicle();
        Customer c = rental.getCustomer();

        double baseAmount = rental.calculateBaseRentalAmount();
        double insuranceAmount = billingService.calculateInsuranceAmount(rental);
        double lateFeeAmount = billingService.calculateLateFeeAmount(rental);
        double totalAmount = billingService.calculateTotalPayable(rental);

        System.out.println("------------------------------------------------------------");
        System.out.printf("Rental ID     : %s%n", rental.getRentalId());
        System.out.printf("Customer      : %s (%s) | DL: %s%n", c.getName(), c.getCustomerId(), c.getLicenseNumber());
        System.out.printf("Vehicle       : %s [%s - %s]%n", v.getModel(), v.getType(), v.getRegistrationNumber());
        if (v instanceof Insurable insurable) {
            System.out.printf("Insurance     : %s%n", insurable.getCoverageDetails());
        } else {
            System.out.println("Insurance     : Not Applicable");
        }
        System.out.printf("Base Rental   : %d day(s) @ ₹%,.2f/day = ₹%,.2f%n",
                rental.getDaysUsed(), v.getDailyRate(), baseAmount);
        System.out.printf("Insurance Fee : ₹%,.2f%n", insuranceAmount);
        System.out.printf("Late Fee      : %d late day(s) = ₹%,.2f%n", rental.getLateDays(), lateFeeAmount);
        System.out.printf("TOTAL PAYABLE : ₹%,.2f%n", totalAmount);
    }
}
