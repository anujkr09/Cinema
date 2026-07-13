import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConfig {
    public static final Path DATA_DIR = Paths.get("data");
    public static final Path ASSETS_DIR = Paths.get("assets");
    public static final Path SEED_MOVIES_FILE = ASSETS_DIR.resolve("movies.tsv");
    public static final Path USERS_FILE = DATA_DIR.resolve("users.tsv");
    public static final Path BOOKINGS_FILE = DATA_DIR.resolve("bookings.tsv");
    public static final Path MOVIES_FILE = DATA_DIR.resolve("movies.tsv");
    public static final int SEAT_PRICE = 200;

    private AppConfig() {
    }
}
