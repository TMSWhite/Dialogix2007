

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<table border="1">
    <tr>
        <th colspan='4' align='center'>Instruments</th>
    </tr>
    <tr>
        <th>Logo</th>
        <th>Name</th>
        <th>Principle Investigator</th>
        <th>Instruments</th>        
    </tr>    
    <c:forEach var="study" items="${dataExporter.studies}">
        <tr>
            <td>
                <img src="${study.studyIconPath}" alt="${study.studyName}"/>
            </td>
            <td>
                ${study.studyName}
            </td>
            <td>
                ${study.piName}
            </td>
            <td>
                <c:forEach var="siv" items="${study.studyInstrumentVersionCollection}">
                    <c:set var="iv" value="${siv.instrumentVersionId}"/>
                    <c:set var="i" value="${iv.instrumentId}"/>
                    <a href="Dialogix.jsp?action=ListInstrumentSessions&id=${iv.instrumentVersionId}&study_id=${study.studyId}"
                        title="Review sessions for this instrument">
                        ${i.instrumentName} (${iv.versionString})
                    </a>&nbsp;(<a 
                        target="_blank" href="servlet/Dialogix?schedule=${iv.instrumentVersionId}&s=${study.studyId}&p=${dataExporter.person.personId}&DIRECTIVE=START"                    
                        title="Launch this instrument in scope of this study">run</a>)<br/>  
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
</table>
