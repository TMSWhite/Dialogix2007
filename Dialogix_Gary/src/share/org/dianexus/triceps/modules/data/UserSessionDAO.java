/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** UserSessionDAO.java ,v 3.0.0 Created on March 20, 2006, 1:14 PM
 ** @author Gary Lyons
 ******************************************************** 
 */
package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;

/**
 * @author ISTCGXL
 *
 */
public interface UserSessionDAO {
	public boolean setUserSession();
	public boolean getUserSession(int pageHitId);
	public boolean updateUserSession();
	public boolean deleteUserSession (int id);
	public void setUserSessionId(int userSessionId);
	public int getUserSessionId();
	public void setInstrumentSessionId(int instrumentSessionId);
	public int getInstrumentSessionId();
	public void setUserId(int userId);
	public int getUserId();
	public void setTimestamp(Timestamp timestamp);
	public Timestamp getTimestamp();
	public void setComments(String comments);
	public String getComments();
	public void setStatus(String status);
	public String getStatus();
}
