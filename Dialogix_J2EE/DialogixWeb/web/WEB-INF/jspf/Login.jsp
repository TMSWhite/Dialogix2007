

<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<%
    if (request.getMethod().equals("POST")) {
        dataExporter.doLogin(request.getParameter("userName"), request.getParameter("pwd"));
    }
%>

<FORM action='Dialogix.jsp?action=Login' METHOD=POST>
    <DIV ALIGN='center'>
        <c:if test="${dataExporter.login}">
            <c:if test="${dataExporter.authenticated}">
                <jsp:forward page="../../Dialogix.jsp?action=Contact" />
            </c:if>
            <c:if test="${!dataExporter.authenticated}">
                <H2 style="color: red">
                    Unable to find username or password<br/>
                </H2>                
            </c:if>
        </c:if>
        <H2>User Name</H2><input type='text' name='userName' value=''><BR>
        <H2>Password</H2><input type='password' name='pwd'><BR>
        <input type='submit' value='Submit'>
    </DIV>
</FORM>

