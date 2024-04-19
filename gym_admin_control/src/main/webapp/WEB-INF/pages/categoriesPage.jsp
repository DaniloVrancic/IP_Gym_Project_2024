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
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jersey+25&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Play:wght@400;700&display=swap" rel="stylesheet">
    
    <link rel="stylesheet" href="css/stylesHeader.css">
    <link rel="stylesheet" href="css/stylesAdminControl.css">
    <link rel="stylesheet" href="css/stylesCategoriesPage.css">
    <link rel="stylesheet" href="css/form-style.css">
    
    
    <title>Fitness Categories</title>
    <script>
    	function checkTypeButtons()
    	{
    		var addButton = document.getElementById("addFitnessTypeButton");
    		var updateButton = document.getElementById("updateFitnessTypeButton");
    		var deleteButton = document.getElementById("deleteFitnessTypeButton");
    		
    		var nameTextField = document.getElementById("fitnessTypeName");
    		
    		var select = document.getElementById("existingProgramSelect");
    		var selectedOption = select.options[select.selectedIndex];
    		var selectedId = selectedOption.getAttribute("data-id");
    		var selectedName = selectedOption.getAttribute("data-name");
    		
    		
    		if(nameTextField.value.length == 0)
    			{
    				addButton.disabled = true;
    			}
    		else{
    				addButton.disabled = false;
    			}
    		
    		if(selectedId.length > 0)
    			{
    			deleteButton.disabled = false;
    			}
    		else
    			{
    			deleteButton.disabled = true;
    			}
    		if(selectedId.length > 0 && nameTextField.value.length > 0)
    			{
    			updateButton.disabled = false;
    			}
    		else{
    			updateButton.disabled = true;
    		}
    	}
    	
    	function selectUpdateTextField()
    	{
    		let nameTextField = document.getElementById("fitnessTypeName");
    		let fitnessTypeId = document.getElementById("fitnessTypeId");
    		let select = document.getElementById("existingProgramSelect");
    		let selectedOption = select.options[select.selectedIndex];
    		let selectedId 	 = selectedOption.getAttribute("data-id");
    		let selectedName = selectedOption.getAttribute("data-name");
    		
    		nameTextField.value = selectedName;
    		fitnessTypeId.value = selectedId;
    	}
    	
    	function checkAttributeButtons()
    	{
    		let addAttributeButton	 = document.getElementById("addAttributeButton");
    		let updateAttributeButton= document.getElementById("updateAttributeButton");
    		let deleteAttributeButton= document.getElementById("deleteAttributeButton");
    		
    		let attributeNameTextBox = document.getElementById("attributeName");
    		let categorySelect 		 = document.getElementById("programTypeSelect");
    		let categorySelectOption = categorySelect.options[categorySelect.selectedIndex];
    		let attributeValueTextBox= document.getElementById("attributeValue");
    		let typeForAttributeInput= document.getElementById("typeForAttribute");
    		
    		
    		var selectedTypeId = categorySelectOption.getAttribute("data-id");
    		var selectedTypeName = categorySelectOption.getAttribute("data-name");
    		typeForAttributeInput.value = selectedTypeId;
    		getNewAttributeList();
    		
    		
    		
    		if(selectedTypeId.length == 0) //If no type is selected, all the buttons are disabled and no need to check further.
    			{
    			addAttributeButton.disabled = true;
    			updateAttributeButton.disabled = true;
    			deleteAttributeButton.disabled = true;
    			return;
    			}
    		else //if a type IS selected... :
    			{
    			
    			}
    	}
    	
    	function getNewAttributeList(){
    		let typeForAttributeInput = document.getElementById("typeForAttribute");
    		
    		if(typeForAttributeInput.value.length > 0)
    			{
    			populateSpecificAttributes(typeForAttributeInput.value);
    			}
    		else{
    			var select = document.getElementById('specificAttributeSelect');
    			select.innerHTML = ''; //Clear all leftover options from before
    			var defaultOption = document.createElement('option');
                
                defaultOption.text = '(none)';
                defaultOption.value = '';
                defaultOption.setAttribute('data-id', '');
                defaultOption.setAttribute('data-name', '');
                defaultOption.setAttribute('data-value', '');
                defaultOption.setAttribute('data-type', '');
                select.appendChild(defaultOption);
    		}
    		
    	}
    	
    	function populateSpecificAttributes(programTypeId) {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        var attributes = JSON.parse(xhr.responseText);
                        var select = document.getElementById('specificAttributeSelect');
                        select.innerHTML = ''; // Clear existing options
                        var defaultOption = document.createElement('option');
                        
                        defaultOption.text = '(none)';
                        defaultOption.value = '';
                        defaultOption.setAttribute('data-id', '');
                        defaultOption.setAttribute('data-name', '');
                        defaultOption.setAttribute('data-value', '');
                        defaultOption.setAttribute('data-type', '');
                        
                        select.appendChild(defaultOption);
                        for (var i = 0; i < attributes.length; i++) {
                            var option = document.createElement('option');
                            option.text = attributes[i].attributeName;
                            option.setAttribute('data-id', attributes[i].id);
                            option.setAttribute('data-type', attributes[i].programType);
                            option.setAttribute('data-name', attributes[i].attributeName);
                            option.setAttribute('data-value', attributes[i].attributeValue);
                            select.appendChild(option);
                        }
                    } else {
                        console.error("Failed to fetch specific attributes. Status code: " + xhr.status);
                    }
                }
            };
            xhr.open('POST', 'Controller', true); // Specify your controller servlet URL
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            
            xhr.send('action=specificAttributes&programTypeId=' + programTypeId); // Send parameters in request body
        }
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
<% List<FitnessProgramType> allTypes = FitnessProgramTypeDAO.selectAllTypes(); %>

