import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Invoice {
    // Task 4: final tax rate (18% GST), static counter, and static initialiser block
    public static final double GST_RATE = 0.18;
    private static int invoiceSequence;

    static {
        invoiceSequence = 1000;
        System.out.println("[Invoice Static Initialiser] Sequence initialised to INV-" + invoiceSequence);
    }

    private final String invoiceNumber;
    private final String customerName;
    private final List<InvoiceLine> lines = new ArrayList<>();

    public Invoice(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        invoiceSequence++;
        this.invoiceNumber = "INV-" + invoiceSequence;
        this.customerName = customerName.trim();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void addLine(InvoiceLine line) {
        if (line == null) {
            throw new IllegalArgumentException("InvoiceLine cannot be null.");
        }
        lines.add(line);
    }

    public List<InvoiceLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public double getSubtotal() {
        double sum = 0.0;
        for (InvoiceLine line : lines) {
            sum += line.getLineTotal();
        }
        return sum;
    }

    public double getTaxAmount() {
        return getSubtotal() * GST_RATE;
    }

    public double getGrandTotal() {
        return getSubtotal() + getTaxAmount();
    }

    public String formatFormattedInvoice() {
        StringBuilder linesBlock = new StringBuilder();
        linesBlock.append(String.format(
            "%-10s | %-28s | %4s | %11s | %12s%n",
            "SKU", "ITEM DESCRIPTION", "QTY", "UNIT PRICE", "LINE TOTAL"
        ));
        linesBlock.append("-".repeat(82)).append(System.lineSeparator());
        for (InvoiceLine line : lines) {
            linesBlock.append(line.toString()).append(System.lineSeparator());
        }

        // Task 2: Text block template with String.format and Indian Rupee symbol (₹)
        String template = """
                ==================================================================================
                                            TAX INVOICE (GST @ %.0f%%)
                ==================================================================================
                Invoice No : %-20s Customer : %s
                ----------------------------------------------------------------------------------
                %s----------------------------------------------------------------------------------
                Subtotal   : ₹%,12.2f
                GST (18%%)  : ₹%,12.2f
                Grand Total: ₹%,12.2f
                ==================================================================================
                """;

        return String.format(
            template,
            GST_RATE * 100,
            invoiceNumber,
            customerName,
            linesBlock.toString(),
            getSubtotal(),
            getTaxAmount(),
            getGrandTotal()
        );
    }

    @Override
    public String toString() {
        return String.format(
            "Invoice[number=%s, customer=%s, lineCount=%d, grandTotal=₹%,.2f]",
            invoiceNumber,
            customerName,
            lines.size(),
            getGrandTotal()
        );
    }
}
