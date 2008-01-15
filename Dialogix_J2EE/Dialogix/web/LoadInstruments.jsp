<%-- 
    Document   : LoadInstruments
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
        <title>Load Dialogix Instruments</title>
    </head>
    <body>
        <H1>Load Dialogix Instruments</H1>
        <FORM method='POST' name='myForm' action='LoadInstruments.jsp'>
            <B>Memory: Total=${dialogix.totalMemory}, Free=${dialogix.freeMemory}, Used=${dialogix.memoryUsed}</B>
            
            <TABLE border='1'>
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
    </body>
</html>
