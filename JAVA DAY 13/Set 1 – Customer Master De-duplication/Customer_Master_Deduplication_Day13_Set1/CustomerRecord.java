// Task 2: Java Record version of Customer value object
public record CustomerRecord(
    String customerId,
    String fullName,
    String email,
    String phone,
    String channel
) implements Auditable {

    // Compact canonical constructor normalises inputs on creation
    public CustomerRecord {
        customerId = customerId.trim();
        fullName = StringCleaner.normaliseName(fullName);
        email = StringCleaner.normaliseEmail(email);
        phone = phone.trim();
        channel = channel.trim();
    }

    @Override
    public String toString() {
        return String.format(
            "CustomerRecord[id=%s, name=%s, email=%s, phone=%s, channel=%s]",
            customerId,
            fullName,
            email,
            StringCleaner.maskPhone(phone),
            channel
        );
    }
}
