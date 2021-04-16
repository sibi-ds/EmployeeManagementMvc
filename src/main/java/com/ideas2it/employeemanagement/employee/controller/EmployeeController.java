package com.ideas2it.employeemanagement.employee.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;

/**
 * this class works as a mediater between view and service
 * by send and receive the requests
 *
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeController extends HttpServlet{

    private EmployeeService employeeServiceImpl = new EmployeeServiceImpl();

    /**
     * used to post the response into the web page
     * 
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	doGet(request, response);
    }

    /**
     * doing the operation as per user's request
     * 
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	String choice = request.getParameter("action");
	    
	switch (choice) {
	    case "create_employee":
	        insertEmployee(request, response);
	        break;
	    case "display_all_employees":
	        getEmployees(request, response);
	        break;
	    case "display_employee":
	        getEmployee(request, response);
	        break;
	    case "delete_employee":
                deleteEmployee(request, response);
	        break;
	    case "restore_employee":
	   	restoreEmployee(request, response);
	       	break;
	    case "assign_project":
	       	assignProject(request, response);
	        break;
	    case "unassign_project":
	       	unassignProject(request, response);
	       	break;
	    case "get_deleted_employees":
	       	getDeletedEmployees(request, response);
	       	break;
	    case "get_employee_details":
	       	getEmployeeDetails(request, response);
	       	break;
	    case "update_employee":
	       	updateEmployee(request, response);
	       	break;
	    default:
	       	System.out.println("no action");
	}
    }

    /**
     * requests service to store employee details in database
     * 
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	Date dob = Date.valueOf(request.getParameter("date_of_birth"));
    	float salary = Float.parseFloat(request.getParameter("salary"));
    	String mobileNumber = request.getParameter("mobile_number");
    	List<List<String>> addresses = new ArrayList<List<String>>();
        
    	List<String> permanentAddress = new ArrayList<String>();
    	permanentAddress.add("permanent");
    	permanentAddress.add(request.getParameter("door_number"));
    	permanentAddress.add(request.getParameter("street"));
    	permanentAddress.add(request.getParameter("village"));
    	permanentAddress.add(request.getParameter("district"));
    	permanentAddress.add(request.getParameter("state"));
    	permanentAddress.add(request.getParameter("pincode"));
    	addresses.add(permanentAddress);
    	
    	if (1 == Integer.parseInt(request.getParameter("isTemporaryAddressPresent"))) {
            List<String> temporaryAddress = new ArrayList<String>();
            temporaryAddress.add("temporary");
            temporaryAddress.add(request.getParameter("temporary_door_number"));
            temporaryAddress.add(request.getParameter("temporary_street"));
            temporaryAddress.add(request.getParameter("temporary_village"));
            temporaryAddress.add(request.getParameter("temporary_district"));
            temporaryAddress.add(request.getParameter("temporary_state"));
            temporaryAddress.add(request.getParameter("temporary_pincode"));
            addresses.add(temporaryAddress);
    	}
    	
        if (employeeServiceImpl.insertEmployee(name, dob, salary, mobileNumber, addresses)) {
            request.setAttribute("message", "Employee Created Successfully");
        } else {
            request.setAttribute("message", "Employee Creation Failure");
        }
        
        request.getRequestDispatcher("/create_employee.jsp").forward(request, response);
    }
    
    /**
     * requests service to assign project to an employee
     *
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void assignProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employee_id"));
    	int projectId = Integer.parseInt(request.getParameter("project_id"));
    	
    	if (isProjectPresent(projectId)) {
    	    if (employeeServiceImpl.assignProject(employeeId, projectId)) {
    		request.setAttribute("message", "Project Assigned Successfully");
    	    } else {
    	    	request.setAttribute("message", "Project Assignation Failure");
    	    }
    	} else {
    	    request.setAttribute("message", "Project Not Present");
    	}
    	
    	request.getRequestDispatcher("/assign_project.jsp").forward(request, response);
    }

    /**
     * requests service to unassign project of an employee
     *
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void unassignProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employee_id"));
    	int projectId = Integer.parseInt(request.getParameter("project_id"));
    	
    	if (employeeServiceImpl.unassignProject(employeeId, projectId)) {
    	    request.setAttribute("message", "Project Unassigned Successfully");
    	} else {
    	    request.setAttribute("message", "Project Unassignation Failure");
    	}
    	
    	request.getRequestDispatcher("/display_employee.jsp").forward(request, response);
    }

    /**
     * requests service to give the details of all employees 
     *
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> employees = employeeServiceImpl.getEmployees();
        
        if (employees.isEmpty()) {
            request.setAttribute("message", "Empty Database");
        } else {
            request.setAttribute("employees", employees);
        }
        
        request.getRequestDispatcher("/display_employees.jsp").forward(request, response);
    }

    /**
     * requests service to give the details of an employee
     *
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void getEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employee_id"));
    	
    	if (isEmployeePresent(employeeId)) {
    	    List<List<String>> employee = employeeServiceImpl.getEmployee(employeeId);
    		
    	    request.setAttribute("employee_id", employee.get(0).get(0));
    	    request.setAttribute("name", employee.get(0).get(1));
    	    request.setAttribute("date_of_birth", employee.get(0).get(2));
    	    request.setAttribute("salary", employee.get(0).get(3));
    	    request.setAttribute("mobile_number", employee.get(0).get(4));
    		
    	    request.setAttribute("addresses", employee.get(1));
    	    request.setAttribute("projects", employee.get(2));
            request.setAttribute("isEmployeePresent", "yes");
    	} else {
    	    request.setAttribute("message", "Employee Not Exist");
    	}
    	
    	request.getRequestDispatcher("/display_employee.jsp").forward(request, response);
    }

    /**
     * requests service to give the basic details and addresses of an employee
     *
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void getEmployeeDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employee_id"));
    	
    	List<List<String>> employee = employeeServiceImpl.getEmployeeAndAddresses(employeeId);

	request.setAttribute("employee_id", employee.get(0).get(0));
	request.setAttribute("name", employee.get(0).get(1));
	request.setAttribute("date_of_birth", employee.get(0).get(2));
	request.setAttribute("salary", employee.get(0).get(3));
	request.setAttribute("mobile_number", employee.get(0).get(4));
		
	request.setAttribute("permanent_address_id", employee.get(1).get(0));
	request.setAttribute("address_type", employee.get(1).get(1));
	request.setAttribute("door_number", employee.get(1).get(2));
	request.setAttribute("street", employee.get(1).get(3));
	request.setAttribute("village", employee.get(1).get(4));
	request.setAttribute("district", employee.get(1).get(5));
	request.setAttribute("state", employee.get(1).get(6));
	request.setAttribute("pincode", employee.get(1).get(7));
    	
	if (2 < employee.size()) {
    	    request.setAttribute("temporary_address_id", employee.get(2).get(0));
    	    request.setAttribute("temporary_address_type", employee.get(2).get(1));
            request.setAttribute("temporary_door_number", employee.get(2).get(2));
    	    request.setAttribute("temporary_street", employee.get(2).get(3));
    	    request.setAttribute("temporary_village", employee.get(2).get(4));
    	    request.setAttribute("temporary_district", employee.get(2).get(5));
    	    request.setAttribute("temporary_state", employee.get(2).get(6));
    	    request.setAttribute("temporary_pincode", employee.get(2).get(7));
        }
		
    	request.getRequestDispatcher("/update_employee.jsp").forward(request, response);
    }

    /**
     * requests service to give addresses of an employee
     *
     * @param employeeId      whose addresses need to be retrieved
     *
     * @return    map containing address IDs and corresponding addresses
     */
