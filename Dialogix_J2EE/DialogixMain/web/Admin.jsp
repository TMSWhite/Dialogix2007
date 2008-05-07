<?xml version="1.0" encoding="UTF-8"?>
<!--
Document   : Admin
Created on : Apr 25, 2008, 8:45:53 AM
Author     : George
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{Admin.page1}" id="page1">
            <webuijsf:html binding="#{Admin.html1}" id="html1">
                <webuijsf:head binding="#{Admin.head1}" id="head1">
                    <webuijsf:link binding="#{Admin.link1}" id="link1" url="/resources/stylesheet.css"/>
                    <webuijsf:link binding="#{Admin.link2}" id="link2" url="/resources/layout11_1.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Admin.body1}" id="body1">
                    <br/>
                    <webuijsf:form binding="#{Admin.form1}" id="form1">
                        <div id="container">
                            <!-- This clearing element should immediately follow the #mainContent div in order to force the #container div to contain all child floats -->
                            <!-- end #container -->
                            <webuijsf:panelLayout binding="#{Admin.mainContentPanel}" id="mainContentPanel" panelLayout="flow" styleClass="mainContent">
                                <webuijsf:table augmentTitle="false" id="table1" title="Table" width="450">
                                    <webuijsf:tableRowGroup id="tableRowGroup1" rows="10" sourceData="#{Admin.defaultTableDataProvider}" sourceVar="currentRow">
                                        <webuijsf:tableColumn headerText="column1" id="tableColumn1" sort="column1">
                                            <webuijsf:staticText id="staticText2" text="#{currentRow.value['column1']}"/>
                                        </webuijsf:tableColumn>
                                        <webuijsf:tableColumn headerText="column2" id="tableColumn2" sort="column2">
                                            <webuijsf:staticText id="staticText3" text="#{currentRow.value['column2']}"/>
                                        </webuijsf:tableColumn>
                                        <webuijsf:tableColumn headerText="column3" id="tableColumn3" sort="column3">
                                            <webuijsf:staticText id="staticText4" text="#{currentRow.value['column3']}"/>
                                        </webuijsf:tableColumn>
                                    </webuijsf:tableRowGroup>
                                </webuijsf:table>
                            </webuijsf:panelLayout>
                            <br class="clearfloat"/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
