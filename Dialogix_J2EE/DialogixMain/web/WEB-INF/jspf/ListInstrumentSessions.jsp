<%-- 
    Document   : ListInstrumentSessions
    Created on : Apr 8, 2008, 2:21:59 PM
    Author     : George
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.main.DataExporter" />
<%
    if (request.getMethod().equals("GET")) {
        dataExporter.setInstrumentVersionID(request.getParameter("id"));
    }        
%>
<table border="1">
    <tr>
        <th colspan='4' align='center'>Sessions for ${dataExporter.instrumentTitle}</th>
    </tr>
    <tr>
        <th>Start Date</th>
        <th>Last Access Time</th>
        <th>Completed</th>
        <th># Pages Viewed</th>
    </tr>    
    <c:forEach var="is" items="${dataExporter.instrumentSessions}">
        <tr>
            <td>
                ${is.startTime}
            </td>
            <td>
                ${is.lastAccessTime}
            </td>
            <td>
                ${is.finished}
            </td>
            <td>
                <c:if test="${is.displayNum > 0}">
                    <a 
                        href="InstrumentSessionRecap.jsp?id=${dataExporter.instrumentVersionID}&sess=${is.instrumentSessionID}"
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