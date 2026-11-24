package com.insurer.premium;

import com.insurer.premium.model.Applicant;
import com.insurer.premium.service.PremiumCalculator;
import java.util.List;

public class InsurancePremiumApp {
    public static void main(String[] args) {
        var calculator = new PremiumCalculator();

        // List of 8 applicants (Applicant 6 is ineligible to trigger labelled break)
        var applicants = List.of(
            new Applicant("INS-01", "Aarav Mehta",   26, false, 68.0, 1.75, 10),
            new Applicant("INS-02", "Sneha Pillai",  32, true,  62.0, 1.65, 15),
            new Applicant("INS-03", "Rohan Kulkarni",41, false, 88.0, 1.72, 10),
            new Applicant("INS-04", "Divya Menon",   29, false, 48.0, 1.68, 20),
            new Applicant("INS-05", "Vikram Joshi",  50, true,  96.0, 1.70, 10),
            new Applicant("INS-06", "Karan Verma",   64, false, 75.0, 1.74, 10), // Ineligible: Age > 60
            new Applicant("INS-07", "Ananya Rao",    37, false, 65.0, 1.62, 15),
            new Applicant("INS-08", "Siddharth Sen", 44, true,  82.0, 1.76, 20)
        );

        System.out.println("=====================================================================================================");
        System.out.printf("%-8s | %-15s | %-6s | %-12s | %-8s | %-12s | %-13s | %-10s%n",
                "ID", "NAME", "BMI", "CATEGORY", "LOAD %", "BASE (INR)", "FINAL (INR)", "STATUS");
        System.out.println("=====================================================================================================");

        // Task 4: Enhanced for loop with labelled break on ineligible applicant
        batchValidation:
        for (var app : applicants) {
            var quote = calculator.evaluate(app);

            if (quote.status().equals("INELIGIBLE")) {
                System.out.printf("%-8s | %-15s | %6.2f | %-12s | %7.1f%% | %12.2f | %13.2f | %-10s%n",
                        quote.applicantId(), quote.name(), quote.bmi(), quote.bmiCategory(),
                        quote.loadingPercent(), quote.basePremium(), quote.finalPremium(), quote.status());
                System.out.println("-----------------------------------------------------------------------------------------------------");
                System.out.println("[BATCH HALTED] Ineligible applicant detected (" + app.id() + " - " + app.name() + "). Labelled break triggered.");
                break batchValidation;
            }

            System.out.printf("%-8s | %-15s | %6.2f | %-12s | %7.1f%% | %12.2f | %13.2f | %-10s%n",
                    quote.applicantId(), quote.name(), quote.bmi(), quote.bmiCategory(),
                    quote.loadingPercent(), quote.basePremium(), quote.finalPremium(), quote.status());
        }
        System.out.println("=====================================================================================================");
    }
}
