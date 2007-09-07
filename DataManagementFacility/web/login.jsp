<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ui="http://www.sun.com/web/ui">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <ui:page binding="#{login.page1}" id="page1">
            <ui:html binding="#{login.html1}" id="html1">
                <ui:head binding="#{login.head1}" id="head1">
                    <ui:link binding="#{login.link1}" id="link1" url="/resources/stylesheet.css"/>
                    <meta content="text/html; charset=iso-8859-1" http-equiv="Content-Type"/>
                    <title>Untitled Document</title>
                    <style type="text/css">&lt;!--
.style1 {
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 24px;
}
.style4 {font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px; }
.style5 {font-size: 24px}
--&gt;
</style>
                </ui:head>
                <ui:body binding="#{login.body1}" id="body1" style="">
                    <div align="left">
                        <ui:form binding="#{login.form2}" id="form2"/>
                        <table border="0" width="100%">
                            <tr>
                                <td width="255">
                                    <h:graphicImage binding="#{login.image1}" height="81" id="image1"
                                        value="../../Documents%20and%20Settings/istcgxl/My%20Documents/Creator/Projects/DataDemo/web/resources/dialogo.jpg" width="180"/>
                                </td>
                                <td width="531">
                                    <div align="center" class="style1 style5">Data Management Facility</div>
                                </td>
                                <td width="189">&amp;nbsp;</td>
                            </tr>
                            <tr>
                                <td>&amp;nbsp;</td>
                                <td width="531">
                                    <div align="right" class="style4">User Name</div>
                                </td>
                                <td>
                                    <h:inputText binding="#{login.textfield}" id="textfield" styleClass="style4"/>
                                </td>
                            </tr>
                            <tr>
                                <td>&amp;nbsp;</td>
                                <td width="531">
                                    <div align="right" class="style4">Password</div>
                                </td>
                                <td>
                                    <h:inputText binding="#{login.textfield_}" id="textfield_" styleClass="style4"/>
                                    <h:commandButton binding="#{login.submit}" id="submit" styleClass="style4" value="Login"/>
                                    <div style="position: relative">
                                        <jsp:directive.include file="HeaderFrag.jspf"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </ui:body>
            </ui:html>
        </ui:page>
    </f:view>
</jsp:root>
