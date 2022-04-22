<?php
$PdfUploadFolder = 'pdf/'; 
if($_SERVER['REQUEST_METHOD']=='POST')
{
	if(isset($_POST['name']) and isset($_FILES['pdf']['name']))
	{
		$PdfName = $_POST['name'];
		$PdfInfo = pathinfo($_FILES['pdf']['name']);
		$PdfFileExtension = $PdfInfo['extension'];
		$PdfFileFinalPath = $PdfUploadFolder . $PdfName . '.'. $PdfFileExtension;
		try
		{
			move_uploaded_file($_FILES['pdf']['tmp_name'],$PdfFileFinalPath);
		}
		catch(Exception $e)
		{

		} 	
	}
}
?>