<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<c:set var="counter" value="0"/>
<c:forTokens var="val" delims="|" items="${dataExporter.currentAnswerListDenormString}" varStatus="status">
        <c:if test="${(status.count % 2) == 1}">
            <c:set var="value" value="${val}"/>
        </c:if>
        <c:if test="${(status.count % 2) == 0}">
            <c:set var="counter" value="${counter + 1}"/>
            <input type='checkbox' name='${param.varName}' id='${param.varName}' value="${value}">${val}</input><br/>
        </c:if>
</c:forTokens>  