public class InvoiceLine {
    private final String sku;
    private final String description;
    private final int quantity;
    private final double unitPrice;

    public InvoiceLine(String sku, String description, int quantity, double unitPrice) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU cannot be empty.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative.");
        }
        this.sku = sku.trim();
        this.description = description.trim();
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getSku() {
        return sku;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getLineTotal() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return String.format(
            "%-10s | %-28s | %4d | ₹%,10.2f | ₹%,11.2f",
            sku,
            description,
            quantity,
            unitPrice,
            getLineTotal()
        );
    }
}
