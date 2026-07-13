# Movie Ticket Booking System

A Java Swing desktop app for booking movie tickets. The app now uses dynamic movie/showtime data, local user and booking persistence, secure password hashing, seat locking, payment validation, and ticket generation.

## Features

- Register and login with locally saved users
- Passwords are stored as salted SHA-256 hashes
- Dynamic movie dashboard
- Runtime movie search from an app-managed movie catalogue
- Movie details are passed through the booking flow
- Dynamic showtime selection by movie, date, theater, and time
- User-selected seat count is respected
- Already booked seats are disabled for the same movie/showtime
- Payment validation for card and UPI
- Ticket summary with booking ID, save, and print actions
- No external database driver is required for normal local use

## Run

```powershell
javac -encoding UTF-8 -d bin src\*.java
java -cp bin LoginPage
```

The app creates a `data/` folder automatically:

- `data/users.tsv`
- `data/bookings.tsv`
- `data/movies.tsv`

These files are ignored by Git because they contain local runtime data.

## Movie Dataset

Movies are loaded from an app-managed catalogue. The tracked seed file is `assets/movies.tsv`; on first run, the app copies it to `data/movies.tsv`. When the code ships with more built-in movies later, missing records are merged automatically.

Internal dataset format:

```text
id	title	poster_path	background_path	format	language	details	description	cast
```

Example:

```text
interstellar	Interstellar	Image/1.jpg	Image/3.jpg	2D	English	2h 49m | Sci-Fi | Adventure	A team travels through a wormhole to find humanity a new home.	Matthew McConaughey, Anne Hathaway, Jessica Chastain
```

Dashboard search checks title, language, details, description, genre, and cast.

## Project Structure

- `src/LoginPage.java` - login screen
- `src/RegistrationPage.java` - registration screen
- `src/DashBoard.java` - movie dashboard
- `src/MovieInfo.java` - movie details
- `src/MovieShowtimesUI.java` - date, theater, time, and seat count selection
- `src/SeatLayoutManager.java` - seat selection and booked-seat locking
- `src/PaymentPageUI.java` - payment validation and booking creation
- `src/TicketGeneratorUI.java` - ticket view, save, and print
- `src/UserService.java` - local user persistence
- `src/BookingService.java` - local booking persistence
- `src/MovieRepository.java` - movie and showtime data
- `data/movies.tsv` - runtime movie dataset created automatically
- `database/schema.sql` - optional MySQL schema for future DB migration

## Notes

The old `RegistationPage` typo class is kept as a small compatibility wrapper, but new code uses `RegistrationPage`.
