

<jsp:useBean id="dialogix" scope="session" class="org.dialogix.util.DialogixParserTool" />
<FORM method='POST' name='myForm' action='Dialogix.jsp?action=LoadInstruments'>
    <TABLE border='1'>
        <TR>
            <TD colspan='2' align='center'><FONT SIZE="5"><b>Load Dialogix Instruments</b></FONT></TD>
        </TR>
        <TR><TD>Load Instrument(s), listing full path name of files, one per line.<br>
                Sources can be: 
                <ol>
                    <li>Excel (.xls) - this supports Unicode</li>
                    <li>Text (.txt) - must be in ASCII format - for legacy instruments</li>
                    <li>Compressed (.jar) - must be legacay  ASCII instruments saved via the SaveAsJar button</li>
                </ol>
                <textarea name='filenames' rows='10' cols='100'></textarea>
        </TD></TR>
        <TR><TD><input name='submit' type='submit'><input name='clear' type='reset'></TD></TR>
    </TABLE>
    <%
            dialogix.setFilesToLoad(request.getParameter("filenames"));
    %>  
    ${dialogix.loadResults}
</FORM>
