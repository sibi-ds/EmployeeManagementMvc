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
     * used to store details in employee database
     *
     * @param  employee    object of employee details
     *
     * @return    true if employee details stored successfully else false 
     */
    public boolean insertEmployee(Employee employee);

    /**
     * requests details of all employees along with thier addresses
     *
     * @return    list consisting employee details
     */
    public List<Employee> getEmployees();

    /**
     * requests database to give the details of an employee
     *
     * @param employeeId    whose details need to be retrieved from the database
     *
     * @return    employee details along with addresses
     */
    public Employee getEmployee(int employeeId);

    /**
     * requests database to give the deails of the employee
     *
     * @param employeeId    whose details need to be retrieved from the database
     *
     * @return    employee details along with projects
     */
    public Employee getEmployeeAndProjects(int employeeId);
	
    /**
     * requests database to give the details of an employee
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
     * requests database to give deleted employees list
     * 
     * @return    list of deleted employees
     */
    public List<Employee> getDeletedEmployees();
	
    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify its exixtence
     *
     * @return    true if employee present else false
     */
    boolean isEmployeePresent(int employeeId);
}