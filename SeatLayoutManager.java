//package Cinema;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.HashSet;
//
//public class SeatLayoutManager extends JFrame implements ActionListener {
//
//    private static final Statement DBConnection = null;
//	private String movie;
//    private String theater;
//    private int maxSeatsToSelect;
//    private int selectedSeatCount;
//    private JButton[][] seats;
//    private HashSet<String> selectedSeats;
//    private JLabel priceLabel;
//    private final int seatPrice = 200;
////	private Container bookedSeats;
//    private HashSet<String> bookedSeats;
//	private int showtimeId;
////	private Set<String> bookedSeats = new HashSet<>();
//
//
//    public SeatLayoutManager(String movie, String theater, int maxSeatsToSelect) {
//        this.movie = movie;
//        this.theater = theater;
//        this.maxSeatsToSelect = maxSeatsToSelect;
//        this.selectedSeats = new HashSet<>();
//        this.selectedSeatCount = 0;
//
//        setTitle("Seat Layout - " + movie + " (" + theater + ")");
//        setSize(1000, 800);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setResizable(false);
//
//        // Background setup
//        ImageIcon bgIcon = new ImageIcon("Image/3.jpg");
//        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
//        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImg));
//        backgroundLabel.setBounds(0, 0, 1000, 800);
//        backgroundLabel.setLayout(null);
//        setContentPane(backgroundLabel);
//
//        // Top Panel
//        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        topPanel.setOpaque(false);
//        JButton backButton = new JButton("\u2190 Back");
//        backButton.setFocusable(false);
//        backButton.setBackground(Color.LIGHT_GRAY);
//        backButton.addActionListener(e -> {
//            dispose();
//            new MovieShowtimesUI();
//        });
//        topPanel.add(backButton);
//        topPanel.setBounds(10, 10, 200, 40);
//        backgroundLabel.add(topPanel);
//
//        // Seat Grid
//        JPanel seatPanel = new JPanel(new GridLayout(6, 7, 10, 10));
//        seatPanel.setOpaque(false);
//        seatPanel.setBounds(200, 100, 600, 300);
//        seats = new JButton[6][6];
//        String[] seatLabels = {
//            "A1", "A2", "A3", "A4", "A5", "A6",
//            "B1", "B2", "B3", "B4", "B5", "B6",
//            "C1", "C2", "C3", "C4", "C5", "C6",
//            "D1", "D2", "D3", "D4", "D5", "D6",
//            "E1", "E2", "E3", "E4", "E5", "E6",
//            "F1", "F2", "F3", "F4", "F5", "F6"
//        };
//
//        for (int i = 0; i < 36; i++) {
//            JButton btn = new JButton(seatLabels[i]);
//            btn.setBackground(Color.GREEN);
//            btn.setFocusable(false);
//            btn.addActionListener(this);
//            int row = i / 6;
//            int col = i % 6;
//            seats[row][col] = btn;
//            seatPanel.add(btn);
//        }
//
//        backgroundLabel.add(seatPanel);
//
//        // Bottom Panel
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        bottomPanel.setOpaque(false);
//        bottomPanel.setBounds(200, 480, 600, 100);
//
//        JButton proceedButton = new JButton("Proceed to Pay");
//        proceedButton.setBackground(Color.CYAN);
//        proceedButton.setFocusable(false);
//        proceedButton.addActionListener(e -> proceedToPay());
//        bottomPanel.add(proceedButton);
//
//        JLabel legend = new JLabel("Legend: Green = Available, Red = Selected");
//        legend.setForeground(Color.white);
//        bottomPanel.add(legend);
//
//        priceLabel = new JLabel("Total Price: ₹0");
//        priceLabel.setForeground(Color.white);
//        bottomPanel.add(priceLabel);
//
//        backgroundLabel.add(bottomPanel);
//
//        setVisible(true);
//    }
//
////    private void loadBookedSeats() {
////        try (Connection conn = DBConnection.getConnection()) {
////            PreparedStatement ps = conn.prepareStatement("SELECT seat_number FROM bookings WHERE showtime_id = ?");
////            int showtimeId;
////			ps.setInt(1, showtimeId);
////            ResultSet rs = ps.executeQuery();
////            while (rs.next()) {
////                bookedSeats.add(rs.getString("seat_number"));
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////            JOptionPane.showMessageDialog(this, "Error loading booked seats.");
////        }
////    }
//    private void loadBookedSeats() {
//        try (Connection conn = DBConnection.getConnection()) {
//            PreparedStatement ps = conn.prepareStatement("SELECT seat_number FROM bookings WHERE showtime_id = ?");
//            ps.setInt(1, showtimeId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                bookedSeats.add(rs.getString("seat_number"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error loading booked seats.");
//        }
//    }
//
//    private void markBookedSeats() {
//        for (int row = 0; row < seats.length; row++) {
//            for (int col = 0; col < seats[row].length; col++) {
//                JButton btn = seats[row][col];
//                if (bookedSeats.contains(btn.getText())) {
//                    btn.setBackground(Color.GRAY);
//                    btn.setEnabled(false);
//                }
//            }
//        }
//    }
//    
//    private void toggleSeatSelection(ActionEvent e) {
//        JButton seat = (JButton) e.getSource();
//        String seatId = seat.getText();
//
//        if (selectedSeats.contains(seatId)) {
//            seat.setBackground(Color.GREEN);
//            selectedSeats.remove(seatId);
//            selectedSeatCount--;
//        } else if (selectedSeatCount < maxSeatsToSelect) {
//            seat.setBackground(Color.RED);
//            selectedSeats.add(seatId);
//            selectedSeatCount++;
//        } else {
//            JOptionPane.showMessageDialog(this, "You can only select " + maxSeatsToSelect + " seat(s).", "Limit Reached", JOptionPane.WARNING_MESSAGE);
//        }
//
//        priceLabel.setText("Total Price: ₹" + (selectedSeatCount * seatPrice));
//    }
//
//    private void proceedToPay() {
//        if (selectedSeats.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please select at least one seat.", "No Seats Selected", JOptionPane.WARNING_MESSAGE);
//        } else {
//            int totalAmount = selectedSeatCount * seatPrice;
//            dispose();
//            new PaymentPageUI(movie, theater, totalAmount, selectedSeats);
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        toggleSeatSelection(e);
//    }
//
//    public static void main(String[] args) {
//        new SeatLayoutManager("Raid-2", "Cinepolis : DB Mall, Bhopal", 3);
//    }
//}

