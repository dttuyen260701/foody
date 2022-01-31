<?php
    require "connect.php";

    $post_data = ($_POST['data']);

    //json_string -> object : json_decode();
    $postOBJ = json_decode($post_data, true);

    $query_rs = '';
    //object -> json_string : json_encode();
    switch($postOBJ['method_name']){
        case 'method_get_bill_data':
            $ID_Cus = $postOBJ['ID_Cus'];

            $query = "SELECT * FROM bill WHERE ID_Cus = $ID_Cus";
        
            $query_rs = mysqli_query($connect, $query);

            $bill_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $bill['ID_Bill'] = $row['ID_Bill'];
                $bill['ID_Cus'] = $row['ID_Cus'];
                $bill['Total'] = $row['Total'];
                $bill['Time'] = $row['Time'];
                $bill['Address'] = $row['Address'];
                $bill['Shipping_fee'] = $row['Shipping_fee'];
                $bill['done'] = $row['done'];

                array_push($bill_array, $bill);
            }
            echo json_encode($bill_array);
            break;
        case 'method_insert_bill':
            //Insert thi gui doi tuong customer
            $data1 = ($_POST['bill']);
            $query_MaxID = "SELECT MAX(ID_Bill) FROM bill";
            $query_rs_MaxID = mysqli_query($connect, $query_MaxID);
            $result1 = mysqli_fetch_assoc($query_rs_MaxID);
            $result['Next_ID'] = $result1['MAX(ID_Bill)'] + 1;

            $bill = json_decode($data1, true);

            $query = "INSERT INTO `bill` (`ID_Bill`, `ID_Cus`, `Total`, `Time`, `Address`, `Shipping_fee`, `done`) VALUES ('".$result['Next_ID']."', '".$bill['ID_Cus']."', '".(($bill['Total'] != NULL) ? $bill['Total'] : 0)."', '".$bill['Time']."', '".$bill['Address']."', '".$bill['Shipping_fee']."', '".$bill['done']."')";
            $query_rs = mysqli_query($connect, $query);
            
            if($query_rs)
                $result['Next_ID'] = $result1['MAX(ID_Bill)'] + 1;
            else
                $result['Next_ID'] = -1;
            echo json_encode($result);    
            break;
        case 'method_update_bill':
            
            $ID_Bill = $postOBJ['ID_Bill'];
            $query = "UPDATE `bill` SET `done` = '1' WHERE `ID_Bill` = ".$ID_Bill;

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