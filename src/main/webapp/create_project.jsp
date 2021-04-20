<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="ISO-8859-1">
<title>Create Project</title>
<style type="text/css">
    label {
        width:100px;
        display:inline-block;
    }
</style>
</head>
<body>
	<form method="post">
	    <input type="hidden" name="project_id" value="${project.getId()}" />
	    <label>Title</label>
		<input type="text" name="title" required="" value="${project.getTitle()}" /> <br> <br>
		<label>Client Name</label>
		<input type="text" name="client_name" required="" value="${project.getClientName()}" /> <br> <br>
		<label>Manager ID</label>
		<input type="number" name="manager_id" required="" value="${project.getManagerId()}" /> <br> <br>
		<label>Start Date</label>
		<input type="date" name="start_date" required="" value="${project.getStartDate()}" /> <br> <br>
		<label>End Date</label>
		<input type="date" name="end_date" required="" value="${project.getEndDate()}" /> <br> <br>
		
		<c:if test="${null != project.getId()}">
            <input type="submit" formaction="project?action=update_project" value="Update Project" />
		</c:if>
		
		<c:if test="${null == project.getId()}">
		    <input type="submit" formaction="project?action=create_project" value="Create Project" />
		</c:if>
	</form>
	<br>

	<a href="index.jsp">
		<button>Home</button>
	</a>

	<a href="project.jsp">
		<button>Back</button>
	</a>

	<div>
		<strong>${message}</strong>
	</div>
</body>
</html>