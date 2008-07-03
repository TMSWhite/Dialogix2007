<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<table border="1">
    <tr>
        <th colspan='5' align='center'>Instrument Sessions for ${dataExporter.person.firstName} ${dataExporter.person.lastName}</th>
    </tr>
    <tr>
        <th>Instrument</th>
        <th>Start Date</th>
        <th>Last Access Time</th>
        <th>Completed</th>
        <th># Pages Viewed</th>
    </tr>    
    <c:forEach var="is" items="${dataExporter.myInstrumentSessions}">
        <tr>
            <td>
                <c:set var="iv" value="${is.instrumentVersionId}"/>
                <c:set var="i" value="${iv.instrumentId}"/>
                ${i.instrumentName} (${iv.versionString})
            </td>
            <td>
                ${is.startTime}
            </td>
            <td>
                <a href="Dialogix.jsp?action=PageUsageRecap&id=${dataExporter.instrumentVersionId}&sess=${is.instrumentSessionId}"
                title="View timing details for each page viewed in this session">
                ${is.lastAccessTime}
                </a>
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
