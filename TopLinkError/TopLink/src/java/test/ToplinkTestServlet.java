/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.*;
import java.net.*;

import java.util.Enumeration;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Coevtmw
 */
@EJB(name="InstrumentFacade_ejbref", beanInterface=InstrumentFacadeLocal.class)
public class ToplinkTestServlet extends HttpServlet {
    private static int ToplinkTestCount = 0;
    private InstrumentTest instrumentTest = null;
    private int varNameCount = 0;
    private int questionCount = 0;
    private boolean initialized = false;
    
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        initialized = initSession(request, response);
        
        if (!initialized) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ToplinkTestServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("Unable to create InstrumentTest");
            out.println("</body></html>");
        }
        
        instrumentTest.beginServerProcessing();
        
        // Write answers for collected questions, if any
        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String name = (String) params.nextElement();
            String value = request.getParameter(name);
            instrumentTest.writeAnswer(name, value);
        }

        // Create form to collect answers to questions
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ToplinkTestServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ToplinkTestServlet at " + request.getContextPath () + "</h1>");
            
            out.println("<FORM method='POST' name='toplinkTestForm' id='toplinkTestForm' action='");
            out.println(response.encodeURL(request.getRequestURL().toString()));
            out.println("'>");
            out.println("<table border='1'><tr><td>VarName</td><td>Question</td><td>Answer</td></tr>");
            for (int i=0;i<5;++i) {
                out.println("<tr><td>VarName" + ++varNameCount + "</td><td>Question #" + ++questionCount + "</td><td>");
                out.println("<input type='text' name='varName" + varNameCount + "' id='varName" + varNameCount + "'></input></td></tr>");
                instrumentTest.writeQuestion("varName" + varNameCount, "Question #" + questionCount);
            }
            out.println("<tr><td><input type='submit' name='submit' value='submit' id='submit'></td></tr>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally { 
            out.close();
            instrumentTest.finishServerProcessing();
        }
    } 
    
    boolean initSession(HttpServletRequest req, HttpServletResponse res) {
        try {
            HttpSession session = req.getSession(true);

            if (session == null || session.isNew()) {
                if ("POST".equals(req.getMethod())) {
                    /* an expired session */
                    return false;
                }
            }
            instrumentTest = (InstrumentTest) session.getAttribute("InstrumentTest");
            if (instrumentTest == null) {
                instrumentTest = new InstrumentTest("Toplink Test #" + ++ToplinkTestCount);                
                session.setAttribute("InstrumentTest", instrumentTest);
            }
            return (instrumentTest != null);
        } catch (Exception e) {
            return false;
        }
    }    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
