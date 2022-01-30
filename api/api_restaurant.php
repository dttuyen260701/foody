<?php
    require "connect.php";

    $post_data = ($_POST['data']);

    //json_string -> object : json_decode();
    $postOBJ = json_decode($post_data, true);

    $query_rs = '';
    //object -> json_string : json_encode();
    switch($postOBJ['method_name']){
        case 'method_get_restaurant_data':
            $ID_Res = $postOBJ['ID_Res'];

            $query = "SELECT * FROM restaurant WHERE ID_Res = ".$ID_Res;
        
            $query_rs = mysqli_query($connect, $query);

            $restaurant_array = array();

            while($row = mysqli_fetch_assoc($query_rs)){
                $restaurant['ID_Res'] = $row['ID_Res'];
                $restaurant['Address_Res'] = $row['Address_Res'];
                $restaurant['Shipping_Fee_1km'] = $row['Shipping_Fee_1km'];
                $restaurant['Link_Slide_1'] = $row['Link_Slide_1'];
                $restaurant['Link_Slide_2'] = $row['Link_Slide_2'];
                $restaurant['Phone_number'] = $row['Phone_number'];
                $restaurant['Link_Term'] = $row['Link_Term'];
                $restaurant['Link_Forger_Pass'] = $row['Link_Forger_Pass'];
                
                array_push($restaurant_array, $restaurant);
            }
            echo json_encode($restaurant_array);
            break;
        default:
            break;
    }
?>