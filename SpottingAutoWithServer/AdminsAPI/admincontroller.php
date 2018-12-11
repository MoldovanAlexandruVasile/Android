<?php
require_once("AdminRestHandler.php");
		
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
		$adminRestHandler = new AdminRestHandler();
		$adminRestHandler->getAllAdmins();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
