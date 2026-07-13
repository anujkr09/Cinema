import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RegistrationPage extends JFrame {
    private final UserService userService = new UserService();
    private JTextField nameField;
    private JTextField emailField;
    private JTextField mobileField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegistrationPage() {
        setTitle("Movie Ticket Booking - Register");
        setSize(900, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(buildContent());
        setVisible(true);
    }

    private JPanel buildContent() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIFactory.SURFACE);

        JPanel hero = new JPanel(new GridBagLayout());
        hero.setBackground(UIFactory.PANEL);
        hero.setBorder(BorderFactory.createEmptyBorder(45, 45, 45, 45));
        JLabel title = UIFactory.heading("<html>Create your<br>booking<br>profile.</html>", 34);
        hero.add(title);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(248, 249, 251));
        form.setBorder(BorderFactory.createEmptyBorder(32, 60, 32, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(6, 0, 6, 0);

        JLabel heading = new JLabel("Create Account");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        heading.setForeground(UIFactory.INK);
        gbc.gridy = 0;
        form.add(heading, gbc);

        nameField = new JTextField();
        emailField = new JTextField();
        mobileField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        addField(form, gbc, 1, "Full Name", nameField);
        addField(form, gbc, 3, "Email", emailField);
        addField(form, gbc, 5, "Mobile", mobileField);
        addField(form, gbc, 7, "Username", usernameField);
        addField(form, gbc, 9, "Password", passwordField);
        addField(form, gbc, 11, "Confirm Password", confirmPasswordField);

        JButton register = UIFactory.primaryButton("Register");
        register.addActionListener(e -> register());
        gbc.gridy = 13;
        gbc.insets = new Insets(18, 0, 6, 0);
        form.add(register, gbc);

        JButton login = UIFactory.secondaryButton("Back to Login");
        login.addActionListener(e -> {
            dispose();
            new LoginPage();
        });
        gbc.gridy = 14;
        gbc.insets = new Insets(6, 0, 6, 0);
        form.add(login, gbc);

        root.add(hero, BorderLayout.WEST);
        root.add(form, BorderLayout.CENTER);
        return root;
    }

    private void addField(JPanel form, GridBagConstraints gbc, int row, String label, JTextField field) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(UIFactory.INK);
        gbc.gridy = row;
        form.add(l, gbc);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(198, 203, 212)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridy = row + 1;
        form.add(field, gbc);
    }

    private void register() {
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }
        try {
            userService.register(
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    mobileField.getText().trim(),
                    usernameField.getText().trim(),
                    password);
            JOptionPane.showMessageDialog(this, "Account created. Please login.");
            dispose();
            new LoginPage();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationPage::new);
    }
}
