# Hospital Appointment System – Test Data

## 1. Patient Records – Searching

**Total patient records:** 20,000

**Patient ID range:** 100001 to 120000

**Number of look-ups:** 1,000

| Patient ID | Patient Name |
|---|---|
| 100001 | Patient1 |
| 100002 | Patient2 |
| 100003 | Patient3 |
| 100004 | Patient4 |
| 100005 | Patient5 |
| 100006 | Patient6 |
| 100007 | Patient7 |
| 100008 | Patient8 |
| 100009 | Patient9 |
| 100010 | Patient10 |

**Search targets:** Generated using patient records at different positions in the array.

## 2. Appointment Records – Sorting

| Appointment ID | Patient ID | Urgency | Arrival Time |
|---|---|---|---|
| 1 | 101 | 3 | 10:00 AM |
| 2 | 102 | 1 | 09:00 AM |
| 3 | 103 | 2 | 08:00 AM |
| 4 | 104 | 1 | 08:30 AM |

**Sorting conditions:**
- First priority: Urgency level (1 = highest priority).
- Second priority: Arrival time (earliest first).

**Expected sorted order:**

| Appointment ID | Urgency | Arrival Time |
|---|---|---|
| 4 | 1 | 08:30 AM |
| 2 | 1 | 09:00 AM |
| 3 | 2 | 08:00 AM |
| 1 | 3 | 10:00 AM |

## 3. Duplicate Booking Detection

| Appointment ID | Patient ID | Start Time | End Time |
|---|---|---|---|
| 10 | 201 | 10:00 AM | 11:00 AM |
| 11 | 201 | 10:00 AM | 11:00 AM |
| 12 | 202 | 11:00 AM | 12:00 PM |

**Expected output:**
```text
Duplicate booking found: Appointment 11
Total duplicate bookings: 1
```

## 4. Queue – Walk-in Patients

**Input:**
```text
301, 302, 303
```

**Expected output:**
```text
Walk-in patient: 301
```

The first patient who enters the queue is served first (FIFO).

## 5. Stack – Recently Viewed Patients

**Input:**
```text
401, 402, 403
```

**Expected output:**
```text
Recently viewed patient: 403
```

The most recently viewed patient is retrieved first (LIFO).

## 6. Dynamic Programming – Appointment Scheduling

| Appointment ID | Start Time | End Time | Duration |
|---|---|---|---|
| 21 | 09:00 AM | 11:00 AM | 2 hours |
| 22 | 09:00 AM | 10:00 AM | 1 hour |
| 23 | 10:00 AM | 12:00 PM | 2 hours |
| 24 | 11:00 AM | 12:00 PM | 1 hour |
| 25 | 12:00 PM | 02:00 PM | 2 hours |
| 26 | 01:00 PM | 02:00 PM | 1 hour |
| 27 | 02:00 PM | 03:00 PM | 1 hour |

**Expected output:**
```text
DP Maximum Appointments: 4
Greedy Maximum Appointments: 4
```

**Explanation:** One possible optimal schedule is appointments 22, 24, 26, and 27.

## Conclusion

These test records are used to verify patient searching, appointment sorting, duplicate detection, queue and stack operations, and maximum appointment scheduling.
