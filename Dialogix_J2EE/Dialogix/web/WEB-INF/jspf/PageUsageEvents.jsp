<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setInstrumentSession(request.getParameter("sess"));
        dataExporter.setPageUsageId(request.getParameter("pu"));
    }        
%>
<table border='1'>
    <tr><th colspan="7" align="center">Page View Events Timings for Page ${param.dispNum} of Session ${param.sess} of ${dataExporter.instrumentTitle}</th></tr>
    <tr>
        <th>#</th>
        <th>Input Type</th>
        <th>Action</th> 
        <th>Duration</th> 
        <th>VarName</th>
        <th>Value1</th> 
        <th>Value2</th> 
    </tr>
        <c:forEach var="pue" items="${dataExporter.pageUsageEvents}">
            <tr>
                <td>${pue.pageUsageEventSequence}</td>
                <td>${pue.eventType}</td>
                <td>${pue.guiActionType}</td>
                <td>${pue.duration}</td>
                <td>${pue.varName}</td>
                <td>${pue.value1}</td>
                <td>${pue.value2}</td>
            </tr>
        </c:forEach>                
</table>