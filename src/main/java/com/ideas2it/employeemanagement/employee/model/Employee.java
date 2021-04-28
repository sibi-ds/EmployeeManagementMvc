package com.ideas2it.employeemanagement.employee.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.project.model.Project;

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
    private List<Project> projects;
    private boolean isDeleted;

    public Employee() {}

    public Employee(String name, Date dateOfBirth, float salary
            , String mobileNumber, List<Address> addresses, boolean isDeleted) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.mobileNumber = mobileNumber;
		this.addresses = addresses;
		this.isDeleted = isDeleted;
    }
	
	public Employee(int id, String name, Date dateOfBirth, float salary, String mobileNumber) {
		this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.mobileNumber = mobileNumber;
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

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
	
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public List<Project> getProjects() {
        return projects;
    }
	
    public boolean getIsDeleted() {
        return isDeleted;
    }
	
    public String toString() {
        return "\nEmployee ID   : " + id
               + "\nName          : " + name
               + "\nDob           : " + dateOfBirth
               + "\nSalary        : " + salary
               + "\nMobile Number : " + mobileNumber + "\n" ;
    }
}
