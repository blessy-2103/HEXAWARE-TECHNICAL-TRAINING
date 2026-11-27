import java.util.Objects;

// Task 1: Handwritten Customer class with equals, hashCode, and toString based on normalised email
public class Customer implements Auditable {
    private final String customerId;
    private final String fullName;
    private final String email; // stored normalised
    private final String phone;
    private final String channel;

    public Customer(String customerId, String fullName, String email, String phone, String channel) {
        this.customerId = customerId.trim();
        this.fullName = StringCleaner.normaliseName(fullName);
        this.email = StringCleaner.normaliseEmail(email);
        this.phone = phone.trim();
        this.channel = channel.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer other)) {
            return false;
        }
        return Objects.equals(this.email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email);
    }

    @Override
    public String toString() {
        return String.format(
            "Customer[id=%s, name=%s, email=%s, phone=%s, channel=%s]",
            customerId,
            fullName,
            email,
            StringCleaner.maskPhone(phone),
            channel
        );
    }
}
