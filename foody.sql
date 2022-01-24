-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3307
-- Thời gian đã tạo: Th1 24, 2022 lúc 05:18 PM
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
  `Distance` float NOT NULL DEFAULT 0,
  `done` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bill`
--

INSERT INTO `bill` (`ID_Bill`, `ID_Cus`, `Total`, `Time`, `Address`, `Distance`, `done`) VALUES
(1, 3, 16.65, '2022-01-13 00:00:00', '', 5, 1),
(2, 2, 10.3, '2022-01-08 00:00:00', '', 2, 1),
(3, 3, 7.1, '2022-01-04 00:00:00', '', 3, 1),
(4, 2, 2, '2022-01-20 08:59:30', '', 1, 1),
(5, 3, 14.6, '2022-01-23 23:35:00', 'Pick your address', 3, 1),
(6, 3, 17.9, '2022-01-23 23:35:00', 'Pick your address', 2, 1),
(7, 3, 8.2, '2022-01-24 11:36:00', 'Pick your address', 1, 1),
(8, 3, 9.2, '2022-01-24 11:39:00', 'Pick your address', 1, 1);

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
(5, 1, 2, 5, 4.5, 'Good food, i will order again'),
(5, 2, 1, 1.5, 4, 'Good'),
(5, 3, 2, 3.5, 5, 'Best'),
(5, 7, 1, 4, 3, 'It can be better'),
(6, 2, 1, 1.5, 3.5, 'OK'),
(6, 7, 3, 12, 5, ''),
(6, 8, 1, 4, 5, ''),
(7, 2, 3, 4.5, 5, ''),
(7, 3, 2, 3.5, 5, ''),
(8, 1, 3, 7.5, 5, ' I will re-order this food'),
(8, 2, 1, 1.5, 4.5, ' So good');

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
(1, 'tuyen@gmail.com', '123', 'Thanh Tuyen', '0123456789'),
(2, 'tuan@gmail.com', '123', 'Thanh Tuan', '0123465789'),
(3, 'nhi@gmail.com', '123', 'Yen Nhi', '0123456798'),
(4, 'test123@gmail.com', '123', 'Thêm', '0123456789'),
(5, 'test13@gmail.com', '123', 'Thêm', '0123456789');

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
(2, 2),
(2, 3),
(3, 1),
(4, 2),
(7, 3),
(8, 3);

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
(1, 'LEMONGRASS CHICKEN (2 PCS)', '2 Pcs Lemongrass Chicken', 2.5, 'https://kfcvietnam.com.vn/uploads/combo/93555ac3f1cadf4112a5e272903a6320.jpg', 5, 4.5, 1, 1),
(2, 'TENDERODS CHICKEN SKEWER (2 PIECES)', '2 pcs Tenderods Chicken Skewer', 1.5, 'https://kfcvietnam.com.vn/uploads/combo/36227503c3ec95248380c7edb19e4494.jpg', 5, 4, 1, 1),
(3, 'PACHITO', '1 Pachito', 1.75, 'https://kfcvietnam.com.vn/uploads/combo/f6d771285267f9460d27074f54f0bc9f.png', 10, 4.625, 1, 1),
(4, 'Fried Chicken (1 Pc)', '1 Pc of Hot & Spicy Chicken / 1 Pc of Non Spicy Crispy Chicken / 1 Pc of Original Recipe Chicken', 1.8, 'https://kfcvietnam.com.vn/uploads/combo/7166d1bee7b66d1e90e7899cda0b03be.jpg', 5, 4.5, 1, 1),
(7, 'COMBO FRIED CHICKEN A', '2 Pcs of Hot & Spicy Chicken / 2 Pcs of Non Spicy Crispy Chicken / 2 Pcs of Original Recipe Chicken\n1 Pepsi Can', 4, 'https://kfcvietnam.com.vn/uploads/combo/b09860e31866521c22705711916cc402.jpg', 5, 4, 1, 1),
(8, 'COMBO FRIED CHICKEN B', '1 Hot Wings 3 Pcs-1 French Fries (L)-1 Pepsi Can', 4, 'https://kfcvietnam.com.vn/uploads/combo/7d36d8d380315c169ba830b0b5b4c26d.jpg', 5, 5, 1, 1);

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
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `bill`
--
ALTER TABLE `bill`
  MODIFY `ID_Bill` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

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
