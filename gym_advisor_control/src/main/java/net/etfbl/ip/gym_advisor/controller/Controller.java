package net.etfbl.ip.gym_advisor.controller;

	import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.etfbl.ip.gym_advisor.beans.UserBean;
import net.etfbl.ip.gym_advisor.dao.ChatroomDAO;
import net.etfbl.ip.gym_advisor.dao.UserDAO;
import net.etfbl.ip.gym_advisor.dto.Chatroom;
import net.etfbl.ip.gym_advisor.dto.User;
import net.etfbl.ip.gym_advisor.util.Util;

	@WebServlet({"/Controller", ""})
	public class Controller extends HttpServlet{

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		private static final String contextPath = "/gym_advisor_control";
		private static final String advisorPagePath = "/WEB-INF/pages/advisorPage.jsp";
		private static final String myMessagesPath = "/WEB-INF/pages/myMessages.jsp";
		private static final String sendmessagePath = "/WEB-INF/pages/sendMessage.jsp";
		private static final String notFoundPath = "/WEB-INF/pages/404NotFound.jsp";
		
		private static final String loginPagePath = "/loginForm.jsp";
		
		public static HttpSession session = null;

		public Controller() {
			super();
		}

		@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			request.setCharacterEncoding("UTF-8");
			String address = loginPagePath;
			String action = request.getParameter("action");
			
			if(session == null)
			{
				session = request.getSession(true);
			}

			session.setAttribute("login_notification", "");

			if (action == null || action.equals("")) {

			}
			else if("login".equals(action))
			{
				address = contextPath + advisorPagePath;

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
		            	request.getRequestDispatcher(advisorPagePath).forward(request, response);
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
			else if("mymessages".equals(action))
			{
				if(!session.getAttribute("userBean").equals(null))
				{
					request.getRequestDispatcher(myMessagesPath).forward(request, response);
					return;
				}
				else
				{
					request.getRequestDispatcher(notFoundPath).forward(request, response);
					return;
				}
			}
			else if("sendmessage".equals(action))
			{
				if(!session.getAttribute("userBean").equals(null))
				{
					request.getRequestDispatcher(sendmessagePath).forward(request, response);
					return;
				}
				else
				{
					request.getRequestDispatcher(notFoundPath).forward(request, response);
					return;
				}
			}
			else if("getMessageDetails".equals(action))
			{
			    int messageId = Integer.parseInt(request.getParameter("id"));
			    
			    
			    // Query database for message details
			    Chatroom message = ChatroomDAO.selectMessageById(messageId);
			    ChatroomDAO.markAsRead(message);

			    // Convert message object to JSON manually
			    StringBuilder jsonBuilder = new StringBuilder("{");
			    
		        jsonBuilder.append("\"id\":").append(message.getId()).append(",");
		        jsonBuilder.append("\"readMsg\":\"").append(message.getReadMsg()).append("\",");
		        jsonBuilder.append("\"text\":\"").append(message.getText().replace("\n", "\\n")).append("\",");
		        jsonBuilder.append("\"sender_id\":\"").append(message.getSenderId()).append("\",");
		        jsonBuilder.append("\"timeOfSend\":").append("\"").append(message.getTimeOfSend()).append("\"");
		        jsonBuilder.append("}");

		        
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
			
			request.getRequestDispatcher(address).forward(request, response);

		}

		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			doGet(request, response);
		}
	}
	