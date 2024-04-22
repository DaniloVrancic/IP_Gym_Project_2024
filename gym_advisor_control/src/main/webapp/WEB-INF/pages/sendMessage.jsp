<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Send Message</title>
<link rel="stylesheet" href="css/stylesSendMessage.css">
<link rel="stylesheet" href="css/form-style.css">
</head>
<body style="background-color:#909090">
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
<div style="margin: 20px;">
    
    <div class="form-container">
    <form method="post" enctype="multipart/form-data">
	<h2 class="form-title" style="color:var(--input-main-text-color); text-align:center; font-size: 3.5rem; margin-bottom: 1rem;">Send Message</h2>
			
			<div class="flex-inputs-row">
            <label class="form-label input-field-descriptor" for="to">To:</label>
            <input class="form-input input-field" placeholder=" " type="text" id="to" name="to" required>
            </div>
			<br>
			<div class="flex-inputs-row">
            <label class="form-label input-field-descriptor" for="message">Message:</label>
            <textarea class="form-input input-field" id="message" placeholder=" " name="message" rows="5" cols="50" style="max-height: 30rem" required></textarea>
            </div>
			<br>
			<div class="flex-inputs-row">
            <label style="margin-left: 0.5rem;" class="form-label play-jersey-font" for="attachment">Attachment:</label>
            <input type="file" id="attachment" name="attachment">
            </div>
			<br>
			<div class="buttons-area">
            <button style="width:15%" type="submit" formaction="/gym_advisor_control/Controller?action=sendEmail">Send Email</button>
			</div>
    </form>
    </div>
</div>
</body>
</html>