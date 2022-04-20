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
	$emp_id_sender=$data["emp_id_sender"];
	$emp_id_receiver=$data["emp_id_receiver"];
	$comments=$data["comments"];
	$doc_id_q="SELECT count(*) FROM document_status WHERE doc_id=$doc_id";
	$doc_id_q_id = oci_parse($con, $doc_id_q); 		
	$doc_id_q_r = oci_execute($doc_id_q_id);
	$row = oci_fetch_array($doc_id_q_id, OCI_BOTH+OCI_RETURN_NULLS);
	if($row)
	{
		$docid=$row['COUNT(*)']+1;
		$doc_id_q="SELECT TO_CHAR(SYSDATE,'DD-MM-YYYY') as cdate FROM dual";
		$doc_id_q_id = oci_parse($con, $doc_id_q); 		
		$doc_id_q_r = oci_execute($doc_id_q_id);
		$row = oci_fetch_array($doc_id_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		$cdate=$row['CDATE'];
		$doc_insert_q="INSERT INTO document_status VALUES($docid,$doc_id,to_date('$cdate','DD-MM-YYYY'),$emp_id_sender,$emp_id_receiver,'$comments')";
		$doc_insert_q_id = oci_parse($con, $doc_insert_q); 		
		$doc_insert_q_r = oci_execute($doc_insert_q_id);
		if($doc_insert_q_r)
		{
			$response['id']="Successfull";
			$response['reqmsg']="Document Sent Successfully";
			$response['reqcode']="2";
			$response2['doc'][0]=$response;
			
		}
		else
		{
			$response['id']="NA";
			$response['reqmsg']="Document Sending Failed";
			$response['reqcode']="3";
			$response2['doc'][0]=$response;
		}
	}
	else
	{
			$response['id']="NA";
			$response['reqmsg']="Document Sending Failed2";
			$response['reqcode']="3";
			$response2['doc'][0]=$response;	 
	}		
}
$x=json_encode($response2);
header('Content-Type: application/json; charset=utf-8');
echo $x;
oci_close($con);
?>