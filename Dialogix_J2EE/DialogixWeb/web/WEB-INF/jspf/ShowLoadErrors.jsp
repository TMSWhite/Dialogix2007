

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionId(request.getParameter("id"));
    }        
%>
<table border="1">
    <tr><th colspan="4" align="CENTER">Load Errors for ${dataExporter.instrumentTitle}</th></tr>
    <tr>
        <th>Row</th>
        <th>Column</th>
        <th>Source Text</th>
        <th>Error</th>
    </tr>
    <c:forEach var="ile" items="${dataExporter.instrumentLoadErrors}">
        <tr>
            <td>
                ${ile.sourceRow + 1}
            </td>
            <td>
                ${ile.sourceColumn + 1}
            </td>
            <td>
                ${ile.sourceText}
            </td>
            <td>
                ${ile.errorMessage}
            </td>
        </tr>
    </c:forEach>
</table>
