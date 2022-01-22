-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3307
-- Thời gian đã tạo: Th1 14, 2022 lúc 10:03 AM
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
  `Total` float DEFAULT NULL,
  `Time` date NOT NULL,
  `Address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bill`
--

INSERT INTO `bill` (`ID_Bill`, `ID_Cus`, `Total`, `Time`, `Address`) VALUES
(1, 3, NULL, '2022-01-13', ''),
(2, 2, NULL, '2022-01-08', ''),
(3, 3, NULL, '2022-01-04', ''),
(4, 3, NULL, '2022-01-07', ''),
(5, 3, 2, '2022-01-02', ''),
(7, 1, 0, '2022-01-13', ''),
(8, 2, 0, '2022-01-13', ''),
(9, 2, 0, '2022-01-08', '15 Da Nang'),
(10, 3, 0, '2022-01-13', '');

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
  `Reviews` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bill_detail`
--

INSERT INTO `bill_detail` (`ID_Bill`, `ID_Food`, `Count`, `Price_Total`, `Rate`, `Reviews`) VALUES
(1, 1, 2, 0, 0, NULL),
(1, 3, 3, 0, 0, NULL),
(1, 4, 3, 0, 0, NULL),
(2, 2, 2, 20, 3.5, 'Good'),
(2, 3, 2, 0, 0, NULL),
(2, 4, 2, 0, 0, NULL),
(3, 3, 2, 0, 4, 'Good');

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
(4, 'tuyen@gmail.com', '123', 'Thanh Tuyen', '0123456789');

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
(3, 1),
(4, 2),
(4, 3);

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
  `Status` tinyint(1) NOT NULL,
  `Is_Available` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `foods`
--

INSERT INTO `foods` (`ID_Food`, `Name_Food`, `Description_Food`, `Frice_Food`, `Link_Img_Food`, `Time_Cooking`, `Status`, `Is_Available`) VALUES
(1, 'LEMONGRASS CHICKEN (2 PCS)', '2 Pcs Lemongrass Chicken', 2.5, 'https://kfcvietnam.com.vn/uploads/combo/93555ac3f1cadf4112a5e272903a6320.jpg', 5, 1, 1),
(2, 'TENDERODS CHICKEN SKEWER (2 PIECES)', '2 pcs Tenderods Chicken Skewer', 1.5, 'https://kfcvietnam.com.vn/uploads/combo/36227503c3ec95248380c7edb19e4494.jpg', 5, 1, 1),
(3, 'PACHITO', '1 Pachito', 1.75, 'https://kfcvietnam.com.vn/uploads/combo/f6d771285267f9460d27074f54f0bc9f.png', 10, 1, 1),
(4, 'Fried Chicken (1 Pc)', '1 Pc of Hot & Spicy Chicken / 1 Pc of Non Spicy Crispy Chicken / 1 Pc of Original Recipe Chicken', 1.8, 'https://kfcvietnam.com.vn/uploads/combo/7166d1bee7b66d1e90e7899cda0b03be.jpg', 5, 1, 1);

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
  MODIFY `ID_Bill` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `customer`
--
ALTER TABLE `customer`
  MODIFY `ID_Cus` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT cho bảng `foods`
--
ALTER TABLE `foods`
  MODIFY `ID_Food` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
