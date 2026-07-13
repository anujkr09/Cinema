import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class MovieShowtimesUI extends JFrame {
    private final User user;
    private final Movie movie;
    private Showtime selectedShowtime;
    private JButton selectedButton;
    private JSpinner seatCount;

    public MovieShowtimesUI(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
        setTitle("Showtimes - " + movie.getTitle());
        setSize(1180, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(buildContent());
        setVisible(true);
    }

    private JPanel buildContent() {
        JPanel root = UIFactory.darkPanel();
        root.setBorder(BorderFactory.createEmptyBorder(22, 32, 22, 32));
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JButton back = UIFactory.secondaryButton("Back");
        back.addActionListener(e -> {
            dispose();
            new MovieInfo(user, movie);
        });
        JLabel title = UIFactory.heading(movie.getTitle() + " - Showtimes", 28);
        header.add(back, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        JPanel showtimePanel = new JPanel(new GridLayout(0, 1, 0, 14));
        showtimePanel.setOpaque(false);
        Map<String, JPanel> groups = new LinkedHashMap<>();
        List<Showtime> showtimes = MovieRepository.showtimesFor(movie.getId());
        for (Showtime showtime : showtimes) {
            String key = showtime.getDate() + " | " + showtime.getTheater();
            groups.computeIfAbsent(key, k -> groupPanel(k)).add(timeButton(showtime));
        }
        for (JPanel panel : groups.values()) {
            showtimePanel.add(panel);
        }
        JScrollPane scroll = new JScrollPane(showtimePanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        JPanel footer = new JPanel(new BorderLayout(16, 0));
        footer.setOpaque(false);
        JLabel seatsLabel = new JLabel("Seats");
        seatsLabel.setForeground(Color.WHITE);
        seatsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        seatCount = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        JButton next = UIFactory.primaryButton("Select Seat Position");
        next.addActionListener(e -> proceed());
        JPanel seatBox = new JPanel(new BorderLayout(8, 0));
        seatBox.setOpaque(false);
        seatBox.add(seatsLabel, BorderLayout.WEST);
        seatBox.add(seatCount, BorderLayout.CENTER);
        footer.add(seatBox, BorderLayout.WEST);
        footer.add(next, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        return root;
    }

    private JPanel groupPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 5, 10, 10));
        panel.setBackground(UIFactory.PANEL);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIFactory.LINE),
                title, 0, 0, new Font("Segoe UI", Font.BOLD, 15), Color.WHITE));
        return panel;
    }

    private JButton timeButton(Showtime showtime) {
        JButton button = UIFactory.secondaryButton(showtime.getTime());
        button.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setBackground(Color.WHITE);
                selectedButton.setForeground(UIFactory.INK);
            }
            selectedButton = button;
            selectedShowtime = showtime;
            button.setBackground(UIFactory.GOLD);
            button.setForeground(UIFactory.INK);
        });
        return button;
    }

    private void proceed() {
        if (selectedShowtime == null) {
            JOptionPane.showMessageDialog(this, "Please select a showtime.");
            return;
        }
        dispose();
        new SeatLayoutManager(user, movie, selectedShowtime, (Integer) seatCount.getValue());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieShowtimesUI(new User("Demo", "demo@example.com", "9999999999", "demo", ""),
                MovieRepository.allMovies().get(0)));
    }
}