/*    public Map<Integer, String> getAddresses(int employeeId) {
        return employeeServiceImpl.getAddresses(employeeId);
    }*/

    /**
     * requests service to update details of an employee
     *
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employee_id"));
    	String name = request.getParameter("name");
    	Date dob = Date.valueOf(request.getParameter("date_of_birth"));
    	float salary = Float.parseFloat(request.getParameter("salary"));
    	String mobileNumber = request.getParameter("mobile_number");
    	List<List<String>> addresses = new ArrayList<List<String>>();
    	
    	List<String> permanentAddress = new ArrayList<String>();
    	permanentAddress.add(request.getParameter("permanent_address_id"));
    	permanentAddress.add("permanent");
    	permanentAddress.add(request.getParameter("door_number"));
    	permanentAddress.add(request.getParameter("street"));
    	permanentAddress.add(request.getParameter("village"));
    	permanentAddress.add(request.getParameter("district"));
    	permanentAddress.add(request.getParameter("state"));
    	permanentAddress.add(request.getParameter("pincode"));

    	addresses.add(permanentAddress);
    	
    	if (1 == Integer.parseInt(request.getParameter("isTemporaryAddressPresent"))) {
            List<String> temporaryAddress = new ArrayList<String>();

            temporaryAddress.add(request.getParameter("temporary_address_id"));
            temporaryAddress.add("temporary");
            temporaryAddress.add(request.getParameter("temporary_door_number"));
            temporaryAddress.add(request.getParameter("temporary_street"));
            temporaryAddress.add(request.getParameter("temporary_village"));
            temporaryAddress.add(request.getParameter("temporary_district"));
            temporaryAddress.add(request.getParameter("temporary_state"));
            temporaryAddress.add(request.getParameter("temporary_pincode"));

            addresses.add(temporaryAddress);
    	}
    	
        if (employeeServiceImpl.updateEmployee(employeeId, name , dob, salary, mobileNumber, addresses)) {
            request.setAttribute("message", "Employee Updated Successfully");
        } else {
            request.setAttribute("message", "Employee Updation Failure");
        }
        
        request.getRequestDispatcher("/update_employee.jsp").forward(request, response);
    }

    /**
     * requests service to update address of an employee
     *
     * @param employeeId      employee ID for which details to be added
     * @param address         employee address details
     *
     * @return    true if address insertion successful else false
     */
