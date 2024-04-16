<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="net.etfbl.ip.gym_admin.beans.UserBean"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/stylesLoginForm.css">
	<title>Admin Login</title>
</head>
<body>
	
	<div class="form-container">
	<h3>Admin Control Page</h3>
	
	<form class="my-form" method="POST" action="?action=login">
		<label for="username">Username:</label>
		<input type="text" name="username" id="username"/>
		<label for="password">Password:</label>
		<input type="password" name="password" id="password"/>
		<button type="submit">Login</button>
	</form>
	</div>
	
	<%-- Accessing UserBean and calling login method with provided parameters --%>
    <%
        // Get UserBean instance
        UserBean userBean = new UserBean();
        
        // Check if form submitted
        if (request.getMethod().equals("POST")) {
            // Retrieve username and password from form
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // Call login method of UserBean with provided parameters
            boolean loggedIn = userBean.login(username, password);
            
            // Perform action based on login result
            if (loggedIn) {
                // Redirect to another page upon successful login
                response.sendRedirect("adminPage.jsp");
            } else {
                // Show error message or handle unsuccessful login
                // For simplicity, just print an error message
                out.println("<p>Login failed. Please try again.</p>");
            }
        }
    %>
	
</body>
</html>