<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1

            response.setHeader("Pragma", "no-cache"); //HTTP 1.0

            response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>
<body>

<form method="POST" action="j_security_check">
<table>
<tr><td>User name:</td><td><input type="text" name="j_username" /></td></tr>
<tr><td>Password:</td><td><input type="password" name="j_password" /></td></tr>
<tr><td><input type="submit" value="Login" /></td></tr>
</table>
</form>


</body>
</html>
