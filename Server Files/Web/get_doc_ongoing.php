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
	$empid=$data["emp_id"];
	$emp_id;
	$app_id;
	$doc_id;
	$docstatus=2;
	$emp_q="SELECT * FROM document WHERE emp_id = $empid AND doc_status=$docstatus ORDER BY doc_id";
	$emp_q_id = oci_parse($con, $emp_q); 		
	$emp_q_r = oci_execute($emp_q_id);
	$inc=1;
	$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
	if($row)
	{
		$response['reqmsg']="Applications Found";
		$response['reqcode']="2";
		$response2['doc'][0]=$response;
		do
		{
			$reponse3=array();
			$response3['doc_id']=$row['DOC_ID'];
			$response3['doc_name']=$row['DOC_NAME'];
			$response3['start_date']=$row['DOC_START_DATE'];
			$response3['end_date']=$row['DOC_DUE_DATE'];
			$response3['app_id']=$row['APP_ID'];
			$app_id_cur=$row['APP_ID'];
			$emp_q2="select app_f_name ||' '|| app_l_name as app_name from applicant WHERE app_id = $app_id_cur";
			$emp_q_id2 = oci_parse($con, $emp_q2); 		
			$emp_q_r2 = oci_execute($emp_q_id2);
			$row2 = oci_fetch_array($emp_q_id2, OCI_BOTH+OCI_RETURN_NULLS);
			$response3['app_name']=$row2['APP_NAME'];
			$response2[doc][$inc]=$response3;
			$inc=$inc+1;
		}
		while($row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS));
	}
	else
	{
		$response['reqmsg']="No Applications Found";
		$response['reqcode']="1";
		$response2['doc'][0]=$response;
	}
}
$x=json_encode($response2);
header('Content-Type: application/json; charset=utf-8');
echo $x;
oci_close($con);
?>