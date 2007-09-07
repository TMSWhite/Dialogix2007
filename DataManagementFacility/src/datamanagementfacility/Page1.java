/*
 * Page1.java
 *
 * Created on July 7, 2006, 8:07 AM
 * Copyright ISTCGXL
 */
package datamanagementfacility;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.File;
import jxl.Cell;
import jxl.Sheet;
import com.sun.data.provider.RowKey;
import jxl.Workbook;
import java.util.StringTokenizer;
//import org.dianexus.triceps.modules.data.ReportQueryDAO;
//import org.dianexus.triceps.modules.data.InstrumentVersionDAO;
import java.util.Date;
import com.sun.rave.web.ui.model.UploadedFile;
import com.sun.data.provider.RefreshableDataProvider;
import com.sun.data.provider.RefreshableDataAdapter;
import javax.faces.event.ValueChangeEvent;
import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.rave.web.ui.component.Body;
import com.sun.rave.web.ui.component.Form;
import com.sun.rave.web.ui.component.Head;
import com.sun.rave.web.ui.component.Html;
import com.sun.rave.web.ui.component.Link;
import com.sun.rave.web.ui.component.Page;
import javax.faces.FacesException;
import com.sun.rave.web.ui.component.PanelLayout;
import com.sun.rave.web.ui.component.ImageComponent;
import com.sun.rave.web.ui.component.StaticText;
import com.sun.rave.web.ui.component.TextField;
import com.sun.rave.web.ui.component.TextArea;
import com.sun.rave.web.ui.component.Button;
import com.sun.rave.web.ui.component.Hyperlink;
import com.sun.rave.web.ui.component.TabSet;
import com.sun.rave.web.ui.component.Tab;
import com.sun.rave.web.ui.component.PageSeparator;
import com.sun.rave.web.ui.component.DropDown;
import javax.faces.component.html.HtmlPanelGrid;
import com.sun.data.provider.impl.CachedRowSetDataProvider;
import javax.faces.convert.IntegerConverter;
//import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import com.sun.rave.web.ui.component.Table;
import com.sun.rave.web.ui.component.TableRowGroup;
import com.sun.rave.web.ui.component.TableColumn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import com.sun.rave.web.ui.model.SingleSelectOptionsList;
import com.sun.rave.web.ui.component.Calendar;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import com.sun.rave.web.ui.component.Upload;
import com.sun.rave.web.ui.component.PasswordField;
import com.sun.rave.web.ui.component.PropertySheet;
import com.sun.rave.web.ui.component.PropertySheetSection;
import com.sun.rave.web.ui.component.Property;
import com.sun.jsfcl.data.CachedRowSetDataModel;
import com.sun.rave.web.ui.component.Alert;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import java.util.ArrayList;
//import org.dianexus.triceps.modules.data.SandBoxDAO;
//import org.dianexus.triceps.modules.data.SandBoxItemDAO;
//import org.dianexus.triceps.modules.data.SandBoxUserDAO;
import java.util.Iterator;
import java.util.Properties;
import javax.servlet.ServletContext;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.dianexus.triceps.DialogixUser;
import org.dianexus.triceps.SandBoxUserBean;
import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.InstrumentDAO;
import org.dianexus.triceps.modules.data.InstrumentInfoDAO;
import org.dianexus.triceps.modules.data.InstrumentVersionDAO;
import org.dianexus.triceps.modules.data.Messages;
import org.dianexus.triceps.modules.data.ReportQueryDAO;
import org.dianexus.triceps.modules.data.SandBoxDAO;
import org.dianexus.triceps.modules.data.SandBoxItemDAO;
import org.dianexus.triceps.modules.data.SandBoxUserDAO;
import org.dianexus.triceps.modules.data.UserPermissionDAO;
//import org.dianexus.triceps.SandBoxUserBean;
//import org.dianexus.triceps.modules.data.UserPermissionDAO;
//import org.dianexus.triceps.modules.data.InstrumentInfoDAO;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class Page1 extends AbstractPageBean {
    // <editor-fold defaultstate="collapsed" desc="Creator-managed Component Definition">
    private int __placeholder;
    
    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
        
        instrument_versionDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrument_versionRowSet}"));
        instrument_sessionDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrument_sessionRowSet}"));
        instrument_sessionDataProvider1.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrument_sessionRowSet1}"));
        dropDown2DefaultOptions.setOptions(new com.sun.rave.web.ui.model.Option[] {new com.sun.rave.web.ui.model.Option("1", "Final Answers"), new com.sun.rave.web.ui.model.Option("2", "Raw Answer Data"), new com.sun.rave.web.ui.model.Option("3", "Event Data")});
        dropDown3DefaultOptions.setOptions(new com.sun.rave.web.ui.model.Option[] {new com.sun.rave.web.ui.model.Option("item1", "Excel")});
        dropDown4DefaultOptions.setOptions(new com.sun.rave.web.ui.model.Option[] {new com.sun.rave.web.ui.model.Option("item1", "None")});
        user_permissionDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.user_permissionRowSet}"));
        dataTable1Model.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrument_sessionRowSet}"));
        user_permissionDataProvider1.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.user_permissionRowSet}"));
        instrumentDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrumentRowSet}"));
        rolesDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.rolesRowSet}"));
        instrument_sessionDataProvider2.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrument_sessionRowSet1}"));
        usersDataProvider1.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.usersRowSet}"));
        instrumentDataProvider1.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrumentRowSet1}"));
        sandboxDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.sandboxRowSet}"));
        sandboxDataProvider1.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.sandboxRowSet1}"));
        sandboxDataProvider2.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.sandboxRowSet2}"));
        instrumentDataProvider2.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrumentRowSet2}"));
        instrumentDataProvider2.addRefreshableDataListener(new RefreshableDataAdapter()  {
            public void refreshed(RefreshableDataProvider rdp) {
                instrumentDataProvider2_refreshed(rdp);
            }
        });
        sandboxDataProvider3.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.sandboxRowSet3}"));
        instrument_versionDataProvider1.setCachedRowSet((javax.sql.rowset.CachedRowSet)getValue("#{SessionBean1.instrument_versionRowSet1}"));
        
    }
    
    private Page page1 = new Page();
    public String appPath="not set";
    public Page getPage1() {
        return page1;
    }
    
    public void setPage1(Page p) {
        this.page1 = p;
    }
    
    private Html html1 = new Html();
    
    public Html getHtml1() {
        return html1;
    }
    
    public void setHtml1(Html h) {
        this.html1 = h;
    }
    
    private Head head1 = new Head();
    
    public Head getHead1() {
        return head1;
    }
    
    public void setHead1(Head h) {
        this.head1 = h;
    }
    
    private Link link1 = new Link();
    
    public Link getLink1() {
        return link1;
    }
    
    public void setLink1(Link l) {
        this.link1 = l;
    }
    
    private Body body1 = new Body();
    
    public Body getBody1() {
        return body1;
    }
    
    public void setBody1(Body b) {
        this.body1 = b;
    }
    
    private Form form1 = new Form();
    
    public Form getForm1() {
        return form1;
    }
    
    public void setForm1(Form f) {
        this.form1 = f;
    }
    
    private TabSet tabSet1 = new TabSet();
    
    public TabSet getTabSet1() {
        return tabSet1;
    }
    
    public void setTabSet1(TabSet ts) {
        this.tabSet1 = ts;
    }
    
    private Tab tab1 = new Tab();
    
    public Tab getTab1() {
        return tab1;
    }
    
    public void setTab1(Tab t) {
        this.tab1 = t;
    }
    
    private PanelLayout layoutPanel1 = new PanelLayout();
    
    public PanelLayout getLayoutPanel1() {
        return layoutPanel1;
    }
    
    public void setLayoutPanel1(PanelLayout pl) {
        this.layoutPanel1 = pl;
    }
    
    private PanelLayout layoutPanel2 = new PanelLayout();
    
    public PanelLayout getLayoutPanel2() {
        return layoutPanel2;
    }
    
    public void setLayoutPanel2(PanelLayout pl) {
        this.layoutPanel2 = pl;
    }
    
    private TextField textField1 = new TextField();
    
    public TextField getTextField1() {
        return textField1;
    }
    
    public void setTextField1(TextField tf) {
        this.textField1 = tf;
    }
    
    private ImageComponent image1 = new ImageComponent();
    
    public ImageComponent getImage1() {
        return image1;
    }
    
    public void setImage1(ImageComponent ic) {
        this.image1 = ic;
    }
    
    private StaticText staticText1 = new StaticText();
    
    public StaticText getStaticText1() {
        return staticText1;
    }
    
    public void setStaticText1(StaticText st) {
        this.staticText1 = st;
    }
    
    private StaticText staticText2 = new StaticText();
    
    public StaticText getStaticText2() {
        return staticText2;
    }
    
    public void setStaticText2(StaticText st) {
        this.staticText2 = st;
    }
    
    private StaticText staticText3 = new StaticText();
    
    public StaticText getStaticText3() {
        return staticText3;
    }
    
    public void setStaticText3(StaticText st) {
        this.staticText3 = st;
    }
    
    private Hyperlink hyperlink1 = new Hyperlink();
    
    public Hyperlink getHyperlink1() {
        return hyperlink1;
    }
    
    public void setHyperlink1(Hyperlink h) {
        this.hyperlink1 = h;
    }
    
    private PageSeparator pageSeparator1 = new PageSeparator();
    
    public PageSeparator getPageSeparator1() {
        return pageSeparator1;
    }
    
    public void setPageSeparator1(PageSeparator ps) {
        this.pageSeparator1 = ps;
    }
    
    private DropDown dropDown1 = new DropDown();
    
    public DropDown getDropDown1() {
        return dropDown1;
    }
    
    public void setDropDown1(DropDown dd) {
        this.dropDown1 = dd;
    }
    
    private Button button2 = new Button();
    
    public Button getButton2() {
        return button2;
    }
    
    public void setButton2(Button b) {
        this.button2 = b;
    }
    
    private HtmlPanelGrid gridPanel1 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel1() {
        return gridPanel1;
    }
    
    public void setGridPanel1(HtmlPanelGrid hpg) {
        this.gridPanel1 = hpg;
    }
    
    private StaticText staticText4 = new StaticText();
    
    public StaticText getStaticText4() {
        return staticText4;
    }
    
    public void setStaticText4(StaticText st) {
        this.staticText4 = st;
    }
    
    private StaticText staticText5 = new StaticText();
    
    public StaticText getStaticText5() {
        return staticText5;
    }
    
    public void setStaticText5(StaticText st) {
        this.staticText5 = st;
    }
    
    private CachedRowSetDataProvider usersDataProvider = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getUsersDataProvider() {
        return usersDataProvider;
    }
    
    public void setUsersDataProvider(CachedRowSetDataProvider crsdp) {
        this.usersDataProvider = crsdp;
    }
    
    private CachedRowSetDataProvider instrument_versionDataProvider = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrument_versionDataProvider() {
        return instrument_versionDataProvider;
    }
    
    public void setInstrument_versionDataProvider(CachedRowSetDataProvider crsdp) {
        this.instrument_versionDataProvider = crsdp;
    }
    
    private Tab tab2 = new Tab();
    
    public Tab getTab2() {
        return tab2;
    }
    
    public void setTab2(Tab t) {
        this.tab2 = t;
    }
    
    private PanelLayout layoutPanel3 = new PanelLayout();
    
    public PanelLayout getLayoutPanel3() {
        return layoutPanel3;
    }
    
    public void setLayoutPanel3(PanelLayout pl) {
        this.layoutPanel3 = pl;
    }
    
    private Tab tab3 = new Tab();
    
    public Tab getTab3() {
        return tab3;
    }
    
    public void setTab3(Tab t) {
        this.tab3 = t;
    }
    
    private PanelLayout layoutPanel4 = new PanelLayout();
    
    public PanelLayout getLayoutPanel4() {
        return layoutPanel4;
    }
    
    public void setLayoutPanel4(PanelLayout pl) {
        this.layoutPanel4 = pl;
    }
    
    private CachedRowSetDataProvider instrument_sessionDataProvider = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrument_sessionDataProvider() {
        return instrument_sessionDataProvider;
    }
    
    public void setInstrument_sessionDataProvider(CachedRowSetDataProvider crsdp) {
        this.instrument_sessionDataProvider = crsdp;
    }
    
    private Table table2 = new Table();
    
    public Table getTable2() {
        return table2;
    }
    
    public void setTable2(Table t) {
        this.table2 = t;
    }
    
    private TableRowGroup tableRowGroup2 = new TableRowGroup();
    
    public TableRowGroup getTableRowGroup2() {
        return tableRowGroup2;
    }
    
    public void setTableRowGroup2(TableRowGroup trg) {
        this.tableRowGroup2 = trg;
    }
    
    private StaticText staticText14 = new StaticText();
    
    public StaticText getStaticText14() {
        return staticText14;
    }
    
    public void setStaticText14(StaticText st) {
        this.staticText14 = st;
    }
    
    private CachedRowSetDataProvider instrument_sessionDataProvider1 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrument_sessionDataProvider1() {
        return instrument_sessionDataProvider1;
    }
    
    public void setInstrument_sessionDataProvider1(CachedRowSetDataProvider crsdp) {
        this.instrument_sessionDataProvider1 = crsdp;
    }
    
    private HtmlPanelGrid gridPanel2 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel2() {
        return gridPanel2;
    }
    
    public void setGridPanel2(HtmlPanelGrid hpg) {
        this.gridPanel2 = hpg;
    }
    
    private StaticText staticText6 = new StaticText();
    
    public StaticText getStaticText6() {
        return staticText6;
    }
    
    public void setStaticText6(StaticText st) {
        this.staticText6 = st;
    }
    
    private StaticText staticText7 = new StaticText();
    
    public StaticText getStaticText7() {
        return staticText7;
    }
    
    public void setStaticText7(StaticText st) {
        this.staticText7 = st;
    }
    
    private StaticText staticText8 = new StaticText();
    
    public StaticText getStaticText8() {
        return staticText8;
    }
    
    public void setStaticText8(StaticText st) {
        this.staticText8 = st;
    }
    
    private StaticText staticText9 = new StaticText();
    
    public StaticText getStaticText9() {
        return staticText9;
    }
    
    public void setStaticText9(StaticText st) {
        this.staticText9 = st;
    }
    
    private StaticText staticText10 = new StaticText();
    
    public StaticText getStaticText10() {
        return staticText10;
    }
    
    public void setStaticText10(StaticText st) {
        this.staticText10 = st;
    }
    
    private StaticText staticText15 = new StaticText();
    
    public StaticText getStaticText15() {
        return staticText15;
    }
    
    public void setStaticText15(StaticText st) {
        this.staticText15 = st;
    }
    
    private StaticText staticText16 = new StaticText();
    
    public StaticText getStaticText16() {
        return staticText16;
    }
    
    public void setStaticText16(StaticText st) {
        this.staticText16 = st;
    }
    
    private StaticText staticText17 = new StaticText();
    
    public StaticText getStaticText17() {
        return staticText17;
    }
    
    public void setStaticText17(StaticText st) {
        this.staticText17 = st;
    }
    
    private StaticText staticText18 = new StaticText();
    
    public StaticText getStaticText18() {
        return staticText18;
    }
    
    public void setStaticText18(StaticText st) {
        this.staticText18 = st;
    }
    
    private StaticText staticText19 = new StaticText();
    
    public StaticText getStaticText19() {
        return staticText19;
    }
    
    public void setStaticText19(StaticText st) {
        this.staticText19 = st;
    }
    
    private StaticText staticText20 = new StaticText();
    
    public StaticText getStaticText20() {
        return staticText20;
    }
    
    public void setStaticText20(StaticText st) {
        this.staticText20 = st;
    }
    
    private StaticText staticText21 = new StaticText();
    
    public StaticText getStaticText21() {
        return staticText21;
    }
    
    public void setStaticText21(StaticText st) {
        this.staticText21 = st;
    }
    
    private StaticText staticText22 = new StaticText();
    
    public StaticText getStaticText22() {
        return staticText22;
    }
    
    public void setStaticText22(StaticText st) {
        this.staticText22 = st;
    }
    
    private StaticText staticText23 = new StaticText();
    
    public StaticText getStaticText23() {
        return staticText23;
    }
    
    public void setStaticText23(StaticText st) {
        this.staticText23 = st;
    }
    
    private StaticText staticText24 = new StaticText();
    
    public StaticText getStaticText24() {
        return staticText24;
    }
    
    public void setStaticText24(StaticText st) {
        this.staticText24 = st;
    }
    
    private StaticText staticText25 = new StaticText();
    
    public StaticText getStaticText25() {
        return staticText25;
    }
    
    public void setStaticText25(StaticText st) {
        this.staticText25 = st;
    }
    
    private StaticText staticText26 = new StaticText();
    
    public StaticText getStaticText26() {
        return staticText26;
    }
    
    public void setStaticText26(StaticText st) {
        this.staticText26 = st;
    }
    
    private StaticText staticText11 = new StaticText();
    
    public StaticText getStaticText11() {
        return staticText11;
    }
    
    public void setStaticText11(StaticText st) {
        this.staticText11 = st;
    }
    
    private StaticText staticText13 = new StaticText();
    
    public StaticText getStaticText13() {
        return staticText13;
    }
    
    public void setStaticText13(StaticText st) {
        this.staticText13 = st;
    }
    
    private StaticText staticText27 = new StaticText();
    
    public StaticText getStaticText27() {
        return staticText27;
    }
    
    public void setStaticText27(StaticText st) {
        this.staticText27 = st;
    }
    
    private StaticText staticText28 = new StaticText();
    
    public StaticText getStaticText28() {
        return staticText28;
    }
    
    public void setStaticText28(StaticText st) {
        this.staticText28 = st;
    }
    
    private HtmlPanelGrid gridPanel3 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel3() {
        return gridPanel3;
    }
    
    public void setGridPanel3(HtmlPanelGrid hpg) {
        this.gridPanel3 = hpg;
    }
    
    private StaticText staticText33 = new StaticText();
    
    public StaticText getStaticText33() {
        return staticText33;
    }
    
    public void setStaticText33(StaticText st) {
        this.staticText33 = st;
    }
    
    private HtmlPanelGrid gridPanel4 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel4() {
        return gridPanel4;
    }
    
    public void setGridPanel4(HtmlPanelGrid hpg) {
        this.gridPanel4 = hpg;
    }
    
    private StaticText staticText34 = new StaticText();
    
    public StaticText getStaticText34() {
        return staticText34;
    }
    
    public void setStaticText34(StaticText st) {
        this.staticText34 = st;
    }
    
    private StaticText staticText35 = new StaticText();
    
    public StaticText getStaticText35() {
        return staticText35;
    }
    
    public void setStaticText35(StaticText st) {
        this.staticText35 = st;
    }
    
    private StaticText staticText36 = new StaticText();
    
    public StaticText getStaticText36() {
        return staticText36;
    }
    
    public void setStaticText36(StaticText st) {
        this.staticText36 = st;
    }
    
    private StaticText staticText37 = new StaticText();
    
    public StaticText getStaticText37() {
        return staticText37;
    }
    
    public void setStaticText37(StaticText st) {
        this.staticText37 = st;
    }
    
    private DropDown dropDown2 = new DropDown();
    
    public DropDown getDropDown2() {
        return dropDown2;
    }
    
    public void setDropDown2(DropDown dd) {
        this.dropDown2 = dd;
    }
    
    private SingleSelectOptionsList dropDown2DefaultOptions = new SingleSelectOptionsList();
    
    public SingleSelectOptionsList getDropDown2DefaultOptions() {
        return dropDown2DefaultOptions;
    }
    
    public void setDropDown2DefaultOptions(SingleSelectOptionsList ssol) {
        this.dropDown2DefaultOptions = ssol;
    }
    
    private DropDown dropDown3 = new DropDown();
    
    public DropDown getDropDown3() {
        return dropDown3;
    }
    
    public void setDropDown3(DropDown dd) {
        this.dropDown3 = dd;
    }
    
    private SingleSelectOptionsList dropDown3DefaultOptions = new SingleSelectOptionsList();
    
    public SingleSelectOptionsList getDropDown3DefaultOptions() {
        return dropDown3DefaultOptions;
    }
    
    public void setDropDown3DefaultOptions(SingleSelectOptionsList ssol) {
        this.dropDown3DefaultOptions = ssol;
    }
    
    private DropDown dropDown4 = new DropDown();
    
    public DropDown getDropDown4() {
        return dropDown4;
    }
    
    public void setDropDown4(DropDown dd) {
        this.dropDown4 = dd;
    }
    
    private SingleSelectOptionsList dropDown4DefaultOptions = new SingleSelectOptionsList();
    
    public SingleSelectOptionsList getDropDown4DefaultOptions() {
        return dropDown4DefaultOptions;
    }
    
    public void setDropDown4DefaultOptions(SingleSelectOptionsList ssol) {
        this.dropDown4DefaultOptions = ssol;
    }
    
    private Button button3 = new Button();
    
    public Button getButton3() {
        return button3;
    }
    
    public void setButton3(Button b) {
        this.button3 = b;
    }
    
    private StaticText staticText38 = new StaticText();
    
    public StaticText getStaticText38() {
        return staticText38;
    }
    
    public void setStaticText38(StaticText st) {
        this.staticText38 = st;
    }
    
    private StaticText staticText39 = new StaticText();
    
    public StaticText getStaticText39() {
        return staticText39;
    }
    
    public void setStaticText39(StaticText st) {
        this.staticText39 = st;
    }
    
    private StaticText staticText40 = new StaticText();
    
    public StaticText getStaticText40() {
        return staticText40;
    }
    
    public void setStaticText40(StaticText st) {
        this.staticText40 = st;
    }
    
    private StaticText staticText41 = new StaticText();
    
    public StaticText getStaticText41() {
        return staticText41;
    }
    
    public void setStaticText41(StaticText st) {
        this.staticText41 = st;
    }
    
    private StaticText staticText42 = new StaticText();
    
    public StaticText getStaticText42() {
        return staticText42;
    }
    
    public void setStaticText42(StaticText st) {
        this.staticText42 = st;
    }
    
    private Hyperlink hyperlink2 = new Hyperlink();
    
    public Hyperlink getHyperlink2() {
        return hyperlink2;
    }
    
    public void setHyperlink2(Hyperlink h) {
        this.hyperlink2 = h;
    }
    
    private StaticText staticText43 = new StaticText();
    
    public StaticText getStaticText43() {
        return staticText43;
    }
    
    public void setStaticText43(StaticText st) {
        this.staticText43 = st;
    }
    
    private StaticText staticText44 = new StaticText();
    
    public StaticText getStaticText44() {
        return staticText44;
    }
    
    public void setStaticText44(StaticText st) {
        this.staticText44 = st;
    }
    
    private Calendar calendar1 = new Calendar();
    
    public Calendar getCalendar1() {
        return calendar1;
    }
    
    public void setCalendar1(Calendar c) {
        this.calendar1 = c;
    }
    
    private Calendar calendar2 = new Calendar();
    
    public Calendar getCalendar2() {
        return calendar2;
    }
    
    public void setCalendar2(Calendar c) {
        this.calendar2 = c;
    }
    
    private StaticText staticText45 = new StaticText();
    
    public StaticText getStaticText45() {
        return staticText45;
    }
    
    public void setStaticText45(StaticText st) {
        this.staticText45 = st;
    }
    
    private HtmlOutputLink hyperlink3 = new HtmlOutputLink();
    
    public HtmlOutputLink getHyperlink3() {
        return hyperlink3;
    }
    
    public void setHyperlink3(HtmlOutputLink hol) {
        this.hyperlink3 = hol;
    }
    
    private HtmlOutputText hyperlink3Text = new HtmlOutputText();
    
    public HtmlOutputText getHyperlink3Text() {
        return hyperlink3Text;
    }
    
    public void setHyperlink3Text(HtmlOutputText hot) {
        this.hyperlink3Text = hot;
    }
    
    private HtmlPanelGrid gridPanel5 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel5() {
        return gridPanel5;
    }
    
    public void setGridPanel5(HtmlPanelGrid hpg) {
        this.gridPanel5 = hpg;
    }
    
    private StaticText staticText46 = new StaticText();
    
    public StaticText getStaticText46() {
        return staticText46;
    }
    
    public void setStaticText46(StaticText st) {
        this.staticText46 = st;
    }
    
    private StaticText staticText47 = new StaticText();
    
    public StaticText getStaticText47() {
        return staticText47;
    }
    
    public void setStaticText47(StaticText st) {
        this.staticText47 = st;
    }
    
    private Upload fileUpload1 = new Upload();
    
    public Upload getFileUpload1() {
        return fileUpload1;
    }
    
    public void setFileUpload1(Upload u) {
        this.fileUpload1 = u;
    }
    
    private StaticText staticText49 = new StaticText();
    
    public StaticText getStaticText49() {
        return staticText49;
    }
    
    public void setStaticText49(StaticText st) {
        this.staticText49 = st;
    }
    
    private StaticText staticText50 = new StaticText();
    
    public StaticText getStaticText50() {
        return staticText50;
    }
    
    public void setStaticText50(StaticText st) {
        this.staticText50 = st;
    }
    
    private TextArea instrumentDescription = new TextArea();
    
    public TextArea getInstrumentDescription() {
        return instrumentDescription;
    }
    
    public void setInstrumentDescription(TextArea ta) {
        this.instrumentDescription = ta;
    }
    
    private StaticText staticText52 = new StaticText();
    
    public StaticText getStaticText52() {
        return staticText52;
    }
    
    public void setStaticText52(StaticText st) {
        this.staticText52 = st;
    }
    
    private StaticText staticText53 = new StaticText();
    
    public StaticText getStaticText53() {
        return staticText53;
    }
    
    public void setStaticText53(StaticText st) {
        this.staticText53 = st;
    }
    
    private StaticText staticText54 = new StaticText();
    
    public StaticText getStaticText54() {
        return staticText54;
    }
    
    public void setStaticText54(StaticText st) {
        this.staticText54 = st;
    }
    
    private StaticText staticText55 = new StaticText();
    
    public StaticText getStaticText55() {
        return staticText55;
    }
    
    public void setStaticText55(StaticText st) {
        this.staticText55 = st;
    }
    
    private Button button4 = new Button();
    
    public Button getButton4() {
        return button4;
    }
    
    public void setButton4(Button b) {
        this.button4 = b;
    }
    
    private TextArea status = new TextArea();
    
    public TextArea getStatus() {
        return status;
    }
    
    public void setStatus(TextArea ta) {
        this.status = ta;
    }
    
    private HtmlPanelGrid gridPanel6 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel6() {
        return gridPanel6;
    }
    
    public void setGridPanel6(HtmlPanelGrid hpg) {
        this.gridPanel6 = hpg;
    }
    
    private StaticText staticText56 = new StaticText();
    
    public StaticText getStaticText56() {
        return staticText56;
    }
    
    public void setStaticText56(StaticText st) {
        this.staticText56 = st;
    }
    
    private StaticText staticText57 = new StaticText();
    
    public StaticText getStaticText57() {
        return staticText57;
    }
    
    public void setStaticText57(StaticText st) {
        this.staticText57 = st;
    }
    
    private StaticText staticText58 = new StaticText();
    
    public StaticText getStaticText58() {
        return staticText58;
    }
    
    public void setStaticText58(StaticText st) {
        this.staticText58 = st;
    }
    
    private PasswordField textField2 = new PasswordField();
    
    public PasswordField getTextField2() {
        return textField2;
    }
    
    public void setTextField2(PasswordField pf) {
        this.textField2 = pf;
    }
    
    private Tab tab4 = new Tab();
    
    public Tab getTab4() {
        return tab4;
    }
    
    public void setTab4(Tab t) {
        this.tab4 = t;
    }
    
    private PanelLayout layoutPanel5 = new PanelLayout();
    
    public PanelLayout getLayoutPanel5() {
        return layoutPanel5;
    }
    
    public void setLayoutPanel5(PanelLayout pl) {
        this.layoutPanel5 = pl;
    }
    
    private PropertySheet propertySheet1 = new PropertySheet();
    
    public PropertySheet getPropertySheet1() {
        return propertySheet1;
    }
    
    public void setPropertySheet1(PropertySheet ps) {
        this.propertySheet1 = ps;
    }
    
    private PropertySheetSection section1 = new PropertySheetSection();
    
    public PropertySheetSection getSection1() {
        return section1;
    }
    
    public void setSection1(PropertySheetSection pss) {
        this.section1 = pss;
    }
    
    private Property property1 = new Property();
    
    public Property getProperty1() {
        return property1;
    }
    
    public void setProperty1(Property p) {
        this.property1 = p;
    }
    
    private Property property2 = new Property();
    
    public Property getProperty2() {
        return property2;
    }
    
    public void setProperty2(Property p) {
        this.property2 = p;
    }
    
    private Property property3 = new Property();
    
    public Property getProperty3() {
        return property3;
    }
    
    public void setProperty3(Property p) {
        this.property3 = p;
    }
    
    private Property property4 = new Property();
    
    public Property getProperty4() {
        return property4;
    }
    
    public void setProperty4(Property p) {
        this.property4 = p;
    }
    
    private Property property5 = new Property();
    
    public Property getProperty5() {
        return property5;
    }
    
    public void setProperty5(Property p) {
        this.property5 = p;
    }
    
    private Property property6 = new Property();
    
    public Property getProperty6() {
        return property6;
    }
    
    public void setProperty6(Property p) {
        this.property6 = p;
    }
    
    private TextField textField3 = new TextField();
    
    public TextField getTextField3() {
        return textField3;
    }
    
    public void setTextField3(TextField tf) {
        this.textField3 = tf;
    }
    
    private TextField textField4 = new TextField();
    
    public TextField getTextField4() {
        return textField4;
    }
    
    public void setTextField4(TextField tf) {
        this.textField4 = tf;
    }
    
    private TextField textField5 = new TextField();
    
    public TextField getTextField5() {
        return textField5;
    }
    
    public void setTextField5(TextField tf) {
        this.textField5 = tf;
    }
    
    private TextField textField6 = new TextField();
    
    public TextField getTextField6() {
        return textField6;
    }
    
    public void setTextField6(TextField tf) {
        this.textField6 = tf;
    }
    
    private TextField textField7 = new TextField();
    
    public TextField getTextField7() {
        return textField7;
    }
    
    public void setTextField7(TextField tf) {
        this.textField7 = tf;
    }
    
    private TextField textField8 = new TextField();
    
    public TextField getTextField8() {
        return textField8;
    }
    
    public void setTextField8(TextField tf) {
        this.textField8 = tf;
    }
    
    private Button button5 = new Button();
    
    public Button getButton5() {
        return button5;
    }
    
    public void setButton5(Button b) {
        this.button5 = b;
    }
    
    private HtmlPanelGrid gridPanel7 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel7() {
        return gridPanel7;
    }
    
    public void setGridPanel7(HtmlPanelGrid hpg) {
        this.gridPanel7 = hpg;
    }
    
    private PropertySheet propertySheet2 = new PropertySheet();
    
    public PropertySheet getPropertySheet2() {
        return propertySheet2;
    }
    
    public void setPropertySheet2(PropertySheet ps) {
        this.propertySheet2 = ps;
    }
    
    private PropertySheetSection section2 = new PropertySheetSection();
    
    public PropertySheetSection getSection2() {
        return section2;
    }
    
    public void setSection2(PropertySheetSection pss) {
        this.section2 = pss;
    }
    
    private Property property7 = new Property();
    
    public Property getProperty7() {
        return property7;
    }
    
    public void setProperty7(Property p) {
        this.property7 = p;
    }
    
    private Button button6 = new Button();
    
    public Button getButton6() {
        return button6;
    }
    
    public void setButton6(Button b) {
        this.button6 = b;
    }
    
    private Property property8 = new Property();
    
    public Property getProperty8() {
        return property8;
    }
    
    public void setProperty8(Property p) {
        this.property8 = p;
    }
    
    private Button button7 = new Button();
    
    public Button getButton7() {
        return button7;
    }
    
    public void setButton7(Button b) {
        this.button7 = b;
    }
    
    private Property property9 = new Property();
    
    public Property getProperty9() {
        return property9;
    }
    
    public void setProperty9(Property p) {
        this.property9 = p;
    }
    
    private Button button8 = new Button();
    
    public Button getButton8() {
        return button8;
    }
    
    public void setButton8(Button b) {
        this.button8 = b;
    }
    
    private Tab tab5 = new Tab();
    
    public Tab getTab5() {
        return tab5;
    }
    
    public void setTab5(Tab t) {
        this.tab5 = t;
    }
    
    private PanelLayout layoutPanel6 = new PanelLayout();
    
    public PanelLayout getLayoutPanel6() {
        return layoutPanel6;
    }
    
    public void setLayoutPanel6(PanelLayout pl) {
        this.layoutPanel6 = pl;
    }
    
    private Tab tab6 = new Tab();
    
    public Tab getTab6() {
        return tab6;
    }
    
    public void setTab6(Tab t) {
        this.tab6 = t;
    }
    
    private PanelLayout layoutPanel7 = new PanelLayout();
    
    public PanelLayout getLayoutPanel7() {
        return layoutPanel7;
    }
    
    public void setLayoutPanel7(PanelLayout pl) {
        this.layoutPanel7 = pl;
    }
    
    private HtmlPanelGrid gridPanel8 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel8() {
        return gridPanel8;
    }
    
    public void setGridPanel8(HtmlPanelGrid hpg) {
        this.gridPanel8 = hpg;
    }
    
    private Button button13 = new Button();
    
    public Button getButton13() {
        return button13;
    }
    
    public void setButton13(Button b) {
        this.button13 = b;
    }
    
    private IntegerConverter dropDown5Converter = new IntegerConverter();
    
    public IntegerConverter getDropDown5Converter() {
        return dropDown5Converter;
    }
    
    public void setDropDown5Converter(IntegerConverter ic) {
        this.dropDown5Converter = ic;
    }
    
    private Table table1 = new Table();
    
    public Table getTable1() {
        return table1;
    }
    
    public void setTable1(Table t) {
        this.table1 = t;
    }
    
    private TableRowGroup tableRowGroup1 = new TableRowGroup();
    
    public TableRowGroup getTableRowGroup1() {
        return tableRowGroup1;
    }
    
    public void setTableRowGroup1(TableRowGroup trg) {
        this.tableRowGroup1 = trg;
    }
    
    private CachedRowSetDataProvider user_permissionDataProvider = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getUser_permissionDataProvider() {
        return user_permissionDataProvider;
    }
    
    public void setUser_permissionDataProvider(CachedRowSetDataProvider crsdp) {
        this.user_permissionDataProvider = crsdp;
    }
    
    private CachedRowSetDataModel dataTable1Model = new CachedRowSetDataModel();
    
    public CachedRowSetDataModel getDataTable1Model() {
        return dataTable1Model;
    }
    
    public void setDataTable1Model(CachedRowSetDataModel crsdm) {
        this.dataTable1Model = crsdm;
    }
    
    private PanelLayout layoutPanel8 = new PanelLayout();
    
    public PanelLayout getLayoutPanel8() {
        return layoutPanel8;
    }
    
    public void setLayoutPanel8(PanelLayout pl) {
        this.layoutPanel8 = pl;
    }
    
    private CachedRowSetDataProvider user_permissionDataProvider1 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getUser_permissionDataProvider1() {
        return user_permissionDataProvider1;
    }
    
    public void setUser_permissionDataProvider1(CachedRowSetDataProvider crsdp) {
        this.user_permissionDataProvider1 = crsdp;
    }
    
    private TableColumn tableColumn3 = new TableColumn();
    
    public TableColumn getTableColumn3() {
        return tableColumn3;
    }
    
    public void setTableColumn3(TableColumn tc) {
        this.tableColumn3 = tc;
    }
    
    private TableColumn tableColumn4 = new TableColumn();
    
    public TableColumn getTableColumn4() {
        return tableColumn4;
    }
    
    public void setTableColumn4(TableColumn tc) {
        this.tableColumn4 = tc;
    }
    
    private TableColumn tableColumn8 = new TableColumn();
    
    public TableColumn getTableColumn8() {
        return tableColumn8;
    }
    
    public void setTableColumn8(TableColumn tc) {
        this.tableColumn8 = tc;
    }
    
    private Button button16 = new Button();
    
    public Button getButton16() {
        return button16;
    }
    
    public void setButton16(Button b) {
        this.button16 = b;
    }
    
    private StaticText staticText31 = new StaticText();
    
    public StaticText getStaticText31() {
        return staticText31;
    }
    
    public void setStaticText31(StaticText st) {
        this.staticText31 = st;
    }
    
    private StaticText staticText62 = new StaticText();
    
    public StaticText getStaticText62() {
        return staticText62;
    }
    
    public void setStaticText62(StaticText st) {
        this.staticText62 = st;
    }
    
    private HtmlPanelGrid gridPanel11 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel11() {
        return gridPanel11;
    }
    
    public void setGridPanel11(HtmlPanelGrid hpg) {
        this.gridPanel11 = hpg;
    }
    
    private StaticText staticText63 = new StaticText();
    
    public StaticText getStaticText63() {
        return staticText63;
    }
    
    public void setStaticText63(StaticText st) {
        this.staticText63 = st;
    }
    
    private HtmlPanelGrid gridPanel12 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel12() {
        return gridPanel12;
    }
    
    public void setGridPanel12(HtmlPanelGrid hpg) {
        this.gridPanel12 = hpg;
    }
    
    private StaticText staticText64 = new StaticText();
    
    public StaticText getStaticText64() {
        return staticText64;
    }
    
    public void setStaticText64(StaticText st) {
        this.staticText64 = st;
    }
    
    private StaticText staticText65 = new StaticText();
    
    public StaticText getStaticText65() {
        return staticText65;
    }
    
    public void setStaticText65(StaticText st) {
        this.staticText65 = st;
    }
    
    private StaticText staticText66 = new StaticText();
    
    public StaticText getStaticText66() {
        return staticText66;
    }
    
    public void setStaticText66(StaticText st) {
        this.staticText66 = st;
    }
    
    private StaticText staticText67 = new StaticText();
    
    public StaticText getStaticText67() {
        return staticText67;
    }
    
    public void setStaticText67(StaticText st) {
        this.staticText67 = st;
    }
    
    private CachedRowSetDataProvider instrumentDataProvider = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrumentDataProvider() {
        return instrumentDataProvider;
    }
    
    public void setInstrumentDataProvider(CachedRowSetDataProvider crsdp) {
        this.instrumentDataProvider = crsdp;
    }
    
    private CachedRowSetDataProvider rolesDataProvider = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getRolesDataProvider() {
        return rolesDataProvider;
    }
    
    public void setRolesDataProvider(CachedRowSetDataProvider crsdp) {
        this.rolesDataProvider = crsdp;
    }
    
    private PageSeparator pageSeparator2 = new PageSeparator();
    
    public PageSeparator getPageSeparator2() {
        return pageSeparator2;
    }
    
    public void setPageSeparator2(PageSeparator ps) {
        this.pageSeparator2 = ps;
    }
    
    private StaticText staticText68 = new StaticText();
    
    public StaticText getStaticText68() {
        return staticText68;
    }
    
    public void setStaticText68(StaticText st) {
        this.staticText68 = st;
    }
    
    private StaticText staticText69 = new StaticText();
    
    public StaticText getStaticText69() {
        return staticText69;
    }
    
    public void setStaticText69(StaticText st) {
        this.staticText69 = st;
    }
    
    private StaticText staticText70 = new StaticText();
    
    public StaticText getStaticText70() {
        return staticText70;
    }
    
    public void setStaticText70(StaticText st) {
        this.staticText70 = st;
    }
    
    private IntegerConverter dropDown6Converter = new IntegerConverter();
    
    public IntegerConverter getDropDown6Converter() {
        return dropDown6Converter;
    }
    
    public void setDropDown6Converter(IntegerConverter ic) {
        this.dropDown6Converter = ic;
    }
    
    private IntegerConverter dropDown10Converter = new IntegerConverter();
    
    public IntegerConverter getDropDown10Converter() {
        return dropDown10Converter;
    }
    
    public void setDropDown10Converter(IntegerConverter ic) {
        this.dropDown10Converter = ic;
    }
    
    private CachedRowSetDataProvider cachedRowSetDataProvider1 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getCachedRowSetDataProvider1() {
        return cachedRowSetDataProvider1;
    }
    
    public void setCachedRowSetDataProvider1(CachedRowSetDataProvider crsdp) {
        this.cachedRowSetDataProvider1 = crsdp;
    }
    
    private CachedRowSetDataProvider instrument_sessionDataProvider2 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrument_sessionDataProvider2() {
        return instrument_sessionDataProvider2;
    }
    
    public void setInstrument_sessionDataProvider2(CachedRowSetDataProvider crsdp) {
        this.instrument_sessionDataProvider2 = crsdp;
    }
    
    private TableColumn tableColumn6 = new TableColumn();
    
    public TableColumn getTableColumn6() {
        return tableColumn6;
    }
    
    public void setTableColumn6(TableColumn tc) {
        this.tableColumn6 = tc;
    }
    
    private StaticText staticText29 = new StaticText();
    
    public StaticText getStaticText29() {
        return staticText29;
    }
    
    public void setStaticText29(StaticText st) {
        this.staticText29 = st;
    }
    
    private TableColumn tableColumn7 = new TableColumn();
    
    public TableColumn getTableColumn7() {
        return tableColumn7;
    }
    
    public void setTableColumn7(TableColumn tc) {
        this.tableColumn7 = tc;
    }
    
    private StaticText staticText30 = new StaticText();
    
    public StaticText getStaticText30() {
        return staticText30;
    }
    
    public void setStaticText30(StaticText st) {
        this.staticText30 = st;
    }
    
    private TableColumn tableColumn11 = new TableColumn();
    
    public TableColumn getTableColumn11() {
        return tableColumn11;
    }
    
    public void setTableColumn11(TableColumn tc) {
        this.tableColumn11 = tc;
    }
    
    private StaticText staticText72 = new StaticText();
    
    public StaticText getStaticText72() {
        return staticText72;
    }
    
    public void setStaticText72(StaticText st) {
        this.staticText72 = st;
    }
    
    private IntegerConverter integerConverter1 = new IntegerConverter();
    
    public IntegerConverter getIntegerConverter1() {
        return integerConverter1;
    }
    
    public void setIntegerConverter1(IntegerConverter ic) {
        this.integerConverter1 = ic;
    }
    
    private IntegerConverter integerConverter2 = new IntegerConverter();
    
    public IntegerConverter getIntegerConverter2() {
        return integerConverter2;
    }
    
    public void setIntegerConverter2(IntegerConverter ic) {
        this.integerConverter2 = ic;
    }
    
    private StaticText staticText12 = new StaticText();
    
    public StaticText getStaticText12() {
        return staticText12;
    }
    
    public void setStaticText12(StaticText st) {
        this.staticText12 = st;
    }
    
    private IntegerConverter integerConverter3 = new IntegerConverter();
    
    public IntegerConverter getIntegerConverter3() {
        return integerConverter3;
    }
    
    public void setIntegerConverter3(IntegerConverter ic) {
        this.integerConverter3 = ic;
    }
    
    private Table table3 = new Table();
    
    public Table getTable3() {
        return table3;
    }
    
    public void setTable3(Table t) {
        this.table3 = t;
    }
    
    private TableRowGroup tableRowGroup3 = new TableRowGroup();
    
    public TableRowGroup getTableRowGroup3() {
        return tableRowGroup3;
    }
    
    public void setTableRowGroup3(TableRowGroup trg) {
        this.tableRowGroup3 = trg;
    }
    
    private CachedRowSetDataProvider usersDataProvider1 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getUsersDataProvider1() {
        return usersDataProvider1;
    }
    
    public void setUsersDataProvider1(CachedRowSetDataProvider crsdp) {
        this.usersDataProvider1 = crsdp;
    }
    
    private TableColumn tableColumn2 = new TableColumn();
    
    public TableColumn getTableColumn2() {
        return tableColumn2;
    }
    
    public void setTableColumn2(TableColumn tc) {
        this.tableColumn2 = tc;
    }
    
    private TableColumn tableColumn9 = new TableColumn();
    
    public TableColumn getTableColumn9() {
        return tableColumn9;
    }
    
    public void setTableColumn9(TableColumn tc) {
        this.tableColumn9 = tc;
    }
    
    private TableColumn tableColumn10 = new TableColumn();
    
    public TableColumn getTableColumn10() {
        return tableColumn10;
    }
    
    public void setTableColumn10(TableColumn tc) {
        this.tableColumn10 = tc;
    }
    
    private TableColumn tableColumn12 = new TableColumn();
    
    public TableColumn getTableColumn12() {
        return tableColumn12;
    }
    
    public void setTableColumn12(TableColumn tc) {
        this.tableColumn12 = tc;
    }
    
    private TableColumn tableColumn13 = new TableColumn();
    
    public TableColumn getTableColumn13() {
        return tableColumn13;
    }
    
    public void setTableColumn13(TableColumn tc) {
        this.tableColumn13 = tc;
    }
    
    private TableColumn tableColumn14 = new TableColumn();
    
    public TableColumn getTableColumn14() {
        return tableColumn14;
    }
    
    public void setTableColumn14(TableColumn tc) {
        this.tableColumn14 = tc;
    }
    
    private StaticText staticText59 = new StaticText();
    
    public StaticText getStaticText59() {
        return staticText59;
    }
    
    public void setStaticText59(StaticText st) {
        this.staticText59 = st;
    }
    
    private Button button1 = new Button();
    
    public Button getButton1() {
        return button1;
    }
    
    public void setButton1(Button b) {
        this.button1 = b;
    }
    
    private Button button11 = new Button();
    
    public Button getButton11() {
        return button11;
    }
    
    public void setButton11(Button b) {
        this.button11 = b;
    }
    
    private PageSeparator pageSeparator3 = new PageSeparator();
    
    public PageSeparator getPageSeparator3() {
        return pageSeparator3;
    }
    
    public void setPageSeparator3(PageSeparator ps) {
        this.pageSeparator3 = ps;
    }
    
    private TextField textField9 = new TextField();
    
    public TextField getTextField9() {
        return textField9;
    }
    
    public void setTextField9(TextField tf) {
        this.textField9 = tf;
    }
    
    private TextField textField10 = new TextField();
    
    public TextField getTextField10() {
        return textField10;
    }
    
    public void setTextField10(TextField tf) {
        this.textField10 = tf;
    }
    
    private TextField textField11 = new TextField();
    
    public TextField getTextField11() {
        return textField11;
    }
    
    public void setTextField11(TextField tf) {
        this.textField11 = tf;
    }
    
    private TextField textField12 = new TextField();
    
    public TextField getTextField12() {
        return textField12;
    }
    
    public void setTextField12(TextField tf) {
        this.textField12 = tf;
    }
    
    private TextField textField13 = new TextField();
    
    public TextField getTextField13() {
        return textField13;
    }
    
    public void setTextField13(TextField tf) {
        this.textField13 = tf;
    }
    
    private TextField textField14 = new TextField();
    
    public TextField getTextField14() {
        return textField14;
    }
    
    public void setTextField14(TextField tf) {
        this.textField14 = tf;
    }
    
    private TableColumn tableColumn15 = new TableColumn();
    
    public TableColumn getTableColumn15() {
        return tableColumn15;
    }
    
    public void setTableColumn15(TableColumn tc) {
        this.tableColumn15 = tc;
    }
    
    private TableColumn tableColumn5 = new TableColumn();
    
    public TableColumn getTableColumn5() {
        return tableColumn5;
    }
    
    public void setTableColumn5(TableColumn tc) {
        this.tableColumn5 = tc;
    }
    
    private Button button18 = new Button();
    
    public Button getButton18() {
        return button18;
    }
    
    public void setButton18(Button b) {
        this.button18 = b;
    }
    
    private CachedRowSetDataProvider instrumentDataProvider1 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrumentDataProvider1() {
        return instrumentDataProvider1;
    }
    
    public void setInstrumentDataProvider1(CachedRowSetDataProvider crsdp) {
        this.instrumentDataProvider1 = crsdp;
    }
    
    private IntegerConverter dropDown7Converter = new IntegerConverter();
    
    public IntegerConverter getDropDown7Converter() {
        return dropDown7Converter;
    }
    
    public void setDropDown7Converter(IntegerConverter ic) {
        this.dropDown7Converter = ic;
    }
    
    private StaticText staticText32 = new StaticText();
    
    public StaticText getStaticText32() {
        return staticText32;
    }
    
    public void setStaticText32(StaticText st) {
        this.staticText32 = st;
    }
    
    private StaticText staticText60 = new StaticText();
    
    public StaticText getStaticText60() {
        return staticText60;
    }
    
    public void setStaticText60(StaticText st) {
        this.staticText60 = st;
    }
    
    private HtmlPanelGrid gridPanel10 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel10() {
        return gridPanel10;
    }
    
    public void setGridPanel10(HtmlPanelGrid hpg) {
        this.gridPanel10 = hpg;
    }
    
    private PropertySheet propertySheet3 = new PropertySheet();
    
    public PropertySheet getPropertySheet3() {
        return propertySheet3;
    }
    
    public void setPropertySheet3(PropertySheet ps) {
        this.propertySheet3 = ps;
    }
    
    private PropertySheetSection section3 = new PropertySheetSection();
    
    public PropertySheetSection getSection3() {
        return section3;
    }
    
    public void setSection3(PropertySheetSection pss) {
        this.section3 = pss;
    }
    
    private Property property10 = new Property();
    
    public Property getProperty10() {
        return property10;
    }
    
    public void setProperty10(Property p) {
        this.property10 = p;
    }
    
    private DropDown dropDown6 = new DropDown();
    
    public DropDown getDropDown6() {
        return dropDown6;
    }
    
    public void setDropDown6(DropDown dd) {
        this.dropDown6 = dd;
    }
    
    private Property property12 = new Property();
    
    public Property getProperty12() {
        return property12;
    }
    
    public void setProperty12(Property p) {
        this.property12 = p;
    }
    
    private DropDown dropDown7 = new DropDown();
    
    public DropDown getDropDown7() {
        return dropDown7;
    }
    
    public void setDropDown7(DropDown dd) {
        this.dropDown7 = dd;
    }
    
    private Property property13 = new Property();
    
    public Property getProperty13() {
        return property13;
    }
    
    public void setProperty13(Property p) {
        this.property13 = p;
    }
    
    private TextField textField18 = new TextField();
    
    public TextField getTextField18() {
        return textField18;
    }
    
    public void setTextField18(TextField tf) {
        this.textField18 = tf;
    }
    
    private Button button15 = new Button();
    
    public Button getButton15() {
        return button15;
    }
    
    public void setButton15(Button b) {
        this.button15 = b;
    }
    
    private Property property14 = new Property();
    
    public Property getProperty14() {
        return property14;
    }
    
    public void setProperty14(Property p) {
        this.property14 = p;
    }
    
    private IntegerConverter integerConverter4 = new IntegerConverter();
    
    public IntegerConverter getIntegerConverter4() {
        return integerConverter4;
    }
    
    public void setIntegerConverter4(IntegerConverter ic) {
        this.integerConverter4 = ic;
    }
    
    private StaticText staticText61 = new StaticText();
    
    public StaticText getStaticText61() {
        return staticText61;
    }
    
    public void setStaticText61(StaticText st) {
        this.staticText61 = st;
    }
    
    private Table table4 = new Table();
    
    public Table getTable4() {
        return table4;
    }
    
    public void setTable4(Table t) {
        this.table4 = t;
    }
    
    private TableRowGroup tableRowGroup4 = new TableRowGroup();
    
    public TableRowGroup getTableRowGroup4() {
        return tableRowGroup4;
    }
    
    public void setTableRowGroup4(TableRowGroup trg) {
        this.tableRowGroup4 = trg;
    }
    
    private CachedRowSetDataProvider sandboxDataProvider = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getSandboxDataProvider() {
        return sandboxDataProvider;
    }
    
    public void setSandboxDataProvider(CachedRowSetDataProvider crsdp) {
        this.sandboxDataProvider = crsdp;
    }
    
    private TableColumn tableColumn16 = new TableColumn();
    
    public TableColumn getTableColumn16() {
        return tableColumn16;
    }
    
    public void setTableColumn16(TableColumn tc) {
        this.tableColumn16 = tc;
    }
    
    private TableColumn tableColumn17 = new TableColumn();
    
    public TableColumn getTableColumn17() {
        return tableColumn17;
    }
    
    public void setTableColumn17(TableColumn tc) {
        this.tableColumn17 = tc;
    }
    
    private TableColumn tableColumn18 = new TableColumn();
    
    public TableColumn getTableColumn18() {
        return tableColumn18;
    }
    
    public void setTableColumn18(TableColumn tc) {
        this.tableColumn18 = tc;
    }
    
    private TableColumn tableColumn19 = new TableColumn();
    
    public TableColumn getTableColumn19() {
        return tableColumn19;
    }
    
    public void setTableColumn19(TableColumn tc) {
        this.tableColumn19 = tc;
    }
    
    private Table table5 = new Table();
    
    public Table getTable5() {
        return table5;
    }
    
    public void setTable5(Table t) {
        this.table5 = t;
    }
    
    private TableRowGroup tableRowGroup5 = new TableRowGroup();
    
    public TableRowGroup getTableRowGroup5() {
        return tableRowGroup5;
    }
    
    public void setTableRowGroup5(TableRowGroup trg) {
        this.tableRowGroup5 = trg;
    }
    
    private CachedRowSetDataProvider sandboxDataProvider1 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getSandboxDataProvider1() {
        return sandboxDataProvider1;
    }
    
    public void setSandboxDataProvider1(CachedRowSetDataProvider crsdp) {
        this.sandboxDataProvider1 = crsdp;
    }
    
    private TableColumn tableColumn20 = new TableColumn();
    
    public TableColumn getTableColumn20() {
        return tableColumn20;
    }
    
    public void setTableColumn20(TableColumn tc) {
        this.tableColumn20 = tc;
    }
    
    private StaticText staticText71 = new StaticText();
    
    public StaticText getStaticText71() {
        return staticText71;
    }
    
    public void setStaticText71(StaticText st) {
        this.staticText71 = st;
    }
    
    private TableColumn tableColumn21 = new TableColumn();
    
    public TableColumn getTableColumn21() {
        return tableColumn21;
    }
    
    public void setTableColumn21(TableColumn tc) {
        this.tableColumn21 = tc;
    }
    
    private StaticText staticText77 = new StaticText();
    
    public StaticText getStaticText77() {
        return staticText77;
    }
    
    public void setStaticText77(StaticText st) {
        this.staticText77 = st;
    }
    
    private TableColumn tableColumn22 = new TableColumn();
    
    public TableColumn getTableColumn22() {
        return tableColumn22;
    }
    
    public void setTableColumn22(TableColumn tc) {
        this.tableColumn22 = tc;
    }
    
    private StaticText staticText78 = new StaticText();
    
    public StaticText getStaticText78() {
        return staticText78;
    }
    
    public void setStaticText78(StaticText st) {
        this.staticText78 = st;
    }
    
    private TableColumn tableColumn23 = new TableColumn();
    
    public TableColumn getTableColumn23() {
        return tableColumn23;
    }
    
    public void setTableColumn23(TableColumn tc) {
        this.tableColumn23 = tc;
    }
    
    private StaticText staticText79 = new StaticText();
    
    public StaticText getStaticText79() {
        return staticText79;
    }
    
    public void setStaticText79(StaticText st) {
        this.staticText79 = st;
    }
    
    private StaticText staticText80 = new StaticText();
    
    public StaticText getStaticText80() {
        return staticText80;
    }
    
    public void setStaticText80(StaticText st) {
        this.staticText80 = st;
    }
    
    private CachedRowSetDataProvider sandboxDataProvider2 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getSandboxDataProvider2() {
        return sandboxDataProvider2;
    }
    
    public void setSandboxDataProvider2(CachedRowSetDataProvider crsdp) {
        this.sandboxDataProvider2 = crsdp;
    }
    
    private Table table7 = new Table();
    
    public Table getTable7() {
        return table7;
    }
    
    public void setTable7(Table t) {
        this.table7 = t;
    }
    
    private TableRowGroup tableRowGroup7 = new TableRowGroup();
    
    public TableRowGroup getTableRowGroup7() {
        return tableRowGroup7;
    }
    
    public void setTableRowGroup7(TableRowGroup trg) {
        this.tableRowGroup7 = trg;
    }
    
    private PageSeparator pageSeparator4 = new PageSeparator();
    
    public PageSeparator getPageSeparator4() {
        return pageSeparator4;
    }
    
    public void setPageSeparator4(PageSeparator ps) {
        this.pageSeparator4 = ps;
    }
    
    private CachedRowSetDataProvider instrumentDataProvider2 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrumentDataProvider2() {
        return instrumentDataProvider2;
    }
    
    public void setInstrumentDataProvider2(CachedRowSetDataProvider crsdp) {
        this.instrumentDataProvider2 = crsdp;
    }
    
    private TableColumn tableColumn26 = new TableColumn();
    
    public TableColumn getTableColumn26() {
        return tableColumn26;
    }
    
    public void setTableColumn26(TableColumn tc) {
        this.tableColumn26 = tc;
    }
    
    private DropDown dropDown5 = new DropDown();
    
    public DropDown getDropDown5() {
        return dropDown5;
    }
    
    public void setDropDown5(DropDown dd) {
        this.dropDown5 = dd;
    }
    
    private IntegerConverter integerConverter5 = new IntegerConverter();
    
    public IntegerConverter getIntegerConverter5() {
        return integerConverter5;
    }
    
    public void setIntegerConverter5(IntegerConverter ic) {
        this.integerConverter5 = ic;
    }
    
    private Button button10 = new Button();
    
    public Button getButton10() {
        return button10;
    }
    
    public void setButton10(Button b) {
        this.button10 = b;
    }
    
    private HtmlPanelGrid gridPanel9 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel9() {
        return gridPanel9;
    }
    
    public void setGridPanel9(HtmlPanelGrid hpg) {
        this.gridPanel9 = hpg;
    }
    
    private StaticText staticText82 = new StaticText();
    
    public StaticText getStaticText82() {
        return staticText82;
    }
    
    public void setStaticText82(StaticText st) {
        this.staticText82 = st;
    }
    
    private TableColumn tableColumn24 = new TableColumn();
    
    public TableColumn getTableColumn24() {
        return tableColumn24;
    }
    
    public void setTableColumn24(TableColumn tc) {
        this.tableColumn24 = tc;
    }
    
    private StaticText staticText84 = new StaticText();
    
    public StaticText getStaticText84() {
        return staticText84;
    }
    
    public void setStaticText84(StaticText st) {
        this.staticText84 = st;
    }
    
    private TableColumn tableColumn25 = new TableColumn();
    
    public TableColumn getTableColumn25() {
        return tableColumn25;
    }
    
    public void setTableColumn25(TableColumn tc) {
        this.tableColumn25 = tc;
    }
    
    private StaticText staticText85 = new StaticText();
    
    public StaticText getStaticText85() {
        return staticText85;
    }
    
    public void setStaticText85(StaticText st) {
        this.staticText85 = st;
    }
    
    private Hyperlink hyperlink4 = new Hyperlink();
    
    public Hyperlink getHyperlink4() {
        return hyperlink4;
    }
    
    public void setHyperlink4(Hyperlink h) {
        this.hyperlink4 = h;
    }
    
    private StaticText staticText83 = new StaticText();
    
    public StaticText getStaticText83() {
        return staticText83;
    }
    
    public void setStaticText83(StaticText st) {
        this.staticText83 = st;
    }
    
    private DropDown dropDown8 = new DropDown();
    
    public DropDown getDropDown8() {
        return dropDown8;
    }
    
    public void setDropDown8(DropDown dd) {
        this.dropDown8 = dd;
    }
    
    private CachedRowSetDataProvider sandboxDataProvider3 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getSandboxDataProvider3() {
        return sandboxDataProvider3;
    }
    
    public void setSandboxDataProvider3(CachedRowSetDataProvider crsdp) {
        this.sandboxDataProvider3 = crsdp;
    }
    
    private IntegerConverter dropDown8Converter = new IntegerConverter();
    
    public IntegerConverter getDropDown8Converter() {
        return dropDown8Converter;
    }
    
    public void setDropDown8Converter(IntegerConverter ic) {
        this.dropDown8Converter = ic;
    }
    
    private StaticText staticText48 = new StaticText();
    
    public StaticText getStaticText48() {
        return staticText48;
    }
    
    public void setStaticText48(StaticText st) {
        this.staticText48 = st;
    }
    
    private StaticText staticText51 = new StaticText();
    
    public StaticText getStaticText51() {
        return staticText51;
    }
    
    public void setStaticText51(StaticText st) {
        this.staticText51 = st;
    }
    
    private StaticText staticText87 = new StaticText();
    
    public StaticText getStaticText87() {
        return staticText87;
    }
    
    public void setStaticText87(StaticText st) {
        this.staticText87 = st;
    }
    
    private Alert alert1 = new Alert();
    
    public Alert getAlert1() {
        return alert1;
    }
    
    public void setAlert1(Alert a) {
        this.alert1 = a;
    }
    
    private Alert alert2 = new Alert();
    
    public Alert getAlert2() {
        return alert2;
    }
    
    public void setAlert2(Alert a) {
        this.alert2 = a;
    }
    
    private DropDown dropDown9 = new DropDown();
    
    public DropDown getDropDown9() {
        return dropDown9;
    }
    
    public void setDropDown9(DropDown dd) {
        this.dropDown9 = dd;
    }
    
    private HtmlPanelGrid gridPanel13 = new HtmlPanelGrid();
    
    public HtmlPanelGrid getGridPanel13() {
        return gridPanel13;
    }
    
    public void setGridPanel13(HtmlPanelGrid hpg) {
        this.gridPanel13 = hpg;
    }
    
    private StaticText staticText81 = new StaticText();
    
    public StaticText getStaticText81() {
        return staticText81;
    }
    
    public void setStaticText81(StaticText st) {
        this.staticText81 = st;
    }
    
    private Button button12 = new Button();
    
    public Button getButton12() {
        return button12;
    }
    
    public void setButton12(Button b) {
        this.button12 = b;
    }
    
    private CachedRowSetDataProvider instrument_versionDataProvider1 = new CachedRowSetDataProvider();
    
    public CachedRowSetDataProvider getInstrument_versionDataProvider1() {
        return instrument_versionDataProvider1;
    }
    
    public void setInstrument_versionDataProvider1(CachedRowSetDataProvider crsdp) {
        this.instrument_versionDataProvider1 = crsdp;
    }
    
    private IntegerConverter dropDown9Converter = new IntegerConverter();
    
    public IntegerConverter getDropDown9Converter() {
        return dropDown9Converter;
    }
    
    public void setDropDown9Converter(IntegerConverter ic) {
        this.dropDown9Converter = ic;
    }
    
    private Alert alert3 = new Alert();
    
    public Alert getAlert3() {
        return alert3;
    }
    
    public void setAlert3(Alert a) {
        this.alert3 = a;
    }
    
    private Button loginButton = new Button();
    
    public Button getLoginButton() {
        return loginButton;
    }
    
    public void setLoginButton(Button b) {
        this.loginButton = b;
    }
    
    private IntegerConverter dropDown1Converter = new IntegerConverter();
    
    public IntegerConverter getDropDown1Converter() {
        return dropDown1Converter;
    }
    
    public void setDropDown1Converter(IntegerConverter ic) {
        this.dropDown1Converter = ic;
    }
    
    private TextField textField15 = new TextField();
    
    public TextField getTextField15() {
        return textField15;
    }
    
    public void setTextField15(TextField tf) {
        this.textField15 = tf;
    }
    
    private TextField textField16 = new TextField();
    
    public TextField getTextField16() {
        return textField16;
    }
    
    public void setTextField16(TextField tf) {
        this.textField16 = tf;
    }
    
    private TextField textField17 = new TextField();
    
    public TextField getTextField17() {
        return textField17;
    }
    
    public void setTextField17(TextField tf) {
        this.textField17 = tf;
    }
    
    private TextField textField19 = new TextField();
    
    public TextField getTextField19() {
        return textField19;
    }
    
    public void setTextField19(TextField tf) {
        this.textField19 = tf;
    }
    
    private TableColumn tableColumn30 = new TableColumn();
    
    public TableColumn getTableColumn30() {
        return tableColumn30;
    }
    
    public void setTableColumn30(TableColumn tc) {
        this.tableColumn30 = tc;
    }
    
    private Button button17 = new Button();
    
    public Button getButton17() {
        return button17;
    }
    
    public void setButton17(Button b) {
        this.button17 = b;
    }
    
    private Button button9 = new Button();
    
    public Button getButton9() {
        return button9;
    }
    
    public void setButton9(Button b) {
        this.button9 = b;
    }

    private TableColumn tableColumn1 = new TableColumn();

    public TableColumn getTableColumn1() {
        return tableColumn1;
    }

    public void setTableColumn1(TableColumn tc) {
        this.tableColumn1 = tc;
    }

    private Button button19 = new Button();

    public Button getButton19() {
        return button19;
    }

    public void setButton19(Button b) {
        this.button19 = b;
    }

    private IntegerConverter integerConverter6 = new IntegerConverter();

    public IntegerConverter getIntegerConverter6() {
        return integerConverter6;
    }

    public void setIntegerConverter6(IntegerConverter ic) {
        this.integerConverter6 = ic;
    }

    private TableColumn tableColumn27 = new TableColumn();

    public TableColumn getTableColumn27() {
        return tableColumn27;
    }

    public void setTableColumn27(TableColumn tc) {
        this.tableColumn27 = tc;
    }

    private StaticText staticText73 = new StaticText();

    public StaticText getStaticText73() {
        return staticText73;
    }

    public void setStaticText73(StaticText st) {
        this.staticText73 = st;
    }

    private TableColumn tableColumn28 = new TableColumn();

    public TableColumn getTableColumn28() {
        return tableColumn28;
    }

    public void setTableColumn28(TableColumn tc) {
        this.tableColumn28 = tc;
    }

    private StaticText staticText74 = new StaticText();

    public StaticText getStaticText74() {
        return staticText74;
    }

    public void setStaticText74(StaticText st) {
        this.staticText74 = st;
    }
    
    // </editor-fold>
    
    
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Page1() {
    }
    
    /**
     * <p>Return a reference to the scoped data bean.</p>
     */
    protected RequestBean1 getRequestBean1() {
        return (RequestBean1)getBean("RequestBean1");
    }
    
    
    /**
     * <p>Return a reference to the scoped data bean.</p>
     */
    protected ApplicationBean1 getApplicationBean1() {
        return (ApplicationBean1)getBean("ApplicationBean1");
    }
    
    
    /**
     * <p>Return a reference to the scoped data bean.</p>
     */
    protected SessionBean1 getSessionBean1() {
        return (SessionBean1)getBean("SessionBean1");
    }
    
    
    /**
     * <p>Callback method that is called whenever a page is navigated to,
     * either directly via a URL, or indirectly via page navigation.
     * Customize this method to acquire resources that will be needed
     * for event handlers and lifecycle methods, whether or not this
     * page is performing post back processing.</p>
     *
     * <p>Note that, if the current request is a postback