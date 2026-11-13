-- Test 1: Duplicate Key Error
INSERT INTO products VALUES ('SKU-101', 'Duplicate Milk', 'Groceries', 1, 1, 50, 10);
-- Result: Error Code: 1062. Duplicate entry 'SKU-101' for key 'products.PRIMARY'

-- Test 2: Missing Parent (Foreign Key Constraint Failure)
INSERT INTO products VALUES ('SKU-999', 'Fake Item', 'Misc', 99, 1, 50, 10);
-- Result: Error Code: 1452. Cannot add or update a child row: a foreign key constraint fails (`retail_inventory_db`.`products`, CONSTRAINT `fk_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`))

-- Test 3: Negative Quantity (Check Constraint Violation)
INSERT INTO products VALUES ('SKU-888', 'Bad Stock', 'Misc', 1, 1, -10, 10);
-- Result: Error Code: 3819. Check constraint 'products_chk_1' is violated.

-- Test 4: NULL in Mandatory Column Violation
INSERT INTO products (sku, product_name, category, supplier_id, warehouse_id, quantity) 
VALUES ('SKU-777', NULL, 'Misc', 1, 1, 50);
-- Result: Error Code: 1048. Column 'product_name' cannot be null
