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
<link rel="stylesheet" href="css/stylesMyMessages.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<script>
function showMessageDetails(messageId) {
let messagePrint = document.getElementById("message-print");
	
	messagePrint.style.display = "block";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "Controller", true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Parse the response and update message details section
            var messageDetails = JSON.parse(xhr.responseText);
            document.getElementById("from-print").innerText = "From (USER_ID): " + messageDetails.sender_id;
            document.getElementById("time-print").innerText = "Time: " + messageDetails.timeOfSend;
            document.getElementById("text-content").innerText = "Message:\n" + messageDetails.text;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send('action=getMessageDetails&id=' + messageId);
}

function hideMessageDetails()
{
	let messagePrint = document.getElementById("message-print");
	
	messagePrint.style.display = "none";
}
</script>
<body style="background-color:#909090">
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>

	<h1 style="text-align:center; padding-bottom: 1rem; color: ghostwhite;">Welcome to My Messages</h1>

	<% HttpSession currentSession = Controller.session;
	UserBean userBean = (UserBean)currentSession.getAttribute("userBean");
	User currentUser = userBean.getCurrentUser();
	List<Chatroom> allReceievedMessages = ChatroomDAO.selectAllReceivedMessages(currentUser.getId()); //gets all the messages in this %>
	
	<section id="message-print">
		<div id="exit-button" onclick="hideMessageDetails()">X</div>
		<div id="background-content">
		<div id="from-print"></div>
		<div id="time-print"></div>
		<div id="text-content"></div>
		</div>
	</section>
	<div class="all-messages-container">
	<%for(Chatroom currentMessage : allReceievedMessages){ %>
		<div class="single-message-container" style="display:flex;flex-direction:row;flex-wrap:nowrap" onclick="showMessageDetails(<%=currentMessage.getId()%>);">
		<div id="message-icon">
		<% if(currentMessage.getReadMsg())
			{
			%>
			<span class="material-symbols-outlined cell-spacing">
			drafts
			</span>
			<% 
			}
			else{%>
				<span class="material-symbols-outlined cell-spacing">
				mail
				</span>
				<% 
			}%>
		</div>
		<div class="timeOfSend-container cell-spacing">[Time of sending:]<br><%=currentMessage.getTimeOfSend()%></div>
		<div class="from-container cell-spacing">(Sender ID):<br><%=currentMessage.getSenderId()%></div>
		<div data-id="<%=currentMessage.getId()%>" class="message-content cell-spacing">[Message:]<br><%=currentMessage.getText()%></div>
		</div>
		<% } %>
	</div>

</body>
</html>