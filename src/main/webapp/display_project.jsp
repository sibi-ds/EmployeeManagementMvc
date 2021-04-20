<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!--<%@ page import="java.util.List"%>-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>-->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Details</title>
</head>
<body>
	<form action="project?action=display_project" method="post">
		<label>Project ID : </label> 
		<input type="number" name="project_id" required="" />
		<input type="submit" value="Display Project" /> <br>
	</form>
	<br>
	<br>

	<div>
		<strong>${message}</strong>
	</div>
	<br>

	<c:if test="${project != null}">
		<div>
			<table border="1">
				<thead>
					<tr>
						<th>Project ID</th>
						<th>Title</th>
						<th>Client Name</th>
						<th>Manager ID</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Update Project</th>
						<th>Assign Employee</th>
						<th>Delete Project</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${project.getId()}</td>
						<td>${project.getTitle()}</td>
						<td>${project.getClientName()}</td>
						<td>${project.getManagerId()}</td>
						<td>${project.getStartDate()}</td>
						<td>${project.getEndDate()}</td>
						<td><a
							href="project?action=get_project_details&project_id=${project.getId()}">
								<button>Update Project</button>
						</a></td>
						<td><a
							href="project?action=get_assigned_employees&project_id=${project.getId()}">
								<button>Assign/Un Assign Employees</button>
						</a></td>
						<td><a
							href="project?action=delete_project&project_id=${project.getId()}">
								<button>Delete Project</button>
						</a></td>
					</tr>
				</tbody>
			</table>
			<br> <br>

			<c:if test="${!project.getEmployees().isEmpty()}">
				<table border="1">
					<thead>
						<tr>
							<th>Employee ID</th>
							<th>Name</th>
							<th>DOB</th>
							<th>Salary</th>
							<th>Mobile Number</th>
						<tr>
					</thead>
					<tbody>
						<c:forEach items="${project.getEmployees()}" var="employee">
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
				<br>
				<br>
			</c:if>
		</div>
	</c:if>

	<a href="index.jsp">
		<button>Home</button>
	</a>

	<a href="project.jsp">
		<button>Back</button>
	</a>
</body>
</html>