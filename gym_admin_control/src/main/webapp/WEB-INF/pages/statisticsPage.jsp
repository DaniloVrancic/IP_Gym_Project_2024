<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        
        <title>Statistics display</title>
	</head>
	<body style="background-color:#909090">
	<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
		 <div>
            <form method="POST">
                <h1 class="form-title">Statistics (Backend Logs)</h1>
                <h5 class="form-title">No log messages found on the backend.</h5>
            </form>
        </div>
	</body>
</html>