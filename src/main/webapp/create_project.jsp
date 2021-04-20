<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	<form action="project?action=create_project" method="post">
	    <label>Title</label>
		<input type="text" name="title" required="" /> <br> <br>
		<label>Client Name</label>
		<input type="text" name="client_name" required="" /> <br> <br>
		<label>Manager ID</label>
		<input type="number" name="manager_id" required="" /> <br> <br>
		<label>Start Date</label>
		<input type="date" name="start_date" required="" /> <br> <br>
		<label>End Date</label>
		<input type="date" name="end_date" required="" /> <br> <br>
		<input type="submit" value="Create Project" />
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