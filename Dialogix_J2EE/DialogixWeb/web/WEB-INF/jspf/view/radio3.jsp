

<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<c:set var="counter" value="0"/>
<table cellpadding='0' cellspacing='1' border='1' width='100%' style="background-color: lightgrey">
    <tr>
        <c:forTokens var="val" delims="|" items="${dataExporter.currentAnswerListDenormString}" varStatus="status">
            <c:if test="${(status.count % 2) == 1}">
                <c:set var="value" value="${val}"/>
            </c:if>
            <c:if test="${(status.count % 2) == 0}">
                <c:set var="counter" value="${counter + 1}"/>
                <td valign='top' width='${100 / dataExporter.numAnswerChoices}%'>
                    <input type='radio' name='${param.varName}' id='${param.varName}' value="${value}">${val}</input>
                </td>
            </c:if>
        </c:forTokens>
    </tr>
</table>