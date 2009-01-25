

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<table border="1">
    <tr>
        <th colspan='6' align='center'>Instrument Sessions for ${dataExporter.person.firstName} ${dataExporter.person.lastName}</th>
    </tr>
    <tr>
        <th>Instrument</th>
        <th>Start Date</th>
        <th>Last Access Time</th>
        <th>Name</th>
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
                <fmt:formatDate type="both" dateStyle="SHORT" timeStyle="SHORT" value="${is.startTime}"/>
            </td>
            <td>
                <a href="Dialogix.jsp?action=PageUsageRecap&id=${iv.instrumentVersionId}&sess=${is.instrumentSessionId}"
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
                        href="Dialogix.jsp?action=InstrumentSessionRecap&id=${iv.instrumentVersionId}&sess=${is.instrumentSessionId}"
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
<hr/>
<table border="1">
    <tr>
        <th colspan='10' align='center'>Planned Sessions for ${dataExporter.person.firstName} ${dataExporter.person.lastName}</th>
    </tr>
    <tr>
        <th>Study</th>
        <th>Instrument</th>
        <th>Start Date</th>
        <th>Last Access Time</th>
        <th>Name</th>
        <th>Completed</th>
        <th># Pages Viewed</th>
        <th>Username</th>
        <th>Passwd</th>
        <th>Startup Values</th>
    </tr>
    <c:set var="person" value="${dataExporter.person}"/>
    <c:forEach var="ss" items="${person.subjectSessionCollection}">
        <c:set var="iv" value="${ss.instrumentVersionId}"/>
        <c:set var="i" value="${iv.instrumentId}"/>
        <c:set var="study" value="${ss.studyId}"/>
        <c:set var="is" value="${ss.instrumentSessionId}"/>
        <tr>
            <td>
                ${study.studyName}
            </td>
            <td>
                ${i.instrumentName} (${iv.versionString})
            </td>
            <td>
                <c:choose>
                    <c:when test="${is != null}">
                        <a 
                            target="_blank" href="servlet/Dialogix?RestoreSuspended=${is.instrumentSessionId}&amp;DIRECTIVE=RESTORE"                    
                            title="Resume this Session">   
                                <fmt:formatDate type="both" dateStyle="SHORT" timeStyle="SHORT" value="${is.startTime}"/>
                        </a>                    
                    </c:when>
                    <c:otherwise>
                        <a target="_blank" href="servlet/Dialogix?schedule=${iv.instrumentVersionId}&s=${study.studyId}&ss=${ss.subjectSessionId}&p=${person.personId}&DIRECTIVE=START"
                            title="Start this Instrument">
                            (start)
                        </a>                              
                    </c:otherwise>
                </c:choose>

            </td>
            <td>
                 <a href="Dialogix.jsp?action=PageUsageRecap&id=${iv.instrumentVersionId}&sess=${is.instrumentSessionId}"
                title="View timing details for each page viewed in this session">
                    <fmt:formatDate type="both" dateStyle="SHORT" timeStyle="SHORT" value="${is.lastAccessTime}"/>
                </a>
            </td>
            <td>
                <c:out escapeXml="true" default="" value="${is.titleForPicklistWhenInProgress}"/>
            </td>            
            <td>
                <c:out default="-" value="${is.finished}" />
            </td>
            <td>
                <c:if test="${is.displayNum > 0}">
                    <a 
                        href="Dialogix.jsp?action=InstrumentSessionRecap&id=${iv.instrumentVersionId}&sess=${is.instrumentSessionId}"
                        title="See what the subject saw for this  session">
                        ${is.displayNum}
                    </a>
                </c:if>
                <c:if test="${is.displayNum == 0}">
                    &nbsp;
                </c:if>
            </td>
            <td>
                ${ss.username}
            </td>
            <td>
                ${ss.pwd}
            </td>
            <td>
                <c:forEach var="ssd" items="${ss.subjectSessionDataCollection}">
                    ${ssd.varName} = ${ssd.value} <br/>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
</table>