//

package Cinema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class SeatLayoutManager extends JFrame implements ActionListener {

    private static final Statement DBConnection = null;
	private String movie;
    private String theater;
    private int maxSeatsToSelect;
    private int selectedSeatCount;
    private JButton[][] seats;
    private HashSet<String> selectedSeats;
    private JLabel priceLabel;
    private final int seatPrice = 200;
//	private Container bookedSeats;
    private HashSet<String> bookedSeats;
	private int showtimeId;
//	private Set<String> bookedSeats = new HashSet<>();


    public SeatLayoutManager(String movie, String theater, int maxSeatsToSelect) {
        this.movie = movie;
        this.theater = theater;
        this.maxSeatsToSelect = maxSeatsToSelect;
        this.selectedSeats = new HashSet<>();
        this.selectedSeatCount = 0;

        setTitle("Seat Layout - " + movie + " (" + theater + ")");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Background setup
        ImageIcon bgIcon = new ImageIcon("Image/3.jpg");
        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImg));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        backgroundLabel.setLayout(null);
        setContentPane(backgroundLabel);

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton backButton = new JButton("← Back");
        backButton.setBounds(10, 30, 80, 29);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);// visible
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            dispose();
            new MovieShowtimesUI(theater);
        });
        topPanel.add(backButton);
        topPanel.setBounds(10, 10, 200, 40);
        backgroundLabel.add(topPanel);

        // Seat Grid
        JPanel seatPanel = new JPanel(new GridLayout(6, 7, 10, 10));
        seatPanel.setOpaque(false);
        seatPanel.setBounds(200, 100, 600, 300);
        seats = new JButton[6][6];
        String[] seatLabels = {
            "A1", "A2", "A3", "A4", "A5", "A6",
            "B1", "B2", "B3", "B4", "B5", "B6",
            "C1", "C2", "C3", "C4", "C5", "C6",
            "D1", "D2", "D3", "D4", "D5", "D6",
            "E1", "E2", "E3", "E4", "E5", "E6",
            "F1", "F2", "F3", "F4", "F5", "F6"
        };

        for (int i = 0; i < 36; i++) {
            JButton btn = new JButton(seatLabels[i]);
            btn.setBackground(Color.GREEN);
            btn.setFocusable(false);
            btn.addActionListener(this);
            int row = i / 6;
            int col = i % 6;
            seats[row][col] = btn;
            seatPanel.add(btn);
        }

        backgroundLabel.add(seatPanel);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBounds(200, 480, 600, 100);

        JButton proceedButton = new JButton("Proceed to Pay");
        proceedButton.setBackground(Color.CYAN);
        proceedButton.setFocusable(false);
        proceedButton.addActionListener(e -> proceedToPay());
        bottomPanel.add(proceedButton);

        JLabel legend = new JLabel("Legend: Green = Available, Red = Selected");
        legend.setForeground(Color.white);
        bottomPanel.add(legend);

        priceLabel = new JLabel("Total Price: ₹0");
        priceLabel.setForeground(Color.white);
        bottomPanel.add(priceLabel);

        backgroundLabel.add(bottomPanel);

        setVisible(true);
    }

