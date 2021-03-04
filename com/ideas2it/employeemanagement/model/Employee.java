package com.ideas2it.employeemanagement.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * POJO class used to create object of employee that contains
 * Name , DOB , Salary and Mobile Number
 * 
 * @author  sibi
 * @created 2021-03-03
 */
public class Employee {

    private int employeeId;
    private String employeeName;
    private Date employeeDob;
    private float employeeSalary;
    private String employeeMobileNumber;

    private Employee() {
    }

    public Employee(int id, String name, Date dob, float salary, String mobileNumber) {
        this.employeeId = id;
        this.employeeName = name;
        this.employeeDob = dob;
        this.employeeSalary = salary;
        this.employeeMobileNumber = mobileNumber;
    }

    public void setId (int id) {
        this.employeeId = id;
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

    public int getId () {
        return employeeId;
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
        return "ID            : " + employeeId
               + "\nNAME          : " + employeeName
               + "\nDOB           : " + new SimpleDateFormat("dd/MM/yyyy").format(employeeDob)
               + "\nSALARY        : " + employeeSalary
               + "\nMOBILE NUMBER : " + employeeMobileNumber + "\n" ;
    }
}
