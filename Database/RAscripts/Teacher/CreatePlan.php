<?php
include 'DatabaseConfig.php';

try{


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

$skupinaID = $_POST['skupina_id'];
$nazev = $_POST['nazev'];
$instrukce = $_POST['instrukce'];
$strany = $_POST['strany'];
$doba = $_POST['doba'];


$query = $conn->prepare("INSERT INTO plany (nazev,popis,strany,doba, skupina_id, datum_zadani) VALUES (:nazev, :popis, :strany, :doba, :skupinaID, CURRENT_DATE)");

$query->bindParam(':nazev',$nazev);
$query->bindParam(':popis',$instrukce);
$query->bindParam(':strany',$strany);
$query->bindParam(':doba',$doba);
$query->bindParam(':skupinaID',$skupinaID);
			
$query->execute();
		
 


			
}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
