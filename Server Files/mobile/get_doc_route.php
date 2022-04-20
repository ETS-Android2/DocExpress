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
	$emp_id=$data["emp_id"];;
	$app_id;
	$doc_id;
	$emp_q="SELECT * FROM document_route WHERE doc_name = '$doc_name' AND emp_id != $emp_id ORDER BY doc_step_no";
	$emp_q_id = oci_parse($con, $emp_q); 		
	$emp_q_r = oci_execute($emp_q_id);
	$inc=1;
	$row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS);
	if($row)
	{
		$response['reqmsg']="Route Found";
		$response['reqcode']="2";
		$response2['doc'][0]=$response;
		do
		{
			$reponse3=array();
			$response3['doc_step_no']=$row['DOC_STEP_NO'];
			$response3['doc_name']=$row['DOC_NAME'];
			$emp_id_cur=$row['EMP_ID'];
			$response3['emp_id']=$emp_id_cur;
			$emp_q2="select emp_f_name ||' '|| emp_l_name as emp_name,emp_rank,dept_id from employee_n WHERE emp_id = $emp_id_cur";
			$emp_q_id2 = oci_parse($con, $emp_q2); 		
			$emp_q_r2 = oci_execute($emp_q_id2);
			$row2 = oci_fetch_array($emp_q_id2, OCI_BOTH+OCI_RETURN_NULLS);
			$response3['emp_name']=$row2['EMP_NAME'];
			$response3['emp_rank']=$row2['EMP_RANK'];
			$dept_id_cur=$row2['DEPT_ID'];
			$emp_q2="select dept_name from department WHERE dept_id = $dept_id_cur";
			$emp_q_id2 = oci_parse($con, $emp_q2); 		
			$emp_q_r2 = oci_execute($emp_q_id2);
			$row2 = oci_fetch_array($emp_q_id2, OCI_BOTH+OCI_RETURN_NULLS);
			$response3['dept_name']=$row2['DEPT_NAME'];
			$response2[doc][$inc]=$response3;
			$inc=$inc+1;
		}
		while($row = oci_fetch_array($emp_q_id, OCI_BOTH+OCI_RETURN_NULLS));
	}
	else
	{
		$response['reqmsg']="No Route Found";
		$response['reqcode']="1";
		$response2['doc'][0]=$response;
	}
}
$x=json_encode($response2);
header('Content-Type: application/json; charset=utf-8');
echo $x;
oci_close($con);
?>