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
		$emp_q="SELECT emp_id,emp_f_name ||' '|| emp_l_name as emp_name,emp_rank FROM employee_n WHERE emp_id = $emp_id AND password='$pass'";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		
		if($row)
		{
			$emp_id=$row['EMP_ID'];
			$id=$emp_id;
			$emp_pass=$row['PASSWORD'];
			$response['id']=$row['EMP_ID'];
			$response['emp_name']=$row['EMP_NAME'];
			$response['emp_job']=$row['EMP_RANK'];
			$response['role']='employee';
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
		$emp_q="SELECT app_id FROM applicant WHERE app_email = '$email' ";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		
		if($row)
		{
			$emp_id=$row['APP_ID'];
			$emp_q="SELECT app_id,app_f_name ||' '|| app_l_name as app_name FROM applicant WHERE app_id = $emp_id AND password='$pass'";
			$emp_q_id = oci_parse($con, $emp_q); 		
			$emp_q_r = oci_execute($emp_q_id);
			$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
			
			if($row)
			{
				$emp_id=$row['APP_ID'];
				$id=$emp_id;
				$emp_pass=$row['PASSWORD'];
				$response['id']=$row['APP_ID'];
				$response['emp_name']=$row['APP_NAME'];
				$response['emp_job']='Applicant';
				$response['role']='Applicant';
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
}
$x=json_encode($response);
echo $x;
oci_close($con);
if($con)
{
	//echo "Oracle Connection Closed";
}
?>