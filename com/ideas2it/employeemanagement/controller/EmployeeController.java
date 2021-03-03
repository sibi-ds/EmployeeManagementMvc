package com.ideas2it.employeemanagement.controller;

import java.util.List;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

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

    public String createEmployee(String name, Date dob, float salary, String mobileNumber) {
        return employeeService.createEmployee(name, dob, salary, mobileNumber);
    }

    public List<String> displayEmployees() {
        return employeeService.displayEmployees();
    }

    public String displayEmployee(int employeeId) {
        return employeeService.displayEmployee(employeeId);
    }

    public void updateName(int employeeId, String name) {
        employeeService.updateName(employeeId, name);
    }

    public void updateDob(int employeeId, Date dob) {
        employeeService.updateDob(employeeId, dob);
    }

    public void updateSalary(int employeeId, float salary) {
        employeeService.updateSalary(employeeId, salary);
    }

    public void updateMobileNumber(int employeeId, String mobileNumber) {
        employeeService.updateMobileNumber(employeeId, mobileNumber);
    }

    public String deleteEmployee(int employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }

    public boolean isEmployeePresent(int employeeId) {
        return employeeService.isEmployeePresent(employeeId);
    }

}