<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <meta charset="ISO-8859-1">
    <title>Update Project</title>
</head>
<body>
    <form action="project?action=update_project" method="post">
        <% String hidden = request.getParameter("project_id"); %>
        <input type="hidden" name="project_id" value="<%=hidden %>"/>
        
        <label>Title       : </label> <input type="text" name="title" required="" value="${title}" /><br><br>
        <label>Client Name : </label> <input type="text" name="client_name" required="" value="${client_name}" /><br><br>
        <label>Manager ID  : </label> <input type="number" name="manager_id" required="" value="${manager_id}" /><br><br>
        <label>Start Date  : </label> <input type="date" name="start_date" required="" value="${start_date}" /><br><br>
        <label>End  Date   : </label> <input type="date" name="end_date" required="" value="${end_date}" /><br><br>

        <input type="submit" value="Update Project" />
    </form><br>
    
    <div>
        <strong>${message}</strong> 
    </div>
</body>
</html>