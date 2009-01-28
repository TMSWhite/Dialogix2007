

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
    <tr><th colspan="15" align="center">Pages Viewed for Session ${param["sess"]} of ${dataExporter.instrumentTitle}</th></tr>
    <tr>
        <th>When</th>
        <th>Lang</th>
        <th>DisplayNum</th>
        <th>GroupNum</th>
        <th>VarName</th>
        <th>Item Visit</th>
        <th>Question</th>
        <th>AnswerString</th>
        <th>AnswerCode</th>
        <th>AnswerId</th>
        <th>Comments</th>
        <th>NullFlavorChange</th>
        <th>Response Latency</th>
        <th>Response Duration</th>
        <th>Vacillation</th>
    </tr>
    <c:forEach var="iu" items="${dataExporter.itemUsages}">
        <c:set var="de" value="${iu.dataElementId}"/>
        <c:set var="var" value="${de.varNameId}"/>
        <c:set var="ans" value="${iu.answerId}"/>
        <c:set var="nf" value="${iu.nullFlavorChangeId}"/>
        <tr>
            <td>
                <fmt:formatDate type="both" dateStyle="SHORT" timeStyle="MEDIUM" value="${iu.timeStamp}"/>
            </td>
            <td>${iu.languageCode}</td>
            <td>
                <a href="Dialogix.jsp?action=DisplayNumRecap&id=${param.id}&sess=${param.sess}&dispNum=${iu.displayNum}" title="View page-level details and events for this displayNum">
                    ${iu.displayNum}
                </a>
            </td>
            <td>${de.groupNum}</td>
            <td>${var.varName}</td>
            <td>${iu.itemVisit}</td>
            <td>${iu.questionAsAsked}</td>
            <td>${iu.answerString}</td>
            <td>${iu.answerCode}</td>
            <td>${ans.answerId}</td>
            <td>${iu.comments}</td>
            <td>${nf.nullFlavorChangeString}</td>
            <td>${iu.responseLatency}</td>
            <td>${iu.responseDuration}</td>
            <td>${iu.vacillation}</td>
        </tr>
    </c:forEach>
</table>