# Calendar Booking Application 

This is a project repo of a calendar booking application created using Spring-boot and H2 in-memory database.


# Installation

1. Download and open this application on ide, and run CalendarApplication.java.
2. I have included postman api collection at postman_collection.json.
3. Login to http://localhost:8080/h2-console/login.jsp for database access, configuration provided in src/main/resources/application.properties.

# Usage

1. User can create account.
2. User can create/schedule calendar.
3. Invitee can book appointment for given calendar shared by calendar owner.
4. Invitee can view available slots for the given time period.
5. Calendar owner can view all its upcoming appointments.
6. Calendar owner can create multiple calendar.
7. Invitee can't book overlapping slot of calendar owner or different calendars of same calendar owner.
8. Invitee can cancel/update/reschedule appointment. 9
9. Calendar owner can provide custom slot duration, default duration is 60 minutes.
10. Calendar owner can fetch calendar details with all its appointment.
11. Calendar owner can update calendar.

# API Documentation

* GET /users/{emailAddress} -> get user details
* POST /users -> create user
* PUT /users -> update user details

* POST /calendar/ -> create calendar
* PUT /calendar/ -> update calendar
* GET /calendar/{calendarId} -> get calendar details with appointments
* GET /{calendarId}/availableSlots -> get available slots

* GET /appointment/{calendarOwnerId}/appointments -> get all upcoming appointment of user
* POST /appointment -> create appointment
* PUT /appointment -> update appointment
* DELETE /appointment -> delete appointment

# Entities;

* User
* Calendar
* Appointment




