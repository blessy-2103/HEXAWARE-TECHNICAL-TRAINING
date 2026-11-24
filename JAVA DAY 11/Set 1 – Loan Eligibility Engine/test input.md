| Test ID | Applicant | Age | Income (₹) | Proposed EMI (₹) | Credit Score | Employment | Existing Loans | Test Scenario / Edge Case | Expected Decision | Assigned Product & Rate |
| :--- | :--- | :---: | :---: | :---: | :---: | :--- | :---: | :--- | :--- | :--- |
| **APP-01** | Aarav Sharma | 29 | 1,20,000 | 30,000 | 785 | Salaried | 1 | High income + Excellent score | **APPROVED** | Platinum Prime Loan (8.25%) |
| **APP-02** | Neha Verma | -25 | 60,000 | 15,000 | 740 | Salaried | 0 | Edge Case: Negative age (`continue`) | **SKIPPED** | N/A |
| **APP-03** | Rohan Das | 19 | 45,000 | 10,000 | 720 | Salaried | 0 | Rule 1 Failure: Underage (< 21) | **REJECTED** | None (0.00%) |
| **APP-04** | Priya Nair | 35 | 80,000 | 18,000 | 760 | Self-Employed | 1 | Valid Self-Employed + Excellent score | **APPROVED** | Gold Personal Loan (8.90%) |
| **APP-05** | Vikram Singh | 42 | 0 | 5,000 | 710 | Salaried | 1 | Edge Case: Zero income (`continue`) | **SKIPPED** | N/A |
| **APP-06** | Ananya Iyer | 31 | 70,000 | 35,000 | 730 | Salaried | 1 | Rule 2 Failure: EMI is 50% (> 40% limit) | **REJECTED** | None (0.00%) |
| **APP-07** | Karthik Rao | 38 | 90,000 | 20,000 | 715 | Self-Employed | 3 | Rule 5 Failure: Exceeds 2-loan cap | **REJECTED** | None (0.00%) |
| **APP-08** | Meera Joshi | 27 | 55,000 | 12,000 | 670 | Salaried | 1 | Valid Salaried + Fair score (650–699) | **APPROVED** | Secured Starter Loan (12.75%) |
| **APP-09** | Suresh Menon | 45 | 65,000 | 15,000 | 950 | Salaried | 0 | Edge Case: Score > 900 (`continue`) | **SKIPPED** | N/A |
| **APP-10** | Divya Kapoor | 33 | 95,000 | 22,000 | 735 | Salaried | 2 | 4th approval triggers daily limit (`break`) | **APPROVED** | Standard Personal Loan (10.50%) |
