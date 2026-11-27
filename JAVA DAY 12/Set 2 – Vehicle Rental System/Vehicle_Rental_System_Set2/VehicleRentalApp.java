public class VehicleRentalApp {

    public static void main(String[] args) {
        Customer c1 = new Customer("CUST-101", "Pranav Subramanian", "TN0120210045678");
        Customer c2 = new Customer("CUST-102", "Nandini Venkat", "TN0720200098765");
        Customer c3 = new Customer("CUST-103", "Harish Balaji", "KA0320190012345");

        Vehicle car = new Car("TN-01-AB-4521", "Hyundai Creta", false);
        Vehicle bike = new Bike("TN-07-CD-8890", "Royal Enfield Classic 350", 350);
        Vehicle van = new Van("KA-03-EF-1122", "Force Traveller", 12);

        Rental[] rentals = {
            new Rental("RNT-501", car, c1, 4, 1),
            new Rental("RNT-502", bike, c2, 3, 2),
            new Rental("RNT-503", van, c3, 5, 3)
        };

        BillingService billingService = new BillingService();
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();

        System.out.println("================ VEHICLE RENTAL INVOICES ================");
        for (Rental rental : rentals) {
            receiptPrinter.printReceipt(rental, billingService);
        }
        System.out.println("------------------------------------------------------------");
    }
}
