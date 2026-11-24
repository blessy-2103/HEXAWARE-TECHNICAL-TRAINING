## JVM Memory Model Explanation (Stack vs. Heap & Garbage Collection)

### 1. Stack Memory (Thread-Local Execution Frames)
* **Primitive local variables**: `bmi`, `loadingPercent`, `basePremium`, `smokerPercent`, `finalPremium`, `isIneligible`, and unpacked pattern variables (`int age`, `int term`) are stored directly in method stack frames.
* **Object references**: Local reference variables (`calculator`, `applicants`, `app`, `quote`, `bmiCategory`) store memory addresses on the Stack that point to objects allocated in the Heap.
* **Lifecycle**: Each method call (`main`, `evaluate`, `getBasePremium`) pushes a new stack frame onto the thread stack and pops it immediately when the method returns.

### 2. Heap Memory (Shared Object Storage)
* **Object instances**: The `PremiumCalculator` object, the `List` created by `List.of(...)`, all eight `Applicant` record instances, transient `AgeTermKey` record instances, `PremiumQuote` record instances, and `String` objects (with string literals stored in the String Pool) reside in the Heap.
* **Instance fields**: All fields inside `Applicant` and `PremiumQuote` records live inside their respective object bodies in the Heap.

### 3. Garbage Collection (Unreachable Objects)
* Temporary objects created per loop iteration (such as `new AgeTermKey(...)` and `PremiumQuote` after the iteration ends) lose all live references from the Stack and become **unreachable**.
* When `main()` finishes (or references go out of scope), the remaining `Applicant` and `PremiumCalculator` instances also become unreachable.
* The **JVM Garbage Collector** traces live references from GC Roots (active stack frames, static fields) and automatically reclaims memory occupied by unreachable Heap objects.
