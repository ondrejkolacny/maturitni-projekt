<?php
include 'DatabaseConfig.php';

try{
$planID = $_POST['plan_id'];
$userID = $_POST['user_id'];
$pages = $_POST['pages_read'];
$time = $_POST ['time_reading'];

$plandone = 0;
$rewardsgained = 0;


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

//Příkaz k vložení nově získané odměny

$novaodmena = $conn->prepare("INSERT INTO odznakuzivatel (user_id, odznak_id) VALUES (:userID,:odznakID);"); 


$query = $conn->prepare("INSERT INTO progress(user_id, plan_id, strany, doba, datum_zapisu) VALUES (:userID,:planID,:pages,:time, CURRENT_DATE());");

$query->bindParam(':userID',$userID);
$query->bindParam(':planID',$planID);
$query->bindParam(':pages',$pages);
$query->bindParam(':time',$time);
			
$query->execute();
		

//získám odznaky		
$odznakysql = $conn->prepare("SELECT odznakuzivatel.odznak_id FROM odznakuzivatel WHERE user_id = :userID;
");

$odznakysql->bindParam(':userID',$userID);
$odznakysql->execute();

$odznaky = array();


while($row=$odznakysql->fetch(PDO::FETCH_ASSOC)){
  
      array_push($odznaky,$row['odznak_id']);
 

}


//Zkontroluju jestli je plán splněný

$isplandonesql = $conn->prepare("SELECT plany.*,COALESCE(SUM(progress.strany),0) AS stranycelkem, COALESCE(SEC_TO_TIME(SUM(TIME_TO_SEC(progress.doba))),'0:00:00') AS doba_celkem, CURRENT_DATE
FROM plany
INNER JOIN progress ON (progress.plan_id = plany.plan_id)
WHERE progress.user_id = :userID AND progress.plan_id =:planID
GROUP BY plan_id
HAVING SUM(progress.strany)>=(plany.strany);
");

$isplandonesql->bindParam(':userID',$userID);
$isplandonesql->bindParam(':planID',$planID);
$isplandonesql->execute();

$row   = $isplandonesql->fetch(PDO::FETCH_ASSOC);
if($isplandonesql->rowCount() ==1){ 
	           $plandone =1;
			
//Odměna: více stran přečteno než zadáno ID2
			if(!in_array(2, $odznaky)){
				if($row['stranycelkem']>$row['strany']){
				array_push($odznaky,"2");
				$odznakID = 2;
				$novaodmena->bindParam(':userID',$userID);
				$novaodmena->bindParam(':odznakID',$odznakID);
				$novaodmena->execute();
				$rewardsgained = 1;

}
}
//Odměna: více stran přečteno za kratší dobu ID3
			if(!in_array(3, $odznaky)){
				if($row['doba_celkem']>$row['doba'] && $row['stranycelkem']>$row['strany'] ){
				array_push($odznaky,"3");
				$odznakID = 3;
				$novaodmena->bindParam(':userID',$userID);
				$novaodmena->bindParam(':odznakID',$odznakID);
				$novaodmena->execute();	
				$rewardsgained = 1;
				}
}
//Odměna: splnit plán ještě v den zadání ID5
if(!in_array(5, $odznaky)){
	if($row['datum_zadani']==$row['CURRENT_DATE']){
		array_push($odznaky,"5");
		$odznakID = 5;
		$novaodmena->bindParam(':userID',$userID);
		$novaodmena->bindParam(':odznakID',$odznakID);
		$novaodmena->execute();
		$rewardsgained = 1;	
}

}
		
			
}else{ 
$plandone = 0;

}

//kontrola celkového počtu plánů

if(!in_array(1, $odznaky)||!in_array(10, $odznaky)){
$doneplanssql = $conn->prepare("SELECT plany.* 
FROM plany
INNER JOIN progress ON (progress.plan_id = plany.plan_id)
WHERE progress.user_id = :userID
GROUP BY plan_id
HAVING SUM(progress.strany)>=(plany.strany);
");

$isplandonesql->bindParam(':userID',$userID);
$isplandonesql->execute();

//Odměna: první přečtený plán ID1
$row   = $isplandonesql->fetch(PDO::FETCH_ASSOC);
if($isplandonesql->rowCount() ==1 AND !in_array(1, $odznaky)){ 
	array_push($odznaky,"1");
	$odznakID = 1;
	$novaodmena->bindParam(':userID',$userID);
	$novaodmena->bindParam(':odznakID',$odznakID);
	$novaodmena->execute();	
	$rewardsgained = 1;
		
//Odměna: deset splněných plánů ID6
}else if ($isplandonesql->rowCount() ==10 AND !in_array(1, $odznaky)){ 
	array_push($odznaky,"6");
	$odznakID = 6;
	$novaodmena->bindParam(':userID',$userID);
	$novaodmena->bindParam(':odznakID',$odznakID);
	$novaodmena->execute();	
	$rewardsgained = 1;

}
	
}

//kontrola pouze záznamů

if(!in_array(0, $odznaky)||!in_array(4, $odznaky)||!in_array(5, $odznaky)){
$progresssql = $conn->prepare("SELECT DISTINCT plan_id FROM progress WHERE user_id =:userID");
$progresssql->bindParam(':userID',$userID);
$progresssql->execute();
$row   = $progresssql->fetch(PDO::FETCH_ASSOC);

//Odměna: první záznam ID0
if($progresssql->rowCount() ==1 AND !in_array(0, $odznaky)){ 
	array_push($odznaky,"0");
	$odznakID = 0;
	$novaodmena->bindParam(':userID',$userID);
	$novaodmena->bindParam(':odznakID',$odznakID);
	$novaodmena->execute();
	$rewardsgained = 1;	
}		
//Odměna: více různých záznamů ID4
else if($progresssql->rowCount() >1 AND !in_array(4, $odznaky)){
	array_push($odznaky,"4");
	$odznakID = 4;
	$novaodmena->bindParam(':userID',$userID);
	$novaodmena->bindParam(':odznakID',$odznakID);
	$novaodmena->execute();
	$rewardsgained = 1;	
}

}

echo json_encode(array("plan_done"=>$plandone,"rewards_gained"=>$rewardsgained));
		
			
}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
