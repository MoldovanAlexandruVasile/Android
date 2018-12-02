<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateAnnouncement();
    }

    function updateAnnouncement(){
        $connect = mysqli_connect('localhost', 'root', '', 'android_server');
        $ID = (int)$_POST["ID"];
        $image_url = $_POST["image_url"];
        $autor = $_POST["autor"];
        $title = $_POST["title"];
        $offerType = $_POST["offerType"];
        $price = $_POST["price"];
        $currency = $_POST["currency"];
        $brand = $_POST["brand"];
        $model = $_POST["model"];
        $year = $_POST["year"];
        $color = $_POST["color"];
        $fuelType = $_POST["fuelType"];
        $transmission = $_POST["transmission"];
        $onBoardKM = $_POST["onBoardKM"];
        $kmOrMiles = $_POST["kmOrMiles"];
        $engineCapacity = $_POST["engineCapacity"];
        $doorsNumber = $_POST["doorsNumber"];
        $seatsNumber = $_POST["seatsNumber"];
        $contact = $_POST["contact"];
        $description = $_POST["description"];

        $query = "UPDATE announcements
                SET image_url = '$image_url', autor = '$autor', title = '$title', offerType = '$offerType', price = '$price',
                currency = '$currency', brand = '$brand', model = '$model', year = '$year', color = '$color', fuelType = '$fuelType',
                transmission = '$transmission', onBoardKM = '$onBoardKM', kmOrMiles = '$kmOrMiles', engineCapacity = '$engineCapacity',
                doorsNumber = '$doorsNumber', seatsNumber = '$seatsNumber', contact = '$contact', description = '$description'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>