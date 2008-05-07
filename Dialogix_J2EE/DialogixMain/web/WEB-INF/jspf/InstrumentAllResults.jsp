<%-- 
    Document   : InstrumentAllResults
    Created on : Apr 8, 2008, 2:06:34 PM
    Author     : George
--%>

<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.main.DataExporter"/>
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionID(request.getParameter("id"));
        dataExporter.setExclude_regex("");  // to avoid filtering if don't want to
        dataExporter.configure();
        dataExporter.filterVarNames();
        dataExporter.findInstrumentSessionResults();
        dataExporter.transposeInstrumentSessionResultsToTable();
//        dataExporter.init();    // to process all directives        
    }
%>

${dataExporter.transposedInstrumentSesionResults}
