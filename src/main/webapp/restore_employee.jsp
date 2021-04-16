<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Restore Employee</title>
</head>
<body>
    <form action="employee?action=restore_employee" method="post">
        <label>Employee ID : </label> <input type="number" name="employee_id" required="" />
        <input type="submit" value="Restore Employee" /><br>
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
                <th>Restore</th>
            </tr>
        </thead>
		</tbody>
            <c:forEach items="${employees}" var="employee">
                <c:set var="employeeDetails" value="${fn:split(employee,',')}" />
                <tr>
                    <td><c:out value="${employeeDetails[0]}"></c:out></td>
                    <td><c:out value="${employeeDetails[1]}"></c:out></td>
                    <td><c:out value="${employeeDetails[2]}"></c:out></td>
                    <td><c:out value="${employeeDetails[3]}"></c:out></td>
                    <td><c:out value="${employeeDetails[4]}"></c:out></td>
                    <td><a href="employee?action=restore_employee&employee_id=${employeeDetails[0]}">Restore</a></td>
                <tr>
            </c:forEach> 
	    </tbody>
    </table><br><br>
    
    <a href="employee.jsp">
        <button>Back</button>
    </a>
    
    <a href="index.jsp">
        <button>Home</button>
    </a>
</body>
</html>