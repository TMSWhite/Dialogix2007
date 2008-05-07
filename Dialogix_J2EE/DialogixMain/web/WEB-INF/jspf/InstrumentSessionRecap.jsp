<%-- 
    Document   : InstrumentSessionRecap
    Created on : Apr 8, 2008, 2:14:24 PM
    Author     : George
--%>

<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.main.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionID(request.getParameter("id"));
        dataExporter.setInstrumentSession(request.getParameter("sess"));
    }        
%>
<table border='1'>
    <tr><th colspan="10" align="center">Pages Viewed for Session ${param["sess"]} of ${dataExporter.instrumentTitle}</th></tr>
    <tr><th>#</th><th>DisplayNum</th><th>GroupNum</th><th>VarName</th><th>Item Visit</th><th>Question</th><th>AnswerString</th><th>AnswerCode</th><th>AnswerID</th><th>Comments</th></tr>
        <c:forEach var="iu" items="${dataExporter.itemUsages}">
            <c:set var="var" value="${iu.varNameID}"/>              
            <tr>
                <td>${iu.itemUsageSequence}</td>
                <td>${iu.displayNum}</td>
                <td>${iu.groupNum}</td>
                <td>${var.varName}</td>
                <td>${iu.itemVisits}</td>
                <td>${iu.questionAsAsked}</td>
                <td>${iu.answerString}</td>
                <td>${iu.answerCode}</td>
                <td>${iu.answerID}</td>
                <td>${iu.comments}</td>
            </tr>
        </c:forEach>                
</table>