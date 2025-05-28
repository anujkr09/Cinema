//package Cinema;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.swing.*;
//
//public class DashBoard extends JFrame implements ActionListener {
//
//    private static final Statement DatabaseConnection = null;
//	JPanel panel;
//    JLabel backgroundLabel, logo, recentTab, recommendedTab, userLoginIDLabel;
//
//    public DashBoard() {
//        this.setTitle("DashBoard");
//        this.setSize(1000, 800);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(null); 
//        this.setLocationRelativeTo(null); // center window
//
//        // Main panel
//        panel = new JPanel();
//        panel.setLayout(null); 
//        panel.setBounds(0, 0, 1000, 800);
//
//        // Background image
//        ImageIcon bgIcon = new ImageIcon("Image/3.jpg"); // <-- Use your desired image here
//        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
//        backgroundLabel = new JLabel(new ImageIcon(bgImg));
//        backgroundLabel.setBounds(0, 0, 1000, 800);
//        backgroundLabel.setLayout(null); // So we can add components directly on top
//
//        // Logo
//        ImageIcon logoIcon = new ImageIcon("image/logo.png"); 
//        logo = new JLabel(logoIcon);
//        logo.setBounds(20, 15, 50, 50); 
//        backgroundLabel.add(logo);
//
//        // User login label=
//        userLoginIDLabel = new JLabel("userLoginID");
//        userLoginIDLabel.setBounds(800, 30, 100, 29);
//        userLoginIDLabel.setForeground(Color.WHITE); // Visible on dark background
//        backgroundLabel.add(userLoginIDLabel);
//
//        // Recent label
//        recentTab = new JLabel("Recent");
//        recentTab.setFont(new Font("Helvetica", Font.BOLD, 15));
//        recentTab.setForeground(Color.WHITE);
//        recentTab.setBounds(20, 80, 100, 20); 
//        backgroundLabel.add(recentTab);
//
//        // Buttons for Recent movies
//        addImageButton("Image/Raid_2_poster.jpg", 20, 110, "Raid 2");
//        addImageButton("Image/Raid_2_poster.jpg", 338, 110, "Logo 1");
//        addImageButton("Image/Raid_2_poster.jpg", 660, 110, "Logo 2");
//
//        // Recommended label
//        recommendedTab = new JLabel("Recommended");
//        recommendedTab.setFont(new Font("Helvetica", Font.BOLD, 15));
//        recommendedTab.setForeground(Color.WHITE);
//        recommendedTab.setBounds(20, 415, 150, 20);
//        backgroundLabel.add(recommendedTab);
//
//        // Buttons for Recommended movies
//        addImageButton("Image/kgf.jpeg", 15, 440, "KGF");
//        addImageButton("Image/rrr.jpeg", 338, 440, "RRR");
//        addImageButton("Image/drishyam.jpeg", 660, 440, "Drishyam");
//        
//        //   loadMoviesFromDB();
//
//        // Add background to panel, then panel to frame
//        panel.add(backgroundLabel);
//        this.add(panel);
//        this.setResizable(false);
//        this.setVisible(true);
//        //
//        loadMoviesFromDB();
//    }
//    
//                                         //
//    private void loadMoviesFromDB() {
//        try (Connection conn = DatabaseConnection.getConnection()) {
//            String query = "SELECT name, image_path, category FROM movies";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            ResultSet rs = stmt.executeQuery();
//
//            int recentX = 20, recommendedX = 15;
//            int recentCount = 0, recommendedCount = 0;
//
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String imagePath = rs.getString("image_path");
//                String category = rs.getString("category");
//
//                if (category.equalsIgnoreCase("Recent") && recentCount < 3) {
//                    addImageButton(imagePath, recentX, 110, name);
//                    recentX += 318;
//                    recentCount++;
//                } else if (category.equalsIgnoreCase("Recommended") && recommendedCount < 3) {
//                    addImageButton(imagePath, recommendedX, 440, name);
//                    recommendedX += 318;
//                    recommendedCount++;
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error loading movies from database: " + e.getMessage());
//        }
//    }
//
//    
//                                        //
//    // Helper method to create and add JButton with scaled image icon
//    private void addImageButton(String imagePath, int x, int y, String movieName) {
//        ImageIcon icon = new ImageIcon(imagePath);
//        Image img = icon.getImage();
//        ImageIcon scaledIcon = new ImageIcon(img.getScaledInstance(310, 300, Image.SCALE_SMOOTH));
//
//        JButton button = new JButton(scaledIcon);
//        button.setFocusPainted(false); 
//        button.setBorderPainted(false); 
//        button.setContentAreaFilled(false); 
//        button.setBounds(x, y, 310, 300);
//
//        // Add action listener to open MovieInfo when button is clicked
//        button.addActionListener(e -> openMovieInfo(movieName));
//
//        backgroundLabel.add(button);
//    }
//
//    // Method to open MovieInfo class
//    private void openMovieInfo(String movieName) {
//        dispose(); // Close dashboard
//        new MovieInfo(movieName); // Open movie info window
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new DashBoard());
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        // Optional: Add if needed
//    }
//}

