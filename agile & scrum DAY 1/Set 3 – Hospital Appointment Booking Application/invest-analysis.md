
# INVEST Analysis – Hospital Appointment Booking Application

## INVEST Principles

| Letter | Meaning | Explanation |
|---|---|---|
| I | Independent | Story can be developed independently. |
| N | Negotiable | Details can be discussed and refined. |
| V | Valuable | Provides value to the user. |
| E | Estimable | Development effort can be estimated. |
| S | Small | Can be completed within a Sprint. |
| T | Testable | Has clear conditions for verification. |

## INVEST Checklist

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

## Stories Requiring Improvement

### Weak Story 1 – Original

As a patient, I want to book and manage all my appointments, so that I can access healthcare easily.

### Problems
- Not independent because it combines booking, rescheduling, and cancellation.
- Too broad to estimate accurately.
- Difficult to test as one complete story.

### Rewritten Stories

1. As a patient, I want to book an available appointment slot, so that I can consult a doctor.
2. As a patient, I want to reschedule an existing appointment, so that I can change my appointment time.
3. As a patient, I want to cancel an existing appointment, so that I can release the slot when I cannot attend.

### Weak Story 2 – Original

As a doctor, I want to manage my schedule, so that patients can book appointments.

### Problems
- The word "manage" is unclear.
- It contains multiple functionalities.
- It does not specify a testable outcome.

### Rewritten Stories

1. As a doctor, I want to update my available consultation hours, so that patients can book during my working hours.
2. As a doctor, I want to block unavailable time slots, so that patients cannot book during my leave or other commitments.
3. As a doctor, I want to view my daily appointment schedule, so that I can prepare for patient consultations.

## Conclusion

The rewritten stories have clear user outcomes, smaller scopes, and measurable acceptance conditions. They are more aligned with INVEST principles.
