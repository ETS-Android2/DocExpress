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
	$doc_id=$data["doc_id"];
	$docstatus=1;
	$emp_q="UPDATE document SET doc_status=$docstatus WHERE doc_id = $doc_id";
	$emp_q_id = oci_parse($con, $emp_q);
	$row = oci_execute($emp_q_id, OCI_COMMIT_ON_SUCCESS);
	if($row)
	{
		$response['reqmsg']="Document has been Marked Completed";
		$response['reqcode']="2";
		$response2['doc'][0]=$response;	
	}
	else
	{
		$response['reqmsg']="Could Not Update Document Status";
		$response['reqcode']="1";
		$response2['doc'][0]=$response;
	}
}
$x=json_encode($response2);
header('Content-Type: application/json; charset=utf-8');
echo $x;
oci_close($con);
?>