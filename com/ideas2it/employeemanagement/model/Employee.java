package com.ideas2it.employeemanagement.model;

import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * POJO class used to create object of employee that contains
 * Name , DOB , Salary and Mobile Number
 * 
 * @author  sibi
 * @created 2021-03-03
 */
public class Employee {

    private String employeeName;
    private Date employeeDob;
    private float employeeSalary;
    private String employeeMobileNumber;

    private Employee() {
    }

    public Employee(String name, Date dob, float salary, String mobileNumber) {
        this.employeeName = name;
        this.employeeDob = dob;
        this.employeeSalary = salary;
        this.employeeMobileNumber = mobileNumber;
    }

    public void setName(String name) {
        this.employeeName = name;
    }
    
    public void setDob(Date date) {
        this.employeeDob = date;
    }

    public void setSalary(float salary) {
        this.employeeSalary = salary;
    }

    public void setMobileNumber(String mobileNumber) {
        this.employeeMobileNumber = mobileNumber;
    }

    public String getName() {
        return employeeName;
    }

    public Date getDob() {
        return employeeDob;
    }

    public float getSalary() {
        return employeeSalary;
    }

    public String getMobileNumber() {
        return employeeMobileNumber;
    }

    public String toString() {
        return "\nNAME          : " + employeeName
               + "\nDOB           : " + employeeDob
               + "\nSALARY        : " + employeeSalary
               + "\nMOBILE NUMBER : " + employeeMobileNumber + "\n" ;
    }
}
