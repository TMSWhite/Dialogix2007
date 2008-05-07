<%-- 
    Document   : LoadInstrument-Ruby
    Created on : Apr 8, 2008, 4:15:25 PM
    Author     : George
--%>

<jsp:useBean id="loader" scope="session" class="org.dialogix.loader.InstrumentLoader"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%
    if (request.getMethod().equals("POST")) {
        loader.setTitle(request.getParameter("title"));
        loader.setVersion(request.getParameter("version"));
        loader.setContents(request.getParameter("contents"));
        
        loader.loadInstrumentRuby();
    }
%>
<c:if test="${loader.status == true}">
    ${loader.result}<br/>
    Launch it from <a href="${loader.rubyLaunchCommand}" target="_blank">here</a><br>
</c:if>
<c:if test="${loader.hasLoadErrors == true}">
    <p>
        There were errors and/or warnings loading ${loader.title}<br>
        ${loader.loadErrors}
    </p>
</c:if>