/*    public boolean addAddress(int employeeId, List<String> address) {
        return employeeServiceImpl.addAddress(employeeId, address);
    }*/

    /**
     * requests service to update address of an employee
     *
     * @param employeeId      whose address details need to be updated
	 * @param addressIndex    position of the address details in the address list
     * @param addressId       address ID for which details to be updated
     * @param address         updated values of the employee address
     *
     * @return    true if address updation successful else false
     */
/*    public boolean updateAddressValues(int employeeId, int addressIndex
	        , int addressId, List<String> address) {
        return employeeServiceImpl.updateAddressValues(employeeId
		        , addressIndex, addressId, address);
    }*/

    /**
     * requesting service to remove an address of an employee
     *
     * @param employeeId      employee ID for which an address to be deleted
     * @param addressIndex    position of address in the address list
     *
     * @return    true if address deletion successful else false
     */
/*    public boolean deleteAddress(int employeeId, int addressIndex) {
        return employeeServiceImpl.deleteAddress(employeeId, addressIndex);
    }*/

    /**
     * requesting service to remove the details of an employee
     *
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employee_id"));
    	
    	if (isEmployeePresent(employeeId)) {
            if (employeeServiceImpl.deleteEmployee(employeeId)) {
            	request.setAttribute("message", "Employee Deleted Successfully");
            } else {
            	request.setAttribute("message", "Deletion Failure");
            }
    	} else {
    	    request.setAttribute("message", "Employee Not Exist");
    	}

    	request.getRequestDispatcher("/delete_employee.jsp").forward(request, response);
    }

    /**
     * requests service to get deleted employees list
     * 
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void getDeletedEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	List<String> deletedEmployees = employeeServiceImpl.getDeletedEmployees();
    	
    	if (deletedEmployees.isEmpty()) {
    		request.setAttribute("message", "No Deleted Employees");
    	} else {
    		request.setAttribute("employees", deletedEmployees);
    	}
    	
    	request.getRequestDispatcher("/restore_employee.jsp").forward(request, response);
    }

    /**
     * requests service to restore deleted employee's details
     * 
     * @param request     request from the user
     * @param response    response to the request
     * 
     * @throws IOException 
     * @throws ServletException 
     */
    private void restoreEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employee_id"));

        if (isEmployeePresent(employeeId)) {
            request.setAttribute("message", "Employee Already Present");
        } else {
	    if (!isEmployeeDeleted(employeeId)) {
                request.setAttribute("message", "Employee Never Exist");
            } else if (employeeServiceImpl.restoreEmployee(employeeId)) {
                request.setAttribute("message", "Employee Restored Successfully");
            } else {
                request.setAttribute("message", "Restoration Failure");
            }
        }

    	request.getRequestDispatcher("/restore_employee.jsp").forward(request, response);
    }

    /**
     * checks whether an employee present or not
     *
     * @param employeeId    Employee ID to verify its exixtence
     *
     * @return    true if employee present else false
     */
    private boolean isEmployeePresent(int employeeId) {
        return employeeServiceImpl.isEmployeePresent(employeeId);
    }

    /**
     * checks whether an employee already deleted or not
     *
     * @param employeeId    Employee ID to verify its deletion
     *
     * @return    true if employee deleted already else false
     */
    private boolean isEmployeeDeleted(int employeeId) {
        return employeeServiceImpl.isEmployeeDeleted(employeeId);
    }
    
    /**
     * requests service to check whether project present or not
     *
     * @param projectId    Project ID to verify its exixtence
     *
     * @return    true if project present else false
     */
    private boolean isProjectPresent(int projectId) {
        return employeeServiceImpl.isProjectPresent(projectId);
    }
}