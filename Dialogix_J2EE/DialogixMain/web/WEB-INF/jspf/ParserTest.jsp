<%-- 
    Document   : ParserTest
    Created on : Apr 9, 2008, 11:21:50 AM
    Author     : George
--%>

<jsp:useBean id="dialogix" scope="session" class="org.dialogix.util.DialogixParserTool" />
<FORM method='POST' name='myForm' action='ParserTest.jsp'>
    <TABLE border='1'>
        <TR>
            <TD align='CENTER'><FONT SIZE="5"><B>Dialogix Parser Test</B></FONT></TD>
        </TR>
        <TR><TD>Input equation(s) to parse<br>
                <textarea name='eqn' rows='10' cols='100'></textarea>
        </TD></TR>
        <TR><TD><input name='submit' type='submit'><input name='clear' type='reset'></TD></TR>
    </TABLE>
    <% 
    dialogix.parse(request.getParameter("eqn"));
    %>
    <TABLE BORDER='1'>
        <TR>
            <TH>Equation</TH><TH>Results</TH><TH>Expected</TH><TH>Errors</TH><TH>Dependencies</TH>
        </TR>
        ${dialogix.queryHistory}
    </TABLE>
</FORM>

