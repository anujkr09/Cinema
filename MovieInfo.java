//package Cinema;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.swing.*;
//
//public class MovieInfo extends JFrame implements ActionListener {
//
//    JPanel panel;
//    JLabel logo, movieName, movieType, movieLanguage, movieDetail, userLoginIDLabel;
//    JLabel aboutHeading, aboutText, movieCast;
//    JLabel castLogo1, castLogo2, castLogo3, castLogo4;
//    JLabel castName1, castName2, castName3, castName4;
//    JButton bookButton, backButton;
//    ImageIcon icon, cast1, cast2, cast3, cast4;
//    JLabel backgroundLabel;
//
//    String selectedMovie;
//
//    public MovieInfo(String selectedMovie) {
//        this.selectedMovie = selectedMovie;
//
//        setTitle("Movie Information");
//        setSize(1000, 800);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setLayout(null);
//
//        // Background image
//        ImageIcon bgIcon = new ImageIcon("Image/12.jpg"); // Path to background image
//        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
//        backgroundLabel = new JLabel(new ImageIcon(bgImg));
//        backgroundLabel.setBounds(0, 0, 1000, 800);
//        backgroundLabel.setLayout(null); // Important for absolute positioning
//
//        // User login label
//        userLoginIDLabel = new JLabel("userLoginID");
//        userLoginIDLabel.setBounds(800, 30, 100, 29);
//        backgroundLabel.add(userLoginIDLabel);
//
//        // Poster
//        icon = new ImageIcon("Image/"+ selectedMovie +".jpeg");
//        logo = new JLabel(icon);
//        logo.setBounds(50, 80, 300, 400);
//        backgroundLabel.add(logo);
//
//        // Movie Name
//        movieName = new JLabel("<html>" + selectedMovie + "</html>");
//        movieName.setFont(new Font("Helvetica", Font.PLAIN, 35));
//        movieName.setForeground(Color.white);
//        movieName.setBounds(400, 100, 300, 100);
//        backgroundLabel.add(movieName);
//
//        // Movie Tags
//        movieType = new JLabel("2D");
//        movieType.setForeground(Color.black);
//        movieType.setBackground(Color.white);
//        movieType.setOpaque(true);
//        movieType.setBorder(BorderFactory.createLineBorder(Color.white, 2));
//        movieType.setHorizontalAlignment(JLabel.CENTER);
//        movieType.setFont(new Font("Helvetica", Font.PLAIN, 15));
//        movieType.setBounds(400, 240, 40, 25);
//        backgroundLabel.add(movieType);
//
//        movieLanguage = new JLabel("Hindi");
//        movieLanguage.setForeground(Color.black);
//        movieLanguage.setBackground(Color.white);
//        movieLanguage.setOpaque(true);
//        movieLanguage.setBorder(BorderFactory.createLineBorder(Color.white, 2));
//        movieLanguage.setHorizontalAlignment(JLabel.CENTER);
//        movieLanguage.setFont(new Font("Helvetica", Font.PLAIN, 15));
//        movieLanguage.setBounds(450, 240, 50, 25);
//        backgroundLabel.add(movieLanguage);
//
//        movieDetail = new JLabel("2h 19m Drama  |  Thriller  | UA13+  |  1 May 2025");
//        movieDetail.setForeground(Color.black);
//        movieDetail.setBackground(Color.white);
//        movieDetail.setOpaque(true);
//        movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
//        movieDetail.setHorizontalAlignment(JLabel.CENTER);
//        movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
//        movieDetail.setBounds(400, 280, 350, 25);
//        backgroundLabel.add(movieDetail);
//
//        // Book Button
//        bookButton = new JButton("Book Tickets");
//        bookButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
//        bookButton.setForeground(Color.white);
//        bookButton.setBackground(Color.red);
//        bookButton.setOpaque(true);
//        bookButton.setFocusable(false);
//        bookButton.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        bookButton.setBounds(460, 330, 200, 40);
//        bookButton.addActionListener(this);
//        backgroundLabel.add(bookButton);
//
//        // Back Button
//        backButton = new JButton("Back");
//        backButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
//        backButton.setForeground(Color.black);
//        backButton.setBackground(Color.WHITE);
//        backButton.setOpaque(true);
//        backButton.setFocusable(false);
//        backButton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
//        backButton.setBounds(40, 30, 80, 30);
//        backButton.addActionListener(e -> {
//            dispose();
//            new DashBoard(); // Make sure DashBoard class is available
//        });
//        backgroundLabel.add(backButton);
//
//        // About Section
//        aboutHeading = new JLabel("<html><h2>About The Movie</h2></html>");
//        aboutHeading.setForeground(Color.white);
//        aboutHeading.setFont(new Font("Helvetica", Font.PLAIN, 15));
//        aboutHeading.setBounds(50, 450, 250, 30);
//        backgroundLabel.add(aboutHeading);
//
//        aboutText = new JLabel("<html>A fiery Income Tax officer, Amay Patnaik, confronts the corrupt nexus in Bhoj. Beneath the veneer of honesty lies a sinister operation tied to Dada Bhai</html>");
//        aboutText.setFont(new Font("Helvetica", Font.PLAIN, 15));
//        aboutText.setForeground(Color.white);
//        aboutText.setBounds(50, 480, 900, 60);
//        backgroundLabel.add(aboutText);
//
//        // Movie Cast Section
//        movieCast = new JLabel("<html><h2>Movie Cast</h2></html>");
//        movieCast.setForeground(Color.white);
//        movieCast.setFont(new Font("Helvetica", Font.PLAIN, 15));
//        movieCast.setBounds(50, 540, 250, 30);
//        backgroundLabel.add(movieCast);
//
//        cast1 = new ImageIcon("image/cast1.png");
//        cast2 = new ImageIcon("image/cast2.png");
//        cast3 = new ImageIcon("image/cast3.png");
//        cast4 = new ImageIcon("image/cast4.png");
//
//        castLogo1 = new JLabel(cast1);
//        castLogo2 = new JLabel(cast2);
//        castLogo3 = new JLabel(cast3);
//        castLogo4 = new JLabel(cast4);
//
//        castLogo1.setBounds(100, 560, 100, 100);
//        castLogo2.setBounds(300, 560, 100, 100);
//        castLogo3.setBounds(500, 560, 100, 100);
//        castLogo4.setBounds(700, 560, 100, 100);
//
//        backgroundLabel.add(castLogo1);
//        backgroundLabel.add(castLogo2);
//        backgroundLabel.add(castLogo3);
//        backgroundLabel.add(castLogo4);
//
//        castName1 = new JLabel("Ajay Devgn");
//        castName1.setForeground(Color.white);
//        castName2 = new JLabel("Saurabh Shukla");
//        castName2.setForeground(Color.white);
//        castName3 = new JLabel("Ileana D'Cruz");
//        castName3.setForeground(Color.white);
//        castName4 = new JLabel("Amit Sial");
//        castName4.setForeground(Color.white);
//
//        castName1.setBounds(150, 720, 100, 20);
//        castName2.setBounds(350, 720, 120, 20);
//        castName3.setBounds(560, 720, 120, 20);
//        castName4.setBounds(770, 720, 120, 20);
//
//        backgroundLabel.add(castName1);
//        backgroundLabel.add(castName2);
//        backgroundLabel.add(castName3);
//        backgroundLabel.add(castName4);
//
//        // Finally, add background label to frame
//        setContentPane(backgroundLabel);
//
//        setResizable(false);
//        setVisible(true);
//        // 
//        loadMovieDetailsFromDB();
//    }
//
//    //
//    private void loadMovieDetailsFromDB() {
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinema_db", "root", "9162");
//
//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movieInfo WHERE title = ?");
//            stmt.setString(1, selectedMovie);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                logo.setIcon(new ImageIcon(rs.getString("poster_path")));
//                movieName.setText("<html>" + rs.getString("title") + "</html>");
//                movieLanguage.setText(rs.getString("language"));
//                movieDetail.setText(rs.getString("duration") + " | " + rs.getString("tags") + " | " + rs.getDate("release_date"));
//                aboutText.setText("<html>" + rs.getString("about") + "</html>");
//            }
//
//            rs.close();
//            stmt.close();
//
//            stmt = conn.prepareStatement("SELECT * FROM movie_cast WHERE movie_title = ?");
//            stmt.setString(1, selectedMovie);
//            rs = stmt.executeQuery();
//
//            int i = 0;
//            JLabel[] castLogos = {castLogo1, castLogo2, castLogo3, castLogo4};
//            JLabel[] castNames = {castName1, castName2, castName3, castName4};
//
//            while (rs.next() && i < 4) {
//                castNames[i].setText(rs.getString("actor_name"));
//                castLogos[i].setIcon(new ImageIcon(rs.getString("actor_image_path")));
//                i++;
//            }
//
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Failed to load movie details: " + ex.getMessage());
//        }
//    }
//    //
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == bookButton) {
//            JOptionPane.showMessageDialog(this, "Redirecting to showtimes...");
//            new MovieShowtimesUI(); // Open showtimes UI
//            dispose();
//        }
//    }
//
//    public static void main(String[] args) {
//        new MovieInfo("Raid-2");
//    }
//}
//

//
package Cinema;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MovieInfo extends JFrame implements ActionListener {

    JPanel panel;
    JLabel logo, movieName, movieType, movieLanguage, movieDetail, userLoginIDLabel;
    JLabel aboutHeading, aboutText, movieCast, movieTime, movieAgeRating, movieReleaseDate;
    JLabel castLogo1, castLogo2, castLogo3, castLogo4;
    JLabel castName1, castName2, castName3, castName4;
    JButton bookButton, backButton;
    ImageIcon icon, cast1, cast2, cast3, cast4;
    JLabel backgroundLabel;

    String selectedMovie;

    // Cast names for each movie
    String[] movie1Cast = {"Ajay Devgn", "Riteish Deshmukh", "Vaani Kapoor", "Tamanna Bhatia"};
    String[] movie2Cast = {"Nani", "Srinidhi Shetty", "Adivi Sesh", "Vijay Sethupathi"};
    String[] movie3Cast = {"Akshay Kumar", "R. Madhavan", "Shriya Saran", "Ananya Pandey"};
    String[] movie4Cast = {"Yash", "Roopa Rayappa", "Ramachandra Raju", "Sanjay Dutt"};
    String[] movie5Cast = {"Ram Charan", "N. T. Rama Rao Jr.", "Ajay Devgn", "Shriya Saran"};
    String[] movie6Cast = {"Ajay Devgn", "Tabu", "Shriya Saran", "Ishita Dutta"};
    String[] movie7Cast = {"Actor5", "Actor6", "Actor7", "Sanjay Dutt"};

    public MovieInfo(String selectedMovie) {
        this.selectedMovie = selectedMovie;

        setTitle("Movie Information");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background image
        ImageIcon bgIcon = new ImageIcon("Image/12.jpg"); // Path to background image
        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
        backgroundLabel = new JLabel(new ImageIcon(bgImg));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        backgroundLabel.setLayout(null); // Important for absolute positioning

        // User login label
        userLoginIDLabel = new JLabel("userLoginID");
        userLoginIDLabel.setBounds(800, 30, 100, 29);
        backgroundLabel.add(userLoginIDLabel);

        // Poster
        
     // Load and scale the movie poster image
        ImageIcon originalIcon = new ImageIcon("Image/" + selectedMovie + ".jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(300, 350, Image.SCALE_SMOOTH); // Width = 300, Height = 400
        icon = new ImageIcon(scaledImage);

        // Set scaled image to JLabel
        logo = new JLabel(icon);
        logo.setBounds(50, 80, 300, 350);
        backgroundLabel.add(logo);


        // Movie Name
        movieName = new JLabel("<html>" + selectedMovie + "</html>");
        movieName.setFont(new Font("Helvetica", Font.PLAIN, 35));
        movieName.setForeground(Color.white);
        movieName.setBounds(400, 100, 300, 100);
        backgroundLabel.add(movieName);

        // Movie Tags
        movieType = new JLabel("2D");
        movieType.setForeground(Color.black);
        movieType.setBackground(Color.white);
        movieType.setOpaque(true);
        movieType.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        movieType.setHorizontalAlignment(JLabel.CENTER);
        movieType.setFont(new Font("Helvetica", Font.PLAIN, 15));
        movieType.setBounds(400, 240, 40, 25);
        backgroundLabel.add(movieType);

        movieLanguage = new JLabel("Hindi");
        movieLanguage.setForeground(Color.black);
        movieLanguage.setBackground(Color.white);
        movieLanguage.setOpaque(true);
        movieLanguage.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        movieLanguage.setHorizontalAlignment(JLabel.CENTER);
        movieLanguage.setFont(new Font("Helvetica", Font.PLAIN, 15));
        movieLanguage.setBounds(450, 240, 50, 25);
        backgroundLabel.add(movieLanguage);

//        movieDetail = new JLabel("2h 19m Drama  |  Thriller  | UA13+  |  1 May 2025");
//        movieDetail.setForeground(Color.black);
//        movieDetail.setBackground(Color.white);
//        movieDetail.setOpaque(true);
//        movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
//        movieDetail.setHorizontalAlignment(JLabel.CENTER);
//        movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
//        movieDetail.setBounds(400, 280, 350, 25);
//        backgroundLabel.add(movieDetail);

        // Book Button
        bookButton = new JButton("Book Tickets");
        bookButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        bookButton.setForeground(Color.white);
        bookButton.setBackground(Color.red);
        bookButton.setOpaque(true);
        bookButton.setFocusable(false);
        bookButton.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        bookButton.setBounds(460, 330, 200, 40);
        bookButton.addActionListener(this);
        backgroundLabel.add(bookButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        backButton.setForeground(Color.black);
        backButton.setBackground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setFocusable(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        backButton.setBounds(40, 30, 80, 30);
        backButton.addActionListener(e -> {
            dispose();
            new DashBoard(); // Make sure DashBoard class is available
        });
        backgroundLabel.add(backButton);

        // About Section
        aboutHeading = new JLabel("<html><h2>About The Movie</h2></html>");
        aboutHeading.setForeground(Color.white);
        aboutHeading.setFont(new Font("Helvetica", Font.PLAIN, 15));
        aboutHeading.setBounds(50, 450, 250, 30);
        backgroundLabel.add(aboutHeading);

        // Movie-specific "About The Movie" text
        aboutText = new JLabel();
        aboutText.setFont(new Font("Helvetica", Font.PLAIN, 15));
        aboutText.setForeground(Color.white);
        aboutText.setBounds(50, 480, 900, 60);
        backgroundLabel.add(aboutText);

        // Movie Time, Age Rating, and Release Date
        movieTime = new JLabel();
        movieTime.setFont(new Font("Helvetica", Font.PLAIN, 15));
        movieTime.setForeground(Color.white);
        movieTime.setBounds(400, 320, 300, 20);
        backgroundLabel.add(movieTime);

        movieAgeRating = new JLabel();
        movieAgeRating.setFont(new Font("Helvetica", Font.PLAIN, 15));
        movieAgeRating.setForeground(Color.white);
        movieAgeRating.setBounds(400, 340, 300, 20);
        backgroundLabel.add(movieAgeRating);

        movieReleaseDate = new JLabel();
        movieReleaseDate.setFont(new Font("Helvetica", Font.PLAIN, 15));
        movieReleaseDate.setForeground(Color.white);
        movieReleaseDate.setBounds(400, 360, 300, 20);
        backgroundLabel.add(movieReleaseDate);

        // Switch between different movie information
        if ("Raid 2".equals(selectedMovie)) {
            aboutText.setText("<html>A fiery Income Tax officer, Amay Patnaik, confronts the corrupt nexus in Bhoj. Beneath the veneer of honesty lies a sinister operation tied to Dada Bhai, a revered politician. As Amay unravels layers of deceit buried in fields and fortresses, one question looms: will justice prevail, or will power silence the truth?</html>");
            movieDetail = new JLabel("2h 19m Drama  |  Thriller  | UA13+  |  1 May 2025");
            movieDetail.setForeground(Color.black);
            movieDetail.setBackground(Color.white);
            movieDetail.setOpaque(true);
            movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            movieDetail.setHorizontalAlignment(JLabel.CENTER);
            movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
            movieDetail.setBounds(400, 280, 350, 25);
            backgroundLabel.add(movieDetail);
            
        } else if ("HIT The Third Case".equals(selectedMovie)) {
            aboutText.setText("<html>Arjun Sarkaar joins Vizag HIT on a mission to solve a series of gritty murders that he had been encountering for some time in his career. His investigation takes him to the four corners of the country, only to put him and his team in the centre of grave danger, leaving him to choose the only way out. Violence.</html>");
            movieDetail = new JLabel("1h 50m  |  Thriller  | UA16+  |  15 June 2025");
            movieDetail.setForeground(Color.black);
            movieDetail.setBackground(Color.white);
            movieDetail.setOpaque(true);
            movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            movieDetail.setHorizontalAlignment(JLabel.CENTER);
            movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
            movieDetail.setBounds(400, 280, 350, 25);
            backgroundLabel.add(movieDetail);
            
            
        } else if ("Keshari Chapter 2".equals(selectedMovie)) {
            aboutText.setText("<html>Inspired by the annals of history, this is the riveting tale of Sankaran Nair, a fearless and charismatic barrister who waged an epic legal battle against the mighty British Empire, exposing to the world the brutal truth behind the Jallianwala Bagh Massacre.</html>");
            movieDetail = new JLabel("2h 10m  |  Thriller  | UA18+  |  20 July 2025");
            movieDetail.setForeground(Color.black);
            movieDetail.setBackground(Color.white);
            movieDetail.setOpaque(true);
            movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            movieDetail.setHorizontalAlignment(JLabel.CENTER);
            movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
            movieDetail.setBounds(400, 280, 350, 25);
            backgroundLabel.add(movieDetail);
            
        } else if ("KGF 2".equals(selectedMovie)) {
            aboutText.setText("<html>The blood-soaked land of Kolar Gold Fields (KGF) has a new overlord now - Rocky, whose name strikes fear in the heart of his foes. His allies look up to Rocky as their saviour, the government sees him as a threat to law and order; enemies are clamouring for revenge and conspiring for his downfall. Bloodier battles and darker days await as Rocky continues on his quest for unchallenged supremacy.</html>");
            movieDetail = new JLabel("1h 55m  |  Thriller  | UA12+  |  10 August 2025");
            movieDetail.setForeground(Color.black);
            movieDetail.setBackground(Color.white);
            movieDetail.setOpaque(true);
            movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            movieDetail.setHorizontalAlignment(JLabel.CENTER);
            movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
            movieDetail.setBounds(400, 280, 350, 25);
            backgroundLabel.add(movieDetail);
            
            
        }else if ("RRR".equals(selectedMovie)) {
            aboutText.setText("<html>RRR is a period drama set in India during the 1920s, revolving around the inspiring journey of two of India`s freedom fighters - Alluri Sitarama Raju and Komaram Bheem.</html>");
            movieDetail = new JLabel("3h 2m  |  Action  | UA  |  12 Mar, 2022");
            movieDetail.setForeground(Color.black);
            movieDetail.setBackground(Color.white);
            movieDetail.setOpaque(true);
            movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            movieDetail.setHorizontalAlignment(JLabel.CENTER);
            movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
            movieDetail.setBounds(400, 280, 350, 25);
            backgroundLabel.add(movieDetail);
            
        }else if ("Drishyam 2".equals(selectedMovie)) {
            aboutText.setText("<html>7 years after the case related to Vijay Salgaonkar and his family was closed, a series of unexpected events bring a truth to light that threatens to change everything for the Salgaonkars. Can Vijay save his family this time?</html>");
            movieDetail = new JLabel("2h 20m  |  Drama  | UA  |  18 Nov, 2022");
            movieDetail.setForeground(Color.black);
            movieDetail.setBackground(Color.white);
            movieDetail.setOpaque(true);
            movieDetail.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            movieDetail.setHorizontalAlignment(JLabel.CENTER);
            movieDetail.setFont(new Font("Helvetica", Font.PLAIN, 15));
            movieDetail.setBounds(400, 280, 350, 25);
            backgroundLabel.add(movieDetail);
            
        }
        else {
            aboutText.setText("<html>Details for this movie are currently unavailable.</html>");
        }

        // Movie Cast Section
        movieCast = new JLabel("<html><h2>Movie Cast</h2></html>");
        movieCast.setForeground(Color.white);
        movieCast.setFont(new Font("Helvetica", Font.PLAIN, 15));
        movieCast.setBounds(50, 540, 250, 30);
        backgroundLabel.add(movieCast);

        // Scaling the cast images to fit properly within the bounds of the JLabel
        cast1 = new ImageIcon("Image/"+ selectedMovie +"Cast1.jpg");
        Image img1 = cast1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
        cast1 = new ImageIcon(img1);

        cast2 = new ImageIcon("Image/"+ selectedMovie +"Cast2.jpg");
        Image img2 = cast2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
        cast2 = new ImageIcon(img2);

        cast3 = new ImageIcon("Image/"+ selectedMovie +"Cast3.jpg");
        Image img3 = cast3.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
        cast3 = new ImageIcon(img3);

        cast4 = new ImageIcon("Image/"+ selectedMovie +"Cast4.jpg");
        Image img4 = cast4.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
        cast4 = new ImageIcon(img4);

        // Set the scaled images to the cast logos
        castLogo1 = new JLabel(cast1);
        castLogo2 = new JLabel(cast2);
        castLogo3 = new JLabel(cast3);
        castLogo4 = new JLabel(cast4);

        castLogo1.setBounds(133, 600, 100, 100);
        castLogo2.setBounds(345, 600, 100, 100);
        castLogo3.setBounds(550, 600, 100, 100);
        castLogo4.setBounds(740, 600, 100, 100);

        backgroundLabel.add(castLogo1);
        backgroundLabel.add(castLogo2);
        backgroundLabel.add(castLogo3);
        backgroundLabel.add(castLogo4);

        // Assign cast names dynamically based on the selected movie
        String[] currentCast;
        if ("Raid 2".equals(selectedMovie)) {
            currentCast = movie1Cast;
        } else if ("HIT The Third Case".equals(selectedMovie)) {
            currentCast = movie2Cast;
        } else if ("Keshari Chapter 2".equals(selectedMovie)) {
            currentCast = movie3Cast;
        }else if ("KGF 2".equals(selectedMovie)) {
            currentCast = movie4Cast;
        }else if ("RRR".equals(selectedMovie)) {
            currentCast = movie5Cast;
        }else if ("Drishyam 2".equals(selectedMovie)) {
            currentCast = movie6Cast;
        } else {
            currentCast = movie7Cast;
        }

        castName1 = new JLabel(currentCast[0]);
        castName2 = new JLabel(currentCast[1]);
        castName3 = new JLabel(currentCast[2]);
        castName4 = new JLabel(currentCast[3]);

        castName1.setForeground(Color.white);
        castName2.setForeground(Color.white);
        castName3.setForeground(Color.white);
        castName4.setForeground(Color.white);

        castName1.setBounds(150, 720, 100, 20);
        castName2.setBounds(350, 720, 120, 20);
        castName3.setBounds(560, 720, 120, 20);
        castName4.setBounds(770, 720, 120, 20);

        backgroundLabel.add(castName1);
        backgroundLabel.add(castName2);
        backgroundLabel.add(castName3);
        backgroundLabel.add(castName4);

        // Finally, add background label to frame
        setContentPane(backgroundLabel);

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            JOptionPane.showMessageDialog(this, "Redirecting to showtimes...");
            new MovieShowtimesUI(selectedMovie); // Open showtimes UI
            dispose();
        }
    }

    public static void main(String[] args) {
        new MovieInfo("Raid-2"); // Pass movie name dynamically
    }
}