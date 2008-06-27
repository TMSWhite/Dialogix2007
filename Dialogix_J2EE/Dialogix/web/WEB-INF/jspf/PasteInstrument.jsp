<jsp:useBean id="loader" scope="session" class="org.dialogix.loader.InstrumentLoader"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<form method='POST' action="Dialogix.jsp?action=PasteInstrument">
    <table border='1'>
        <tr>
            <td colspan='2' align='center'>Test Instrument Loader - Full</td>
        </tr>
        <tr>
            <td>Title - this will generate filename</td>
            <td><input type='text' name='title'></td>
        </tr>
        <tr>
            <td colspan='2'>Contents, using full Dialogix model<br/>
                Note: Unicode characters are improperly supported.  To test all unicode, load from file<br/>
                <textarea name='contents' rows='30' cols='100'></textarea>
            </td>
        </tr>
        <tr>
            <td colspan='2' align='center'>
                <input type='submit' title='Submit'>
            </td>
        </tr>
    </table>
</form>
<%
    if (request.getMethod().equals("POST")) {
        loader.setTitle(request.getParameter("title"));
        loader.setVersion(request.getParameter("version"));
        loader.setContents(request.getParameter("contents"));
        
        loader.loadInstrument();
    }
%>
<c:if test="${loader.status == true}">
    ${loader.result}<br/>
    Launch it from <a href="${loader.launchCommand}" target="_blank">here</a><br>
</c:if>
<c:if test="${loader.hasLoadErrors == true}">
    <p>
        There were errors and/or warnings loading ${loader.title}<br>
        ${loader.loadErrors}
    </p>
</c:if>
