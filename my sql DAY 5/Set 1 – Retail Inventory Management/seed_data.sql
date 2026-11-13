USE retail_inventory_db;

-- Insert Suppliers (10 rows)
INSERT INTO suppliers (supplier_name, supplier_phone) VALUES
('Global Goods Inc', '9876543210'),
('Prime Distributors', '9123456780'),
('Apex Wholesale', '9988776655'),
('City Trade Corp', '9811223344'),
('Metro Supplies', '9722334455'),
('Zenith Enterprises', '9633445566'),
('Vanguard Hub', '9544556677'),
('Pioneer Retailers', '9455667788'),
('Titanium Goods', '9366778899'),
('Alpha Stockists', '9277889900');

-- Insert Warehouses (3 rows)
INSERT INTO warehouses (warehouse_location) VALUES
('North Warehouse'),
('South Warehouse'),
('Central Warehouse');

-- Insert Products / SKUs (10 rows)
INSERT INTO products (sku, product_name, category, supplier_id, warehouse_id, quantity, reorder_level) VALUES
('SKU-101', 'Organic Milk 1L', 'Groceries', 1, 1, 150, 20),
('SKU-102', 'Whole Wheat Bread', 'Bakery', 2, 1, 80, 15),
('SKU-103', 'Basmati Rice 5kg', 'Grains', 3, 2, 200, 25),
('SKU-104', 'Olive Oil 500ml', 'Cooking Essentials', 4, 2, 60, 10),
('SKU-105', 'Almonds 500g', 'Dry Fruits', 5, 3, 120, 15),
('SKU-106', 'Green Tea Bags', 'Beverages', 6, 3, 90, 12),
('SKU-107', 'Tomato Ketchup', 'Condiments', 7, 1, 110, 15),
('SKU-108', 'Toothpaste 150g', 'Personal Care', 8, 2, 300, 40),
('SKU-109', 'Bath Soap Pack', 'Personal Care', 9, 3, 250, 30),
('SKU-110', 'Laundry Detergent', 'Household', 10, 1, 75, 10);
