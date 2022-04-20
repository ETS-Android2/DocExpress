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
	$emp_q="SELECT * FROM document WHERE doc_status=$docstatus ORDER BY doc_id";
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
			$doc_id_cur=$row['DOC_ID'];
			$emp_q2="SELECT COUNT(*) as cur_record FROM document_status WHERE doc_id = $doc_id_cur";
			$emp_q_id2 = oci_parse($con, $emp_q2); 		
			$emp_q_r2 = oci_execute($emp_q_id2);
			$row2 = oci_fetch_array($emp_q_id2, OCI_BOTH+OCI_RETURN_NULLS);
			if($row2)
			{
				$doc_ser_no_cur=$row2['CUR_RECORD'];
				$emp_q2="SELECT * FROM document_status WHERE doc_id = $doc_id_cur AND (docst_serial_no=$doc_ser_no_cur AND emp_id_receiver=$empid)";
				$emp_q_id2 = oci_parse($con, $emp_q2); 		
				$emp_q_r2 = oci_execute($emp_q_id2);
				$row2 = oci_fetch_array($emp_q_id2, OCI_BOTH+OCI_RETURN_NULLS);
				if($row2)
				{
					$response3['rec_date']=$row2['RECEIVED_DATE'];
					$response3['sender_note']=$row2['COMMENTS'];
					$sender_emp_id_cur=$row2['EMP_ID_SENDER'];
					$response3['sender_emp_id']=$sender_emp_id_cur;
					$emp_q3="select emp_f_name ||' '|| emp_l_name as emp_name,emp_rank,dept_id from employee_n WHERE emp_id = $sender_emp_id_cur";
					$emp_q_id3 = oci_parse($con, $emp_q3); 		
					$emp_q_r3 = oci_execute($emp_q_id3);
					$row3 = oci_fetch_array($emp_q_id3, OCI_BOTH+OCI_RETURN_NULLS);
					$response3['sender_emp_name']=$row3['EMP_NAME'];
					$response3['sender_emp_rank']=$row3['EMP_RANK'];
					$dept_id_cur=$row3['DEPT_ID'];
					$response3['sender_dept_id']=$dept_id_cur;
					$emp_q3="select dept_name from department WHERE dept_id = $dept_id_cur";
					$emp_q_id3 = oci_parse($con, $emp_q3); 		
					$emp_q_r3 = oci_execute($emp_q_id3);
					$row3 = oci_fetch_array($emp_q_id3, OCI_BOTH+OCI_RETURN_NULLS);
					$response3['sender_dept_name']=$row3['DEPT_NAME'];
					$emp_q3="select * from document WHERE doc_id = $doc_id_cur";
					$emp_q_id3 = oci_parse($con, $emp_q3); 		
					$emp_q_r3 = oci_execute($emp_q_id3);
					$row3 = oci_fetch_array($emp_q_id3, OCI_BOTH+OCI_RETURN_NULLS);
					$response3['doc_id']=$row3['DOC_ID'];
					$response3['doc_name']=$row3['DOC_NAME'];
					$response3['doc_start_date']=$row3['DOC_START_DATE'];
					$response3['doc_due_date']=$row3['DOC_DUE_DATE'];
					$response3['doc_attachment']=$row3['DOC_ATTACHMENT'];
					$response3['doc_status']=$row3['DOC_STATUS'];
					$starting_emp_id_cur=$row3['EMP_ID'];		
					$response3['starting_emp_id']=$starting_emp_id_cur;
					$app_id_cur=$row3['APP_ID'];		
					$response3['app_id']=$app_id_cur;
					$emp_q3="select emp_f_name ||' '|| emp_l_name as emp_name,emp_rank,dept_id from employee_n WHERE emp_id = $starting_emp_id_cur";
					$emp_q_id3 = oci_parse($con, $emp_q3); 		
					$emp_q_r3 = oci_execute($emp_q_id3);
					$row3 = oci_fetch_array($emp_q_id3, OCI_BOTH+OCI_RETURN_NULLS);
					$response3['starting_emp_name']=$row3['EMP_NAME'];
					$response3['starting_emp_rank']=$row3['EMP_RANK'];
					$dept_id_cur=$row3['DEPT_ID'];
					$response3['starting_dept_id']=$dept_id_cur;
					$emp_q3="select dept_name from department WHERE dept_id = $dept_id_cur";
					$emp_q_id3 = oci_parse($con, $emp_q3); 		
					$emp_q_r3 = oci_execute($emp_q_id3);
					$row3 = oci_fetch_array($emp_q_id3, OCI_BOTH+OCI_RETURN_NULLS);
					$response3['starting_dept_name']=$row3['DEPT_NAME'];
					$emp_q3="select app_f_name ||' '|| app_l_name as app_name from applicant WHERE app_id = $app_id_cur";
					$emp_q_id3 = oci_parse($con, $emp_q3); 		
					$emp_q_r3 = oci_execute($emp_q_id3);
					$row3 = oci_fetch_array($emp_q_id3, OCI_BOTH+OCI_RETURN_NULLS);
					$response3['app_name']=$row3['APP_NAME'];
					$response2[doc][$inc]=$response3;
					$inc=$inc+1;				
				}
			}
		}
		while($row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS));
	}
	else
	{
		$response['reqmsg']="No Applications Found";
		$response['reqcode']="1";
		$response2['doc'][0]=$response;
	}
	if($inc==1)
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