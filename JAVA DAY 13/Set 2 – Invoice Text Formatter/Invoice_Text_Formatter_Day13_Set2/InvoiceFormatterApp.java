import java.util.List;

public class InvoiceFormatterApp {

    public static void main(String[] args) {
        // Task 1: Benchmark 1,000 line items
        int lineCount = 1000;
        long concatNs = PerformanceBenchmark.buildWithStringConcat(lineCount);
        long builderNs = PerformanceBenchmark.buildWithStringBuilder(lineCount);
        long bufferNs = PerformanceBenchmark.buildWithStringBuffer(lineCount);

        System.out.println("=== 1,000-Line-Item String Build Benchmark ===");
        System.out.printf("String '+' Concat : %,10d ns (%.3f ms)%n", concatNs, concatNs / 1_000_000.0);
        System.out.printf("StringBuilder     : %,10d ns (%.3f ms)%n", builderNs, builderNs / 1_000_000.0);
        System.out.printf("StringBuffer      : %,10d ns (%.3f ms)%n%n", bufferNs, bufferNs / 1_000_000.0);

        // Task 2 & 3: Build sample invoice and demonstrate toString() in logging
        RetailInvoice retail = new RetailInvoice("Ananya Krishnan", "CHN-04", "UPI");
        retail.addLine(new InvoiceLine("ITM-101", "Wireless Mechanical Keyboard", 1, 3499.00));
        retail.addLine(new InvoiceLine("ITM-102", "USB-C Multiport Hub", 2, 1299.50));
        retail.addLine(new InvoiceLine("ITM-103", "Laptop Stand Aluminium", 1, 1850.00));

        CorporateInvoice corporate = new CorporateInvoice(
            "Cauvery Logistics Pvt Ltd",
            "33AABCC1234D1Z5",
            "PO-2026-889",
            30
        );
        corporate.addLine(new InvoiceLine("SRV-201", "Enterprise Cloud Node (Monthly)", 4, 14500.00));
        corporate.addLine(new InvoiceLine("SRV-202", "Dedicated Support SLA", 1, 9500.00));

        // Logging uses toString() automatically when formatting objects
        System.out.println("[LOG] Created line item : " + retail.getLines().get(0));
        System.out.println("[LOG] Created invoice   : " + retail);
        System.out.println("[LOG] Created invoice   : " + corporate + "\n");

        // Task 5: instanceof with pattern matching for retail vs corporate invoices
        List<Invoice> invoices = List.of(retail, corporate);
        System.out.println("=== Polymorphic Invoice Output (instanceof Pattern Matching) ===");
        for (Invoice inv : invoices) {
            if (inv instanceof RetailInvoice rInv) {
                System.out.println(rInv.formatRetailSlip());
                System.out.println(rInv.formatFormattedInvoice());
            } else if (inv instanceof CorporateInvoice cInv) {
                System.out.println(cInv.formatCorporateTaxSchedule());
                System.out.println(cInv.formatFormattedInvoice());
            } else {
                System.out.println(inv.formatFormattedInvoice());
            }
        }
    }
}
