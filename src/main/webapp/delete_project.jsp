<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Project</title>
</head>
<body>
    <form action="project?action=delete_project" method="post">
        <label>Project ID : </label> <input type="number" name="project_id" required="" />
        <input type="submit" value="Delete Project" /><br>
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