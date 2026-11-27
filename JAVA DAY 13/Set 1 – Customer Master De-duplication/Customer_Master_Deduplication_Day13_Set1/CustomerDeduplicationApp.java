import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerDeduplicationApp {

    public static void main(String[] args) {
        // Task 1: Incoming CRM records from 3 channels (Web, MobileApp, StorePOS)
        List<Customer> incoming = List.of(
            new Customer("C-101", "  arjun   nair ", "Arjun.Nair@Example.com ", "+91 98401 23456", "Web"),
            new Customer("C-102", "MEERA KRISHNAN", "meera.k@example.com", "9840987654", "MobileApp"),
            new Customer("C-103", "Arjun Nair", "  arjun.nair@example.com", "9840123456", "MobileApp"),
            new Customer("C-104", "karthik sundaram", "KARTHIK.S@EXAMPLE.COM", "+91-94440-11223", "StorePOS"),
            new Customer("C-105", "Meera K.", "Meera.K@Example.COM ", "9840987654", "StorePOS"),
            new Customer("C-106", "divya menon", "divya.menon@example.com", "9123456780", "Web")
        );

        System.out.println("=== 1. Incoming CRM Records (" + incoming.size() + " total) ===");
        incoming.forEach(System.out::println);

        // Deduplication using HashSet (relies on Customer.hashCode() and Customer.equals())
        Set<Customer> uniqueCustomers = new HashSet<>(incoming);
        System.out.println("\n=== 2. Deduplicated Master Customers (" + uniqueCustomers.size() + " unique) ===");
        uniqueCustomers.forEach(System.out::println);

        // Task 2: Record comparison (compares ALL record components by default!)
        System.out.println("\n=== 3. Java Record Comparison ===");
        CustomerRecord r1 = new CustomerRecord("C-101", "arjun nair", "Arjun.Nair@Example.com", "9840123456", "Web");
        CustomerRecord r2 = new CustomerRecord("C-103", "Arjun Nair", "arjun.nair@example.com", "9840123456", "MobileApp");
        CustomerRecord r3 = new CustomerRecord("C-101", "ARJUN NAIR", "arjun.nair@example.com", "9840123456", "Web");
        System.out.println("r1.equals(r2) [different id/channel]: " + r1.equals(r2));
        System.out.println("r1.equals(r3) [same normalised components]: " + r1.equals(r3));

        // Task 3: String cleaning, immutability & String Constant Pool
        System.out.println("\n=== 4. String Cleaning, Immutability & Constant Pool ===");
        String raw = "  arjun.nair@example.com  ";
        String trimmed = raw.trim(); // creates a new String; raw remains unchanged
        System.out.println("Original raw length : " + raw.length() + " | Trimmed length: " + trimmed.length());

        String poolLiteral1 = "arjun.nair@example.com";
        String poolLiteral2 = "arjun.nair@example.com";
        String heapString = new String("arjun.nair@example.com");
        String internedString = heapString.intern();

        System.out.println("poolLiteral1 == poolLiteral2      : " + (poolLiteral1 == poolLiteral2));
        System.out.println("poolLiteral1 == heapString        : " + (poolLiteral1 == heapString));
        System.out.println("poolLiteral1.equals(heapString)   : " + poolLiteral1.equals(heapString));
        System.out.println("poolLiteral1 == internedString    : " + (poolLiteral1 == internedString));

        String[] split = StringCleaner.splitFullName("  ravi shankar menon ");
        System.out.println("Split Name -> First: '" + split[0] + "', Last: '" + split[1] + "'");
        System.out.println("Masked Phone -> " + StringCleaner.maskPhone("+91 98401 23456"));

        // Task 4: Notifier interface with default and static methods
        System.out.println("\n=== 5. Notifier Interface Demo ===");
        Notifier emailNotifier = new EmailNotifier();
        Notifier smsNotifier = new SmsNotifier();
        for (Customer c : uniqueCustomers) {
            emailNotifier.sendWelcome(c);
            smsNotifier.sendWelcome(c);
        }

        // Task 5: Marker interface (Auditable) & Functional interface (CustomerFilter)
        System.out.println("\n=== 6. Marker & Functional Interface Demo ===");
        CustomerFilter webChannelFilter = c -> "Web".equalsIgnoreCase(c.getChannel());
        List<Customer> webCustomers = new ArrayList<>();
        for (Customer c : uniqueCustomers) {
            if (c instanceof Auditable && webChannelFilter.test(c)) {
                webCustomers.add(c);
            }
        }
        System.out.println("Auditable Web-channel customers: " + webCustomers);
    }
}
