
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


--
-- Database: `aldiwandb`
--
CREATE DATABASE IF NOT EXISTS `aldiwandb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `aldiwandb`;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(200) NOT NULL,
  `address` varchar(255) NOT NULL,
  `tel` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `customer_name`, `address`, `tel`) VALUES
(1, 'Ahmed Al-Mansoori', 'Building 14, Prince Faisal Street, Al-Dammam 32421', '+966 55 123 4567'),
(2, 'Fatima Al-Nasser', 'Apt 23, King Saud Road, Al-Dammam 31952', '+966 50 234 5678'),
(3, 'Abdullah Al-Harbi', 'Villa 8, Al-Raka District, Al-Dammam 34231', '+966 56 345 6789'),
(4, 'Sara Al-Sharif', 'Flat 12, Al-Faisaliah Street, Al-Dammam 31425', '+966 54 456 7890'),
(5, 'Khalid Al-Saud', 'House 7, Al-Quds Neighborhood, Al-Dammam 32114', '+966 59 567 8901'),
(6, 'Noor Al-Awadhi', 'Building 19, Al-Khalidiyah Area, Al-Dammam 31967', '+966 58 678 9012'),
(7, 'Omar Al-Farhan', 'Apartment 5, Al-Nakheel District, Al-Dammam 34321', '+966 57 789 0123'),
(8, 'Layla Al-Ghamdi', 'Villa 11, Al-Murjan Street, Al-Dammam 31987', '+966 53 890 1234'),
(9, 'Mohammed Al-Zahrani', 'Apt 3, Al-Waha Neighborhood, Al-Dammam 32213', '+966 52 901 2345'),
(10, 'AishaAl-Jaber', 'Flat 22, Al-Rashid Road, Al-Dammam 31456', '+966 51 012 3456');

-- --------------------------------------------------------

--
-- Table structure for table `menu_items`
--

CREATE TABLE `menu_items` (
  `item_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_items`
--

INSERT INTO `menu_items` (`item_id`, `name`, `price`) VALUES
(1, 'Pizza Heart', 10),
(2, 'Double Cheese Burger', 20),
(3, 'Burger', 10.5),
(4, 'Cheesesteak', 15.5),
(5, 'Cheesesteak Double', 18.5),
(6, 'Cheesesteak Fire', 22.8),
(7, 'Cola Bottle 2 Liter', 10),
(8, 'Cola Bottle 1 Liter', 10),
(9, 'Cola Can', 3),
(10, 'Frise', 3),
(11, 'Pasta Casserole', 8.4),
(12, 'Pasta Parmesan', 10.2);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `total_cost` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `item_id`, `type`, `quantity`, `total_cost`) VALUES
(1, 1, 1, 'Cash', 2, 20),
(2, 2, 2, 'Credit', 1, 10),
(3, 1, 3, 'Takeaway', 3, 30),
(4, 2, 1, 'Cash', 4, 40),
(5, 3, 4, 'Takeaway', 2, 20),
(6, 3, 11, 'Credit', 2, 22),
(7, 4, 2, 'Takeaway', 2, 30),
(8, 4, 6, 'Takeaway', 2, 30),
(9, 5, 12, 'Credit', 2, 25),
(10, 5, 4, 'Cash', 2, 22);

-- --------------------------------------------------------

--
-- Table structure for table `paycredit`
--

CREATE TABLE `paycredit` (
  `pay_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `person_id` int(11) NOT NULL,
  `cash_withdraw` double NOT NULL,
  `cardNo` varchar(200) NOT NULL,
  `name_on_card` varchar(200) NOT NULL,
  `expDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `paycredit`
--

INSERT INTO `paycredit` (`pay_id`, `customer_id`, `person_id`, `name_on_card`, `cash_withdraw`, `expDate`, `cardNo`) VALUES
(1, 2, 2, 'Sara', 50, '2027-03-12', '3423131456'),
(2, 2, 2, 'Sara', 40, '2027-05-12', '3423131456'),
(3, 3, 3, 'Mohammed', 44, '2028-10-01', '3423345456'),
(4, 4, 3, 'Layla', 35, '2026-11-10', '3423567654'),
(5, 5, 6, 'Noor', 20, '2027-05-10', '3423131987'),
(6, 6, 6, 'Khalid', 28, '2028-04-23', '3423131123'),
(7, 3, 4, 'Khalid', 45, '2029-12-25', '3423131369'),
(8, 3, 4, 'Abdullah', 15, '2026-01-30', '3423131687'),
(9, 4, 4, 'Jaber', 27, '2027-05-20', '3423131753'),
(10, 4, 4, 'Al-Mansoori', 26, '2028-05-11', '3423131487'),
(11, 6, 3, 'Noor', 35, '2028-03-22', '3423131669');

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `person_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `fullname` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`person_id`, `name`, `role`, `password`, `fullname`) VALUES
(1, 'Razan', 'Manager', 'rrr', 'Razan Alqahtani'),
(2, 'Alreem ', 'user', 'aaa', 'Alreem Alkhaldi'),
(3, 'Norah ', 'user', 'nnn', 'Norah Alharkan'),
(4, 'Sara', 'user', 'sss', 'Sara Mohamed'),
(5, 'Marwah', 'user', 'mmm', 'Marwah Ahmed'),
(6, 'Samia', 'user', 'sss', 'Samia Saad'),
(7, 'a', 'Manager', 'a', 'Anhar Mohamed');

