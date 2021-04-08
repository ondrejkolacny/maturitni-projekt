<?php
include 'DatabaseConfig.php';

try{
$email = $_POST['email'];

// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);




$sql = $conn->prepare("SELECT users.* FROM users WHERE email = :email;");
$sql->bindParam(':email',$email);
$sql->execute();



$row   = $sql->fetch(PDO::FETCH_ASSOC);
if($sql->rowCount() ==1){ 
	           echo json_encode($row);
		
			
	        }else{ 
		 	echo json_encode(array('message'=>'Uživatel neexistuje'));}


			
}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
