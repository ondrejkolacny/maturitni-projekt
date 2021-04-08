<?php
include 'DatabaseConfig.php';

try{

$planID = $_POST['plan_id'];
// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);


$sql = $conn->prepare("SELECT plany.strany AS pagesToRead, SEC_TO_TIME(SUM(TIME_TO_SEC(plany.doba))) AS timeToRead , users.jmeno, COALESCE(SUM(progress.strany),0)AS pagesRead,COALESCE(SEC_TO_TIME(SUM(TIME_TO_SEC(progress.doba))),'0:00:00') AS timeRead
FROM users
INNER JOIN skupinauzivatel ON (users.user_id = skupinauzivatel.user_id)
INNER JOIN skupiny ON (skupinauzivatel.skupina_id = skupiny.skupina_id)
INNER JOIN plany ON (plany.skupina_id = skupiny.skupina_id)
LEFT JOIN progress ON (users.user_id = progress.user_id)
WHERE plany.plan_id = :planID
GROUP BY users.user_id
ORDER BY pagesToRead DESC;
");

$sql->bindParam(':planID',$planID);
$sql->execute();


$rows=array();
while($row=$sql->fetch(PDO::FETCH_ASSOC)){
  
$timePlan = explode(':',$row['timeToRead']);
$timeDone= explode(':',$row['timeRead']);
 
$timePerc= round((($timeDone[0]*60+$timeDone[1])/(($timePlan[0]*60+$timePlan[1])))*100);
$pagesPerc = round(($row['pagesRead'])/($row['pagesToRead'])*100);
if($timePerc>=$pagesPerc){$finalPerc = $timePerc;}
else{$finalPerc = $pagesPerc;}

$user = array('username'=>$row['jmeno'],'percent'=>$finalPerc."%");
$rows[]=$user;

}
echo json_encode($rows);

}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
