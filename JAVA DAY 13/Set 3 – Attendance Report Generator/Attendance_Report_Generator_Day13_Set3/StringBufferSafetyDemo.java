// Task 5: Thread-safety comparison between StringBuilder and StringBuffer
public class StringBufferSafetyDemo {

    public static void runConcurrentAppendDemo() throws InterruptedException {
        int threadsCount = 10;
        int appendsPerThread = 200;

        StringBuilder unsafeBuilder = new StringBuilder();
        StringBuffer safeBuffer = new StringBuffer();

        Runnable unsafeTask = () -> {
            for (int i = 0; i < appendsPerThread; i++) {
                unsafeBuilder.append("X");
            }
        };

        Runnable safeTask = () -> {
            for (int i = 0; i < appendsPerThread; i++) {
                safeBuffer.append("X");
            }
        };

        Thread[] t1 = new Thread[threadsCount];
        Thread[] t2 = new Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            t1[i] = new Thread(unsafeTask);
            t2[i] = new Thread(safeTask);
            t1[i].start();
            t2[i].start();
        }
        for (int i = 0; i < threadsCount; i++) {
            t1[i].join();
            t2[i].join();
        }

        int expectedLength = threadsCount * appendsPerThread;
        System.out.printf("Expected length : %d%n", expectedLength);
        System.out.printf("StringBuilder   : %d (unsynchronised; race conditions possible)%n", unsafeBuilder.length());
        System.out.printf("StringBuffer    : %d (synchronised methods; thread-safe)%n", safeBuffer.length());
    }
}
