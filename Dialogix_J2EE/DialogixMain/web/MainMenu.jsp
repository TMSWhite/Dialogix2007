<?xml version="1.0" encoding="UTF-8"?>
<!--
Document   : MainMenu
Created on : Apr 3, 2008, 11:18:16 AM
Author     : George
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{MainMenu.page1}" id="page1">
            <webuijsf:html binding="#{MainMenu.html1}" id="html1">
                <webuijsf:head binding="#{MainMenu.head1}" id="head1">
                    <webuijsf:link binding="#{MainMenu.link1}" id="link1" url="/resources/stylesheet.css"/>
                    <webuijsf:link binding="#{MainMenu.link2}" id="link2" url="/resources/layout11_1.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{MainMenu.body1}" id="body1">
                    <br/>
                    <webuijsf:form binding="#{MainMenu.form1}" id="form1">
                        <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}"> 
                            <br />
                            <div id="container">
                                <div id="header">
                                    <div style="position: relative">
                                        <jsp:directive.include file="Header1.jspf"/>
                                    </div>
                                </div>
                                <div id="topBar">
                                    <div style="position: relative">
                                        <jsp:directive.include file="TopNavigation1.jspf"/>
                                    </div>
                                </div>
                                <div id="leftBar">
                                    <div style="position: relative">
                                        <h:panelGrid id="leftSidebarPanel2" style="font-size: small">
                                            <webuijsf:tree binding="#{MainMenu.displayTree}" id="displayTree" style="width: 200px" text="Navigation"/>
                                        </h:panelGrid>
                                    </div>
                                </div>
                                <div id="content">
                                    <webuijsf:staticText binding="#{MainMenu.displayPageText}" id="displayPageText"/>
                                    <h:panelGrid id="contentGridPanel" rendered="#{SessionBean1.selectPage != null}" style="font-size: small">
                                        <jsp:include page="${SessionBean1.selectPage}.jspf"/>
                                    </h:panelGrid>
                                </div>
                                <!-- This clearing element should immediately follow the 
                            #mainContent div in order to force the #container div to 
                            contain all child floats -->
                                <br class="clearfloat"/>
                                <div id="footer">
                                    <div style="position: relative">
                                        <jsp:directive.include file="Footer1.jspf"/>
                                    </div>
                                </div>
                            </div>
                            <!-- end #container  /> -->
                        </webuijsf:markup>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
