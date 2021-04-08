<?php
include 'DatabaseConfig.php';

try{


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
$userID = $_POST['user_id'];

$odznakysql = $conn->prepare("SELECT odznakuzivatel.odznak_id FROM odznakuzivatel WHERE user_id = :userID;
");

$odznakysql->bindParam(':userID',$userID);
$odznakysql->execute();

$odznaky = array();


while($row=$odznakysql->fetch(PDO::FETCH_ASSOC)){
  
      array_push($odznaky,$row['odznak_id']);
 

}
$profilesql = $conn->prepare("SELECT users.jmeno, users.email, COALESCE(SUM(progress.strany),0) AS strany_celkem, COALESCE(SEC_TO_TIME(SUM(TIME_TO_SEC(progress.doba))),'0:00:00') AS doba_celkem 
FROM users 
LEFT JOIN progress ON (progress.user_id = users.user_id)
WHERE users.user_id = :userID
");
$profilesql->bindParam(':userID',$userID);
$profilesql->execute();

$row   = $profilesql->fetch(PDO::FETCH_ASSOC);

$minutes = explode(':',$row['doba_celkem']);
$level = floor((-30+sqrt(900+20*($minutes[0]*60+$minutes[1])))/10);
if($level>16){$level = 16;}

echo json_encode(array("odznaky"=>$odznaky,"jmeno"=>$row['jmeno'],"email"=>$row['email'],"strany"=>$row['strany_celkem'], "doba"=>substr($row['doba_celkem'],0,-3),"level"=>$level)); 




}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
