<%@page import="java.util.List"%>
<%@page import="net.etfbl.ip.gym_admin.dto.FitnessProgramType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.etfbl.ip.gym_admin.dto.FitnessProgramType" %>
<%@ page import="net.etfbl.ip.gym_admin.dto.SpecificProgramAttribute" %>
<%@ page import="net.etfbl.ip.gym_admin.dao.FitnessProgramTypeDAO" %>
<%@ page import="net.etfbl.ip.gym_admin.dao.SpecificProgramAttributeDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/stylesAdminControl.css">
    <title>Fitness Categories</title>
    <script>
    	function checkTypeButtons()
    	{
    		var addButton = document.getElementById("addFitnessTypeButton");
    		var updateButton = document.getElementById("updateFitnessTypeButton");
    		var removeButton = document.getElementById("deleteFitnessTypeButton");
    		
    		var nameTextField = document.getElementById("fitnessTypeName");
    		
    		console.log(nameTextField.value.length > 0);
    		
    		var select = document.getElementById("existingProgramSelect");
    		var selectedOption = select.options[select.selectedIndex];
    		var selectedId = selectedOption.getAttribute("data-id");
    		var selectedName = selectedOption.getAttribute("data-name");
    		console.log(selectedId);
    		
    		if(selectedId.length > 0 && nameTextField.value.length > 0)
    			{
    				addButton.disabled = false;
    			}
    		else{
    				addButton.disabled = true;
    			}
    	}
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
<% List<FitnessProgramType> allTypes = FitnessProgramTypeDAO.selectAllTypes(); %>
<div>
    <h1>Fitness Categories</h1>
    <div id="fitnessTypes">
        <h2>Fitness Types</h2>
        <!-- Form for adding/updating fitness types -->
        <form id="fitnessTypeForm" method="POST">
        	<label for="existingProgramSelect">Program Type:</label>
        	<select id="existingProgramSelect" name="existingProgramSelect" onchange="checkTypeButtons()">
            <option data-id="" data-name="">(none)</option>
                <% 
                for(FitnessProgramType programType : allTypes)
                {
                	%> <option data-id="<%= programType.getId()%>" data-name="<%= programType.getName()%>"><%= programType.getId()%>: <%= programType.getName()%></option> <%
                }
                
                %>
            </select>
            <label for="fitnessTypeName">Name:</label>
            <input type="text" id="fitnessTypeName" name="fitnessTypeName" oninput="checkTypeButtons()">
            <button type="submit" id="addFitnessTypeButton" disabled>Add Fitness Type</button>
            <button type="submit" id="updateFitnessTypeButton" disabled>Update Fitness Type</button>
            <button type="submit" id="deleteFitnessTypeButton" disabled>Delete Fitness Type</button>
        </form>
        <div id="fitnessTypeList">
            <!-- Fitness type list will be displayed here -->
        </div>
    </div>
    <div id="specificAttributes">
        <h2>Specific Attributes</h2>
        <!-- Form for adding/updating specific attributes -->
        <form id="specificAttributeForm" method="POST">
            <label for="attributeName">Attribute Name:</label>
            <input type="text" id="attributeName" name="attributeName">
            <label for="programTypeSelect">Program Type:</label>
            <select id="programTypeSelect" name="programTypeSelect">
            <option data-id="" data-name="">(none)</option>
                <% 
                for(FitnessProgramType programType : allTypes)
                {
                	%> <option data-id="<%= programType.getId()%>" data-name="<%= programType.getName()%>"><%= programType.getName()%></option> <%
                }
                
                %>
            </select>
            <label for="value">Attribute value:</label>
            <input type="text" id="value" name="value">
            <button type="submit" id="addAttributeButton" disabled>Add Attribute</button>
            <button type="submit" id="updateAttributeButton" disabled>Update Attribute</button>
            <button type="submit" id="deleteAttributeButton" disabled>Delete Attribute</button>
        </form>
        <div id="attributeList">
            <!-- Attribute list will be displayed here -->
        </div>
    </div>
</div>
</body>
</html>
