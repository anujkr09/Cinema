//package Cinema;
//import javax.swing.*;
//import java.awt.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class MovieShowtimesUI extends JFrame {
//
//    private static final Statement DBConnection = null;
//	JLabel userLoginIDLabel, movieNameLabel, movieHallName1, movieHallName2, numberOfSeat;
//    JPanel panel, movieDateGrid, movieTimeGrid1, movieTimeGrid2, numberOfSeatGrid;
//    JButton selectSeat;
//
//    private JButton selectedDateButton = null;
//    private JButton selectedTimeButton1 = null;
//    private JButton selectedTimeButton2 = null;
//    private JButton selectedSeatButton = null;  // Track the previously selected seat button
//    private int selectedSeats = 0;  // Variable to store the selected number of seats
//    //
//    private String selectedDate = null;
//    private String selectedTime = null;
//    private String selectedTheater = null;
//    public MovieShowtimesUI() {
//        setTitle("Showtimes - Movie");
//        setSize(1000, 800);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//        setLocationRelativeTo(null);
//
//        // Background setup
//        ImageIcon bgIcon = new ImageIcon("Image/13.jpg");
//        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
//        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImg));
//        backgroundLabel.setBounds(0, 0, 1000, 800);
//        backgroundLabel.setLayout(null);
//
//        // Main panel
//        panel = new JPanel();
//        panel.setLayout(null);
//        panel.setOpaque(false);
//        panel.setBounds(0, 0, 1000, 800);
//
//        // User Login ID Label
//        userLoginIDLabel = new JLabel("userLoginID");
//        userLoginIDLabel.setBounds(800, 30, 100, 29);
//        panel.add(userLoginIDLabel);
//
//        // Movie Name
//        movieNameLabel = new JLabel("<html><h1>Raid-2</h1></html>");
//        movieNameLabel.setBounds(30, 0, 200, 100);
//        panel.add(movieNameLabel);
//
//        // Movie Date Buttons
//        movieDateGrid = new JPanel(new GridLayout(1, 7, 10, 10));
//        movieDateGrid.setOpaque(false);
//        movieDateGrid.setBounds(20, 120, 950, 50);
//        for (int i = 1; i <= 7; i++) {
//            JButton dateButton = new JButton("May " + i);
//            styleWhiteButton(dateButton);
//            int finalI = i;
//            dateButton.addActionListener(e -> {
//                if (selectedDateButton == dateButton) {
//                    // Deselect if clicked again
//                    styleWhiteButton(dateButton);
//                    selectedDateButton = null;
//                } else {
//                    if (selectedDateButton != null) {
//                        styleWhiteButton(selectedDateButton);  // Reset previous
//                    }
//                    selectedDateButton = dateButton;
//                    styleSelectedButton(dateButton);  // Highlight current
//                }
//            });
//            movieDateGrid.add(dateButton);
//        }
//        panel.add(movieDateGrid);
//
//        // Hall 1
//        movieHallName1 = new JLabel("<html><h2>Cinepolis : DB Mall, Bhopal</h2></html>");
//        movieHallName1.setForeground(Color.white);
//        movieHallName1.setFont(new Font("Helvetica", Font.BOLD, 15));
//        movieHallName1.setBounds(50, 210, 300, 100);
//        panel.add(movieHallName1);
//
//        movieTimeGrid1 = new JPanel(new GridLayout(2, 5, 20, 20));
//        movieTimeGrid1.setOpaque(false);
//        movieTimeGrid1.setBounds(300, 240, 650, 100);
//        String[] times = {"10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM", "8:00 PM", "10:00 PM", "12:00 AM", "2:00 AM", "4:00 AM"};
//        for (String time : times) {
//            JButton timeButton = new JButton(time);
//            styleWhiteButton(timeButton);
//            timeButton.addActionListener(e -> {
//                if (selectedTimeButton1 == timeButton) {
//                    // Deselect if clicked again
//                    styleWhiteButton(timeButton);
//                    selectedTimeButton1 = null;
//                } else {
//                    if (selectedTimeButton1 != null) {
//                        styleWhiteButton(selectedTimeButton1); // Reset previous
//                    }
//                    selectedTimeButton1 = timeButton;
//                    styleSelectedButton(timeButton); // Highlight current
//                }
//                // Reset Hall 2 time selection when Hall 1 time is selected
//                if (selectedTimeButton2 != null) {
//                    styleWhiteButton(selectedTimeButton2);
//                }
//            });
//            movieTimeGrid1.add(timeButton);
//        }
//        panel.add(movieTimeGrid1);
//
//        // Hall 2
//        movieHallName2 = new JLabel("<html><h2>PVR : Peoples Mall, Bhopal</h2></html>");
//        movieHallName2.setForeground(Color.white);
//        movieHallName2.setFont(new Font("Helvetica", Font.BOLD, 15));
//        movieHallName2.setBounds(50, 380, 300, 100);
//        panel.add(movieHallName2);
//
//        movieTimeGrid2 = new JPanel(new GridLayout(2, 5, 20, 20));
//        movieTimeGrid2.setOpaque(false);
//        movieTimeGrid2.setBounds(300, 410, 650, 100);
//        for (String time : times) {
//            JButton timeButton = new JButton(time);
//            styleWhiteButton(timeButton);
//            timeButton.addActionListener(e -> {
//                if (selectedTimeButton2 == timeButton) {
//                    // Deselect if clicked again
//                    styleWhiteButton(timeButton);
//                    selectedTimeButton2 = null;
//                } else {
//                    if (selectedTimeButton2 != null) {
//                        styleWhiteButton(selectedTimeButton2); // Reset previous
//                    }
//                    selectedTimeButton2 = timeButton;
//                    styleSelectedButton(timeButton); // Highlight current
//                }
//                // Reset Hall 1 time selection when Hall 2 time is selected
//                if (selectedTimeButton1 != null) {
//                    styleWhiteButton(selectedTimeButton1);
//                }
//            });
//            movieTimeGrid2.add(timeButton);
//        }
//        panel.add(movieTimeGrid2);
//
//        // Number of Seats
//        numberOfSeat = new JLabel("<html><h2>Number of seats</h2></html>");
//        numberOfSeat.setBounds(100, 550, 200, 100);
//        numberOfSeat.setForeground(Color.white);
//        numberOfSeat.setFont(new Font("Helvetica", Font.BOLD, 15));
//        panel.add(numberOfSeat);
//
//        numberOfSeatGrid = new JPanel(new GridLayout(1, 5, 20, 20));
//        numberOfSeatGrid.setOpaque(false);
//        numberOfSeatGrid.setBounds(360, 580, 500, 50);
//        int[] seatOptions = {1, 2, 3, 4, 5};
//        for (int n : seatOptions) {
//            JButton seatBtn = new JButton(String.valueOf(n));
//            styleWhiteButton(seatBtn);
//            seatBtn.setFont(new Font("Arial", Font.BOLD, 16));
//            seatBtn.addActionListener(e -> {
//                if (selectedSeatButton == seatBtn) {
//                    // Deselect if clicked again
//                    styleWhiteButton(seatBtn);
//                    selectedSeatButton = null;
//                    selectedSeats = 0;  // Reset selected seats
//                } else {
//                    if (selectedSeatButton != null) {
//                        styleWhiteButton(selectedSeatButton);  // Reset previously selected seat
//                    }
//                    selectedSeatButton = seatBtn;  // Track the newly selected button
//                    seatBtn.setBackground(Color.GREEN);  // Set background color to green for the selected seat
//                    selectedSeats = n;  // Store the selected number of seats
//                }
//            });
//            numberOfSeatGrid.add(seatBtn);
//        }
//        panel.add(numberOfSeatGrid);
//
//        // Select Seat Button (Exception: Red button)
//        selectSeat = new JButton("<html><h1>Select Seat Position</h1></html>");
//        selectSeat.setForeground(Color.WHITE);
//        selectSeat.setBackground(Color.RED);
//        selectSeat.setOpaque(true);
//        selectSeat.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
//        selectSeat.setFocusable(false);
//        selectSeat.setHorizontalAlignment(JLabel.CENTER);
//        selectSeat.setBounds(430, 670, 250, 50);
//        selectSeat.addActionListener(e -> validateAndProceed());
//        panel.add(selectSeat);
//
//        // Final assembly
//        backgroundLabel.add(panel);
//        setContentPane(backgroundLabel);
//
//        setResizable(false);
//        setVisible(true);
//    }
//    //
//    private void loadMovieAndShowtimes() {
//        try (Connection conn = DBConnection.getConnection()) {
//            Statement stmt = conn.createStatement();
//
//            // Load Movie
//            ResultSet rsMovie = stmt.executeQuery("SELECT * FROM movieshow LIMIT 1");
//         int movieId = 1;
//            //final int movieId = rsMovie.getInt("movie_id");
//
//            if (rsMovie.next()) {
//                movieNameLabel.setText("<html><h1>" + rsMovie.getString("name") + "</h1></html>");
//                movieId = rsMovie.getInt("movie_id");
//            }
//           
//            // Load available dates
//            PreparedStatement psDates = conn.prepareStatement(
//                "SELECT DISTINCT date FROM showtimes WHERE movie_id = ? ORDER BY date LIMIT 7"
//            );
//            psDates.setInt(1, movieId);
//            ResultSet rsDates = psDates.executeQuery();
//            
//           
//            final int finalMovieId = movieId; // ✅ Capture the value for the lambda
//
//
//            while (rsDates.next()) {
//                String dateStr = rsDates.getDate("date").toString();
//                JButton dateBtn = new JButton(dateStr);
//                styleWhiteButton(dateBtn);
//                dateBtn.addActionListener(e -> {
//                    if (selectedDateButton != null) styleWhiteButton(selectedDateButton);
//                    selectedDateButton = dateBtn;
//                    styleSelectedButton(dateBtn);
//                    selectedDate = dateStr;
////                    loadTimesForDate(movieId, dateStr);
//                    loadTimesForDate(finalMovieId, dateStr);
//                });
//                movieDateGrid.add(dateBtn);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error loading data from database.");
//        }
//    }
//
//
//    private void loadTimesForDate(int movieId, String dateStr) {
//        movieTimeGrid1.removeAll();
//        movieTimeGrid2.removeAll();
//
//        try (Connection conn = DBConnection.getConnection()) {
//            PreparedStatement ps = conn.prepareStatement(
//                "SELECT s.time, t.name FROM showtimes s " +
//                "JOIN theaters t ON s.theater_id = t.theater_id " +
//                "WHERE s.movie_id = ? AND s.date = ?"
//            );
//            ps.setInt(1, movieId);
//            ps.setString(2, dateStr);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                String timeStr = rs.getTime("time").toString();
//                String theaterName = rs.getString("name");
//
//                JButton timeBtn = new JButton(timeStr);
//                styleWhiteButton(timeBtn);
//                timeBtn.addActionListener(e -> {
//                    selectedTime = timeStr;
//                    selectedTheater = theaterName;
//                });
//
//                if (theaterName.contains("Cinepolis")) {
//                    movieTimeGrid1.add(timeBtn);
//                } else {
//                    movieTimeGrid2.add(timeBtn);
//                }
//            }
//
//            
//            movieTimeGrid1.revalidate();
//            movieTimeGrid2.revalidate();
//            movieTimeGrid1.repaint();
//            movieTimeGrid2.repaint();
//
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    //
//
//    private void validateAndProceed() {
//        if (selectedDateButton == null) {
//            JOptionPane.showMessageDialog(this, "Please select a date.");
//            return;
//        }
//
//        if (selectedTimeButton1 == null && selectedTimeButton2 == null) {
//            JOptionPane.showMessageDialog(this, "Please select a time.");
//            return;
//        }
//
//        if (selectedSeats == 0) {
//            JOptionPane.showMessageDialog(this, "Please select the number of seats.");
//            return;
//        }
//
//        // If all validations pass, proceed to the next page
//        openSeatLayout(3);
//    }
//
//    private void openSeatLayout(int maxSeatsToSelect) {
//        // Transition only after "Select Seat Position" button click and validation
//        String movie = "Raid-2";
//        String theater = selectedTimeButton1 != null ? "Cinepolis : DB Mall, Bhopal" : "PVR : Peoples Mall, Bhopal";
//        dispose();
//        new SeatLayoutManager(movie, theater, maxSeatsToSelect);
//    }
//
//    private void styleWhiteButton(JButton button) {
//        button.setBackground(Color.WHITE);
//        button.setForeground(Color.BLACK);
//        button.setFont(new Font("Arial", Font.PLAIN, 14));
//        button.setFocusPainted(false);
//        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        button.setOpaque(true);
//    }
//
//    private void styleSelectedButton(JButton button) {
//        button.setBackground(Color.GREEN);
//        button.setForeground(Color.WHITE);
//        button.setBorder(BorderFactory.createLineBorder(Color.GREEN.darker()));
//        button.setFont(new Font("Arial", Font.BOLD, 14));
//    }
//
//    public static void main(String[] args) {
//        new MovieShowtimesUI();
//    }
//}

