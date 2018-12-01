<?php
require_once("AnnouncementRestHandler.php");
		
$view = "";
if(isset($_GET["view"]))
	$view = $_GET["view"];
/*
controls the RESTful services
URL mapping
*/
switch($view){

	case "all":
		// to handle REST Url /mobile/list/
		$announcementRestHandler = new AnnouncementRestHandler();
		$announcementRestHandler->getAllAnnouncements();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
