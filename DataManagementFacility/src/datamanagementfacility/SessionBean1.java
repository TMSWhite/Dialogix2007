/*
 * SessionBean1.java
 *
 * Created on July 7, 2006, 8:07 AM
 * Copyright ISTCGXL
 */
package datamanagementfacility;

import com.sun.rave.web.ui.appbase.AbstractSessionBean;
import javax.faces.FacesException;
import com.sun.sql.rowset.CachedRowSetXImpl;
import java.util.Date;
import org.dianexus.triceps.DialogixUser;

/**
 * <p>Session scope data bean for your application.  Create properties
 *  here to represent cached data that should be made available across
 *  multiple HTTP requests for an individual user.</p>
 *
 * <p>An instance of this class will be created for you automatically,
 * the first time your application evaluates a value binding expression
 * or method binding expression that references a managed bean using
 * this class.</p>
 */
public class SessionBean1 extends AbstractSessionBean {
    // <editor-fold defaultstate="collapsed" desc="Creator-managed Component Definition">
    private int __placeholder;
    
    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
        
        
        //*uncomment for local deploy
        instrument_versionRowSet.setDataSourceName("java:comp/env/jdbc/datasource1");
        instrument_versionRowSet.setCommand("SELECT ALL instrument_version.instrument_version_id, \n                    instrument_version.instrument_id, \n                    instrument_version.instance_table_name, \n                    instrument_version.instrument_notes, \n                    instrument_version.instrument_status, \n                    user_permission.id, \n                    user_permission.user_id, \n                    user_permission.instrument_id, \n                    user_permission.role, \n                    user_permission.comment \nFROM instrument_version, user_permission\nWHERE user_permission.instrument_id = instrument_version.instrument_id\n          AND user_permission.user_id = ? ");
        instrument_versionRowSet.setTableName("instrument_version");
        instrument_sessionRowSet.setDataSourceName("java:comp/env/jdbc/datasource1");
        instrument_sessionRowSet.setCommand("SELECT ALL instrument_session.instrument_session_id, \n                    instrument_session.start_time, \n                    instrument_session.end_time, \n                    instrument_session.instrument_id, \n                    instrument_session.user_id, \n                    instrument_session.first_group, \n                    instrument_session.last_group, \n                    instrument_session.last_action, \n                    instrument_session.last_access, \n                    instrument_session.statusMsg, \n                    instrument_session.instrument_version_id \nFROM instrument_session\nWHERE instrument_session.instrument_version_id = ? ");
        instrument_sessionRowSet.setTableName("instrument_session");
        user_permissionRowSet.setDataSourceName("java:comp/env/jdbc/datasource1");
        user_permissionRowSet.setCommand("SELECT ALL user_permission.id, \n                    user_permission.user_id, \n                    user_permission.instrument_id, \n                    user_permission.role, \n                    user_permission.comment, \n                    instrument.instrument_id, \n                    instrument.instrument_name, \n                    instrument.instrument_description \nFROM user_permission, instrument\nWHERE instrument.instrument_id = user_permission.instrument_id\n          AND user_permission.user_id = ? ");
        user_permissionRowSet.setTableName("user_permission");
        instrumentRowSet.setDataSourceName("java:comp/env/jdbc/datasource1");
        instrumentRowSet.setCommand("SELECT ALL instrument.instrument_id, \n                    instrument.instrument_name, \n                    instrument.instrument_description \nFROM instrument ");
        instrumentRowSet.setTableName("instrument");
        rolesRowSet.setDataSourceName("java:comp/env/jdbc/datasource1");
        rolesRowSet.setCommand("SELECT ALL roles.id, \n                    roles.name \nFROM roles ");
        rolesRowSet.setTableName("roles");
        instrument_sessionRowSet1.setDataSourceName("java:comp/env/jdbc/datasource1");
        instrument_sessionRowSet1.setCommand("SELECT ALL instrument_session.instrument_session_id, \n                    instrument_session.start_time, \n                    instrument_session.end_time, \n                    instrument_session.instrument_id, \n                    instrument_session.instrument_version_id, \n                    instrument_session.user_id, \n                    instrument_session.first_group, \n                    instrument_session.last_group, \n                    instrument_session.last_action, \n                    instrument_session.last_access, \n                    instrument_session.statusMsg \nFROM instrument_session ");
        instrument_sessionRowSet1.setTableName("instrument_session");
        usersRowSet.setDataSourceName("java:comp/env/jdbc/datasource1");
        usersRowSet.setCommand("SELECT ALL users.id, \n                    users.user_name, \n                    users.password, \n                    users.first_name, \n                    users.last_name, \n                    users.email, \n                    users.phone \nFROM users ");
        usersRowSet.setTableName("users");
        instrumentRowSet1.setDataSourceName("java:comp/env/jdbc/datasource1");
        instrumentRowSet1.setCommand("SELECT * FROM instrument");
        instrumentRowSet1.setTableName("instrument");
        sandboxRowSet.setDataSourceName("java:comp/env/jdbc/datasource1");
        sandboxRowSet.setCommand("SELECT * FROM sandbox");
        sandboxRowSet.setTableName("sandbox");
        sandboxRowSet1.setDataSourceName("java:comp/env/jdbc/datasource1");
        sandboxRowSet1.setCommand("SELECT ALL sandbox.id, \n                    sandbox.name, \n                    sandbox.application_path, \n                    sandbox.url, \n                    sandbox.port, \n                    sandbox_items.id, \n                    sandbox_items.sandbox_id, \n                    sandbox_items.instrument_id, \n                    sandbox_items.instrument_version_id, \n                    instrument.instrument_id, \n                    instrument.instrument_name, \n                    instrument.instrument_description, \n                    instrument_version.instrument_version_id, \n                    instrument_version.instrument_id, \n                    instrument_version.instance_table_name, \n                    instrument_version.major_version, \n                    instrument_version.minor_version, \n                    instrument_version.instrument_notes, \n                    instrument_version.instrument_status, \n                    instrument_version.instrument_version_id \nFROM sandbox, sandbox_items, instrument, instrument_version\nWHERE sandbox_items.sandbox_id = sandbox.id\n          AND instrument.instrument_id = sandbox_items.instrument_id\n          AND instrument_version.instrument_version_id = sandbox_items.instrument_version_id\n          AND sandbox.id = ? ");
        sandboxRowSet1.setTableName("sandbox");
        sandboxRowSet2.setDataSourceName("java:comp/env/jdbc/datasource1");
        sandboxRowSet2.setCommand("SELECT ALL sandbox.id, \n                    sandbox.name, \n                    sandbox.application_path, \n                    sandbox.url, \n                    sandbox.port, \n                    sandbox_user.id, \n                    sandbox_user.sandbox_id, \n                    sandbox_user.user_id, \n                    sandbox_user.role_id \nFROM sandbox, sandbox_user\nWHERE sandbox_user.sandbox_id = sandbox.id\n          AND sandbox_user.user_id = ? ");
        sandboxRowSet2.setTableName("sandbox");
        instrumentRowSet2.setDataSourceName("java:comp/env/jdbc/datasource1");
        instrumentRowSet2.setCommand("SELECT ALL instrument.instrument_id, \n                    instrument.instrument_name, \n                    instrument.instrument_description, \n                    instrument_version.instrument_version_id, \n                    instrument_version.instrument_id, \n                    instrument_version.instance_table_name, \n                    instrument_version.major_version, \n                    instrument_version.minor_version, \n                    instrument_version.instrument_notes, \n                    instrument_version.instrument_status, \n                    sandbox.id, \n                    sandbox.name, \n                    sandbox.application_path, \n                    sandbox.url, \n                    sandbox.port, \n                    sandbox_items.id, \n                    sandbox_items.sandbox_id, \n                    sandbox_items.instrument_id, \n                    sandbox_items.instrument_version_id \nFROM instrument, instrument_version, sandbox, sandbox_items\nWHERE sandbox_items.sandbox_id = sandbox.id\n          AND sandbox_items.instrument_id = instrument.instrument_id\n          AND instrument_version.instrument_version_id = sandbox_items.instrument_version_id\n          AND sandbox.id = ? ");
        instrumentRowSet2.setTableName("instrument");
        sandboxRowSet3.setDataSourceName("java:comp/env/jdbc/datasource1");
        sandboxRowSet3.setCommand("SELECT ALL sandbox.id, \n                    sandbox.name, \n                    sandbox.application_path, \n                    sandbox.url, \n                    sandbox.port, \n                    sandbox_user.id, \n                    sandbox_user.sandbox_id, \n                    sandbox_user.user_id, \n                    sandbox_user.role_id \nFROM sandbox, sandbox_user\nWHERE sandbox_user.sandbox_id = sandbox.id\n          AND sandbox_user.user_id = ? ");
        sandboxRowSet3.setTableName("sandbox");
        instrument_versionRowSet1.setDataSourceName("java:comp/env/jdbc/datasource1");
        instrument_versionRowSet1.setCommand("SELECT ALL instrument_version.instrument_version_id, \n                    instrument_version.instrument_id, \n                    instrument_version.instance_table_name, \n                    instrument_version.major_version, \n                    instrument_version.minor_version, \n                    instrument_version.instrument_notes, \n                    instrument_version.instrument_status, \n                    instrument.instrument_id, \n                    instrument.instrument_name, \n                    instrument.instrument_description, \n                    user_permission.id, \n                    user_permission.user_id, \n                    user_permission.instrument_id, \n                    user_permission.role, \n                    user_permission.comment \nFROM instrument_version, instrument, user_permission\nWHERE instrument.instrument_id = instrument_version.instrument_id\n          AND user_permission.instrument_id = instrument.instrument_id\n          AND user_permission.user_id = ? ");
        instrument_versionRowSet1.setTableName("instrument_version");
      
