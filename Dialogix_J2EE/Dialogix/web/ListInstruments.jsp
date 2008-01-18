<%-- 
    Document   : ListInstruments
    Created on : Jan 17, 2008, 5:13:57 PM
    Author     : Coevtmw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dialogix Instruments</title>
    </head>
    <body>
        <jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter" />
        <table border="1">
            <c:forEach var="instrumentVersion" items="${dataExporter.instrumentVersions}">
                <tr>
                    <td>${instrumentVersion.instrumentName}</td>
                    <td><a href="SPSSscript.jsp?id=${instrumentVersion.instrumentVersionID}">SPSS Import Script</a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
