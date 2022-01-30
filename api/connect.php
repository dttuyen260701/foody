<?php
    $connect = mysqli_connect("localhost:3307","root","","foody") or die("Lỗi kết nối database!");
	mysqli_query($connect ,"SET NAMES 'utf8'");
?>