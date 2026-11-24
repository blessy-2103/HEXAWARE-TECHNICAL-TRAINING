package com.bank.loan;

import com.bank.loan.model.Applicant;
import com.bank.loan.service.EligibilityEngine;
import java.util.List;

public class LoanEligibilityApp {
    public static void main(String[] args) {
        var engine = new EligibilityEngine();
        final int DAILY_APPROVAL_LIMIT = 4;
        int approvedCount = 0;

        var applicants = List.of(
            new Applicant("APP-01", "Aarav Sharma",   29, 120000, 30000, 785, "SALARIED",      1),
            new Applicant("APP-02", "Neha Verma",    -25,  60000, 15000, 740, "SALARIED",      0),
            new Applicant("APP-03", "Rohan Das",      19,  45000, 10000, 720, "SALARIED",      0),
            new Applicant("APP-04", "Priya Nair",     35,  80000, 18000, 760, "SELF_EMPLOYED", 1),
            new Applicant("APP-05", "Vikram Singh",   42,      0,  5000, 710, "SALARIED",      1),
            new Applicant("APP-06", "Ananya Iyer",    31,  70000, 35000, 730, "SALARIED",      1),
            new Applicant("APP-07", "Karthik Rao",    38,  90000, 20000, 715, "SELF_EMPLOYED", 3),
            new Applicant("APP-08", "Meera Joshi",    27,  55000, 12000, 670, "SALARIED",      1),
            new Applicant("APP-09", "Suresh Menon",   45,  65000, 15000, 950, "SALARIED",      0),
            new Applicant("APP-10", "Divya Kapoor",   33,  95000, 22000, 735, "SALARIED",      2)
        );

        System.out.println("================================================================================================================");
        System.out.printf("%-8s | %-14s | %-4s | %-10s | %-6s | %-10s | %-22s | %-6s | %-28s%n",
                "ID", "NAME", "AGE", "INCOME", "SCORE", "STATUS", "PRODUCT", "RATE%", "REMARKS");
        System.out.println("================================================================================================================");

        for (var app : applicants) {
            if (!engine.isValidRecord(app)) {
                System.out.printf("%-8s | %-14s | %-4d | %-10.2f | %-6d | %-10s | %-22s | %-6s | %-28s%n",
                        app.id(), app.name(), app.age(), app.monthlyIncome(), app.creditScore(),
                        "SKIPPED", "N/A", "0.00%", "Invalid record (edge case)");
                continue;
            }

            var decision = engine.evaluate(app);

            System.out.printf("%-8s | %-14s | %-4d | %-10.2f | %-6d | %-10s | %-22s | %5.2f%% | %-28s%n",
                    app.id(), app.name(), app.age(), app.monthlyIncome(), app.creditScore(),
                    decision.status(), decision.product(), decision.interestRate(), decision.remarks());

            if (decision.status().equals("APPROVED")) {
                approvedCount++;
                if (approvedCount >= DAILY_APPROVAL_LIMIT) {
                    System.out.println("----------------------------------------------------------------------------------------------------------------");
                    System.out.println("[SYSTEM HALT] Daily approval limit (" + DAILY_APPROVAL_LIMIT + ") reached. Stopping batch processing.");
                    break;
                }
            }
        }
        System.out.println("================================================================================================================");
    }
}
