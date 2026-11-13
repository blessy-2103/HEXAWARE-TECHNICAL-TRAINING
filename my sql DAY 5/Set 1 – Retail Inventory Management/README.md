erDiagram
    SUPPLIERS {
        int supplier_id PK
        varchar supplier_name
        varchar supplier_phone UK
    }

    WAREHOUSES {
        int warehouse_id PK
        varchar warehouse_location
    }

    PRODUCTS {
        varchar sku PK
        varchar product_name
        varchar category
        int supplier_id FK
        int warehouse_id FK
        int quantity
        int reorder_level
    }

    SUPPLIERS ||--o{ PRODUCTS : supplies
    WAREHOUSES ||--o{ PRODUCTS : stored_in
