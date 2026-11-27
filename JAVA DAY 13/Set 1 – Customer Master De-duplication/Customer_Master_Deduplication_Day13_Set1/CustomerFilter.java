// Task 5: Functional interface (single abstract method - usable with lambda expressions)
@FunctionalInterface
public interface CustomerFilter {
    boolean test(Customer customer);
}
