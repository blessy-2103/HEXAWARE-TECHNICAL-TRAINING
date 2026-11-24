# Utility Billing Calculator (Java JDK 25 LTS)

Set 2 solution for **Day 11 – Java (JDK 25 LTS)** covering slab-based electricity billing, `switch` expressions, surcharge/rebate operator precedence, `for` and `do-while` loops, and primitive casting demos.

## Project Structure

```text
UtilityBillingCalculator/
├── .gitignore
├── README.md
└── src/
    └── com/utility/billing/
        ├── model/
        │   └── BillResult.java
        ├── service/
        │   └── BillingCalculator.java
        └── UtilityBillingApp.java
```

## Build & Run

```bash
javac -d out src/com/utility/billing/model/*.java src/com/utility/billing/service/*.java src/com/utility/billing/UtilityBillingApp.java
java -cp out com.utility.billing.UtilityBillingApp
```

## Boundary Test Values (100, 101, 200, 201, 400, 401)

| Boundary Value (Units) | Slab Breakdown | Energy Charge (INR) | Fixed Charge (INR) | Subtotal (INR) | Surcharge (INR) | Rebate (5%) (INR) | Final Bill (INR) |
| :---: | :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **100** | `100 x INR 3.50` | 350.00 | 75.00 | 425.00 | 0.00 | 21.25 | **403.75** |
| **101** | `(100 x INR 3.50) + (1 x INR 5.00)` | 355.00 | 75.00 | 430.00 | 0.00 | 21.50 | **408.50** |
| **200** | `(100 x INR 3.50) + (100 x INR 5.00)` | 850.00 | 75.00 | 925.00 | 0.00 | 46.25 | **878.75** |
| **201** | `(100 x INR 3.50) + (100 x INR 5.00) + (1 x INR 7.00)` | 857.00 | 75.00 | 932.00 | 0.00 | 46.60 | **885.40** |
| **400** | `(100 x INR 3.50) + (100 x INR 5.00) + (200 x INR 7.00)` | 2250.00 | 75.00 | 2325.00 | 0.00 | 116.25 | **2208.75** |
| **401** | `(100 x INR 3.50) + (100 x INR 5.00) + (200 x INR 7.00) + (1 x INR 9.50)` | 2259.50 | 75.00 | 2334.50 | 0.00 | 116.73 | **2217.78** |

## Sample Console Output

```text
==========================================================================================
UNITS  | CATEGORY   | ENERGY (INR) | FIXED (INR)  | SURCHARGE  | REBATE   | TOTAL (INR)
==========================================================================================
100    | DOMESTIC   |       350.00 |        75.00 |       0.00 |    21.25 |     403.75
101    | DOMESTIC   |       355.00 |        75.00 |       0.00 |    21.50 |     408.50
200    | DOMESTIC   |       850.00 |        75.00 |       0.00 |    46.25 |     878.75
201    | DOMESTIC   |       857.00 |        75.00 |       0.00 |    46.60 |     885.40
400    | DOMESTIC   |      2250.00 |        75.00 |       0.00 |   116.25 |    2208.75
401    | DOMESTIC   |      2259.50 |        75.00 |       0.00 |   116.73 |    2217.78
==========================================================================================

--- 12-Month Domestic Billing Statement ---
Month  1 | Units:  95 | Online: No    | Total: INR   407.50
Month  2 | Units: 100 | Online: Yes   | Total: INR   403.75
Month  3 | Units: 101 | Online: No    | Total: INR   430.00
Month  4 | Units: 165 | Online: Yes   | Total: INR   705.38
Month  5 | Units: 200 | Online: No    | Total: INR   925.00
Month  6 | Units: 201 | Online: Yes   | Total: INR   885.40
Month  7 | Units: 290 | Online: No    | Total: INR  1555.00
Month  8 | Units: 400 | Online: Yes   | Total: INR  2208.75
Month  9 | Units: 401 | Online: No    | Total: INR  2334.50
Month 10 | Units: 450 | Online: Yes   | Total: INR  2645.75
Month 11 | Units: 220 | Online: No    | Total: INR  1065.00
Month 12 | Units: 140 | Online: Yes   | Total: INR   617.50
Annual Total Payable: INR 14443.55

--- Task 5: Primitive Casting Issues & Fixes ---
Int division (401 / 12): 33 | Cast to double: 33.42
Int overflow (50M * 100): 705032704 | Safe long cast: 5000000000
Double to int cast (1489.85): 1489 | Math.round: 1490

--- Interactive Meter Reading Entry (Enter 0 to stop) ---
Enter meter reading units (0 to exit): 150
Units: 150 -> Energy: INR 600.00 | Total (Domestic, no flags): INR 675.00
Enter meter reading units (0 to exit): 0
Meter reading session ended.
```
