<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%
            if (request.getMethod().equals("GET")) {
                dataExporter.setInstrumentVersionId(request.getParameter("id"));
            }        
%>
<table border='1'>
    <tr><th colspan="8" align="CENTER">Translations of ${dataExporter.instrumentTitle}</th></tr>
    <tr><th>#</th><th>Group</th><th>VarName</th><th>Relevance</th><th>Language</th><th>Question</th><th>DataType</th><th>AnswerList</th></tr>
    <c:forEach var="ic" items="${dataExporter.instrumentContents}">
        <c:set var="var" value="${ic.varNameId}"/>  
        <c:set var="item" value="${ic.itemId}"/>
        <c:set var="question" value="${item.questionId}"/>
        <c:set var="answerList" value="${item.answerListId}"/>
        <c:set var="displayType" value="${ic.displayTypeId}"/>
        <c:forEach varStatus="langCounter" var="lang" items="${dataExporter.languages}">
            <tr>
            <c:if test="${langCounter.first}">
                <td valign="middle" rowspan="${dataExporter.numLanguages}">${ic.itemSequence}</td>
                <td valign="middle" rowspan="${dataExporter.numLanguages}">${ic.groupNum}</td>
                <td valign="middle" rowspan="${dataExporter.numLanguages}">${var.varName}</td>
                <td valign="middle" rowspan="${dataExporter.numLanguages}">${ic.relevance}</td>                
            </c:if>
                <td>${lang}</td>
                <td>
                    <c:forEach var="ql" items="${question.questionLocalizedCollection}">
                        <c:if test="${fn:startsWith(ql.languageCode,lang)}">
                        ${ql.questionString}                                
                        </c:if>
                    </c:forEach>
                </td>
                <td>${displayType.displayType}</td>
                <td>
                    <c:if test="${displayType.hasAnswerList}">
                        <c:forEach  var="al" items="${answerList.answerListDenormCollection}">
                            <c:if test="${fn:startsWith(al.languageCode,lang)}">
                                <c:set var="ans" value="${al.answerListDenormString}"/>
                                <c:forTokens var="val" delims="|" items="${ans}" varStatus="status">
                                    <c:if test="${(status.count % 2) == 1}">
                                        [${val}]&nbsp;
                                    </c:if>
                                    <c:if test="${(status.count % 2) == 0}">
                                    ${val}<br/>
                                    </c:if>
                                </c:forTokens>
                            </c:if>                                
                        </c:forEach>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </c:forEach>
</table>
