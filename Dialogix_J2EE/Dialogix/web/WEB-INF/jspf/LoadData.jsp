<jsp:useBean id="dialogix" scope="session" class="org.dialogix.util.DialogixParserTool" />
<FORM method='POST' name='myForm' action='Dialogix.jsp?action=LoadData'>
    <TABLE border='1'>
        <TR>
            <TD colspan='2' align='center'><FONT SIZE="5"><b>Load Dialogix Data</b></FONT></TD>
        </TR>
        <TR><TD>Load Data File(s), listing full path name of files, one per line.<br>
                Sources can be: 
                <ol>
                    <li>Completed (.jar) - either legacay ASCII versions or newer Unicode versions</li>
                    <li>Working (.dat) (will look for .dat.evt) - either legacay ASCII versions or newer Unicode versions</li>
                </ol>
                <textarea name='filenames' rows='10' cols='100'></textarea>
        </TD></TR>
        <TR><TD><input name='submit' type='submit'><input name='clear' type='reset'></TD></TR>
    </TABLE>
    <%
            dialogix.setFilesToLoad(request.getParameter("filenames"));
    %>  
    ${dialogix.dataLoadResults}
</FORM>
