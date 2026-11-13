```mermaid
erDiagram
    DEPARTMENTS {
        int department_id PK
        varchar department_name
    }

    DOCTORS {
        int doctor_id PK
        varchar doctor_name
        int department_id FK
    }

    PATIENTS {
        int patient_id PK
        varchar patient_name
        varchar address
        varchar phone UK
    }

    VISITS {
        int visit_id PK
        int patient_id FK
        int doctor_id FK
        date visit_date
        text diagnosis
        text prescription
        varchar visit_status
    }

    BILLING {
        int bill_id PK
        int visit_id FK
        decimal bill_amount
    }

    DEPARTMENTS ||--o{ DOCTORS : employs
    DOCTORS ||--o{ VISITS : attends
    PATIENTS ||--o{ VISITS : makes
    VISITS ||--|| BILLING : generates
