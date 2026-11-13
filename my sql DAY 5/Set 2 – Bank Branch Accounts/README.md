```mermaid
erDiagram
    BRANCHES {
        int branch_id PK
        varchar branch_name
        varchar branch_location
    }

    CUSTOMERS {
        int customer_id PK
        varchar customer_name
        varchar address
        varchar phone UK
        varchar kyc_status
    }

    RELATIONSHIP_MANAGERS {
        int manager_id PK
        varchar manager_name
        varchar email UK
    }

    ACCOUNTS {
        varchar account_number PK
        int customer_id FK
        int branch_id FK
        int manager_id FK
        varchar account_type
        decimal balance
        date opened_date
    }

    BRANCHES ||--o{ ACCOUNTS : houses
    CUSTOMERS ||--o{ ACCOUNTS : owns
    RELATIONSHIP_MANAGERS ||--o{ ACCOUNTS : manages
