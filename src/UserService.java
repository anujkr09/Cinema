import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    public UserService() {
        ensureStorage();
    }

    public synchronized void register(String name, String email, String mobile, String username, String password) {
        validate(name, email, mobile, username, password);
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("Username already exists.");
            }
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email already exists.");
            }
            if (user.getMobile().equals(mobile)) {
                throw new IllegalArgumentException("Mobile number already exists.");
            }
        }
        users.add(new User(name, email, mobile, username, PasswordUtil.hash(password)));
        saveUsers(users);
    }

    public Optional<User> login(String username, String password) {
        return loadUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .filter(user -> PasswordUtil.verify(password, user.getPasswordHash()))
                .findFirst();
    }

    private void validate(String name, String email, String mobile, String username, String password) {
        if (name.isBlank() || email.isBlank() || mobile.isBlank() || username.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Please fill all fields.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        if (!mobile.matches("\\d{10}")) {
            throw new IllegalArgumentException("Enter a valid 10 digit mobile number.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
    }

    private List<User> loadUsers() {
        ensureStorage();
        List<User> users = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(AppConfig.USERS_FILE, StandardCharsets.UTF_8)) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\t", -1);
                if (parts.length == 5) {
                    users.add(new User(unescape(parts[0]), unescape(parts[1]), unescape(parts[2]), unescape(parts[3]), parts[4]));
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to read users.", ex);
        }
        return users;
    }

    private void saveUsers(List<User> users) {
        ensureStorage();
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.add(String.join("\t",
                    escape(user.getName()),
                    escape(user.getEmail()),
                    escape(user.getMobile()),
                    escape(user.getUsername()),
                    user.getPasswordHash()));
        }
        try {
            Files.write(AppConfig.USERS_FILE, lines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save users.", ex);
        }
    }

    private void ensureStorage() {
        try {
            Files.createDirectories(AppConfig.DATA_DIR);
            if (!Files.exists(AppConfig.USERS_FILE)) {
                Files.createFile(AppConfig.USERS_FILE);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to prepare data folder.", ex);
        }
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n");
    }

    private String unescape(String value) {
        return value.replace("\\n", "\n").replace("\\t", "\t").replace("\\\\", "\\");
    }
}
