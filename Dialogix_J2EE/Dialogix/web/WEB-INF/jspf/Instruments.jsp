<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<table border="1">
    <tr>
        <th colspan='5' align='center'>Available Instruments</th>
    </tr>
    <tr>
        <th>Title</th>
        <th>Version</th>
        <th>#Languages</th>
        <th>Questions (min-max)</th>
        <th>Max Pages</th>
    </tr>    
    <c:forEach var="ivv" items="${dataExporter.instrumentVersions}">
        <tr>
            <td>
                <a 
                    target="_blank" href="servlet/Dialogix?schedule=${ivv.instrumentVersionId}&p=${dataExporter.person.personId}&DIRECTIVE=START"                    
                    title="Launch this instrument">
                    ${ivv.instrumentName}
                </a>
            </td>
            <td>${ivv.instrumentVersion}</td>
            <td>${ivv.numLanguages}</td>
            <td>(${ivv.numQuestions - ivv.numBranches} - ${ivv.numQuestions})</td>
            <td>${ivv.numGroups}</td>
        </tr>
    </c:forEach>
</table>
