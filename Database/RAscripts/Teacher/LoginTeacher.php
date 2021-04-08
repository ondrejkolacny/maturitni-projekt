<?php
include 'DatabaseConfig.php';

try{


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

$email = $_POST['user'];
$password = $_POST['password'];

$sql = $conn->prepare("SELECT * FROM teachers WHERE (email = :email)");
$sql->bindParam(':email',$email);
$sql->execute();



$row   = $sql->fetch(PDO::FETCH_ASSOC);
if($sql->rowCount()>0){
	if(password_verify($password,$row['heslo'])){
		$randomNumber = mt_rand(); 
	           
		$validateloginsql = $conn->prepare("INSERT INTO validloginsteacher (user_id, random_number) VALUES (:user_id,:randomNumber);");
			
			$validateloginsql->bindParam(':user_id',$row['teacher_id']);
			$validateloginsql->bindParam(':randomNumber',$randomNumber);
			
			$validateloginsql->execute();
echo json_encode(array( "status" => "success","message" => "Logged","user_id"=>$row['teacher_id'],"random_number"=>$randomNumber) );



}else{echo json_encode(array( "status" => "false") );}
	        }else{ 
		 	echo json_encode(array( "status" => "false") );

}
			
}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
