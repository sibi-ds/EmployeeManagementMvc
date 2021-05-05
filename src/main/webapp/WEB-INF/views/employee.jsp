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
			<a href="/employeeCreationForm">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Create
					Employee</button>
			</a><br> <br>
		</div>

		<div>
			<a href="/displayAllEmployees">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Display
					All Employees</button>
			</a><br> <br>
		</div>

		<div>
			<a href="/displayEmployee">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Display
					Employee</button>
			</a><br> <br>
		</div>
		
		<div>
			<a href="/getDeletedEmployees">
				<button
					style="height: 50px; width: 400px; font-size: 30px; border-radius: 30px">Restore
					Employee</button>
			</a><br> <br>
		</div>

		<a href="/">
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