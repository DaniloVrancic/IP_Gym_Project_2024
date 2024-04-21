<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/stylesHeader.css">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Jersey+25&display=swap" rel="stylesheet">
  	<link href="https://fonts.googleapis.com/css2?family=Play:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>

	<header class="jersey-25-regular">
	<h1 >Advisor Control Panel</h1>
    <ul class="list" style="display:flex; flex-direction: row; flex-wrap: wrap;">
      <a href="/gym_advisor_control/Controller?action=mymessages"><li class="list-item">My Messages</li></a>
      <a href="/gym_advisor_control/Controller?action=sendmessage"><li class="list-item">Send Messages</li></a>
      <a href="/gym_advisor_control/Controller?action=logout"><li class="list-item">Logout</li></a>
    </ul>
  </header>

</body>

</html>