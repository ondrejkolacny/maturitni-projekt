<?php
include 'DatabaseConfig.php';

try{


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

$userID = $_POST['user_id'];
$randomNumber = $_POST['random_number'];

$sql = $conn->prepare("SELECT * FROM validloginsstudent WHERE (user_id = :userID AND random_number =:randomNumber)");
$sql->bindParam(':userID',$userID);
$sql->bindParam(':randomNumber',$randomNumber);
$sql->execute();



$row   = $sql->fetch(PDO::FETCH_ASSOC);
if($sql->rowCount() ==0){ 
	           
			echo json_encode(array( "status" => "false") );
			
	        }else{ 
			echo json_encode(array( "status" => "success") );

}
			
}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
