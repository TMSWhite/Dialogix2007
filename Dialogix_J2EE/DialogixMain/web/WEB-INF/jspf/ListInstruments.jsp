<%-- 
    Document   : ListInstruments
    Created on : Apr 8, 2008, 2:23:16 PM
    Author     : George
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.main.DataExporter" />
<table border="1">
    <tr>
        <th colspan='6' align='center'>Instruments</th>
    </tr>
    <tr>
        <th>Name</th>
        <th>Version</th>
        <th>Sessions</th>
        <th>Languages</th>        
        <th>All Results</th>
        <th>XML Export</th>
    </tr>    
    <c:forEach var="ivv" items="${dataExporter.instrumentVersions}">
        <tr>
            <td>
                <a 
                    href="InstrumentLogicFile.jsp?id=${ivv.instrumentVersionID}"
                    title="View Instrument Definition">
                    ${ivv.instrumentName}
                </a>
            </td>
            <td>${ivv.instrumentVersion}</td>
            <td>
                <c:if test="${ivv.numSessions > 0}">
                    <a 
                        href="ListInstrumentSessions.jsp?id=${ivv.instrumentVersionID}"
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
                        href="InstrumentTranslationFile.jsp?id=${ivv.instrumentVersionID}"
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
                        href="InstrumentAllResults.jsp?id=${ivv.instrumentVersionID}"
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
                    href="InstrumentAsVoiceXML.jsp?id=${ivv.instrumentVersionID}"
                    title="View VoiceXML Translation">
                    voice
                </a>
                <a 
                    href="InstrumentAsXForms.jsp?id=${ivv.instrumentVersionID}"
                    title="View XForms Translation">
                    xforms
                </a>                        
            </td>
        </tr>
    </c:forEach>
</table>
