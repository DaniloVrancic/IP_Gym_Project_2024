<%@page import="net.etfbl.ip.gym_admin.dto.FitnessProgramType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.etfbl.ip.gym_admin.dto.FitnessProgramType" %>
<%@ page import="net.etfbl.ip.gym_admin.dto.SpecificProgramAttribute" %>
<%@ page import="net.etfbl.ip.gym_admin.dao.FitnessProgramTypeDAO" %>
<%@ page import="net.etfbl.ip.gym_admin.dao.SpecificProgramAttributeDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/stylesAdminControl.css">
    <title>Fitness Categories</title>
    <script>
        // JavaScript functions for CRUD operations will go here
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/partials/header.jsp"></jsp:include>
<div>
    <h1>Fitness Categories</h1>
    <div id="fitnessTypes">
        <h2>Fitness Types</h2>
        <!-- Form for adding/updating fitness types -->
        <form id="fitnessTypeForm" method="POST">
            <label for="fitnessTypeName">Name:</label>
            <input type="text" id="fitnessTypeName" name="fitnessTypeName">
            <button type="submit" id="addFitnessTypeButton">Add Fitness Type</button>
            <button type="submit" id="updateFitnessTypeButton">Update Fitness Type</button>
            <button type="submit" id="deleteFitnessTypeButton">Delete Fitness Type</button>
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
                for(FitnessProgramType programType : FitnessProgramTypeDAO.selectAllTypes())
                {
                	%> <option data-id="<%= programType.getId()%>" data-name="<%= programType.getName()%>"><%= programType.getName()%></option> <%
                }
                
                %>
            </select>
            <label for="value">Attribute value:</label>
            <input type="text" id="value" name="value">
            <button type="submit" id="addAttributeButton">Add Attribute</button>
            <button type="submit" id="updateAttributeButton">Update Attribute</button>
            <button type="submit" id="deleteAttributeButton">Delete Attribute</button>
        </form>
        <div id="attributeList">
            <!-- Attribute list will be displayed here -->
        </div>
    </div>
</div>
</body>
</html>
