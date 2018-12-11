<?php
class DBController {
	private $conn = "";

	function __construct() {
		$conn = $this->connectDB();
		if(!empty($conn)) {
			$this->conn = $conn;			
		}
	}

	function connectDB() {
		$conn = mysqli_connect('localhost', 'root', '', 'android_server');
		return $conn;
	}

	function executeSelectQuery($query) {
		$json_array = array();
		$result = mysqli_query($this->conn, $query);
		while($row = mysqli_fetch_assoc($result)) {
			$json_array[] = $row;
		}
		if(!empty($json_array))
			return $json_array;
	}
}
?>