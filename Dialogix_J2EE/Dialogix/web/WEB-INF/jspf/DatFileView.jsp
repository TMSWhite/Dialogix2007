<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%
            if (request.getMethod().equals("GET")) {
                dataExporter.setInstrumentVersionId(request.getParameter("id"));
                dataExporter.setInstrumentSession(request.getParameter("sess"));
            }
%>
<pre>
${dataExporter.datFileView}
</pre>