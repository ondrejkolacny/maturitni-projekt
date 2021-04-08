<?php
include 'DatabaseConfig.php';

try{
$skupinaID = $_POST['skupina_id'];


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);


$sql = $conn->prepare("SELECT * FROM plany WHERE skupina_id = :skupinaID;");

$sql->bindParam(':skupinaID',$skupinaID);
$sql->execute();



while($row[]=$sql->fetch(PDO::FETCH_ASSOC)){
  
      $tem = $row;
 
$json = json_encode($tem); 
 
}
echo $json;

}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
