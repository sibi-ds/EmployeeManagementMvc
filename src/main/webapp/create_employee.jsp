<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <meta charset="ISO-8859-1">
    <title>Create Employee</title>
</head>
<body>
    <form action="employee?action=create_employee" method="post">
        <h3>Basic Details</h3>
        <label>Name          : </label> <input type="text" name="name" required="" /><br><br>
        <label>Date Of Birth : </label> <input type="date" name="date_of_birth" required="" /><br><br>
        <label>Salary        : </label> <input type="number" name="salary" required="" /><br><br>
        <label>Mobile Number : </label> <input type="number" name="mobile_number" required="" /><br><br>
        
        <h3>Permanent Address Details</h3>
        <label>Door Number   : </label> <input type="text" name="door_number" required="" /><br><br>
        <label>Street        : </label> <input type="text" name="street" required="" /><br><br>
        <label>Village       : </label> <input type="text" name="village" required="" /><br><br>
        <label>District      : </label> <input type="text" name="district" required="" /><br><br>
        <label>State         : </label> <input type="text" name="state" required="" /><br><br>
        <label>Pincode       : </label> <input type="number" name="pincode" required="" /><br><br>
        
        <h3>Do you want to add temporary address ?</h3>
        <input type="radio" name="isTemporaryAddressPresent" value="1" required="" />Yes
        <input type="radio" name="isTemporaryAddressPresent" value="0" />No<br><br>
        
        <div class="temporary_address">
            <label>Door Number   : </label> <input type="text" name="temporary_door_number" /><br><br>
            <label>Street        : </label> <input type="text" name="temporary_street" /><br><br>
            <label>Village       : </label> <input type="text" name="temporary_village" /><br><br>
            <label>District      : </label> <input type="text" name="temporary_district" /><br><br>
            <label>State         : </label> <input type="text" name="temporary_state" /><br><br>
            <label>Pincode       : </label> <input type="number" name="temporary_pincode" /><br><br>
        </div>

        <input type="submit" value="Create Employee" />
    </form><br>
    
    <a href="employee.jsp">
        <button>Back</button>
    </a>
    
    <a href="index.jsp">
        <button>Home</button>
    </a>

    <div>
        <strong>${message}</strong> 
    </div>
    
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