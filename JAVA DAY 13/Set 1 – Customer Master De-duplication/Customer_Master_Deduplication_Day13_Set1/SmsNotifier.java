public class SmsNotifier implements Notifier {

    @Override
    public void send(String recipient, String message) {
        System.out.printf("[SMS   -> %s] %s%n", recipient, message);
    }

    @Override
    public void sendWelcome(Customer customer) {
        String masked = StringCleaner.maskPhone(customer.getPhone());
        if (!Notifier.isValidContact(customer.getPhone())) {
            System.out.println("[SKIP] Invalid phone for " + customer.getFullName());
            return;
        }
        String[] nameParts = StringCleaner.splitFullName(customer.getFullName());
        send(masked, "Hi " + nameParts[0] + ", your CRM registration is active.");
    }
}
