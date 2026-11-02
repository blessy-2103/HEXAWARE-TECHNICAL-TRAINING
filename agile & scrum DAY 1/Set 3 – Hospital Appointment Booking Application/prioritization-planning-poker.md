
# Prioritisation and Planning Poker
## Hospital Appointment Booking Application

## 1. Business Value vs Effort

Business value indicates how much benefit a story provides to patients or doctors.

Effort indicates the relative complexity and work required to implement a story.

| ID | User Story | Business Value | Effort | Priority |
|---|---|---|---|---|
| US01 | View doctors | High | Medium | High |
| US02 | View available slots | High | Medium | High |
| US03 | Book an appointment | Very High | High | Highest |
| US04 | View existing appointments | High | Low | High |
| US05 | Reschedule appointment | High | High | High |
| US06 | Cancel appointment | High | Low | High |
| US07 | Receive appointment reminder | Medium | Medium | Medium |
| US08 | View reminder details | Medium | Low | Medium |
| US09 | Manage reminder preferences | Low | Medium | Low |
| US10 | View doctor schedule | High | Medium | High |
| US11 | Update doctor availability | High | Medium | High |
| US12 | Block unavailable slots | High | Low | High |

## 2. Prioritised Backlog with Story Points

| ID | User Story | Priority | Story Points |
|---|---|---|---:|
| US01 | View doctors | High | 3 |
| US02 | View available slots | High | 3 |
| US03 | Book appointment | Highest | 5 |
| US04 | View existing appointments | High | 2 |
| US05 | Reschedule appointment | High | 5 |
| US06 | Cancel appointment | High | 2 |
| US07 | Appointment reminders | Medium | 3 |
| US08 | View reminder details | Medium | 1 |
| US09 | Manage reminder preferences | Low | 2 |
| US10 | View doctor schedule | High | 3 |
| US11 | Update doctor availability | High | 3 |
| US12 | Block unavailable slots | High | 2 |

## 3. Fibonacci Story Point Scale

The team uses the following Fibonacci values:

1, 2, 3, 5, 8, 13

- 1: Very simple story.
- 2: Small effort.
- 3: Moderate effort.
- 5: Medium-to-high complexity.
- 8: Complex story.
- 13: Very complex story requiring further splitting.

## 4. Reference Story

US08 – View Reminder Details

Estimated Story Points: 1

Justification:
This is a simple story involving displaying existing appointment reminder information. It is selected as the reference story for comparing the complexity of other stories.

## 5. Planning Poker Round

### Example: US03 – Book an Appointment

Three developers provide their estimates:

| Developer | Initial Estimate |
|---|---:|
| Developer A | 3 |
| Developer B | 5 |
| Developer C | 8 |

### Discussion on Outlier

Developer A estimated 3 points because the story appeared to involve only selecting and confirming a time slot.

Developer B estimated 5 points because the functionality also requires checking availability and generating a booking confirmation.

Developer C estimated 8 points because they considered concurrent bookings, duplicate slot prevention, and booking validation.

### Final Decision

After discussion, the team agreed on 5 story points.

Reason:
The story includes slot availability checking, booking validation, and confirmation. Advanced scheduling features are outside this story's scope.

## 6. Difference Between Story Points and Hours

| Story Points | Hours |
|---|---|
| Measure relative complexity and effort. | Measure actual time spent. |
| Include uncertainty and technical complexity. | Represent duration. |
| Assigned collaboratively by Developers. | Usually recorded by individuals or teams. |
| Do not directly represent working hours. | Represent time in hours. |
| Used for relative estimation and planning. | Used for time tracking and scheduling. |

### Example

A story estimated at 5 points does not mean it requires 5 hours.

It means the story is relatively more complex than a 2-point story.

Different teams may complete the same story in different amounts of time.

## 7. Total Backlog Size

Total Story Points:

3 + 3 + 5 + 2 + 5 + 2 + 3 + 1 + 2 + 3 + 3 + 2 = 34

**Total Backlog Size = 34 Story Points**

Note: These are illustrative estimates. In an actual Planning Poker session, the Developers collectively decide the final story points.
