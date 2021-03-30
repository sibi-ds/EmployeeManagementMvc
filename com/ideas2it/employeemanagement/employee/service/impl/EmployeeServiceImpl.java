package com.ideas2it.employeemanagement.employee.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.project.service.ProjectService;

/**
 * this class receives the request from the controller to store the details
 * and send back the details as per the request of the controller
 * 
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDaoImpl = new EmployeeDaoImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(String name, Date dob, float salary, String mobileNumber
            , List<List<String>> addresses) {
        List<Address> employeeAddresses = new ArrayList<Address>();
        addresses.forEach((address) -> {
            employeeAddresses.add(new Address(address.get(0), address.get(1), address.get(2)
                    , address.get(3), address.get(4), address.get(5), address.get(6)));
        });
        return employeeDaoImpl
                .insertEmployee(new Employee(name, dob, salary, mobileNumber, employeeAddresses));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignProject(int employeeId, int projectId) {
        ProjectService projectServiceImpl = new ProjectServiceImpl();
        List<Integer> projectIds = new ArrayList<Integer>();
        projectIds.add(projectId);
        List<Project> projects = projectServiceImpl.getSpecifiedProjects(projectIds);
        Project project = projects.get(0);
        return employeeDaoImpl.assignProject(employeeId, project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unassignProject(int employeeId, int projectId) {
        ProjectService projectServiceImpl = new ProjectServiceImpl();
        List<Integer> projectIds = new ArrayList<Integer>();
        projectIds.add(projectId);
        List<Project> projects = projectServiceImpl.getSpecifiedProjects(projectIds);
        Project project = projects.get(0);
        return employeeDaoImpl.unassignProject(employeeId, project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getEmployees() {
        List<String> employees = new ArrayList<String>();

        employeeDaoImpl.getEmployees().forEach((employee) -> {
            String employeeDetails = employee.toString();
            int addressId = 1;

            for (Address address : employee.getAddresses()) {
                employeeDetails = employeeDetails + "\nSerial Number : " + addressId + address.toString();
                addressId++;
            }

            employeeDetails = employeeDetails + "\n--------------------";

            employees.add(employeeDetails);
        });

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmployeeAndAddresses(int employeeId) {
        Employee employee = employeeDaoImpl.getEmployee(employeeId);
        String employeeDetails = employee.toString();
        ProjectService projectServiceImpl = new ProjectServiceImpl();

        int addressId = 1;

        for (Address address : employee.getAddresses()) {
            employeeDetails = employeeDetails + "\nSerial Number : " + addressId + address.toString();
            addressId++;
        }

        employeeDetails = employeeDetails + "--------------------\n";

        return employeeDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmployeeAndProjects(int employeeId) {
        Employee employee = employeeDaoImpl.getSpecifiedEmployee(employeeId);
        String employeeDetails = employee.toString();
        ProjectService projectServiceImpl = new ProjectServiceImpl();

        List<Integer> projectIds = new ArrayList<Integer>();

        employee.getProjects().forEach((project) -> {
            projectIds.add(project.getId());
        });

        if (projectIds.isEmpty()) {
            employeeDetails = employeeDetails + "\nNo projects assigned\n";
        } else {
            List<Project> projects = projectServiceImpl.getSpecifiedProjects(projectIds);

            employeeDetails = employeeDetails + "\n-----Projects Assigned-----\n";

            for (Project project : projects) {
                employeeDetails = employeeDetails + project.toString();
            }

            employeeDetails = employeeDetails + "--------------------\n";
        }

        return employeeDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds) {
        List<Employee> employees = new ArrayList<Employee>();
        ProjectService projectServiceImpl = new ProjectServiceImpl();

        employeeIds.forEach((employeeId) -> {
            Employee employee = employeeDaoImpl.getSpecifiedEmployee(employeeId);

            if (null != employee) {
                employees.add(employee);
            }
        });

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, String> getAddresses(int employeeId) {
        Map<Integer, Address> employeeAddresses = employeeDaoImpl.getAddresses(employeeId);
        Map<Integer, String> addresses = new LinkedHashMap<Integer, String>();

        int serialNumber = 1;

        for (Integer addressId : employeeAddresses.keySet()) {
            String address = employeeAddresses.get(addressId).toString();
            String addressDetails = "Serial Number : " + serialNumber + address;
            addresses.put(addressId, addressDetails);
            serialNumber++;
        }

        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(int employeeId, String name, Date dob
            , float salary, String mobileNumber) {
        Employee employee = new Employee(employeeId, name, dob, salary, mobileNumber);
        return employeeDaoImpl.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAddress(int employeeId, List<String> address) {
        return employeeDaoImpl.addAddress(employeeId
                , new Address(address.get(0), address.get(1), address.get(2)
                , address.get(3), address.get(4), address.get(5), address.get(6)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAddressValues(int employeeId, int addressId, List<String> address) {
        return employeeDaoImpl.updateAddressValues(employeeId, addressId
                , new Address(addressId, address.get(0), address.get(1), address.get(2)
                , address.get(3), address.get(4), address.get(5), address.get(6)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAddress(int employeeId, int addressId) {
        return employeeDaoImpl.deleteAddress(employeeId, addressId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int employeeId) {
        return employeeDaoImpl.deleteEmployee(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getDeletedEmployees() {
        List<String> deletedEmployees = new ArrayList<String>();

        employeeDaoImpl.getDeletedEmployees().forEach((employee) -> {
            deletedEmployees.add(employee.toString());
        });

        return deletedEmployees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreDeleted(int employeeId) {
        return employeeDaoImpl.restoreDeleted(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) {
        return employeeDaoImpl.isEmployeePresent(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) {
        ProjectService projectServiceImpl = new ProjectServiceImpl();
        return projectServiceImpl.isProjectPresent(projectId);
    }
}