package com.ideas2it.employeemanagement.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;

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
    boolean insertEmployee(Employee employee) throws SQLException, ClassNotFoundException;

    /**
     * insert address details of employee into the database
     *
     * @return    true if address inserted successfully else false
     */
    public boolean insertAddress(Address address) throws SQLException, ClassNotFoundException;

    /**
     * used to display details of all employees
     *
     * @return    list consisting employee details
     */
    public List<Employee> getEmployees() throws SQLException, ClassNotFoundException;

    /**
     * requests database to give the details of an employee
     *
     * @param employeeId    which need to be retrieved from the database
     *
     * @return    object of employee details
     */
    public Employee getEmployee(int employeeId) throws SQLException, ClassNotFoundException;

    /**
     * request database to give all addresses of an employee
     *
     * @param employeeId    to update certain employee
     *
     * @return    map containing address ID and address details 
     */
    public Map<Integer, Address> getAddresses(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates name of an employee
     *
     * @param employeeId    to update certain employee
     * @param name          updated name value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateName(int employeeId, String name) throws SQLException, ClassNotFoundException;

    /**
     * updates Date Of Birth of an employee
     *
     * @param employeeId    to update certain employee
     * @param name          updated date of birth value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateDob(int employeeId, Date dob) throws SQLException, ClassNotFoundException;

    /**
     * updates salary of an employee
     *
     * @param employeeId    to update certain employee
     * @param name          updated salary value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateSalary(int employeeId, float salary) throws SQLException, ClassNotFoundException;

    /**
     * updates mobile number of an employee
     *
     * @param employeeId    to update certain employee
     * @param name          updated mobile number value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateMobileNumber(int employeeId, String mobileNumber) throws SQLException, ClassNotFoundException;

    /**
     * requests service to add address to an employee
     *
     * @param employeeId      employee ID for which address to be added
     * @param address         employee address details
     *
     * @return    true if address insertion successfully else false
     */
    public boolean addAddress(int employeeId, Address address)
            throws ClassNotFoundException, SQLException;

    /**
     * requests database to update address of an employee
     *
     * @param employeeId      employee ID for which address to be updated
     * @param addressId       address ID for which address to be updated
     * @param address         updated values of the employee's address
     *
     * @return    true if address updation successful else false
     */
    public boolean updateAddressValues(int employeeId, int addressId, Address address)
            throws ClassNotFoundException, SQLException;

    /**
     * requests database to remove an address of an employee
     *
     * @param employeeId      employee ID for which an address to be deleted
     * @param addressId       address ID which address to be deleted
     *
     * @return    true if address deletion successful else false
     */
    public boolean deleteAddress(int employeeId, int addressId) throws ClassNotFoundException, SQLException;

    /**
     * request database to delete the employee details
     *
     * @param  employeeId    for which employee to be removed
     *
     * @return    true if removed successfully else false
     */
    boolean deleteEmployee(int employeeId) throws SQLException, ClassNotFoundException;

    /**
     * requests database to get deleted employees list
     * 
     * @return    list of deleted employees details
     */
    public List<Employee> getDeleted() throws ClassNotFoundException, SQLException;

    /**
     * requests database to restore deleted employee's details
     * 
     * @param employeeId    which need to be restored
     * 
     * @return    true if restoration successful else false
     */
    public boolean restoreDeleted(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    returns true if employee present else return false
     */
    boolean isEmployeePresent(int employeeId) throws ClassNotFoundException, SQLException;
}