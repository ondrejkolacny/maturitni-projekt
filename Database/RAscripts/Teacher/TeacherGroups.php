<?php
include 'DatabaseConfig.php';

try{

$teacherID = $_POST['teacher_id'];


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);


$sql = $conn->prepare("SELECT * FROM skupiny WHERE ucitel_id = :teacherID ORDER BY skupina_id DESC;");
$sql->bindParam(':teacherID',$teacherID);
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
