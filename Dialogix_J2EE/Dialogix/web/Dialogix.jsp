<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<jsp:directive.page errorPage="Dialogix.jsp?action=Contact" />

<%
    dataExporter.setMenuSelection(request.getParameter("action"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dialogix: ${param.action}</title>
    </head>
    <body>
        <table border="0" width="100%">
            <tr>
                <td valign="top" width="10%">
                    <!-- embedding LeftNavBar -->
                    <img src="images/dialogoSmall.jpg" height="40" width="89"><br/>
                    <br/>
                    <hr/>
                    <c:if test="${!dataExporter.authenticated}">
                        <a href="Dialogix.jsp?action=Login">Login</a><br/>
                    </c:if>
                    <c:if test="${dataExporter.authenticated}">
                        Welcome ${dataExporter.person.firstName} ${dataExporter.person.lastName}<br/>
                    </c:if>
                    <c:forEach var="menu" items="${dataExporter.menus}">
                        <c:if test="${menu.menuType == 0}">
                            <hr/>
                            <b>${menu.displayText}:</b><br/>
                        </c:if>
                        <c:if test="${menu.menuType == 1}">
                            <a href="Dialogix.jsp?action=${menu.menuName}">${menu.displayText}</a><br/>
                        </c:if>
                    </c:forEach>
                    <c:if test="${dataExporter.authenticated}">
                        <hr/>
                        <a href="Dialogix.jsp?action=Logout">Logout</a><br/> 
                    </c:if>        
                </td>
                <td valign="top"  width="90%">
                    <c:if test="${dataExporter.authenticatedForMenu}">
                        <jsp:include page="WEB-INF/jspf/${param.action}.jsp" />
                    </c:if>
                    <c:if test="${!dataExporter.authenticatedForMenu}">
                        You are not authorized for action ${param.action}.
                    </c:if>
                </td>
            </tr>
        </table>
    </body>
</html>
