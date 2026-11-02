
# Acceptance Criteria – Online Grocery Delivery Application

Acceptance criteria define the conditions that must be satisfied for a user story to be accepted.

## US01 – View Grocery Categories

- Given the customer opens the application,
- When the grocery homepage loads,
- Then the available grocery categories should be displayed.

## US02 – Search Grocery Products

- Given the customer is on the search page,
- When the customer enters "Milk",
- Then products containing "Milk" in their name should be displayed.

## US05 – Add Product to Cart

- Given a grocery product is available,
- When the customer clicks "Add to Cart",
- Then the product should be added to the cart with quantity 1.

## US08 – Online Payment (Negative Scenario)

- Given the customer has items in the cart and proceeds to payment,
- When the payment transaction fails,
- Then the order should not be marked as paid and the customer should receive a payment failure message.

## US11 – Track Order

- Given the customer has placed an order,
- When the customer opens the tracking page,
- Then the current order status should be displayed.

## Summary

- Total stories covered: 5
- Format: Given / When / Then
- Negative scenario included: Payment failure
