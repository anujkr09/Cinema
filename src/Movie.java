public class Movie {
    private final String id;
    private final String title;
    private final String posterPath;
    private final String backgroundPath;
    private final String format;
    private final String language;
    private final String details;
    private final String description;
    private final String[] cast;

    public Movie(String id, String title, String posterPath, String backgroundPath, String format,
                 String language, String details, String description, String[] cast) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.backgroundPath = backgroundPath;
        this.format = format;
        this.language = language;
        this.details = details;
        this.description = description;
        this.cast = cast;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public String getFormat() {
        return format;
    }

    public String getLanguage() {
        return language;
    }

    public String getDetails() {
        return details;
    }

    public String getDescription() {
        return description;
    }

    public String[] getCast() {
        return cast;
    }
}
