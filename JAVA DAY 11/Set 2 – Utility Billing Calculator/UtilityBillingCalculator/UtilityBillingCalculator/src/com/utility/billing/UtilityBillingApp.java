package com.utility.billing;

import com.utility.billing.service.BillingCalculator;
import java.util.Scanner;

public class UtilityBillingApp {
    public static void main(String[] args) {
        var calculator = new BillingCalculator();

        // Part A: Boundary test values required by Set 2 (100, 101, 200, 201, 400, 401)
        int[] boundaryUnits = {100, 101, 200, 201, 400, 401};
        System.out.println("==========================================================================================");
        System.out.printf("%-6s | %-10s | %-12s | %-12s | %-10s | %-8s | %-10s%n",
                "UNITS", "CATEGORY", "ENERGY (INR)", "FIXED (INR)", "SURCHARGE", "REBATE", "TOTAL (INR)");
        System.out.println("==========================================================================================");

        for (var u : boundaryUnits) {
            var res = calculator.calculateBill(u, "DOMESTIC", false, true);
            System.out.printf("%-6d | %-10s | %12.2f | %12.2f | %10.2f | %8.2f | %10.2f%n",
                    res.units(), res.category(), res.energyCharge(), res.fixedCharge(),
                    res.surcharge(), res.rebate(), res.totalBill());
        }
        System.out.println("==========================================================================================");

        // Task 4a: For loop producing a 12-month statement
        System.out.println("\n--- 12-Month Domestic Billing Statement ---");
        int[] monthlyUnits = {95, 100, 101, 165, 200, 201, 290, 400, 401, 450, 220, 140};
        double annualTotal = 0.0;

        for (int month = 1; month <= 12; month++) {
            int u = monthlyUnits[month - 1];
            boolean onlinePaid = (month % 2 == 0);
            var bill = calculator.calculateBill(u, "DOMESTIC", false, onlinePaid);
            annualTotal += bill.totalBill();
            System.out.printf("Month %2d | Units: %3d | Online: %-5s | Total: INR %8.2f%n",
                    month, u, onlinePaid ? "Yes" : "No", bill.totalBill());
        }
        System.out.printf("Annual Total Payable: INR %.2f%n", annualTotal);

        // Task 5: Primitive casting demonstrations
        calculator.demonstratePrimitiveCasting();

        // Task 4b: do-while loop reading meter readings until user enters 0
        System.out.println("\n--- Interactive Meter Reading Entry (Enter 0 to stop) ---");
        try (var scanner = new Scanner(System.in)) {
            int reading;
            do {
                System.out.print("Enter meter reading units (0 to exit): ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next();
                    reading = -1;
                    continue;
                }
                reading = scanner.nextInt();
                if (reading > 0) {
                    var res = calculator.calculateBill(reading, "DOMESTIC", false, false);
                    System.out.printf("Units: %d -> Energy: INR %.2f | Total (Domestic, no flags): INR %.2f%n",
                            reading, res.energyCharge(), res.totalBill());
                } else if (reading < 0) {
                    System.out.println("Negative reading ignored. Enter a positive number or 0 to exit.");
                }
            } while (reading != 0);
        }
        System.out.println("Meter reading session ended.");
    }
}
