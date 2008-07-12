<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setInstrumentSession(request.getParameter("sess"));
    }        
%>
<table border='1'>
    <tr><th colspan="14" align="center">Page View  Timings for Session ${param.sess} of ${dataExporter.instrumentTitle}</th></tr>
    <tr>
        <th>DisplayNum</th>
        <th>Start Time</th>
        <th>FromGroup</th>  <!-- from_group_num -->
        <th>ToGroup</th> <!-- to_group_num -->
        <th>Page Visit</th> <!-- page_visits -->
        <th>Lang</th> <!-- language_code -->
        <th>Action</th> <!-- action_type -->
        <th>Server(ms)</th>
        <th>Storage(ms)</th>
        <th>Network(ms)</th>
        <th>Load(ms)</th>
        <th>Page(ms)</th>
        <th>Total(ms)</th>
        <th>Status</th>
    </tr>
        <c:forEach var="pu" items="${dataExporter.pageUsages}">
            <c:set var="act" value="${pu.actionTypeId}"/>
            <tr>
                <td>
                    <a href="Dialogix.jsp?action=PageUsageEvents&id=${param.id}&sess=${param.sess}&pu=${pu.pageUsageId}&dispNum=${pu.displayNum}" title="View events for this page">
                        ${pu.displayNum}
                    </a>
                </td>
                <td>
                    <fmt:formatDate type="both" dateStyle="SHORT" timeStyle="MEDIUM" value="${pu.serverSendTime}"/>
                </td>
                <td>${pu.fromGroupNum}</td>
                <td>${pu.toGroupNum}</td>
                <td>${pu.pageVisits}</td>
                <td>${pu.languageCode}</td>
                <td>${act.actionName}</td>
                <td>${pu.serverDuration}</td>
                <td>${pu.storageDuration}</td>
                <td>${pu.networkDuration}</td>
                <td>${pu.loadDuration}</td>
                <td>${pu.pageDuration}</td>
                <td>${pu.totalDuration}</td>
                <td>${pu.statusMsg}</td>
            </tr>
        </c:forEach>                
</table>