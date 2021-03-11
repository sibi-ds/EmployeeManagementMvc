package com.ideas2it.employeemanagement.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public interface EmployeeService {

    /**
     * creates employee details object and store it in database
     * 
     * @param name            name of the employee
     * @param dob             Date Of Birth of the employee
     * @param salary          salary of the employee
     * @param mobileNumber    mobile number of the employee
     *
     * @return    true if employee details stored successfully else false
     */
    public boolean insertEmployee(String name, Date dob, float salary, String mobileNumber)
            throws ClassNotFoundException, SQLException;

    /**
     * requests employee address +data base to create employee address object and store it in database
     * 
     * @param addressType   whether current or permanent address
     * @param doorNumber    door number of the employee address
     * @param street        street name
     * @param village       village name
     * @param district      district name
     * @param state         state name
     * @param pincode       postal code
     * 
     * @return    true if employee address stored successfully else false
     */
    public boolean insertAddress(char addressType, String doorNumber, String street
            , String village, String district, String state, int pincode)
            throws ClassNotFoundException, SQLException;

    /**
     * request employee data base to
     * give the deails of all employees
     *
     * @return    map consisting employee details
     */
    public Map<Integer, String> getEmployees() throws ClassNotFoundException, SQLException;

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
    public boolean updateMobileNumber(int employeeId, String mobileNumber) throws ClassNotFoundException, SQLException;

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
