package com.ideas2it.employeemanagement.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.model.Address;

/**
 * POJO class used to create object of employee that contains
 * Name , DOB , Salary and Mobile Number
 * 
 * @author  sibi
 * @created 2021-03-03
 */
public class Employee {

    private int id;
    private String name;
    private Date dateOfBirth;
    private float salary;
    private String mobileNumber;
    private List<Address> addresses;

    private Employee() {
    }

    public Employee(int id, String name, Date dateOfBirth, float salary
            , String mobileNumber, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.mobileNumber = mobileNumber;
        this.addresses = addresses;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void SetAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public float getSalary() {
        return salary;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String toString() {
        return "\nEmployee ID   : " + id
               + "\nName          : " + name
               + "\nDob           : " + dateOfBirth
               + "\nSalary        : " + salary
               + "\nMobile Number : " + mobileNumber + "\n" ;
    }
}
