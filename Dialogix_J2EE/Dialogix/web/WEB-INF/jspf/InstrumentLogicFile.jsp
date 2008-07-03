<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setLanguageCode("en");
    }        
%>
<table border='1'>
    <tr><th colspan="7" align="CENTER">Logic File for ${dataExporter.instrumentTitle}</th></tr>
    <tr>
        <th>#</th>
        <th>Group</th>
        <th>VarName<br/><font color="blue">Concept</font></th>
        <th>Relevance<br/><font color="blue">Validation</font></th>
        <th>Question<br/><font color="blue">Equation</font></th>
        <th>DataType</th>
        <th>AnswerList</th>
    </tr>
    <c:forEach var="ic" items="${dataExporter.instrumentContents}">
        <c:set var="var" value="${ic.varNameId}"/>  
        <c:set var="item" value="${ic.itemId}"/>
        <c:set var="question" value="${item.questionId}"/>
        <c:set var="answerList" value="${item.answerListId}"/>
        <c:set var="displayType" value="${ic.displayTypeId}"/>
        <c:set var="validation" value="${item.validationId}"/>
        <tr>
            <td>${ic.itemSequence}</td>
            <td>${ic.groupNum}</td>
            <td>${var.varName}<br/>
                <font color="blue">
                    ${ic.concept}
                </font>
            </td>
            <td>${ic.relevance}<br/>
                <font color="blue">
                    <c:if test="${fn:length(validation.inputMask)>0}">
                        Input Mask: ${validation.inputMask}<br/>
                    </c:if>
                    <c:if test="${fn:length(validation.minVal) > 0 || fn:length(validation.maxVal) > 0}">
                        (${validation.minVal} - ${validation.maxVal})
                    </c:if>
                    <c:if test="${fn:length(validation.otherVals) > 0}">
                        (or one of ${validation.otherVals})
                    </c:if>
                </font>
            </td>
            <td>
                <c:forEach var="ql" items="${question.questionLocalizedCollection}">
                    <c:if test="${fn:startsWith(ql.languageCode,dataExporter.languageCode)}"> 

                        <c:if test='${fn:startsWith(ic.itemActionType,"e")}'>
                            <font color="blue">
                                ${q1.questionString}
                            </font>
                        </c:if>
                        <c:if test='${!fn:startsWith(ic.itemActionType,"e")}'>
                            ${ql.questionString}                                
                        </c:if>
                    </c:if> 
                </c:forEach>
            </td>
            <td>${displayType.displayType}</td>
            <td>
                <c:if test="${displayType.hasAnswerList}">
                    <c:forEach  var="al" items="${answerList.answerListDenormCollection}">
                        <c:if test="${fn:startsWith(al.languageCode,dataExporter.languageCode)}">
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
</table>