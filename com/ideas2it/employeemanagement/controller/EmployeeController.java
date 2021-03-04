package com.ideas2it.employeemanagement.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.service.EmployeeService;

/**
 * this class works as a mediater between view and service
 * by send and receive the requests
 *
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeController {

    private EmployeeService employeeService = new EmployeeService();

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
    public boolean createEmployee(String name, Date dob, float salary, String mobileNumber) {
        return employeeService.createEmployee(name, dob, salary, mobileNumber);
    }

    /**
     * requests service to give
     * all employees details
     *
     * @return    list of employees
     */
    public List<String> getEmployees() {
        return employeeService.displayEmployees();
    }

    /**
     * requests service to give details
     * of an employee as a string
     *
     * @param employeeId    for which details need to be displayed
     *
     * @return    details of an employee
     */
    public String getEmployee(int employeeId) {
        return employeeService.displayEmployee(employeeId);
    }

    /**
     * requests service to update name
     * of an employee
     *
     * @param employeeId    employee ID for which details to be updated
     * @param name          updated value of the employee's name
     *
     * @return    details of an employee
     */
    public void updateName(int employeeId, String name) {
        employeeService.updateName(employeeId, name);
    }

    /**
     * requests service to update
     * Date Of Birth of an employee
     *
     * @param employeeId    employee ID for which details to be updated
     * @param dob           updated value of the employee's date of birth
     *
     * @return    details of an employee
     */
    public void updateDob(int employeeId, Date dob) {
        employeeService.updateDob(employeeId, dob);
    }

    /**
     * requests service to update
     * salary of an employee
     *
     * @param employeeId    employee ID for which details to be updated
     * @param salary        updated value of the employee's salary
     *
     * @return    details of an employee
     */
    public void updateSalary(int employeeId, float salary) {
        employeeService.updateSalary(employeeId, salary);
    }

    /**
     * requests service to update
     * mobile number of an employee
     *
     * @param employeeId      employee ID for which details to be updated
     * @param mobileNumber    updated value of the employee's mobile number
     *
     * @return    details of an employee
     */
    public void updateMobileNumber(int employeeId, String mobileNumber) {
        employeeService.updateMobileNumber(employeeId, mobileNumber);
    }

    /**
     * requests service to remove details
     * of an employee
     *
     * @return    true if deletion successful else false
     */
    public boolean deleteEmployee(int employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }

    /**
     * requests service to give status
     * about existence of an employee
     *
     * @param employeeId    to which existence need to be checked
     *
     * @return    true if employee exist else false
     */
    public boolean isEmployeePresent(int employeeId) {
        return employeeService.isEmployeePresent(employeeId);
    }
}