package org.dialogix.login;

import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class UserBean {
   private String name;
   private String role;
   private Logger logger = Logger.getLogger("org.dialogix.login");
 
   public String getName() { 
      if (name == null) getUserData(); 
      return name == null ? "" : name; 
   }

   public String getRole() { return role == null ? "" : role; }
   public void setRole(String newValue) { role = newValue; }

   public boolean isInRole() { 
      ExternalContext context 
         = FacesContext.getCurrentInstance().getExternalContext();
      Object requestObject =  context.getRequest();
      if (!(requestObject instanceof HttpServletRequest)) {
         logger.severe("request object has type " + requestObject.getClass());
         return false;
      }
      HttpServletRequest request = (HttpServletRequest) requestObject;
      return request.isUserInRole(role);
   }

   private void getUserData() {
      ExternalContext context 
         = FacesContext.getCurrentInstance().getExternalContext();
      Object requestObject =  context.getRequest();
      if (!(requestObject instanceof HttpServletRequest)) {
         logger.severe("request object has type " + requestObject.getClass());
         return;
      }
      HttpServletRequest request = (HttpServletRequest) requestObject;
      name = request.getRemoteUser();
   }
}
