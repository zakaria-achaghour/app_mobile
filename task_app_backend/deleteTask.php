<?php
     if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $id=$_POST['id'];

        require_once("connect.php");

        $query = "DELETE from `tasks` where id='$id')";

          if ( mysqli_query($conn,$query) ){
          $response['success'] = true;
          $response['message'] = "Successfully";

        }else {
           $response['success'] = false;
           $response['message'] = "Failed";
        }
   } else {

      $response['success'] = false;
      $response['message'] = "error!!!!";
 }

    echo json_encode($response);
?>