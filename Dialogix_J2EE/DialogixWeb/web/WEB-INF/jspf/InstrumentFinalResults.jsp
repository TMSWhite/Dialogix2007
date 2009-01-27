<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setExclude_regex("");  // to avoid filtering if don't want to
        dataExporter.configure();
        dataExporter.filterVarNames();
    }
%>

${dataExporter.horizontalInstrumentVersionResults}
