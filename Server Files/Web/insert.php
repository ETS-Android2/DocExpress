<?php
error_reporting(E_ALL & ~E_NOTICE & ~E_WARNING);
include "conn.php";
include "employee.php";
$response=array();

if(isset($_POST["doc_name"],$_POST["doc_start_date"],$_POST["doc_due_date"],$_POST["doc_attachment"],$_POST["emp_id"],$_POST["app_id"]))
{
	if($login==0)
	{
		$doc_name=$_POST["doc_name"];
		$doc_start_date=$_POST["doc_start_date"];
		$doc_due_date=$_POST["doc_due_date"];
		$doc_attachment=$_POST["doc_attachment"];
		$doc_status=0;
		$emp_id=$_POST["emp_id"];
		$app_id=$_POST["app_id"];
		$app_q="SELECT app_id FROM applicant WHERE app_id = '$app_id'";
		$app_q_id = oci_parse($con, $app_q); 		
		$app_q_r = oci_execute($app_q_id);
		$row = oci_fetch_array($app_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		
		if($row)
		{
			$doc_id_q="SELECT count(*) FROM document";
			$doc_id_q_id = oci_parse($con, $doc_id_q); 		
			$doc_id_q_r = oci_execute($doc_id_q_id);
			$row = oci_fetch_array($doc_id_q_id, OCI_BOTH+OCI_RETURN_NULLS);
			//echo $row['count(*)'];
			
			if($row)
			{
				$docid=$row['COUNT(*)']+800001;
				$doc_insert_q="INSERT INTO document VALUES($docid,'$doc_name',to_date('$doc_start_date','YYYY-MM-DD'),to_date('$doc_due_date','YYYY-MM-DD'),$doc_attachment,0,$emp_id,$app_id)";
				$doc_insert_q_id = oci_parse($con, $doc_insert_q); 		
				$doc_insert_q_r = oci_execute($doc_insert_q_id);
				//$row = oci_fetch_array($doc_insert_q_id, OCI_BOTH+OCI_RETURN_NULLS);
				
				if($doc_insert_q_r)
				{
					//echo "Record Inserted Successfully"."<br>";
					$response['id']=$docid;
					$response['reqmsg']="Submitted Successfully";
					$response['reqcode']="1";
				}
				else
				{
					//echo "Record Insertion Failed"."<br>";
					$response['id']="NA";
					$response['reqmsg']="Record Insertion Failed";
					$response['reqcode']="2";
				}
			}
			else
			{
				$response['id']="NA";
				$response['reqmsg']="Error in Opening this Form";
				$response['reqcode']="3";	 
			}		
		}
		else
		{
			$response['id']="NA";
			$response['reqmsg']="Applicant ID NOT Found";
			$response['reqcode']="4";		
		}
	}
	else
	{
		$response['id']="NA";
		$response['reqmsg']="Not Logged In";
		$response['reqcode']="5";
	}
}
else
{
	$response['id']="NA";
	$response['reqmsg']="Incomplete Request!";
	$response['reqcode']="0";
}
/*$doc_name="Clearence Form";
$doc_start_date="2018-12-5";
$doc_due_date="2018-12-5";
$doc_attachment=1;
$doc_status=0;
$emp_id=400001;
$app_id=900001;	
$doc_id_q="SELECT count(*) FROM document";
$doc_id_q_id = oci_parse($con, $doc_id_q); 		
$doc_id_q_r = oci_execute($doc_id_q_id);
$row = oci_fetch_array($doc_id_q_id, OCI_BOTH+OCI_RETURN_NULLS);
$docid=$row['COUNT(*)']+800001;
echo $docid."<br>";
$doc_insert_q="INSERT INTO document VALUES($docid,'$doc_name',to_date('$doc_start_date','YYYY-MM-DD'),to_date('$doc_due_date','YYYY-MM-DD'),$doc_attachment,0,$emp_id,$app_id)";
$doc_insert_q_id = oci_parse($con, $doc_insert_q); 		
$doc_insert_q_r = oci_execute($doc_insert_q_id);
//$row = oci_fetch_array($doc_insert_q_id, OCI_BOTH+OCI_RETURN_NULLS);
if($doc_insert_q_r)
{
	echo "Record Inserted Successfully"."<br>";
}*/
$x=json_encode($response);
echo $x;
oci_close($con);

if($con)
{
	//echo "Oracle Connection Closed";
}
?>