package Cinema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;

public class RegistationPage extends JFrame implements ActionListener {

    JLabel login, label, nameLabel, userNameLabel, emailLabel, mobileLabel, passwordLabel, confPasswordLabel, messAge;
    JPanel panel1, panel2;
    JTextField nameField, userNameField, emailField, mobileField;
    JPasswordField passwordField, confPasswordField;
    JButton registerButton;

    public RegistationPage() {
        this.setTitle("Registation Page");
        this.setLayout(null);
        

        // Panel 1
        panel1 = new JPanel();
        panel1.setBounds(0, 0, 400, 600);
        panel1.setBackground(Color.cyan);
        panel1.setLayout(null);

        JLabel label1 = new JLabel("<html><center><i>Welcome To<br>Movie Ticket Booking<br> Registation Page</center></html>");
        label1.setBounds(60, 180, 300, 150);
        label1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        panel1.add(label1);

        // Panel 2
        panel2 = new JPanel();
        panel2.setBounds(400, 0, 400, 600);
        panel2.setBackground(Color.white);
        panel2.setLayout(null);

        // Logo
        ImageIcon icon = new ImageIcon("image/logo.png");
        JLabel label = new JLabel(icon);
        label.setBounds(140, 5, 100, 100);
        panel2.add(label);

        // Registration label
        login = new JLabel("<html><h1>REGISTATION</h1></html>");
        login.setBounds(110, 40, 200, 100);
        panel2.add(login);

        // Form Labels
        nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(60, 125, 100, 30);
        panel2.add(nameLabel);

        emailLabel = new JLabel("Email : ");
        emailLabel.setBounds(60, 175, 100, 30);
        panel2.add(emailLabel);

        mobileLabel = new JLabel("Mobile : ");
        mobileLabel.setBounds(60, 225, 100, 30);
        panel2.add(mobileLabel);

        userNameLabel = new JLabel("Username :");
        userNameLabel.setBounds(60, 275, 100, 30);
        panel2.add(userNameLabel);

        passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(60, 325, 100, 30);
        panel2.add(passwordLabel);

        confPasswordLabel = new JLabel("<html>Confirm<br> Password : </html>");
        confPasswordLabel.setBounds(60, 375, 100, 30);
        panel2.add(confPasswordLabel);

        messAge = new JLabel("Already have a account ? Login");
        messAge.setBounds(100, 470, 200, 30);
        panel2.add(messAge);

        // Form Fields
        nameField = new JTextField(20);
        nameField.setBounds(150, 125, 180, 30);
        panel2.add(nameField);

        emailField = new JTextField(20);
        emailField.setBounds(150, 175, 180, 30);
        panel2.add(emailField);

        mobileField = new JTextField(10);
        mobileField.setBounds(150, 225, 180, 30);
        panel2.add(mobileField);

        userNameField = new JTextField(20);
        userNameField.setBounds(150, 275, 180, 30);
        panel2.add(userNameField);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 325, 180, 30);
        panel2.add(passwordField);

        confPasswordField = new JPasswordField(20);
        confPasswordField.setBounds(150, 375, 180, 30);
        panel2.add(confPasswordField);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setBounds(150, 435, 100, 30);
        registerButton.setBackground(Color.cyan);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        registerButton.addActionListener(e -> registerUser());
        panel2.add(registerButton);

        this.add(panel1);
        this.add(panel2);

        this.setSize(800, 600);
        this.setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void registerUser() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String mobile = mobileField.getText().trim();
        String username = userNameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confPassword = new String(confPasswordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || username.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if (!password.equals(confPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinema_db", "root", "9162");

            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
                conn.close();
                return;
            }

          //  String hashedPassword = hashPassword(password);

            PreparedStatement pst = conn.prepareStatement("INSERT INTO users(name, email, mobile, username, password) VALUES (?, ?, ?, ?, ?)");
            
            System.out.println("Inserting user: " + username);

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, mobile);
            pst.setString(4, username);
            pst.setString(5, password);
           // pst.setString(5, hashedPassword);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Account registered successfully!");
                dispose();
                new LoginPage();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }

            conn.close();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "JDBC Driver not found: " + ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

//    private String hashPassword(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256"); // or "SHA-512"
//            byte[] hash = md.digest(password.getBytes());
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hash) {
//                sb.append(String.format("%02x", b));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Hashing algorithm not found.");
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Required by ActionListener but handled via lambda
    }

    public static void main(String[] args) {
        new RegistationPage();
    }
}

