import java.util.Objects;

public class Learner {
    private final String learnerId;
    private final String name;

    public Learner(String learnerId, String name) {
        if (learnerId == null || learnerId.isBlank()) {
            throw new IllegalArgumentException("Learner ID cannot be blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Learner name cannot be blank.");
        }
        this.learnerId = learnerId.strip();
        this.name = name.strip();
    }

    public String getLearnerId() {
        return learnerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Learner other)) {
            return false;
        }
        return Objects.equals(this.learnerId, other.learnerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.learnerId);
    }

    @Override
    public String toString() {
        return learnerId + " - " + name;
    }
}
