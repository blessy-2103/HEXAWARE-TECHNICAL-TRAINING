
# INVEST Review – Insurance Claims Portal

## INVEST Principles

- I – Independent
- N – Negotiable
- V – Valuable
- E – Estimable
- S – Small
- T – Testable

## INVEST Review

| Story | I | N | V | E | S | T |
|---|---|---|---|---|---|---|
| US01 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US02 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US03 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US04 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US05 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US06 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US07 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US08 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US09 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US10 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US11 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US12 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US13 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| US14 | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |

## Identification of Large Stories

### Original Large Story 1
As an insurance customer, I want to register my claim, upload all documents, and track the settlement, so that I can complete the entire insurance process online.

### Problem
- Contains multiple user outcomes.
- Too large to estimate accurately.
- Difficult to complete within one Sprint.
- Combines different customer activities.

### Vertical Split

1. As an insurance customer, I want to start a new claim, so that I can report an incident.
2. As an insurance customer, I want to enter incident details, so that the insurer can understand my claim.
3. As an insurance customer, I want to upload supporting documents, so that I can provide claim evidence.
4. As an insurance customer, I want to view claim status, so that I can monitor processing.

### Original Large Story 2
As an insurance customer, I want to manage all claim documents, so that my claim is processed successfully.

### Problem
- Includes multiple functionalities.
- Scope is unclear.
- Not sufficiently small or estimable.

### Vertical Split

1. As an insurance customer, I want to upload supporting documents, so that I can provide evidence.
2. As an insurance customer, I want to view uploaded documents, so that I can verify submissions.
3. As an insurance customer, I want to replace an uploaded document, so that I can correct mistakes.

## Vertical Splitting Explanation

Vertical splitting creates smaller stories that deliver complete user-visible functionality.

For example, document upload is split into upload, view, and replace capabilities rather than separating frontend, backend, and database work into different stories.
