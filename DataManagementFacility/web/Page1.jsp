<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ui="http://www.sun.com/web/ui">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <ui:page binding="#{Page1.page1}" id="page1">
            <ui:html binding="#{Page1.html1}" id="html1">
                <ui:head binding="#{Page1.head1}" id="head1">
                    <ui:link binding="#{Page1.link1}" id="link1" url="/resources/stylesheet.css"/>
                </ui:head>
                <ui:body binding="#{Page1.body1}" id="body1" style="-rave-layout: grid">
                    <ui:form binding="#{Page1.form1}" id="form1">
                        <ui:panelLayout binding="#{Page1.layoutPanel2}" id="layoutPanel2" style="height: 142px; left: 0px; top: 0px; position: absolute; width: 1006px; -rave-layout: grid">
                            <ui:staticText binding="#{Page1.staticText1}" id="staticText1"
                                style="font-family: Geneva,Arial,Helvetica,sans-serif; font-size: 24px; left: 288px; top: 48px; position: absolute" text="Data Management Facility **"/>
                            <ui:image binding="#{Page1.image1}" height="96" id="image1" url="/resources/dialogo.jpg" width="213"/>
                            <h:panelGrid binding="#{Page1.gridPanel6}" columns="8" id="gridPanel6" width="864">
                                <ui:staticText binding="#{Page1.staticText57}" id="staticText57"/>
                                <ui:staticText binding="#{Page1.staticText58}" id="staticText58"/>
                                <ui:staticText binding="#{Page1.staticText56}" id="staticText56"/>
                                <ui:staticText binding="#{Page1.staticText2}" id="staticText2" text="User Name"/>
                                <ui:textField binding="#{Page1.textField1}" columns="10" id="textField1" immediate="true" style="font-size: 12px" valueChangeListener="#{Page1.textField1_processValueChange}"/>
                                <ui:staticText binding="#{Page1.staticText3}" id="staticText3" text="Password"/>
                                <ui:passwordField binding="#{Page1.textField2}" id="textField2" immediate="true"/>
                                <ui:button action="#{Page1.loginButton_action}" binding="#{Page1.loginButton}" id="loginButton" text="Login"/>
                            </h:panelGrid>
                            <ui:pageSeparator binding="#{Page1.pageSeparator1}" id="pageSeparator1" style="font-size: 12px"/>
                            <ui:hyperlink action="#{Page1.hyperlink1_action}" binding="#{Page1.hyperlink1}" id="hyperlink1"
                                style="position: absolute; left: 816px; top: 96px" text="logout" visible="false"/>
                        </ui:panelLayout>
                        <ui:tabSet binding="#{Page1.tabSet1}" id="tabSet1" selected="tab2" style="font-family: 'Verdana','Arial','Helvetica',sans-serif; height: 382px; left: 0px; top: 144px; position: absolute; width: 1222px">
                            <ui:tab action="#{Page1.tab1_action}" binding="#{Page1.tab1}" id="tab1" text="Instruments">
                                <ui:panelLayout binding="#{Page1.layoutPanel1}" id="layoutPanel1" style="width: 100%;">
                                    <h:panelGrid binding="#{Page1.gridPanel1}" columns="2" id="gridPanel1">
                                        <ui:staticText binding="#{Page1.staticText5}" id="staticText5" text="You have access to the following Data:"/>
                                        <ui:staticText binding="#{Page1.staticText4}" id="staticText4"/>
                                        <h:panelGrid binding="#{Page1.gridPanel3}" columns="2" id="gridPanel3" style="width: 100%; height: 100%;">
                                            <ui:dropDown binding="#{Page1.dropDown1}" converter="#{Page1.dropDown1Converter}" id="dropDown1"
                                                items="#{Page1.instrument_versionDataProvider.options['instrument_version.instrument_version_id,instrument_version.instance_table_name']}" valueChangeListener="#{Page1.dropDown1_processValueChange}"/>
                                            <ui:button action="#{Page1.button2_action}" binding="#{Page1.button2}" id="button2" text="Select"/>
                                            <h:outputLink binding="#{Page1.hyperlink3}" id="hyperlink3" value="http://www.sun.com/jscreator">
                                                <h:outputText binding="#{Page1.hyperlink3Text}" id="hyperlink3Text" value="Hyperlink"/>
                                            </h:outputLink>
                                        </h:panelGrid>
                                        <ui:staticText binding="#{Page1.staticText33}" id="staticText33"/>
                                        <h:panelGrid binding="#{Page1.gridPanel2}" columns="4" id="gridPanel2" style="width: 100%; height: 100%;">
                                            <ui:staticText binding="#{Page1.staticText7}" id="staticText7" style="font-weight: bold" text="Total Groups"/>
                                            <ui:staticText binding="#{Page1.staticText8}" id="staticText8" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText9}" id="staticText9" style="font-weight: bold" text="Number of Sesions"/>
                                            <ui:staticText binding="#{Page1.staticText10}" id="staticText10" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText15}" id="staticText15" style="font-weight: bold" text="Min Group Finished"/>
                                            <ui:staticText binding="#{Page1.staticText16}" id="staticText16" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText17}" id="staticText17" style="font-weight: bold" text="Min. Time"/>
                                            <ui:staticText binding="#{Page1.staticText18}" id="staticText18" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText19}" id="staticText19" style="font-weight: bold" text="Max. Group Finished"/>
                                            <ui:staticText binding="#{Page1.staticText20}" id="staticText20" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText21}" id="staticText21" style="font-weight: bold" text="Max.Time"/>
                                            <ui:staticText binding="#{Page1.staticText22}" id="staticText22" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText23}" id="staticText23" style="font-weight: bold" text="Avg. Group Finished"/>
                                            <ui:staticText binding="#{Page1.staticText24}" id="staticText24" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText25}" id="staticText25" style="font-weight: bold" text="Average Time"/>
                                            <ui:staticText binding="#{Page1.staticText26}" id="staticText26" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText11}" id="staticText11" style="font-weight: bold" text="Std. Dev."/>
                                            <ui:staticText binding="#{Page1.staticText13}" id="staticText13" text="0"/>
                                            <ui:staticText binding="#{Page1.staticText27}" id="staticText27" style="font-weight: bold" text="Std. Dev."/>
                                            <ui:staticText binding="#{Page1.staticText28}" id="staticText28" text="0"/>
                                        </h:panelGrid>
                                        <ui:staticText binding="#{Page1.staticText6}" id="staticText6"/>
                                        <ui:table augmentTitle="false" binding="#{Page1.table2}" deselectSingleButton="true" id="table2" paginateButton="true"
                                            paginationControls="true" sortPanelToggleButton="true" title="Sessions to Date" width="268">
                                            <script><![CDATA[
/* ----- Functions for Table Preferences Panel ----- */
/*
 * Toggle the table preferences panel open or closed
 */
function togglePreferencesPanel() {
  var table = document.getElementById("form1:table2");
  table.toggleTblePreferencesPanel();
}
/* ----- Functions for Filter Panel ----- */
/*
 * Return true if the filter menu has actually changed,
 * so the corresponding event should be allowed to continue.
 */
function filterMenuChanged() {
  var table = document.getElementById("form1:table2");
  return table.filterMenuChanged();
}
/*
 * Toggle the custom filter panel (if any) open or closed.
 */
function toggleFilterPanel() {
  var table = document.getElementById("form1:table2");
  return table.toggleTableFilterPanel();
}
/* ----- Functions for Table Actions ----- */
/*
 * Initialize all rows of the table when the state
 * of selected rows changes.
 */
function initAllRows() {
  var table = document.getElementById("form1:table2");
  table.initAllRows();
}
/*
 * Set the selected state for the given row groups
 * displayed in the table.  This functionality requires
 * the 'selectId' of the tableColumn to be set.
 *
 * @param rowGroupId HTML element id of the tableRowGroup component
 * @param selected Flag indicating whether components should be selected
 */
function selectGroupRows(rowGroupId, selected) {
  var table = document.getElementById("form1:table2");
  table.selectGroupRows(rowGroupId, selected);
}
/*
 * Disable all table actions if no rows have been selected.
 */
function disableActions() {
  // Determine whether any rows are currently selected
  var table = document.getElementById("form1:table2");
  var disabled = (table.getAllSelectedRowsCount() > 0) ? false : true;
  // Set disabled state for top actions
  document.getElementById("form1:table2:tableActionsTop:deleteTop").setDisabled(disabled);
  // Set disabled state for bottom actions
  document.getElementById("form1:table2:tableActionsBottom:deleteBottom").setDisabled(disabled);
}]]></script>
                                            <ui:tableRowGroup binding="#{Page1.tableRowGroup2}" id="tableRowGroup2" rows="10"
                                                sourceData="#{Page1.instrument_sessionDataProvider2}" sourceVar="currentRow">
                                                <ui:tableColumn binding="#{Page1.tableColumn6}" headerText="start_time" id="tableColumn6" sort="instrument_session.start_time">
                                                    <ui:staticText binding="#{Page1.staticText29}" id="staticText29" text="#{currentRow.value['instrument_session.start_time']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn7}" headerText="end_time" id="tableColumn7" sort="instrument_session.end_time">
                                                    <ui:staticText binding="#{Page1.staticText30}" id="staticText30" text="#{currentRow.value['instrument_session.end_time']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn11}" headerText="first_group" id="tableColumn11" sort="instrument_session.first_group">
                                                    <ui:staticText binding="#{Page1.staticText72}" id="staticText72" text="#{currentRow.value['instrument_session.first_group']}"/>
                                                </ui:tableColumn>
                                            </ui:tableRowGroup>
                                        </ui:table>
                                        <ui:staticText binding="#{Page1.staticText14}" id="staticText14"/>
                                    </h:panelGrid>
                                </ui:panelLayout>
                            </ui:tab>
                            <ui:tab action="#{Page1.tab2_action}" binding="#{Page1.tab2}" id="tab2" text="Upload">
                                <ui:panelLayout binding="#{Page1.layoutPanel3}" id="layoutPanel3" style="height: 175px; position: relative; width: 100%; -rave-layout: grid">
                                    <h:panelGrid binding="#{Page1.gridPanel5}" columns="3" id="gridPanel5" style="height: 96px" width="672">
                                        <ui:staticText binding="#{Page1.staticText46}" id="staticText46" text="Select Upload File"/>
                                        <ui:upload binding="#{Page1.fileUpload1}" id="fileUpload1" valueChangeListener="#{Page1.fileUpload1_processValueChange}"/>
                                        <ui:staticText binding="#{Page1.staticText47}" id="staticText47"/>
                                        <ui:staticText binding="#{Page1.staticText50}" id="staticText50" text="Instrument Description"/>
                                        <ui:textArea binding="#{Page1.instrumentDescription}" id="instrumentDescription"/>
                                        <ui:staticText binding="#{Page1.staticText52}" id="staticText52"/>
                                        <ui:staticText binding="#{Page1.staticText54}" id="staticText54" text="Deploy to Site"/>
                                        <ui:dropDown binding="#{Page1.dropDown8}" converter="#{Page1.dropDown8Converter}" id="dropDown8" items="#{Page1.sandboxDataProvider3.options['sandbox.id,sandbox.name']}"/>
                                        <ui:staticText binding="#{Page1.staticText55}" id="staticText55"/>
                                        <ui:staticText binding="#{Page1.staticText53}" id="staticText53"/>
                                        <ui:button action="#{Page1.button4_action}" binding="#{Page1.button4}" id="button4" text="Upload File"/>
                                        <ui:staticText binding="#{Page1.staticText49}" id="staticText49"/>
                                        <ui:staticText binding="#{Page1.staticText87}" id="staticText87"/>
                                        <ui:staticText binding="#{Page1.staticText51}" id="staticText51"/>
                                        <ui:staticText binding="#{Page1.staticText48}" id="staticText48"/>
                                    </h:panelGrid>
                                    <ui:textArea binding="#{Page1.status}" id="status" style="position: absolute; left: 144px; top: 168px"/>
                                    <ui:alert binding="#{Page1.alert1}"
                                        detail="Please edit the Major and/or Minor Version &#xa; Reserved words in you spreadsheet." id="alert1"
                                        linkAction="#{Page1.alert1_action}" linkTarget="_self" linkText="Close"
                                        style="left: 288px; top: 72px; position: relative" summary="This schedule version already exists. &#xa;" type="error"/>
                                    <ui:alert binding="#{Page1.alert2}" detail="All data from this point on will be stored in a new table." id="alert2"
                                        linkAction="#{Page1.alert2_action}" linkTarget="_self" linkText="Close"
                                        style="left: 336px; top: 168px; position: absolute" summary="The Major Version has Changed!" type="error"/>
                                    <ui:alert binding="#{Page1.alert3}"
                                        detail="Either rename the variables in your spreadsheet&#xa; or change the Major Version Number." id="alert3"
                                        linkAction="#{Page1.alert3_action}" linkText="Close" style="left: 384px; top: 216px; position: relative" summary="Variable Names are Inconsistant"/>
                                </ui:panelLayout>
                            </ui:tab>
                            <ui:tab action="#{Page1.tab3_action}" binding="#{Page1.tab3}" id="tab3" text="Export">
                                <ui:panelLayout binding="#{Page1.layoutPanel4}" id="layoutPanel4" style="vertical-align: top; width: 100%">
                                    <h:panelGrid binding="#{Page1.gridPanel4}" columns="2" id="gridPanel4">
                                        <ui:staticText binding="#{Page1.staticText34}" id="staticText34" text="Data Set"/>
                                        <ui:dropDown binding="#{Page1.dropDown2}" id="dropDown2" items="#{Page1.dropDown2DefaultOptions.options}"/>
                                        <ui:staticText binding="#{Page1.staticText43}" id="staticText43" text="Start Date"/>
                                        <ui:calendar binding="#{Page1.calendar1}" columns="10" id="calendar1" selectedDate="#{SessionBean1.start_date}" validator="#{Page1.calendar1_validate}"/>
                                        <ui:staticText binding="#{Page1.staticText44}" id="staticText44" text="End Date"/>
                                        <ui:calendar binding="#{Page1.calendar2}" columns="10" id="calendar2" selectedDate="#{SessionBean1.end_date}" validator="#{Page1.calendar2_validate}"/>
                                        <ui:staticText binding="#{Page1.staticText35}" id="staticText35" text="Format"/>
                                        <ui:dropDown binding="#{Page1.dropDown3}" id="dropDown3" items="#{Page1.dropDown3DefaultOptions.options}"/>
                                        <ui:staticText binding="#{Page1.staticText36}" id="staticText36" text="Compression"/>
                                        <ui:dropDown binding="#{Page1.dropDown4}" id="dropDown4" items="#{Page1.dropDown4DefaultOptions.options}"/>
                                        <ui:staticText binding="#{Page1.staticText37}" id="staticText37"/>
                                        <ui:button action="#{Page1.button3_action}" binding="#{Page1.button3}" id="button3" text="Generate Report"/>
                                        <ui:staticText binding="#{Page1.staticText40}" id="staticText40"/>
                                        <ui:staticText binding="#{Page1.staticText39}" id="staticText39" text="Clik on Link Below to Start Download" visible="false"/>
                                        <ui:staticText binding="#{Page1.staticText41}" id="staticText41"/>
                                        <ui:hyperlink binding="#{Page1.hyperlink2}" id="hyperlink2" target="_blank" text="Hyperlink" visible="false"/>
                                        <ui:staticText binding="#{Page1.staticText42}" id="staticText42"/>
                                        <ui:staticText binding="#{Page1.staticText38}" id="staticText38"/>
                                    </h:panelGrid>
                                    <h:panelGrid binding="#{Page1.gridPanel7}" id="gridPanel7" style="height: 168px; left: 288px; top: 0px; position: absolute" width="288">
                                        <ui:propertySheet binding="#{Page1.propertySheet2}" id="propertySheet2" style="vertical-align: top">
                                            <ui:propertySheetSection binding="#{Page1.section2}" id="section2" label="Resources" style="height: 93px">
                                                <ui:property binding="#{Page1.property7}" id="property7" label="SAS Import Script">
                                                    <ui:button binding="#{Page1.button6}" id="button6" text="Download"/>
                                                </ui:property>
                                                <ui:property binding="#{Page1.property8}" id="property8" label="HTML Logic File">
                                                    <ui:button binding="#{Page1.button7}" id="button7" text="Download"/>
                                                </ui:property>
                                                <ui:property binding="#{Page1.property9}" id="property9" label="Schedule as Text">
                                                    <ui:button binding="#{Page1.button8}" id="button8" text="Download"/>
                                                </ui:property>
                                            </ui:propertySheetSection>
                                        </ui:propertySheet>
                                    </h:panelGrid>
                                    <ui:staticText binding="#{Page1.staticText45}" id="staticText45" style="position: absolute; left: 384px; top: 48px" visible="false"/>
                                </ui:panelLayout>
                            </ui:tab>
                            <ui:tab action="#{Page1.tab5_action}" binding="#{Page1.tab5}" id="tab5" text="Site Management">
                                <ui:panelLayout binding="#{Page1.layoutPanel6}" id="layoutPanel6" style="width: 100%;">
                                    <ui:panelLayout binding="#{Page1.layoutPanel8}" id="layoutPanel8" panelLayout="flow" style="border-width: 2px; border-style: solid; background-color: rgb(242, 244, 248); height: 428px; width: 1220px">
                                        <h:panelGrid binding="#{Page1.gridPanel9}" columns="3" id="gridPanel9">
                                            <ui:staticText binding="#{Page1.staticText82}" id="staticText82"
                                                style="font-family: Verdana,Arial,Helvetica,sans-serif; font-size: 14px; font-weight: bold" text="Available Sites"/>
                                            <ui:dropDown binding="#{Page1.dropDown5}" converter="#{Page1.integerConverter6}" id="dropDown5"
                                                items="#{Page1.sandboxDataProvider2.options['sandbox.id,sandbox.name']}" valueChangeListener="#{Page1.dropDown5_processValueChange}"/>
                                            <ui:button action="#{Page1.button10_action}" binding="#{Page1.button10}" id="button10" text="Select"/>
                                        </h:panelGrid>
                                        <ui:pageSeparator binding="#{Page1.pageSeparator4}" id="pageSeparator4"/>
                                        <ui:table augmentTitle="false" binding="#{Page1.table7}" id="table7" title="Instruments" width="40">
                                            <script><![CDATA[
/* ----- Functions for Table Preferences Panel ----- */
/*
 * Toggle the table preferences panel open or closed
 */
function togglePreferencesPanel() {
  var table = document.getElementById("form1:table7");
  table.toggleTblePreferencesPanel();
}
/* ----- Functions for Filter Panel ----- */
/*
 * Return true if the filter menu has actually changed,
 * so the corresponding event should be allowed to continue.
 */
function filterMenuChanged() {
  var table = document.getElementById("form1:table7");
  return table.filterMenuChanged();
}
/*
 * Toggle the custom filter panel (if any) open or closed.
 */
function toggleFilterPanel() {
  var table = document.getElementById("form1:table7");
  return table.toggleTableFilterPanel();
}
/* ----- Functions for Table Actions ----- */
/*
 * Initialize all rows of the table when the state
 * of selected rows changes.
 */
function initAllRows() {
  var table = document.getElementById("form1:table7");
  table.initAllRows();
}
/*
 * Set the selected state for the given row groups
 * displayed in the table.  This functionality requires
 * the 'selectId' of the tableColumn to be set.
 *
 * @param rowGroupId HTML element id of the tableRowGroup component
 * @param selected Flag indicating whether components should be selected
 */
function selectGroupRows(rowGroupId, selected) {
  var table = document.getElementById("form1:table7");
  table.selectGroupRows(rowGroupId, selected);
}
/*
 * Disable all table actions if no rows have been selected.
 */
function disableActions() {
  // Determine whether any rows are currently selected
  var table = document.getElementById("form1:table7");
  var disabled = (table.getAllSelectedRowsCount() > 0) ? false : true;
  // Set disabled state for top actions
  document.getElementById("form1:table7:tableActionsTop:deleteTop").setDisabled(disabled);
  // Set disabled state for bottom actions
  document.getElementById("form1:table7:tableActionsBottom:deleteBottom").setDisabled(disabled);
}]]></script>
                                            <ui:tableRowGroup binding="#{Page1.tableRowGroup7}" id="tableRowGroup7" rows="10"
                                                sourceData="#{Page1.instrumentDataProvider2}" sourceVar="currentRow">
                                                <ui:tableColumn binding="#{Page1.tableColumn26}" headerText="Name" id="tableColumn26" sort="instrument.instrument_name">
                                                    <ui:hyperlink action="#{Page1.hyperlink4_action}" binding="#{Page1.hyperlink4}" id="hyperlink4" text="#{currentRow.value['instrument.instrument_name']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn24}" headerText="Major Version" id="tableColumn24" sort="instrument_version.major_version">
                                                    <ui:staticText binding="#{Page1.staticText84}" id="staticText84" text="#{currentRow.value['instrument_version.major_version']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn25}" headerText="Minor Version" id="tableColumn25" sort="instrument_version.minor_version">
                                                    <ui:staticText binding="#{Page1.staticText85}" id="staticText85" text="#{currentRow.value['instrument_version.minor_version']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn27}" headerText="Description" id="tableColumn27" sort="instrument.instrument_description">
                                                    <ui:staticText binding="#{Page1.staticText73}" id="staticText73" text="#{currentRow.value['instrument.instrument_description']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn28}" headerText="Version Comments" id="tableColumn28" sort="instrument_version.instrument_notes">
                                                    <ui:staticText binding="#{Page1.staticText74}" id="staticText74" text="#{currentRow.value['instrument_version.instrument_notes']}"/>
                                                </ui:tableColumn>
                                            </ui:tableRowGroup>
                                        </ui:table>
                                        <ui:staticText binding="#{Page1.staticText83}" id="staticText83"/>
                                        <h:panelGrid binding="#{Page1.gridPanel13}" columns="3" id="gridPanel13">
                                            <ui:staticText binding="#{Page1.staticText81}" id="staticText81"/>
                                            <ui:dropDown binding="#{Page1.dropDown9}" converter="#{Page1.dropDown9Converter}" id="dropDown9" items="#{Page1.instrument_versionDataProvider1.options['instrument_version.instrument_version_id,instrument.instrument_name']}"/>
                                            <ui:button action="#{Page1.button12_action}" binding="#{Page1.button12}" id="button12" text="Add Instrument"/>
                                        </h:panelGrid>
                                    </ui:panelLayout>
                                </ui:panelLayout>
                            </ui:tab>
                            <ui:tab action="#{Page1.tab6_action}" binding="#{Page1.tab6}" id="tab6" style="vertical-align: top" text="Admin">
                                <ui:panelLayout binding="#{Page1.layoutPanel7}" id="layoutPanel7" style="vertical-align: top; width: 100%">
                                    <h:panelGrid binding="#{Page1.gridPanel8}" columns="1" id="gridPanel8" style="vertical-align: top">
                                        <ui:staticText binding="#{Page1.staticText62}" id="staticText62"
                                            style="color: gray; font-family: Verdana,Arial,Helvetica,sans-serif; font-size: 24px; font-weight: bold" text="Manage Users"/>
                                        <ui:staticText binding="#{Page1.staticText59}" id="staticText59"/>
                                        <ui:button action="#{Page1.button1_action}" binding="#{Page1.button1}" id="button1" text="Add New User"/>
                                        <ui:table augmentTitle="false" binding="#{Page1.table3}" id="table3" title="Users" width="618">
                                            <script><![CDATA[
/* ----- Functions for Table Preferences Panel ----- */
/*
 * Toggle the table preferences panel open or closed
 */
function togglePreferencesPanel() {
  var table = document.getElementById("form1:table3");
  table.toggleTblePreferencesPanel();
}
/* ----- Functions for Filter Panel ----- */
/*
 * Return true if the filter menu has actually changed,
 * so the corresponding event should be allowed to continue.
 */
function filterMenuChanged() {
  var table = document.getElementById("form1:table3");
  return table.filterMenuChanged();
}
/*
 * Toggle the custom filter panel (if any) open or closed.
 */
function toggleFilterPanel() {
  var table = document.getElementById("form1:table3");
  return table.toggleTableFilterPanel();
}
/* ----- Functions for Table Actions ----- */
/*
 * Initialize all rows of the table when the state
 * of selected rows changes.
 */
function initAllRows() {
  var table = document.getElementById("form1:table3");
  table.initAllRows();
}
/*
 * Set the selected state for the given row groups
 * displayed in the table.  This functionality requires
 * the 'selectId' of the tableColumn to be set.
 *
 * @param rowGroupId HTML element id of the tableRowGroup component
 * @param selected Flag indicating whether components should be selected
 */
function selectGroupRows(rowGroupId, selected) {
  var table = document.getElementById("form1:table3");
  table.selectGroupRows(rowGroupId, selected);
}
/*
 * Disable all table actions if no rows have been selected.
 */
function disableActions() {
  // Determine whether any rows are currently selected
  var table = document.getElementById("form1:table3");
  var disabled = (table.getAllSelectedRowsCount() > 0) ? false : true;
  // Set disabled state for top actions
  document.getElementById("form1:table3:tableActionsTop:deleteTop").setDisabled(disabled);
  // Set disabled state for bottom actions
  document.getElementById("form1:table3:tableActionsBottom:deleteBottom").setDisabled(disabled);
}]]></script>
                                            <ui:tableRowGroup binding="#{Page1.tableRowGroup3}" id="tableRowGroup3" rows="10"
                                                sourceData="#{Page1.usersDataProvider1}" sourceVar="currentRow">
                                                <ui:tableColumn binding="#{Page1.tableColumn2}" headerText="user_name" id="tableColumn2" sort="users.user_name">
                                                    <ui:textField binding="#{Page1.textField9}" id="textField9" text="#{currentRow.value['users.user_name']}" valueChangeListener="#{Page1.textField9_processValueChange}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn9}" headerText="password" id="tableColumn9" sort="users.password">
                                                    <ui:textField binding="#{Page1.textField10}" id="textField10" text="#{currentRow.value['users.password']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn10}" headerText="first_name" id="tableColumn10" sort="users.first_name">
                                                    <ui:textField binding="#{Page1.textField11}" id="textField11" text="#{currentRow.value['users.first_name']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn12}" headerText="last_name" id="tableColumn12" sort="users.last_name">
                                                    <ui:textField binding="#{Page1.textField12}" id="textField12" text="#{currentRow.value['users.last_name']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn13}" headerText="email" id="tableColumn13" sort="users.email">
                                                    <ui:textField binding="#{Page1.textField13}" id="textField13" text="#{currentRow.value['users.email']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn14}" headerText="phone" id="tableColumn14" sort="users.phone">
                                                    <ui:textField binding="#{Page1.textField14}" id="textField14" text="#{currentRow.value['users.phone']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn5}" headerText="Permissions" id="tableColumn5" sort="users.id">
                                                    <ui:button action="#{Page1.button18_action}" binding="#{Page1.button18}" id="button18" text="View"/>
                                                </ui:tableColumn>
                                            </ui:tableRowGroup>
                                        </ui:table>
                                        <ui:button action="#{Page1.button11_action}" binding="#{Page1.button11}" id="button11" text="Update"/>
                                        <ui:pageSeparator binding="#{Page1.pageSeparator3}" id="pageSeparator3"/>
                                        <ui:table augmentTitle="false" binding="#{Page1.table1}" id="table1" paginateButton="true" paginationControls="true"
                                            title="Permissions" width="216">
                                            <script><![CDATA[
/* ----- Functions for Table Preferences Panel ----- */
/*
 * Toggle the table preferences panel open or closed
 */
function togglePreferencesPanel() {
  var table = document.getElementById("form1:table1");
  table.toggleTblePreferencesPanel();
}
/* ----- Functions for Filter Panel ----- */
/*
 * Return true if the filter menu has actually changed,
 * so the corresponding event should be allowed to continue.
 */
function filterMenuChanged() {
  var table = document.getElementById("form1:table1");
  return table.filterMenuChanged();
}
/*
 * Toggle the custom filter panel (if any) open or closed.
 */
function toggleFilterPanel() {
  var table = document.getElementById("form1:table1");
  return table.toggleTableFilterPanel();
}
/* ----- Functions for Table Actions ----- */
/*
 * Initialize all rows of the table when the state
 * of selected rows changes.
 */
function initAllRows() {
  var table = document.getElementById("form1:table1");
  table.initAllRows();
}
/*
 * Set the selected state for the given row groups
 * displayed in the table.  This functionality requires
 * the 'selectId' of the tableColumn to be set.
 *
 * @param rowGroupId HTML element id of the tableRowGroup component
 * @param selected Flag indicating whether components should be selected
 */
function selectGroupRows(rowGroupId, selected) {
  var table = document.getElementById("form1:table1");
  table.selectGroupRows(rowGroupId, selected);
}
/*
 * Disable all table actions if no rows have been selected.
 */
function disableActions() {
  // Determine whether any rows are currently selected
  var table = document.getElementById("form1:table1");
  var disabled = (table.getAllSelectedRowsCount() > 0) ? false : true;
  // Set disabled state for top actions
  document.getElementById("form1:table1:tableActionsTop:deleteTop").setDisabled(disabled);
  // Set disabled state for bottom actions
  document.getElementById("form1:table1:tableActionsBottom:deleteBottom").setDisabled(disabled);
}]]></script>
                                            <ui:tableRowGroup binding="#{Page1.tableRowGroup1}" id="tableRowGroup1" rows="5"
                                                sourceData="#{Page1.user_permissionDataProvider1}" sourceVar="currentRow">
                                                <ui:tableColumn binding="#{Page1.tableColumn15}" headerText="instrument_name" id="tableColumn15" sort="instrument.instrument_name">
                                                    <ui:staticText binding="#{Page1.staticText32}" id="staticText32" text="#{currentRow.value['instrument.instrument_name']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn3}" headerText="role" id="tableColumn3">
                                                    <ui:staticText binding="#{Page1.staticText60}" id="staticText60" text="#{Page1.user_permissionDataProvider1.cachedRowSet.currentRow['role']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn4}" headerText="comment" id="tableColumn4" sort="user_permission.comment">
                                                    <ui:staticText binding="#{Page1.staticText61}" id="staticText61" text="#{currentRow.value['user_permission.comment']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn8}" id="tableColumn8">
                                                    <ui:button action="#{Page1.button16_action}" binding="#{Page1.button16}" id="button16" text="Delete"/>
                                                </ui:tableColumn>
                                            </ui:tableRowGroup>
                                        </ui:table>
                                        <h:panelGrid binding="#{Page1.gridPanel10}" id="gridPanel10"
                                            style="border-width: 3px; border-style: inset; height: 100%; width: 100%" title="&#xa;">
                                            <ui:propertySheet binding="#{Page1.propertySheet3}" id="propertySheet3">
                                                <ui:propertySheetSection binding="#{Page1.section3}" id="section3" label="New Permission">
                                                    <ui:property binding="#{Page1.property10}" id="property10" label="Instrument">
                                                        <ui:dropDown binding="#{Page1.dropDown6}" converter="#{Page1.integerConverter4}" id="dropDown6" items="#{Page1.instrumentDataProvider.options['instrument.instrument_id,instrument.instrument_name']}"/>
                                                    </ui:property>
                                                    <ui:property binding="#{Page1.property12}" id="property12" label="Role">
                                                        <ui:dropDown binding="#{Page1.dropDown7}" id="dropDown7" items="#{Page1.rolesDataProvider.options['roles.name,roles.name']}"/>
                                                    </ui:property>
                                                    <ui:property binding="#{Page1.property13}" id="property13" label="Notes">
                                                        <ui:textField binding="#{Page1.textField18}" id="textField18"/>
                                                        <ui:button action="#{Page1.button15_action}" binding="#{Page1.button15}" id="button15" text="Submit"/>
                                                    </ui:property>
                                                    <ui:property binding="#{Page1.property14}" id="property14"/>
                                                </ui:propertySheetSection>
                                            </ui:propertySheet>
                                        </h:panelGrid>
                                        <ui:staticText binding="#{Page1.staticText63}" id="staticText63"/>
                                        <ui:staticText binding="#{Page1.staticText12}" id="staticText12"/>
                                    </h:panelGrid>
                                    <h:panelGrid binding="#{Page1.gridPanel12}" id="gridPanel12" style="border-width: 3px; border-style: inset; margin: 3px; height: 100%; width: 100%">
                                        <ui:staticText binding="#{Page1.staticText65}" id="staticText65"/>
                                        <ui:staticText binding="#{Page1.staticText66}" id="staticText66"/>
                                        <ui:staticText binding="#{Page1.staticText80}" id="staticText80"
                                            style="color: gray; font-family: 'Verdana','Arial','Helvetica',sans-serif; font-size: 24px; font-weight: bold" text="Manage Sandboxes"/>
                                        <ui:pageSeparator binding="#{Page1.pageSeparator2}" id="pageSeparator2" style="height: 7px"/>
                                        <ui:button action="#{Page1.button13_action}" binding="#{Page1.button13}" id="button13" text="New Sandbox"/>
                                        <ui:table augmentTitle="false" binding="#{Page1.table4}" id="table4" title="Sandboxes" width="480">
                                            <script><![CDATA[
/* ----- Functions for Table Preferences Panel ----- */
/*
 * Toggle the table preferences panel open or closed
 */
function togglePreferencesPanel() {
  var table = document.getElementById("form1:table4");
  table.toggleTblePreferencesPanel();
}
/* ----- Functions for Filter Panel ----- */
/*
 * Return true if the filter menu has actually changed,
 * so the corresponding event should be allowed to continue.
 */
function filterMenuChanged() {
  var table = document.getElementById("form1:table4");
  return table.filterMenuChanged();
}
/*
 * Toggle the custom filter panel (if any) open or closed.
 */
function toggleFilterPanel() {
  var table = document.getElementById("form1:table4");
  return table.toggleTableFilterPanel();
}
/* ----- Functions for Table Actions ----- */
/*
 * Initialize all rows of the table when the state
 * of selected rows changes.
 */
function initAllRows() {
  var table = document.getElementById("form1:table4");
  table.initAllRows();
}
/*
 * Set the selected state for the given row groups
 * displayed in the table.  This functionality requires
 * the 'selectId' of the tableColumn to be set.
 *
 * @param rowGroupId HTML element id of the tableRowGroup component
 * @param selected Flag indicating whether components should be selected
 */
function selectGroupRows(rowGroupId, selected) {
  var table = document.getElementById("form1:table4");
  table.selectGroupRows(rowGroupId, selected);
}
/*
 * Disable all table actions if no rows have been selected.
 */
function disableActions() {
  // Determine whether any rows are currently selected
  var table = document.getElementById("form1:table4");
  var disabled = (table.getAllSelectedRowsCount() > 0) ? false : true;
  // Set disabled state for top actions
  document.getElementById("form1:table4:tableActionsTop:deleteTop").setDisabled(disabled);
  // Set disabled state for bottom actions
  document.getElementById("form1:table4:tableActionsBottom:deleteBottom").setDisabled(disabled);
}]]></script>
                                            <ui:tableRowGroup binding="#{Page1.tableRowGroup4}" id="tableRowGroup4" rows="10"
                                                sourceData="#{Page1.sandboxDataProvider}" sourceVar="currentRow">
                                                <ui:tableColumn binding="#{Page1.tableColumn16}" headerText="name" id="tableColumn16" sort="sandbox.name">
                                                    <ui:textField binding="#{Page1.textField15}" id="textField15" text="#{currentRow.value['sandbox.name']}" valueChangeListener="#{Page1.textField15_processValueChange}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn17}" headerText="application_path" id="tableColumn17" sort="sandbox.application_path">
                                                    <ui:textField binding="#{Page1.textField16}" id="textField16" text="#{currentRow.value['sandbox.application_path']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn18}" headerText="url" id="tableColumn18" sort="sandbox.url">
                                                    <ui:textField binding="#{Page1.textField17}" id="textField17" text="#{currentRow.value['sandbox.url']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn19}" headerText="port" id="tableColumn19" sort="sandbox.port">
                                                    <ui:textField binding="#{Page1.textField19}" id="textField19" text="#{currentRow.value['sandbox.port']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn1}" headerText="Instruments" id="tableColumn1">
                                                    <ui:button action="#{Page1.button19_action}" binding="#{Page1.button19}" id="button19" text="View"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn30}" id="tableColumn30">
                                                    <ui:button action="#{Page1.button17_action}" binding="#{Page1.button17}" id="button17" text="Delete"/>
                                                </ui:tableColumn>
                                            </ui:tableRowGroup>
                                        </ui:table>
                                        <ui:button action="#{Page1.button9_action}" binding="#{Page1.button9}" id="button9" text="Update Sandboxes"/>
                                        <ui:table augmentTitle="false" binding="#{Page1.table5}" id="table5" title="Contents " width="120">
                                            <script><![CDATA[
/* ----- Functions for Table Preferences Panel ----- */
/*
 * Toggle the table preferences panel open or closed
 */
function togglePreferencesPanel() {
  var table = document.getElementById("form1:table5");
  table.toggleTblePreferencesPanel();
}
/* ----- Functions for Filter Panel ----- */
/*
 * Return true if the filter menu has actually changed,
 * so the corresponding event should be allowed to continue.
 */
function filterMenuChanged() {
  var table = document.getElementById("form1:table5");
  return table.filterMenuChanged();
}
/*
 * Toggle the custom filter panel (if any) open or closed.
 */
function toggleFilterPanel() {
  var table = document.getElementById("form1:table5");
  return table.toggleTableFilterPanel();
}
/* ----- Functions for Table Actions ----- */
/*
 * Initialize all rows of the table when the state
 * of selected rows changes.
 */
function initAllRows() {
  var table = document.getElementById("form1:table5");
  table.initAllRows();
}
/*
 * Set the selected state for the given row groups
 * displayed in the table.  This functionality requires
 * the 'selectId' of the tableColumn to be set.
 *
 * @param rowGroupId HTML element id of the tableRowGroup component
 * @param selected Flag indicating whether components should be selected
 */
function selectGroupRows(rowGroupId, selected) {
  var table = document.getElementById("form1:table5");
  table.selectGroupRows(rowGroupId, selected);
}
/*
 * Disable all table actions if no rows have been selected.
 */
function disableActions() {
  // Determine whether any rows are currently selected
  var table = document.getElementById("form1:table5");
  var disabled = (table.getAllSelectedRowsCount() > 0) ? false : true;
  // Set disabled state for top actions
  document.getElementById("form1:table5:tableActionsTop:deleteTop").setDisabled(disabled);
  // Set disabled state for bottom actions
  document.getElementById("form1:table5:tableActionsBottom:deleteBottom").setDisabled(disabled);
}]]></script>
                                            <ui:tableRowGroup binding="#{Page1.tableRowGroup5}" id="tableRowGroup5" rows="10"
                                                sourceData="#{Page1.sandboxDataProvider1}" sourceVar="currentRow">
                                                <ui:tableColumn binding="#{Page1.tableColumn20}" headerText="Sandbox Name" id="tableColumn20" sort="sandbox.name">
                                                    <ui:staticText binding="#{Page1.staticText71}" id="staticText71" text="#{currentRow.value['sandbox.name']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn21}" headerText="Instrument Name" id="tableColumn21" sort="instrument.instrument_name">
                                                    <ui:staticText binding="#{Page1.staticText77}" id="staticText77" text="#{currentRow.value['instrument.instrument_name']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn22}" headerText="Version Maj." id="tableColumn22" sort="instrument_version.major_version">
                                                    <ui:staticText binding="#{Page1.staticText78}" id="staticText78" text="#{currentRow.value['instrument_version.major_version']}"/>
                                                </ui:tableColumn>
                                                <ui:tableColumn binding="#{Page1.tableColumn23}" headerText="Version Min." id="tableColumn23" sort="instrument_version.minor_version">
                                                    <ui:staticText binding="#{Page1.staticText79}" id="staticText79" text="#{currentRow.value['instrument_version.minor_version']}"/>
                                                </ui:tableColumn>
                                            </ui:tableRowGroup>
                                        </ui:table>
                                        <ui:staticText binding="#{Page1.staticText64}" id="staticText64"/>
                                        <ui:staticText binding="#{Page1.staticText67}" id="staticText67"/>
                                    </h:panelGrid>
                                    <h:panelGrid binding="#{Page1.gridPanel11}" columns="2" id="gridPanel11" style="margin: 5px; ">
                                        <ui:staticText binding="#{Page1.staticText69}" id="staticText69"/>
                                        <ui:staticText binding="#{Page1.staticText70}" id="staticText70"/>
                                        <ui:staticText binding="#{Page1.staticText68}" id="staticText68"/>
                                        <ui:staticText binding="#{Page1.staticText31}" id="staticText31"/>
                                    </h:panelGrid>
                                </ui:panelLayout>
                            </ui:tab>
                            <ui:tab binding="#{Page1.tab4}" id="tab4" text="Settings">
                                <ui:panelLayout binding="#{Page1.layoutPanel5}" id="layoutPanel5" style="height: 319px; position: relative; width: 100%; -rave-layout: grid">
                                    <ui:propertySheet binding="#{Page1.propertySheet1}" id="propertySheet1" style="height: 216px; width: 456px">
                                        <ui:propertySheetSection binding="#{Page1.section1}" id="section1" label="Properties" style="height: 333px">
                                            <ui:property binding="#{Page1.property1}" id="property1" label="Path to Application">
                                                <ui:textField binding="#{Page1.textField3}" id="textField3"/>
                                            </ui:property>
                                            <ui:property binding="#{Page1.property2}" id="property2" label="Path to Perl files">
                                                <ui:textField binding="#{Page1.textField4}" id="textField4"/>
                                            </ui:property>
                                            <ui:property binding="#{Page1.property3}" id="property3" label="JDBC URL">
                                                <ui:textField binding="#{Page1.textField5}" id="textField5"/>
                                            </ui:property>
                                            <ui:property binding="#{Page1.property4}" id="property4" label="JDBC Driver">
                                                <ui:textField binding="#{Page1.textField6}" id="textField6"/>
                                            </ui:property>
                                            <ui:property binding="#{Page1.property5}" id="property5" label="JDBC User">
                                                <ui:textField binding="#{Page1.textField7}" id="textField7"/>
                                            </ui:property>
                                            <ui:property binding="#{Page1.property6}" id="property6" label="JDBC Password">
                                                <ui:textField binding="#{Page1.textField8}" id="textField8"/>
                                            </ui:property>
                                        </ui:propertySheetSection>
                                    </ui:propertySheet>
                                    <ui:button action="#{Page1.button5_action}" binding="#{Page1.button5}" id="button5"
                                        style="position: absolute; left: 144px; top: 240px" text="Update"/>
                                </ui:panelLayout>
                            </ui:tab>
                        </ui:tabSet>
                    </ui:form>
                </ui:body>
            </ui:html>
        </ui:page>
    </f:view>
</jsp:root>
