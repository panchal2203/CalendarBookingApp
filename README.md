# Calendar Booking Application
This is a Calendar Booking Application built using Spring Boot and H2 in-memory database. The application allows users to create and manage calendars, schedule appointments, and invitees can book available slots.

## Installation
To set up and run the application locally:

1. Clone or download the repository.
2. Open the project in your preferred IDE.
3. Run CalendarApplication.java to start the Spring Boot application.
4. Postman API collection is provided in the postman_collection.json file for easier API testing.
5. Access the H2 in-memory database console at http://localhost:8080/h2-console/login.jsp. The configuration can be found in the src/main/resources/application.properties file.

## Usage

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

## API Documentation

* GET /users/{emailAddress} -> get user details
* POST /users -> create user
* PUT /users -> update user details

* POST /calendar/ -> create calendar
* PUT /calendar/ -> update calendar
* GET /calendar/{calendarId} -> get calendar details with appointments
* GET /{calendarId}/availableSlots -> get available slots

* GET /appointment/{calendarOwnerId}/appointments -> get all upcoming appointments of user
* POST /appointment -> create appointment
* PUT /appointment -> update appointment
* DELETE /appointment -> delete appointment

## Entities

* User
* Calendar
* Appointment


