<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="net.etfbl.ip.gym_admin.beans.UserBean"%>
<%@page import="net.etfbl.ip.gym_admin.dao.*" %>
<%@page import="net.etfbl.ip.gym_admin.dto.User" %>
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
    <link rel="stylesheet" href="css/stylesUsersPage.css">
    <link rel="stylesheet" href="css/form-style.css">
    
    
    <title>User Management</title>
    
    <script>
        function validateInputs() {
            var username = document.getElementById("username").value.trim();
            var password = document.getElementById("password").value.trim();
            var firstName = document.getElementById("firstName").value.trim();
            var lastName = document.getElementById("lastName").value.trim();
            var city = document.getElementById("city").value.trim();
            var email = document.getElementById("email").value.trim();
            
            // Enable add user button only if all required fields are filled
            if (username !== '' && password !== '' && firstName !== '' && lastName !== '' && city !== '' && email !== '') {
                document.getElementById("addUserButton").disabled = false;
            } else {
                document.getElementById("addUserButton").disabled = true;
            }
        }
        
        function clearInputs() {
        	document.getElementById("userSelect").value = "";
            document.getElementById("userId").value = "";
            document.getElementById("username").value = "";
            document.getElementById("password").value = "";
            document.getElementById("firstName").value = "";
            document.getElementById("lastName").value = "";
            document.getElementById("city").value = "";
            document.getElementById("avatar").value = "";
            document.getElementById("email").value = "";
            document.getElementById("addUserButton").disabled = true;
            document.getElementById("updateButton").disabled = true;
            document.getElementById("activatedNo").checked="checked";
            document.getElementById("typeUser").checked="checked"
        }
        
        function enableUpdateButton() {
            var userId = document.getElementById("userId").value.trim();
            if (userId.length > 0 && userId != null) {
                document.getElementById("updateButton").disabled = false;
                document.getElementById("deleteButton").disabled = false;
            } else {
                document.getElementById("updateButton").disabled = true;
                document.getElementById("deleteButton").disabled = true;
            }
        }
        
        function selectUser() {
            var select = document.getElementById("userSelect");
            var selectedOption = select.options[select.selectedIndex];

            // Get attributes from the selected option
            var id = selectedOption.getAttribute("data-id");
            var username = selectedOption.getAttribute("data-username");
            var city = selectedOption.getAttribute("data-city");
            var firstName = selectedOption.getAttribute("data-firstname");
            var lastName = selectedOption.getAttribute("data-lastname");
            var avatar = selectedOption.getAttribute("data-avatar");
            var email = selectedOption.getAttribute("data-email");
            var activated = selectedOption.getAttribute("data-activated");
            var selectedType	 = selectedOption.getAttribute("data-type");
            

            // Assign values to corresponding text-boxes
            document.getElementById("userId").value = id;
            document.getElementById("username").value = username;
            document.getElementById("city").value = city;
            document.getElementById("firstName").value = firstName;
            document.getElementById("lastName").value = lastName;
            document.getElementById("avatar").value = avatar;
            document.getElementById("email").value = email;
            
            if (activated == "false") {
                document.getElementById("activatedNo").checked = true;
            } else {
                document.getElementById("activatedYes").checked = true;
            }

            if (selectedType == "2") {
                document.getElementById("typeAdvisor").checked = true;
            } else {
                document.getElementById("typeUser").checked = true;
            }
            };
            
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
<% User selectedUser = new User(); %>
<div class="main-content-div">
    <form method="POST" class="centered-form">
    <h1 class="form-title">User Management</h1>
    	<label for="userSelect" class="input-field-descriptor">Select User:</label>
        <select id="userSelect" name="userSelect" class="input-field select-input" onchange="selectUser(); enableUpdateButton()">
        	<option data-id="" data-username="" data-city="" data-firstName="" data-lastName="" data-avatar="" data-email="">(none)</option>
            <% 
            for(User regularUser : UserDAO.selectAllUsersAndAdvisors())
            {
            %> <option 
            data-id="<%=regularUser.getId()%>" 
            data-username="<%=regularUser.getUsername()%>" 
            data-city="<%=regularUser.getCity()%>" 
            data-firstName="<%=regularUser.getFirstName()%>" 
            data-lastName="<%=regularUser.getLastName()%>" 
            data-avatar="<%=regularUser.getAvatar()%>" 
            data-email="<%=regularUser.getEmail()%>" 
            data-activated="<%=regularUser.getActivated()%>" 
            data-type="<%=regularUser.getType()%>">
            <%= regularUser.getUsername()%></option> <%
            }
            %>
        </select><br>
    	<label for="userId" class="input-field-descriptor">Selected User ID:</label>
        <input type="text" readonly="readonly" id="userId" name="userId" class="input-field" placeholder=" "><br>
        
        <label for="username" class="input-field-descriptor">Username:</label>
        <input type="text" id="username" name="username" class="input-field" placeholder=" " oninput="validateInputs(); enableUpdateButton();"><br>
        
        <label for="password" class="input-field-descriptor">Password:</label>
        <input type="password" id="password" name="password" class="input-field" placeholder=" " oninput="validateInputs();"><br>
        
        <label for="firstName" class="input-field-descriptor">First Name:</label>
        <input type="text" id="firstName" name="firstName" class="input-field" placeholder=" " oninput="validateInputs();"><br>
        
        <label for="lastName" class="input-field-descriptor">Last Name:</label>
        <input type="text" id="lastName" name="lastName" class="input-field" placeholder=" " oninput="validateInputs();"><br>
        
        <label for="city" class="input-field-descriptor">City:</label>
        <input type="text" id="city" name="city" class="input-field" placeholder=" " oninput="validateInputs();"><br>
        
        <label for="avatar" class="input-field-descriptor">Avatar:</label>
        <input type="text" id="avatar" name="avatar" class="input-field" placeholder=" "><br>
        
        <label for="email" class="input-field-descriptor">Email:</label>
        <input type="email" id="email" name="email" class="input-field" placeholder=" " oninput="validateInputs();"><br>
        
        
    <label for="activated" class="input-field-descriptor">Activated:</label>
    <div class="input-field">
	    <input type="radio" id="activatedYes" name="activated" value="true" class="radio-input">
	    <label for="activatedYes">Yes</label>
	    <input checked="checked" type="radio" id="activatedNo" name="activated" value="false" class="radio-input">
	    <label for="activatedNo">No</label><br>
	</div>
	
	<br>
	<label for="type" class="input-field-descriptor">Type:</label>
	<div class="input-field">
	    <input type="radio" id="typeAdvisor" name="type" value="2" class="radio-input">
	    <label for="typeAdvisor">Advisor</label>
	    <input type="radio" checked="checked" id="typeUser" name="type" value="3" class="radio-input">
	    <label for="typeUser">User</label>
	</div>
    <br>
    <div class="buttons-area">
        <button type="submit" id="addUserButton" formaction="/gym_admin_control/Controller?action=userAdd" disabled>Add User</button>
        <button type="button" onclick="clearInputs();">Clear All Inputs</button>
        <button type="submit" id="updateButton" formaction="/gym_admin_control/Controller?action=userUpdate" disabled>Update User</button>
        <button type="submit" id="deleteButton" formaction="/gym_admin_control/Controller?action=userRemove" disabled>Remove User</button>
    </div>
    </form>
</div>
</body>
</html>
