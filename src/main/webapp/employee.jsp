<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee</title>
</head>
<body>
	<div style="text-align: center">
		<div>
			<a href="create_employee.jsp">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Create
					Employee</button>
			</a><br> <br>
		</div>

		<div>
			<a href="employee?action=display_all_employees">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Display
					All Employees</button>
			</a><br> <br>
		</div>

		<div>
			<a href="display_employee.jsp">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Display
					Employee</button>
			</a><br> <br>
		</div>

		<div style="display: none">
			<a href="delete_employee.jsp">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Delete
					Employee</button>
			</a><br> <br>
		</div>
		<div>

			<a href="employee?action=get_deleted_employees">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Restore
					Employee</button>
			</a><br> <br>
		</div>

		<div style="display: none">
			<a href="employee?action=assign_project">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Assign
					Project</button>
			</a><br> <br>
		</div>

		<a href="index.jsp">
			<button
				style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Home</button>
		</a><br>
		<br>

		<div>
			<h1>${message}</h1>
		</div>
	</div>
</body>
</html>