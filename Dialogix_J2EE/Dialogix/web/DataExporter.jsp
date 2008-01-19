<%-- 
    Document   : DataExporter
    Created on : Jan 18, 2008, 8:28:22 AM
    Author     : Coevtmw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<jsp:useBean id="dialogix" scope="session" class="org.dialogix.util.DialogixParserTool" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
            if (request.getMethod().equals("POST")) {
                dataExporter.setExclude_regex(request.getParameter("exclude_regex"));
                dataExporter.setFrequency_distributions(request.getParameter("frequency_distributions"));
                dataExporter.setSort_order(request.getParameter("sort_order"));
                dataExporter.setSas_huh(request.getParameter("sas_huh"));
                dataExporter.setSas_invalid(request.getParameter("sas_invalid"));
                dataExporter.setSas_na(request.getParameter("sas_na"));
                dataExporter.setSas_refused(request.getParameter("sas_refused"));
                dataExporter.setSas_script(request.getParameter("sas_script"));
                dataExporter.setSas_unasked(request.getParameter("sas_unasked"));
                dataExporter.setSas_unknown(request.getParameter("sas_unknown"));
                dataExporter.setSpss_huh(request.getParameter("spss_huh"));
                dataExporter.setSpss_invalid(request.getParameter("spss_invalid"));
                dataExporter.setSpss_na(request.getParameter("spss_na"));
                dataExporter.setSpss_refused(request.getParameter("spss_refused"));
                dataExporter.setSpss_script(request.getParameter("spss_script"));
                dataExporter.setSpss_unasked(request.getParameter("spss_unasked"));
                dataExporter.setSpss_unknown(request.getParameter("spss_unknown"));
                dataExporter.setValue_labels(request.getParameter("value_labels"));
                dataExporter.setVariable_labels(request.getParameter("variable_labels"));

                dataExporter.setInstrumentVersionID(request.getParameter("id"));
            }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dialogix Data Exporter</title>
    </head>
    <body>
        <H1>Dialogix Data Exporter</H1>
        <B>Memory: Total=${dialogix.totalMemory}, Free=${dialogix.freeMemory}, Used=${dialogix.memoryUsed}</B>        
        <FORM method='POST' name='myForm' action='DataExporter.jsp'>
            <TABLE border='1'>
                <TR>
                    <TD>Export data for</TD>>
                    <TD>
                        <select name='id'>
                            <c:forEach var="instrumentVersion" items="${dataExporter.instrumentVersions}">                            
                                <option value='${instrumentVersion.instrumentVersionID}'>${instrumentVersion.instrumentName}</option>
                            </c:forEach>
                        </select>                                 
                    </TD>
                </TR>
                <TR>
                    <TD>For which statistical packages do you want input scripts?</TD>
                    <TD>
                        <TABLE border='1' width='100%'>
                            <TR>
                                <TD>SAS</TD>
                                <TD><input name='sas_script' type='checkbox' value='1' checked='${dataExporter.sas_script}' ></TD>
                            </TR>
                            <TR>
                                <TD>SPSS</TD>
                                <TD><input name='spss_script' type='checkbox' value='1' checked='${dataExporter.spss_script}'></TD>
                            </TR>
                        </TABLE>
                    </TD>                    
                </TR>            		
                <TR>
                    <TD>How should missing values be coded?</TD>
                    <TD>
                        <TABLE border='1' width='100%'>
                            <TR><TH>Type</TH><TH>SPSS</TH><TH>SAS</TH></TR>
                            <TR><TD>Unasked</TD><TD><input name='spss_unasked' type='text' value='${dataExporter.spss_unasked}'></TD><TD><input name='sas_unasked' type='text' value='${dataExporter.sas_unasked}'></TD></TR>
                            <TR><TD>N/A</TD><TD><input name='spss_na' type='text' value='${dataExporter.spss_na}'></TD><TD><input name='sas_na' type='text' value='${dataExporter.sas_na}'></TD></TR>
                            <TR><TD>Refused</TD><TD><input name='spss_refused' type='text' value='${dataExporter.spss_refused}'></TD><TD><input name='sas_refused' type='text' value='${dataExporter.sas_refused}'></TD></TR>
                            <TR><TD>Unknown</TD><TD><input name='spss_unknown' type='text' value='${dataExporter.spss_unknown}'></TD><TD><input name='sas_unknown' type='text' value='${dataExporter.sas_unknown}'></TD></TR>
                            <TR><TD>Not Understood</TD><TD><input name='spss_huh' type='text' value='${dataExporter.spss_huh}'></TD><TD><input name='sas_huh' type='text' value='${dataExporter.sas_huh}'></TD></TR>            
                            <TR><TD>Invalid</TD><TD><input name='spss_invalid' type='text' value='${dataExporter.spss_invalid}'></TD><TD><input name='sas_invalid' type='text' value='${dataExporter.sas_invalid}'></TD></TR>                            
                        </TABLE>
                    </TD>
                </TR>
                <TR>
                    <TD>In what order should the columns appear?</TD>
                    <TD>
                        <select name='sort_order'>
                            <option value='sort_order_asked'>Sort by order asked</option>
                            <option value='sort_varname'>Sort by variable name</option>
                        </select>
                    </TD>
                </TR>
                <TR>
                    <TD>Optionally exclude variable names matching pattern</TD>
                    <TD><input name='exclude_regex' type='text' value='${dataExporter.exclude_regex}' size='40'></TD>
                </TR>
                <TR>
                    <TD>What features should be available in the SAS/SPSS scripts?</TD>
                    <TD>
                        <TABLE border='1' width='100%'>
                            <TR>
                                <TD>Value labels</TD>
                                <TD><input name='value_labels' type='checkbox' value='1' checked='${dataExporter.value_labels}'></TD>
                            </TR>
                            <TR>
                                <TD>Variable labels</TD>
                                <TD><input name='variable_labels' type='checkbox' value='1' checked='${dataExporter.variable_labels}'></TD>
                            </TR>
                            <TR>
                                <TD>Frequency Distributions</TD>
                                <TD><input name='frequency_distributions' type='checkbox' value='1' checked='${dataExporter.frequency_distributions}'></TD>
                            </TR>                            
                        </TABLE>
                    </TD>
                </TR>
                <TR>
                    <TD colspan='2' align='center'><input name='submit' type='submit'><input name='clear' type='reset'></TD>
                </TR>
            </TABLE>
        </FORM>        
        <P>
            <%
            if (request.getMethod().equals("POST")) {
            %>
            <pre>
                ${dataExporter.spssImportFile}
            </pre>
            <%
            }
            %>
        </P>
    </body>
</html>