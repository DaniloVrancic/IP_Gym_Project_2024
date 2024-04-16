package net.etfbl.ip.gym_admin.beans;

import java.io.Serializable;

import net.etfbl.ip.gym_admin.dao.UserDAO;
import net.etfbl.ip.gym_admin.dto.User;
import net.etfbl.ip.gym_admin.util.Util;

public class UserBean implements Serializable {
	
	private User _currentUser = new User();
	private boolean isLoggedIn = false;
	
	public boolean login(String username, String password)
	{
		
		
		String hashedPassword = (password.length() > 0) ? Util.hashString(password, "SHA-256") : "";
		System.out.println(hashedPassword);
		if()
		{
			System.out.println(_currentUser);
		}
		else {
			System.out.println("User not found.");
		}
		
		return true;
	}
	

}
