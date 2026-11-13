---

###  SQL vs NoSQL Comparison Table
| Feature | Relational SQL (MySQL) | NoSQL (e.g., MongoDB) |
| :--- | :--- | :--- |
| **Structure** | Strict tabular schema with rows and columns, enforcing 3NF normalisation[cite: 2]. | Flexible JSON-like documents or key-value pairs; schema-less. |
| **Consistency** | High ACID compliance, ideal for financial and hospital billing accuracy. | Often eventual consistency (BASE), prioritizing high availability. |
| **Scalability** | Vertically scalable (adding more power to a single server); sharding can be complex. | Horizontally scalable (easily distributes data across distributed clusters). |
| **Typical Use** | Complex queries joining patients, doctors, departments, and bills[cite: 2]. | Rapidly changing medical logs, unstructured IoT feeds, or patient telemetry. |
