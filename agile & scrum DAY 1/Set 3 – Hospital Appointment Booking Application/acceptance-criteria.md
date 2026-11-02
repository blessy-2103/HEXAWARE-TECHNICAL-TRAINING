
# Acceptance Criteria – Hospital Appointment Booking Application

Acceptance criteria define the expected behaviour of each user story using Given / When / Then.

# Epic 1: Appointment Booking

## US01 – View Doctors

### Scenario: Display available doctors
- Given the patient opens the appointment booking section,
- When the patient selects a medical department,
- Then the application should display the doctors available in that department.

## US02 – View Available Appointment Slots

### Scenario: Display available slots
- Given the patient has selected a doctor,
- When the patient selects an appointment date,
- Then the application should display the available time slots for that doctor.

## US03 – Book an Appointment

### Scenario 1: Successful appointment booking
- Given the patient has selected a doctor and an available appointment slot,
- When the patient confirms the booking,
- Then the application should create the appointment and display a booking confirmation with an appointment ID.

### Scenario 2: Slot already taken (Negative Scenario)
- Given the patient has selected an appointment slot,
- When another patient books that slot before the first patient confirms,
- Then the application should prevent duplicate booking and display a message that the slot is no longer available.

### Scenario 3: Missing mandatory information
- Given the patient is on the appointment booking form,
- When the patient leaves mandatory information empty and tries to confirm,
- Then the application should display validation messages and prevent booking.

# Epic 2: Rescheduling and Cancellation

## US04 – View Existing Appointments

### Scenario: View upcoming appointments
- Given the patient is logged into the application,
- When the patient opens My Appointments,
- Then the application should display the patient's upcoming appointments.

## US05 – Reschedule an Appointment

### Scenario: Successfully reschedule
- Given the patient has an existing appointment,
- When the patient selects a new available time slot and confirms,
- Then the application should update the appointment details and display a rescheduling confirmation.

### Scenario: New slot unavailable
- Given the patient has selected a new appointment slot,
- When the selected slot becomes unavailable before confirmation,
- Then the application should prevent rescheduling and ask the patient to select another slot.

## US06 – Cancel an Appointment

### Scenario 1: Successfully cancel an appointment
- Given the patient has an upcoming appointment,
- When the patient selects Cancel Appointment and confirms the cancellation,
- Then the application should update the appointment status to Cancelled and display a cancellation confirmation.

### Scenario 2: Cancel an already cancelled appointment
- Given the appointment has already been cancelled,
- When the patient attempts to cancel it again,
- Then the application should inform the patient that the appointment is already cancelled.

## Summary

- Booking stories: 3
- Cancellation and rescheduling stories: 3
- Negative scenarios: Slot already taken, missing information, unavailable rescheduling slot, repeated cancellation.
