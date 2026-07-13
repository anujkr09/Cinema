CREATE DATABASE IF NOT EXISTS cinema_db;
USE cinema_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    mobile VARCHAR(15) NOT NULL UNIQUE,
    username VARCHAR(60) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS movies (
    id VARCHAR(40) PRIMARY KEY,
    title VARCHAR(120) NOT NULL,
    poster_path VARCHAR(255),
    background_path VARCHAR(255),
    format VARCHAR(20),
    language VARCHAR(40),
    details VARCHAR(255),
    description TEXT,
    cast TEXT
);

CREATE TABLE IF NOT EXISTS showtimes (
    id VARCHAR(80) PRIMARY KEY,
    movie_id VARCHAR(40) NOT NULL,
    theater VARCHAR(160) NOT NULL,
    show_date VARCHAR(20) NOT NULL,
    show_time VARCHAR(20) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id VARCHAR(20) PRIMARY KEY,
    username VARCHAR(60) NOT NULL,
    movie_id VARCHAR(40) NOT NULL,
    theater VARCHAR(160) NOT NULL,
    show_date VARCHAR(20) NOT NULL,
    show_time VARCHAR(20) NOT NULL,
    total_amount INT NOT NULL,
    payment_method VARCHAR(40) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE IF NOT EXISTS booking_seats (
    booking_id VARCHAR(20) NOT NULL,
    seat_no VARCHAR(10) NOT NULL,
    PRIMARY KEY (booking_id, seat_no),
    UNIQUE KEY unique_show_seat (seat_no, booking_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);
