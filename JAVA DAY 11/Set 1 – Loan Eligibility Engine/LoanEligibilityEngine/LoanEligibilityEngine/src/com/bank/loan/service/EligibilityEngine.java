package com.bank.loan.service;

import com.bank.loan.model.Applicant;
import com.bank.loan.model.LoanOffer;

public class EligibilityEngine {

    public boolean isValidRecord(Applicant app) {
        if (app == null) {
            return false;
        }
        if (app.age() <= 0 || app.age() > 100) {
            return false;
        }
        if (app.monthlyIncome() <= 0 || app.proposedEmi() < 0) {
            return false;
        }
        if (app.creditScore() < 300 || app.creditScore() > 900) {
            return false;
        }
        return app.employmentType() != null && !app.employmentType().isBlank();
    }

    public LoanOffer evaluate(Applicant app) {
        // Rule 1: Age band (21 - 60)
        if (app.age() < 21 || app.age() > 60) {
            return new LoanOffer("REJECTED", "N/A", "None", 0.0, "Age outside 21-60 range");
        }

        // Rule 2: Income-to-EMI ratio (proposed EMI must be <= 40% of monthly income)
        var maxAllowedEmi = app.monthlyIncome() * 0.40;
        if (app.proposedEmi() > maxAllowedEmi) {
            return new LoanOffer("REJECTED", "N/A", "None", 0.0, "EMI exceeds 40% of income");
        }

        // Rule 3: Credit-score band classification (if-else ladder)
        String creditBand;
        if (app.creditScore() >= 750) {
            creditBand = "EXCELLENT";
        } else if (app.creditScore() >= 700) {
            creditBand = "GOOD";
        } else if (app.creditScore() >= 650) {
            creditBand = "FAIR";
        } else {
            return new LoanOffer("REJECTED", "POOR", "None", 0.0, "Credit score below 650");
        }

        // Rule 4 & 5: Employment type and existing-loan limit (nested conditions)
        var empType = app.employmentType().trim().toUpperCase();
        if (empType.equals("SALARIED")) {
            if (app.existingLoans() > 3) {
                return new LoanOffer("REJECTED", creditBand, "None", 0.0, "Max 3 active loans for Salaried");
            }
        } else if (empType.equals("SELF_EMPLOYED")) {
            if (app.existingLoans() > 2) {
                return new LoanOffer("REJECTED", creditBand, "None", 0.0, "Max 2 active loans for Self-Employed");
            }
            if (app.creditScore() < 700) {
                return new LoanOffer("REJECTED", creditBand, "None", 0.0, "Self-Employed needs score >= 700");
            }
        } else {
            return new LoanOffer("REJECTED", creditBand, "None", 0.0, "Unsupported employment type");
        }

        // Rule 6: Enhanced switch expression with pattern matching and guards
        var offerDetails = mapBandToProduct(creditBand, app.monthlyIncome());

        return new LoanOffer(
            "APPROVED",
            creditBand,
            offerDetails.product(),
            offerDetails.interestRate(),
            "All rules satisfied"
        );
    }

    private LoanOffer mapBandToProduct(Object bandObj, double income) {
        return switch (bandObj) {
            case String s when s.equals("EXCELLENT") && income >= 100000 ->
                new LoanOffer("APPROVED", s, "Platinum Prime Loan", 8.25, "");
            case String s when s.equals("EXCELLENT") ->
                new LoanOffer("APPROVED", s, "Gold Personal Loan", 8.90, "");
            case String s when s.equals("GOOD") ->
                new LoanOffer("APPROVED", s, "Standard Personal Loan", 10.50, "");
            case String s when s.equals("FAIR") ->
                new LoanOffer("APPROVED", s, "Secured Starter Loan", 12.75, "");
            default ->
                new LoanOffer("REJECTED", "UNKNOWN", "None", 0.0, "Unmapped band");
        };
    }
}
