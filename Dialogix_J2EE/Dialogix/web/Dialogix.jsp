<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="Dialogix.jsp?action=Contact"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dialogix: Run Instruments</title>
    </head>
    <body>
        <table border="0" width="100%">
            <tr>
                <td valign="top" width="10%"><jsp:include page="WEB-INF/jspf/LeftNavBar.jsp" /></td>
                <td valign="top"  width="90%">
                    <jsp:include page="WEB-INF/jspf/${param.action}.jsp" />
                </td>
            </tr>
        </table>
    </body>
</html>
