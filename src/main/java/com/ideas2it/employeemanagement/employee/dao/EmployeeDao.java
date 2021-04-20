package com.ideas2it.employeemanagement.employee.dao;

import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;

/**
 * this interface class is used as DAO layer
 * to perform operations on employee deatils
 * 
 * @author  sibi
 * @created 2021-03-08
 */
public interface EmployeeDao {

    /**
     * stores the employee details
     *
     * @param  employee    object of employee details
     *
     * @return    true if employee details stored successfully else false 
     */
    public boolean insertEmployee(Employee employee);

    /**
     * get details of all employees along with their addresses
     *
     * @return    list consisting employee details
     */
    public List<Employee> getEmployees();

    /**
     * get the details of an employee
     *
     * @param employeeId    whose details need to be retrieved from the database
     *
     * @return    employee details along with addresses
     */
    public Employee getEmployee(int employeeId);

    /**
     * get the details of the employee
     *
     * @param employeeId    whose details need to be retrieved from the database
     *
     * @return    employee details along with projects
     */
    public Employee getEmployeeAndAddresses(int employeeId);
    
    /**
     * get the details of the employee
     *
     * @param employeeId    whose details need to be retrieved from the database
     *
     * @return    employee details along with projects
     */
    public Employee getEmployeeAndProjects(int employeeId);
	
    /**
     * get the details of an employee
     *
     * @param employeeId    whose details need to be retrieved
     *
     * @return    employee details
     */
    public Employee getSpecifiedEmployee(int employeeId);

    /**
     * updates details of an employee
     *
     * @param employee    employee with updated details
     *
     * @return    true if updation successful else false 
     */
    public boolean updateEmployee(Employee employee);

    /**
     * get the deleted employees list
     * 
     * @return    list of deleted employees
     */
    public List<Employee> getDeletedEmployees();
	
    /**
     * checks whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify its exixtence
     *
     * @return    true if employee present else false
     */
    boolean isEmployeePresent(int employeeId);
    
    /**
     * checks whether the details of an employee deleted already or not
     *
     * @param employeeId    employeeId to verify its deletion
     *
     * @return    true if employee deleted already else false
     */
    boolean isEmployeeDeleted(int employeeId);
}