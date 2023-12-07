-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Nov 27, 2023 at 01:45 PM
-- Server version: 5.7.39
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pos_java`
--

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(65) NOT NULL,
  `description` text NOT NULL,
  `price` double NOT NULL,
  `img` text NOT NULL,
  `stock` int(11) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `description`, `price`, `img`, `stock`, `created_at`, `updated_at`) VALUES
(52, 'LOST IN THE GLOSS LIPGLASS TRIO', 'A holiday-exclusive lip trio featuring full-sized Lipglass in all-new Pink and Purple shades, along with best-selling Lipglass Clear', 545000, 'https://sdcdn.io/mac/us/mac_sku_STNNY3_1x1_0.png?width=480&height=480', 20, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(53, 'HAIL TO THE CHIC! LIPSTICK TRIO', 'A holiday-exclusive lip kit of full-sized Retro Matte Lipstick and Powder Kiss Lipstick in three best-selling shades', 390000, 'https://www.maccosmetics.com/media/export/cms/collections/11.06.2023/1107_SINGLESDAYGWPCAROUSEL_DESK.jpg', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(54, 'SQUALL GOALS EYE SHADOW PALETTE X 6', 'A holiday-exclusive eye shadow palette featuring six all-new and best-selling Warm-and Cool-toned shades.', 514000, 'https://sdcdn.io/mac/us/mac_sku_SJ8P10_1x1_1.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(55, 'MERRY MUST-HAVES', 'A holiday-exclusive five-piece kit for eyes, lips and skin featuring best-selling Lustreglass Lipstick, Eye Shadow, M·A·CStack Mascara, Fix+ and a mini Hyper Real Serumizer™', 920000, 'https://sdcdn.io/mac/us/mac_sku_STNGY3_1x1_0.png?width=480&height=480', 6, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(56, 'LUSTRELITE MINI LIPSTICK TRIO', 'A holiday-exclusive lip trio of mini Lustreglass Lipstick featuring all-new Neutral and Pink shades.', 390000, 'https://sdcdn.io/mac/us/mac_sku_STMYY3_1x1_0.png?width=480&height=480', 9, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(57, 'SPARKLER EYESHADOW / BIZARRE BLIZZARD BASH', 'A limited-edition eye shadow with a cream-like texture and brilliant sparkly finish in holiday-exclusive shades.', 390000, 'https://sdcdn.io/mac/us/mac_sku_STXA10_1x1_0.png?width=480&height=480', 9, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(58, 'BRUSH OF SNOW ESSENTIAL BRUSH KIT', 'A holiday-exclusive brush kit featuring four fundamental makeup brushes for face and eyes in travel-friendly sizes.', 857000, 'https://sdcdn.io/mac/us/mac_sku_SMXF21_1x1_0.png?width=480&height=480', 9, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(59, 'SNOWBODY’S BUSINESS EYE SHADOW PALETTE X 12', 'A holiday-exclusive 12-pan eye shadow palette featuring all-new and best-selling colours in Matte to Frost textures.', 842000, 'https://sdcdn.io/mac/us/mac_sku_STNTY3_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(60, 'STUDIO RADIANCE SERUM-POWERED FOUNDATION', 'Light to medium beige with peachy golden undertone for medium skin', 717000, 'https://sdcdn.io/mac/us/mac_sku_SYP815_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(61, 'STUDIO FIX TECH CREAM-TO-POWDER FOUNDATION', 'Medium peach with golden peach undertone for medium skin', 654000, 'https://sdcdn.io/mac/us/mac_sku_SKL213_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(62, 'STUDIO FIX FLUID SPF 15', 'fair beige with neutral undertone', 654000, 'https://sdcdn.io/mac/us/mac_sku_M6JC53_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(63, 'STUDIO FIX POWDER PLUS FOUNDATION', 'very fair beige with neutral golden undertone for light skin (neutral-cool)', 654000, 'https://sdcdn.io/mac/us/mac_sku_M51089_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(64, 'STUDIO FIX 24-HOUR SMOOTH WEAR CONCEALER', 'A lightweight liquid concealer with a wand applicator that offers 24-hour wear with medium-to-full buildable coverage and a natural matte finish.', 436000, 'https://sdcdn.io/mac/us/mac_sku_SF4X02_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(65, 'LIP PENCIL', 'A pencil in a wide range of colours designed for shaping, lining or filling in lips.', 374000, 'https://sdcdn.io/mac/us/mac_sku_M38050_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(66, 'LIPGLASS', 'A lip gloss in a wide colour range that can create a glass-like finish or a subtle sheen.', 358000, 'https://sdcdn.io/mac/us/mac_sku_S3HT03_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(67, 'POWDER KISS LIQUID LIPCOLOUR', 'A liquid lipcolour with a whipped, mousse-like texture that delivers Soft-focus moisture-matte, comfortable colour with ten hours of hydration.', 420000, 'https://sdcdn.io/mac/us/mac_sku_SJC208_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41'),
(68, 'EYE SHADOW', 'Soft muted beige-taupe', 358000, 'https://sdcdn.io/mac/us/mac_sku_M25027_1x1_0.png?width=480&height=480', 10, '2023-11-12 03:49:41', '2023-11-12 03:49:41');

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `products_name_index` (`name`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_products_has_many_purchases` (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `purchases`
--
ALTER TABLE `purchases`
  ADD CONSTRAINT `fk_products_has_many_purchases` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
