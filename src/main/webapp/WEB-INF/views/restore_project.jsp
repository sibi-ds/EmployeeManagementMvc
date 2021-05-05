<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>-->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Restore Project</title>
</head>
<body>


	<div>
		<strong>${message}</strong>
	</div>
	<br>

	<c:if test="${projects != null}">
		<div>
			<form action="project?action=restore_project" method="post">
				<label>Project ID : </label>
				<input type="number" name="project_id" required="" />
				<input type="submit" value="Restore Project" /><br>
			</form>
			<br> <br>

			<table border="1">
				<thead>
					<tr>
						<th>Project ID</th>
						<th>Title</th>
						<th>Client Name</th>
						<th>Manager ID</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Restore</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projects}" var="project">
						<tr>
							<td><c:out value="${project.getId()}"></c:out></td>
							<td><c:out value="${project.getTitle()}"></c:out></td>
							<td><c:out value="${project.getClientName()}"></c:out></td>
							<td><c:out value="${project.getManagerId()}"></c:out></td>
							<td><c:out value="${project.getStartDate()}"></c:out></td>
							<td><c:out value="${project.getEndDate()}"></c:out></td>
							<td><a
								href="project?action=restore_project&project_id=${project.getId()}">Restore</a></td>
						<tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
		</div>
	</c:if>

	<a href="project.jsp">
		<button>Back</button>
	</a>

	<a href="index.jsp">
		<button>Home</button>
	</a>
</body>
</html>