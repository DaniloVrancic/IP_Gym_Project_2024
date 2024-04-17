package net.etfbl.ip.gym_admin.beans;

import java.io.Serializable;

import net.etfbl.ip.gym_admin.dao.UserDAO;
import net.etfbl.ip.gym_admin.dto.User;
import net.etfbl.ip.gym_admin.util.Util;

public class UserBean implements Serializable {
	
	
	private static final long serialVersionUID = -7996958614906818103L;
	
	private User _currentUser = new User();
	private boolean isLoggedIn = false;
	
	public boolean login(String username, String password)
	{
		
		
		String hashedPassword = (password.length() > 0) ? Util.hashString(password, "SHA-256") : "";
		User selectedUser = UserDAO.selectByUsername(username);
		
		if(selectedUser != null)
		{
			if(selectedUser.getPassword().equals(hashedPassword))
			{
				this._currentUser = selectedUser;
				this.isLoggedIn = true;
				System.out.println("Successful login.");
			}
			else
			{
				System.out.println("Incorrect password.");
				return false;
			}
		}
		else {
			System.out.println("No such user found");
			return false;
		}
		
		return true;
	}
	
	public void logout()
	{
		this._currentUser = new User();
		this.isLoggedIn = false;
	}
	

}
