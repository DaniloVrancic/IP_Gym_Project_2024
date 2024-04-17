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
	
	<form class="my-form" method="POST" action="/gym_admin_control/Controller?action=login">
		<%-- Check if there's a notification message in the session --%>
   	 <% String notification = (String) session.getAttribute("notification"); %>
    	<% if (notification != null && !notification.isEmpty()) { %>
        <div class="error-message"><%= notification %></div>
    	<% } %>
		<label for="username">Username:</label>
		<input type="text" name="username" id="username"/>
		<label for="password">Password:</label>
		<input type="password" name="password" id="password"/>
		<button type="submit" name="submit">Login</button>
	</form>
	</div>
	

	
</body>
</html>