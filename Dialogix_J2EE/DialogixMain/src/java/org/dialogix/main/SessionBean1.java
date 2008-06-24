/*
 * SessionBean1.java
 *
 * Created on Mar 25, 2008, 5:07:11 PM
 */
package org.dialogix.main;

import com.sun.rave.web.ui.appbase.AbstractSessionBean;
import com.sun.sql.rowset.CachedRowSetXImpl;
import javax.faces.FacesException;

/**
 * <p>Session scope data bean for your application.  Create properties
 *  here to represent cached data that should be made available across
 *  multiple HTTP requests for an individual user.</p>
 *
 * <p>An instance of this class will be created for you automatically,
 * the first time your application evaluates a value binding expression
 * or method binding expression that references a managed bean using
 * this class.</p>
 *
 * @author George
 */
public class SessionBean1 extends AbstractSessionBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private String version = "";
    private String userId;
    private String user = "";
    private String password = "";
    private boolean loggedIn = new Boolean(false);

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(String user) {
        this.user = user;
    }
    private String selectPage = "Logo";

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public String getSelectPage() {
        return selectPage;
    }

    /**
     * Set the value of selectPage
     *
     * @param user new value of user
     */
    public void setselectPage(String selectPage) {
        this.selectPage = selectPage;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    /**
     * Get the value of loggedIn
     *
     * @return the value of loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Set the value of loggedIn
     *
     * @param loggedIn new value of loggedIn
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    private void _init() throws Exception {

        actionsRowSet.setDataSourceName("java:comp/env/jdbc/mysql_dialogix_j2ee");
        String nav_query = "SELECT ALL person.first_name, " +
                "person.last_name, person.user_name, " +
                "person.email, person.phone, " +
                "role.codetype, " +
                "role.role, person_role.person_id, " +
                "person_role.role_id, " +
                "role_menu.menu_id, " +
                "menu.menu_code, menu.display_text, " +
                "menu.menu_name " +
                "FROM person, role, person_role, " +
                "role_menu, menu " +
                "WHERE person.person_id = person_role.person_id " +
                "AND person_role.role_id = role.role_id " +
                "AND role.role_id = role_menu.role_id " +
                "AND role_menu.menu_id = menu.menu_id " +
                "AND person.person_id = ? " +
                "ORDER BY role.role ASC , menu.menu_name ASC ";
        
        actionsRowSet.setCommand(nav_query);
        actionsRowSet.setTableName("user_actions");
        actionsRowSet.setObject(1, this.userId);

        instrument_versionsRowSet.setDataSourceName("java:comp/env/jdbc/dialogix_j2ee_MySQL");
        instrument_versionsRowSet.setCommand("SELECT * FROM instrument_version");
        instrument_versionsRowSet.setTableName("instrument_version");

        instrumentsRowSet.setDataSourceName("java:comp/env/jdbc/dialogix_j2ee_MySQL");
        instrumentsRowSet.setCommand("SELECT * FROM instrument");
        instrumentsRowSet.setTableName("instrument");
        
        dialogix_usersRowSet.setDataSourceName("java:comp/env/jdbc/mysql_dialogix_j2ee");
        dialogix_usersRowSet.setCommand("SELECT * FROM person WHERE person.user_name = ? AND person.pwd = ?");
        dialogix_usersRowSet.setTableName("person");        
    }
    private CachedRowSetXImpl actionsRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getActionsRowSet() {
        return actionsRowSet;
    }

    public void setActionsRowSet(CachedRowSetXImpl crsxi) {
        this.actionsRowSet = crsxi;
    }
    private CachedRowSetXImpl instrument_versionsRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrument_versionsRowSet() {
        return instrument_versionsRowSet;
    }

    public void setInstrument_versionsRowSet(CachedRowSetXImpl crsxi) {
        this.instrument_versionsRowSet = crsxi;
    }
    private CachedRowSetXImpl instrumentsRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getInstrumentsRowSet() {
        return instrumentsRowSet;
    }

    public void setInstrumentsRowSet(CachedRowSetXImpl crsxi) {
        this.instrumentsRowSet = crsxi;
    }
    private CachedRowSetXImpl dialogix_usersRowSet = new CachedRowSetXImpl();

    public CachedRowSetXImpl getDialogix_usersRowSet() {
        return dialogix_usersRowSet;
    }

    public void setDialogix_usersRowSet(CachedRowSetXImpl crsxi) {
        this.dialogix_usersRowSet = crsxi;
    }
    
    // </editor-fold>

    /**
     * <p>Construct a new session data bean instance.</p>
     */
    public SessionBean1() {
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
    @Override
    public void init() {
        // Perform initializations inherited from our superclass
        super.init();
        // Perform application initialization that must complete
        // *before* managed components are initialized
        // TODO - add your own initialiation code here

        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("SessionBean1 Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
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
    @Override
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
    @Override
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
    @Override
    public void destroy() {
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected ApplicationBean1 getApplicationBean1() {
        return (ApplicationBean1) getBean("ApplicationBean1");
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
