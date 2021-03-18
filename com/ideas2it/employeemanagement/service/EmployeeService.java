package com.ideas2it.employeemanagement.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
     * requests DAO to give the deails of all employees
     *
     * @return    list consisting employees details
     */
    public List<String> getEmployees() throws ClassNotFoundException, SQLException;

    /**
     * requests DAO to give the deails of the employee
     *
     * @param employeeId    which need to be retrieved from the database
     *
     * @return    string of employee's details
     */
    public String getEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * requests DAO to give all addresses of an employee
     *
     * @param employeeId    to update certain employee
     *
     * @return    map containing address IDs and addresses
     */
    public Map<Integer, String> getAddresses(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates name of an employee
     *
     * @param employeeId    which need to be updated
     * @param name          updated name value
     *
     * @return    true if updation successful else false
     */
    public boolean updateName(int employeeId, String name) throws ClassNotFoundException, SQLException;

    /**
     * updates Date Of Birth of an employee
     *
     * @param employeeId    which need to be updated
     * @param name          updated date of birth value
     *
     * @return    true if updation successful else false
     */
    public boolean updateDob(int employeeId, Date dob) throws ClassNotFoundException, SQLException;

    /**
     * updates salary of an employee
     *
     * @param employeeId    which need to be updated
     * @param name          updated salary value
     *
     * @return    true if updation successful else false
     */
    public boolean updateSalary(int employeeId, float salary) throws ClassNotFoundException, SQLException;

    /**
     * updates mobile number of an employee
     *
     * @param employeeId    which need to be updated
     * @param name          updated mobile number value
     *
     * @return    true if updation successful else false
     */
    public boolean updateMobileNumber(int employeeId, String mobileNumber)
            throws ClassNotFoundException, SQLException;

    /**
     * requests DAO to add address to an employee
     *
     * @param employeeId      employee ID for which details to be added
     * @param address         employee address details
     *
     * @return    true if address insertion successful else false
     */
    public boolean addAddress(int employeeId, List<String> address)
            throws ClassNotFoundException, SQLException;

    /**
     * requests DAO to update address of an employee
     *
     * @param employeeId      employee ID for which details to be updated
     * @param addressId       address ID for which details to be updated
     * @param address         updated values of the employee address
     *
     * @return    true if address updation successful else false
     */
    public boolean updateAddressValues(int employeeId, int addressId, List<String> address)
            throws ClassNotFoundException, SQLException;

    /**
     * requests DAO to remove an address of an employee
     *
     * @param employeeId      employee ID for which an address to be deleted
     * @param addressId       address ID for which details to be deleted
     *
     * @return    true if address deletion successful else false
     */
    public boolean deleteAddress(int employeeId, int addressId) throws ClassNotFoundException, SQLException;

    /**
     * requesting DAO to remove the details of an employee
     *
     * @return    true if deletion successful else false
     */
    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * requests DAO to restore get deleted employees list
     * 
     * @return    list of deleted employees
     */
    public List<String> getDeleted() throws ClassNotFoundException, SQLException;

    /**
     * requests DAO to restore deleted employee details
     * 
     * @param employeeId    which need to be restored
     * 
     * @return    true if restoration successful else false
     */
    public boolean restoreDeleted(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * checks whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    returns true if employee present else return false
     */
    public boolean isEmployeePresent(int employeeId)
            throws ClassNotFoundException, SQLException; 
}
