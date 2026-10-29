
public class Order {
    int orderId;
    String customerId;
    double amount;
    String createdAt;

    public Order(int orderId, String customerId,
                 double amount, String createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId
                + ", Customer: " + customerId
                + ", Amount: " + amount
                + ", Created At: " + createdAt;
    }
}
