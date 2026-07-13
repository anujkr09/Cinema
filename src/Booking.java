import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private final String id;
    private final String username;
    private final String movieId;
    private final String movieTitle;
    private final String theater;
    private final String date;
    private final String time;
    private final List<String> seats;
    private final int totalAmount;
    private final String paymentMethod;
    private final LocalDateTime createdAt;

    public Booking(String id, String username, String movieId, String movieTitle, String theater,
                   String date, String time, List<String> seats, int totalAmount, String paymentMethod,
                   LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.theater = theater;
        this.date = date;
        this.time = time;
        this.seats = new ArrayList<>(seats);
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getTheater() {
        return theater;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<String> getSeats() {
        return new ArrayList<>(seats);
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
