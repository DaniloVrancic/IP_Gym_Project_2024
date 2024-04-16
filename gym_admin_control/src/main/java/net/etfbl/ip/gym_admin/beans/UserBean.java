package net.etfbl.ip.gym_admin.beans;

import java.io.Serializable;

import net.etfbl.ip.gym_admin.dao.UserDAO;
import net.etfbl.ip.gym_admin.dto.User;

public class UserBean implements Serializable {
	
	private User _currentUser = new User();
	private boolean isLoggedIn = false;
	
	public boolean login(String username, String password)
	{
		_currentUser = UserDAO.selectById(5);
		
		if(_currentUser != null)
		{
			System.out.println(_currentUser);
		}
		else {
			System.out.println("User not found.");
		}
		
		return true;
	}
	

}
