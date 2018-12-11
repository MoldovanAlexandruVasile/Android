<?php
require_once("dbcontroller.php");

Class Admin {
	private $admin = array();
	public function getAllAdmins(){
		$query = "SELECT * FROM admins";
		$dbcontroller = new DBController();
		$this->admin = $dbcontroller->executeSelectQuery($query);
		return $this->admin;
	}	
}
?>