//
package Cinema;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MovieShowtimesUI extends JFrame {

    private static final Statement DBConnection = null;
	JLabel userLoginIDLabel, movieNameLabel, movieHallName1, movieHallName2, numberOfSeat;
    JPanel panel, movieDateGrid, movieTimeGrid1, movieTimeGrid2, numberOfSeatGrid;
    JButton selectSeat;
    String selectedMovie;

    private JButton selectedDateButton = null;
    private JButton selectedTimeButton1 = null;
    private JButton selectedTimeButton2 = null;
    private JButton selectedSeatButton = null;  // Track the previously selected seat button
    private int selectedSeats = 0;  // Variable to store the selected number of seats
    //
    private String selectedDate = null;
    private String selectedTime = null;
    private String selectedTheater = null;
    public MovieShowtimesUI(String selectedMovie) {
        setTitle("Showtimes - Movie");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Background setup
        ImageIcon bgIcon = new ImageIcon("Image/13.jpg");
        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImg));
        backgroundLabel.setBounds(0, 0, 1000, 800);
        backgroundLabel.setLayout(null);

        // Main panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(0, 0, 1000, 800);
        
        //
        
        
     // Back Button
        JButton backButton = new JButton("← Back");
        backButton.setBounds(10, 30, 80, 29);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);// visible
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            dispose();
            new  MovieInfo(selectedMovie);
        });
        backgroundLabel.add(backButton);        
        // User Login ID Label
