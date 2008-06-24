/*
 * MainMenu.java
 *
 * Created on Apr 3, 2008, 11:18:16 AM
 * Copyright George
 */
package org.dialogix.main;

import com.sun.data.provider.impl.CachedRowSetDataProvider;
import com.sun.rave.faces.data.DefaultTableDataModel;
import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.Html;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import java.util.List;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.FacesException;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class MainMenu extends AbstractPageBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private int __placeholder;

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
        actionsDataProvider.setCachedRowSet((javax.sql.rowset.CachedRowSet) getValue("#{SessionBean1.actionsRowSet}"));
    }
    private CachedRowSetDataProvider actionsDataProvider = new CachedRowSetDataProvider();

    public CachedRowSetDataProvider getActionsDataProvider() {
        return actionsDataProvider;
    }

    public void setActionsDataProvider(CachedRowSetDataProvider crsdp) {
        this.actionsDataProvider = crsdp;
    }
    private Boolean resetNavTree = new Boolean(false);
    private Tree displayTree = new Tree();

    public Tree getDisplayTree() {
        return displayTree;
    }

    public void setDisplayTree(Tree t) {
        this.displayTree = t;
    }
    private Page page1 = new Page();

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
    private Link link2 = new Link();

    public Link getLink2() {
        return link2;
    }

    public void setLink2(Link l) {
        this.link2 = l;
    }
    private StaticText messageText = new StaticText();

    public StaticText getMessageText() {
        return messageText;
    }

    public void setMessageText(StaticText st) {
        this.messageText = st;
    }
    private String selectPage2;

    public String getSelectPage2() {
        return selectPage2;
    }

    public void setSelectPage2(String st) {
        this.selectPage2 = st;
    }
    private StaticText staticText1 = new StaticText();

    public StaticText getStaticText1() {
        return staticText1;
    }

    public void setStaticText1(StaticText st) {
        this.staticText1 = st;
    }
    private Hyperlink hyperlink1 = new Hyperlink();

    public Hyperlink getHyperlink1() {
        return hyperlink1;
    }

    public void setHyperlink1(Hyperlink h) {
        this.hyperlink1 = h;
    }
    private StaticText displayPageText = new StaticText();

    public StaticText getDisplayPageText() {
        return displayPageText;
    }

    public void setDisplayPageText(StaticText st) {
        this.displayPageText = st;
    }
    private DefaultTableDataModel dataTable1Model = new DefaultTableDataModel();

    public DefaultTableDataModel getDataTable1Model() {
        return dataTable1Model;
    }

    public void setDataTable1Model(DefaultTableDataModel dtdm) {
        this.dataTable1Model = dtdm;
    }

    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public MainMenu() {
    }

    /**
     * <p>Callback method that is called whenever a page is navigated to,
     * either directly via a URL, or indirectly via page navigation.
     * Customize this method to acquire resources that will be needed
     * for event handlers and lifecycle methods, whether or not this
     * page is performing post back processing.</p>
     * 
     * <p>Note that, if the current request is a postback, the property
     * values of the components do <strong>not</strong> represent any
     * values submitted with this request.  Instead, they represent the
     * property values that were saved for this view when it was rendered.</p>
     */
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
            log("Page1 Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
        }

    // </editor-fold>
    // Perform application initialization that must complete
    // *after* managed components are initialized
    // TODO - add your own initialization code here
    }

    /**
     * <p>Callback method that is called after the component tree has been
     * restored, but before any event processing takes place.  This method
     * will <strong>only</strong> be called on a postback request that
     * is processing a form submit.  Customize this method to allocate
     * resources that will be required in your event handlers.</p>
     */
    public void preprocess() {
    }

    /**
     * <p>Callback method that is called just before rendering takes place.
     * This method will <strong>only</strong> be called for the page that
     * will actually be rendered (and not, for example, on a page that
     * handled a postback and then navigated to a different page).  Customize
     * this method to allocate resources that will be required for rendering
     * this page.</p>
     */
    public void prerender() {
        // see if user is logged in
        if (getSessionBean1().getLoggedIn().booleanValue()) {
            messageText.setValue("Welcome, " + getSessionBean1().getUser());

        } else {
            messageText.setValue("Please login using the form below.");
        }
        String headerString = getSessionBean1().getSelectPage() + " Version: " + getSessionBean1().getVersion();
        displayPageText.setValue(headerString);

        Integer expandedRoleId = getRequestBean1().getRoleId();
        try {

            // Set up the variables we will need
            Integer currentRoleId = new Integer(-1);
            // If nbrChildren is not 0 then this is a
            // postback and we have our tree already
            int nbrChildren = displayTree.getChildCount();
            // Or if resetNavTree is true - set in Login menthod
            if ((nbrChildren == 0) || (resetNavTree)) {
                // List of outer (role) nodes
                List outerChildren = displayTree.getChildren();
                // Erase previous contents
                outerChildren.clear();
                // List of inner (trip) nodes
                List innerChildren = null;
                // Execute the SQL query
                actionsDataProvider.refresh();
                // Iterate over the rows of the result set.
                // Every time we encounter a new role, add first level node.
                // Add second level action nodes to the parent role node.
                boolean hasNext = actionsDataProvider.cursorFirst();
                while (hasNext) {
                    Integer newRoleId =
                            (Integer) actionsDataProvider.getValue(
                            "person_role.role_id");
                    if (!newRoleId.equals(currentRoleId)) {
                        currentRoleId = newRoleId;
                        TreeNode roleNode = new TreeNode();
                        roleNode.setStyle("font-size: small");
                        roleNode.setId("role" + newRoleId.toString());
                        roleNode.setText(
                                (String) actionsDataProvider.getValue(
                                "role.role"));
                        // If the request bean passed a role id,
                        // expand that role's node
                        roleNode.setExpanded(newRoleId.equals(expandedRoleId));
                        outerChildren.add(roleNode);
                        innerChildren = roleNode.getChildren();
                    }
                    // Create a new action node
                    // actionNode ID must unique
                    TreeNode actionNode = new TreeNode();
                    actionNode.setStyle("font-size: small");
                    actionNode.setId("menu" +
                            actionsDataProvider.getValue("role_menu.menu_id").toString());
                    actionNode.setText(
                            actionsDataProvider.getValue("menu.display_text").toString());

                    // Bind node to actonExpression
                    ExpressionFactory exFactory =
                            getApplication().getExpressionFactory();
                    ELContext elContext =
                            getFacesContext().getELContext();
                    actionNode.setActionExpression(
                            exFactory.createMethodExpression(
                            elContext, "#{MainMenu.actionNode_action}",
                            String.class, new Class<?>[0]));
                    // end Bind
                    innerChildren.add(actionNode);
                    hasNext = actionsDataProvider.cursorNext();
                }
            }
            // Reset Boolean toggle to false
            resetNavTree = false; 
        } catch (Exception ex) {
            log("Exception gathering tree data", ex);
            error("Exception gathering tree data: " + ex);
        }
    }

    /**
     * <p>Callback method that is called after rendering is completed for
     * this request, if <code>init()</code> was called (regardless of whether
     * or not this was the page that was actually rendered).  Customize this
     * method to release resources acquired in the <code>init()</code>,
     * <code>preprocess()</code>, or <code>prerender()</code> methods (or
     * acquired during execution of an event handler).</p>
     */
    public void destroy() {
        actionsDataProvider.close();

    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     */
    protected RequestBean1 getRequestBean1() {
        return (RequestBean1) getBean("RequestBean1");
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     */
    protected SessionBean1 getSessionBean1() {
        return (SessionBean1) getBean("SessionBean1");
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     */
    protected ApplicationBean1 getApplicationBean1() {
        return (ApplicationBean1) getBean("ApplicationBean1");
    }

    public Boolean getResetNavTree() {
        return resetNavTree;
    }

    public void setResetNavTree(Boolean resetNavTree) {
        this.resetNavTree = resetNavTree;
    }

    public String hyperlink1_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        getSessionBean1().setselectPage("MainContent1");
        return null;
    }

    public String actionNode_action() {
        // Get the id of the currently selected tree node
        String nodeId = displayTree.getSelected();

        // Find the tree node component with the given id
        TreeNode selectedNode =
                (TreeNode) this.getForm1().findComponentById(nodeId);
        try {
            // Node's id property is composed of "action" plus the action id
            getRequestBean1().setActionName(nodeId);
            getSessionBean1().setselectPage(selectedNode.getText());

        } catch (Exception e) {
            error("Can't convert node id to Integer!");
            return null;
        }
        return null;
    }
}

