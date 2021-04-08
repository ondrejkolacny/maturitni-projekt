<?php
include 'DatabaseConfig.php';

try{

$array= $_POST['users_array'];
$usersarray = json_decode($array);
var_dump($usersarray);

$groupName = $_POST['group_name'];
$teacherID = $_POST['teacher_id'];
$skupinaID =0;


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);


$sql = $conn->prepare("INSERT INTO skupiny (nazev,ucitel_id) VALUES (:nazev,:teacherID)");
$sql->bindParam(':nazev',$groupName);
$sql->bindParam(':teacherID',$teacherID);

$sql->execute();

if($skupinaID == 0){
$skupinaID = $conn->lastInsertId();
}

$sql1 = $conn->prepare("INSERT INTO skupinauzivatel (user_id, skupina_id) VALUES (:userID, :skupinaID)");
foreach ($usersarray as $userID){
$sql1->bindParam(':userID',$userID);
$sql1->bindParam(':skupinaID',$skupinaID);
$sql1->execute();
}


}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
