package com.ideas2it.employeemanagement.employee.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;
import com.ideas2it.exceptions.EmployeeManagementException;
import com.ideas2it.loggers.EmployeeManagementLogger;

/**
 * this class works as a mediater between view and
 * service by send and receive the requests
 *
 * @author sibi
 * @created 2021-03-03
 */
public class EmployeeController extends HttpServlet {
    
    private EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeController.class);
    
    /**
     * process the user request and gives the
     * corresponding response
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String choice = request.getParameter("action");
        
        try {
            switch (choice) {
                case "create_employee":
                    insertEmployee(request, response);
                    break;
                case "update_employee":
                    updateEmployee(request, response);
                    break;
                case "display_employee":
                    getEmployee(request, response);
                    break;
                case "assign_project":
                    assignProject(request, response);
                    break;
                case "delete_employee":
                    deleteEmployee(request, response);
                    break;
                case "restore_employee":
                    restoreEmployee(request, response);
                    break;
                default:
                    errorPage(request, response);
            }
        } catch (ServletException | IOException e) {
            logger.logError(e);
        }
    }
    
    /**
     * process the user request and gives the
     * corresponding response
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String choice = request.getParameter("action");
        
        try {
            switch (choice) {
                case "delete_employee":
                    deleteEmployee(request, response);
                    break;
                case "unassign_project":
                    unassignProject(request, response);
                    break;
                case "restore_employee":
                    restoreEmployee(request, response);
                    break;
                case "display_all_employees":
                    getEmployees(request, response);
                    break;
                case "get_deleted_employees":
                    getDeletedEmployees(request, response);
                    break;
                case "get_employee_details":
                    getEmployeeDetails(request, response);
                    break;
                default:
                    errorPage(request, response);
            }
        } catch (ServletException | IOException e) {
            logger.logError(e);
        }
    }
    
    /**
     * redirects to error page if error occurs
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void errorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("error_page.jsp");
    }
    
    /**
     * stores employee details in database
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void insertEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        
        try {
            employeeServiceImpl.insertEmployee(name, dob, salary, mobileNumber, addresses);
            request.setAttribute("message", "Employee Created Successfully");
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/employee.jsp").forward(request, response);
        }
    }
    
    /**
     * assign project to an employee
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void assignProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        
        try {
            if (employeeServiceImpl.isProjectPresent(projectId)) {
                String message = employeeServiceImpl.assignProject(employeeId, projectId);
                request.setAttribute("message", message);
            } else {
                request.setAttribute("message", "Project Not Present");
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/assign_project.jsp").forward(request, response);
        }
    }
    
    /**
     * unassign project of an employee
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void unassignProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        
        try {
            String message = employeeServiceImpl.unassignProject(employeeId, projectId);
            request.setAttribute("message", message);
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/display_employee.jsp").forward(request, response);
        }
    }
    
    /**
     * get the details of all employees
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getEmployees(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            List<Employee> employees = employeeServiceImpl.getEmployees();
            
            if (employees.isEmpty()) {
                request.setAttribute("message", "Empty Database");
            } else {
                request.setAttribute("employees", employees);
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/display_employees.jsp").forward(request, response);
        }
    }
    
    /**
     * get the details of an employee
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        
        try {
            if (employeeServiceImpl.isEmployeePresent(employeeId)) {
                Employee employee = employeeServiceImpl.getEmployee(employeeId);
                request.setAttribute("employee", employee);
            } else {
                request.setAttribute("message", "Employee Not Present");
            }
            
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        }
        
        request.getRequestDispatcher("/display_employee.jsp").forward(request, response);
    }
    
    /**
     * get the basic details and addresses of an
     * employee
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getEmployeeDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        
        try {
            Employee employee = employeeServiceImpl.getEmployeeAndAddresses(employeeId);
            
            request.setAttribute("employee", employee);
            request.setAttribute("permanentAddress", employee.getAddresses().get(0));
            
            if (1 < employee.getAddresses().size()) {
                request.setAttribute("temporaryAddress", employee.getAddresses().get(1));
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/create_employee.jsp").forward(request, response);
        }
    }
    
    /**
     * get addresses of an employee
     *
     * @param employeeId whose addresses need to be
     *                   retrieved
     *
     * @return map containing address IDs and
     *         corresponding addresses
     */
//    public Map<Integer, String> getAddresses(int employeeId) {
//        return employeeServiceImpl.getAddresses(employeeId);
//    }
    
    /**
     * updates details of an employee
     *
     * @param request  request from the user
     * @param response response to the request
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
        
        try {
            employeeServiceImpl
                    .updateEmployee(employeeId, name, dob, salary, mobileNumber, addresses);
            request.setAttribute("message", "Employee Updated Successfully");
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/display_employee.jsp").forward(request, response);
        }
    }
    
    /**
     * updates address of an employee
     *
     * @param employeeId employee ID for which details
     *                   to be added
     * @param address    employee address details
     *                   
     * @return true if address insertion successful
     *         else false
     */
//    public boolean addAddress(int employeeId, List<String> address) {
//        return employeeServiceImpl.addAddress(employeeId, address);
//    }
    
    /**
     * updates address of an employee
     *
     * @param employeeId   whose address details need
     *                     to be updated
     * @param addressIndex position of the address
     *                     details in the address list
     * @param addressId    address ID for which
     *                     details to be updated
     * @param address      updated values of the
     *                     employee address
     *
     * @return true if address updation successful
     *         else false
     */
//    public boolean updateAddressValues(int employeeId, int addressIndex, int addressId, List<String> address) {
//        return employeeServiceImpl.updateAddressValues(employeeId, addressIndex, addressId, address);
//    }
    
    /**
     * removes an address of an employee
     *
     * @param employeeId   employee ID for which an
     *                     address to be deleted
     * @param addressIndex position of address in the
     *                     address list
     *
     * @return true if address deletion successful
     *         else false
     */
//    public boolean deleteAddress(int employeeId, int addressIndex) {
//        return employeeServiceImpl.deleteAddress(employeeId, addressIndex);
//    }
    
    /**
     * removes the details of an employee
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        
        try {
            if (employeeServiceImpl.isEmployeePresent(employeeId)) {
                employeeServiceImpl.deleteEmployee(employeeId);
                request.setAttribute("message", "Deletion Successful");
            } else {
                request.setAttribute("message", "Employee Not Present");
            }
            
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/delete_employee.jsp").forward(request, response);
        }
    }
    
    /**
     * get deleted employees list
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getDeletedEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Employee> deletedEmployees = employeeServiceImpl.getDeletedEmployees();
            
            if (deletedEmployees.isEmpty()) {
                request.setAttribute("message", "No Deleted Employees");
            } else {
                request.setAttribute("employees", deletedEmployees);
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("employees", e.getMessage());
        } finally {
            request.getRequestDispatcher("/restore_employee.jsp").forward(request, response);
        }
    }
    
    /**
     * restores deleted employee's details
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void restoreEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        
        try {
            if (employeeServiceImpl.isEmployeeDeleted(employeeId)) {
                employeeServiceImpl.restoreEmployee(employeeId);
                request.setAttribute("message", "Restoration Successful");
            } else {
                request.setAttribute("message", "Employee ID Not In The Restoration List");
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/restore_employee.jsp").forward(request, response);
        }
    }
}