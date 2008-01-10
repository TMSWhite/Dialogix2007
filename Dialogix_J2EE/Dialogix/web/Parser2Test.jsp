<%-- 
    Document   : ParserTest
    Created on : Jan 10, 2008, 8:30:22 AM
    Author     : Coevtmw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="dialogix" scope="session" class="org.dialogix.util.DialogixParserTool" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dialogix Parser Test</title>
    </head>
    <body>
        <H1>Dialogix Parser Test</H1>
        <FORM method='POST' name='myForm' action='Parser2Test.jsp'>
            <B>Memory: Total=${dialogix.totalMemory}, Free=${dialogix.freeMemory}, Used=${dialogix.memoryUsed}</B>
            
            <TABLE border='1'>
                <TR><TD>Input equation(s) to parse<br>
                        <textarea name='eqn' rows='10' cols='100'></textarea>
                </TD></TR>
                <TR><TD><input name='submit' type='submit'><input name='clear' type='reset'></TD></TR>
            </TABLE>
            <% dialogix.parse(request.getParameter("eqn"));
            %>
            <TABLE BORDER='1'>
                <TR>
                    <TH>Equation</TH><TH>Results</TH><TH>Expected</TH><TH>Errors</TH><TH>Dependencies</TH>
                </TR>
                ${dialogix.queryHistory}
            </TABLE>
        </FORM>
    </body>
</html>
