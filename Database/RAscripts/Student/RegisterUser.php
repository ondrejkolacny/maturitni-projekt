<?php
include 'DatabaseConfig.php';

try{


// Create connection
$conn = new PDO("mysql:host=$HostName;dbname=$DatabaseName", $HostUser, $HostPass );

$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
$name = $_POST['jmeno'];
$email = $_POST['email'];
$password = $_POST['heslo'];
$passwordHashed = password_hash($password,PASSWORD_DEFAULT);

$sql = $conn->prepare("SELECT * FROM users WHERE email=:email");
$sql->bindParam(':email',$email);
$sql->execute();



if($sql->fetchColumn() > 0){  
	           echo json_encode(array( "status" => "false","message" => "Email already used!") );
			exit();
	        }else{ 
		 	$query = $conn->prepare("INSERT INTO users (jmeno,email,heslo) VALUES (:name, :email, :password)");

			$query->bindParam(':name',$name);
			$query->bindParam(':email',$email);
			$query->bindParam(':password',$passwordHashed);
			
			$query->execute();
			$userID = $conn->lastInsertId();

			$validateloginsql = $conn->prepare("INSERT INTO validloginsstudent (user_id, random_number) VALUES (:user_id,:randomNumber);");
			
			$validateloginsql->bindParam(':user_id',$userID);
			$validateloginsql->bindParam(':randomNumber',$randomNumber);
			
			$randomNumber = mt_rand();
			$validateloginsql->execute();
		
			echo json_encode(array(
        		"status" => "success",
        		"message" => "Account has been created",
        		"user_id" => $userID,
			"random_number" => $randomNumber
    ));
 

		}
			
}catch(PDOException $ex){
	die($ex->getMessage());
}


$conn = null; 

?>