        /*/
        //uncomment for network deploy
        /*
        usersRowSet.setUrl("jdbc:mysql://localhost:3306/inst_database_test");
        usersRowSet.setCommand("SELECT * FROM users");
        usersRowSet.setTableName("users");
        usersRowSet.setUsername("istcgxl");
        usersRowSet.setPassword("mmrx0202");
        instrument_versionRowSet.setUrl("jdbc:mysql://localhost:3306/inst_database_test");
        instrument_versionRowSet.setCommand("SELECT ALL instrument_version.instrument_version_id, \n                    instrument_version.instrument_id, \n                    instrument_version.instance_table_name, \n                    instrument_version.instrument_notes, \n                    instrument_version.instrument_status, \n                    users.id, \n                    users.user_name, \n                    users.password, \n                    users.first_name, \n                    users.last_name, \n                    users.email, \n                    users.phone, \n                    user_permission.id, \n                    user_permission.user_id, \n                    user_permission.instrument_id, \n                    user_permission.role, \n                    user_permission.comment \nFROM instrument_version, users, user_permission\nWHERE user_permission.instrument_id = instrument_version.instrument_version_id\n          AND user_permission.user_id = users.id\n          AND users.id = ? ");
        instrument_versionRowSet.setTableName("instrument_version");
        instrument_versionRowSet.setUsername("istcgxl");
        instrument_versionRowSet.setPassword("mmrx0202");
        instrument_sessionRowSet.setUrl("jdbc:mysql://localhost:3306/inst_database_test");
        instrument_sessionRowSet.setCommand("SELECT ALL instrument_session.instrument_session_id, \n                    instrument_session.start_time, \n                    instrument_session.end_time, \n                    instrument_session.instrument_id, \n                    instrument_session.user_id, \n                    instrument_session.first_group, \n                    instrument_session.last_group, \n                    instrument_session.last_action, \n                    instrument_session.last_access, \n                    instrument_session.statusMsg \nFROM instrument_session\nWHERE instrument_session.instrument_id = ? ");
        instrument_sessionRowSet.setTableName("instrument_session");
        instrument_sessionRowSet.setUsername("istcgxl");
        instrument_sessionRowSet.setPassword("mmrx0202");
        instrument_sessionRowSet1.setUrl("jdbc:mysql://localhost:3306/inst_database_test");
        instrument_sessionRowSet1.setCommand("SELECT (MAX (end_time - start_time)) as max_time, (MIN (end_time - start_time)) as min_time FROM instrument_session WHERE 1 ");
        instrument_sessionRowSet1.setTableName("instrument_session");
        instrument_sessionRowSet1.setUsername("istcgxl");
        instrument_sessionRowSet1.setPassword("mmrx0202");
        */
    }

    private CachedRowSetXImpl instrument_versionRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrument_versionRowSet() {
        return instrument_versionRowSet;
    }

    public void setInstrument_versionRowSet(CachedRowSetXImpl crsxi) {
        this.instrument_versionRowSet = crsxi;
    }

    private CachedRowSetXImpl instrument_sessionRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrument_sessionRowSet() {
        return instrument_sessionRowSet;
    }

    public void setInstrument_sessionRowSet(CachedRowSetXImpl crsxi) {
        this.instrument_sessionRowSet = crsxi;
    }

    private CachedRowSetXImpl user_permissionRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getUser_permissionRowSet() {
        return user_permissionRowSet;
    }

    public void setUser_permissionRowSet(CachedRowSetXImpl crsxi) {
        this.user_permissionRowSet = crsxi;
    }

    private CachedRowSetXImpl instrumentRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrumentRowSet() {
        return instrumentRowSet;
    }

    public void setInstrumentRowSet(CachedRowSetXImpl crsxi) {
        this.instrumentRowSet = crsxi;
    }

    private CachedRowSetXImpl rolesRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getRolesRowSet() {
        return rolesRowSet;
    }

    public void setRolesRowSet(CachedRowSetXImpl crsxi) {
        this.rolesRowSet = crsxi;
    }

    private CachedRowSetXImpl instrument_sessionRowSet1 = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrument_sessionRowSet1() {
        return instrument_sessionRowSet1;
    }

    public void setInstrument_sessionRowSet1(CachedRowSetXImpl crsxi) {
        this.instrument_sessionRowSet1 = crsxi;
    }

    private CachedRowSetXImpl usersRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getUsersRowSet() {
        return usersRowSet;
    }

    public void setUsersRowSet(CachedRowSetXImpl crsxi) {
        this.usersRowSet = crsxi;
    }

    private CachedRowSetXImpl instrumentRowSet1 = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrumentRowSet1() {
        return instrumentRowSet1;
    }

    public void setInstrumentRowSet1(CachedRowSetXImpl crsxi) {
        this.instrumentRowSet1 = crsxi;
    }

    private CachedRowSetXImpl sandboxRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getSandboxRowSet() {
        return sandboxRowSet;
    }

    public void setSandboxRowSet(CachedRowSetXImpl crsxi) {
        this.sandboxRowSet = crsxi;
    }

    private CachedRowSetXImpl sandboxRowSet1 = new CachedRowSetXImpl();

    public CachedRowSetXImpl getSandboxRowSet1() {
        return sandboxRowSet1;
    }

    public void setSandboxRowSet1(CachedRowSetXImpl crsxi) {
        this.sandboxRowSet1 = crsxi;
    }

    private CachedRowSetXImpl sandboxRowSet2 = new CachedRowSetXImpl();

    public CachedRowSetXImpl getSandboxRowSet2() {
        return sandboxRowSet2;
    }

    public void setSandboxRowSet2(CachedRowSetXImpl crsxi) {
        this.sandboxRowSet2 = crsxi;
    }

    private CachedRowSetXImpl instrumentRowSet2 = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrumentRowSet2() {
        return instrumentRowSet2;
    }

    public void setInstrumentRowSet2(CachedRowSetXImpl crsxi) {
        this.instrumentRowSet2 = crsxi;
    }

    private CachedRowSetXImpl sandboxRowSet3 = new CachedRowSetXImpl();

    public CachedRowSetXImpl getSandboxRowSet3() {
        return sandboxRowSet3;
    }

    public void setSandboxRowSet3(CachedRowSetXImpl crsxi) {
        this.sandboxRowSet3 = crsxi;
    }

    private CachedRowSetXImpl instrument_versionRowSet1 = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrument_versionRowSet1() {
        return instrument_versionRowSet1;
    }

    public void setInstrument_versionRowSet1(CachedRowSetXImpl crsxi) {
        this.instrument_versionRowSet1 = crsxi;
    }
    // </editor-fold>


    /** 
     * <p>Construct a new session data bean instance.</p>
     */
    public SessionBean1() {
    }

    /** 
     * <p>Return a reference to the scoped data bean.</p>
     */
    protected ApplicationBean1 getApplicationBean1() {
        return (ApplicationBean1)getBean("ApplicationBean1");
    }


    /** 
     * <p>This method is called when this bean is initially added to
     * session scope.  Typically, this occurs as a result of evaluating
     * a value binding or method binding expression, which utilizes the
     * managed bean facility to instantiate this bean and store it into
     * session scope.</p>
     * 
     * <p>You may customize this method to initialize and cache data values
     * or resources that are required for the lifetime of a particular
     * user session.</p>
     */
    public void init() {
        // Perform initializations inherited from our superclass
        super.init();
        // Perform application initialization that must complete
        // *before* managed components are initialized
        // TODO - add your own initialiation code 
          // <editor-fold defaultstate="collapsed" desc="Creator-managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("SessionBean1 Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e: new FacesException(e);
        }
        // </editor-fold>
        // Perform application initialization that must complete
        // *after* managed components are initialized
        // TODO - add your own initialization code here

    }

    /** 
     * <p>This method is called when the session containing it is about to be
     * passivated.  Typically, this occurs in a distributed servlet container
     * when the session is about to be transferred to a different
     * container instance, after which the <code>activate()</code> method
     * will be called to indicate that the transfer is complete.</p>
     * 
     * <p>You may customize this method to release references to session data
     * or resources that can not be serialized with the session itself.</p>
     */
    public void passivate() {
    }

    /** 
     * <p>This method is called when the session containing it was
     * reactivated.</p>
     * 
     * <p>You may customize this method to reacquire references to session
     * data or resources that could not be serialized with the
     * session itself.</p>
     */
    public void activate() {
    }

    /** 
     * <p>This method is called when this bean is removed from
     * session scope.  Typically, this occurs as a result of
     * the session timing out or being terminated by the application.</p>
     * 
     * <p>You may customize this method to clean up resources allocated
     * during the execution of the <code>init()</code> method, or
     * at any later time during the lifetime of the application.</p>
     */
    public void destroy() {
    }

    /**
     * Holds value of property start_date.
     */
    private Date start_date;

    /**
     * Getter for property start_date.
     * @return Value of property start_date.
     */
    public Date getStart_date() {

        return this.start_date;
    }

    /**
     * Setter for property start_date.
     * @param start_date New value of property start_date.
     */
    public void setStart_date(Date start_date) {

        this.start_date = start_date;
    }

    /**
     * Holds value of property end_date.
     */
    private Date end_date;

    /**
     * Getter for property end_date.
     * @return Value of property end_date.
     */
    public Date getEnd_date() {

        return this.end_date;
    }

    /**
     * Setter for property end_date.
     * @param end_date New value of property end_date.
     */
    public void setEnd_date(Date end_date) {

        this.end_date = end_date;
    }

    /**
     * Holds value of property userId.
     */
    private int userId;

    /**
     * Getter for property userId.
     * @return Value of property userId.
     */
    public int getUserId() {

        return this.userId;
    }

    /**
     * Setter for property userId.
     * @param userId New value of property userId.
     */
    public void setUserId(int userId) {

        this.userId = userId;
    }

    /**
     * Holds value of property permissionsEditId.
     */
    private Integer permissionsEditId;

    /**
     * Getter for property permissionsEditId.
     * @return Value of property permissionsEditId.
     */
    public Integer getPermissionsEditId() {

        return this.permissionsEditId;
    }

    /**
     * Setter for property permissionsEditId.
     * @param permissionsEditId New value of property permissionsEditId.
     */
    public void setPermissionsEditId(Integer permissionsEditId) {

        this.permissionsEditId = permissionsEditId;
    }

    /**
     * Holds value of property dialogixUser.
     */
    private DialogixUser dialogixUser;

    /**
     * Getter for property dialogixUser.
     * @return Value of property dialogixUser.
     */
    public DialogixUser getDialogixUser() {

        return this.dialogixUser;
    }

    /**
     * Setter for property dialogixUser.
     * @param dialogixUser New value of property dialogixUser.
     */
    public void setDialogixUser(DialogixUser dialogixUser) {

        this.dialogixUser = dialogixUser;
    }

    /**
     * Holds value of property startDate.
     */
    private String startDate;

    /**
     * Getter for property startDate.
     * @return Value of property startDate.
     */
    public String getStartDate() {

        return this.startDate;
    }

    /**
     * Setter for property startDate.
     * @param startDate New value of property startDate.
     */
    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    /**
     * Holds value of property endDate.
     */
    private String endDate;

    /**
     * Getter for property endDate.
     * @return Value of property endDate.
     */
    public String getEndDate() {

        return this.endDate;
    }

    /**
     * Setter for property endDate.
     * @param endDate New value of property endDate.
     */
    public void setEndDate(String endDate) {

        this.endDate = endDate;
    }

    /**
     * Holds value of property selectedSandbox.
     */
    private int selectedSandbox;

    /**
     * Getter for property selectedSandbox.
     * @return Value of property selectedSandbox.
     */
    public int getSelectedSandbox() {

        return this.selectedSandbox;
    }

    /**
     * Setter for property selectedSandbox.
     * @param selectedSandbox New value of property selectedSandbox.
     */
    public void setSelectedSandbox(int selectedSandbox) {

        this.selectedSandbox = selectedSandbox;
    }
}
