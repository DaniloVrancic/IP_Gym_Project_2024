package net.etfbl.ip.gym_advisor.controller;

	import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.util.mime.MimeUtility;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.activation.*;



import net.etfbl.ip.gym_advisor.beans.UserBean;
import net.etfbl.ip.gym_advisor.dao.ChatroomDAO;
import net.etfbl.ip.gym_advisor.dao.UserDAO;
import net.etfbl.ip.gym_advisor.dto.Chatroom;
import net.etfbl.ip.gym_advisor.dto.User;
import net.etfbl.ip.gym_advisor.util.Util;

	@WebServlet({"/Controller", ""})
	@MultipartConfig(
		    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		    maxFileSize = 1024 * 1024 * 10,      // 10MB
		    maxRequestSize = 1024 * 1024 * 50    // 50MB
		)
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
			else if("sendEmail".equals(action))
			{
				 	String to = request.getParameter("to");
			        String message = request.getParameter("message");
			        Part filePart = null;
			        InputStream fileContent = null;
			        if(request.getPart("attachment") != null) {
			            filePart = request.getPart("attachment");
			            fileContent = filePart.getInputStream();			        	
			        }
			        

			     // Send email with (optional) attachment
			        sendEmail(to, "Answer to your question", message, fileContent, "advisor@fitcheck.com");
			        return;
			}
			
			
			request.getRequestDispatcher(address).forward(request, response);

		}

		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			doGet(request, response);
		}
		
		 private void sendEmail(String to, String subject, String message, InputStream attachment, String from) throws IOException {
		
			 /*
			 MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
			 mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
			 mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
			 mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
			 mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
			 mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822"); 
			 */
			 
		        String host = "localhost"; // MailDev server host
		        int port = 1025; // MailDev server port
		        

		        Properties props = new Properties();
		        props.put("mail.smtp.host", host);
		        props.put("mail.smtp.port", port);

		        Session session = Session.getInstance(props);

		        MimeMessage emailMessage = new MimeMessage(session);
		        try {
		            emailMessage.setFrom(new InternetAddress(from));
		            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		            emailMessage.setSubject(subject);
		            
		            
		            // Create multipart message
		            Multipart multipart = new MimeMultipart();
		            
		            //Text message Body part
		            MimeBodyPart messageBodyPart = new MimeBodyPart();
		            messageBodyPart.setText(message);
		            multipart.addBodyPart(messageBodyPart);
		            
		            

		            // Add attachment
		            if (attachment != null) {
		                MimeBodyPart attachmentPart = new MimeBodyPart();
		                ByteArrayDataSource source = new ByteArrayDataSource(attachment, "application/octet-stream");
		                DataHandler handler = new DataHandler(source);
		                attachmentPart.setDataHandler(handler);
		                attachmentPart.setHeader("Content-ID", "<Logo>");
		                multipart.addBodyPart(attachmentPart);
		            }

		            emailMessage.setContent(multipart);
		            Transport.send(emailMessage);
		            // Send message
		            System.out.println("Email sent successfully.");
		        } catch (MessagingException e) {
		            e.printStackTrace();
		        }
		    }
	}
	