<div class="main-content-div">
    <h1 class="form-title">Fitness Categories</h1><br>
    <div id="fitnessTypes">
        <!-- Form for adding/updating fitness types -->
        <div id="form-content-div-id">
        <form id="fitnessTypeForm" method="POST" class="fixed-width-form fixed-height-form">
        <h2 class="form-title">Fitness Types</h2>
        
        	<label for="existingProgramSelect" class="input-field-descriptor">Program Type:</label>
        	<select id="existingProgramSelect" name="existingProgramSelect" class="input-field select-input" onchange="checkTypeButtons();selectUpdateTextField();">
            <option data-id="" data-name="">(none)</option>
                <% 
                for(FitnessProgramType programType : allTypes)
                {
                	%> <option data-id="<%= programType.getId()%>" data-name="<%= programType.getName()%>"><%= programType.getId()%>: <%= programType.getName()%></option> <%
                }
                
                %>
            </select>
            <input type="hidden" id="fitnessTypeId" name="fitnessTypeId">
            
            <label for="fitnessTypeName" class="input-field-descriptor">Name:</label>
            <input type="text" id="fitnessTypeName" name="fitnessTypeName" class="input-field" placeholder=" " oninput="checkTypeButtons();">
            <br><br>
            <div class="buttons-area">
                <button type="submit" id="addFitnessTypeButton" disabled>Add Fitness Type</button>
                <button type="submit" id="updateFitnessTypeButton" disabled>Update Fitness Type</button>
                <button type="submit" id="deleteFitnessTypeButton" disabled>Delete Fitness Type</button>
            </div>
        </form>
        
    
    <div id="specificAttributes">
        <!-- Form for adding/updating specific attributes -->
        <form id="specificAttributeForm" method="POST" class="fixed-width-form fixed-height-form">
        	<h2 class="form-title">Specific Attributes</h2>
            <label for="programTypeSelect" class="input-field-descriptor">Program Type:</label>
            <select id="programTypeSelect" name="programTypeSelect" class="input-field select-input" onchange="checkAttributeButtons();">
            <option data-id="" data-name="">(none)</option>
                <% 
                for(FitnessProgramType programType : allTypes)
                {
                	%> <option data-id="<%= programType.getId()%>" data-name="<%= programType.getName()%>"><%= programType.getName()%></option> <%
                }
                
                %>
            </select>
            <input type="hidden" name="typeForAttribute" id="typeForAttribute">
            <label for="attributeList" class="input-field-descriptor">Specific attributes:</label>
            <select id="specificAttributeSelect" class="input-field select-input">
            <option data-name="" data-value="">(none)</option>
            <!-- Attribute list will be displayed here, depending on selected Fitness Program Type -->
            </select>
            <label for="attributeName" class="input-field-descriptor">Attribute Name:</label>
            <input type="text" id="attributeName" name="attributeName" class="input-field" placeholder=" " oninput="checkAttributeButtons();">
            <label for="attributeValue" class="input-field-descriptor">Attribute value:</label>
            <input type="text" id="attributeValue" name="attributeValue" class="input-field" placeholder=" " oninput="checkAttributeButtons();">
            <div class="buttons-area">
	            <button type="submit" id="addAttributeButton" disabled>Add Attribute</button>
	            <button type="submit" id="updateAttributeButton" disabled>Update Attribute</button>
	            <button type="submit" id="deleteAttributeButton" disabled>Delete Attribute</button>
            </div>
        </form>
        </div>
        </div>
    </div>
</div>
</body>
</html>
