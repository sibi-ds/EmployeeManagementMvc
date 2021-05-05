package com.ideas2it.employeemanagement.employee.service;

import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.exceptions.EmployeeManagementException;

/**
 * interface class of the employee service
 *
 * @author sibi
 * @created 2021-03-03
 */
public interface EmployeeService {
    
    /**
     * stores the employee details
     * 
     * @param name         name of the employee
     * @param dob          Date Of Birth of the
     *                     employee
     * @param salary       salary of the employee
     * @param mobileNumber mobile number of the
     *                     employee
     * @param addresses    list containing addresses
     *         
     * @throws EmployeeManagementException
     */
    public void insertEmployee(Employee employee, int status) throws EmployeeManagementException;
    
    /**
     * get the details of all projects
     *
     * @return list containing all project details
     * 
     * @throws EmployeeManagementException
     */
    public List<Project> getProjects() throws EmployeeManagementException;
    
    /**
     * assign project to an employee
     *
     * @param employeeId for which project need to be
     *                   assigned
     * @param projectId  which project to be assigned
     *
     * @return true if project assigned successfully
     *         else false
     * 
     * @throws EmployeeManagementException
     */
    public String assignProjects(int employeeId, String projectIds[]) throws EmployeeManagementException;
    
    /**
     * get the details of all employees
     *
     * @param activeStatus used to get active or deleted employees
     *
     * @return list containing all employees details
     * 
     * @throws EmployeeManagementException
     */
    public List<Employee> getEmployees(boolean activeStatus) throws EmployeeManagementException;
    
    /**
     * get the details of the employee
     *
     * @param employeeId which need to be retrieved
     *                   from the database
     *                  
     * @return list containing employee details
     *                   
     * @throws EmployeeManagementException
     */
    public Employee getEmployee(int employeeId) throws EmployeeManagementException;
    
    /**
     * get the basic details and corresponding
     * addresses of an employee
     *
     * @param employeeId whose details need to be
     *                   retrieved from the database
     *                  
     * @return employee details along with projects
     *                   
     * @throws EmployeeManagementException
     */
    public Employee getEmployeeAndAddresses(int employeeId) throws EmployeeManagementException;
    
    /**
     * get the details of the employees in the list
     *
     * @param employeeIds list of employee Ids
     *
     * @return list containing employees
     * 
     * @throws EmployeeManagementException
     */
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds)
            throws EmployeeManagementException;
    
    /**
     * updates details of an employee
     *
     * @param employeeId   for which details should be
     *                     updated
     * @param name         name of the employee
     * @param dob          Date Of Birth of the
     *                     employee
     * @param salary       salary of the employee
     * @param mobileNumber mobile number of the
     *                     employee
     * @param addresses    list of addresses
     * 
     * @throws EmployeeManagementException
     */
    public void updateEmployee(Employee employee, int status) throws EmployeeManagementException;
    
    /**
     * removes the details of an employee
     *
     * @param employeeId whose details need to be
     *                   deleted
     * 
     * @throws EmployeeManagementException
     */
    public String deleteEmployee(int employeeId) throws EmployeeManagementException;
    
    /**
     * restores the deleted employee
     * 
     * @param employeeId whose details need to be
     *                   restored
     * 
     * @return success or failure message string
     * 
     * @throws EmployeeManagementException
     */
    public String restoreEmployee(int employeeId) throws EmployeeManagementException;
    
    /**
     * checks whether the details of an employee
     * present or not
     *
     * @param employeeId employeeId to verify its
     *                   existence
     *
     * @return returns true if employee present else
     *         return false
     * 
     * @throws EmployeeManagementException
     */
    public boolean isEmployeePresent(int employeeId) throws EmployeeManagementException;
    
    /**
     * checks whether the details of an employee
     * deleted already or not
     *
     * @param employeeId employeeId to verify its
     *                   deletion
     *
     * @return returns true if employee deleted
     *         already else false
     *         
     * @throws EmployeeManagementException
     */
    public boolean isEmployeeDeleted(int employeeId) throws EmployeeManagementException;
    
    /**
     * checks whether project present or not
     *
     * @param projectId whose existence need to be
     *                  checked
     *
     * @return true if project present else false
     * 
     * @throws EmployeeManagementException
     */
    public boolean isProjectPresent(int projectId) throws EmployeeManagementException;
}