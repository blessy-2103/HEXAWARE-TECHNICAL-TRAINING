# Loan Eligibility Engine (Java JDK 25 LTS)

A bank loan pre-screening engine built with Java 25 LTS demonstrating control statements, pattern-matching `switch` expressions, `var` type inference, records, `printf` formatting, and edge-case validation.

## Project Structure

```text
LoanEligibilityEngine/
├── .gitignore
├── README.md
└── src/
    └── com/bank/loan/
        ├── model/
        │   ├── Applicant.java
        │   └── LoanOffer.java
        ├── service/
        │   └── EligibilityEngine.java
        └── LoanEligibilityApp.java
```

## Build & Run

```bash
javac -d out src/com/bank/loan/model/*.java src/com/bank/loan/service/*.java src/com/bank/loan/LoanEligibilityApp.java
java -cp out com.bank.loan.LoanEligibilityApp
```
