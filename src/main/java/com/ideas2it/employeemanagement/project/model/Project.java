package com.ideas2it.employeemanagement.project.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;

/**
 * POJO class used to create object of project that contains
 * Project ID , Project Name , Start Dtae, End date
 * 
 * @author  sibi
 * @created 2021-03-24
 */
public class Project {

    private int id;
    private String title;
    private String clientName;
    private int managerId;
    private Date startDate;
    private Date endDate;
    private List<Employee> employees;
    private boolean isDeleted;

    private Project() {
    }

    public Project(String title, String clientName, int managerId
            , Date startDate, Date endDate, boolean isDeleted) {
        this.title = title;
        this.clientName = clientName;
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDeleted = isDeleted;
    }

    public Project(int id, String title, String clientName, int managerId
            , Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.clientName = clientName;
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project(int id, String title, String clientName, int managerId
            , Date startDate, Date endDate, List<Employee> employees, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.clientName = clientName;
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employees = employees;
        this.isDeleted = isDeleted;
    }

    public Project(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
	
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
	
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getClientName() {
        return clientName;
    }

    public int getManagerId() {
        return managerId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }
	
    public String toString() {
        return "\nProject ID    : " + id
               + "\nProject Title : " + title
               + "\nClient Name   : " + clientName
               + "\nManager ID    : " + managerId
               + "\nStart Date    : " + startDate
               + "\nEnd Date      : " + endDate + "\n";
    }
}