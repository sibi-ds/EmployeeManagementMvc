<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display All Projects</title>
</head>
<body>
	<a href="index.jsp">
		<button>Home</button>
	</a>

	<a href="project.jsp">
		<button>Back</button>
	</a>
	<br>
	<br>

	<div>
		<strong>${message}</strong>
	</div>
	<br>

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
</body>
</html>