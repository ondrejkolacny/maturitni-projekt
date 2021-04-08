<?php
include 'DatabaseConfig.php';

try{


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);


$sql = $conn->prepare("SELECT plany.*, skupiny.nazev AS skupina_nazev, COALESCE(SUM(progress.strany),0) AS pagesRead
FROM plany 
INNER JOIN  skupinauzivatel ON (plany.skupina_id=skupinauzivatel.skupina_id)
INNER JOIN skupiny ON (skupinauzivatel.skupina_id = skupiny.skupina_id)
LEFT JOIN progress ON (plany.plan_id = progress.plan_id)
WHERE skupinauzivatel.user_id =:userID
GROUP BY plany.plan_id
HAVING SUM(progress.strany) < plany.strany OR COUNT(progress.plan_id)=0
ORDER BY plany.plan_id DESC
;
");
$userID = $_POST['user_id'];
$sql->bindParam(':userID',$userID);
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
