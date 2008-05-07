/*
 * Login1.java
 *
 * Created on Apr 2, 2008, 11:07:09 AM
 */
package org.dialogix.main;

import com.sun.data.provider.impl.CachedRowSetDataProvider;
import com.sun.rave.web.ui.appbase.AbstractFragmentBean;
import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.PasswordField;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.TextField;
import javax.faces.FacesException;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page
 * fragment.  This class contains component definitions (and initialization
 * code) for all components that you have defined on this fragment, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @author George
 */
public class Login1_1 extends AbstractFragmentBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
        dialogix_usersDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet) getValue("#{SessionBean1.dialogix_usersRowSet}"));
    }
    private CachedRowSetDataProvider dialogix_usersDataProvider = new CachedRowSetDataProvider();

    public CachedRowSetDataProvider getDialogix_usersDataProvider() {
        return dialogix_usersDataProvider;
    }

    public void setDialogix_usersDataProvider(CachedRowSetDataProvider crsdp) {
        this.dialogix_usersDataProvider = crsdp;
    }
    private TextField userField = new TextField();

    public TextField getUserField() {
        return userField;
    }

    public void setUserField(TextField tf) {
        this.userField = tf;
    }
    private PasswordField passwordField = new PasswordField();

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField pf) {
        this.passwordField = pf;
    }
    private StaticText tempText = new StaticText();

    public StaticText getTempText() {
        return tempText;
    }

    public void setTempText(StaticText st) {
        this.tempText = st;
    }
    private Button loginButton = new Button();

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button b) {
        this.loginButton = b;
    }
    // </editor-fold>

    public Login1_1() {
    }

    /**
     * <p>Callback method that is called whenever a page containing
     * this page fragment is navigated to, either directly via a URL,
     * or indirectly via page navigation.  Override this method to acquire
     * resources that will be needed for event handlers and lifecycle methods.</p>
     * 
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void init() {
        // Perform initializations inherited from our superclass
        super.init();
        // Perform application initialization that must complete
        // *before* managed components are initialized
        // TODO - add your own initialiation code here


        // <editor-fold defaultstate="collapsed" desc="Visual-Web-managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("Page1 Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
        }

    // </editor-fold>
    // Perform application initialization that must complete
    // *after* managed components are initialized
    // TODO - add your own initialization code here
    }

    /**
     * <p>Callback method that is called after rendering is completed for
     * this request, if <code>init()</code> was called.  Override this
     * method to release resources acquired in the <code>init()</code>
     * resources that will be needed for event handlers and lifecycle methods.</p>
     * 
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void destroy() {
        dialogix_usersDataProvider.close();
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected SessionBean1 getSessionBean1() {
        return (SessionBean1) getBean("SessionBean1");
    }

    
     /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
      protected MainMenu getMainMenu() {
        return (MainMenu) getBean("MainMenu");
    }
    
    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected RequestBean1 getRequestBean1() {
        return (RequestBean1) getBean("RequestBean1");
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected ApplicationBean1 getApplicationBean1() {
        return (ApplicationBean1) getBean("ApplicationBean1");
    }
    
  /**
     * <p>Login action.</p>
     *
     * @return reference to navigation for page
     */
    public String loginButton_action() {
        
        try {
            getSessionBean1().getDialogix_usersRowSet().setObject(
                    1, userField.getValue().toString());
            getSessionBean1().getDialogix_usersRowSet().setObject(
                    2, passwordField.getValue().toString());
            getSessionBean1().getDialogix_usersRowSet().execute();
            // If user with password is found - TODO confirm for precise security
            if (dialogix_usersDataProvider.getRowCount() == 1) {
                getSessionBean1().setLoggedIn(new Boolean(true));
                getSessionBean1().setUserId(
                        dialogix_usersDataProvider.getValue("dialogix_users.id").toString());
                getSessionBean1().getActionsRowSet().setObject(
                        1, getSessionBean1().getUserId());
                getSessionBean1().getActionsRowSet().execute();
                // Set to clear the tree nodes in Mainmenu
                getMainMenu().setResetNavTree(new Boolean(true));               
                userField.setText(null);
                passwordField.setPassword(null);
                 // Set view to home - TODO set default content parameter
                getSessionBean1().setselectPage("Logo");
            } else {
                // TODO Handle and inform user login was not correct
            }               
        } catch (Exception e) {
            error("Cannot find user " +
                    dialogix_usersDataProvider.getValue(
                    "dialogix_users.user_name"));
            log("Cannot find user " +
                    dialogix_usersDataProvider.getValue(
                    "dialogix_users.user_name"), e);
        }
        return null;
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected InstrumentLogicFile getInstrumentLogicFile() {
        return (InstrumentLogicFile) getBean("InstrumentLogicFile");
    }
}
