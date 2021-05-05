<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Assign Project</title>
</head>
<body>
	<form action="/assignProjects" method="post">
		<input type="hidden" name="employee_id" value="${employee_id}" />
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
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projects}" var="project">
						<c:set var="project_id" value="${project.getId()}" />
						<c:set var="isAssigned" value="true" />
						<tr>
							<td><c:out value="${project.getId()}"></c:out></td>
							<td><c:out value="${project.getTitle()}"></c:out></td>
							<td><c:out value="${project.getClientName()}"></c:out></td>
							<td><c:out value="${project.getManagerId()}"></c:out></td>
							<td><c:out value="${project.getStartDate()}"></c:out></td>
                            <td><c:out value="${project.getEndDate()}"></c:out></td>

							<td>
							    <c:forEach items="${assignedProjects}" var="assignedProject">
									<c:if test="${assignedProject.getId() == project_id}">
										<input name="check" type="checkbox" checked value="${project_id}">
										<c:set var="isAssigned" value="false" />
									</c:if>
								</c:forEach>
								<c:if test="${isAssigned == 'true'}">
									<input name="check" type="checkbox" value="${project_id}">
								</c:if>
						    </td>
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

	<a href="/">
		<button>Home</button>
	</a>

	<a href="/displayEmployee">
		<button>Back</button>
	</a>
</body>
</html>