import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BookingService {
    public BookingService() {
        ensureStorage();
    }

    public synchronized Set<String> bookedSeats(Showtime showtime) {
        Set<String> booked = new HashSet<>();
        for (Booking booking : loadBookings()) {
            if (sameShowtime(booking, showtime)) {
                booked.addAll(booking.getSeats());
            }
        }
        return booked;
    }

    public synchronized Booking createBooking(User user, Movie movie, Showtime showtime, List<String> seats,
                                              String paymentMethod) {
        if (seats.isEmpty()) {
            throw new IllegalArgumentException("Select at least one seat.");
        }
        Set<String> alreadyBooked = bookedSeats(showtime);
        for (String seat : seats) {
            if (alreadyBooked.contains(seat)) {
                throw new IllegalArgumentException("Seat " + seat + " is already booked. Please choose another seat.");
            }
        }
        Booking booking = new Booking(
                "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                user.getUsername(),
                movie.getId(),
                movie.getTitle(),
                showtime.getTheater(),
                showtime.getDate(),
                showtime.getTime(),
                seats,
                seats.size() * AppConfig.SEAT_PRICE,
                paymentMethod,
                LocalDateTime.now());
        List<Booking> bookings = loadBookings();
        bookings.add(booking);
        saveBookings(bookings);
        return booking;
    }

    private boolean sameShowtime(Booking booking, Showtime showtime) {
        return booking.getMovieId().equals(showtime.getMovieId())
                && booking.getTheater().equals(showtime.getTheater())
                && booking.getDate().equals(showtime.getDate())
                && booking.getTime().equals(showtime.getTime());
    }

    private List<Booking> loadBookings() {
        ensureStorage();
        List<Booking> bookings = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(AppConfig.BOOKINGS_FILE, StandardCharsets.UTF_8)) {
                if (line.isBlank()) {
                    continue;
                }
                String[] p = line.split("\t", -1);
                if (p.length == 11) {
                    bookings.add(new Booking(
                            p[0], unescape(p[1]), unescape(p[2]), unescape(p[3]), unescape(p[4]),
                            unescape(p[5]), unescape(p[6]), List.of(unescape(p[7]).split(",", -1)),
                            Integer.parseInt(p[8]), unescape(p[9]), LocalDateTime.parse(p[10])));
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to read bookings.", ex);
        }
        return bookings;
    }

    private void saveBookings(List<Booking> bookings) {
        ensureStorage();
        List<String> lines = new ArrayList<>();
        for (Booking b : bookings) {
            lines.add(String.join("\t",
                    b.getId(),
                    escape(b.getUsername()),
                    escape(b.getMovieId()),
                    escape(b.getMovieTitle()),
                    escape(b.getTheater()),
                    escape(b.getDate()),
                    escape(b.getTime()),
                    escape(String.join(",", b.getSeats())),
                    String.valueOf(b.getTotalAmount()),
                    escape(b.getPaymentMethod()),
                    b.getCreatedAt().toString()));
        }
        try {
            Files.write(AppConfig.BOOKINGS_FILE, lines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save bookings.", ex);
        }
    }

    private void ensureStorage() {
        try {
            Files.createDirectories(AppConfig.DATA_DIR);
            if (!Files.exists(AppConfig.BOOKINGS_FILE)) {
                Files.createFile(AppConfig.BOOKINGS_FILE);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to prepare bookings file.", ex);
        }
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n");
    }

    private String unescape(String value) {
        return value.replace("\\n", "\n").replace("\\t", "\t").replace("\\\\", "\\");
    }
}
