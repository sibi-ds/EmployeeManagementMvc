package com.ideas2it.employeemanagement.employee.dao;

import java.util.List;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.exceptions.EmployeeManagementException;

/**
 * this interface class is used as DAO layer to
 * perform operations on employee details
 * 
 * @author sibi
 * @created 2021-03-08
 */
public interface EmployeeDao {
    
    /**
     * stores the employee details
     *
     * @param employee object of employee details
     *         
     * @throws EmployeeManagementException
     */
    public void insertEmployee(Employee employee)
            throws EmployeeManagementException;
    
    /**
     * get details of all employees along with their
     * addresses
     *
     * @param activeStatus used to get active or deleted employees
     *
     * @return list consisting employee details
     * 
     * @throws EmployeeManagementException
     */
    public List<Employee> getEmployees(boolean activeStatus)
            throws EmployeeManagementException;
    
    /**
     * get the details of an employee
     *
     * @param employeeId whose details need to be
     *                   retrieved from the database
     *                  
     * @return employee details along with addresses
     *                   
     * @throws EmployeeManagementException
     */
    public Employee getEmployee(int employeeId)
            throws EmployeeManagementException;
    
    /**
     * get the details of the employee
     *
     * @param employeeId whose details need to be
     *                   retrieved from the database
     *                  
     * @return employee details along with projects
     *                   
     * @throws EmployeeManagementException
     */
    public Employee getEmployeeAndAddresses(int employeeId)
            throws EmployeeManagementException;
    
    /**
     * get the details of the employee
     *
     * @param employeeId whose details need to be
     *                   retrieved from the database
     *                  
     * @return employee details along with projects
     *                   
     * @throws EmployeeManagementException
     */
    public Employee getEmployeeAndProjects(int employeeId)
            throws EmployeeManagementException;
    
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
     * @param employee employee with updated details
     * 
     * @throws EmployeeManagementException
     */
    public void updateEmployee(Employee employee);
    
    /**
     * checks whether the details of an employee
     * present or not
     *
     * @param employeeId employeeId to verify its
     *                   existence
     *
     * @return true if employee present else false
     * 
     * @throws EmployeeManagementException
     */
    boolean isEmployeePresent(int employeeId)
            throws EmployeeManagementException;
    
    /**
     * checks whether the details of an employee
     * deleted already or not
     *
     * @param employeeId employeeId to verify its
     *                   deletion
     *
     * @return true if employee deleted already else
     *         false
     * 
     * @throws EmployeeManagementException
     */
    boolean isEmployeeDeleted(int employeeId)
            throws EmployeeManagementException;
}