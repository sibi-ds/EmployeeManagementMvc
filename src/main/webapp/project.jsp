<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project</title>
</head>
<body>
    <div style="text-align:center">
        <div>
            <a href="create_project.jsp">
                <button style="height:50px; width:400px; font-size:30px; border-radius:30px">Create Project</button>
            </a><br><br>
        </div>
        
        <div>
            <a href="project?action=display_all_projects">
                <button style="height:50px; width:400px; font-size:30px; border-radius:30px">Display All Projects</button>
            </a><br><br>
        </div>
        
        <div>
            <a href="display_project.jsp">
                <button style="height:50px; width:400px; font-size:30px; border-radius:30px">Display Project</button>
            </a><br><br>
        </div>
        
        <div style="display:none">
            <a href="delete_project.jsp">
                <button style="height:50px; width:400px; font-size:30px; border-radius:30px">Delete Project</button>
            </a><br><br>
        </div>
        
        <div>
            <a href="project?action=get_deleted_projects">
                <button style="height:50px; width:400px; font-size:30px; border-radius:30px">Restore Project</button>
            </a><br><br>
        </div>
        
        <div style="display:none">
            <a href="project?action=assign_employees">
                <button style="height:50px; width:400px; font-size:30px; border-radius:30px">Assign Employees</button>
            </a><br><br>
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