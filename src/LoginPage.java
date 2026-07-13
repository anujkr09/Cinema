import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginPage extends JFrame {
    private final UserService userService = new UserService();
    private JTextField userNameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Movie Ticket Booking - Login");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(buildContent());
        setVisible(true);
    }

    private JPanel buildContent() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIFactory.SURFACE);

        JPanel brand = new JPanel(new GridBagLayout());
        brand.setBackground(UIFactory.PANEL);
        brand.setBorder(BorderFactory.createEmptyBorder(46, 50, 46, 50));
        JLabel title = UIFactory.heading("<html>Cinema<br>Booking<br>Desk</html>", 40);
        JLabel subtitle = new JLabel("<html>Fast booking, clean seat selection, and instant ticket generation.</html>");
        subtitle.setForeground(new Color(215, 219, 226));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        brand.add(title, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(25, 0, 0, 0);
        brand.add(subtitle, gbc);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(248, 249, 251));
        form.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        GridBagConstraints f = new GridBagConstraints();
        f.gridx = 0;
        f.fill = GridBagConstraints.HORIZONTAL;
        f.weightx = 1;
        f.insets = new Insets(8, 0, 8, 0);

        JLabel loginTitle = new JLabel("Sign in");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 34));
        loginTitle.setForeground(UIFactory.INK);
        f.gridy = 0;
        form.add(loginTitle, f);

        userNameField = new JTextField();
        passwordField = new JPasswordField();
        addField(form, f, 1, "Username", userNameField);
        addField(form, f, 3, "Password", passwordField);

        JButton loginButton = UIFactory.primaryButton("Login");
        loginButton.addActionListener(e -> login());
        f.gridy = 5;
        f.insets = new Insets(22, 0, 8, 0);
        form.add(loginButton, f);

        JButton registerButton = UIFactory.secondaryButton("Create Account");
        registerButton.addActionListener(e -> {
            dispose();
            new RegistrationPage();
        });
        f.gridy = 6;
        f.insets = new Insets(8, 0, 8, 0);
        form.add(registerButton, f);

        root.add(brand, BorderLayout.WEST);
        root.add(form, BorderLayout.CENTER);
        return root;
    }

    private void addField(JPanel form, GridBagConstraints f, int row, String label, JTextField field) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(UIFactory.INK);
        f.gridy = row;
        form.add(l, f);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(198, 203, 212)),
                BorderFactory.createEmptyBorder(9, 10, 9, 10)));
        f.gridy = row + 1;
        form.add(field, f);
    }

    private void login() {
        String username = userNameField.getText().trim();
        String password = new String(passwordField.getPassword());
        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {
            dispose();
            new DashBoard(user.get());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
