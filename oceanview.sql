-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 01, 2026 at 08:40 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oceanview`
--

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `bill_id` int(11) NOT NULL,
  `reservation_id` int(11) NOT NULL,
  `generated_date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `reservation_id` int(11) NOT NULL,
  `guest_name` varchar(100) NOT NULL,
  `guest_address` varchar(255) NOT NULL,
  `contact_number` varchar(20) NOT NULL,
  `room_type` varchar(50) NOT NULL,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`reservation_id`, `guest_name`, `guest_address`, `contact_number`, `room_type`, `check_in`, `check_out`, `total_price`) VALUES
(1, 'Sandra net', 'america', '0776291245', 'Deluxe', '2026-02-22', '2026-02-25', 37500.00),
(2, 'Sandra net', 'america', '0776291245', 'Deluxe', '2026-02-22', '2026-02-25', 37500.00),
(3, 'dffdhdf', 'fdhdfdfh', '07415452', 'Superior', '2026-02-25', '2026-02-26', 9000.00),
(4, 'dffdhdf', 'fdhdfdfh', '07415452', 'Superior', '2026-02-25', '2026-02-26', 9000.00),
(5, 'mark anderson', 'matara', '0451122411', 'Standard', '2026-02-22', '2026-02-23', 6000.00),
(6, 'marski anderson', 'mataraa', '0451125411', 'Deluxe', '2026-02-22', '2026-02-25', 37500.00),
(7, 'marski anderson', 'mataraa', '0451125411', 'Deluxe', '2026-02-22', '2026-02-25', 37500.00),
(8, 'Julian Den', 'Bakers street Colombo', '0745521212', 'Standard', '2026-02-23', '2026-02-27', 24000.00),
(9, 'Nikita Sen', 'India', '0321541211', 'Superior', '2026-02-23', '2026-02-28', 45000.00),
(10, 'dsgsfdgdfg', 'sdgsdfgfsdg', '998', 'Deluxe', '2026-02-23', '2026-02-26', 37500.00),
(11, 'Charcos Brandon', 'Hiriketiya South,Matara', '0481114545', 'Standard', '2026-02-25', '2026-02-27', 12000.00),
(12, 'Anthony Goodman', 'Hiriketiya ,Matara', '0717501222', 'Deluxe', '2026-02-25', '2026-02-26', 12500.00),
(13, 'Brandon Henry', 'Rahula', '0412256133', 'Superior', '2026-02-27', '2026-03-02', 27000.00);

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `room_id` int(11) NOT NULL,
  `room_type` varchar(50) NOT NULL,
  `roomRate` decimal(10,2) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`room_id`, `room_type`, `roomRate`, `status`) VALUES
(1, 'Standard', 8000.00, 'Occupied'),
(2, 'Superior', 9000.00, 'Occupied'),
(3, 'Deluxe', 12500.00, 'Occupied');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin123', 'admin'),
(2, 'john', 'john', 'staff'),
(3, 'sarah', 'sarah456', 'customer'),
(5, 'staff1', 'staff123', 'staff'),
(6, 'shanet98', 'shanet26', 'staff'),
(8, 'Rashmi', '1212', 'staff');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`bill_id`),
  ADD UNIQUE KEY `unique_reservation` (`reservation_id`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservation_id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`room_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `reservation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `room_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `FK_Reservation` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bills_ibfk_1` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
