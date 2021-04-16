<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Assign Project</title>
</head>
<body>
    <form action="employee?action=assign_project" method="post">
        <% String hidden = request.getParameter("employee_id"); %>
        <input type="hidden" name="employee_id" value="<%=hidden %>"/>
        <label>Project ID : </label> <input type="number" name="project_id" required="" />
        <input type="submit" value="Assign Project" /><br>
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