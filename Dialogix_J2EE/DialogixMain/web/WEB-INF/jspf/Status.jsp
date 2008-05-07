<%-- 
    Document   : Status
    Created on : Apr 9, 2008, 11:41:15 AM
    Author     : George
--%>


<jsp:useBean id="dialogix" scope="session" class="org.dialogix.util.DialogixParserTool" />
<H1>Dialogix Status</H1>
<B>Memory: Total=${dialogix.totalMemory}, Free=${dialogix.freeMemory}, Used=${dialogix.memoryUsed}</B>        