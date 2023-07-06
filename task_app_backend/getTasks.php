<?php
   header("Content-type:application/json");

   require_once("connect.php");
   $query = mysqli_query($conn, "select * from `tasks`");

   $response = array();
   while ( $row = mysqli_fetch_assoc($query)){
      array_push($response,
     	array(
     		'id'    =>$row['id'],
            'title' =>$row['title'],
            'task'  =>$row['task'],
            'color' =>$row['color'],
            'date'  =>$row['date'])

     ) ;

   }

    echo json_encode($response);

?>