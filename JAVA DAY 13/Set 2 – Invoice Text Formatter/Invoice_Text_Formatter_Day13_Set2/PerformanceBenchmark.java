// Task 1: Measure 1,000 line items using String concatenation, StringBuilder, and StringBuffer
public final class PerformanceBenchmark {

    private PerformanceBenchmark() {
    }

    public static long buildWithStringConcat(int itemCount) {
        long start = System.nanoTime();
        String output = "";
        for (int i = 1; i <= itemCount; i++) {
            output = output + "SKU-" + i + " | Item " + i + " | Qty: 1 | ₹100.00\n";
        }
        long elapsed = System.nanoTime() - start;
        if (output.isEmpty()) {
            throw new IllegalStateException("Unexpected empty string");
        }
        return elapsed;
    }

    public static long buildWithStringBuilder(int itemCount) {
        long start = System.nanoTime();
        StringBuilder sb = new StringBuilder(itemCount * 48);
        for (int i = 1; i <= itemCount; i++) {
            sb.append("SKU-").append(i)
              .append(" | Item ").append(i)
              .append(" | Qty: 1 | ₹100.00\n");
        }
        String output = sb.toString();
        long elapsed = System.nanoTime() - start;
        if (output.isEmpty()) {
            throw new IllegalStateException("Unexpected empty string");
        }
        return elapsed;
    }

    public static long buildWithStringBuffer(int itemCount) {
        long start = System.nanoTime();
        StringBuffer sbuf = new StringBuffer(itemCount * 48);
        for (int i = 1; i <= itemCount; i++) {
            sbuf.append("SKU-").append(i)
                .append(" | Item ").append(i)
                .append(" | Qty: 1 | ₹100.00\n");
        }
        String output = sbuf.toString();
        long elapsed = System.nanoTime() - start;
        if (output.isEmpty()) {
            throw new IllegalStateException("Unexpected empty string");
        }
        return elapsed;
    }
}
