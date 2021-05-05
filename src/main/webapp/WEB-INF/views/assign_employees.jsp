<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Assign Employees</title>
</head>
<body>
	<form action="project?action=assign_employees" method="post">
		<input type="hidden" name="project_id" value="<%= request.getParameter("project_id") %>" />
		<div>
			<table border="1">
				<thead>
					<tr>
						<th>Employee ID</th>
						<th>Name</th>
						<th>DOB</th>
						<th>Salary</th>
						<th>Mobile Number</th>
						<th>Assign</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${employees}" var="employee">
						<c:set var="employee_id" value="${employee.getId()}" />
						<c:set var="isAssigned" value="true" />
						<tr>
							<td><c:out value="${employee.getId()}"></c:out></td>
							<td><c:out value="${employee.getName()}"></c:out></td>
							<td><c:out value="${employee.getDateOfBirth()}"></c:out></td>
							<td><c:out value="${employee.getSalary()}"></c:out></td>
							<td><c:out value="${employee.getMobileNumber()}"></c:out></td>

							<td><c:forEach items="${assignedEmployees}"
									var="assignedEmployee">
									<c:if test="${assignedEmployee.getId() == employee_id}">
										<input name="check" type="checkbox" checked value="${employee_id}">
										<c:set var="isAssigned" value="false" />
									</c:if>
								</c:forEach> <c:if test="${isAssigned == 'true'}">
									<input name="check" type="checkbox" value="${employee_id}">
								</c:if></td>
						<tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br> <input type="submit" value="Assign Employees" />
	</form>

	<div>
		<strong>${message}</strong>
	</div>
	<br>

	<a href="index.jsp">
		<button>Home</button>
	</a>

	<a href="display_project.jsp">
		<button>Back</button>
	</a>
</body>
</html>