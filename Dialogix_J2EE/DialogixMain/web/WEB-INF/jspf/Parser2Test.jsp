<%-- 
    Document   : Parser2Test
    Created on : Apr 9, 2008, 11:19:58 AM
    Author     : George
--%>

<jsp:useBean id="dialogix2" scope="session" class="org.dialogix.parser.DialogixParserTool" />
<FORM method='POST' name='myForm' action='Parser2Test.jsp'>
    <TABLE border='1'>
        <TR>
            <TD align='CENTER'><FONT SIZE="5"><b>New Dialogix Parser Test</b></FONT></TD>
        </TR>
        <TR><TD>Input equation(s) to parse<br>
                <textarea name='eqn' rows='10' cols='100'></textarea>
        </TD></TR>
        <TR><TD><input name='submit' type='submit'><input name='clear' type='reset'></TD></TR>
    </TABLE>
    <% 
    dialogix2.parse(request.getParameter("eqn"));
    %>
    <TABLE BORDER='1'>
        <TR>
            <TH>Equation</TH><TH>Results</TH><TH>Expected</TH><TH>Errors</TH><TH>Dependencies</TH>
        </TR>
        ${dialogix2.queryHistory}
    </TABLE>
</FORM>