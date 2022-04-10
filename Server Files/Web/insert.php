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
			$doc_id_q="SELECT TO_CHAR(SYSDATE,'DD-MM-YYYY') as cdate FROM dual";
			$doc_id_q_id = oci_parse($con, $doc_id_q); 		
			$doc_id_q_r = oci_execute($doc_id_q_id);
			$row = oci_fetch_array($doc_id_q_id, OCI_BOTH+OCI_RETURN_NULLS);
			$cdate=$row['CDATE'];
			$doc_insert_q="INSERT INTO document VALUES($docid,'$doc_name',to_date('$cdate','DD-MM-YYYY'),to_date('$doc_due_date','DD-MM-YYYY'),$doc_attachment,$doc_status,$emp_id,$app_id)";
			$doc_insert_q_id = oci_parse($con, $doc_insert_q); 		
			$doc_insert_q_r = oci_execute($doc_insert_q_id);
			if($doc_insert_q_r)
			{
				$response['id']=$docid;
				$response['reqmsg']="Submitted Successfully";
				$response['reqcode']="2";
				$response2['doc'][0]=$response;
				
				$response=array();
				$emp_q="SELECT * FROM document WHERE doc_id = $docid";
				$emp_q_id = oci_parse($con, $emp_q); 		
				$emp_q_r = oci_execute($emp_q_id);
				$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
				$emp_id=$row["EMP_ID"];
				$app_id=$row["APP_ID"];
				$response['doc_id']=$row['DOC_ID'];
				$response['doc_name']=$row['DOC_NAME'];
				$response['doc_start_date']=$row['DOC_START_DATE'];
				$response['doc_due_date']=$row['DOC_DUE_DATE'];
				$response['doc_attachment']=$row['DOC_ATTACHMENT'];
				$response['doc_status']=$row['DOC_STATUS'];
				$response['emp_id']=$row['EMP_ID'];
				$response['app_id']=$row['APP_ID'];
				
				$emp_q="select emp_f_name ||' '|| emp_l_name as emp_name,dept_id from employee_n WHERE emp_id = $emp_id";
				$emp_q_id = oci_parse($con, $emp_q); 		
				$emp_q_r = oci_execute($emp_q_id);
				$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
				$response['emp_name']=$row['EMP_NAME'];
				$dept_id=$row['DEPT_ID'];
				
				$emp_q="SELECT dept_name FROM department WHERE dept_id = $dept_id";
				$emp_q_id = oci_parse($con, $emp_q); 		
				$emp_q_r = oci_execute($emp_q_id);
				$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
				$response['dept_name']=$row['DEPT_NAME'];
				
				$emp_q="select app_f_name ||' '|| app_l_name as app_name from applicant WHERE app_id = $app_id";
				$emp_q_id = oci_parse($con, $emp_q); 		
				$emp_q_r = oci_execute($emp_q_id);
				$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
				$response['app_name']=$row['APP_NAME'];
				$response2['doc'][1]=$response;
			}
			else
			{
				$response['id']="NA";
				$response['reqmsg']="Record Insertion Failed1";
				$response['reqcode']="3";
				$response2['doc'][0]=$response;
			}
		}
		else
		{
				$response['id']="NA";
				$response['reqmsg']="Record Insertion Failed2";
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