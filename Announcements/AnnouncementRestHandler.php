<?php
require_once("SimpleRest.php");
require_once("Announcement.php");
		
class AnnouncementRestHandler extends SimpleRest {

	function getAllAnnouncements() {	

		$announcement = new Announcement();
		$rawData = $announcement->getAllAnnouncements();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No announcement found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["announcements"] = $rawData;

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