-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3307
-- Thời gian đã tạo: Th1 27, 2022 lúc 12:41 PM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `foody`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bill`
--

CREATE TABLE `bill` (
  `ID_Bill` int(11) NOT NULL,
  `ID_Cus` int(11) NOT NULL,
  `Total` float NOT NULL DEFAULT 0,
  `Time` datetime NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Shipping_fee` float NOT NULL DEFAULT 0,
  `done` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bill`
--

INSERT INTO `bill` (`ID_Bill`, `ID_Cus`, `Total`, `Time`, `Address`, `Shipping_fee`, `done`) VALUES
(1, 3, 20.65, '2022-01-13 00:00:00', 'Nguyen Huu Tho', 5, 1),
(2, 2, 10.3, '2022-01-08 00:00:00', '', 2, 1),
(3, 3, 9.5, '2022-01-04 00:00:00', 'Nui Thanh', 3, 1),
(4, 3, 9, '2022-01-25 21:05:00', '12 Thang 9 Street ', 1, 1),
(5, 3, 35.1, '2022-01-25 21:05:00', '12 Thang 9 Street ', 1, 1),
(6, 3, 29, '2022-01-25 21:12:00', '12 Thang 9 Street ', 1, 1),
(7, 3, 10.55, '2022-01-25 21:51:00', '12 Thang 9 Street ', 1.5, 1),
(8, 3, 15.35, '2022-01-25 21:51:00', '12 Thang 9 Street ', 1.5, 1),
(9, 3, 14.75, '2022-01-25 21:52:00', '12 Thang 9 Street ', 1.5, 1),
(10, 1, 13.9, '2022-01-26 01:00:00', '12 Thang 9 Street ', 1.5, 1),
(11, 3, 11.6, '2022-01-26 01:12:00', '12 Thang 9 Street', 1.5, 1),
(12, 1, 13, '2022-01-26 13:47:00', '12 Thang 9 Street', 1.5, 1),
(13, 1, 17.6, '2022-01-26 22:26:00', '12 Thang 9 Street', 1.5, 1),
(14, 4, 14.85, '2022-01-27 00:51:00', '12 Thang 9 Street', 1.5, 1),
(15, 9, 8.5, '2022-01-27 17:34:00', '12 Thang 9 Street', 1.5, 1),
(16, 9, 8.5, '2022-01-27 17:35:00', '12 Thang 9 Street', 1.5, 1),
(17, 9, 12.25, '2022-01-27 17:37:00', '12 Thang 9 Street', 1.5, 1),
(18, 9, 15.25, '2022-01-27 18:15:00', '12 Thang 9 Street', 1.5, 1),
(19, 9, 28.85, '2022-01-27 18:16:00', '12 Thang 9 Street', 1.5, 1),
(20, 9, 33.85, '2022-01-27 18:17:00', '12 Thang 9 Street', 1.5, 1),
(21, 9, 33.85, '2022-01-27 18:23:00', '12 Thang 9 Street', 1.5, 1),
(22, 9, 15.25, '2022-01-27 18:24:00', '12 Thang 9 Street', 1.5, 1),
(23, 9, 23.6, '2022-01-27 18:25:00', '12 Thang 9 Street', 1.5, 1),
(24, 9, 20.6, '2022-01-27 18:26:00', '12 Thang 9 Street', 0, 1),
(25, 9, 3, '2022-01-27 18:26:00', '12 Thang 9 Street', 0, 1),
(26, 9, 6, '2022-01-27 18:27:00', '12 Thang 9 Street', 1.5, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bill_detail`
--

