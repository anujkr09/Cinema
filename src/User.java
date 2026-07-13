public class User {
    private final String name;
    private final String email;
    private final String mobile;
    private final String username;
    private final String passwordHash;

    public User(String name, String email, String mobile, String username, String passwordHash) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