//        userLoginIDLabel = new JLabel("userLoginID");
//        userLoginIDLabel.setBounds(800, 30, 100, 29);
//        panel.add(userLoginIDLabel);
        

        // Movie Name
        movieNameLabel = new JLabel("<html><h1>"+ selectedMovie +"</h1></html>");
        movieNameLabel.setBounds(150, 0, 200, 100);
        movieNameLabel.setForeground(Color.WHITE);// visible
        panel.add(movieNameLabel);

        // Movie Date Buttons
        movieDateGrid = new JPanel(new GridLayout(1, 7, 10, 10));
        movieDateGrid.setOpaque(false);
        movieDateGrid.setBounds(20, 120, 950, 50);
        for (int i = 1; i <= 7; i++) {
            JButton dateButton = new JButton("May " + i);
            styleWhiteButton(dateButton);
            int finalI = i;
            dateButton.addActionListener(e -> {
                if (selectedDateButton == dateButton) {
                    // Deselect if clicked again
                    styleWhiteButton(dateButton);
                    selectedDateButton = null;
                } else {
                    if (selectedDateButton != null) {
                        styleWhiteButton(selectedDateButton);  // Reset previous
                    }
                    selectedDateButton = dateButton;
                    styleSelectedButton(dateButton);  // Highlight current
                }
            });
            movieDateGrid.add(dateButton);
        }
        panel.add(movieDateGrid);

        // Hall 1
        movieHallName1 = new JLabel("<html><h2>Cinepolis : DB Mall, Bhopal</h2></html>");
        movieHallName1.setForeground(Color.white);
        movieHallName1.setFont(new Font("Helvetica", Font.BOLD, 15));
        movieHallName1.setBounds(50, 210, 300, 100);
        panel.add(movieHallName1);

        movieTimeGrid1 = new JPanel(new GridLayout(2, 5, 20, 20));
        movieTimeGrid1.setOpaque(false);
        movieTimeGrid1.setBounds(300, 240, 650, 100);
        String[] times = {"10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM", "8:00 PM", "10:00 PM", "12:00 AM", "2:00 AM", "4:00 AM"};
        for (String time : times) {
            JButton timeButton = new JButton(time);
            styleWhiteButton(timeButton);
            timeButton.addActionListener(e -> {
                if (selectedTimeButton1 == timeButton) {
                    // Deselect if clicked again
                    styleWhiteButton(timeButton);
                    selectedTimeButton1 = null;
                } else {
                    if (selectedTimeButton1 != null) {
                        styleWhiteButton(selectedTimeButton1); // Reset previous
                    }
                    selectedTimeButton1 = timeButton;
                    styleSelectedButton(timeButton); // Highlight current
                }
                // Reset Hall 2 time selection when Hall 1 time is selected
                if (selectedTimeButton2 != null) {
                    styleWhiteButton(selectedTimeButton2);
                }
            });
            movieTimeGrid1.add(timeButton);
        }
        panel.add(movieTimeGrid1);

        // Hall 2
