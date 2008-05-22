<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Main
    Created on : May 8, 2008, 12:25:19 AM
    Author     : Coevtmw
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page id="page1">
            <webuijsf:html id="html1">
                <webuijsf:head id="head1">
                    <webuijsf:link id="link1" url="/resources/stylesheet.css"/>
                    <webuijsf:link id="link2" url="/resources/dialogix.css"/>
                </webuijsf:head>
                <webuijsf:body id="body1" style="-rave-layout: grid">
                    <webuijsf:panelLayout id="outerPanel" panelLayout="flow" style="" styleClass="container">
                        <div class="header" id="header">
                            <h:panelGrid columns="3" id="gridPanel1" styleClass="header">
                                <webuijsf:image align="left" id="logo" url="/resources/dialogoSmall.jpg"/>
                                <webuijsf:staticText id="mainTitle" styleClass="h1" text="Dialogix Management System"/>
                                <webuijsf:staticText id="logout" style="" text="Logout"/>
                            </h:panelGrid>
                        </div>
                        <div class="topNav" id="topNav">
                            <h:panelGrid columns="5" id="topNavigationGrid">
                                <webuijsf:hyperlink id="home" text="Home" visible="true"/>
                                <webuijsf:hyperlink id="login" rendered="true" text="Login" visible="true"/>
                            </h:panelGrid>
                        </div>
                        <h:panelGrid columns="2" id="gridPanel2">
                            <div class="leftNav" id="leftNav">
                                <webuijsf:accordion id="accordion1" style="" styleClass="leftNav"/>
                            </div>
                            <div class="mainBody" id="mainBody">
                                <webuijsf:panelLayout id="layoutPanel3" panelLayout="flow" style="" styleClass="mainBody"/>
                            </div>
                        </h:panelGrid>
                        <webuijsf:panelLayout id="footerPanel" panelLayout="flow" style="" styleClass="footer">
                            <webuijsf:staticText id="staticText1" style="" text="OMH"/>
                        </webuijsf:panelLayout>
                    </webuijsf:panelLayout>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
    <webuijsf:form id="form1"/>
</jsp:root>
