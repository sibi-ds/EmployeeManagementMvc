<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Employee</title>
</head>
<body>
    <form action="employee?action=delete_employee" method="post">
        <label>Employee ID : </label>
        <input type="number" name="employee_id" required="" />
        <input type="submit" value="Delete Employee" /><br>
    </form><br><br>
    
    <div>
        <strong>${message}</strong> 
    </div><br>
    
    <a href="index.jsp">
        <button>Home</button>
    </a>
    
    <a href="display_employee.jsp">
        <button>Back</button>
    </a>
</body>
</html>