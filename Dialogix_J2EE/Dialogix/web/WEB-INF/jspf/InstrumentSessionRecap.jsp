<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setInstrumentSession(request.getParameter("sess"));
    }        
%>
<table border='1'>
    <tr><th colspan="10" align="center">Pages Viewed for Session ${param["sess"]} of ${dataExporter.instrumentTitle}</th></tr>
    <tr><th>When</th><th>Lang</th><th>DisplayNum</th><th>GroupNum</th><th>VarName</th><th>Item Visit</th><th>Question</th><th>AnswerString</th><th>AnswerCode</th><th>AnswerId</th><th>Comments</th></tr>
        <c:forEach var="iu" items="${dataExporter.itemUsages}">
            <c:set var="de" value="${iu.dataElementId}"/>
            <c:set var="var" value="${de.varNameId}"/>              
            <c:set var="ans" value="${iu.answerId}"/>
            <tr>
                <td>${iu.timeStamp}</td>
                <td>${iu.languageCode}</td>
                <td>${iu.displayNum}</td>
                <td>${de.groupNum}</td>
                <td>${var.varName}</td>
                <td>${iu.itemVisit}</td>
                <td>${iu.questionAsAsked}</td>
                <td>${iu.answerString}</td>
                <td>${iu.answerCode}</td>
                <td>${ans.answerId}</td>
                <td>${iu.comments}</td>
            </tr>
        </c:forEach>                
</table>