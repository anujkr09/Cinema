import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MovieInfo extends JFrame {
    private final User user;
    private final Movie movie;

    public MovieInfo(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
        setTitle("Movie Details - " + movie.getTitle());
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
            new DashBoard(user);
        });
        JLabel account = new JLabel(user.getUsername());
        account.setForeground(Color.WHITE);
        account.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.add(back, BorderLayout.WEST);
        header.add(account, BorderLayout.EAST);

        JPanel body = new JPanel(new BorderLayout(34, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));
        body.add(UIFactory.imageLabel(movie.getPosterPath(), 330, 480), BorderLayout.WEST);

        JPanel details = new JPanel(new GridLayout(0, 1, 0, 14));
        details.setOpaque(false);
        JLabel title = UIFactory.heading(movie.getTitle(), 38);
        JLabel tags = pill(movie.getFormat() + "  |  " + movie.getLanguage());
        JLabel runtime = pill(movie.getDetails());
        JLabel about = new JLabel("<html><div style='width:520px'>" + movie.getDescription() + "</div></html>");
        about.setForeground(new Color(226, 230, 237));
        about.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel castTitle = UIFactory.heading("Cast", 22);
        JLabel cast = new JLabel(String.join("  |  ", movie.getCast()));
        cast.setForeground(new Color(222, 226, 235));
        cast.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JButton book = UIFactory.primaryButton("Book Tickets");
        book.addActionListener(e -> {
            dispose();
            new MovieShowtimesUI(user, movie);
        });
        details.add(title);
        details.add(tags);
        details.add(runtime);
        details.add(about);
        details.add(castTitle);
        details.add(cast);
        details.add(book);
        body.add(details, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        return root;
    }

    private JLabel pill(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setForeground(UIFactory.INK);
        label.setBackground(new Color(239, 194, 103));
        label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieInfo(new User("Demo", "demo@example.com", "9999999999", "demo", ""),
                MovieRepository.allMovies().get(0)));
    }
}
