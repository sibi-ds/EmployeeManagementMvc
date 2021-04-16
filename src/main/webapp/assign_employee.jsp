<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Assign Employees</title>
</head>
<body>
    <form action="project?action=assign_employee" method="post">
        <% String hidden = request.getParameter("project_id"); %>
        <input type="hidden" name="project_id" value="<%=hidden %>"/>
        <label>Employee ID : </label> <input type="number" name="employee_id" required="" />
        <input type="submit" value="Assign Employee" /><br>
    </form><br><br>
    
    <div>
        <strong>${message}</strong>
    </div><br>
    
    <a href="index.jsp">
        <button>Home</button>
    </a>
    
    <a href="display_project.jsp">
        <button>Back</button>
    </a>
</body>
</html>