<?php
    require "connect.php";

    $post_data = ($_POST['data']);

    //json_string -> object : json_decode();
    $postOBJ = json_decode($post_data, true);

    $query_rs = '';
    //object -> json_string : json_encode();
    switch($postOBJ['method_name']){
        case 'method_get_customer_data':
            $Gmail = $postOBJ['Gmail'];

            $query = "SELECT * FROM customer WHERE Gmail = '$Gmail'";
        
            $query_rs = mysqli_query($connect, $query);

            $cus_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $cus['ID_Cus'] = $row['ID_Cus'];
                $cus['Gmail'] = $row['Gmail'];
                $cus['Password'] = $row['Password'];
                $cus['Name_Cus'] = $row['Name_Cus'];
                $cus['Phone'] = $row['Phone'];
                
                array_push($cus_array, $cus);
            }
            echo json_encode($cus_array);
            break;
        case 'method_get_customer_data_byID':
            $ID_Cus = $postOBJ['ID_Cus'];

            $query = "SELECT * FROM customer WHERE ID_Cus = '$ID_Cus'";
        
            $query_rs = mysqli_query($connect, $query);

            $cus_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $cus['ID_Cus'] = $row['ID_Cus'];
                $cus['Gmail'] = $row['Gmail'];
                $cus['Password'] = $row['Password'];
                $cus['Name_Cus'] = $row['Name_Cus'];
                $cus['Phone'] = $row['Phone'];
                
                array_push($cus_array, $cus);
            }
            echo json_encode($cus_array);
            break;

        case 'method_check_mail_exist':
            $Gmail = $postOBJ['Gmail'];

            $query = "SELECT * FROM customer WHERE Gmail = '$Gmail'";
        
            $query_rs = mysqli_query($connect, $query);

            $result['value'] = true;
            while($row = mysqli_fetch_assoc($query_rs)){
                $result['value'] = false;
            }
            echo json_encode($result);    
            break;
        case 'method_insert_customer':
            //Lay ID max
            $query = "SELECT MAX(ID_Cus) FROM customer";
            $query_rs = mysqli_query($connect, $query);
            $result1 = mysqli_fetch_assoc($query_rs);
            $ID = $result1['MAX(ID_Cus)'] + 1;
            
            //Insert thi gui doi tuong customer
            $data1 = ($_POST['customer']);
            $customer = json_decode($data1, true);

            $query = "INSERT INTO `customer` (`ID_Cus`, `Gmail`, `Password`, `Name_Cus`, `Phone`) VALUES ('".$ID."', '".$customer['Gmail']."', '".$customer['Password']."', '".$customer['Name_Cus']."', '".$customer['Phone']."')";

            $query_rs = mysqli_query($connect, $query);
            
            if($query_rs)
                $result['value'] = true;
            else
                $result['value'] = false;
            echo json_encode($result);    
            break;
        case 'method_update_customer':
            $data1 = ($_POST['customer']);
            $Customer = json_decode($data1, true);

            $query = "UPDATE `customer` SET `Gmail` = '".$Customer['Gmail']."', `Password` = '".$Customer['Password']."', `Name_Cus` = '".$Customer['Name_Cus']."', `Phone` = '".$Customer['Phone']."' WHERE `ID_Cus` = ".$Customer['ID_Cus'];
        
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