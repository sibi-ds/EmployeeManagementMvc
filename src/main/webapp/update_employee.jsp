<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <meta charset="ISO-8859-1">
    <title>Update Employee</title>
</head>
<body>
    <form action="employee?action=update_employee" method="post">
        <% String hidden = request.getParameter("employee_id"); %>
        <input type="hidden" name="employee_id" value="<%=hidden %>"/>
        
        <h3>Basic Details</h3>
        <label>Name          : </label> <input type="text" name="name" required="" value="${name}" /><br><br>
        <label>Date Of Birth : </label> <input type="date" name="date_of_birth" required="" value="${date_of_birth}" /><br><br>
        <label>Salary        : </label> <input type="number" name="salary" required="" value="${salary}" /><br><br>
        <label>Mobile Number : </label> <input type="number" name="mobile_number" required="" value="${mobile_number}" /><br><br>
        
        <h3>Permanent Address Details</h3>
        <input type="hidden" name="permanent_address_id" value="${permanent_address_id}" />
        <label>Door Number   : </label> <input type="text" name="door_number" required="" value="${door_number}" /><br><br>
        <label>Street        : </label> <input type="text" name="street" required="" value="${street}" /><br><br>
        <label>Village       : </label> <input type="text" name="village" required="" value="${village}" /><br><br>
        <label>District      : </label> <input type="text" name="district" required="" value="${district}" /><br><br>
        <label>State         : </label> <input type="text" name="state" required="" value="${state}" /><br><br>
        <label>Pincode       : </label> <input type="number" name="pincode" required="" value="${pincode}" /><br><br>
        
        <h3>Do you want to add temporary address ?</h3>
        <input type="radio" name="isTemporaryAddressPresent" value="1" required="" />Yes
        <input type="radio" name="isTemporaryAddressPresent" value="0" />No<br><br>
        
        <div class="temporary_address">
        <input type="hidden" name="temporary_address_id" value="${temporary_address_id}" />
            <label>Door Number   : </label> <input type="text" name="temporary_door_number" value="${temporary_door_number}" /><br><br>
            <label>Street        : </label> <input type="text" name="temporary_street" value="${temporary_street}" /><br><br>
            <label>Village       : </label> <input type="text" name="temporary_village" value="${temporary_village}" /><br><br>
            <label>District      : </label> <input type="text" name="temporary_district" value="${temporary_district}" /><br><br>
            <label>State         : </label> <input type="text" name="temporary_state" value="${temporary_state}" /><br><br>
            <label>Pincode       : </label> <input type="number" name="temporary_pincode" value="${temporary_pincode}" /><br><br>
        </div>

        <input type="submit" value="Update Employee" />
    </form><br>
    
    <div>
        <strong>${message}</strong> 
    </div>
    
    <a href="display_employee.jsp">
        <button>Back</button>
    </a>
    
    <a href="index.jsp">
        <button>Home</button>
    </a>
    
    <script>
        $(document).ready(function(){
	        $('.temporary_address').hide();
	        $("input[name='isTemporaryAddressPresent']").click(function(){
		        var radioValue = $("input[name='isTemporaryAddressPresent']:checked").val();
		        if (1 == radioValue) {
			        $('.temporary_address').show();
		        } else if (0 == radioValue) {
			        $('.temporary_address').hide();
		        }
	        });
        });
    </script>
</body>
</html>