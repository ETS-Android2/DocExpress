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
	$emp_id=$data["emp_id"];
	$emp_q="SELECT count(*) as total FROM document WHERE emp_id = $emp_id";
	$emp_q_id = oci_parse($con, $emp_q); 		
	$emp_q_r = oci_execute($emp_q_id);
	$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
	if($row)
	{
		$response['doc_total']=$row["TOTAL"];
		$response['reqcode']="2";
		
		$emp_q="SELECT count(*) as completed FROM document WHERE emp_id = $emp_id GROUP BY DOC_STATUS HAVING DOC_STATUS=1";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		$temp=$row['COMPLETED'];
		$response['doc_completed']=$temp;
		if($temp==0)
		{
			$response['doc_completed']='0';
		}
		else
		{
			$response['doc_completed']=$temp;
		}
		
		$emp_q="SELECT count(*) as ongoing FROM document WHERE emp_id = $emp_id GROUP BY DOC_STATUS HAVING DOC_STATUS=2";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		$temp=$row['ONGOING'];
		$response['doc_on_going']=$temp;
		if($temp==0)
		{
			$response['doc_on_going']='0';
		}
		else
		{
			$response['doc_on_going']=$temp;
		}
		
		
		$emp_q="SELECT count(*) as returned FROM document WHERE emp_id = $emp_id GROUP BY DOC_STATUS HAVING DOC_STATUS=3";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		$response['doc_returned']=$row["RETURNED"];
		
		$emp_q="SELECT count(*) as rejected FROM document WHERE emp_id = $emp_id GROUP BY DOC_STATUS HAVING DOC_STATUS=4";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		$response['doc_rejected']=$row["REJECTED"];

		$response2['doc'][0]=$response;
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