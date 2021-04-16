<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display All Projects</title>
</head>
<body>
    <a href="index.jsp">
        <button>Home</button>
    </a>
    
    <a href="project.jsp">
        <button>Back</button>
    </a>
    
    <div>
        <strong>${message}</strong>
    </div><br>
    
    <table border="1">
        <thead>
            <tr>
			    <th>Project ID</td>
			    <th>Title</td>
			    <th>Client Name</td>
			    <th>Manager ID</td>
			    <th>Start Date</td>
			    <th>End Date</td>
            </tr>
        </thead>
		</tbody>
            <c:forEach items="${projects}" var="project">
                <c:set var="projectDetails" value="${fn:split(project,',')}" />
                <tr>
                    <td><c:out value="${projectDetails[0]}"></c:out></td>
                    <td><c:out value="${projectDetails[1]}"></c:out></td>
                    <td><c:out value="${projectDetails[2]}"></c:out></td>
                    <td><c:out value="${projectDetails[3]}"></c:out></td>
                    <td><c:out value="${projectDetails[4]}"></c:out></td>
                    <td><c:out value="${projectDetails[5]}"></c:out></td>
                <tr>
            </c:forEach> 
	    </tbody>
    </table>
</body>
</html>