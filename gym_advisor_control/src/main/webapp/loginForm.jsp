<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="net.etfbl.ip.gym_advisor.beans.UserBean"%>

<!DOCTYPE html>
<html>
<head>
   	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jersey+25&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Play:wght@400;700&display=swap" rel="stylesheet">
    
    <link rel="stylesheet" href="css/stylesHeader.css">
    <link rel="stylesheet" href="css/stylesAdminControl.css">
    <link rel="stylesheet" href="css/form-style.css">
	<title>Advisor Login</title>
</head>
<body style="background-color:#909090">
	<div class="main-content-div">
		<div class="centered-form">
		
		<form class="my-form" method="POST" action="/gym_advisor_control/Controller?action=login">
			<h1 class="form-title">Advisor Control Page</h1>
			<%-- Check if there's a notification message in the session --%>
	   	 <% String notification = (String) session.getAttribute("login_notification"); %>
	    	<% if (notification != null && !notification.isEmpty()) { %>
	        <div class="error-message form-title" style="background-color:#909090; border-radius: 0.4rem; padding:0.1rem; color: red;"><%= notification %></div>
	    	<% } %>
			<label for="username" class="input-field-descriptor">Username:</label>
			<input type="text" name="username" id="username" class="input-field" placeholder=" "/>
			<label for="password" class="input-field-descriptor">Password:</label>
			<input type="password" name="password" id="password" class="input-field" placeholder=" "/>
			<br><br>
			<div class="buttons-area">
				<button type="submit" name="submit">Login</button>
			</div>
		</form>
		</div>
	</div>
	

	
</body>
</html>