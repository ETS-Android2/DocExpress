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
	$empid=$data["id"];
	$emp_q="SELECT * FROM document_type";
	$emp_q_id = oci_parse($con, $emp_q); 		
	$emp_q_r = oci_execute($emp_q_id);
	$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
	$inc=1;
	if($row)
	{
		$response['reqmsg']="Documents Names Found";
		$response['reqcode']="2";
		$response2['doc'][0]=$response;
		do
		{
			$reponse3=array();
			$response3['doc_name']=$row['DOC_NAME'];
			$response2[doc][$inc]=$response3;
			$inc=$inc+1;
		}
		while($row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS));
	}
	else
	{
		$response['reqmsg']="No Douments Names Found";
		$response['reqcode']="1";
		$response2['doc'][0]=$response;
	}
}
$response2['doc'][0]['doc_numbers']=$inc-1;
$x=json_encode($response2);
header('Content-Type: application/json; charset=utf-8');
echo $x;
oci_close($con);
?>