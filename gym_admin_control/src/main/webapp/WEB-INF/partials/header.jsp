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
</head>
<body>

	<header class="jersey-25-regular">
	<h1 >Admin Control Panel</h1>
    <ul class="list" style="display:flex; flex-direction: row; flex-wrap=wrap">
      <a href="/gym_admin_control/Controller?action=categories"><li class="list-item">Categories</li></a>
      <a href="/gym_admin_control/Controller?action=users"><li class="list-item">Users</li></a>
      <a href="/gym_admin_control/Controller?action=statistics"><li class="list-item">Statistics</li></a>
      <a href="/gym_admin_control/Controller?action=logout"><li class="list-item">Logout</li></a>
    </ul>
  </header>
	
</body>
</html>