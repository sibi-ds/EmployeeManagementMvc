<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Employee</title>
<style type="text/css">
    label {
        width:150px;
        display:inline-block;
        align:center;
    }
</style>
</head>
<body>
	<form:form method="post" modelAttribute="employee">
	    <form:hidden path="id" name="employee_id" value="${employee.id}" />

		<h3>Basic Details</h3>
		<label>Name</label>
		<form:input type="text" path="name" value="${employee.name}" required="required" /> <br> <br>
		<label>Date Of Birth</label>
		<form:input type="date" path="dateOfBirth" value="${employee.dateOfBirth}" required="required" /> <br> <br>
		<label>Salary</label>
		<form:input type="number" path="salary" value="${employee.salary}" required="required" /> <br> <br>
		<label>Mobile Number</label>
		<form:input type="number" path="mobileNumber" value="${employee.mobileNumber}" required="required" /> <br> <br>

		<h3>Permanent Address Details</h3>
		<form:hidden path="addresses[0].addressId" value="${permanentAddress.addressId}" />
		<form:hidden path="addresses[0].addressType" value="permanent" />
		<label>Door Number</label>
		<form:input path="addresses[0].doorNumber" required="required" value="${permanentAddress.doorNumber}" /><br> <br>
		<label>Street</label>
		<form:input path="addresses[0].street" required="required" value="${permanentAddress.street}" /><br> <br>
		<label>Village</label>
		<form:input path="addresses[0].village" required="required" value="${permanentAddress.village}" /><br> <br>
		<label>District</label>
		<form:input path="addresses[0].district" required="required" value="${permanentAddress.district}" /><br> <br>
		<label>State</label>
		<form:input path="addresses[0].state" required="required" value="${permanentAddress.state}" /><br> <br>
		<label>Pincode</label>
		<form:input path="addresses[0].pincode" required="required" value="${permanentAddress.pincode}" /><br> <br>

	    <h3>Do you want to have temporary address ?</h3>
	    <input type="radio" name="isTemporaryAddressPresent" value="1" onClick="addRequired()" checked required="required" />Yes
	    <input type="radio" name="isTemporaryAddressPresent" value="0" onClick="removeRequired()" />No
	    <br>
	    <br>
	    
	    <div id="temporary_address" style="display:block">
		    <form:hidden path="addresses[1].addressId" value="${temporaryAddress.addressId}" />
		    <form:hidden path="addresses[1].addressType" value="temporary" />
		    <label>Door Number</label>
		    <form:input id="door_number" path="addresses[1].doorNumber" value="${temporaryAddress.doorNumber}" required="required" /><br> <br>
		    <label>Street</label>
		    <form:input id="street" path="addresses[1].street" value="${temporaryAddress.street}" required="required" /><br> <br>
		    <label>Village</label>
		    <form:input id="village" path="addresses[1].village" value="${temporaryAddress.village}" required="required" /><br> <br>
		    <label>District</label>
		    <form:input id="district" path="addresses[1].district" value="${temporaryAddress.district}" required="required" /><br> <br>
		    <label>State</label>
		    <form:input id="state" path="addresses[1].state" value="${temporaryAddress.state}" required="required" /><br> <br>
		    <label>Pincode</label>
		    <form:input id="pincode" path="addresses[1].pincode" value="${temporaryAddress.pincode}" required="required" /><br> <br>
	    </div>

        <c:if test="${0 == employee.id}">
    	    <input formaction="/createEmployee" type="submit" value="Create Employee" />
        </c:if>

        <c:if test="${0 != employee.id}">
    	    <input formaction="/updateEmployee" type="submit" value="Update Employee" />
        </c:if>
	</form:form>
	<br>

	<a href="/employee">
		<button>Back</button>
	</a>

	<a href="/">
		<button>Home</button>
	</a>
	<br>

	<div>
		<strong>${message}</strong>
	</div>
	
	<script type="text/javascript">
	    function removeRequired() {
	    	document.getElementById("temporary_address").style.display ='none';
	    	document.getElementById("door_number").removeAttribute("required");
	    	document.getElementById("street").removeAttribute("required");
	    	document.getElementById("village").removeAttribute("required");
	    	document.getElementById("district").removeAttribute("required");
	    	document.getElementById("state").removeAttribute("required");
	    	document.getElementById("pincode").removeAttribute("required");
	    }
	    
	    function addRequired() {
	    	document.getElementById("temporary_address").style.display ='block';
	    	document.getElementById("door_number").setAttribute("required","required");
	    	document.getElementById("street").setAttribute("required","required");
	    	document.getElementById("village").setAttribute("required","required");
	    	document.getElementById("district").setAttribute("required","required");
	    	document.getElementById("state").setAttribute("required","required");
	    	document.getElementById("pincode").setAttribute("required","required");
	    }
	</script>
	
</body>
</html>