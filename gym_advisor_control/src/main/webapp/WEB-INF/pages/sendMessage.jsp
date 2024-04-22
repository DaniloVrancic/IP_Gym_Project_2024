<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Send Message</title>
</head>
<body style="background-color:#909090">
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
<div style="margin: 20px;">
    <h2>Send Message</h2>
    <form method="post" enctype="multipart/form-data">

            <label for="to">To:</label>
            <input type="text" id="to" name="to" required>

            <label for="message">Message:</label>
            <textarea id="message" name="message" rows="5" cols="50" required></textarea>

            <label for="attachment">Attachment:</label>
            <input type="file" id="attachment" name="attachment">

            <button type="submit" formaction="/gym_advisor_control/Controller?action=sendEmail">Send Email</button>

    </form>
</div>
</body>
</html>