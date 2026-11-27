public class CorporateInvoice extends Invoice {
    private final String companyGstin;
    private final String purchaseOrderRef;
    private final int creditDays;

    public CorporateInvoice(String customerName, String companyGstin, String purchaseOrderRef, int creditDays) {
        super(customerName);
        this.companyGstin = companyGstin.trim();
        this.purchaseOrderRef = purchaseOrderRef.trim();
        this.creditDays = creditDays;
    }

    public String getCompanyGstin() {
        return companyGstin;
    }

    public String getPurchaseOrderRef() {
        return purchaseOrderRef;
    }

    public int getCreditDays() {
        return creditDays;
    }

    public String formatCorporateTaxSchedule() {
        return String.format(
            "[CORPORATE TAX INVOICE] %s | GSTIN: %s | PO: %s | Net %d Days | Subtotal: ₹%,.2f | GST: ₹%,.2f | Total: ₹%,.2f",
            getInvoiceNumber(),
            companyGstin,
            purchaseOrderRef,
            creditDays,
            getSubtotal(),
            getTaxAmount(),
            getGrandTotal()
        );
    }
}
