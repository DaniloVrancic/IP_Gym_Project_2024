<%@page import="net.etfbl.ip.gym_advisor.dto.User"%>
<%@page import="net.etfbl.ip.gym_advisor.beans.UserBean"%>
<%@page import="net.etfbl.ip.gym_advisor.controller.Controller"%>
<%@page import="net.etfbl.ip.gym_advisor.dao.ChatroomDAO"%>
<%@page import="net.etfbl.ip.gym_advisor.dto.Chatroom"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Messages</title>
</head>
<body style="background-color:#909090">
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
Welcome to myMessages

	<% HttpSession currentSession = Controller.session;
	UserBean userBean = (UserBean)currentSession.getAttribute("userBean");
	User currentUser = userBean.getCurrentUser();
	List<Chatroom> allReceievedMessages = ChatroomDAO.selectAllReceivedMessages(currentUser.getId()); //gets all the messages in this %>
	<div class="all-messages-container">
	<%for(Chatroom currentMessage : allReceievedMessages){ %>
		<div class="single-message-container">
		<div id="message-icon"></div>
		<div id="timeOfSend-container">[Time of sending:]<%=currentMessage.getTimeOfSend() %></div>
		<div id="from-container">(Sender ID): <%=currentMessage.getSenderId() %></div>
		<div id="message-content">[Message:] <%=currentMessage.getText() %></div>
		</div>
		<% } %>
	</div>

</body>
</html>