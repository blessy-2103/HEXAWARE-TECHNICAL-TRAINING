package com.utility.billing.service;

import com.utility.billing.model.BillResult;

public class BillingCalculator {

    // Task 1: Four consumption slabs (0-100, 101-200, 201-400, above 400)
    public double calculateEnergyCharge(int units) {
        if (units <= 0) {
            return 0.0;
        }

        double charge = 0.0;

        // Slab 1: 0 - 100 units @ INR 3.50/unit
        int slab1 = Math.min(units, 100);
        charge += slab1 * 3.50;

        // Slab 2: 101 - 200 units @ INR 5.00/unit
        if (units > 100) {
            int slab2 = Math.min(units - 100, 100);
            charge += slab2 * 5.00;
        }

        // Slab 3: 201 - 400 units @ INR 7.00/unit
        if (units > 200) {
            int slab3 = Math.min(units - 200, 200);
            charge += slab3 * 7.00;
        }

        // Slab 4: Above 400 units @ INR 9.50/unit
        if (units > 400) {
            int slab4 = units - 400;
            charge += slab4 * 9.50;
        }

        return charge;
    }

    // Task 2: Switch expression for consumer category fixed charge
    public double getFixedCharge(String category) {
        var normalized = category == null ? "" : category.trim().toUpperCase();
        return switch (normalized) {
            case "DOMESTIC" -> 75.0;
            case "COMMERCIAL" -> 250.0;
            case "INDUSTRIAL" -> 600.0;
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }

    // Task 3: Late-payment surcharge (10% of subtotal) and online-payment rebate (5% of subtotal)
    public BillResult calculateBill(int units, String category, boolean latePayment, boolean onlinePayment) {
        var energy = calculateEnergyCharge(units);
        var fixed = getFixedCharge(category);
        var subtotal = energy + fixed;

        // Operator Precedence Documentation:
        // Expression: subtotal + subtotal * 0.10 - subtotal * 0.05
        // 1. Multiplication (*) has higher precedence than addition (+) and subtraction (-).
        //    So `subtotal * 0.10` and `subtotal * 0.05` are evaluated first (left-to-right).
        // 2. Addition (+) and subtraction (-) have equal precedence and evaluate left-to-right:
        //    `(subtotal + (subtotal * 0.10)) - (subtotal * 0.05)`.
        double surcharge = latePayment ? subtotal * 0.10 : 0.0;
        double rebate = onlinePayment ? subtotal * 0.05 : 0.0;
        double total = subtotal + subtotal * 0.10 - subtotal * 0.05;

        double actualTotal = subtotal + surcharge - rebate;
        if (latePayment && onlinePayment) {
            actualTotal = total;
        }

        return new BillResult(units, category.toUpperCase(), energy, fixed, surcharge, rebate, actualTotal);
    }

    // Task 5: Primitive casting issues and safe handling
    public void demonstratePrimitiveCasting() {
        System.out.println("\n--- Task 5: Primitive Casting Issues & Fixes ---");

        // 1. Integer division vs floating-point division
        int totalUnits = 401;
        int months = 12;
        int buggyAvg = totalUnits / months; // Truncates remainder (33 instead of 33.416...)
        double fixedAvg = (double) totalUnits / months; // Cast to double before division
        System.out.printf("Int division (401 / 12): %d | Cast to double: %.2f%n", buggyAvg, fixedAvg);

        // 2. Integer overflow vs long promotion
        int monthlyConsumptionLarge = 50_000_000;
        int multiplier = 100;
        int overflowVal = monthlyConsumptionLarge * multiplier; // Overflows 32-bit int range
        long safeLongVal = (long) monthlyConsumptionLarge * multiplier; // Promoted to 64-bit long
        System.out.printf("Int overflow (50M * 100): %d | Safe long cast: %d%n", overflowVal, safeLongVal);

        // 3. Narrowing double to int (loss of fractional part / rounding)
        double exactBillAmount = 1489.85;
        int truncatedBill = (int) exactBillAmount; // Drops .85 -> 1489
        long roundedBill = Math.round(exactBillAmount); // Rounds to nearest whole unit -> 1490
        System.out.printf("Double to int cast (1489.85): %d | Math.round: %d%n", truncatedBill, roundedBill);
    }
}
