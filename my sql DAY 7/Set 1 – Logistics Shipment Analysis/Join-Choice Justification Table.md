| Join Type | Business Question Context | Why This Join Was Chosen |
| --- | --- | --- |
| **INNER JOIN** | Shipments delayed by > 2 days with carrier names | Only matching records between shipments and carriers are relevant for performance review. |
| **LEFT JOIN** | Hubs with no outbound shipments | Preserves all records from the left table (`hubs`) to identify items with zero matches in `shipments`. |
| **Self Join** | Employees with their supervisors | The employee hierarchy resides entirely within a single table (`employees`), requiring an alias-based self reference. |
| **Multiple JOINs** | Detailed shipment report with origin & destination hub names | Requires bridging multiple lookup tables (`customers`, `origin_hub`, `destination_hub`) to construct a complete view. |
| **RIGHT JOIN** | Carriers and their handled shipments | Preserves all carrier records even if a carrier has zero active shipments assigned. |
| **CROSS JOIN** | Hub-Carrier matrix mapping | Generates every possible combination of hubs and carriers for operational network planning. |
