package Cinema;				
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class PaymentPageUI extends JFrame {

    private static final Statement DBConnection = null;
	private JComboBox<String> paymentMethodDropdown;
    private JTextField cardNumberField, upiField;
    private JButton payNowButton;
    private String movie, theater;
    private int totalAmount;
    private HashSet<String> selectedSeats;
    JPanel panel;
    JLabel backgroundLabel;
    private int showtimeId;

    public PaymentPageUI(String movie, String theater, int totalAmount, HashSet<String> selectedSeats) {
        this.movie = movie;
        this.theater = theater;
        this.totalAmount = totalAmount;
        this.selectedSeats = selectedSeats;

        setTitle("Payment - " + movie);
        setLayout(null);
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
     // Background image
        ImageIcon bgIcon = new ImageIcon("Image/14.jpg"); // <-- Use your desired image here
        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(bgImg));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        backgroundLabel.setLayout(null); // So we can add components directly on top
        
     // Main panel
        panel = new JPanel();
        panel.setLayout(null); 
        panel.setBounds(0, 0, 1000, 800);
        panel.add(backgroundLabel);
        

        // Back Button
        JButton backButton = new JButton("← Back");
        backButton.setBounds(10, 30, 80, 29);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);// visible
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            dispose();
            new SeatLayoutManager(movie, theater, selectedSeats.size());
        });
        add(backButton);

        JLabel movieLabel = new JLabel("<html><h1>" + movie + "</h1></html>");
        movieLabel.setForeground(Color.white);
        movieLabel.setBounds(50, 70, 400, 50);
        add(movieLabel);

        JLabel theaterLabel = new JLabel("<html><h2>" + theater + "</h2></html>");
        theaterLabel.setForeground(Color.white);
        theaterLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        theaterLabel.setBounds(50, 130, 400, 40);
        add(theaterLabel);

        JLabel seatsLabel = new JLabel("<html><h3>Selected Seats : " + String.join(", ", selectedSeats) + "</h3></html>");
        seatsLabel.setForeground(Color.white);
        seatsLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        seatsLabel.setBounds(50, 180, 700, 40);
        add(seatsLabel);

        JLabel amountLabel = new JLabel("<html><h2>Total Price : ₹" + totalAmount + "</h2></html>");
        amountLabel.setForeground(Color.white);
        amountLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        amountLabel.setBounds(50, 230, 300, 40);
        add(amountLabel);

        JLabel methodLabel = new JLabel("Select Payment Method :");
        methodLabel.setForeground(Color.white);
        methodLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        methodLabel.setBounds(50, 300, 250, 30);
        add(methodLabel);

        paymentMethodDropdown = new JComboBox<>(new String[]{"Credit/Debit Card", "UPI"});
        paymentMethodDropdown.setBounds(300, 300, 200, 30);
        paymentMethodDropdown.addActionListener(e -> togglePaymentFields());
        add(paymentMethodDropdown);

        JLabel cardLabel = new JLabel("Card Number :");
        cardLabel.setForeground(Color.white);
        cardLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        cardLabel.setBounds(50, 350, 150, 30);
        add(cardLabel);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(300, 350, 250, 30);
        add(cardNumberField);

        JLabel upiLabel = new JLabel("UPI ID :");
        upiLabel.setForeground(Color.white);
        upiLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        upiLabel.setBounds(50, 400, 150, 30);
        add(upiLabel);

        upiField = new JTextField();
        upiField.setBounds(300, 400, 250, 30);
        add(upiField);

        payNowButton = new JButton("<html><h2>Pay ₹" + totalAmount + "</h2></html>");
        payNowButton.setBounds(380, 600, 220, 50);
        payNowButton.setBackground(Color.GREEN);
        payNowButton.setForeground(Color.WHITE);
        payNowButton.setFocusable(false);
        payNowButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        payNowButton.addActionListener(e -> processPayments());
        add(payNowButton);
        
        togglePaymentFields();
        setVisible(true);
        this.add(panel);
    }
    //
//    private void processPayment() {
//        String method = (String) paymentMethodDropdown.getSelectedItem();
//
//        if ("Credit/Debit Card".equals(method)) {
//            String card = cardNumberField.getText().trim();
//            if (card.length() < 12 || !card.matches("\\d+")) {
//                JOptionPane.showMessageDialog(this, "Enter a valid card number.");
//                return;
//            }
//        } else {
//            String upi = upiField.getText().trim();
//            if (!upi.contains("@")) {
//                JOptionPane.showMessageDialog(this, "Enter a valid UPI ID.");
//                return;
//            }
//        }

        // ⚠️ Assume showtime_id is available — you must pass it from SeatLayoutManager
//        int showtimeId = 1; // Replace with actual ID passed to constructor
//
//        try (Connection conn = DBConnection.getConnection()) {
//            String insertSQL = "INSERT INTO bookingP  (showtime_id, seat_number, payment_method) VALUES (?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(insertSQL);
//
//            for (String seat : selectedSeats) {
//                ps.setInt(1, showtimeId);
//                ps.setString(2, seat);
//                ps.setString(3, method);
//                ps.addBatch();
//            }
//
//            ps.executeBatch();
//            JOptionPane.showMessageDialog(this, "Payment Successful!", "Payment", JOptionPane.INFORMATION_MESSAGE);
//            dispose();
//            new TicketGeneratorUI(movie, theater, totalAmount, selectedSeats);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Payment failed due to database error.");
//        }
//    }
    //

    private void togglePaymentFields() {
        String method = (String) paymentMethodDropdown.getSelectedItem();
        cardNumberField.setEnabled("Credit/Debit Card".equals(method));
        upiField.setEnabled("UPI".equals(method));
    }

    private void processPayments() {
        String method = (String) paymentMethodDropdown.getSelectedItem();

        if ("Credit/Debit Card".equals(method)) {
            String card = cardNumberField.getText().trim();
            if (card.length() < 12 || !card.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Enter a valid card number.");
                return;
            }
        } else {
            String upi = upiField.getText().trim();
            if (!upi.contains("@")) {
                JOptionPane.showMessageDialog(this, "Enter a valid UPI ID.");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Payment Successful!", "Payment", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new TicketGeneratorUI(movie, theater, totalAmount, selectedSeats);
    }

    public static void main(String[] args) {
        // Example usage for testing
        HashSet<String> seats = new HashSet<>();
        seats.add("A1");
        seats.add("A2");
        new PaymentPageUI("Raid-2", "Cinepolis : DB Mall, Bhopal", 400, seats);
    }
}
