package net.etfbl.ip.gym_admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.etfbl.ip.gym_admin.beans.UserBean;

import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Controller() {
		super();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String address = "/WEB-INF/pages/login.jsp";
		String action = request.getParameter("action");
		HttpSession session = null;
		if(session == null)
		{
			session = request.getSession(true);
		}
		
		session.setAttribute("notification", "");
		
		if (action == null || action.equals("")) {
			address = "/WEB-INF/pages/login.jsp";
		}
		else if("login".equals(action))
		{
			System.out.println("Login action!!!");
			address = "/WEB-INF/pages/adminPage.jsp";
			
			UserBean userBean = new UserBean();
	        
	            // Retrieve username and password from form
	            String username = request.getParameter("username");
	            String password = request.getParameter("password");
	            
	            // Call login method of UserBean with provided parameters
	            boolean loggedIn = userBean.login(username, password);
	            
	            // Perform action based on login result
	            if (loggedIn) {
	            	session = request.getSession(true);
	            	session.setAttribute("userBean", userBean);
	            	
	                // Redirect to another page upon successful login
	                response.sendRedirect(address);
	            } else {
	                // Show error message or handle unsuccessful login
	                // For simplicity, just print an error message
	                session.setAttribute("notification", "Wrong login parameters");
	            }
	        
		}
		
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST REQUEST!!!!!!!!!!!!");
		
		doGet(request, response);
	}
}


