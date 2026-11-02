
# Acceptance Criteria – Claim Registration

Acceptance criteria are written using Given / When / Then.

## US01 – Start a Claim

### Scenario: Successfully start a claim
- Given the customer is logged into the insurance portal,
- When the customer selects "Start New Claim",
- Then the portal should display the claim registration form.

### Scenario: Customer is not authenticated
- Given the customer is not logged into the portal,
- When the customer attempts to start a claim,
- Then the portal should request authentication before allowing claim registration.

## US02 – Enter Claim Details

### Scenario: Enter valid incident details
- Given the customer has opened the claim registration form,
- When the customer enters the incident date, incident type, description, and policy details,
- Then the entered information should be saved in the form.

### Scenario: Mandatory details are missing
- Given the customer has opened the claim registration form,
- When the customer leaves mandatory fields empty,
- Then the portal should display validation messages for the missing fields.

## US03 – Validate Claim Information

### Scenario: Valid claim information
- Given the customer has entered all mandatory claim details,
- When the customer selects "Validate",
- Then the portal should confirm that the information is valid.

### Scenario: Validation failure
- Given the customer has entered invalid or incomplete claim information,
- When the customer selects "Validate",
- Then the portal should display appropriate validation errors and prevent submission until the errors are corrected.

## US04 – Submit Claim

### Scenario: Successfully submit claim
- Given the customer has entered valid claim details,
- When the customer selects "Submit Claim",
- Then the portal should create a claim reference number and display a submission confirmation.

### Scenario: Submission blocked by validation
- Given the claim contains invalid or incomplete mandatory information,
- When the customer selects "Submit Claim",
- Then the portal should prevent submission and display validation errors.

## Summary

- Claim Registration stories covered: US01, US02, US03, US04
- Given / When / Then scenarios documented.
- Positive and negative scenarios included.
