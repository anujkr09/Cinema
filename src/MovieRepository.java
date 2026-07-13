import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public final class MovieRepository {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM");
    private static List<Movie> cachedMovies;
    private static final List<Movie> DEFAULT_MOVIES = Arrays.asList(
            movie("raid2", "Raid 2", "Image/Raid_2_poster.jpg", "2h 19m | Drama | Thriller | UA13+", "Hindi", "Ajay Devgn, Riteish Deshmukh, Vaani Kapoor, Saurabh Shukla"),
            movie("kgf", "KGF", "Image/kgf.jpeg", "2h 48m | Action | Drama | UA", "Hindi", "Yash, Srinidhi Shetty, Anant Nag, Ramachandra Raju"),
            movie("rrr", "RRR", "Image/rrr.jpeg", "3h 2m | Action | Period Drama | UA", "Hindi", "N. T. Rama Rao Jr., Ram Charan, Alia Bhatt, Ajay Devgn"),
            movie("drishyam", "Drishyam", "Image/drishyam.jpeg", "2h 43m | Mystery | Thriller | UA", "Hindi", "Ajay Devgn, Tabu, Shriya Saran, Ishita Dutta"),
            movie("pushpa2", "Pushpa 2", "Image/1.jpg", "3h 20m | Action | Drama | UA", "Hindi", "Allu Arjun, Rashmika Mandanna, Fahadh Faasil"),
            movie("jawan", "Jawan", "Image/5.jpg", "2h 49m | Action | Thriller | UA", "Hindi", "Shah Rukh Khan, Nayanthara, Vijay Sethupathi"),
            movie("pathaan", "Pathaan", "Image/8.jpg", "2h 26m | Action | Spy | UA", "Hindi", "Shah Rukh Khan, Deepika Padukone, John Abraham"),
            movie("animal", "Animal", "Image/4.jpg", "3h 21m | Action | Drama | A", "Hindi", "Ranbir Kapoor, Rashmika Mandanna, Anil Kapoor"),
            movie("dangal", "Dangal", "Image/2.jpg", "2h 41m | Sports | Drama | U", "Hindi", "Aamir Khan, Fatima Sana Shaikh, Sanya Malhotra"),
            movie("pk", "PK", "Image/3.jpg", "2h 33m | Comedy | Drama | UA", "Hindi", "Aamir Khan, Anushka Sharma, Sushant Singh Rajput"),
            movie("3idiots", "3 Idiots", "Image/6.jpg", "2h 50m | Comedy | Drama | U", "Hindi", "Aamir Khan, R. Madhavan, Sharman Joshi, Kareena Kapoor"),
            movie("taarezameenpar", "Taare Zameen Par", "Image/12.jpg", "2h 42m | Family | Drama | U", "Hindi", "Darsheel Safary, Aamir Khan, Tisca Chopra"),
            movie("chhichhore", "Chhichhore", "Image/13.jpg", "2h 26m | Comedy | Drama | UA", "Hindi", "Sushant Singh Rajput, Shraddha Kapoor, Varun Sharma"),
            movie("queen", "Queen", "Image/14.jpg", "2h 26m | Comedy | Drama | UA", "Hindi", "Kangana Ranaut, Rajkummar Rao, Lisa Haydon"),
            movie("barfi", "Barfi", "Image/15.jpg", "2h 31m | Romance | Comedy | U", "Hindi", "Ranbir Kapoor, Priyanka Chopra, Ileana D'Cruz"),
            movie("zindaginamilegidobara", "Zindagi Na Milegi Dobara", "Image/1.jpg", "2h 35m | Comedy | Drama | UA", "Hindi", "Hrithik Roshan, Farhan Akhtar, Abhay Deol, Katrina Kaif"),
            movie("gullyboy", "Gully Boy", "Image/2.jpg", "2h 34m | Musical | Drama | UA", "Hindi", "Ranveer Singh, Alia Bhatt, Siddhant Chaturvedi"),
            movie("war", "War", "Image/3.jpg", "2h 34m | Action | Thriller | UA", "Hindi", "Hrithik Roshan, Tiger Shroff, Vaani Kapoor"),
            movie("brahmastra", "Brahmastra", "Image/4.jpg", "2h 47m | Fantasy | Adventure | UA", "Hindi", "Ranbir Kapoor, Alia Bhatt, Amitabh Bachchan"),
            movie("shershaah", "Shershaah", "Image/5.jpg", "2h 15m | Biography | War | UA", "Hindi", "Sidharth Malhotra, Kiara Advani"),
            movie("uri", "Uri: The Surgical Strike", "Image/6.jpg", "2h 18m | Action | War | UA", "Hindi", "Vicky Kaushal, Yami Gautam, Paresh Rawal"),
            movie("andhadhun", "Andhadhun", "Image/8.jpg", "2h 18m | Crime | Thriller | UA", "Hindi", "Ayushmann Khurrana, Tabu, Radhika Apte"),
            movie("kantara", "Kantara", "Image/12.jpg", "2h 30m | Action | Drama | UA", "Hindi", "Rishab Shetty, Sapthami Gowda, Kishore"),
            movie("bahubali1", "Baahubali: The Beginning", "Image/13.jpg", "2h 39m | Action | Epic | UA", "Hindi", "Prabhas, Rana Daggubati, Anushka Shetty, Tamannaah"),
            movie("bahubali2", "Baahubali 2: The Conclusion", "Image/14.jpg", "2h 47m | Action | Epic | UA", "Hindi", "Prabhas, Rana Daggubati, Anushka Shetty"),
            movie("vikram", "Vikram", "Image/15.jpg", "2h 54m | Action | Thriller | UA", "Hindi", "Kamal Haasan, Vijay Sethupathi, Fahadh Faasil"),
            movie("leo", "Leo", "Image/1.jpg", "2h 44m | Action | Thriller | UA", "Hindi", "Vijay, Trisha Krishnan, Sanjay Dutt"),
            movie("master", "Master", "Image/2.jpg", "2h 59m | Action | Drama | UA", "Hindi", "Vijay, Vijay Sethupathi, Malavika Mohanan"),
            movie("kaithi", "Kaithi", "Image/3.jpg", "2h 25m | Action | Thriller | UA", "Hindi", "Karthi, Narain, Dheena"),
            movie("jailer", "Jailer", "Image/4.jpg", "2h 48m | Action | Comedy | UA", "Hindi", "Rajinikanth, Mohanlal, Shiva Rajkumar"),
            movie("interstellar", "Interstellar", "Image/5.jpg", "2h 49m | Sci-Fi | Adventure | UA", "English", "Matthew McConaughey, Anne Hathaway, Jessica Chastain"),
            movie("inception", "Inception", "Image/6.jpg", "2h 28m | Sci-Fi | Thriller | UA", "English", "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page"),
            movie("thedarkknight", "The Dark Knight", "Image/8.jpg", "2h 32m | Action | Crime | UA", "English", "Christian Bale, Heath Ledger, Aaron Eckhart"),
            movie("oppenheimer", "Oppenheimer", "Image/12.jpg", "3h | Biography | Drama | UA", "English", "Cillian Murphy, Emily Blunt, Robert Downey Jr."),
            movie("avatar", "Avatar", "Image/13.jpg", "2h 42m | Sci-Fi | Adventure | UA", "English", "Sam Worthington, Zoe Saldana, Sigourney Weaver"),
            movie("avatarthewayofwater", "Avatar: The Way of Water", "Image/14.jpg", "3h 12m | Sci-Fi | Adventure | UA", "English", "Sam Worthington, Zoe Saldana, Sigourney Weaver"),
            movie("avengersendgame", "Avengers: Endgame", "Image/15.jpg", "3h 1m | Superhero | Action | UA", "English", "Robert Downey Jr., Chris Evans, Scarlett Johansson"),
            movie("avengersinfinitywar", "Avengers: Infinity War", "Image/1.jpg", "2h 29m | Superhero | Action | UA", "English", "Robert Downey Jr., Chris Hemsworth, Josh Brolin"),
            movie("spidermannowayhome", "Spider-Man: No Way Home", "Image/2.jpg", "2h 28m | Superhero | Adventure | UA", "English", "Tom Holland, Zendaya, Benedict Cumberbatch"),
            movie("joker", "Joker", "Image/3.jpg", "2h 2m | Crime | Drama | A", "English", "Joaquin Phoenix, Robert De Niro, Zazie Beetz"),
            movie("topgunmaverick", "Top Gun: Maverick", "Image/4.jpg", "2h 10m | Action | Drama | UA", "English", "Tom Cruise, Miles Teller, Jennifer Connelly"),
            movie("missionimpossibledeadreckoning", "Mission: Impossible - Dead Reckoning", "Image/5.jpg", "2h 43m | Action | Spy | UA", "English", "Tom Cruise, Hayley Atwell, Ving Rhames"),
            movie("fastx", "Fast X", "Image/6.jpg", "2h 21m | Action | Crime | UA", "English", "Vin Diesel, Jason Momoa, Michelle Rodriguez"),
            movie("johnwick4", "John Wick: Chapter 4", "Image/8.jpg", "2h 49m | Action | Thriller | A", "English", "Keanu Reeves, Donnie Yen, Bill Skarsgard")
    );

    private MovieRepository() {
    }

    public static synchronized List<Movie> allMovies() {
        if (cachedMovies == null) {
            ensureDataset();
            List<Movie> movies = loadMovies();
            cachedMovies = movies.isEmpty() ? DEFAULT_MOVIES : movies;
        }
        return cachedMovies;
    }

    public static List<Movie> searchMovies(String query) {
        String normalized = query == null ? "" : query.trim().toLowerCase();
        if (normalized.isEmpty()) {
            return allMovies();
        }
        List<Movie> matches = new ArrayList<>();
        for (Movie movie : allMovies()) {
            String haystack = String.join(" ",
                    movie.getTitle(),
                    movie.getLanguage(),
                    movie.getFormat(),
                    movie.getDetails(),
                    movie.getDescription(),
                    String.join(" ", movie.getCast())).toLowerCase();
            if (haystack.contains(normalized)) {
                matches.add(movie);
            }
        }
        return matches;
    }

    public static Optional<Movie> findMovie(String id) {
        return allMovies().stream().filter(movie -> movie.getId().equals(id)).findFirst();
    }

    public static List<Showtime> showtimesFor(String movieId) {
        String[] theaters = {"Cinepolis : DB Mall, Bhopal", "PVR : Aura Mall, Bhopal", "INOX : Century 21, Bhopal"};
        String[] times = {"10:00 AM", "01:30 PM", "05:00 PM", "08:30 PM"};
        List<Showtime> showtimes = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int day = 0; day < 5; day++) {
            String date = today.plusDays(day).format(DATE_FORMAT);
            for (int theaterIndex = 0; theaterIndex < theaters.length; theaterIndex++) {
                for (int timeIndex = 0; timeIndex < times.length; timeIndex++) {
                    String id = movieId + "-" + day + "-" + theaterIndex + "-" + timeIndex;
                    showtimes.add(new Showtime(id, movieId, theaters[theaterIndex], date, times[timeIndex]));
                }
            }
        }
        return showtimes;
    }

    private static void ensureDataset() {
        try {
            Files.createDirectories(AppConfig.DATA_DIR);
            if (Files.exists(AppConfig.SEED_MOVIES_FILE)) {
                Files.copy(AppConfig.SEED_MOVIES_FILE, AppConfig.MOVIES_FILE, StandardCopyOption.REPLACE_EXISTING);
            } else if (!Files.exists(AppConfig.MOVIES_FILE)) {
                saveDefaultDataset();
            } else {
                if (mergeDefaultDataset()) {
                    cachedMovies = null;
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to prepare movie dataset.", ex);
        }
    }

    private static void saveDefaultDataset() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("id\ttitle\tposter_path\tbackground_path\tformat\tlanguage\tdetails\tdescription\tcast");
        for (Movie movie : DEFAULT_MOVIES) {
            lines.add(toLine(movie));
        }
        Files.write(AppConfig.MOVIES_FILE, lines, StandardCharsets.UTF_8);
    }

    private static boolean mergeDefaultDataset() throws IOException {
        List<String> lines = Files.readAllLines(AppConfig.MOVIES_FILE, StandardCharsets.UTF_8);
        Set<String> existingIds = new HashSet<>();
        boolean hasHeader = false;
        for (String line : lines) {
            if (line.startsWith("id\t")) {
                hasHeader = true;
                continue;
            }
            String[] p = line.split("\t", -1);
            if (p.length > 0 && !p[0].isBlank()) {
                existingIds.add(p[0].trim());
            }
        }
        List<String> updated = new ArrayList<>();
        if (!hasHeader) {
            updated.add("id\ttitle\tposter_path\tbackground_path\tformat\tlanguage\tdetails\tdescription\tcast");
        }
        updated.addAll(lines);
        boolean changed = !hasHeader;
        for (Movie movie : DEFAULT_MOVIES) {
            if (!existingIds.contains(movie.getId())) {
                updated.add(toLine(movie));
                changed = true;
            }
        }
        if (changed) {
            Files.write(AppConfig.MOVIES_FILE, updated, StandardCharsets.UTF_8);
        }
        return changed;
    }

    private static List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(AppConfig.MOVIES_FILE, StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("id\t")) {
                    continue;
                }
                String[] p = line.split("\t", -1);
                if (p.length >= 9) {
                    movies.add(new Movie(
                            p[0].trim(),
                            p[1].trim(),
                            p[2].trim(),
                            p[3].trim(),
                            p[4].trim(),
                            p[5].trim(),
                            p[6].trim(),
                            shortText(p[7].trim()),
                            Arrays.stream(p[8].split(",")).map(String::trim).toArray(String[]::new)));
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to load movie dataset.", ex);
        }
        return movies;
    }

    private static String toLine(Movie movie) {
        return String.join("\t",
                movie.getId(),
                movie.getTitle(),
                movie.getPosterPath(),
                movie.getBackgroundPath(),
                movie.getFormat(),
                movie.getLanguage(),
                movie.getDetails(),
                movie.getDescription(),
                String.join(", ", movie.getCast()));
    }

    private static Movie movie(String id, String title, String posterPath, String details, String language, String cast) {
        return new Movie(
                id,
                title,
                posterPath,
                "Image/3.jpg",
                "2D",
                language,
                details,
                title + " is available in the current catalogue with fresh showtimes and seat booking.",
                Arrays.stream(cast.split(",")).map(String::trim).toArray(String[]::new));
    }

    private static String shortText(String text) {
        if (text.length() <= 420) {
            return text;
        }
        int sentence = text.indexOf(". ", 220);
        if (sentence > 0 && sentence < 420) {
            return text.substring(0, sentence + 1);
        }
        return text.substring(0, 417) + "...";
    }
}
