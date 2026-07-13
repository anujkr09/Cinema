import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TicketGeneratorUI extends JFrame {
    private final User user;
    private final Booking booking;
    private JTextPane ticketPane;

    public TicketGeneratorUI(User user, Booking booking) {
        this.user = user;
        this.booking = booking;
        setTitle("Ticket - " + booking.getId());
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
        JLabel title = UIFactory.heading("Ticket Confirmed", 32);
        JButton home = UIFactory.secondaryButton("Home");
        home.addActionListener(e -> {
            dispose();
            new DashBoard(user);
        });
        header.add(title, BorderLayout.WEST);
        header.add(home, BorderLayout.EAST);

        JPanel ticketPanel = new JPanel(new BorderLayout());
        ticketPanel.setBackground(UIFactory.PANEL);
        ticketPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIFactory.GOLD, 1),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));
        ticketPane = new JTextPane();
        ticketPane.setEditable(false);
        ticketPane.setOpaque(false);
        ticketPane.setForeground(Color.WHITE);
        ticketPane.setFont(new Font("Consolas", Font.BOLD, 17));
        ticketPane.setText(buildTicketText());
        StyledDocument doc = ticketPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        ticketPanel.add(ticketPane, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.setOpaque(false);
        JButton save = UIFactory.secondaryButton("Save Ticket");
        save.addActionListener(e -> saveTicket());
        JButton print = UIFactory.primaryButton("Print Ticket");
        print.addActionListener(e -> printTicket());
        actions.add(save);
        actions.add(print);

        root.add(header, BorderLayout.NORTH);
        root.add(ticketPanel, BorderLayout.CENTER);
        root.add(actions, BorderLayout.SOUTH);
        return root;
    }

    private String buildTicketText() {
        return "----------- MOVIE TICKET -----------\n"
                + "Booking ID : " + booking.getId() + "\n"
                + "Customer   : " + booking.getUsername() + "\n"
                + "Movie      : " + booking.getMovieTitle() + "\n"
                + "Theater    : " + booking.getTheater() + "\n"
                + "Date/Time  : " + booking.getDate() + " at " + booking.getTime() + "\n"
                + "Seats      : " + String.join(", ", booking.getSeats()) + "\n"
                + "Amount     : Rs. " + booking.getTotalAmount() + "\n"
                + "Paid Via   : " + booking.getPaymentMethod() + "\n"
                + "------------------------------------\n"
                + "Enjoy your movie experience!";
    }

    private void saveTicket() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Ticket");
        fileChooser.setSelectedFile(new java.io.File(booking.getId() + ".txt"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) {
                writer.write(ticketPane.getText());
                JOptionPane.showMessageDialog(this, "Ticket saved successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving ticket: " + ex.getMessage());
            }
        }
    }

    private void printTicket() {
        try {
            if (ticketPane.print()) {
                JOptionPane.showMessageDialog(this, "Ticket sent to printer.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error printing ticket: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Booking booking = new Booking("BKDEMO001", "demo", "raid2", "Raid 2", "Cinepolis",
                "13 Jul", "08:30 PM", java.util.List.of("A1", "A2"), 400, "UPI", java.time.LocalDateTime.now());
        SwingUtilities.invokeLater(() -> new TicketGeneratorUI(new User("Demo", "demo@example.com", "9999999999", "demo", ""), booking));
    }
}
