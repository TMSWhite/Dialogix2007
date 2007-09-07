/*
 * Page1Test.java
 * JUnit based test
 *
 * Created on August 4, 2006, 12:52 PM
 */

package datamanagementfacility;

import junit.framework.*;
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
import com.sun.rave.web.ui.component.Button;
import com.sun.rave.web.ui.component.Hyperlink;
import com.sun.rave.web.ui.component.TabSet;
import com.sun.rave.web.ui.component.Tab;
import com.sun.rave.web.ui.component.PageSeparator;
import com.sun.rave.web.ui.component.DropDown;
import javax.faces.component.html.HtmlPanelGrid;
import com.sun.data.provider.impl.CachedRowSetDataProvider;
import javax.faces.convert.IntegerConverter;
import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.UserDAO;
import com.sun.rave.web.ui.component.Table;
import com.sun.rave.web.ui.component.TableRowGroup;
import com.sun.rave.web.ui.component.TableColumn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.sun.rave.web.ui.model.SingleSelectOptionsList;
import com.sun.rave.web.ui.component.Calendar;
import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.dianexus.triceps.modules.data.InstrumentVersionDAO;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.ValueChangeEvent;
import com.sun.rave.web.ui.component.Upload;
import com.sun.rave.web.ui.component.TextArea;
import com.sun.rave.web.ui.model.UploadedFile;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import jxl.Cell;
import jxl.Sheet;
import org.dianexus.triceps.modules.data.InstrumentDAO;
import com.sun.rave.web.ui.component.PasswordField;
import com.sun.rave.web.ui.component.PropertySheet;
import com.sun.rave.web.ui.component.PropertySheetSection;
import com.sun.rave.web.ui.component.Property;

/**
 *
 * @author ISTCGXL
 */
public class Page1Test extends TestCase {
    
    public Page1Test(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(Page1Test.class);
        
        return suite;
    }

