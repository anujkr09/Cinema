import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.Timer;

public class DashBoard extends JFrame {
    private final User user;
    private JPanel movieGrid;
    private JLabel resultLabel;
    private JTextField searchField;
    private Timer searchTimer;

    public DashBoard(User user) {
        this.user = user;
        setTitle("Movie Ticket Booking - Dashboard");
        setSize(1180, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(buildContent());
        setVisible(true);
    }

    private JPanel buildContent() {
        JPanel root = UIFactory.darkPanel();
        root.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JPanel header = new JPanel(new BorderLayout(18, 14));
        header.setOpaque(false);
        JLabel title = UIFactory.heading("Movie Catalogue", 32);
        JLabel userLabel = new JLabel("Logged in as " + user.getUsername(), SwingConstants.RIGHT);
        userLabel.setForeground(new Color(220, 224, 232));
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JButton logout = UIFactory.secondaryButton("Logout");
        logout.addActionListener(e -> {
            dispose();
            new LoginPage();
        });
        JPanel right = new JPanel(new BorderLayout(12, 0));
        right.setOpaque(false);
        right.add(userLabel, BorderLayout.CENTER);
        right.add(logout, BorderLayout.EAST);
        header.add(title, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        JPanel searchPanel = new JPanel(new BorderLayout(12, 0));
        searchPanel.setOpaque(false);
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIFactory.LINE),
                BorderFactory.createEmptyBorder(11, 14, 11, 14)));
        searchField.setToolTipText("Search by movie, actor, genre, or language");
        resultLabel = new JLabel();
        resultLabel.setForeground(new Color(215, 219, 226));
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(resultLabel, BorderLayout.EAST);
        header.add(searchPanel, BorderLayout.SOUTH);

        movieGrid = new JPanel(new GridLayout(0, 5, 18, 18));
        movieGrid.setOpaque(false);
        renderMovies(MovieRepository.allMovies());
        searchTimer = new Timer(120, e -> renderMovies(MovieRepository.searchMovies(searchField.getText())));
        searchTimer.setRepeats(false);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            public void removeUpdate(DocumentEvent e) {
                search();
            }

            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {
                searchTimer.restart();
            }
        });

        JScrollPane scroll = new JScrollPane(movieGrid);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        root.add(header, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        return root;
    }

    private void renderMovies(List<Movie> movies) {
        movieGrid.removeAll();
        for (Movie movie : movies) {
            movieGrid.add(movieCard(movie));
        }
        if (movies.isEmpty()) {
            JLabel empty = new JLabel("<html><div style='text-align:center'>No movie found in the current catalogue.<br>Try another title, actor, language, or genre.</div></html>", SwingConstants.CENTER);
            empty.setForeground(Color.WHITE);
            empty.setFont(new Font("Segoe UI", Font.BOLD, 18));
            movieGrid.add(empty);
        }
        resultLabel.setText(movies.size() + " movie(s)");
        movieGrid.revalidate();
        movieGrid.repaint();
    }

    private JPanel movieCard(Movie movie) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(UIFactory.PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(43, 50, 62)),
                BorderFactory.createEmptyBorder(10, 10, 12, 10)));
        card.setPreferredSize(new Dimension(205, 365));
        card.add(UIFactory.imageLabel(movie.getPosterPath(), 180, 240), BorderLayout.NORTH);

        JLabel title = new JLabel(movie.getTitle());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel detail = new JLabel(movie.getLanguage() + " | " + movie.getFormat());
        detail.setForeground(new Color(185, 191, 202));
        detail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JButton view = UIFactory.primaryButton("View Details");
        view.addActionListener(e -> {
            dispose();
            new MovieInfo(user, movie);
        });
        JPanel info = new JPanel(new GridLayout(3, 1, 0, 6));
        info.setOpaque(false);
        info.add(title);
        info.add(detail);
        info.add(view);
        card.add(info, BorderLayout.CENTER);
        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashBoard(new User("Demo User", "demo@example.com", "9999999999", "demo", "")));
    }
}
