public class Showtime {
    private final String id;
    private final String movieId;
    private final String theater;
    private final String date;
    private final String time;

    public Showtime(String id, String movieId, String theater, String date, String time) {
        this.id = id;
        this.movieId = movieId;
        this.theater = theater;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
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
}