CREATE TABLE `bill_detail` (
  `ID_Bill` int(11) NOT NULL,
  `ID_Food` int(11) NOT NULL,
  `Count` int(11) NOT NULL,
  `Price_Total` float DEFAULT 0,
  `Rate` float NOT NULL DEFAULT 0,
  `Reviews` varchar(200) NOT NULL DEFAULT '  '
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bill_detail`
--

INSERT INTO `bill_detail` (`ID_Bill`, `ID_Food`, `Count`, `Price_Total`, `Rate`, `Reviews`) VALUES
(1, 1, 2, 0, 4, ' '),
(1, 3, 3, 0, 4.5, ' '),
(1, 4, 3, 0, 4.5, ' '),
(2, 2, 2, 20, 3.5, 'Good'),
(2, 3, 2, 0, 0, ' '),
(2, 4, 2, 0, 4.5, ' '),
(3, 2, 2, 15, 3.5, 'Review'),
(3, 3, 2, 0, 4, 'Good'),
(4, 1, 2, 5, 5, ''),
(4, 2, 2, 3, 5, ''),
(5, 3, 2, 3.5, 5, ''),
(5, 4, 2, 3.6, 5, ''),
(5, 7, 3, 15, 4.5, ''),
(5, 8, 3, 12, 5, ' '),
(6, 7, 4, 20, 5, ''),
(6, 8, 2, 8, 5, ''),
(7, 1, 2, 5, 4.5, ''),
(7, 3, 3, 5.25, 5, ''),
(8, 3, 3, 5.25, 5, ''),
(8, 4, 2, 3.6, 5, ''),
(8, 7, 1, 5, 5, ''),
(9, 1, 2, 5, 5, ''),
(9, 2, 2, 3, 5, ''),
(9, 3, 3, 5.25, 5, ''),
(10, 4, 2, 3.6, 4, ''),
(10, 7, 2, 10, 5, ''),
(11, 2, 2, 3, 5, ''),
(11, 3, 2, 3.5, 5, ''),
(11, 4, 2, 3.6, 5, ''),
(12, 2, 3, 4.5, 5, ''),
(12, 3, 4, 7, 4, ''),
(13, 3, 2, 3.5, 5, ''),
(13, 4, 2, 3.6, 5, ''),
(13, 7, 1, 5, 5, ''),
(13, 8, 1, 4, 5, ''),
(14, 2, 3, 4.5, 5, ''),
(14, 3, 3, 5.25, 5, ''),
(14, 4, 2, 3.6, 5, ''),
(15, 1, 1, 2.5, 5, ''),
(15, 2, 3, 4.5, 5, ' '),
(16, 3, 4, 7, 4.5, ''),
(17, 1, 3, 7.5, 5, 'Alo'),
(17, 2, 1, 1.5, 5, 'Goood'),
(17, 3, 1, 1.75, 5, ' '),
(18, 1, 3, 7.5, 5, 'Alo'),
(18, 2, 3, 4.5, 5, 'Goood'),
(18, 3, 1, 1.75, 5, ' '),
(19, 1, 1, 2.5, 5, ''),
(19, 2, 3, 4.5, 5, ' '),
(19, 3, 1, 1.75, 5, ''),
(19, 4, 2, 3.6, 4.5, ''),
(19, 7, 3, 15, 5, ''),
(20, 1, 3, 7.5, 5, ' '),
(20, 2, 3, 4.5, 5, ' '),
(20, 3, 1, 1.75, 5, ' '),
(20, 4, 2, 3.6, 4.5, ' '),
(20, 7, 3, 15, 5, ''),
(21, 1, 3, 7.5, 4.5, ''),
(21, 2, 3, 4.5, 4, ''),
(21, 3, 1, 1.75, 5, ''),
(21, 4, 2, 3.6, 5, ''),
(21, 7, 3, 15, 5, ''),
(22, 1, 1, 2.5, 5, ''),
(22, 2, 1, 1.5, 5, ''),
(22, 3, 1, 1.75, 5, ''),
(22, 8, 2, 8, 5, ''),
(23, 2, 3, 4.5, 4.5, 'Gd'),
(23, 4, 2, 3.6, 4, 'it can be better'),
(23, 7, 2, 10, 5, ''),
(23, 8, 1, 4, 5, ''),
(24, 2, 3, 4.5, 5, ''),
(24, 3, 2, 3.5, 5, ''),
(24, 4, 2, 3.6, 5, ''),
(24, 7, 1, 5, 5, ''),
(24, 8, 1, 4, 5, ''),
(25, 2, 2, 3, 5, ''),
(26, 2, 3, 4.5, 5, '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer`
--

CREATE TABLE `customer` (
  `ID_Cus` int(11) NOT NULL,
  `Gmail` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Name_Cus` varchar(100) DEFAULT 'Guest',
  `Phone` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `customer`
--

INSERT INTO `customer` (`ID_Cus`, `Gmail`, `Password`, `Name_Cus`, `Phone`) VALUES
(1, 'tuyen@gmail.com', '1234', 'Thanh Tuyen', '0775705210'),
(2, 'tuan@gmail.com', '123', 'Thanh Tuan', '0123465789'),
(3, 'vuadoan@gmail.com', '123', 'Vua Do An', '0123456798'),
(4, 'nhi@gmail.com', '123', 'Nhi Nhi', '0123456781'),
(5, 'test13@gmail.com', '1234', 'Thêm', '0123456789'),
(6, 'matthaus078@gmail.com', '1234', 'Test', '0775705211'),
(7, 'them@gmail.com', '123', '', '120235655'),
(8, 'themm@gmail.com', '123', '', '0120120210'),
(9, 'admin@gmail.com', '1234', 'Chua Te Review ', '0123456789');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `favorite`
--

CREATE TABLE `favorite` (
  `ID_Food` int(11) NOT NULL,
  `ID_Cus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `favorite`
--

INSERT INTO `favorite` (`ID_Food`, `ID_Cus`) VALUES
(1, 2),
(1, 3),
(1, 9),
(2, 2),
(2, 9),
(3, 1),
(3, 3),
(3, 9),
(4, 1),
(4, 2),
(4, 3),
(7, 1),
(8, 3),
(8, 9);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `foods`
--

CREATE TABLE `foods` (
  `ID_Food` int(11) NOT NULL,
  `Name_Food` varchar(50) NOT NULL,
  `Description_Food` varchar(200) DEFAULT 'Good Food',
  `Frice_Food` float NOT NULL,
  `Link_Img_Food` text DEFAULT NULL,
  `Time_Cooking` int(11) NOT NULL,
  `Rate` float NOT NULL DEFAULT 5,
  `Status` tinyint(1) NOT NULL,
  `Is_Available` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `foods`
--

INSERT INTO `foods` (`ID_Food`, `Name_Food`, `Description_Food`, `Frice_Food`, `Link_Img_Food`, `Time_Cooking`, `Rate`, `Status`, `Is_Available`) VALUES
(1, 'LEMONGRASS CHICKEN (2 PCS)', '2 Pcs Lemongrass Chicken', 2.5, 'https://kfcvietnam.com.vn/uploads/combo/93555ac3f1cadf4112a5e272903a6320.jpg', 5, 4.81818, 1, 1),
(2, 'TENDERODS CHICKEN SKEWER (2 PIECES)', '2 pcs Tenderods Chicken Skewer', 1.5, 'https://kfcvietnam.com.vn/uploads/combo/36227503c3ec95248380c7edb19e4494.jpg', 7, 4.75, 1, 1),
(3, 'PACHITO', '1 Pachito', 1.75, 'https://kfcvietnam.com.vn/uploads/combo/f6d771285267f9460d27074f54f0bc9f.png', 10, 4.83333, 1, 1),
(4, 'Fried Chicken (1 Pc)', '1 Pc of Hot & Spicy Chicken / 1 Pc of Non Spicy Crispy Chicken / 1 Pc of Original Recipe Chicken', 1.8, 'https://kfcvietnam.com.vn/uploads/combo/7166d1bee7b66d1e90e7899cda0b03be.jpg', 10, 4.69231, 1, 1),
(7, 'COMBO FRIED CHICKEN A', '2 Pcs of Hot & Spicy Chicken / 2 Pcs of Non Spicy Crispy Chicken / 2 Pcs of Original Recipe Chicken\n1 Pepsi Can', 5, 'https://kfcvietnam.com.vn/uploads/combo/b09860e31866521c22705711916cc402.jpg', 9, 4.95, 1, 1),
(8, 'COMBO FRIED CHICKEN B', '1 Hot Wings 3 Pcs-1 French Fries (L)-1 Pepsi Can', 4, 'https://kfcvietnam.com.vn/uploads/combo/7d36d8d380315c169ba830b0b5b4c26d.jpg', 6, 5, 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `restaurant`
--

CREATE TABLE `restaurant` (
  `ID_Res` int(11) NOT NULL,
  `Address_Res` varchar(100) NOT NULL DEFAULT ' ',
  `Shipping_Fee_1km` float NOT NULL DEFAULT 0.2,
  `Link_Slide_1` text NOT NULL,
  `Link_Slide_2` text NOT NULL,
  `Phone_number` varchar(10) NOT NULL,
  `Link_Term` text NOT NULL,
  `Link_Forger_Pass` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `restaurant`
--

INSERT INTO `restaurant` (`ID_Res`, `Address_Res`, `Shipping_Fee_1km`, `Link_Slide_1`, `Link_Slide_2`, `Phone_number`, `Link_Term`, `Link_Forger_Pass`) VALUES
(1, '56 Nguyen Luong Bang Street', 0.3, 'https://d1ralsognjng37.cloudfront.net/e61274a4-2f83-4e03-adb0-2253db148f5d.jpeg', 'https://c.ndtvimg.com/2020-08/2dv9fku_kfc-covid_625x300_25_August_20.jpg', '0775705211', 'https://www.facebook.com/', 'https://www.youtube.com/');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`ID_Bill`),
  ADD KEY `ID_Cus` (`ID_Cus`);

--
-- Chỉ mục cho bảng `bill_detail`
--
ALTER TABLE `bill_detail`
  ADD PRIMARY KEY (`ID_Bill`,`ID_Food`),
  ADD KEY `ID_Food` (`ID_Food`);

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`ID_Cus`);

--
-- Chỉ mục cho bảng `favorite`
--
ALTER TABLE `favorite`
  ADD PRIMARY KEY (`ID_Food`,`ID_Cus`),
  ADD KEY `ID_Cus` (`ID_Cus`);

--
-- Chỉ mục cho bảng `foods`
--
ALTER TABLE `foods`
  ADD PRIMARY KEY (`ID_Food`);

--
-- Chỉ mục cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`ID_Res`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `bill`
--
ALTER TABLE `bill`
  MODIFY `ID_Bill` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT cho bảng `customer`
--
ALTER TABLE `customer`
  MODIFY `ID_Cus` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT cho bảng `foods`
--
ALTER TABLE `foods`
  MODIFY `ID_Food` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `ID_Res` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`ID_Cus`) REFERENCES `customer` (`ID_Cus`);

--
-- Các ràng buộc cho bảng `bill_detail`
--
ALTER TABLE `bill_detail`
  ADD CONSTRAINT `bill_detail_ibfk_1` FOREIGN KEY (`ID_Bill`) REFERENCES `bill` (`ID_Bill`),
  ADD CONSTRAINT `bill_detail_ibfk_2` FOREIGN KEY (`ID_Food`) REFERENCES `foods` (`ID_Food`);

--
-- Các ràng buộc cho bảng `favorite`
--
ALTER TABLE `favorite`
  ADD CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`ID_Cus`) REFERENCES `customer` (`ID_Cus`),
  ADD CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`ID_Food`) REFERENCES `foods` (`ID_Food`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
