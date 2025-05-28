package Cinema;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class TicketGeneratorUI extends JFrame {

    private static final Statement DBConnection = null;
	private JLabel movieLabel, theaterLabel, seatsLabel, amountLabel, dateLabel;
    private JButton saveButton, printButton;
    private JTextPane ticketPane; // ✅ Use JTextPane instead of JTextArea
    private String movie, theater;
    private int totalAmount;
    private HashSet<String> selectedSeats;

    public TicketGeneratorUI(String movie, String theater, int totalAmount, HashSet<String> selectedSeats) {
        this.movie = movie;
        this.theater = theater;
        this.totalAmount = totalAmount;
        this.selectedSeats = selectedSeats;

        setTitle("Ticket Summary - " + movie);
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background image
        ImageIcon bgIcon = new ImageIcon("Image/15.jpg"); // Replace with your image path
        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImg));
        setContentPane(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Back Button
        JButton backButton = new JButton("← Back");
        backButton.setBounds(10, 30, 80, 29);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);// visible
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            dispose();
            new PaymentPageUI(movie, theater, totalAmount, selectedSeats);
        });
        backgroundLabel.add(backButton);

        // Movie Info
        movieLabel = new JLabel("<html><h1> " + movie + "</h1></html>");
        movieLabel.setBounds(50, 70, 500, 50);
        movieLabel.setForeground(Color.WHITE);
        backgroundLabel.add(movieLabel);

        theaterLabel = new JLabel("<html><h2>" + theater + "</h2></html>");
        theaterLabel.setBounds(50, 130, 600, 40);
        theaterLabel.setForeground(Color.WHITE);
        backgroundLabel.add(theaterLabel);

        seatsLabel = new JLabel("<html><h2>Selected Seats: " + String.join(", ", selectedSeats) + "</h2></html>");
        seatsLabel.setBounds(50, 180, 700, 40);
        seatsLabel.setForeground(Color.WHITE);
        backgroundLabel.add(seatsLabel);

        amountLabel = new JLabel("<html><h2> Total Price: ₹" + totalAmount + "</h2></html>");
        amountLabel.setBounds(50, 230, 400, 40);
        amountLabel.setForeground(Color.WHITE);
        backgroundLabel.add(amountLabel);

        dateLabel = new JLabel("<html><h2>Date: " + java.time.LocalDate.now() + "</h2></html>");
        dateLabel.setBounds(50, 280, 400, 40);
        dateLabel.setForeground(Color.WHITE);
        backgroundLabel.add(dateLabel);

        // Ticket Panel
        JPanel ticketPanel = new JPanel();
        ticketPanel.setBounds(50, 330, 880, 220);
        ticketPanel.setBackground(new Color(0, 0, 0, 180)); // Semi-transparent
        ticketPanel.setLayout(new BorderLayout());
        ticketPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        backgroundLabel.add(ticketPanel);

        // ✅ Ticket Text Pane (center-aligned)
        ticketPane = new JTextPane();
        ticketPane.setEditable(false);
        ticketPane.setOpaque(false);
        ticketPane.setForeground(Color.GREEN);
        ticketPane.setFont(new Font("Courier New", Font.BOLD, 18));
        ticketPane.setText(buildFancyTicketText());

        StyledDocument doc = ticketPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        ticketPanel.add(ticketPane, BorderLayout.CENTER);

        // Save Button
        saveButton = new JButton("Save Ticket");
        saveButton.setBounds(100, 600, 150, 40);
        saveButton.setBackground(Color.white);
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> saveTicket());
        backgroundLabel.add(saveButton);

        // Print Button
        printButton = new JButton("Print Ticket");
        printButton.setBounds(280, 600, 150, 40);
        printButton.setBackground(Color.white);
        printButton.setFocusable(false);
        printButton.addActionListener(e -> printTicket());
        backgroundLabel.add(printButton);

        setVisible(true);
        //
        saveTicketToDatabase(); // store the generated ticket in the DB
    }
//
    private void saveTicketToDatabase() {
        String seatsCombined = String.join(", ", selectedSeats);

        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "INSERT INTO tickets (movie_name, theater_name, seats, amount) VALUES (?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, movie);
//            ps.setString(2, theater);
//            ps.setString(3, seatsCombined);
//            ps.setInt(4, totalAmount);
//            ps.executeUpdate();
            //
            String sql = "INSERT INTO tickets (movie_name, theater_name, seats, amount, date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, movie);
            ps.setString(2, theater);
            ps.setString(3, seatsCombined);
            ps.setInt(4, totalAmount);
            ps.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to store ticket details in database.");
        }
    }
    ///
    private String buildFancyTicketText() {
        return " ----------- MOVIE TICKET ----------- \n"
             + " Movie    : " + movie + "\n"
             + " Theater  : " + theater + "\n"
             + " Seats    : " + String.join(", ", selectedSeats) + "\n"
             + " Amount   : ₹" + totalAmount + "\n"
             + " Date     : " + java.time.LocalDate.now() + "\n"
             + "-----------------------------------------\n"
             + " Enjoy your movie experience!\n"
             + " Don’t forget your popcorn!\n";
    }

    private void saveTicket() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Ticket as Text File");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile() + ".txt")) {
                writer.write(ticketPane.getText()); // ✅ fixed reference
                JOptionPane.showMessageDialog(this, "Ticket saved successfully!");
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving ticket: " + e.getMessage());
            }
        }
    }

    private void printTicket() {
        try {
            boolean printed = ticketPane.print(); // ✅ fixed reference
            if (printed) {
                JOptionPane.showMessageDialog(this, "Ticket printed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Ticket printing canceled.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error printing: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        HashSet<String> seats = new HashSet<>();
        seats.add("B1");
        seats.add("B2");
        new TicketGeneratorUI("Raid-2", "Cinepolis : DB Mall, Bhopal", 400, seats);
    }
}
