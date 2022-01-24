<?php
    require "connect.php";

    $post_data = ($_POST['data']);

    //json_string -> object : json_decode();
    $postOBJ = json_decode($post_data, true);

    //object -> json_string : json_encode();
    switch($postOBJ['method_name']){
        case 'method_get_food_data':
            $query = "SELECT * FROM foods";
        
            $query_rs = mysqli_query($connect, $query);

            $food_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $food['ID_Food'] = $row['ID_Food'];
                $food['Name_Food'] = $row['Name_Food'];
                $food['Description_Food'] = $row['Description_Food'];
                $food['Frice_Food'] = $row['Frice_Food'];
                $food['Link_Img_Food'] = $row['Link_Img_Food'];
                $food['Time_Cooking'] = $row['Time_Cooking'];
                $food['Rate'] = $row['Rate'];
                $food['Status'] = $row['Status'];
                $food['Is_Available'] = $row['Is_Available'];
                
                array_push($food_array, $food);
            }

            echo json_encode($food_array);
            break;
        case 'method_get_update_food_data':
            $ID_Food = $postOBJ['ID_Food'];

            $query_rate = "SELECT SUM(Rate) FROM bill_detail WHERE ID_Food = $ID_Food AND Rate > 0";
        
            $query_rate_rs = mysqli_query($connect, $query_rate);

            $rate_sum = mysqli_fetch_assoc($query_rate_rs);

            $query_count = "SELECT Count(ID_Food) FROM bill_detail WHERE ID_Food = $ID_Food AND Rate > 0";

            $query_count_rs = mysqli_query($connect, $query_count);

            $count = mysqli_fetch_assoc($query_count_rs);
            if($count['Count(ID_Food)'] > 0){
                $rate = (float) ( (float)$rate_sum['SUM(Rate)']/ (float)$count['Count(ID_Food)']);
            }
            else{
                $rate = 5;
            }

            $query_update_Food = "UPDATE `foods` SET `Rate` = '".$rate."' WHERE `ID_Food` = ".$ID_Food;
            $query_update_Food_rs = mysqli_query($connect, $query_update_Food);
            if($query_update_Food_rs)
                $result['value'] = true;
            else
                $result['value'] = false;
            echo json_encode($result);    
            break;
        default:
            break;
    }
?>