<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display All Employees</title>
</head>
<body>
    <a href="index.jsp">
        <button>Home</button>
    </a>
    
    <a href="employee.jsp">
        <button>Back</button>
    </a>
    
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
		</tbody>
            <c:forEach items="${employees}" var="employee">
                <c:set var="employeeDetails" value="${fn:split(employee,',')}" />
                <tr>
                    <td><c:out value="${employeeDetails[0]}"></c:out></td>
                    <td><c:out value="${employeeDetails[1]}"></c:out></td>
                    <td><c:out value="${employeeDetails[2]}"></c:out></td>
                    <td><c:out value="${employeeDetails[3]}"></c:out></td>
                    <td><c:out value="${employeeDetails[4]}"></c:out></td>
                <tr>
            </c:forEach> 
	    </tbody>
    </table>
</body>
</html>