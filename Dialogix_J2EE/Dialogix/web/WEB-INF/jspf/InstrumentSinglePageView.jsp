<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setLanguageCode(request.getParameter("lang"));
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
        <th>Input</th>
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
            <td>
                <c:if test="${displayType.hasAnswerList}">
                    <c:forEach  var="al" items="${answerList.answerListDenormCollection}">
                        <c:if test="${fn:startsWith(al.languageCode,dataExporter.languageCode)}">
                            <!-- N.B.  I wanted to c:import of  jsp:include a sub-file, but Unicode just wouldn't work properly.  There must be a solution, but for now,
                            do it the hard way and in-line everything 
                            <p style="color: red">${al.answerListDenormString}</p>
                            -->
                            <c:choose>
                                <c:when test="${fn:startsWith(displayType.displayType,'combo')}">
                                    <select name='${var.varName}' id='${var.varName}'>
                                            <option value='' selected>--Select one of the following--  </option>
                                            <c:set var="counter" value="0"/>
                                            <c:forTokens var="val" delims="|" items="${al.answerListDenormString}" varStatus="status">
                                                    <c:if test="${(status.count % 2) == 1}">
                                                        <c:set var="value" value="${val}"/>
                                                    </c:if>
                                                    <c:if test="${(status.count % 2) == 0}">
                                                        <c:set var="counter" value="${counter + 1}"/>
                                                        <option value='${value}'>${counter})&nbsp;${val}</option>
                                                    </c:if>
                                            </c:forTokens>  
                                    </select>
                                </c:when>                                
                                <c:when test="${fn:startsWith(displayType.displayType,'list')}">
                                    <select name='${var.varName}' id='${var.varName}' size = '3'>  <!-- FIXME - there is no easy way to count the number of elements! -->
                                            <option value='' selected>--Select one of the following--  </option>
                                            <c:set var="counter" value="0"/>
                                            <c:forTokens var="val" delims="|" items="${al.answerListDenormString}" varStatus="status">
                                                    <c:if test="${(status.count % 2) == 1}">
                                                        <c:set var="value" value="${val}"/>
                                                    </c:if>
                                                    <c:if test="${(status.count % 2) == 0}">
                                                        <c:set var="counter" value="${counter + 1}"/>
                                                        <option value='${value}'>${counter})&nbsp;${val}</option>
                                                    </c:if>
                                            </c:forTokens>  
                                    </select>
                                </c:when>
                                <c:when test="${fn:startsWith(displayType.displayType,'radio')}">
                                    <c:set var="counter" value="0"/>
                                    <c:forTokens var="val" delims="|" items="${al.answerListDenormString}" varStatus="status">
                                            <c:if test="${(status.count % 2) == 1}">
                                                <c:set var="value" value="${val}"/>
                                            </c:if>
                                            <c:if test="${(status.count % 2) == 0}">
                                                <c:set var="counter" value="${counter + 1}"/>
                                                <input type='radio' name='${var.varName}' id='${var.varName}' value="${value}">${val}</input><br/>
                                            </c:if>
                                    </c:forTokens>  
                                </c:when>                          
                                <c:otherwise>
                                    <select name='${var.varName}' id='${var.varName}' size = '3'>
                                            <option value='' selected>--Select one of the following--  </option>
                                            <c:set var="counter" value="0"/>
                                            <c:forTokens var="val" delims="|" items="${al.answerListDenormString}" varStatus="status">
                                                    <c:if test="${(status.count % 2) == 1}">
                                                        <c:set var="value" value="${val}"/>
                                                    </c:if>
                                                    <c:if test="${(status.count % 2) == 0}">
                                                        <c:set var="counter" value="${counter + 1}"/>
                                                        <option value='${value}'>${counter})&nbsp;${val}</option>
                                                    </c:if>
                                            </c:forTokens>  
                                    </select>
                                </c:otherwise>                
                            </c:choose>                           
                        </c:if>                                
                    </c:forEach>
                </c:if>
                <c:if test="${!displayType.hasAnswerList}">
                    <jsp:include page="view/${displayType.displayType}.jsp">
                        <jsp:param name="varName" value="${var.varName}" />
                    </jsp:include>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
