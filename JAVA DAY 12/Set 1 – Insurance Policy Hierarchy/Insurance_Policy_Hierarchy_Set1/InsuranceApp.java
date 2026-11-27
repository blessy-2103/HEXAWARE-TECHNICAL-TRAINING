public class InsuranceApp {

    public static void main(String[] args) {
        // Task 3: Array of 8 Policy objects (includes Motor, Health, Travel, and Home)
        Policy[] policies = new Policy[] {
            new MotorPolicy("POL-MTR101", "Arjun Nair", 500000, PolicyStatus.ACTIVE, 3, true),
            new MotorPolicy("POL-MTR102", "Meera Krishnan", 800000, PolicyStatus.LAPSED, 7, false),
            new HealthPolicy("POL-HLT201", "Karthik Sundaram", 1000000, PolicyStatus.ACTIVE, 4, 48),
            new HealthPolicy("POL-HLT202", "Divya Menon", 500000, PolicyStatus.DRAFT, 1, 29),
            new TravelPolicy("POL-TRV301", "Rohan Deshmukh", 300000, PolicyStatus.ACTIVE, 10, true),
            new TravelPolicy("POL-TRV302", "Ananya Iyer", 150000, PolicyStatus.CANCELLED, 4, false),
            new HomePolicy("POL-HOM401", "Vikramaditya Rao", 2000000, PolicyStatus.ACTIVE, 6500000, true),
            new HomePolicy("POL-HOM402", "Sneha Kulkarni", 1500000, PolicyStatus.ACTIVE, 4200000, false)
        };

        System.out.println("=== Policy Portfolio (Polymorphic Premium Calculation) ===");
        double totalPortfolioPremium = 0.0;
        for (Policy policy : policies) {
            System.out.println(policy.summaryLine());
            totalPortfolioPremium += policy.calculatePremium();
        }
        System.out.printf("Total Portfolio Premium: ₹%,.2f%n%n", totalPortfolioPremium);

        // Task 4a: 2D array - Monthly premium collection by region (4 regions x 3 months: Jan, Feb, Mar)
        String[] regions = { "North", "South", "East", "West" };
        double[][] regionalMonthlyCollection = {
            { 420000.0, 455000.0, 490000.0 },
            { 510000.0, 530000.0, 565000.0 },
            { 280000.0, 295000.0, 310000.0 },
            { 390000.0, 415000.0, 440000.0 }
        };

        System.out.println("=== Regional Monthly Premium Collection (2D Array) ===");
        double grand2DTotals = 0.0;
        for (int r = 0; r < regionalMonthlyCollection.length; r++) {
            double regionTotal = 0.0;
            for (int m = 0; m < regionalMonthlyCollection[r].length; m++) {
                regionTotal += regionalMonthlyCollection[r][m];
            }
            grand2DTotals += regionTotal;
            System.out.printf(
                "%-6s -> Jan: ₹%,.2f | Feb: ₹%,.2f | Mar: ₹%,.2f | Quarter Total: ₹%,.2f%n",
                regions[r],
                regionalMonthlyCollection[r][0],
                regionalMonthlyCollection[r][1],
                regionalMonthlyCollection[r][2],
                regionTotal
            );
        }
        System.out.printf("All-Regions Quarter Total: ₹%,.2f%n%n", grand2DTotals);

        // Task 4b: Jagged array - Varying number of branches per region
        double[][] branchCollectionsByRegion = {
            { 150000.0, 140000.0, 175000.0 },                 // North: 3 branches
            { 210000.0, 195000.0, 225000.0, 180000.0 },       // South: 4 branches
            { 160000.0, 155000.0 },                           // East:  2 branches
            { 185000.0, 170000.0, 190000.0 }                  // West:  3 branches
        };

        System.out.println("=== Branch-Level Collection by Region (Jagged Array) ===");
        double grandJaggedTotal = 0.0;
        for (int r = 0; r < branchCollectionsByRegion.length; r++) {
            double sum = 0.0;
            for (int b = 0; b < branchCollectionsByRegion[r].length; b++) {
                sum += branchCollectionsByRegion[r][b];
            }
            grandJaggedTotal += sum;
            System.out.printf(
                "%-6s (%d branches) -> Total: ₹%,.2f%n",
                regions[r],
                branchCollectionsByRegion[r].length,
                sum
            );
        }
        System.out.printf("Total Across All Branches: ₹%,.2f%n", grandJaggedTotal);
    }
}
