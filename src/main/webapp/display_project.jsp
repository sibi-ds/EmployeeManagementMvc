<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Details</title>
</head>
<body>
    <form action="project?action=display_project" method="post">
        <label>Project ID : </label> <input type="number" name="project_id" required="" />
        <input type="submit" value="Display Project" /><br>
    </form><br><br>
    
    <div>
        <strong>${message}</strong>
    </div><br>
 
    <table border="1">
	    <thead>
		    <tr>
			    <th>Project ID</td>
			    <th>Title</td>
			    <th>Client Name</td>
			    <th>Manager ID</td>
			    <th>Start Date</td>
			    <th>End Date</td>
		    </tr>
		</thead>
		<tbody>
		    <tr>
			    <td>${project_id}</td>
			    <td>${title}</td>
			    <td>${client_name}</td>
			    <td>${manager_id}</td>
			    <td>${start_date}</td>
			    <td>${end_date}</td>
		    </tr>
	    </tbody>
    </table><br><br>

    <table border="1">
        <thead>
            <tr>
                <th>Employee ID</th>
                <th>Name</th>
                <th>DOB</th>
                <th>Salary</th>
                <th>Mobile Number</th>
                <th>Unassign</th>
            <tr>
        </thead>
        <tbody>
            <c:forEach items="${employees}" var="employee">
                <c:set var="employeeDetails" value="${fn:split(employee,',')}" />
                <tr>
                    <td><c:out value="${employeeDetails[0]}"></c:out></td>
                    <td><c:out value="${employeeDetails[1]}"></c:out></td>
                    <td><c:out value="${employeeDetails[2]}"></c:out></td>
                    <td><c:out value="${employeeDetails[3]}"></c:out></td>
                    <td><c:out value="${employeeDetails[4]}"></c:out></td>
                    <td><a href="project?action=unassign_employee&project_id=${project_id}&employee_id=${employeeDetails[0]}">Unassign</a></td>
                <tr>
            </c:forEach>   
        </tbody>
    </table><br><br>
    
    <a href="project?action=get_project_details&project_id=${project_id}">
        <button>Update Project</button>
    </a>
    
    <a href="assign_employee.jsp?project_id=${project_id}">
        <button>Assign Employee</button>
    </a>
    
    <a href="project?action=delete_project&project_id=${project_id}">
        <button>Delete Project</button>
    </a><br><br>
    
    <a href="index.jsp">
        <button>Home</button>
    </a>
    
    <a href="display_employee.jsp">
        <button>Back</button>
    </a>
</body>
</html>