//    private void loadBookedSeats() {
//        try (Connection conn = DBConnection.getConnection()) {
//            PreparedStatement ps = conn.prepareStatement("SELECT seat_number FROM bookings WHERE showtime_id = ?");
//            int showtimeId;
//			ps.setInt(1, showtimeId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                bookedSeats.add(rs.getString("seat_number"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error loading booked seats.");
//        }
//    }
    private void loadBookedSeats() {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT seat_number FROM bookings WHERE showtime_id = ?");
            ps.setInt(1, showtimeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookedSeats.add(rs.getString("seat_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading booked seats.");
        }
    }

    private void markBookedSeats() {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                JButton btn = seats[row][col];
                if (bookedSeats.contains(btn.getText())) {
                    btn.setBackground(Color.GRAY);
                    btn.setEnabled(false);
                }
            }
        }
    }
    
    private void toggleSeatSelection(ActionEvent e) {
        JButton seat = (JButton) e.getSource();
        String seatId = seat.getText();

        if (selectedSeats.contains(seatId)) {
            seat.setBackground(Color.GREEN);
            selectedSeats.remove(seatId);
            selectedSeatCount--;
        } else if (selectedSeatCount < maxSeatsToSelect) {
            seat.setBackground(Color.RED);
            selectedSeats.add(seatId);
            selectedSeatCount++;
        } else {
            JOptionPane.showMessageDialog(this, "You can only select " + maxSeatsToSelect + " seat(s).", "Limit Reached", JOptionPane.WARNING_MESSAGE);
        }

        priceLabel.setText("Total Price: ₹" + (selectedSeatCount * seatPrice));
    }

    private void proceedToPay() {
        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one seat.", "No Seats Selected", JOptionPane.WARNING_MESSAGE);
        } else {
            int totalAmount = selectedSeatCount * seatPrice;
            dispose();
            new PaymentPageUI(movie, theater, totalAmount, selectedSeats);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        toggleSeatSelection(e);
    }

    public static void main(String[] args) {
       new SeatLayoutManager("Raid-2", "Cinepolis : DB Mall, Bhopal", 3);
    
        
    }
}
