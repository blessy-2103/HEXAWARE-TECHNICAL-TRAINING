// Task 4: Interface with default method and static helper
public interface Notifier {

    void send(String recipient, String message);

    default void sendWelcome(Customer customer) {
        if (!isValidContact(customer.getEmail())) {
            System.out.println("[SKIP] Invalid contact for " + customer.getFullName());
            return;
        }
        String[] nameParts = StringCleaner.splitFullName(customer.getFullName());
        String msg = "Welcome " + nameParts[0] + "! Your CRM profile is verified.";
        send(customer.getEmail(), msg);
    }

    static boolean isValidContact(String value) {
        return value != null && !value.isBlank();
    }
}
