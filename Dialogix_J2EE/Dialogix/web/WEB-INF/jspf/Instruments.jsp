<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<table border="1">
    <tr>
        <td colspan='11' align='center'><FONT SIZE="5"><b>Available Instruments</b></FONT></td>
    </tr>
    <tr>
        <td>Name</td>
        <td>Version</td>
        <td>#Vars</td>
        <td>#Groups</td>
        <td>#Questions</td>
        <td>#Equations</td>
        <td>#Branches</td>
        <td>#Tailorings</td>
        <td>#Languages</td>
        <td>#Instructions</td>
    </tr>    
    <c:forEach var="ivv" items="${dataExporter.instrumentVersions}">
        <tr>
            <td>
                <a 
                    target="_blank" href="servlet/Dialogix?schedule=${ivv.instrumentVersionId}&DIRECTIVE=START"                    
                    title="Launch this instrument">
                    ${ivv.instrumentName}
                </a>
            </td>
            <td>
                <a href="Dialogix.jsp?action=RunInstrument&schedule=${ivv.instrumentVersionId}&p=${dataExporter.person.personId}&DIRECTIVE=START"
                   title="Launch this instrument using JSP">
                    ${ivv.instrumentVersion}
                </a>
            </td>
            <td>
                <a href="Dialogix.jsp?action=Run&id=${ivv.instrumentVersionId}&lang=en"
                   title="Prototype Instrument Logic">                
                    ${ivv.numVars}
                </a>
            </td>
            <td>${ivv.numGroups}</td>
            <td>${ivv.numQuestions}</td>
            <td>${ivv.numEquations}</td>
            <td>${ivv.numBranches}</td>
            <td>${ivv.numTailorings}</td>
            <td>${ivv.numLanguages}</td>
            <td>${ivv.numInstructions}</td>
        </tr>
    </c:forEach>
</table>
