<?php include("Dialogix_Table_PartA.php"); ?>

<div style="text-align: center;"><br>
<img src="images/dialogo.jpg" align="bottom" border="0" height="81" width="180"> <br>

<H1>Upload ArcView zip file for AutoMEQ analysis</H1>

<form enctype="multipart/form-data" action="Upload_Arcview.php" method="post">
    <input type="hidden" name="MAX_FILE_SIZE" value="200000000" />
    Choose a file to upload: <input name="userfile" type="file" />
    <input type="submit" value="Upload File" />
</form>

<?php
if (isset($_FILES['userfile']['size']) && $_FILES['userfile']['size'] > 0) {
  print "<hr><pre>";
  
  "Uploading " . $_FILES['userfile']['name'] . " to here: " . $_FILES['userfile']['tmp_name'] . "<br>";
  
  /*
  if (preg_match('/^(.*)\.zip$/', $_FILES['userfile']['name'], $matches) != 1) {
    print "Please upload an Zip file";
  }
  else {
    $uploadDir = '/usr/local/tomcat554/webapps/OMH/WEB-INF/schedules/';
    
    }
    @chmod($uploadFile, 0644);
    @unlink($_FILES['userfile']['tmp_name']); 
    }     
  }
  */
  print "</pre>";
}

?>

<?php include("Dialogix_Table_PartB.php"); ?>


