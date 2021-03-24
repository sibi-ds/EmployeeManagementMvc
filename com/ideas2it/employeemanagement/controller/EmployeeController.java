package com.ideas2it.employeemanagement.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.service.EmployeeService;
import com.ideas2it.employeemanagement.service.impl.EmployeeServiceImpl;

/**
 * this class works as a mediater between view and service
 * by send and receive the requests
 *
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeController {

    private EmployeeService employeeServiceImpl = new EmployeeServiceImpl();

    /**
     * requests service to create employee details object and store it in database
     * 
     * @param name            name of the employee
     * @param dob             Date Of Birth of the employee
     * @param salary          salary of the employee
     * @param mobileNumber    mobile number of the employee    
     * @param addresses       list containing addresses
     * 
     * @return    true if employee details stored successfully else false
     */
    public boolean insertEmployee(String name, Date dob, float salary, String mobileNumber
            , List<List<String>> addresses) {
        return employeeServiceImpl.insertEmployee(name, dob, salary, mobileNumber, addresses);
    }

    /**
     * requests service to give the details of all employees 
     *
     * @return    list consisting employee details
     */
    public List<String> getEmployees() {
        return employeeServiceImpl.getEmployees();
    }

    /**
     * requests service to give the details of an employee
     *
     * @param employeeId    which need to be extracted from the database
     *
     * @return    string containing employee details
     */
    public String getEmployee(int employeeId) {
        return employeeServiceImpl.getEmployee(employeeId);
    }

    /**
     * requests service to give addresses of an employee
     *
     * @param employeeId      employee ID for which details to be updated
     *
     * @return    map containing address ID and address details
     */
    public Map<Integer, String> getAddresses(int employeeId) {
        return employeeServiceImpl.getAddresses(employeeId);
    }

    /**
     * requests service to update name of an employee
     *
     * @param employeeId    employee ID for which details to be updated
     * @param employeeBasicDetails    updated basic details of an employee
     *
     * @return    true if updation successful else false
     */
    public boolean updateEmployee(int employeeId, List<String> employeeBasicDetails) {
        return employeeServiceImpl.updateEmployee(employeeId, employeeBasicDetails);
    }

    /**
     * requests service to update address of an employee
     *
     * @param employeeId      employee ID for which details to be added
     * @param address         employee address details
     *
     * @return    true if address insertion successful else false
     */
    public boolean addAddress(int employeeId, List<String> address) {
        return employeeServiceImpl.addAddress(employeeId, address);
    }
        

    /**
     * requests service to update address of an employee
     *
     * @param employeeId      employee ID for which details to be updated
     * @param addressId       address ID for which details to be updated
     * @param address         updated values of the employee address
     *
     * @return    true if address updation successful else false
     */
    public boolean updateAddressValues(int employeeId, int addressId, List<String> address) {
        return employeeServiceImpl.updateAddressValues(employeeId, addressId, address);
    }

    /**
     * requesting service to remove an address of an employee
     *
     * @param employeeId      employee ID for which an address to be deleted
     * @param addressId       address ID for which details to be deleted
     *
     * @return    true if address deletion successful else false
     */
    public boolean deleteAddress(int employeeId, int addressId) {
        return employeeServiceImpl.deleteAddress(employeeId, addressId);
    }

    /**
     * requesting service to remove the details of an employee
     *
     * @param employeeId      employee ID for which details to be deleted
     *
     * @return    true if deletion successful else false
     */
    public boolean deleteEmployee(int employeeId) {
        return employeeServiceImpl.deleteEmployee(employeeId);
    }

    /**
     * requests service to restore get deleted employees list
     * 
     * @return    list of deleted employee IDs
     */
    public List<String> getDeletedEmployees() {
        return employeeServiceImpl.getDeletedEmployees();
    }

    /**
     * requests service to restore deleted employee's details
     * 
     * @param employeeId    which need to be restored
     * 
     * @return    true if restoration successful else false
     */
    public boolean restoreDeleted(int employeeId) {
        return employeeServiceImpl.restoreDeleted(employeeId);
    }

    /**
     * checks whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    returns true if employee present else return false
     */
    public boolean isEmployeePresent(int employeeId) {
        return employeeServiceImpl.isEmployeePresent(employeeId);
    }
}