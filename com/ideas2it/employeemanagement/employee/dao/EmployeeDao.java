package com.ideas2it.employeemanagement.employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
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
    boolean insertEmployee(Employee employee);

    /**
     * insert address details of employee into the database
     *
     * @param  employeeId    employee ID for which basic details inserted
     * @param  addresses     list of addresses
     * @param  connection    connection object
     *
     * @return    true if address inserted successfully else false
     */
    public boolean insertAddresses(int employeeId, List<Address> addresses, Connection connection);

    /**
     * requests database to assign project to an employee
     *
     * @param employeeId    for which projects to be assigned
     * @param project       project object
     *
     * @return    true if project assigned successfully else false
     */
    public boolean assignProject(int employeeId, Project project);

    /**
     * requests details of all employees along with thier addresses
     *
     * @return    list consisting employee details
     */
    public List<Employee> getEmployees();

    /**
     * requests database to give the details of an employee with corresponding addresses
     *
     * @param employeeId    which need to be retrieved from the database
     *
     * @return    object of employee details
     */
    public Employee getEmployee(int employeeId);

    /**
     * requests database to give the details of an employee
     *
     * @param employeeId    whose details with corresponding projects assigned to be retrieved
     *
     * @return    object of employee details
     */
    public Employee getSpecifiedEmployee(int employeeId);

    /**
     * request database to give all addresses of an employee
     *
     * @param employeeId    whose addresses need to be retrieved
     *
     * @return    map containing address ID and addresses
     */
    public Map<Integer, Address> getAddresses(int employeeId);

    /**
     * updates details of an employee
     *
     * @param employee      employee with updated details
     *
     * @return    true if updation successful else false 
     */
    public boolean updateEmployee(Employee employee);

    /**
     * used to add address to an employee
     *
     * @param employeeId      employee ID for which address to be added
     * @param address         employee address details
     *
     * @return    true if address insertion successful else false
     */
    public boolean addAddress(int employeeId, Address address);

    /**
     * used to update address of an employee
     *
     * @param employeeId      employee ID for which address to be updated
     * @param addressId       address ID for which address to be updated
     * @param address         updated values of the employee's address
     *
     * @return    true if address updation successful else false
     */
    public boolean updateAddressValues(int employeeId, int addressId, Address address);

    /**
     * requests database to remove an address of an employee
     *
     * @param employeeId      employee ID for which an address to be deleted
     * @param addressId       address ID which address to be deleted
     *
     * @return    true if address deletion successful else false
     */
    public boolean deleteAddress(int employeeId, int addressId);

    /**
     * request database to delete the employee details
     *
     * @param employeeId    for which employee to be removed
     *
     * @return    true if employee removed successfully else false
     */
    boolean deleteEmployee(int employeeId);

    /**
     * requests database to get deleted employees list
     * 
     * @return    list of deleted employees
     */
    public List<Employee> getDeletedEmployees();

    /**
     * requests database to restore deleted employee's details
     * 
     * @param employeeId    which need to be restored
     * 
     * @return    true if restoration successful else false
     */
    public boolean restoreDeleted(int employeeId);

    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    true if employee present else false
     */
    boolean isEmployeePresent(int employeeId);
}