//        movieHallName2 = new JLabel("<html><h2>PVR : Peoples Mall, Bhopal</h2></html>");
//        movieHallName2.setForeground(Color.white);
//        movieHallName2.setFont(new Font("Helvetica", Font.BOLD, 15));
//        movieHallName2.setBounds(50, 380, 300, 100);
//        panel.add(movieHallName2);

        movieTimeGrid2 = new JPanel(new GridLayout(2, 5, 20, 20));
        movieTimeGrid2.setOpaque(false);
        movieTimeGrid2.setBounds(300, 410, 650, 100);
        for (String time : times) {
            JButton timeButton = new JButton(time);
            styleWhiteButton(timeButton);
            timeButton.addActionListener(e -> {
                if (selectedTimeButton2 == timeButton) {
                    // Deselect if clicked again
                    styleWhiteButton(timeButton);
                    selectedTimeButton2 = null;
                } else {
                    if (selectedTimeButton2 != null) {
                        styleWhiteButton(selectedTimeButton2); // Reset previous
                    }
                    selectedTimeButton2 = timeButton;
                    styleSelectedButton(timeButton); // Highlight current
                }
                // Reset Hall 1 time selection when Hall 2 time is selected
                if (selectedTimeButton1 != null) {
                    styleWhiteButton(selectedTimeButton1);
                }
            });
            movieTimeGrid2.add(timeButton);
        }
        panel.add(movieTimeGrid2);

        // Number of Seats
        numberOfSeat = new JLabel("<html><h2>Number of seats</h2></html>");
        numberOfSeat.setBounds(100, 550, 200, 100);
        numberOfSeat.setForeground(Color.white);
        numberOfSeat.setFont(new Font("Helvetica", Font.BOLD, 15));
        panel.add(numberOfSeat);

        numberOfSeatGrid = new JPanel(new GridLayout(1, 5, 20, 20));
        numberOfSeatGrid.setOpaque(false);
        numberOfSeatGrid.setBounds(360, 580, 500, 50);
        int[] seatOptions = {1, 2, 3, 4, 5};
        for (int n : seatOptions) {
            JButton seatBtn = new JButton(String.valueOf(n));
            styleWhiteButton(seatBtn);
            seatBtn.setFont(new Font("Arial", Font.BOLD, 16));
            seatBtn.addActionListener(e -> {
                if (selectedSeatButton == seatBtn) {
                    // Deselect if clicked again
                    styleWhiteButton(seatBtn);
                    selectedSeatButton = null;
                    selectedSeats = 0;  // Reset selected seats
                } else {
                    if (selectedSeatButton != null) {
                        styleWhiteButton(selectedSeatButton);  // Reset previously selected seat
                    }
                    selectedSeatButton = seatBtn;  // Track the newly selected button
                    seatBtn.setBackground(Color.GREEN);  // Set background color to green for the selected seat
                    selectedSeats = n;  // Store the selected number of seats
                }
            });
            numberOfSeatGrid.add(seatBtn);
        }
        panel.add(numberOfSeatGrid);

        // Select Seat Button (Exception: Red button)
        selectSeat = new JButton("<html><h1>Select Seat Position</h1></html>");
        selectSeat.setForeground(Color.WHITE);
        selectSeat.setBackground(Color.RED);
        selectSeat.setOpaque(true);
        selectSeat.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        selectSeat.setFocusable(false);
        selectSeat.setHorizontalAlignment(JLabel.CENTER);
        selectSeat.setBounds(430, 670, 250, 50);
        selectSeat.addActionListener(e -> validateAndProceed());
        panel.add(selectSeat);

        // Final assembly
        backgroundLabel.add(panel);
        setContentPane(backgroundLabel);

        setResizable(false);
        setVisible(true);
    }
    //
    private void loadMovieAndShowtimes() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();

            // Load Movie
            ResultSet rsMovie = stmt.executeQuery("SELECT * FROM movieshow LIMIT 1");
         int movieId = 1;
            //final int movieId = rsMovie.getInt("movie_id");

            if (rsMovie.next()) {
                movieNameLabel.setText("<html><h1>" + rsMovie.getString("name") + "</h1></html>");
                movieId = rsMovie.getInt("movie_id");
            }
           
            // Load available dates
            PreparedStatement psDates = conn.prepareStatement(
                "SELECT DISTINCT date FROM showtimes WHERE movie_id = ? ORDER BY date LIMIT 7"
            );
            psDates.setInt(1, movieId);
            ResultSet rsDates = psDates.executeQuery();
            
           
            final int finalMovieId = movieId; // ✅ Capture the value for the lambda


            while (rsDates.next()) {
                String dateStr = rsDates.getDate("date").toString();
                JButton dateBtn = new JButton(dateStr);
                styleWhiteButton(dateBtn);
                dateBtn.addActionListener(e -> {
                    if (selectedDateButton != null) styleWhiteButton(selectedDateButton);
                    selectedDateButton = dateBtn;
                    styleSelectedButton(dateBtn);
                    selectedDate = dateStr;
//                    loadTimesForDate(movieId, dateStr);
                    loadTimesForDate(finalMovieId, dateStr);
                });
                movieDateGrid.add(dateBtn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from database.");
        }
    }


    private void loadTimesForDate(int movieId, String dateStr) {
        movieTimeGrid1.removeAll();
        movieTimeGrid2.removeAll();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT s.time, t.name FROM showtimes s " +
                "JOIN theaters t ON s.theater_id = t.theater_id " +
                "WHERE s.movie_id = ? AND s.date = ?"
            );
            ps.setInt(1, movieId);
            ps.setString(2, dateStr);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String timeStr = rs.getTime("time").toString();
                String theaterName = rs.getString("name");

                JButton timeBtn = new JButton(timeStr);
                styleWhiteButton(timeBtn);
                timeBtn.addActionListener(e -> {
                    selectedTime = timeStr;
                    selectedTheater = theaterName;
                });

                if (theaterName.contains("Cinepolis")) {
                    movieTimeGrid1.add(timeBtn);
                } else {
                    movieTimeGrid2.add(timeBtn);
                }
            }

            movieTimeGrid1.revalidate();
            movieTimeGrid2.revalidate();
            movieTimeGrid1.repaint();
            movieTimeGrid2.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //

    private void validateAndProceed() {
        if (selectedDateButton == null) {
            JOptionPane.showMessageDialog(this, "Please select a date.");
            return;
        }

        if (selectedTimeButton1 == null && selectedTimeButton2 == null) {
            JOptionPane.showMessageDialog(this, "Please select a time.");
            return;
        }

        if (selectedSeats == 0) {
            JOptionPane.showMessageDialog(this, "Please select the number of seats.");
            return;
        }

        // If all validations pass, proceed to the next page
        openSeatLayout(3);
    }
    

    private void openSeatLayout(int maxSeatsToSelect) {
        // Transition only after "Select Seat Position" button click and validation
         String movie = "Raid-2";
    	//String movie = " ";
        String theater = selectedTimeButton1 != null ? "Cinepolis : DB Mall, Bhopal" : "PVR : Peoples Mall, Bhopal";

        dispose();
        new SeatLayoutManager(movie, theater, maxSeatsToSelect);
    }

    private void styleWhiteButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setOpaque(true);
    }

    private void styleSelectedButton(JButton button) {
        button.setBackground(Color.GREEN);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.GREEN.darker()));
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    public static void main(String[] args) {
		new MovieShowtimesUI("Raid 2");
    }
}
