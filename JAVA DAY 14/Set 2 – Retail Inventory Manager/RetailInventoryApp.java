import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RetailInventoryApp {

    static class Product implements Comparable<Product> {
        private final String sku;
        private final String name;
        private final String category;
        private int stock;
        private final int reorderThreshold;
        private final double price;

        public Product(String sku, String name, String category, int stock, int reorderThreshold, double price) {
            this.sku = sku;
            this.name = name;
            this.category = category;
            this.stock = stock;
            this.reorderThreshold = reorderThreshold;
            this.price = price;
        }

        public String getSku() { return sku; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }
        public int getReorderThreshold() { return reorderThreshold; }
        public double getPrice() { return price; }

        public boolean isLowStock() {
            return stock <= reorderThreshold;
        }

        // Natural ordering: category (asc), stock (desc), name (asc)
        @Override
        public int compareTo(Product other) {
            return Comparator.comparing(Product::getCategory)
                    .thenComparing(Comparator.comparingInt(Product::getStock).reversed())
                    .thenComparing(Product::getName)
                    .compare(this, other);
        }

        @Override
        public String toString() {
            return String.format("[%s | %-10s | %-22s | stock=%-2d (min=%-2d) | ₹%.2f]",
                    sku, category, name, stock, reorderThreshold, price);
        }
    }

    static class Supplier {
        private final String supplierId;
        private final String name;
        private final String contactEmail;

        public Supplier(String supplierId, String name, String contactEmail) {
            this.supplierId = supplierId;
            this.name = name;
            this.contactEmail = contactEmail;
        }

        public String getSupplierId() { return supplierId; }
        public String getName() { return name; }

        @Override
        public String toString() {
            return String.format("[%s | %s | %s]", supplierId, name, contactEmail);
        }
    }

    // Task 4: Bounded generic repository
    static class Repository<T> {
        private final Map<String, T> store = new HashMap<>();

        public void save(String id, T item) {
            store.put(id, Objects.requireNonNull(item));
        }

        public T findById(String id) {
            return store.get(id);
        }

        public List<T> findAll() {
            return new ArrayList<>(store.values());
        }

        public int count() {
            return store.size();
        }
    }

    static class InventoryManager {
        private static final int MAX_LOG_SIZE = 10;

        // Task 1: Fast SKU lookup map
        private final Map<String, Product> inventoryBySku = new HashMap<>();
        // Task 3: Bounded recent-activity log (last 10 movements)
        private final Deque<String> activityLog = new ArrayDeque<>();

        private void recordMovement(String entry) {
            if (activityLog.size() == MAX_LOG_SIZE) {
                activityLog.removeFirst(); // evict oldest entry
            }
            activityLog.addLast(entry);
        }

        public void addProduct(Product product) {
            inventoryBySku.put(product.getSku(), product);
            recordMovement("ADDED " + product.getSku() + " (stock=" + product.getStock() + ")");
        }

        public boolean updateStock(String sku, int delta) {
            Product product = inventoryBySku.get(sku);
            if (product == null) return false;
            int updated = Math.max(0, product.getStock() + delta);
            product.setStock(updated);
            recordMovement("UPDATED " + sku + " by " + delta + " -> stock=" + updated);
            return true;
        }

        public Product removeProduct(String sku) {
            Product removed = inventoryBySku.remove(sku);
            if (removed != null) {
                recordMovement("REMOVED " + sku);
            }
            return removed;
        }

        public List<Product> getLowStockProducts() {
            List<Product> lowStock = new ArrayList<>();
            for (Product p : inventoryBySku.values()) {
                if (p.isLowStock()) {
                    lowStock.add(p);
                }
            }
            return lowStock;
        }

        public List<Product> getAllProducts() {
            return new ArrayList<>(inventoryBySku.values());
        }

        public List<String> getRecentActivity() {
            return new ArrayList<>(activityLog);
        }
    }

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();

        // Task 1: Add, update, remove, low-stock listing
        System.out.println("=== TASK 1: INVENTORY OPERATIONS & LOW-STOCK ALERTS ===");
        manager.addProduct(new Product("SKU-101", "Wireless Mouse", "Electronics", 18, 5, 799.0));
        manager.addProduct(new Product("SKU-102", "Mechanical Keyboard", "Electronics", 4, 6, 2499.0));
        manager.addProduct(new Product("SKU-103", "USB-C Hub", "Electronics", 18, 8, 1499.0));
        manager.addProduct(new Product("SKU-201", "Office Chair", "Furniture", 3, 4, 8499.0));
        manager.addProduct(new Product("SKU-202", "Standing Desk", "Furniture", 7, 3, 15999.0));
        manager.addProduct(new Product("SKU-301", "A4 Copier Paper", "Stationery", 25, 10, 320.0));
        manager.addProduct(new Product("SKU-302", "Gel Pen Pack", "Stationery", 6, 8, 150.0));
        manager.addProduct(new Product("SKU-303", "Temporary Binder", "Stationery", 2, 5, 199.0));

        manager.updateStock("SKU-101", -14); // 18 -> 4 (now low stock)
        manager.updateStock("SKU-202", +5);  // 7 -> 12
        manager.removeProduct("SKU-303");

        System.out.println("Low-stock products:");
        manager.getLowStockProducts().forEach(p -> System.out.println("  " + p));

        // Task 2: Comparable (category asc, stock desc, name asc) vs alternative Comparator
        System.out.println("\n=== TASK 2: SORTING (Comparable vs Comparator) ===");
        List<Product> byComparable = manager.getAllProducts();
        byComparable.sort(null); // uses Product.compareTo
        System.out.println("Natural order (Category ASC, Stock DESC, Name ASC):");
        byComparable.forEach(p -> System.out.println("  " + p));

        Comparator<Product> byPriceDescThenName = Comparator
                .comparingDouble(Product::getPrice).reversed()
                .thenComparing(Product::getName);
        List<Product> byAlternativeComparator = manager.getAllProducts();
        byAlternativeComparator.sort(byPriceDescThenName);
        System.out.println("Alternative order (Price DESC, Name ASC):");
        byAlternativeComparator.forEach(p -> System.out.println("  " + p));

        // Task 3: ArrayDeque recent-activity log (last 10 movements)
        System.out.println("\n=== TASK 3: RECENT ACTIVITY LOG (Last 10 movements) ===");
        manager.updateStock("SKU-102", +10);
        manager.updateStock("SKU-201", +4);
        manager.updateStock("SKU-301", -18);
        manager.updateStock("SKU-103", -2);
        manager.getRecentActivity().forEach(entry -> System.out.println("  " + entry));

        // Task 4: Generic Repository<T> for Product and Supplier
        System.out.println("\n=== TASK 4: GENERIC REPOSITORY<T> ===");
        Repository<Product> productRepo = new Repository<>();
        for (Product p : manager.getAllProducts()) {
            productRepo.save(p.getSku(), p);
        }

        Repository<Supplier> supplierRepo = new Repository<>();
        supplierRepo.save("SUP-01", new Supplier("SUP-01", "Apex Tech Distributors", "orders@apextech.in"));
        supplierRepo.save("SUP-02", new Supplier("SUP-02", "Crestline Office Goods", "supply@crestline.in"));

        System.out.println("Fetched Product: " + productRepo.findById("SKU-202"));
        System.out.println("Fetched Supplier: " + supplierRepo.findById("SUP-01"));
        System.out.println("Repos sizes -> Products: " + productRepo.count() + ", Suppliers: " + supplierRepo.count());

        // Task 5: Autoboxing, Integer cache, and == vs equals() trap
        System.out.println("\n=== TASK 5: AUTOBOXING, INTEGER CACHE & == VS equals() ===");
        int rawStock = 100;
        Integer boxedStock = rawStock;          // autoboxing
        int unboxedStock = boxedStock;         // unboxing

        Integer cachedA = 127;                 // inside [-128, 127] Integer cache
        Integer cachedB = 127;
        Integer uncachedA = 128;               // outside default cache range
        Integer uncachedB = 128;

        System.out.println("unboxedStock = " + unboxedStock);
        System.out.println("127 == 127 (cached): " + (cachedA == cachedB));
        System.out.println("128 == 128 (outside cache): " + (uncachedA == uncachedB));
        System.out.println("128.equals(128): " + uncachedA.equals(uncachedB));
    }
}
