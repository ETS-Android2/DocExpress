<?php
error_reporting(E_ALL & ~E_NOTICE & ~E_WARNING);
include "conn.php";
$response=array();
$reponse2=array();
$reponse2['doc']=[array()];
$reponse3=array();
$doc_id_min=799999;
$doc_id_max=900000;
//header("Access-Control-Allow-Origin: *");
$data = json_decode(file_get_contents("php://input"), true);

//if(isset($_POST["doc_id"]))
if($data)
{
	$docid=$data["doc_id"];
	if($docid>$doc_id_min and $docid<$doc_id_max)
	{
		$emp_id;
		$app_id;
		$doc_id;
		$dept_id;
		$emp_q="SELECT * FROM document WHERE doc_id = $docid";
		$emp_q_id = oci_parse($con, $emp_q); 		
		$emp_q_r = oci_execute($emp_q_id);
		$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
		
		if($row)
		{
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
			$response['reqcode']="1";
			
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
			$response['reqmsg']="Documen Found";
			//$response2={"doc":[{}]}
			//$response2[0]=$response;
			$response2['doc'][0]=$response;
			
			$doc_q="select * from document_status WHERE doc_id = $docid";
			$doc_q_id = oci_parse($con, $doc_q); 		
			$doc_q_r = oci_execute($doc_q_id);
			//$row = oci_fetch_array($doc_q_id, OCI_BOTH+OCI_RETURN_NULLS); 
			$inc=1;
            while($row = oci_fetch_array($doc_q_id, OCI_BOTH+OCI_RETURN_NULLS))
            {
				$reponse3=array();
				$response3['step']=$row['DOCST_SERIAL_NO'];
				$response3['rec_date']=$row['RECEIVED_DATE'];
				$response3['emp_id']=$row['EMP_ID'];
				$response3['comments']=$row['COMMENTS'];
				//$response2[$inc]=$response3;
				$response2[doc][$inc]=$response3;
				$inc=$inc+1;
            }
		}
		else
		{
			$response['reqmsg']="Document Not Found";
			$response['reqcode']="2";
			$response2['doc'][0]=$response;
		}
	}
	else
	{
		$response['reqmsg']="Document ID Format Not Correct";
		$response['reqcode']="3";
		$response2['doc'][0]=$response;
	}
}
$x=json_encode($response2);
header('Content-Type: application/json; charset=utf-8');
echo $x;
oci_close($con);

?>