<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display All Employees</title>
</head>
<body>
	<a href="/">
		<button>Home</button>
	</a>

	<a href="/employee">
		<button>Back</button>
	</a>
	<br>
	<br>

	<div>
		<strong>${message}</strong>
	</div>
	<br>

	<c:if test="${employees != null}">
		<div>
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
					<tr>
						<td><c:out value="${employee.getId()}"></c:out></td>
						<td><c:out value="${employee.getName()}"></c:out></td>
						<td><c:out value="${employee.getDateOfBirth()}"></c:out></td>
						<td><c:out value="${employee.getSalary()}"></c:out></td>
						<td><c:out value="${employee.getMobileNumber()}"></c:out></td>
					<tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</body>
</html>