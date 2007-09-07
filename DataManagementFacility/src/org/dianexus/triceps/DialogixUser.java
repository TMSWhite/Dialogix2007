package org.dianexus.triceps;

import java.util.ArrayList;
import java.util.Iterator;
import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.UserDAO;
import org.dianexus.triceps.modules.data.UserPermissionDAO;

public class DialogixUser {

	private ArrayList roles ;
	private UserDAO userDAO ;
	private int userId;
	private int DBID = 1;

	public boolean loginUser(String userName, String password){
            System.out.println("in DialoigixUser.loginUser");
		boolean rtn = false;
		DialogixDAOFactory dataFactory = DialogixDAOFactory.getDAOFactory(DBID);
		this.userDAO = dataFactory.getUserDAO();
		if(this.userDAO.getUser(userName, password)){
			this.userId=userDAO.getId();
			rtn = true;
                        System.out.println("user found: id is :"+this.userId);
		}   
		return rtn;	
	}

	public ArrayList getPermissions(){
            System.out.println("in DialoigixUser.getPermissions");
		ArrayList roleList;
		DialogixDAOFactory dataFactory = DialogixDAOFactory.getDAOFactory(DBID);
		UserPermissionDAO userPermissionDAO = dataFactory.getUserPermissionDAO();
		roleList = userPermissionDAO.getUserPermissions(userId);
		this.roles = roleList;
		return roleList;
	}

	public boolean hasPermission(String permission){
            System.out.println("in DialoigixUser.hasPermission: checking for "+permission);
		if(this.roles != null){
			Iterator it = this.roles.iterator();
			while(it.hasNext()){
				UserPermission up = (UserPermission)it.next();
                                System.out.println("comparing to "+up.getRole());
				if(up.getRole().equals(permission)){
                                    System.out.println("permission found");
					return true;
				}
			}
		}
		return false;
	}

	public int getUserId(){
		return userDAO.getId();
	}
}