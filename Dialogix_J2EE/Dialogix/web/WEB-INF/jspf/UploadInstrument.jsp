<jsp:useBean id="dialogix" scope="session" class="org.dialogix.util.DialogixParserTool" />
<%@ page import="org.apache.commons.fileupload.*, org.apache.commons.fileupload.servlet.ServletFileUpload, org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%
  if (request.getMethod().equals("POST")) {
    try {
      if (ServletFileUpload.isMultipartContent(request)){
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        java.util.Iterator<FileItem> it = servletFileUpload.parseRequest(request).iterator();
        while (it.hasNext()){
          FileItem item = (FileItem)it.next();
          if (!item.isFormField()){
            String fileName = item.getName();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            java.io.File uploadedFile = java.io.File.createTempFile("dlx", suffix);
            item.write(uploadedFile);
            dialogix.setFilesToLoad(uploadedFile.getAbsolutePath());
          }
        }
      }
     } catch (Exception e) { }
  }
%>
<FORM method="POST" name="myForm" action="Dialogix.jsp?action=UploadInstrument" enctype="multipart/form-data">
    <TABLE border="1">
        <TR>
            <TD colspan="2" align="center"><FONT SIZE="5"><b>Upload Dialogix Instruments</b></FONT></TD>
        </TR>
        <TR><TD>Select one instrument from your local file system.  It will be uploaded to the Dialogix server.<br>
                Sources can be: 
                <ol>
                    <li>Excel (.xls) - this supports Unicode</li>
                    <li>Text (.txt) - must be in ASCII format - for legacy instruments</li>
                    <li>Compressed (.jar) - must be legacay  ASCII instruments saved via the SaveAsJar button</li>
                </ol>
               
        </TD></TR>
        <TR><TD><input name="file" id="file" type="file"></TD></TR>
        <TR><TD><input name="submit" id="submit" type="submit" value="Upload"></TD></TR>
    </TABLE>
    ${dialogix.uploadResults}
</FORM>

