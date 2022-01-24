<?php
    require "connect.php";

    $post_data = ($_POST['data']);

    //json_string -> object : json_decode();
    $postOBJ = json_decode($post_data, true);

    $query_rs = '';
    //object -> json_string : json_encode();
    switch($postOBJ['method_name']){
        case 'method_get_favorite_data':
            $ID_Cus = $postOBJ['ID_Cus'];
            
            $query = "SELECT * FROM favorite WHERE ID_Cus = $ID_Cus";
        
            $query_rs = mysqli_query($connect, $query);

            $fav_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $fav['ID_Food'] = $row['ID_Food'];
                $fav['ID_Cus'] = $row['ID_Cus'];
                
                array_push($fav_array, $fav);
            }
            echo json_encode($fav_array);
            break;
        case 'method_insert_favorite':
            //Insert thi gui doi tuong customer
            $data1 = ($_POST['favorite']);
            $fav = json_decode($data1, true);

            $query = "INSERT INTO `favorite` (`ID_Food`, `ID_Cus`) VALUES ('".$fav['ID_Food']."', '".$fav['ID_Cus']."')";

            $query_rs = mysqli_query($connect, $query);
            
            if($query_rs)
                $result['value'] = true;
            else
                $result['value'] = false;
            echo json_encode($result);    
            break;
        case 'method_del_favorite':   
            $data1 = ($_POST['favorite']);
            $fav = json_decode($data1, true);
            
            $query = "DELETE FROM `favorite` WHERE `ID_Food` = ".$fav['ID_Food']." AND `favorite`.`ID_Cus` = ".$fav['ID_Cus'];
            
            $query_rs = mysqli_query($connect, $query);
            
            if($query_rs)
                $result['value'] = true;
            else
                $result['value'] = false;
            echo json_encode($result);    
            break;
        default:
            break;
    }
?>