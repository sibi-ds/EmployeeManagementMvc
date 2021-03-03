package com.ideas2it.employeemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ideas2it.employeemanagement.model.Employee;

/**
 * this class receives the request from the controller to store the details
 * and send back the details as per the request of the controller
 * 
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeService {

    private Map<Integer, Employee> employees = new LinkedHashMap<Integer, Employee>();
    
    private int id = 1;

    /*
     * creates employee details object and store it in database
     * 
     * @param name            name of the employee
     * @param dob             Date Of Birth of the employee
     * @param salary          salary of the employee
     * @param mobileNumber    mobile number of the employee
     */
    public String createEmployee(String name, Date dob, float salary, String mobileNumber) {
        employees.put(id, new Employee(id, name, dob, salary, mobileNumber));
        id++;
        return "Employee details stored successfully";
    }

    /*
     * returns employee database as arraylist
     * which consisting string values
     */
    public List<String> displayEmployees() {
        List<String> allEmployees = new ArrayList<String>();
        employees.values().forEach((employeeDetails) -> {
            allEmployees.add(employeeDetails.toString());
        });
        return allEmployees;
    }

    /*
     * searches details of an employee which is mapped
     * with the employeeId which need to be displayed
     *
     * @param employeeId    employeeId to display details of an employee
     *
     * @return     details of the employee as a string
     */
    public String displayEmployee(int employeeId) {        
        return employees.get(employeeId).toString();
    }

    /*
     * updates name of an employee
     */
    public void updateName(int employeeId, String name) {
        employees.get(employeeId).setName(name);
    }

    /*
     * updates Date Of Birth of an employee
     */
    public void updateDob(int employeeId, Date dob) {
        employees.get(employeeId).setDob(dob);
    }

    /*
     * updates salary of an employee
     */
    public void updateSalary(int employeeId, float salary) {
        employees.get(employeeId).setSalary(salary);
    }

    /*
     * updates mobile number of an employee
     */
    public void updateMobileNumber(int employeeId, String mobileNumber) {
        employees.get(employeeId).setMobileNumber(mobileNumber);
    }

    /*
     * searches employee details and delete it from the database
     *
     * @param employeeId    employeeId to delete details of the employee
     */
    public String deleteEmployee(int employeeId) {
        employees.remove(employeeId);
        return "Deletion successful";
    }

    /*
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify it's exixtence
     *
     * @return    returns true if employee present else return false
     */
    public boolean isEmployeePresent(int employeeId) {
        return employees.containsKey(employeeId);
    }

}