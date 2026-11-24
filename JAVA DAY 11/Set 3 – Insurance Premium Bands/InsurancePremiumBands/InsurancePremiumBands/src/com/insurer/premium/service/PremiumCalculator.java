package com.insurer.premium.service;

import com.insurer.premium.model.AgeTermKey;
import com.insurer.premium.model.Applicant;
import com.insurer.premium.model.PremiumQuote;

public class PremiumCalculator {

    public double calculateBmi(double weightKg, double heightMeters) {
        if (heightMeters <= 0 || weightKg <= 0) {
            return -1.0;
        }
        return weightKg / (heightMeters * heightMeters);
    }

    // Task 1: Classify BMI and assign loading percentage using nested if statements
    public String classifyBmi(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else {
            if (bmi < 25.0) {
                return "Normal";
            } else {
                if (bmi < 30.0) {
                    return "Overweight";
                } else {
                    return "Obese";
                }
            }
        }
    }

    public double getBmiLoadingPercent(double bmi) {
        if (bmi < 18.5) {
            return 5.0;
        } else {
            if (bmi < 25.0) {
                return 0.0;
            } else {
                if (bmi < 30.0) {
                    return 15.0;
                } else {
                    if (bmi < 35.0) {
                        return 25.0;
                    } else {
                        return 40.0;
                    }
                }
            }
        }
    }

    // Task 2: Switch expression with pattern matching and 'when' guards
    public double getBasePremium(AgeTermKey key) {
        return switch (key) {
            case AgeTermKey(int age, int term) when age >= 18 && age <= 30 && term <= 10 -> 4500.0;
            case AgeTermKey(int age, int term) when age >= 18 && age <= 30 && term > 10  -> 6200.0;
            case AgeTermKey(int age, int term) when age >= 31 && age <= 45 && term <= 10 -> 7800.0;
            case AgeTermKey(int age, int term) when age >= 31 && age <= 45 && term > 10  -> 9800.0;
            case AgeTermKey(int age, int term) when age >= 46 && age <= 60 && term <= 10 -> 12500.0;
            case AgeTermKey(int age, int term) when age >= 46 && age <= 60 && term > 10  -> 15200.0;
            default -> -1.0; // Ineligible age band
        };
    }

    // Task 3: Final premium calculation with arithmetic, comparison, and logical operators
    public PremiumQuote evaluate(Applicant app) {
        double bmi = calculateBmi(app.weightKg(), app.heightMeters());

        // Eligibility check using comparison (<, >, <=) and logical (||) operators:
        // Precedence: Comparison operators (<, >, <=) evaluate before logical OR (||) left-to-right.
        boolean isIneligible = app.age() < 18 || app.age() > 60 || bmi <= 0 || bmi >= 40.0 || app.policyTermYears() <= 0;
        if (isIneligible) {
            return new PremiumQuote(app.id(), app.name(), Math.max(bmi, 0.0), "Ineligible", 0.0, 0.0, 0.0, "INELIGIBLE");
        }

        String bmiCategory = classifyBmi(bmi);
        double loadingPercent = getBmiLoadingPercent(bmi);
        double basePremium = getBasePremium(new AgeTermKey(app.age(), app.policyTermYears()));

        // Smoker surcharge percentage using comparison (&&, >=) and ternary operator:
        // Precedence: Comparison (>=) evaluates first, then logical AND (&&), then ternary (?:).
        double smokerPercent = (app.smoker() && app.age() >= 35) ? 30.0 : (app.smoker() ? 20.0 : 0.0);

        // Final premium arithmetic expression:
        // Precedence:
        // 1. Parentheses (...) evaluate first: (loadingPercent + smokerPercent)
        // 2. Division (/) and Multiplication (*) have equal precedence and evaluate left-to-right:
        //    basePremium * ((loadingPercent + smokerPercent) / 100.0)
        // 3. Addition (+) evaluates last: basePremium + <surchargeAmount>
        double finalPremium = basePremium + basePremium * ((loadingPercent + smokerPercent) / 100.0);

        return new PremiumQuote(
            app.id(),
            app.name(),
            bmi,
            bmiCategory,
            loadingPercent + smokerPercent,
            basePremium,
            finalPremium,
            "ELIGIBLE"
        );
    }
}
