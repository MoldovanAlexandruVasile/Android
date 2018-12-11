<?php
require_once("SimpleRest.php");
require_once("Admin.php");
		
class AdminRestHandler extends SimpleRest {

	function getAllAdmins() {	

		$admin = new Admin();
		$rawData = $admin->getAllAdmins();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No admin found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["admin"] = $rawData;

		if(strpos($requestContentType,'application/json') !== false){
			$response = $this->encodeJson($result);
			echo $response;
		}
	}
	
	public function encodeJson($responseData) {
		$jsonResponse = json_encode($responseData);
		return $jsonResponse;		
	}
}
?>