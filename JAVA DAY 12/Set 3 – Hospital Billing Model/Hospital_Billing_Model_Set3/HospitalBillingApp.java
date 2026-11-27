public class HospitalBillingApp {

    public static void main(String[] args) {
        Patient p1 = new Patient("PAT-101", "Raghavendra Rao", 42);
        Patient p2 = new Patient("PAT-102", "Meenakshi Sundaram", 58);
        Patient p3 = new Patient("PAT-103", "Aarav Krishnan", 27);
        Patient p4 = new Patient("PAT-104", "Kavitha Nair", 35);

        Doctor d1 = new Doctor("DOC-201", "Dr. S. Venkatesh", "Cardiology");
        Doctor d2 = new Doctor("DOC-202", "Dr. Priya Natarajan", "Orthopedics");
        Doctor d3 = new Doctor("DOC-203", "Dr. Arvind Menon", "Emergency");

        // Task 4a: Array of visits printed in one polymorphic loop
        Visit[] visits = new Visit[] {
            new OutPatientVisit("VST-1001", p1, d1, 800.0, 1450.0),
            new InPatientVisit("VST-1002", p2, d2, 1500.0, 4, 3500.0, 18000.0),
            new EmergencyVisit("VST-1003", p3, d3, 2000.0, 2500.0, 6200.0),
            new OutPatientVisit("VST-1004", p4, d2, 700.0, 950.0)
        };

        System.out.println("=== Hospital Visit Billing Summary (Polymorphic Loop) ===");
        double totalHospitalRevenue = 0.0;
        for (Visit visit : visits) {
            System.out.println(visit.formatInvoiceLine());
            totalHospitalRevenue += visit.calculateBill();
        }
        System.out.printf("Total Gross Billing: ₹%,.2f%n%n", totalHospitalRevenue);

        // Task 3: Demonstrating overloaded calculateBill variants on an InPatientVisit
        Visit sampleInPatient = visits[1];
        System.out.println("=== Overloaded Billing Variants (" + sampleInPatient.getVisitId() + ") ===");
        System.out.printf("Standard Bill                        : ₹%,.2f%n", sampleInPatient.calculateBill());
        System.out.printf("With 10%% Senior/Corporate Discount   : ₹%,.2f%n", sampleInPatient.calculateBill(10.0));
        System.out.printf("With 10%% Discount + ₹15,000 Insurance: ₹%,.2f%n%n", sampleInPatient.calculateBill(10.0, 15000.0));

        // Task 4b: 2D array for department-wise monthly totals (3 departments x 3 months: Jan, Feb, Mar)
        String[] departments = { "Cardiology", "Orthopedics", "Emergency" };
        double[][] deptMonthlyTotals = {
            { 640000.0, 685000.0, 710000.0 },
            { 520000.0, 545000.0, 590000.0 },
            { 480000.0, 515000.0, 560000.0 }
        };

        System.out.println("=== Department-Wise Monthly Billing Totals (2D Array) ===");
        double grandDeptTotal = 0.0;
        for (int d = 0; d < deptMonthlyTotals.length; d++) {
            double deptSum = 0.0;
            for (int m = 0; m < deptMonthlyTotals[d].length; m++) {
                deptSum += deptMonthlyTotals[d][m];
            }
            grandDeptTotal += deptSum;
            System.out.printf(
                "%-12s -> Jan: ₹%,.2f | Feb: ₹%,.2f | Mar: ₹%,.2f | Quarter Total: ₹%,.2f%n",
                departments[d],
                deptMonthlyTotals[d][0],
                deptMonthlyTotals[d][1],
                deptMonthlyTotals[d][2],
                deptSum
            );
        }
        System.out.printf("All-Departments Quarter Total: ₹%,.2f%n", grandDeptTotal);
    }
}
