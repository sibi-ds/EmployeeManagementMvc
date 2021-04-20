<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Restore Employee</title>
</head>
<body>
	<div>
		<strong>${message}</strong>
	</div>
	<br>

	<c:if test="${employees != null}">
		<div>
			<form action="employee?action=restore_employee" method="post">
				<label>Employee ID : </label>
				<input type="number" name="employee_id" required="" />
				<input type="submit" value="Restore Employee" /><br>
			</form>
			<br> <br>

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
					<tr>
						<td><c:out value="${employee.getId()}"></c:out></td>
						<td><c:out value="${employee.getName()}"></c:out></td>
						<td><c:out value="${employee.getDateOfBirth()}"></c:out></td>
						<td><c:out value="${employee.getSalary()}"></c:out></td>
						<td><c:out value="${employee.getMobileNumber()}"></c:out></td>
						<td><a
							href="employee?action=restore_employee&employee_id=${employee.getId()}">Restore</a></td>
					<tr>
				</c:forEach>
				</tbody>
			</table>
			<br> <br>
		</div>
	</c:if>

	<a href="employee.jsp">
		<button>Back</button>
	</a>

	<a href="index.jsp">
		<button>Home</button>
	</a>
</body>
</html>