package Cinema;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class DashBoard extends JFrame implements ActionListener {

    private static final Statement DatabaseConnection = null;
	JPanel panel;
    JLabel backgroundLabel, logo, recentTab, recommendedTab, userLoginIDLabel;

    public DashBoard() {
        this.setTitle("DashBoard");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null); 
        this.setLocationRelativeTo(null); // center window

        // Main panel
        panel = new JPanel();
        panel.setLayout(null); 
        panel.setBounds(0, 0, 1000, 800);

        // Background image
        ImageIcon bgIcon = new ImageIcon("Image/1.jpg"); // <-- Use your desired image here
        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(bgImg));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        backgroundLabel.setLayout(null); // So we can add components directly on top

//        // User login label=
//        userLoginIDLabel = new JLabel("userLoginID");
//        userLoginIDLabel.setBounds(800, 30, 100, 29);
//        userLoginIDLabel.setForeground(Color.WHITE); // Visible on dark background
//        backgroundLabel.add(userLoginIDLabel);

        // Recent label
        recentTab = new JLabel("Recent");
        recentTab.setFont(new Font("Helvetica", Font.BOLD, 15));
        recentTab.setForeground(Color.WHITE);
        recentTab.setBounds(20, 80, 100, 20); 
        backgroundLabel.add(recentTab);

        // Buttons for Recent movies
        addImageButton("Image/Raid 2.jpg", 20, 110, "Raid 2");
        addImageButton("Image/HIT The Third Case.jpg", 338, 110, "HIT The Third Case");
        addImageButton("Image/Keshari Chapter 2.jpg", 660, 110, "Keshari Chapter 2");

        // Recommended label
        recommendedTab = new JLabel("Recommended");
        recommendedTab.setFont(new Font("Helvetica", Font.BOLD, 15));
        recommendedTab.setForeground(Color.WHITE);
        recommendedTab.setBounds(20, 415, 150, 20);
        backgroundLabel.add(recommendedTab);

        // Buttons for Recommended movies
        addImageButton("Image/KGF 2.jpg", 15, 440, "KGF 2");
        addImageButton("Image/RRR.jpg", 338, 440, "RRR");
        addImageButton("Image/Drishyam 2.jpg", 660, 440, "Drishyam 2");
        
        //   loadMoviesFromDB();

        // Add background to panel, then panel to frame
        panel.add(backgroundLabel);
        this.add(panel);
        this.setResizable(false);
        this.setVisible(true);
        //
        loadMoviesFromDB();
    }
    
                                         //
    private void loadMoviesFromDB() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT name, image_path, category FROM movies";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int recentX = 20, recommendedX = 15;
            int recentCount = 0, recommendedCount = 0;

            while (rs.next()) {
                String name = rs.getString("name");
                String imagePath = rs.getString("image_path");
                String category = rs.getString("category");

                if (category.equalsIgnoreCase("Recent") && recentCount < 3) {
                    addImageButton(imagePath, recentX, 110, name);
                    recentX += 318;
                    recentCount++;
                } else if (category.equalsIgnoreCase("Recommended") && recommendedCount < 3) {
                    addImageButton(imagePath, recommendedX, 440, name);
                    recommendedX += 318;
                    recommendedCount++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading movies from database: " + e.getMessage());
        }
    }

    
                                        //
    // Helper method to create and add JButton with scaled image icon
    private void addImageButton(String imagePath, int x, int y, String movieName) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        ImageIcon scaledIcon = new ImageIcon(img.getScaledInstance(310, 300, Image.SCALE_SMOOTH));

        JButton button = new JButton(scaledIcon);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false); 
        button.setBounds(x, y, 310, 300);

        // Add action listener to open MovieInfo when button is clicked
        button.addActionListener(e -> openMovieInfo(movieName));

        backgroundLabel.add(button);
    }

    // Method to open MovieInfo class
    private void openMovieInfo(String movieName) {
        dispose(); // Close dashboard
        new MovieInfo(movieName); // Open movie info window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashBoard());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Optional: Add if needed
    }
}
