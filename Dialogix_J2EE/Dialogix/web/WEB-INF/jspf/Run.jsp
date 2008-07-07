<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%
            if (request.getMethod().equals("GET")) {
                dataExporter.setInstrumentVersionId(request.getParameter("id"));
                dataExporter.setLanguageCode(request.getParameter("lang"));
            }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html dir="ltr">    <!-- FIXME -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dialogix: FIXME</title>
        <jsp:include page="view/dialogix_js.jsp"/>
    </head>
    <body bgcolor='white' onload='init();document.dialogixForm.next.focus()'>
        <table border='0' cellpadding='0' cellspacing='3' width='100%'>
            <tr>
                <td width='1%'>
                    <img name='icon' id='icon' src='/Dialogix/images/dialogo.jpg' align='top' border='0' onmouseup='setAdminModePassword();' alt='Logo' />	
                </td>	
                <td align='left'><font SIZE='4'>HEADER MESSAGE</font>
                </td>	
                <td width='1%'>&nbsp;
                </td>
            </tr>
        </table>
        <form method='POST' name='dialogixForm' id='dialogixForm' action='Dialogix.jsp?action=Run'>
            <table cellpadding='2' cellspacing='1' width='100%' border='1'>
                <c:forEach var="ic" items="${dataExporter.instrumentContents}">
                    <c:set var="var" value="${ic.varNameId}"/>  
                    <c:set var="item" value="${ic.itemId}"/>
                    <c:set var="question" value="${item.questionId}"/>
                    <c:set var="answerList" value="${item.answerListId}"/>
                    <c:set var="displayType" value="${ic.displayTypeId}"/>
                    <c:set var="validation" value="${item.validationId}"/>
                    <tr>
                        <jsp:include page="view/question_ref.jsp">
                            <jsp:param name="displayName" value="${ic.displayName}"/>
                        </jsp:include>
                        <td>
                            <c:forEach var="ql" items="${question.questionLocalizedCollection}">
                                <c:if test="${fn:startsWith(ql.languageCode,dataExporter.languageCode)}"> 
                                ${ql.questionString}
                                    <span style="color: blue">
                                        <c:if test="${fn:length(validation.minVal) > 0 || fn:length(validation.maxVal) > 0}">
                                            (${validation.minVal} - ${validation.maxVal})
                                        </c:if>
                                        <c:if test="${fn:length(validation.otherVals) > 0}">
                                            (or one of ${validation.otherVals})
                                        </c:if>
                                    </span>
                                </c:if> 
                            </c:forEach>
                        </td>
                        <td>
                            <c:if test="${displayType.hasAnswerList}">
                                <c:forEach  var="al" items="${answerList.answerListDenormCollection}">
                                    <c:if test="${fn:startsWith(al.languageCode,dataExporter.languageCode)}">
                                        <c:set value="${al.answerListDenormString}" target="${dataExporter}" property="currentAnswerListDenormString" />
                                        <jsp:include page="view/${displayType.displayType}.jsp">
                                            <jsp:param name="varName" value="${var.varName}"/>
                                        </jsp:include>
                                    </c:if>                                
                                </c:forEach>
                            </c:if>
                            <c:if test="${!displayType.hasAnswerList}">
                                <jsp:include page="view/${displayType.displayType}.jsp">
                                    <jsp:param name="varName" value="${var.varName}" />
                                </jsp:include>
                            </c:if>
                            <jsp:include page="view/hidden.jsp">
                                <jsp:param name="varName" value="${var.varName}" />
                            </jsp:include>
                        </td>
                        <!-- nullFlavor should be conditionally included -->
                        <jsp:include page="view/nullFlavor.jsp">
                            <jsp:param name="varName" value="${var.varName}"/>
                        </jsp:include>
                    </tr>
                </c:forEach>                
            </table>     
            <jsp:include page="view/global_hidden.jsp"/>
        </form>
    </body>
</html>
