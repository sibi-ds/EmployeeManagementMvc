package com.ideas2it.employeemanagement.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ideas2it.employeemanagement.service.impl.EmployeeServiceImpl;

/**
 * this class works as a mediater between view and service
 * by send and receive the requests
 *
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeController {

    private EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl();

    /**
     * requests service to create employee details object and store it in database
     * 
     * @param name            name of the employee
     * @param dob             Date Of Birth of the employee
     * @param salary          salary of the employee
     * @param mobileNumber    mobile number of the employee
     *
     * @return    true if employee details stored successfully else false
     */
    public boolean createEmployee(String name, Date dob, float salary, String mobileNumber)
            throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.createEmployee(name, dob, salary, mobileNumber);
    }

    /**
     * request service to
     * give the deails of all employeea
     *
     * @return    map consisting employee details
     */
    public Map<Integer, String> getEmployees() throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.getEmployees();
    }

    /**
     * request service to
     * give the deails of the employee
     *
     * @param employeeId    which need to be extracted from the database
     *
     * @return    string of employee details
     */
    public String getEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.getEmployee(employeeId);
    }

    /**
     * requests service to update name
     * of an employee
     *
     * @param employeeId    employee ID for which details to be updated
     * @param name          updated value of the employee's name
     *
     * @return    true if updation successful else false
     */
    public boolean updateName(int employeeId, String name) throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.updateName(employeeId, name);
    }

    /**
     * requests service to update
     * Date Of Birth of an employee
     *
     * @param employeeId    employee ID for which details to be updated
     * @param dob           updated value of the employee's date of birth
     *
     * @return    true if updation successful else false
     */
    public boolean updateDob(int employeeId, Date dob) throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.updateDob(employeeId, dob);
    }

    /**
     * requests service to update
     * salary of an employee
     *
     * @param employeeId    employee ID for which details to be updated
     * @param salary        updated value of the employee's salary
     *
     * @return    true if updation successful else false
     */
    public boolean updateSalary(int employeeId, float salary) throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.updateSalary(employeeId, salary);
    }

    /**
     * requests service to update
     * mobile number of an employee
     *
     * @param employeeId      employee ID for which details to be updated
     * @param mobileNumber    updated value of the employee's mobile number
     *
     * @return    true if updation successful else false
     */
    public boolean updateMobileNumber(int employeeId, String mobileNumber) throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.updateMobileNumber(employeeId, mobileNumber);
    }

    /**
     * requesting service to remove the details of an employee
     *
     * @return    true if deletion successful else false
     */
    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.deleteEmployee(employeeId);
    }

    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    returns true if employee present else return false
     */
    public boolean isEmployeePresent(int employeeId)
            throws ClassNotFoundException, SQLException {
        return employeeServiceImpl.isEmployeePresent(employeeId);
    }
}