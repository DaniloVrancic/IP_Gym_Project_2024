	package net.etfbl.ip.gym_admin.controller;
	
	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.HttpServlet;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import jakarta.servlet.http.HttpSession;
	import net.etfbl.ip.gym_admin.beans.UserBean;
	
	import java.io.IOException;
	
	@WebServlet({"/Controller", ""})
	public class Controller extends HttpServlet{
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private static final String contextPath = "/gym_admin_control";
		private static final String adminPagePath = "/WEB-INF/pages/adminPage.jsp";
		private static final String headerPath = "/WEB-INF/partials/header.jsp";
		private static final String loginPagePath = "/loginForm.jsp";
		
		public Controller() {
			super();
		}
		
		@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			request.setCharacterEncoding("UTF-8");
			String address = loginPagePath;
			String action = request.getParameter("action");
			HttpSession session = null;
			if(session == null)
			{
				session = request.getSession(true);
			}
			
			session.setAttribute("login_notification", "");
			
			if (action == null || action.equals("")) {
				
			}
			else if("login".equals(action))
			{
				address = contextPath + adminPagePath;
				
				UserBean userBean = new UserBean();
		        
		            // Retrieve username and password from form
		            String username = request.getParameter("username");
		            String password = request.getParameter("password");
		            
		            // Call login method of UserBean with provided parameters
		            boolean loggedIn = false;
		            try
		            {	
		            	loggedIn = userBean.login(username, password);
		            }
		            catch(Exception ex)
		            {
		            	session.setAttribute("login_notification", ex.getLocalizedMessage());
		            }
		            
		            // Perform action based on login result
		            if (loggedIn) {
		            	session = request.getSession(true);
		            	session.setAttribute("userBean", userBean);
		            	session.setAttribute("login_notification", "");
		                // Redirect to another page upon successful login
		            	request.getRequestDispatcher(adminPagePath).forward(request, response);
		            	return;
		            } else {
		                // Show error message or handle unsuccessful login
		                response.sendRedirect(contextPath + loginPagePath);
		            	
		            	return;
		            }
		        
			}
			request.getRequestDispatcher(address).forward(request, response);

		}
		
		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			doGet(request, response);
		}
	}
	
	
