<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="net.etfbl.ip.gym_admin.beans.UserBean"%>


<html>
	<head>
		<title>Admin Control Panel</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="css/stylesAdminControl.css">
	</head>
	<body>
	<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
	<%
    
    UserBean userBean = (UserBean) session.getAttribute("userBean");
    // If userBean is not null, get the current user's username
    String username = "";
    if (userBean != null) {
        username = userBean.getCurrentUser().getUsername();
    }
	%>

<h2 class="logged-in-message" style="padding:1rem;">Logged in as: <%= username %></h2>
	</body>

</html>