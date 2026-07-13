import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SeatLayoutManager extends JFrame {
    private final User user;
    private final Movie movie;
    private final Showtime showtime;
    private final int maxSeatsToSelect;
    private final BookingService bookingService = new BookingService();
    private final Set<String> selectedSeats = new LinkedHashSet<>();
    private final Set<String> bookedSeats;
    private JLabel priceLabel;

    public SeatLayoutManager(User user, Movie movie, Showtime showtime, int maxSeatsToSelect) {
        this.user = user;
        this.movie = movie;
        this.showtime = showtime;
        this.maxSeatsToSelect = maxSeatsToSelect;
        this.bookedSeats = bookingService.bookedSeats(showtime);
        setTitle("Seat Layout - " + movie.getTitle());
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
            new MovieShowtimesUI(user, movie);
        });
        JLabel title = UIFactory.heading(movie.getTitle() + " | " + showtime.getDate() + " | " + showtime.getTime(), 26);
        header.add(back, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        JPanel screen = new JPanel(new BorderLayout());
        screen.setBackground(UIFactory.PANEL);
        screen.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        JLabel screenLabel = new JLabel("SCREEN", JLabel.CENTER);
        screenLabel.setForeground(UIFactory.INK);
        screenLabel.setOpaque(true);
        screenLabel.setBackground(new Color(232, 235, 240));
        screenLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        screenLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        screen.add(screenLabel, BorderLayout.NORTH);

        JPanel seatGrid = new JPanel(new GridLayout(6, 8, 10, 10));
        seatGrid.setOpaque(false);
        seatGrid.setBorder(BorderFactory.createEmptyBorder(34, 130, 34, 130));
        for (char row = 'A'; row <= 'F'; row++) {
            for (int col = 1; col <= 8; col++) {
                String seatId = row + String.valueOf(col);
                JButton seat = new JButton(seatId);
                seat.setFocusPainted(false);
                seat.setFont(new Font("Segoe UI", Font.BOLD, 13));
                if (bookedSeats.contains(seatId)) {
                    seat.setEnabled(false);
                    seat.setBackground(new Color(91, 98, 112));
                    seat.setForeground(Color.WHITE);
                } else {
                    seat.setBackground(new Color(34, 154, 91));
                    seat.setForeground(Color.WHITE);
                    seat.addActionListener(e -> toggleSeat(seat));
                }
                seatGrid.add(seat);
            }
        }
        screen.add(seatGrid, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JLabel legend = new JLabel("Green = Available   Red = Selected   Grey = Booked");
        legend.setForeground(Color.WHITE);
        legend.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceLabel = new JLabel("Total: Rs. 0");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JButton pay = UIFactory.primaryButton("Proceed to Pay");
        pay.addActionListener(e -> proceed());
        footer.add(legend, BorderLayout.WEST);
        footer.add(priceLabel, BorderLayout.CENTER);
        footer.add(pay, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);
        root.add(screen, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        return root;
    }

    private void toggleSeat(JButton seat) {
        String seatId = seat.getText();
        if (selectedSeats.contains(seatId)) {
            selectedSeats.remove(seatId);
            seat.setBackground(new Color(34, 154, 91));
        } else {
            if (selectedSeats.size() >= maxSeatsToSelect) {
                JOptionPane.showMessageDialog(this, "You selected " + maxSeatsToSelect + " seat(s) on the previous page.");
                return;
            }
            selectedSeats.add(seatId);
            seat.setBackground(UIFactory.RED);
        }
        priceLabel.setText("Total: Rs. " + selectedSeats.size() * AppConfig.SEAT_PRICE);
    }

    private void proceed() {
        if (selectedSeats.size() != maxSeatsToSelect) {
            JOptionPane.showMessageDialog(this, "Please select exactly " + maxSeatsToSelect + " seat(s).");
            return;
        }
        dispose();
        new PaymentPageUI(user, movie, showtime, new ArrayList<>(selectedSeats));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeatLayoutManager(new User("Demo", "demo@example.com", "9999999999", "demo", ""),
                MovieRepository.allMovies().get(0), MovieRepository.showtimesFor("raid2").get(0), 2));
    }
}
