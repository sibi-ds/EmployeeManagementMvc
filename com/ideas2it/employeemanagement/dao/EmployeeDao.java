package com.ideas2it.employeemanagement.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;
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
     * @return    true if stored successful else false 
     */
    boolean createEmployee(Employee employee) throws SQLException, ClassNotFoundException;

    /**
     * used to display details of all employees
     *
     * @return    map consisting employee details
     */
    public Map<Integer, String> getEmployees() throws SQLException, ClassNotFoundException;

    /**
     * request database to
     * give the deails of the employee
     *
     * @param employeeId    which need to be extracted from the database
     *
     * @return    object of employee details
     */
    Employee getEmployee(int employeeId) throws SQLException, ClassNotFoundException;

    /**
     * updates name of an employee
     *
     * @param employeeId    to update certain employee
     * @param name    updated name value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateName(int employeeId, String name) throws SQLException, ClassNotFoundException;

    /**
     * updates Date Of Birth of an employee
     * @param employeeId    to update certain employee
     * @param name    updated date of birth value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateDob(int employeeId, Date dob) throws SQLException, ClassNotFoundException;

    /**
     * updates salary of an employee
     * @param employeeId    to update certain employee
     * @param name    updated salary value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateSalary(int employeeId, float salary) throws SQLException, ClassNotFoundException;

    /**
     * updates mobile number of an employee
     * @param employeeId    to update certain employee
     * @param name    updated mobile number value
     *
     * @return    true if updation successful else false 
     */
    public boolean updateMobileNumber(int employeeId, String mobileNumber) throws SQLException, ClassNotFoundException;

    /**
     * request database to delete the employee details
     *
     * @param  employeeId    for which employee to be removed
     *
     * @return    true if removed successfully else false
     */
    boolean deleteEmployee(int employeeId) throws SQLException, ClassNotFoundException;

    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    returns true if employee present else return false
     */
    boolean isEmployeePresent(int employeeId) throws ClassNotFoundException, SQLException;
}