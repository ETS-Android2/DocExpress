<?php
$filefolder = "pdf/";
$reponse3=array();
$data = json_decode(file_get_contents("php://input"), true);
//if($data)
//{
	$filename1=$data["doc_id"];
	//$filename=$filefolder.$filename1.".pdf";
	$filename="pdf/800007.pdf";

	if(file_exists($filename)){

		//Get file type and set it as Content Type
		$finfo = finfo_open(FILEINFO_MIME_TYPE);
		header('Content-Type: ' . finfo_file($finfo, $filename));
		finfo_close($finfo);

		//Use Content-Disposition: attachment to specify the filename
		header('Content-Disposition: attachment; filename='.basename($filename));

		//No cache
		header('Expires: 0');
		header('Cache-Control: must-revalidate');
		header('Pragma: public');

		//Define file size
		header('Content-Length: ' . filesize($filename));

		ob_clean();
		flush();
		readfile($filename);
		exit;
	}
//}
?>