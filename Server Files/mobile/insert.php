<?php
error_reporting(E_ALL & ~E_NOTICE & ~E_WARNING);
include "conn.php";
$response=array();
$reponse2=array();
$reponse2['doc']=[array()];
$reponse3=array();
$data = json_decode(file_get_contents("php://input"), true);
if($data)
{
	$doc_name=$data["doc_name"];
	$doc_start_date=$data["doc_start_date"];
	$doc_due_date=$data["doc_due_date"];
	$doc_attachment=$data["doc_attachment"];
	$doc_status=$data["doc_status"];
	$emp_id=$data["emp_id"];
	$app_id=$data["app_id"];
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
		if($row)
		{
			$docid=$row['COUNT(*)']+800001;
			$doc_insert_q="INSERT INTO document VALUES($docid,'$doc_name',to_date('$doc_start_date','DD-MM-YYYY'),to_date('$doc_due_date','DD-MM-YYYY'),$doc_attachment,$doc_status,$emp_id,$app_id)";
			$doc_insert_q_id = oci_parse($con, $doc_insert_q); 		
			$doc_insert_q_r = oci_execute($doc_insert_q_id);
			if($doc_insert_q_r)
			{
				$response['id']=$docid;
				$response['reqmsg']="Submitted Successfully";
				$response['reqcode']="2";
				$response2['doc'][0]=$response;
			}
			else
			{
				$response['id']="NA";
				$response['reqmsg']="Record Insertion Failed";
				$response['reqcode']="3";
				$response2['doc'][0]=$response;
			}
		}
		else
		{
				$response['id']="NA";
				$response['reqmsg']="Record Insertion Failed";
				$response['reqcode']="3";
				$response2['doc'][0]=$response;	 
		}		
	}
	else
	{
		
		$response['reqmsg']="Applicant ID Not Found";
		$response['reqcode']="1";
		$response2['doc'][0]=$response;		
	}
}
$x=json_encode($response2);
header('Content-Type: application/json; charset=utf-8');
echo $x;
oci_close($con);
?>