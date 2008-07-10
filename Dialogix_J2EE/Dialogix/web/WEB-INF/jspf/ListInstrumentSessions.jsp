<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
        dataExporter.setStudy(request.getParameter("study_id"));
    }        
%>
<table border="1">
    <tr>
        <th colspan='5' align='center'>Sessions for ${dataExporter.instrumentTitle}</th>
    </tr>
    <tr>
        <th>Start Date</th>
        <th>Last Access Time</th>
        <th>Name</th>
        <th>Completed</th>
        <th># Pages Viewed</th>
    </tr>    
    <c:forEach var="is" items="${dataExporter.instrumentSessions}">
        <tr>
            <td>
                <fmt:formatDate type="both" dateStyle="SHORT" timeStyle="SHORT" value="${is.startTime}"/>
            </td>
            <td>
                <a href="Dialogix.jsp?action=PageUsageRecap&id=${dataExporter.instrumentVersionId}&sess=${is.instrumentSessionId}"
                title="View timing details for each page viewed in this session">
                <fmt:formatDate type="both" dateStyle="SHORT" timeStyle="SHORT" value="${is.lastAccessTime}"/>
                </a>
            </td>
            <td>
                <c:out escapeXml="true" default="-" value="${is.titleForPicklistWhenInProgress}"/>
            </td>
            <td>
                <a 
                    target="_blank" href="servlet/Dialogix?RestoreSuspended=${is.instrumentSessionId}&amp;DIRECTIVE=RESTORE"                    
                    title="Resume this Session">
                    <c:if test="${is.finished != 0}">
                        yes
                    </c:if>
                    <c:if test="${is.finished == 0}">                    
                        no
                    </c:if>                
                </a>                 
            </td>
            <td>
                <c:if test="${is.displayNum > 0}">
                    <a 
                        href="Dialogix.jsp?action=InstrumentSessionRecap&id=${dataExporter.instrumentVersionId}&sess=${is.instrumentSessionId}"
                        title="See what the subject saw for this  session">
                        ${is.displayNum}
                    </a>
                </c:if>
                <c:if test="${is.displayNum == 0}">
                    &nbsp;
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
