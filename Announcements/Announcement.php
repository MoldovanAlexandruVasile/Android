<?php
require_once("dbcontroller.php");

Class Announcement {
	private $announcement = array();
	public function getAllAnnouncements(){
		$query = "SELECT * FROM announcements";
		$dbcontroller = new DBController();
		$this->announcement = $dbcontroller->executeSelectQuery($query);
		return $this->announcement;
	}	
}
?>