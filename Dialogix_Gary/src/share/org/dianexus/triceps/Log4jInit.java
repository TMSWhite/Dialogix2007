package org.dianexus.triceps;

/* add to web.xml
 * 
 * 
   <servlet>
    <servlet-name>log4j-init</servlet-name>
    <servlet-class>com.foo.Log4jInit</servlet-class>

    <init-param>
      <param-name>log4j-init-file</param-name>
      <param-value>WEB-INF/classes/log4j.lcf</param-value>
    </init-param>

    <load-on-startup>1</load-on-startup>
  </servlet>
 * */
 
import org.apache.log4j.PropertyConfigurator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;

public class Log4jInit extends HttpServlet {

	  public
	  void init() {
	    String prefix =  getServletContext().getRealPath("/");
	    String file = getInitParameter("log4j-init-file");
	    // if the log4j-init-file is not set, then no point in trying
	    if(file != null) {
	      PropertyConfigurator.configure(prefix+file);
	    }
	  }

	  public
	  void doGet(HttpServletRequest req, HttpServletResponse res) {
	  }
	}
