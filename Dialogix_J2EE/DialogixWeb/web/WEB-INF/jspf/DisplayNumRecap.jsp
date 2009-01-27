<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setInstrumentSession(request.getParameter("sess"));
        dataExporter.setDisplayNum(request.getParameter("dispNum"));
    }
%>
<table border='1'>
    <tr><th colspan="14" align="center">Pages Viewed for Session ${param["sess"]} of ${dataExporter.instrumentTitle}</th></tr>
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
    </tr>
    <c:forEach var="iu" items="${dataExporter.itemUsage}">
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
                    ${iu.displayNum}
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
        </tr>
    </c:forEach>
</table>
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
        <c:set var="pu" value="${dataExporter.pageUsage}"/>
        <c:set var="act" value="${pu.actionTypeId}"/>

        <tr>
            <td>
                    ${pu.displayNum}
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
</table>

<table border='1'>
    <tr><th colspan="7" align="center">Page View Events Timings for Page ${param.dispNum} of Session ${param.sess} of ${dataExporter.instrumentTitle}</th></tr>
    <tr>
        <th>#</th>
        <th>Input Type</th>
        <th>Action</th>
        <th>When (ms)</th>
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