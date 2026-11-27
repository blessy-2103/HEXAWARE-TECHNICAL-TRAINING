// Task 3: String cleaning utilities (trim, case-normalise, split full name, mask phone)
public final class StringCleaner {

    private StringCleaner() {
    }

    public static String normaliseEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }
        return email.trim().toLowerCase();
    }

    public static String normaliseName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be null or blank.");
        }
        String[] parts = fullName.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String word = parts[i].toLowerCase();
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            if (i < parts.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String[] splitFullName(String fullName) {
        String clean = normaliseName(fullName);
        int lastSpace = clean.lastIndexOf(' ');
        if (lastSpace == -1) {
            return new String[] { clean, "" };
        }
        String firstName = clean.substring(0, lastSpace);
        String lastName = clean.substring(lastSpace + 1);
        return new String[] { firstName, lastName };
    }

    public static String maskPhone(String phone) {
        if (phone == null) {
            return "N/A";
        }
        String digits = phone.trim().replaceAll("[^0-9]", "");
        if (digits.length() < 4) {
            return "****";
        }
        String lastFour = digits.substring(digits.length() - 4);
        return "*".repeat(digits.length() - 4) + lastFour;
    }
}
