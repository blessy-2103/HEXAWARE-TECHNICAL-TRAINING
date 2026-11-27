public class EmailNotifier implements Notifier {

    @Override
    public void send(String recipient, String message) {
        System.out.printf("[EMAIL -> %s] %s%n", recipient, message);
    }
}
