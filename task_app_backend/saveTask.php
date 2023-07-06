<?php

      if($_SERVER['REQUEST_METHOD'] == 'POST'){

        $title=$_POST['title'];
        $task= $_POST['task'];
        $color=$_POST['color'];

        require_once("connect.php");

        $query = "INSERT INTO `tasks` (title, task, color) VALUES ('$title', '$task', '$color')";

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