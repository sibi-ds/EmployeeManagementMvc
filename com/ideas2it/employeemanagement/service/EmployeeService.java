package com.ideas2it.employeemanagement.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeService {

    /**
     * creates employee details object and store it in database
     * 
     * @param name            name of the employee
     * @param dob             Date Of Birth of the employee
     * @param salary          salary of the employee
     * @param mobileNumber    mobile number of the employee
     * @param addresses       list containing addresses
     *
     * @return    true if employee details stored successfully else false
     */
    public boolean insertEmployee(String name, Date dob, float salary
            , String mobileNumber, List<List<String>> addresses) throws ClassNotFoundException, SQLException;

    /**
     * request employee data base to
     * give the deails of all employees
     *
     * @return    map consisting employee details
     */
    public List<String> getEmployees() throws ClassNotFoundException, SQLException;

    /**
     * request DAO to
     * give the deails of the employee
     *
     * @param employeeId    which need to be extracted from the database
     *
     * @return    string of employee details
     */
    public String getEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * request DAO to give all addresses of an employee
     *
     * @param employeeId    to update certain employee
     *
     * @return    list of addresses 
     */
    public List<String> getAddresses(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates name of an employee
     *
     * @param employeeId    to update certain employee
     * @param name    updated name value
     *
     * @return    true if updation successful else false
     */
    public boolean updateName(int employeeId, String name) throws ClassNotFoundException, SQLException;

    /**
     * updates Date Of Birth of an employee
     * @param employeeId    to update certain employee
     * @param name    updated date of birth value
     *
     * @return    true if updation successful else false
     */
    public boolean updateDob(int employeeId, Date dob) throws ClassNotFoundException, SQLException;

    /**
     * updates salary of an employee
     * @param employeeId    to update certain employee
     * @param name    updated salary value
     *
     * @return    true if updation successful else false
     */
    public boolean updateSalary(int employeeId, float salary) throws ClassNotFoundException, SQLException;

    /**
     * updates mobile number of an employee
     * @param employeeId    to update certain employee
     * @param name    updated mobile number value
     *
     * @return    true if updation successful else false
     */
    public boolean updateMobileNumber(int employeeId, String mobileNumber)
            throws ClassNotFoundException, SQLException;

    /**
     * updates address of an employee
     *
     * @param employeeId      employee ID for which details to be updated
     * @param addressId     to update certain employee's address
     * @param address       list of string
     *
     * @return    true if updation successful else false
     */
    public boolean updateAddress(int employeeId, int addressId, List<String> address)
            throws ClassNotFoundException, SQLException;

    /**
     * requesting DAO to remove the details of an employee
     *
     * @return    true if deletion successful else false
     */
    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    returns true if employee present else return false
     */
    public boolean isEmployeePresent(int employeeId)
            throws ClassNotFoundException, SQLException;
        
}
