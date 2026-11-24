# Insurance Premium Bands (Java JDK 25 LTS)

Set 3 solution for **Day 11 – Java (JDK 25 LTS)** covering BMI classification with nested `if` statements, pattern-matching `switch` expressions with `when` guards, operator precedence, enhanced `for` loop with labelled `break`, and JVM Stack/Heap memory model analysis.

## Project Structure

```text
InsurancePremiumBands/
├── .gitignore
├── README.md
└── src/
    └── com/insurer/premium/
        ├── model/
        │   ├── AgeTermKey.java
        │   ├── Applicant.java
        │   └── PremiumQuote.java
        ├── service/
        │   └── PremiumCalculator.java
        └── InsurancePremiumApp.java
```

## Build & Run

```bash
javac -d out src/com/insurer/premium/model/*.java src/com/insurer/premium/service/*.java src/com/insurer/premium/InsurancePremiumApp.java
java -cp out com.insurer.premium.InsurancePremiumApp
```

## Console Output (Batch of Eight Applicants)

```text
=====================================================================================================
ID       | NAME            | BMI    | CATEGORY     | LOAD %   | BASE (INR)   | FINAL (INR)   | STATUS    
=====================================================================================================
INS-01   | Aarav Mehta     |  22.20 | Normal       |     0.0% |      4500.00 |       4500.00 | ELIGIBLE  
INS-02   | Sneha Pillai    |  22.77 | Normal       |    20.0% |      6200.00 |       7440.00 | ELIGIBLE  
INS-03   | Rohan Kulkarni  |  29.75 | Overweight   |    15.0% |      7800.00 |       8970.00 | ELIGIBLE  
INS-04   | Divya Menon     |  17.01 | Underweight  |     5.0% |      6200.00 |       6510.00 | ELIGIBLE  
INS-05   | Vikram Joshi    |  33.22 | Obese        |    55.0% |     12500.00 |      19375.00 | ELIGIBLE  
INS-06   | Karan Verma     |  24.77 | Ineligible   |     0.0% |         0.00 |          0.00 | INELIGIBLE
-----------------------------------------------------------------------------------------------------
[BATCH HALTED] Ineligible applicant detected (INS-06 - Karan Verma). Labelled break triggered.
=====================================================================================================
```

## Operator Precedence Summary

1. **Eligibility Check (`app.age() < 18 || app.age() > 60 || bmi <= 0 || bmi >= 40.0 || app.policyTermYears() <= 0`)**:
   - Relational operators (`<`, `>`, `<=`, `>=`) have higher precedence than logical OR (`||`), so every comparison resolves to a `boolean` first before short-circuit `||` evaluates left-to-right.
2. **Smoker Loading (`(app.smoker() && app.age() >= 35) ? 30.0 : (app.smoker() ? 20.0 : 0.0)`)**:
   - Relational `>=` evaluates first, followed by logical AND `&&`, and finally the conditional ternary `?:`.
3. **Final Premium (`basePremium + basePremium * ((loadingPercent + smokerPercent) / 100.0)`)**:
   - Parentheses `(...)` evaluate first (`loadingPercent + smokerPercent`), then division `/` and multiplication `*` left-to-right, and finally addition `+`.

## JVM Memory Model Explanation (Stack vs. Heap & Garbage Collection)

### 1. Stack Memory (Thread-Local Execution Frames)
- **Primitive local variables**: `bmi`, `loadingPercent`, `basePremium`, `smokerPercent`, `finalPremium`, `isIneligible`, and primitive record components while unpacked in stack frames (`int age`, `int term`).
- **Object references**: Local reference variables such as `calculator`, `applicants`, `app`, `quote`, and `bmiCategory` store memory addresses (pointers) on the Stack pointing to objects in the Heap.
- **Lifecycle**: Each method call (`main`, `evaluate`, `getBasePremium`) pushes a new stack frame; all local primitives and reference variables are popped immediately when the method returns.

### 2. Heap Memory (Shared Object Storage)
- **Object instances**: `PremiumCalculator` instance, the immutable `List` created by `List.of(...)`, all eight `Applicant` record instances, transient `AgeTermKey` record instances, `PremiumQuote` record instances, and `String` objects (`"Normal"`, `"ELIGIBLE"`, etc., with string literals interned in the String Pool).
- **Instance fields**: All fields inside `Applicant` and `PremiumQuote` records reside inside their respective object headers/bodies on the Heap.

### 3. Garbage Collection (Unreachable Objects)
- Inside each loop iteration, temporary objects like `new AgeTermKey(...)` and `PremiumQuote` become **unreachable** as soon as the iteration finishes and no live stack reference points to them.
- Once `main()` exits (or references go out of scope), the `Applicant` objects and `PremiumCalculator` also become unreachable.
- The **JVM Garbage Collector (G1/ZGC in JDK 25)** automatically identifies unreachable objects via reachability analysis from GC Roots (active stack frames, static references) and reclaims their Heap memory during minor/major GC cycles.
