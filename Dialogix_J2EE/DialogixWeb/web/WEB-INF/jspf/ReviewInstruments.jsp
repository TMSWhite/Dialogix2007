

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<table border="1">
    <tr>
        <th colspan='9' align='center'>Instruments</th>
    </tr>
    <tr>
        <th>Name</th>
        <th>Version</th>
        <th>V:G:Q:E:B:T</th>
        <th>Errors</th>
        <th>Status</th>
        <th>Sessions</th>
        <th>Languages</th>        
        <th>All Results</th>
        <th>XML Export</th>
    </tr>    
    <c:forEach var="ivv" items="${dataExporter.instrumentVersions}">
        <tr>
            <td>
                <a 
                    href="Dialogix.jsp?action=InstrumentLogicFile&id=${ivv.instrumentVersionId}"
                    title="View Instrument Definition">
                    ${ivv.instrumentName}
                </a>
            </td>
            <td>
                <a 
                    href="Dialogix.jsp?action=InstrumentSinglePageView&id=${ivv.instrumentVersionId}"
                    title="View Single Page User View of Instrument">
                    ${ivv.instrumentVersion}
                </a>    
                <a href="Dialogix.jsp?action=Run&id=${ivv.instrumentVersionId}&lang=en"
                   title="Prototype Instrument Logic">                      
                    (*)
                </a>                 
            </td>
            <td>
                ${ivv.numVars}v ${ivv.numGroups}g ${ivv.numQuestions}q ${ivv.numEquations}e ${ivv.numBranches}b ${ivv.numTailorings}t
            </td>
            <td>
                <c:if test="${ivv.numErrors > 0}">
                    <a href="Dialogix.jsp?action=ShowLoadErrors&id=${ivv.instrumentVersionId}"
                       title="Show Load  Errors for Instrument">                      
                        ${ivv.numErrors}
                    </a>                      
                </c:if>
            </td>
            <td>
                ${ivv.instrumentStatus}
            </td>
            <td>
                <c:if test="${ivv.numSessions > 0}">
                    <a 
                        href="Dialogix.jsp?action=ListInstrumentSessions&id=${ivv.instrumentVersionId}"
                        title="View Session Status for this instrument">
                        ${ivv.numSessions}
                    </a>
                </c:if>
                <c:if test="${ivv.numSessions == 0}">
                    &nbsp;
                </c:if>
            </td>
            <td>
                <c:if test="${ivv.numLanguages > 1}">
                    <a 
                        href="Dialogix.jsp?action=InstrumentTranslationFile&id=${ivv.instrumentVersionId}"
                        title="View Translation File">
                        ${ivv.numLanguages}
                    </a>                    
                </c:if>
                <c:if test="${ivv.numLanguages == 1}">
                    1
                </c:if>
            </td>
            <td>
                <c:if test="${ivv.numSessions > 0}">                 
                    <a 
                        href="Dialogix.jsp?action=InstrumentFinalResults&id=${ivv.instrumentVersionId}"
                        title="View final data from all sessions of this instrument">
                        Results
                    </a>
                </c:if>
                <c:if test="${ivv.numSessions == 0}">
                    &nbsp;
                </c:if>
            </td>
            <td>
                <a 
                    href="Dialogix.jsp?action=InstrumentAsVoiceXML&id=${ivv.instrumentVersionId}"
                    title="View VoiceXML Translation">
                    voice
                </a>,
                <a 
                    href="Dialogix.jsp?action=InstrumentAsXForms&id=${ivv.instrumentVersionId}"
                    title="View XForms Translation">
                    xforms
                </a>                        
            </td>
        </tr>
    </c:forEach>
</table>
