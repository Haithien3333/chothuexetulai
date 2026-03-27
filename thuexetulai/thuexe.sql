-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for thuexe
CREATE DATABASE IF NOT EXISTS `thuexe` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `thuexe`;

-- Dumping structure for table thuexe.bookings
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `car_id` bigint DEFAULT NULL,
  `rent_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table thuexe.bookings: ~0 rows (approximately)

-- Dumping structure for table thuexe.car
CREATE TABLE IF NOT EXISTS `car` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `seats` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table thuexe.car: ~0 rows (approximately)

-- Dumping structure for table thuexe.cars
CREATE TABLE IF NOT EXISTS `cars` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `seats` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `fuel_type` varchar(255) DEFAULT NULL,
  `transmission` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `description` text,
  `status` varchar(20) DEFAULT 'AVAILABLE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table thuexe.cars: ~4 rows (approximately)
INSERT INTO `cars` (`id`, `name`, `brand`, `seats`, `price`, `fuel_type`, `transmission`, `image`, `description`, `status`) VALUES
	(1, 'Toyota Vios', 'Toyota', 5, 600000, NULL, 'Tự động', 'https://toyota.page/wp-content/uploads/2023/01/Vios-G-TSS-Type-Car-Front.jpg', 'Xe tiết kiệm xăng', 'AVAILABLE'),
	(2, 'Mitsubishi Xpander', 'Mitsubishi', 7, 900000, 'Xăng', 'Số sàn', 'https://images.topgear.com.ph/topgear/images/2024/02/01/2-1706762244.jpg', 'Xe gia đình 7 chỗ', 'AVAILABLE'),
	(3, 'Ford Ranger', 'Ford', 5, 1200000, 'Diesel', 'Tự động', 'https://autos.yahoo.com.tw/p/r/w880/car-trim/June2023/79b167362576f6b493b8ee8c420cd5ef.jpeg', 'Xe bán tải mạnh mẽ', 'AVAILABLE'),
	(4, 'Hyundai Accent', 'Hyundai', 5, 550000, 'Xăng', 'Số sàn', 'https://www.elcarrocolombiano.com/wp-content/uploads/2023/03/20230321-HYUNDAI-ACCENT-2024-PORTADA.jpg', 'Xe phổ thông giá rẻ', 'AVAILABLE'),
	(5, 'civic rs', 'honda', 4, 1300000, 'Diesel', 'Tự động', 'https://img1.oto.com.vn/2022/10/07/OpzfnMD2/honda-civic-type-r2-8986.jpg', NULL, 'AVAILABLE');

-- Dumping structure for table thuexe.payments
CREATE TABLE IF NOT EXISTS `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_id` bigint DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `payment_status` varchar(20) DEFAULT NULL,
  `payment_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table thuexe.payments: ~0 rows (approximately)

-- Dumping structure for table thuexe.reviews
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `car_id` bigint DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `comment` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table thuexe.reviews: ~0 rows (approximately)

-- Dumping structure for table thuexe.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table thuexe.users: ~2 rows (approximately)
INSERT INTO `users` (`id`, `name`, `email`, `phone`, `password`, `role`, `created_at`) VALUES
	(1, 'Nguyen Van A', 'a@gmail.com', '0900000001', '123456', 'ADMIN\r\n', '2026-03-15 14:38:22'),
	(2, 'thien', 't@gmail.com', '12345678', '123456', 'USER', '2026-03-15 21:29:52');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
