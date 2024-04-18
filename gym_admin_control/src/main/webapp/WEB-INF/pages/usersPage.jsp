<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="net.etfbl.ip.gym_admin.beans.UserBean"%>
<%@page import="net.etfbl.ip.gym_admin.dao.*" %>
<%@page import="net.etfbl.ip.gym_admin.dto.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/stylesAdminControl.css">
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

            // Assign values to corresponding text-boxes
            document.getElementById("userId").value = id;
            document.getElementById("username").value = username;
            document.getElementById("city").value = city;
            document.getElementById("firstName").value = firstName;
            document.getElementById("lastName").value = lastName;
            document.getElementById("avatar").value = avatar;
            document.getElementById("email").value = email;
            console.log(activated);
            if(activated == "false")
            	{
            	document.getElementById("activatedNo").checked = "checked";
            	}
            else{
            	document.getElementById("activatedYes").checked = "checked";
            	}
            };
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
<% User selectedUser = new User(); %>
<div>
    <h1>User Management</h1>
    <form method="POST">
    	<label for="userSelect">Select User:</label>
        <select id="userSelect" name="userSelect" onchange="selectUser(); enableUpdateButton()">
        	<option data-id="" data-username="" data-city="" data-firstName="" data-lastName="" data-avatar="" data-email="">(none)</option>
            <% 
            for(User regularUser : UserDAO.selectAllRegularUsers())
            {
            %> <option 
            data-id="<%=regularUser.getId()%>" 
            data-username="<%=regularUser.getUsername()%>" 
            data-city="<%=regularUser.getCity()%>" 
            data-firstName="<%=regularUser.getFirstName()%>" 
            data-lastName="<%=regularUser.getLastName()%>" 
            data-avatar="<%=regularUser.getAvatar()%>" 
            data-email="<%=regularUser.getEmail()%>" 
            data-activated="<%=regularUser.getActivated()%>">
            <%= regularUser.getUsername()%></option> <%
            }
            %>
        </select><br>
    	<label for="userId">Selected User ID:</label>
        <input type="text" readonly="readonly" id="userId" name="userId"><br>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" oninput="validateInputs(); enableUpdateButton();"><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" oninput="validateInputs();"><br>
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" oninput="validateInputs();"><br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" oninput="validateInputs();"><br>
        <label for="city">City:</label>
        <input type="text" id="city" name="city" oninput="validateInputs();"><br>
        <label for="avatar">Avatar:</label>
        <input type="text" id="avatar" name="avatar"><br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" oninput="validateInputs();"><br>
        
    <label for="activated">Activated:</label><br>
    <input type="radio" id="activatedYes" name="activated" value="true">
    <label for="activatedYes">Yes</label>
    <input checked="checked" type="radio" id="activatedNo" name="activated" value="false">
    <label for="activatedNo">No</label><br>
    
    
        <button type="submit" id="addUserButton" formaction="/gym_admin_control/Controller?action=userAdd" disabled>Add User</button>
        <button type="button" onclick="clearInputs();">Clear All Inputs</button>
        <button type="submit" id="updateButton" formaction="/gym_admin_control/Controller?action=userUpdate" disabled>Update User</button>
        <button type="submit" id="deleteButton" formaction="/gym_admin_control/Controller?action=userRemove" disabled>Remove User</button>
    </form>
</div>
</body>
</html>
