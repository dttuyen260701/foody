<?php
    require "connect.php";

    $post_data = ($_POST['data']);

    //json_string -> object : json_decode();
    $postOBJ = json_decode($post_data, true);

    $query_rs = '';
    //object -> json_string : json_encode();
    switch($postOBJ['method_name']){
        case 'method_get_bill_detail_data':
            $ID_Bill = $postOBJ['ID_Bill'];

            $query = "SELECT * FROM bill_detail WHERE ID_Bill = $ID_Bill";
        
            $query_rs = mysqli_query($connect, $query);

            $bill_detail_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $bill_detail['ID_Bill'] = $row['ID_Bill'];
                $bill_detail['ID_Food'] = $row['ID_Food'];
                $bill_detail['Count'] = $row['Count'];
                $bill_detail['Price_Total'] = $row['Price_Total'];
                $bill_detail['Rate'] = $row['Rate'];
                $bill_detail['Reviews'] = $row['Reviews'];
                
                array_push($bill_detail_array, $bill_detail);
            }
            echo json_encode($bill_detail_array);
            break;
        case 'method_get_reviews_data':
            $ID_Food = $postOBJ['ID_Food'];

            $query = "SELECT customer.Name_Cus, bill.Time, bill_detail.Rate, bill_detail.Reviews FROM bill_detail INNER JOIN bill ON bill_detail.ID_Bill = bill.ID_Bill INNER JOIN customer ON bill.ID_Cus = customer.ID_Cus WHERE bill_detail.ID_Food = '".$ID_Food."' AND bill_detail.Rate > 0";
        
            $query_rs = mysqli_query($connect, $query);

            $bill_detail_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $bill_detail['Name_Cus'] = $row['Name_Cus'];
                $bill_detail['Time'] = $row['Time'];
                $bill_detail['Rate'] = $row['Rate'];
                $bill_detail['Reviews'] = $row['Reviews'];
                
                array_push($bill_detail_array, $bill_detail);
            }
            echo json_encode($bill_detail_array);
            break;
        case 'method_insert_bill_detail':
            //Insert thi gui doi tuong customer
            $data1 = ($_POST['bill_detail']);
            $bill_detail = json_decode($data1, true);

            $query = "INSERT INTO `bill_detail` (`ID_Bill`, `ID_Food`, `Count`, `Price_Total`, `Rate`, `Reviews`) VALUES ('".$bill_detail['ID_Bill']."', '".$bill_detail['ID_Food']."', '".$bill_detail['Count']."', '".$bill_detail['Price_Total']."', '".$bill_detail['Rate']."', '".$bill_detail['Reviews']."');";
            $query_rs = mysqli_query($connect, $query);
            
            if($query_rs)
                $result['value'] = true;
            else
                $result['value'] = false;
            echo json_encode($result);    
            break;
        case 'method_update_review':
            $data1 = ($_POST['bill_detail']);
            $bill_detail = json_decode($data1, true);

            $query = "UPDATE `bill_detail` SET `Rate` = '".$bill_detail['Rate']."', `Reviews` = '".$bill_detail['Reviews']."' WHERE `ID_Bill` = ".$bill_detail['ID_Bill']." AND `ID_Food` = ".$bill_detail['ID_Food'];
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