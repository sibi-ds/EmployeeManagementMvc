<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Details</title>
</head>
<body>
	<form action="/displayEmployeeDetails" method="post">
		<label>Employee ID : </label>
		<input type="number" name="employee_id" required="required" />
		<input type="submit" value="Display Employee" /> <br>
	</form>
	<br>
	<br>

	<div>
		<strong>${message}</strong>
	</div>
	<br>

	<c:if test="${employee != null}">
		<div>
			<table border="1">
				<thead>
					<tr>
						<th>Employee ID</th>
						<th>Name</th>
						<th>DOB</th>
						<th>Salary</th>
						<th>Mobile Number</th>
						<th>Update Employee</th>
						<th>Edit Projects Assigned</th>
						<th>Delete Employee</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${employee.getId()}</td>
						<td>${employee.getName()}</td>
						<td>${employee.getDateOfBirth()}</td>
						<td>${employee.getSalary()}</td>
						<td>${employee.getMobileNumber()}</td>
						<td><a
							href="/getEmployee/${employee.getId()}">
								<button>Update Employee</button>
						</a></td>
						<td><a
							href="/projectAssignPage/${employee.getId()}">
								<button>Assign/UnAssign Projects</button>
						</a></td>
						<td><a
							href="/deleteEmployee/${employee.getId()}">
								<button>Delete Employee</button>
						</a></td>
					</tr>
				</tbody>
			</table>
			<br> <br>

			<c:if test="${!employee.getAddresses().isEmpty()}">
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
						<c:forEach items="${employee.getAddresses()}" var="address">
						    <c:if test="${address.getIsDeleted() == false}">
							    <tr>
								    <td><c:out value="${address.getAddressType()}"></c:out></td>
								    <td><c:out value="${address.getDoorNumber()}"></c:out></td>
								    <td><c:out value="${address.getStreet()}"></c:out></td>
								    <td><c:out value="${address.getVillage()}"></c:out></td>
								    <td><c:out value="${address.getDistrict()}"></c:out></td>
								    <td><c:out value="${address.getState()}"></c:out></td>
								    <td><c:out value="${address.getPincode()}"></c:out></td>
							    </tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>

			<c:if test="${!employee.getProjects().isEmpty()}">
				<table border="1">
					<thead>
						<tr>
							<th>Project ID</th>
							<th>Title</th>
							<th>Client Name</th>
							<th>Manager ID</th>
							<th>Start Date</th>
							<th>End Date</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${employee.getProjects()}" var="project">
							<tr>
								<td><c:out value="${project.getId()}"></c:out></td>
								<td><c:out value="${project.getTitle()}"></c:out></td>
								<td><c:out value="${project.getClientName()}"></c:out></td>
								<td><c:out value="${project.getManagerId()}"></c:out></td>
								<td><c:out value="${project.getStartDate()}"></c:out></td>
								<td><c:out value="${project.getEndDate()}"></c:out></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
			</c:if>
		</div>
	</c:if>

	<a href="/employee">
		<button>Back</button>
	</a>

	<a href="/">
		<button>Home</button>
	</a>
</body>
</html>