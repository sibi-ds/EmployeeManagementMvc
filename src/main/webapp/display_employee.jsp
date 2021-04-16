<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Details</title>
</head>
<body>
    <form action="employee?action=display_employee" method="post">
        <label>Employee ID : </label> <input type="number" name="employee_id" required="" />
        <input type="submit" value="Display Employee" /><br>
    </form><br><br>
    
    <div>
        <strong>${message}</strong>
    </div><br>

    <table border="1">
	    <thead>
		    <tr>
			    <th>Employee ID</th>
			    <th>Name</th>
			    <th>DOB</th>
			    <th>Salary</th>
			    <th>Mobile Number</th>
		    </tr>
		</thead>
		<tbody>
		    <tr>
			    <td>${employee_id}</td>
			    <td>${name}</td>
			    <td>${date_of_birth}</td>
			    <td>${salary}</td>
			    <td>${mobile_number}</td>
		    </tr>
	    </tbody>
    </table><br><br>

    <table border="1">
        <thead>
            <tr>
                <th>Address Type</th>
                <th>Door Number</th>
                <th>Street</th>
                <th>Village</th>
                <th>District</th>
                <th>State</th>
                <th>Pincode</th>
            <tr>
        </thead>
        <tbody>
            <c:forEach items="${addresses}" var="address">
                <c:set var="addressDetails" value="${fn:split(address,',')}" />
                <tr>
                    <td><c:out value="${addressDetails[0]}"></c:out></td>
                    <td><c:out value="${addressDetails[1]}"></c:out></td>
                    <td><c:out value="${addressDetails[2]}"></c:out></td>
                    <td><c:out value="${addressDetails[3]}"></c:out></td>
                    <td><c:out value="${addressDetails[4]}"></c:out></td>
                    <td><c:out value="${addressDetails[5]}"></c:out></td>
                    <td><c:out value="${addressDetails[6]}"></c:out></td>
                <tr>
            </c:forEach>   
        </tbody>
    </table><br><br>
    
    <table border="1">
	    <thead>
		    <tr>
			    <th>Project ID</th>
			    <th>Title</th>
			    <th>Client Name</th>
			    <th>Manager ID</th>
			    <th>Start Date</th>
			    <th>End Date</th>
			    <th>Unassign Project</th>
		    </tr>
		</thead>
		<tbody>
            <c:forEach items="${projects}" var="project">
                <c:set var="projectDetails" value="${fn:split(project,',')}" />
                <tr>
                    <td><c:out value="${projectDetails[0]}"></c:out></td>
                    <td><c:out value="${projectDetails[1]}"></c:out></td>
                    <td><c:out value="${projectDetails[2]}"></c:out></td>
                    <td><c:out value="${projectDetails[3]}"></c:out></td>
                    <td><c:out value="${projectDetails[4]}"></c:out></td>
                    <td><c:out value="${projectDetails[5]}"></c:out></td>
                    <td><a href="employee?action=unassign_project&employee_id=${employee_id}&project_id=${projectDetails[0]}">Unassign</a></td>
                <tr>
            </c:forEach> 
	    </tbody>
    </table><br>

    <a href="employee?action=get_employee_details&employee_id=${employee_id}">
        <button>Update Employee</button>
    </a>

    <a href="assign_project.jsp?employee_id=${employee_id}">
        <button>Assign Project</button>
    </a>
    
    <a href="employee?action=delete_employee&employee_id=${employee_id}">
        <button>Delete Employee</button>
    </a><br><br>
    
    <a href="employee.jsp">
        <button>Back</button>
    </a>
    
    <a href="index.jsp">
        <button>Home</button>
    </a>
</body>
</html>