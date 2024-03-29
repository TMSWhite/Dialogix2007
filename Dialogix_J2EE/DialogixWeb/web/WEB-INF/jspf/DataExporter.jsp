

<jsp:useBean id="dataExporter" scope="session" class="org.dialogix.export.DataExporter"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
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
                dataExporter.setExtract_data(request.getParameter("extract_data"));
                dataExporter.setShow_irb_view(request.getParameter("show_irb_view"));
                dataExporter.setShow_pi_view(request.getParameter("show_pi_view"));
                dataExporter.setLanguageCode(request.getParameter("language_code"));

                dataExporter.setInstrumentVersionId(request.getParameter("id"));

                dataExporter.init();    // to process all directives
            }
%>
<FORM method='POST' name='myForm' action='Dialogix.jsp?action=DataExporter'>
    <TABLE border='1'>
        <TR>
            <TD colspan='2' align='center'><FONT SIZE="5"><b>Dialogix Data Exporter</b></FONT></TD>
        </TR>
        <TR>
            <TD>Export data for</TD>
            <TD>
                <select name='id'>
                    <c:forEach var="instrumentVersion" items="${dataExporter.instrumentVersions}">                            
                        <option value='${instrumentVersion.instrumentVersionId}' ${(instrumentVersion.instrumentVersionId == dataExporter.instrumentVersionId) ? "selected" : ""}>${instrumentVersion.instrumentName} (${instrumentVersion.instrumentVersion}) -- (${instrumentVersion.numSessions} sessions)</option>
                    </c:forEach>
                </select>                                 
            </TD>
        </TR>
        <TR>
            <TD>Generate the following HTML files</TD>
            <TD>
                <TABLE border='1' width='100%'>
                    <TR>
                        <TD>Logic file for PI</TD>
                        <TD><input name='show_pi_view' type='checkbox' value='1' ${dataExporter.show_pi_view}></TD>
                    </TR>
                    <TR>
                        <TD>Language Code</TD>
                        <TD><input name='language_code' type='text' value='${dataExporter.languageCode}'></TD>
                    </TR>
                </TABLE>
            </TD>
        </TR>
        <TR>
            <TD>For which statistical packages do you want input scripts?</TD>
            <TD>
                <TABLE border='1' width='100%'>
                    <TR>
                        <TD>SAS</TD>
                        <TD><input name='sas_script' type='checkbox' value='1' ${dataExporter.sas_script}></TD>
                    </TR>
                    <TR>
                        <TD>SPSS</TD>
                        <TD><input name='spss_script' type='checkbox' value='1' ${dataExporter.spss_script}></TD>
                    </TR>
                    <TR>
                        <TD>Extract Data</TD>
                        <TD><input name='extract_data' type='checkbox' value='1' ${dataExporter.extract_data}></TD>
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
                    <option value='sort_order_asked' ${(fn:startsWith(dataExporter.sort_order,"sort_order_asked")) ? "selected": ""}>Sort by order asked</option>
                    <option value='sort_varname' ${(fn:startsWith(dataExporter.sort_order,"sort_varname")) ? "selected" : ""}>Sort by variable name</option>
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
                        <TD><input name='value_labels' type='checkbox' value='1' ${dataExporter.value_labels}'></TD>
                    </TR>
                    <TR>
                        <TD>Variable labels</TD>
                        <TD><input name='variable_labels' type='checkbox' value='1' ${dataExporter.variable_labels}></TD>
                    </TR>
                    <TR>
                        <TD>Frequency Distributions</TD>
                        <TD><input name='frequency_distributions' type='checkbox' value='1' ${dataExporter.frequency_distributions}></TD>
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
    <c:if test="${fn:startsWith(dataExporter.spss_script,'checked')}">
        ${dataExporter.spssImportFile}
    </c:if>
    <c:if test="${fn:startsWith(dataExporter.sas_script,'checked')}">
        ${dataExporter.sasImportFile}
    </c:if>
    </pre>
    <c:if test="${fn:startsWith(dataExporter.extract_data,'checked')}">
        <hr>Raw Data<hr>
        <table border='1'>
            <tr><th>Session</th><th>Order</th><th>VarNameId</th><th>VarName</th><th>AnswerCode</th><th>NullFlavorId</th></tr>
            <c:forEach var="isrb" items="${dataExporter.rawResults}">
                <tr>
                    <td>${isrb.instrumentSessionId}</td>
                    <td>${isrb.dataElementSequence}</td>
                    <td>${isrb.varNameId}</td>
                    <td>${isrb.varNameString}</td>
                    <td>${isrb.answerCode}</td>
                    <td>${isrb.nullFlavorId}</td>
                </tr>
            </c:forEach>                
        </table>
        <hr>Transposed (Horizontal) Table<hr>
        ${dataExporter.transposedInstrumentSesionResults}
        <hr>Transposed Data as Tab Separated Values<hr>
        <pre>
                ${dataExporter.transposedInstrumentSesionResultsTSV}
        </pre>
    </c:if>
    <c:if test="${fn:startsWith(dataExporter.show_pi_view,'checked')}">
        <hr>PI View<hr>
        <jsp:include page="InstrumentLogicFile.jsp"/>
    </c:if>
    <%
            }
    %>
</P>
