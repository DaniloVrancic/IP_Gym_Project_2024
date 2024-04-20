package net.etfbl.ip.gym_advisor.beans;

import java.io.Serializable;

import net.etfbl.ip.gym_advisor.*;
import net.etfbl.ip.gym_advisor.dao.UserDAO;
import net.etfbl.ip.gym_advisor.dto.User;
import net.etfbl.ip.gym_advisor.util.Util;

public class UserBean implements Serializable {


	private static final long serialVersionUID = -7996958614906818103L;

	private User _currentUser = new User();
	private boolean isLoggedIn = false;

	public boolean login(String username, String password) throws RuntimeException
	{


		String hashedPassword = (password.length() > 0) ? Util.hashString(password, "SHA-256") : "";
		User selectedUser = UserDAO.selectAdvisorByUsername(username);

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
				throw new RuntimeException("Incorrect password for given user.");
			}
		}
		else {
			throw new RuntimeException("No such user found.");
		}

		return true;
	}

	public User getCurrentUser()
	{
		return _currentUser;
	}

	public void logout()
	{
		this._currentUser = new User();
		this.isLoggedIn = false;
	}

	@Override
	public String toString()
	{
		return this._currentUser.getUsername();
	}


}
