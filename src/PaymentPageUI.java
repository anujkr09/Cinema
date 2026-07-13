import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PaymentPageUI extends JFrame {
    private final User user;
    private final Movie movie;
    private final Showtime showtime;
    private final List<String> selectedSeats;
    private final BookingService bookingService = new BookingService();
    private JComboBox<String> paymentMethodDropdown;
    private JTextField cardNumberField;
    private JTextField upiField;

    public PaymentPageUI(User user, Movie movie, Showtime showtime, List<String> selectedSeats) {
        this.user = user;
        this.movie = movie;
        this.showtime = showtime;
        this.selectedSeats = selectedSeats;
        setTitle("Payment - " + movie.getTitle());
        setSize(960, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(buildContent());
        setVisible(true);
    }

    private JPanel buildContent() {
        JPanel root = UIFactory.darkPanel();
        root.setBorder(BorderFactory.createEmptyBorder(26, 36, 26, 36));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JButton back = UIFactory.secondaryButton("Back");
        back.addActionListener(e -> {
            dispose();
            new SeatLayoutManager(user, movie, showtime, selectedSeats.size());
        });
        header.add(back, BorderLayout.WEST);
        header.add(UIFactory.heading("Secure Payment", 30), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UIFactory.PANEL);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(43, 50, 62)),
                BorderFactory.createEmptyBorder(26, 34, 26, 34)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(8, 0, 8, 0);

        addSummary(form, gbc, 0, "Movie", movie.getTitle());
        addSummary(form, gbc, 1, "Theater", showtime.getTheater());
        addSummary(form, gbc, 2, "Date & Time", showtime.getDate() + " at " + showtime.getTime());
        addSummary(form, gbc, 3, "Seats", String.join(", ", selectedSeats));
        addSummary(form, gbc, 4, "Amount", "Rs. " + selectedSeats.size() * AppConfig.SEAT_PRICE);

        paymentMethodDropdown = new JComboBox<>(new String[]{"Credit/Debit Card", "UPI"});
        paymentMethodDropdown.addActionListener(e -> togglePaymentFields());
        cardNumberField = new JTextField();
        upiField = new JTextField();
        addInput(form, gbc, 5, "Payment Method", paymentMethodDropdown);
        addInput(form, gbc, 7, "Card Number", cardNumberField);
        addInput(form, gbc, 9, "UPI ID", upiField);

        JButton pay = UIFactory.primaryButton("Pay Rs. " + selectedSeats.size() * AppConfig.SEAT_PRICE);
        pay.addActionListener(e -> processPayment());
        gbc.gridy = 11;
        gbc.insets = new Insets(22, 0, 8, 0);
        form.add(pay, gbc);

        togglePaymentFields();
        root.add(header, BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        return root;
    }

    private void addSummary(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        JLabel text = new JLabel("<html><b>" + label + ":</b> " + value + "</html>");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = row;
        panel.add(text, gbc);
    }

    private void addInput(JPanel panel, GridBagConstraints gbc, int row, String label, java.awt.Component input) {
        JLabel l = new JLabel(label);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = row;
        panel.add(l, gbc);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridy = row + 1;
        panel.add(input, gbc);
    }

    private void togglePaymentFields() {
        String method = (String) paymentMethodDropdown.getSelectedItem();
        cardNumberField.setEnabled("Credit/Debit Card".equals(method));
        upiField.setEnabled("UPI".equals(method));
    }

    private void processPayment() {
        String method = (String) paymentMethodDropdown.getSelectedItem();
        if ("Credit/Debit Card".equals(method)) {
            String card = cardNumberField.getText().trim();
            if (!card.matches("\\d{12,19}")) {
                JOptionPane.showMessageDialog(this, "Enter a valid card number.");
                return;
            }
        } else {
            String upi = upiField.getText().trim();
            if (!upi.matches("^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+$")) {
                JOptionPane.showMessageDialog(this, "Enter a valid UPI ID.");
                return;
            }
        }
        try {
            Booking booking = bookingService.createBooking(user, movie, showtime, selectedSeats, method);
            JOptionPane.showMessageDialog(this, "Payment successful. Booking ID: " + booking.getId());
            dispose();
            new TicketGeneratorUI(user, booking);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            dispose();
            new SeatLayoutManager(user, movie, showtime, selectedSeats.size());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentPageUI(new User("Demo", "demo@example.com", "9999999999", "demo", ""),
                MovieRepository.allMovies().get(0), MovieRepository.showtimesFor("raid2").get(0), List.of("A1", "A2")));
    }
}
