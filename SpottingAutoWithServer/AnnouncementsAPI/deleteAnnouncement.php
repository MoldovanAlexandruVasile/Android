<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        deleteAnnouncement();
    }

    function deleteAnnouncement(){
        $connect = mysqli_connect('localhost', 'root', '', 'android_server');

        $ID = (int)$_POST["ID"];
        $query = "DELETE FROM announcements WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>