-- --------------------------------------------------------

--
-- Table structure for table `takeaway`
--

CREATE TABLE `takeaway` (
  `takeaway_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `delivery_fees` double NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `customer_addr` varchar(255) NOT NULL,
  `customer_tel` varchar(20) NOT NULL,
  `amount_due` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `takeaway`
--

INSERT INTO `takeaway` (`takeaway_id`, `order_id`, `customer_id`, `delivery_fees`, `customer_name`, `customer_addr`,`customer_tel`,`amount_due`) VALUES
(21, 1, 1, 5, 'Ahmed Al-Mansoori', 'Apt 3, Al-Waha Neighborhood, Al-Dammam 32213', '+966 55 123 4567',25),
(22, 2, 2, 6, 'Fatima Al-Nasser', 'Apt 23, King Saud Road, Al-Dammam 31952', '+966 50 234 5678',24),
(23, 3, 3, 7, 'Abdullah Al-Harbi', 'Building 14, Prince Faisal Street, Al-Dammam 32421', '+966 56 345 6789',66),
(24, 4, 4, 5, 'Sara Al-Sharif', 'Villa 8, Al-Raka District, Al-Dammam 34231', '+966 54 456 7890',48),
(25, 5, 5, 6, 'Khalid Al-Saud', 'Villa 11, Al-Murjan Street, Al-Dammam 31987', '+966 59 567 8901',10),
(26, 6, 2, 7, 'Noor Al-Awadhi', 'Flat 12, Al-Faisaliah Street, Al-Dammam 31425', '+966 58 678 9012',15),
(27, 7, 3, 5, 'Omar Al-Farhan', 'Building 19, Al-Khalidiyah Area, Al-Dammam 31967', '+966 57 789 0123',62),
(28, 8, 4, 6, 'Layla Al-Ghamdi', 'Apartment 5, Al-Nakheel District, Al-Dammam 34321', '+966 53 890 1234',24),
(29, 9, 5, 7, 'Mohammed Al-Zahrani', 'House 7, Al-Quds Neighborhood, Al-Dammam 32114', '+966 52 901 2345',35),
(30, 10, 3, 5, 'AishaAl-Jaber', 'House 7, Al-Quds Neighborhood, Al-Dammam 32114', '+966 51 012 3456',29);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `menu_items`
--
ALTER TABLE `menu_items`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indexes for table `paycredit`
--
ALTER TABLE `paycredit`
  ADD PRIMARY KEY (`pay_id`),
  ADD KEY `customer_id` (`customer_id`);
  -- ADD KEY `person_id` (`person_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`person_id`);

--
-- Indexes for table `takeaway`
--
ALTER TABLE `takeaway`
  ADD PRIMARY KEY (`takeaway_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `menu_items`
--
ALTER TABLE `menu_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `paycredit`
--
ALTER TABLE `paycredit`
  MODIFY `pay_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `person_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `takeaway`
--
ALTER TABLE `takeaway`
  MODIFY `takeaway_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `menu_items` (`item_id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `person` (`person_id`);

--
-- Constraints for table `paycredit`
--
ALTER TABLE `paycredit`
  ADD CONSTRAINT `paycredit_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);
  -- ADD CONSTRAINT `paycredit_ibfk_2` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`);

--
-- Constraints for table `takeaway`
--
ALTER TABLE `takeaway`
  ADD CONSTRAINT `takeaway_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `takeaway_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);
COMMIT;
