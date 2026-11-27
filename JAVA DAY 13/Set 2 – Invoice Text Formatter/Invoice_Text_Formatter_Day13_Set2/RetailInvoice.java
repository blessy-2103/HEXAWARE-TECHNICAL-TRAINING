public final class RetailInvoice extends Invoice {
    private final String storeCode;
    private final String paymentMode;

    public RetailInvoice(String customerName, String storeCode, String paymentMode) {
        super(customerName);
        this.storeCode = storeCode.trim();
        this.paymentMode = paymentMode.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public String formatRetailSlip() {
        return String.format(
            "[RETAIL RECEIPT] %s | Store: %s | Paid via: %s | Total: ₹%,.2f",
            getInvoiceNumber(),
            storeCode,
            paymentMode,
            getGrandTotal()
        );
    }
}
