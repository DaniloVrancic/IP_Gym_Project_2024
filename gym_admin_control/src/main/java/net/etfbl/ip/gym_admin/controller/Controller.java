	package net.etfbl.ip.gym_admin.controller;

	import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.etfbl.ip.gym_admin.beans.UserBean;
import net.etfbl.ip.gym_admin.dao.FitnessProgramTypeDAO;
import net.etfbl.ip.gym_admin.dao.SpecificProgramAttributeDAO;
import net.etfbl.ip.gym_admin.dao.UserDAO;
import net.etfbl.ip.gym_admin.dto.FitnessProgramType;
import net.etfbl.ip.gym_admin.dto.SpecificProgramAttribute;
import net.etfbl.ip.gym_admin.dto.User;
import net.etfbl.ip.gym_admin.util.Util;

	@WebServlet({"/Controller", ""})
	public class Controller extends HttpServlet{

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		private static final String contextPath = "/gym_admin_control";
		private static final String adminPagePath = "/WEB-INF/pages/adminPage.jsp";
		private static final String headerPath = "/WEB-INF/partials/header.jsp";
		private static final String categoriesPath = "/WEB-INF/pages/categoriesPage.jsp";
		private static final String usersPath = "/WEB-INF/pages/usersPage.jsp";
		private static final String statisticsPath = "/WEB-INF/pages/statisticsPage.jsp";
		private static final String notFoundPage = "/WEB-INF/pages/404NotFound.jsp";
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
			else if("logout".equals(action))
			{
				session.invalidate();
				address = (loginPagePath);
			}
			else if("categories".equals(action))
			{

				if(!session.getAttribute("userBean").equals(null))
				{
					request.getRequestDispatcher(categoriesPath).forward(request, response);
					return;
				}
				else
				{
					request.getRequestDispatcher(notFoundPage).forward(request, response);
					return;
				}
			}
			else if("users".equals(action))
			{
				if(!session.getAttribute("userBean").equals(null))
				{
					request.getRequestDispatcher(usersPath).forward(request, response);
					return;
				}
				else
				{
					request.getRequestDispatcher(notFoundPage).forward(request, response);
					return;
				}
			}
			else if("statistics".equals(action))
			{
				if(!session.getAttribute("userBean").equals(null))
				{
					request.getRequestDispatcher(statisticsPath).forward(request, response);
					return;
				}
				else
				{
					request.getRequestDispatcher(notFoundPage).forward(request, response);
					return;
				}
			}
			else if("userAdd".equals(action))
			{
				addUserToRepository(request, response);
				return;
			}
			else if("userUpdate".equals(action))
			{
				updateUserInRepository(request, response);
				return;
			}
			else if("userRemove".equals(action))
			{
				removeUserFromRepository(request, response);
				return;
			}
			else if ("specificAttributes".equals(action)) { //This is a special case when selecting Attributes in the Category tab
			    int programTypeId = Integer.parseInt(request.getParameter("programTypeId"));
			    
			    
			    // Call a method to retrieve specific attributes based on programTypeId
			    List<SpecificProgramAttribute> attributes = SpecificProgramAttributeDAO.selectByFitnessProgramTypeId(programTypeId);
			    
			    // Manually construct JSON string
			    StringBuilder jsonBuilder = new StringBuilder("[");
			    for (int i = 0; i < attributes.size(); i++) {
			        SpecificProgramAttribute attribute = attributes.get(i);
			        jsonBuilder.append("{");
			        jsonBuilder.append("\"id\":").append(attribute.getId()).append(",");
			        jsonBuilder.append("\"attributeName\":\"").append(attribute.getAttributeName()).append("\",");
			        jsonBuilder.append("\"attributeValue\":\"").append(attribute.getValue()).append("\",");
			        jsonBuilder.append("\"programType\":").append(attribute.getProgramType());
			        jsonBuilder.append("}");
			    // End of manually constructing JSON string
			        if (i < attributes.size() - 1) {
			            jsonBuilder.append(",");
			        }
			    }
			    jsonBuilder.append("]");
			    String json = jsonBuilder.toString();
			    
			    // Set response content type and character encoding
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    
			    // Write JSON string to response
			    try (PrintWriter out = response.getWriter()) {
			        out.write(json);
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    return;
			}
			else if("typeAdd".equals(action))
			{
				addTypeInRepository(request, response);
				return;
			}
			else if("typeUpdate".equals(action))
			{
				updateTypeInRepository(request, response);
				return;
			}
			else if("typeRemove".equals(action))
			{
				removeTypeFromRepository(request, response);
				return;
			}
			else if("attributeAdd".equals(action))
			{
				addAttributeInRepository(request, response);
				return;
			}
			else if("attributeUpdate".equals(action))
			{
				updateAttributeInRepository(request,response);
				return;
			}
			else if("attributeDelete".equals(action))
			{
				removeAttributeFromRepository(request, response);
				return;
			}
			request.getRequestDispatcher(address).forward(request, response);

		}

		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			doGet(request, response);
		}
		
		private void addUserToRepository(HttpServletRequest request, HttpServletResponse response)
		{
			
			String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String city = request.getParameter("city");
            String avatar = request.getParameter("avatar").length() > 0 ? request.getParameter("avatar") : null;
            String email = request.getParameter("email");
            Boolean activated = Boolean.parseBoolean(request.getParameter("activated"));
            
            User userToAdd = new User();
            userToAdd.setUsername(username);
            String hashedPassword = Util.hashString(password, "SHA-256");
            userToAdd.setPassword(hashedPassword);
            userToAdd.setFirstName(firstName);
            userToAdd.setLastName(lastName);
            userToAdd.setCity(city);
            userToAdd.setAvatar(avatar);
            userToAdd.setEmail(email);
            userToAdd.setActivated(activated);
            userToAdd.setType(3); // Wanna make it so that admins from the control panel
            					  //can only add User type users.
            
            
            
            try {
            	
            if(UserDAO.insert(userToAdd))
            {
            	System.out.println("Successfuly added:" + userToAdd.getUsername());
            	          	
            }
            }
            catch(Exception ex)
            {
            	System.out.println(ex.getLocalizedMessage());
            }
            
            
            
		}
		
		private void updateUserInRepository(HttpServletRequest request, HttpServletResponse response)
		{
			Integer userId = Integer.parseInt(request.getParameter("userId"));
			String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String city = request.getParameter("city");
            String avatar = request.getParameter("avatar").length() > 0 ? request.getParameter("avatar") : null;
            String email = request.getParameter("email");
            Boolean activated = Boolean.parseBoolean(request.getParameter("activated"));
            
            User userToUpdate = UserDAO.selectById(userId);
            if(username.length() > 0) {userToUpdate.setUsername(username);}
            if(password.length() > 0) {userToUpdate.setPassword(Util.hashString(password, "SHA-256"));};
            if(firstName.length() > 0) {userToUpdate.setFirstName(firstName);}
            if(lastName.length() > 0) {userToUpdate.setLastName(lastName);}
            if(city.length() > 0) {userToUpdate.setCity(city);}
            if(avatar != null && avatar.length() > 0) {userToUpdate.setAvatar(avatar);}
            else {userToUpdate.setAvatar(null);}
            if(email.length() > 0) {userToUpdate.setEmail(email);}
            userToUpdate.setActivated(activated);
            
            
            try {
            	
            if(UserDAO.update(userToUpdate))
            {
            	System.out.println("Successfuly updated:");
            	System.out.println(userToUpdate);            	
            }
            }
            catch(Exception ex)
            {
            	System.out.println(ex.getLocalizedMessage());
            }
		}
		
		private void removeUserFromRepository(HttpServletRequest request, HttpServletResponse response)
		{
			Integer userId = Integer.parseInt(request.getParameter("userId"));
			
			try {
				if(UserDAO.remove(userId))
				{
					System.out.println("Successfuly removed user with ID: " + userId);
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
		}
		
		private void addTypeInRepository(HttpServletRequest request, HttpServletResponse response)
		{
			String nameToAdd = request.getParameter("fitnessTypeName");
			
			FitnessProgramType newType = new FitnessProgramType();
			newType.setName(nameToAdd);
			try {
				if(FitnessProgramTypeDAO.insert(newType))
				{
					System.out.println("Successfuly add Type with name: " + nameToAdd);
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
		}
		
		private void updateTypeInRepository(HttpServletRequest request, HttpServletResponse response)
		{
			Integer typeId	 = Integer.parseInt(request.getParameter("fitnessTypeId"));
			String nameToAdd = request.getParameter("fitnessTypeName");
			
			FitnessProgramType newType = new FitnessProgramType();
			newType.setId(typeId);
			newType.setName(nameToAdd);
			try {
				if(FitnessProgramTypeDAO.update(newType))
				{
					System.out.println("Successfuly updated Type with Id: " + typeId);
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
		}
		
		private void removeTypeFromRepository(HttpServletRequest request, HttpServletResponse response)
		{
			Integer typeId	 = Integer.parseInt(request.getParameter("fitnessTypeId"));
			
			try {
				if(FitnessProgramTypeDAO.remove(typeId))
				{
					System.out.println("Successfuly removed Type with Id: " + typeId);
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
		}
		
		private void addAttributeInRepository(HttpServletRequest request, HttpServletResponse response)
		{
			Integer typeId = Integer.parseInt(request.getParameter("typeForAttribute"));
			String attributeName = request.getParameter("attributeName");
			String attributeValue = (request.getParameter("attributeValue").length() == 0) ? null : request.getParameter("attributeValue");
			
			SpecificProgramAttribute newAttribute = new SpecificProgramAttribute();
			newAttribute.setProgramType(typeId);
			newAttribute.setAttributeName(attributeName);
			newAttribute.setValue(attributeValue);
			try {
				if(SpecificProgramAttributeDAO.insert(newAttribute))
				{
					System.out.println("Successfuly Added new attribute. ");
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
		}
		
		private void updateAttributeInRepository(HttpServletRequest request, HttpServletResponse response)
		{
			Integer attributeId = Integer.parseInt(request.getParameter("selectedAttributeId"));
			Integer typeId = Integer.parseInt(request.getParameter("typeForAttribute"));
			String attributeName = request.getParameter("attributeName");
			String attributeValue = (request.getParameter("attributeValue").length() == 0) ? null : request.getParameter("attributeValue");
			
			SpecificProgramAttribute newAttribute = new SpecificProgramAttribute();
			newAttribute.setId(attributeId);
			newAttribute.setProgramType(typeId);
			newAttribute.setAttributeName(attributeName);
			newAttribute.setValue(attributeValue);
			try {
				if(SpecificProgramAttributeDAO.update(newAttribute))
				{
					System.out.println("Successfuly updated attribute. ");
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
		}
		
		private void removeAttributeFromRepository(HttpServletRequest request, HttpServletResponse response)
		{
			Integer attributeId = Integer.parseInt(request.getParameter("selectedAttributeId"));
			

			try {
				if(SpecificProgramAttributeDAO.remove(attributeId))
				{
					System.out.println("Successfuly removed attribute. ");
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
		}
	}


