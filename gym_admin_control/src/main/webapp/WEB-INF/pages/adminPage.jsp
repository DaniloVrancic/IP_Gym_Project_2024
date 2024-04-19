<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="net.etfbl.ip.gym_admin.beans.UserBean"%>


<html>
	<head>
		<title>Admin Control Panel</title>
		<link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Jersey+25&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Play:wght@400;700&display=swap" rel="stylesheet">
        
        <link rel="stylesheet" href="css/stylesHeader.css">
        <link rel="stylesheet" href="css/stylesAdminControl.css">
        <link rel="stylesheet" href="css/form-style.css">
	</head>
	<body style="background-color:#909090">
	<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
	<%
    
    UserBean userBean = (UserBean) session.getAttribute("userBean");
    // If userBean is not null, get the current user's username
    String username = "";
    if (userBean != null) {
        username = userBean.getCurrentUser().getUsername();
    }
	%>

<h2 class="form-title" style="padding:1rem;">Logged in as: <%= username %></h2>
	</body>

</html>