<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%
    dataExporter.doLogout();
%>   
<jsp:forward page="../../Dialogix.jsp?action=Login" />
