<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertAnnouncement();
    }

    function insertAnnouncement(){
        $connect = mysqli_connect('localhost', 'root', '', 'android_server');
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

        $query = "INSERT INTO announcements(image_url, autor, title, offerType, price, currency, brand, model, year, color, fuelType, 
                transmission, onBoardKM, kmOrMiles, engineCapacity, doorsNumber, seatsNumber, contact, description) VALUES
                            ('$image_url', '$autor', '$title', '$offerType', '$price', '$currency', '$brand', '$model', '$year',
                            '$color', '$fuelType', '$transmission', '$onBoardKM', '$kmOrMiles', '$engineCapacity', '$doorsNumber',
                            '$seatsNumber', '$contact', '$description');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>