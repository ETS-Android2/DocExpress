<?php
error_reporting(E_ALL & ~E_NOTICE & ~E_WARNING);
$emp_id;
$emp_pass;
include "conn.php";
include "employee.php";
$response=array();

if(isset($_POST["email"],$_POST["pass"]))
{
	$email=$_POST["email"];
	$pass=$_POST["pass"]; 
	$emp_q="SELECT emp_id FROM employee_n WHERE emp_email = '$email' ";
	$emp_q_id = oci_parse($con, $emp_q); 		
	$emp_q_r = oci_execute($emp_q_id);
	$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
	
	if($row)
	{
		$emp_id=$row['EMP_ID'];
		$emp_q="SELECT * FROM employee_n WHERE emp_id = $emp_id AND password='$pass'";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		
		if($row)
		{
			$emp_id=$row['EMP_ID'];
			$id=$emp_id;
			$emp_pass=$row['PASSWORD'];
			$response['id']=$row['EMP_ID'];;
			$response['reqmsg']="Successfully Logged In";
			$response['reqcode']="1";
		}
		else
		{
			$response['id']="NA";
			$response['reqmsg']="Incorrect Password";
			$response['reqcode']="2";
		}
	}
	else
	{
		$response['id']="NA";
		$response['reqmsg']="Email Does not Exist";
		$response['reqcode']="3"; 
	}
}
$x=json_encode($response);
echo $x;
oci_close($con);
if($con)
{
	//echo "Oracle Connection Closed";
}
?>