    /**
     * Test of getPage1 method, of class datamanagementfacility.Page1.
     */
    public void testGetPage1() {
        System.out.println("testGetPage1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setPage1 method, of class datamanagementfacility.Page1.
     */
    public void testSetPage1() {
        System.out.println("testSetPage1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getHtml1 method, of class datamanagementfacility.Page1.
     */
    public void testGetHtml1() {
        System.out.println("testGetHtml1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setHtml1 method, of class datamanagementfacility.Page1.
     */
    public void testSetHtml1() {
        System.out.println("testSetHtml1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getHead1 method, of class datamanagementfacility.Page1.
     */
    public void testGetHead1() {
        System.out.println("testGetHead1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setHead1 method, of class datamanagementfacility.Page1.
     */
    public void testSetHead1() {
        System.out.println("testSetHead1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getLink1 method, of class datamanagementfacility.Page1.
     */
    public void testGetLink1() {
        System.out.println("testGetLink1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setLink1 method, of class datamanagementfacility.Page1.
     */
    public void testSetLink1() {
        System.out.println("testSetLink1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getBody1 method, of class datamanagementfacility.Page1.
     */
    public void testGetBody1() {
        System.out.println("testGetBody1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setBody1 method, of class datamanagementfacility.Page1.
     */
    public void testSetBody1() {
        System.out.println("testSetBody1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getForm1 method, of class datamanagementfacility.Page1.
     */
    public void testGetForm1() {
        System.out.println("testGetForm1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setForm1 method, of class datamanagementfacility.Page1.
     */
    public void testSetForm1() {
        System.out.println("testSetForm1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTabSet1 method, of class datamanagementfacility.Page1.
     */
    public void testGetTabSet1() {
        System.out.println("testGetTabSet1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTabSet1 method, of class datamanagementfacility.Page1.
     */
    public void testSetTabSet1() {
        System.out.println("testSetTabSet1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTab1 method, of class datamanagementfacility.Page1.
     */
    public void testGetTab1() {
        System.out.println("testGetTab1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTab1 method, of class datamanagementfacility.Page1.
     */
    public void testSetTab1() {
        System.out.println("testSetTab1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getLayoutPanel1 method, of class datamanagementfacility.Page1.
     */
    public void testGetLayoutPanel1() {
        System.out.println("testGetLayoutPanel1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setLayoutPanel1 method, of class datamanagementfacility.Page1.
     */
    public void testSetLayoutPanel1() {
        System.out.println("testSetLayoutPanel1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getLayoutPanel2 method, of class datamanagementfacility.Page1.
     */
    public void testGetLayoutPanel2() {
        System.out.println("testGetLayoutPanel2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setLayoutPanel2 method, of class datamanagementfacility.Page1.
     */
    public void testSetLayoutPanel2() {
        System.out.println("testSetLayoutPanel2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField1 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField1() {
        System.out.println("testGetTextField1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField1 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField1() {
        System.out.println("testSetTextField1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getImage1 method, of class datamanagementfacility.Page1.
     */
    public void testGetImage1() {
        System.out.println("testGetImage1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setImage1 method, of class datamanagementfacility.Page1.
     */
    public void testSetImage1() {
        System.out.println("testSetImage1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText1 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText1() {
        System.out.println("testGetStaticText1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText1 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText1() {
        System.out.println("testSetStaticText1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText2 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText2() {
        System.out.println("testGetStaticText2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText2 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText2() {
        System.out.println("testSetStaticText2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText3 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText3() {
        System.out.println("testGetStaticText3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText3 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText3() {
        System.out.println("testSetStaticText3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton1 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton1() {
        System.out.println("testGetButton1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton1 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton1() {
        System.out.println("testSetButton1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getHyperlink1 method, of class datamanagementfacility.Page1.
     */
    public void testGetHyperlink1() {
        System.out.println("testGetHyperlink1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setHyperlink1 method, of class datamanagementfacility.Page1.
     */
    public void testSetHyperlink1() {
        System.out.println("testSetHyperlink1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getPageSeparator1 method, of class datamanagementfacility.Page1.
     */
    public void testGetPageSeparator1() {
        System.out.println("testGetPageSeparator1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setPageSeparator1 method, of class datamanagementfacility.Page1.
     */
    public void testSetPageSeparator1() {
        System.out.println("testSetPageSeparator1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown1 method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown1() {
        System.out.println("testGetDropDown1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown1 method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown1() {
        System.out.println("testSetDropDown1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton2 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton2() {
        System.out.println("testGetButton2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton2 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton2() {
        System.out.println("testSetButton2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getGridPanel1 method, of class datamanagementfacility.Page1.
     */
    public void testGetGridPanel1() {
        System.out.println("testGetGridPanel1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setGridPanel1 method, of class datamanagementfacility.Page1.
     */
    public void testSetGridPanel1() {
        System.out.println("testSetGridPanel1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText4 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText4() {
        System.out.println("testGetStaticText4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText4 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText4() {
        System.out.println("testSetStaticText4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText5 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText5() {
        System.out.println("testGetStaticText5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText5 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText5() {
        System.out.println("testSetStaticText5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getUsersDataProvider method, of class datamanagementfacility.Page1.
     */
    public void testGetUsersDataProvider() {
        System.out.println("testGetUsersDataProvider");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setUsersDataProvider method, of class datamanagementfacility.Page1.
     */
    public void testSetUsersDataProvider() {
        System.out.println("testSetUsersDataProvider");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getInstrument_versionDataProvider method, of class datamanagementfacility.Page1.
     */
    public void testGetInstrument_versionDataProvider() {
        System.out.println("testGetInstrument_versionDataProvider");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setInstrument_versionDataProvider method, of class datamanagementfacility.Page1.
     */
    public void testSetInstrument_versionDataProvider() {
        System.out.println("testSetInstrument_versionDataProvider");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTab2 method, of class datamanagementfacility.Page1.
     */
    public void testGetTab2() {
        System.out.println("testGetTab2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTab2 method, of class datamanagementfacility.Page1.
     */
    public void testSetTab2() {
        System.out.println("testSetTab2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getLayoutPanel3 method, of class datamanagementfacility.Page1.
     */
    public void testGetLayoutPanel3() {
        System.out.println("testGetLayoutPanel3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setLayoutPanel3 method, of class datamanagementfacility.Page1.
     */
    public void testSetLayoutPanel3() {
        System.out.println("testSetLayoutPanel3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTab3 method, of class datamanagementfacility.Page1.
     */
    public void testGetTab3() {
        System.out.println("testGetTab3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTab3 method, of class datamanagementfacility.Page1.
     */
    public void testSetTab3() {
        System.out.println("testSetTab3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getLayoutPanel4 method, of class datamanagementfacility.Page1.
     */
    public void testGetLayoutPanel4() {
        System.out.println("testGetLayoutPanel4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setLayoutPanel4 method, of class datamanagementfacility.Page1.
     */
    public void testSetLayoutPanel4() {
        System.out.println("testSetLayoutPanel4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getInstrument_sessionDataProvider method, of class datamanagementfacility.Page1.
     */
    public void testGetInstrument_sessionDataProvider() {
        System.out.println("testGetInstrument_sessionDataProvider");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setInstrument_sessionDataProvider method, of class datamanagementfacility.Page1.
     */
    public void testSetInstrument_sessionDataProvider() {
        System.out.println("testSetInstrument_sessionDataProvider");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTable2 method, of class datamanagementfacility.Page1.
     */
    public void testGetTable2() {
        System.out.println("testGetTable2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTable2 method, of class datamanagementfacility.Page1.
     */
    public void testSetTable2() {
        System.out.println("testSetTable2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTableRowGroup2 method, of class datamanagementfacility.Page1.
     */
    public void testGetTableRowGroup2() {
        System.out.println("testGetTableRowGroup2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTableRowGroup2 method, of class datamanagementfacility.Page1.
     */
    public void testSetTableRowGroup2() {
        System.out.println("testSetTableRowGroup2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText14 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText14() {
        System.out.println("testGetStaticText14");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText14 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText14() {
        System.out.println("testSetStaticText14");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getInstrument_sessionDataProvider1 method, of class datamanagementfacility.Page1.
     */
    public void testGetInstrument_sessionDataProvider1() {
        System.out.println("testGetInstrument_sessionDataProvider1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setInstrument_sessionDataProvider1 method, of class datamanagementfacility.Page1.
     */
    public void testSetInstrument_sessionDataProvider1() {
        System.out.println("testSetInstrument_sessionDataProvider1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getGridPanel2 method, of class datamanagementfacility.Page1.
     */
    public void testGetGridPanel2() {
        System.out.println("testGetGridPanel2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setGridPanel2 method, of class datamanagementfacility.Page1.
     */
    public void testSetGridPanel2() {
        System.out.println("testSetGridPanel2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText6 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText6() {
        System.out.println("testGetStaticText6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText6 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText6() {
        System.out.println("testSetStaticText6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText7 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText7() {
        System.out.println("testGetStaticText7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText7 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText7() {
        System.out.println("testSetStaticText7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText8 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText8() {
        System.out.println("testGetStaticText8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText8 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText8() {
        System.out.println("testSetStaticText8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText9 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText9() {
        System.out.println("testGetStaticText9");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText9 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText9() {
        System.out.println("testSetStaticText9");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText10 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText10() {
        System.out.println("testGetStaticText10");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText10 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText10() {
        System.out.println("testSetStaticText10");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText15 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText15() {
        System.out.println("testGetStaticText15");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText15 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText15() {
        System.out.println("testSetStaticText15");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText16 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText16() {
        System.out.println("testGetStaticText16");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText16 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText16() {
        System.out.println("testSetStaticText16");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText17 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText17() {
        System.out.println("testGetStaticText17");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText17 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText17() {
        System.out.println("testSetStaticText17");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText18 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText18() {
        System.out.println("testGetStaticText18");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText18 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText18() {
        System.out.println("testSetStaticText18");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText19 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText19() {
        System.out.println("testGetStaticText19");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText19 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText19() {
        System.out.println("testSetStaticText19");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText20 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText20() {
        System.out.println("testGetStaticText20");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText20 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText20() {
        System.out.println("testSetStaticText20");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText21 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText21() {
        System.out.println("testGetStaticText21");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText21 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText21() {
        System.out.println("testSetStaticText21");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText22 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText22() {
        System.out.println("testGetStaticText22");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText22 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText22() {
        System.out.println("testSetStaticText22");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText23 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText23() {
        System.out.println("testGetStaticText23");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText23 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText23() {
        System.out.println("testSetStaticText23");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText24 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText24() {
        System.out.println("testGetStaticText24");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText24 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText24() {
        System.out.println("testSetStaticText24");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText25 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText25() {
        System.out.println("testGetStaticText25");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText25 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText25() {
        System.out.println("testSetStaticText25");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText26 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText26() {
        System.out.println("testGetStaticText26");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText26 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText26() {
        System.out.println("testSetStaticText26");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTableColumn2 method, of class datamanagementfacility.Page1.
     */
    public void testGetTableColumn2() {
        System.out.println("testGetTableColumn2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTableColumn2 method, of class datamanagementfacility.Page1.
     */
    public void testSetTableColumn2() {
        System.out.println("testSetTableColumn2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText12 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText12() {
        System.out.println("testGetStaticText12");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText12 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText12() {
        System.out.println("testSetStaticText12");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTableColumn6 method, of class datamanagementfacility.Page1.
     */
    public void testGetTableColumn6() {
        System.out.println("testGetTableColumn6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTableColumn6 method, of class datamanagementfacility.Page1.
     */
    public void testSetTableColumn6() {
        System.out.println("testSetTableColumn6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText29 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText29() {
        System.out.println("testGetStaticText29");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText29 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText29() {
        System.out.println("testSetStaticText29");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTableColumn7 method, of class datamanagementfacility.Page1.
     */
    public void testGetTableColumn7() {
        System.out.println("testGetTableColumn7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTableColumn7 method, of class datamanagementfacility.Page1.
     */
    public void testSetTableColumn7() {
        System.out.println("testSetTableColumn7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText30 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText30() {
        System.out.println("testGetStaticText30");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText30 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText30() {
        System.out.println("testSetStaticText30");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText11 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText11() {
        System.out.println("testGetStaticText11");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText11 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText11() {
        System.out.println("testSetStaticText11");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText13 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText13() {
        System.out.println("testGetStaticText13");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText13 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText13() {
        System.out.println("testSetStaticText13");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText27 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText27() {
        System.out.println("testGetStaticText27");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText27 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText27() {
        System.out.println("testSetStaticText27");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText28 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText28() {
        System.out.println("testGetStaticText28");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText28 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText28() {
        System.out.println("testSetStaticText28");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getGridPanel3 method, of class datamanagementfacility.Page1.
     */
    public void testGetGridPanel3() {
        System.out.println("testGetGridPanel3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setGridPanel3 method, of class datamanagementfacility.Page1.
     */
    public void testSetGridPanel3() {
        System.out.println("testSetGridPanel3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText33 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText33() {
        System.out.println("testGetStaticText33");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText33 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText33() {
        System.out.println("testSetStaticText33");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getGridPanel4 method, of class datamanagementfacility.Page1.
     */
    public void testGetGridPanel4() {
        System.out.println("testGetGridPanel4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setGridPanel4 method, of class datamanagementfacility.Page1.
     */
    public void testSetGridPanel4() {
        System.out.println("testSetGridPanel4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText34 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText34() {
        System.out.println("testGetStaticText34");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText34 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText34() {
        System.out.println("testSetStaticText34");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText35 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText35() {
        System.out.println("testGetStaticText35");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText35 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText35() {
        System.out.println("testSetStaticText35");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText36 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText36() {
        System.out.println("testGetStaticText36");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText36 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText36() {
        System.out.println("testSetStaticText36");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText37 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText37() {
        System.out.println("testGetStaticText37");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText37 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText37() {
        System.out.println("testSetStaticText37");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown2 method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown2() {
        System.out.println("testGetDropDown2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown2 method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown2() {
        System.out.println("testSetDropDown2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown2DefaultOptions method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown2DefaultOptions() {
        System.out.println("testGetDropDown2DefaultOptions");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown2DefaultOptions method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown2DefaultOptions() {
        System.out.println("testSetDropDown2DefaultOptions");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown3 method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown3() {
        System.out.println("testGetDropDown3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown3 method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown3() {
        System.out.println("testSetDropDown3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown3DefaultOptions method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown3DefaultOptions() {
        System.out.println("testGetDropDown3DefaultOptions");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown3DefaultOptions method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown3DefaultOptions() {
        System.out.println("testSetDropDown3DefaultOptions");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown4 method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown4() {
        System.out.println("testGetDropDown4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown4 method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown4() {
        System.out.println("testSetDropDown4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown4DefaultOptions method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown4DefaultOptions() {
        System.out.println("testGetDropDown4DefaultOptions");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown4DefaultOptions method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown4DefaultOptions() {
        System.out.println("testSetDropDown4DefaultOptions");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton3 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton3() {
        System.out.println("testGetButton3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton3 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton3() {
        System.out.println("testSetButton3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText38 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText38() {
        System.out.println("testGetStaticText38");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText38 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText38() {
        System.out.println("testSetStaticText38");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText39 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText39() {
        System.out.println("testGetStaticText39");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText39 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText39() {
        System.out.println("testSetStaticText39");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText40 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText40() {
        System.out.println("testGetStaticText40");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText40 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText40() {
        System.out.println("testSetStaticText40");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText41 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText41() {
        System.out.println("testGetStaticText41");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText41 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText41() {
        System.out.println("testSetStaticText41");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText42 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText42() {
        System.out.println("testGetStaticText42");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText42 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText42() {
        System.out.println("testSetStaticText42");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getHyperlink2 method, of class datamanagementfacility.Page1.
     */
    public void testGetHyperlink2() {
        System.out.println("testGetHyperlink2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setHyperlink2 method, of class datamanagementfacility.Page1.
     */
    public void testSetHyperlink2() {
        System.out.println("testSetHyperlink2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText43 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText43() {
        System.out.println("testGetStaticText43");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText43 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText43() {
        System.out.println("testSetStaticText43");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText44 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText44() {
        System.out.println("testGetStaticText44");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText44 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText44() {
        System.out.println("testSetStaticText44");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getCalendar1 method, of class datamanagementfacility.Page1.
     */
    public void testGetCalendar1() {
        System.out.println("testGetCalendar1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setCalendar1 method, of class datamanagementfacility.Page1.
     */
    public void testSetCalendar1() {
        System.out.println("testSetCalendar1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getCalendar2 method, of class datamanagementfacility.Page1.
     */
    public void testGetCalendar2() {
        System.out.println("testGetCalendar2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setCalendar2 method, of class datamanagementfacility.Page1.
     */
    public void testSetCalendar2() {
        System.out.println("testSetCalendar2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText45 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText45() {
        System.out.println("testGetStaticText45");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText45 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText45() {
        System.out.println("testSetStaticText45");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getHyperlink3 method, of class datamanagementfacility.Page1.
     */
    public void testGetHyperlink3() {
        System.out.println("testGetHyperlink3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setHyperlink3 method, of class datamanagementfacility.Page1.
     */
    public void testSetHyperlink3() {
        System.out.println("testSetHyperlink3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getHyperlink3Text method, of class datamanagementfacility.Page1.
     */
    public void testGetHyperlink3Text() {
        System.out.println("testGetHyperlink3Text");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setHyperlink3Text method, of class datamanagementfacility.Page1.
     */
    public void testSetHyperlink3Text() {
        System.out.println("testSetHyperlink3Text");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getGridPanel5 method, of class datamanagementfacility.Page1.
     */
    public void testGetGridPanel5() {
        System.out.println("testGetGridPanel5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setGridPanel5 method, of class datamanagementfacility.Page1.
     */
    public void testSetGridPanel5() {
        System.out.println("testSetGridPanel5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText46 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText46() {
        System.out.println("testGetStaticText46");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText46 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText46() {
        System.out.println("testSetStaticText46");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText47 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText47() {
        System.out.println("testGetStaticText47");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText47 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText47() {
        System.out.println("testSetStaticText47");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText48 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText48() {
        System.out.println("testGetStaticText48");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText48 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText48() {
        System.out.println("testSetStaticText48");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getFileUpload1 method, of class datamanagementfacility.Page1.
     */
    public void testGetFileUpload1() {
        System.out.println("testGetFileUpload1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setFileUpload1 method, of class datamanagementfacility.Page1.
     */
    public void testSetFileUpload1() {
        System.out.println("testSetFileUpload1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText49 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText49() {
        System.out.println("testGetStaticText49");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText49 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText49() {
        System.out.println("testSetStaticText49");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getInstrumentName method, of class datamanagementfacility.Page1.
     */
    public void testGetInstrumentName() {
        System.out.println("testGetInstrumentName");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setInstrumentName method, of class datamanagementfacility.Page1.
     */
    public void testSetInstrumentName() {
        System.out.println("testSetInstrumentName");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText50 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText50() {
        System.out.println("testGetStaticText50");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText50 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText50() {
        System.out.println("testSetStaticText50");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText51 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText51() {
        System.out.println("testGetStaticText51");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText51 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText51() {
        System.out.println("testSetStaticText51");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getInstrumentDescription method, of class datamanagementfacility.Page1.
     */
    public void testGetInstrumentDescription() {
        System.out.println("testGetInstrumentDescription");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setInstrumentDescription method, of class datamanagementfacility.Page1.
     */
    public void testSetInstrumentDescription() {
        System.out.println("testSetInstrumentDescription");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText52 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText52() {
        System.out.println("testGetStaticText52");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText52 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText52() {
        System.out.println("testSetStaticText52");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText53 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText53() {
        System.out.println("testGetStaticText53");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText53 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText53() {
        System.out.println("testSetStaticText53");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText54 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText54() {
        System.out.println("testGetStaticText54");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText54 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText54() {
        System.out.println("testSetStaticText54");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getInstrumentVersion method, of class datamanagementfacility.Page1.
     */
    public void testGetInstrumentVersion() {
        System.out.println("testGetInstrumentVersion");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setInstrumentVersion method, of class datamanagementfacility.Page1.
     */
    public void testSetInstrumentVersion() {
        System.out.println("testSetInstrumentVersion");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText55 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText55() {
        System.out.println("testGetStaticText55");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText55 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText55() {
        System.out.println("testSetStaticText55");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton4 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton4() {
        System.out.println("testGetButton4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton4 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton4() {
        System.out.println("testSetButton4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStatus method, of class datamanagementfacility.Page1.
     */
    public void testGetStatus() {
        System.out.println("testGetStatus");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStatus method, of class datamanagementfacility.Page1.
     */
    public void testSetStatus() {
        System.out.println("testSetStatus");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getGridPanel6 method, of class datamanagementfacility.Page1.
     */
    public void testGetGridPanel6() {
        System.out.println("testGetGridPanel6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setGridPanel6 method, of class datamanagementfacility.Page1.
     */
    public void testSetGridPanel6() {
        System.out.println("testSetGridPanel6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText56 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText56() {
        System.out.println("testGetStaticText56");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText56 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText56() {
        System.out.println("testSetStaticText56");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText57 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText57() {
        System.out.println("testGetStaticText57");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText57 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText57() {
        System.out.println("testSetStaticText57");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getStaticText58 method, of class datamanagementfacility.Page1.
     */
    public void testGetStaticText58() {
        System.out.println("testGetStaticText58");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setStaticText58 method, of class datamanagementfacility.Page1.
     */
    public void testSetStaticText58() {
        System.out.println("testSetStaticText58");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDropDown1Converter method, of class datamanagementfacility.Page1.
     */
    public void testGetDropDown1Converter() {
        System.out.println("testGetDropDown1Converter");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setDropDown1Converter method, of class datamanagementfacility.Page1.
     */
    public void testSetDropDown1Converter() {
        System.out.println("testSetDropDown1Converter");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField2 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField2() {
        System.out.println("testGetTextField2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField2 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField2() {
        System.out.println("testSetTextField2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTab4 method, of class datamanagementfacility.Page1.
     */
    public void testGetTab4() {
        System.out.println("testGetTab4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTab4 method, of class datamanagementfacility.Page1.
     */
    public void testSetTab4() {
        System.out.println("testSetTab4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getLayoutPanel5 method, of class datamanagementfacility.Page1.
     */
    public void testGetLayoutPanel5() {
        System.out.println("testGetLayoutPanel5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setLayoutPanel5 method, of class datamanagementfacility.Page1.
     */
    public void testSetLayoutPanel5() {
        System.out.println("testSetLayoutPanel5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getPropertySheet1 method, of class datamanagementfacility.Page1.
     */
    public void testGetPropertySheet1() {
        System.out.println("testGetPropertySheet1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setPropertySheet1 method, of class datamanagementfacility.Page1.
     */
    public void testSetPropertySheet1() {
        System.out.println("testSetPropertySheet1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getSection1 method, of class datamanagementfacility.Page1.
     */
    public void testGetSection1() {
        System.out.println("testGetSection1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setSection1 method, of class datamanagementfacility.Page1.
     */
    public void testSetSection1() {
        System.out.println("testSetSection1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty1 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty1() {
        System.out.println("testGetProperty1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty1 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty1() {
        System.out.println("testSetProperty1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty2 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty2() {
        System.out.println("testGetProperty2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty2 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty2() {
        System.out.println("testSetProperty2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty3 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty3() {
        System.out.println("testGetProperty3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty3 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty3() {
        System.out.println("testSetProperty3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty4 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty4() {
        System.out.println("testGetProperty4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty4 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty4() {
        System.out.println("testSetProperty4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty5 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty5() {
        System.out.println("testGetProperty5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty5 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty5() {
        System.out.println("testSetProperty5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty6 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty6() {
        System.out.println("testGetProperty6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty6 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty6() {
        System.out.println("testSetProperty6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField3 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField3() {
        System.out.println("testGetTextField3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField3 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField3() {
        System.out.println("testSetTextField3");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField4 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField4() {
        System.out.println("testGetTextField4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField4 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField4() {
        System.out.println("testSetTextField4");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField5 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField5() {
        System.out.println("testGetTextField5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField5 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField5() {
        System.out.println("testSetTextField5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField6 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField6() {
        System.out.println("testGetTextField6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField6 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField6() {
        System.out.println("testSetTextField6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField7 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField7() {
        System.out.println("testGetTextField7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField7 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField7() {
        System.out.println("testSetTextField7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTextField8 method, of class datamanagementfacility.Page1.
     */
    public void testGetTextField8() {
        System.out.println("testGetTextField8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTextField8 method, of class datamanagementfacility.Page1.
     */
    public void testSetTextField8() {
        System.out.println("testSetTextField8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton5 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton5() {
        System.out.println("testGetButton5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton5 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton5() {
        System.out.println("testSetButton5");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getGridPanel7 method, of class datamanagementfacility.Page1.
     */
    public void testGetGridPanel7() {
        System.out.println("testGetGridPanel7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setGridPanel7 method, of class datamanagementfacility.Page1.
     */
    public void testSetGridPanel7() {
        System.out.println("testSetGridPanel7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getPropertySheet2 method, of class datamanagementfacility.Page1.
     */
    public void testGetPropertySheet2() {
        System.out.println("testGetPropertySheet2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setPropertySheet2 method, of class datamanagementfacility.Page1.
     */
    public void testSetPropertySheet2() {
        System.out.println("testSetPropertySheet2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getSection2 method, of class datamanagementfacility.Page1.
     */
    public void testGetSection2() {
        System.out.println("testGetSection2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setSection2 method, of class datamanagementfacility.Page1.
     */
    public void testSetSection2() {
        System.out.println("testSetSection2");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty7 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty7() {
        System.out.println("testGetProperty7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty7 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty7() {
        System.out.println("testSetProperty7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton6 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton6() {
        System.out.println("testGetButton6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton6 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton6() {
        System.out.println("testSetButton6");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty8 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty8() {
        System.out.println("testGetProperty8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty8 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty8() {
        System.out.println("testSetProperty8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton7 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton7() {
        System.out.println("testGetButton7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton7 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton7() {
        System.out.println("testSetButton7");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getProperty9 method, of class datamanagementfacility.Page1.
     */
    public void testGetProperty9() {
        System.out.println("testGetProperty9");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setProperty9 method, of class datamanagementfacility.Page1.
     */
    public void testSetProperty9() {
        System.out.println("testSetProperty9");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getButton8 method, of class datamanagementfacility.Page1.
     */
    public void testGetButton8() {
        System.out.println("testGetButton8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setButton8 method, of class datamanagementfacility.Page1.
     */
    public void testSetButton8() {
        System.out.println("testSetButton8");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getRequestBean1 method, of class datamanagementfacility.Page1.
     */
    public void testGetRequestBean1() {
        System.out.println("testGetRequestBean1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getApplicationBean1 method, of class datamanagementfacility.Page1.
     */
    public void testGetApplicationBean1() {
        System.out.println("testGetApplicationBean1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getSessionBean1 method, of class datamanagementfacility.Page1.
     */
    public void testGetSessionBean1() {
        System.out.println("testGetSessionBean1");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of init method, of class datamanagementfacility.Page1.
     */
    public void testInit() {
        System.out.println("testInit");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of preprocess method, of class datamanagementfacility.Page1.
     */
    public void testPreprocess() {
        System.out.println("testPreprocess");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of prerender method, of class datamanagementfacility.Page1.
     */
    public void testPrerender() {
        System.out.println("testPrerender");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of destroy method, of class datamanagementfacility.Page1.
     */
    public void testDestroy() {
        System.out.println("testDestroy");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of button1_action method, of class datamanagementfacility.Page1.
     */
    public void testButton1_action() {
        System.out.println("testButton1_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of hyperlink1_action method, of class datamanagementfacility.Page1.
     */
    public void testHyperlink1_action() {
        System.out.println("testHyperlink1_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of button2_action method, of class datamanagementfacility.Page1.
     */
    public void testButton2_action() {
        System.out.println("testButton2_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of tab1_action method, of class datamanagementfacility.Page1.
     */
    public void testTab1_action() {
        System.out.println("testTab1_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of button3_action method, of class datamanagementfacility.Page1.
     */
    public void testButton3_action() {
        System.out.println("testButton3_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of tab3_action method, of class datamanagementfacility.Page1.
     */
    public void testTab3_action() {
        System.out.println("testTab3_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of calendar1_validate method, of class datamanagementfacility.Page1.
     */
    public void testCalendar1_validate() {
        System.out.println("testCalendar1_validate");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of dropDown1_processValueChange method, of class datamanagementfacility.Page1.
     */
    public void testDropDown1_processValueChange() {
        System.out.println("testDropDown1_processValueChange");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of button4_action method, of class datamanagementfacility.Page1.
     */
    public void testButton4_action() {
        System.out.println("testButton4_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of tab2_action method, of class datamanagementfacility.Page1.
     */
    public void testTab2_action() {
        System.out.println("testTab2_action");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of fileUpload1_processValueChange method, of class datamanagementfacility.Page1.
     */
    public void testFileUpload1_processValueChange() {
        System.out.println("testFileUpload1_processValueChange");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }
    
}
