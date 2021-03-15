package com.ideas2it.employeemanagement.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.service.EmployeeService;
import com.ideas2it.sessionfactoy.DatabaseConnection;

/**
 * this class receives the request from the controller to store the details
 * and send back the details as per the request of the controller
 * 
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(String name, Date dob, float salary, String mobileNumber
            , List<List<String>> addresses) throws ClassNotFoundException, SQLException {
        List<Address> employeeAddresses = new ArrayList<Address>();
        addresses.forEach((address) -> {
            employeeAddresses.add(new Address(0, address.get(0), address.get(1), address.get(2)
                    , address.get(3), address.get(4), address.get(5), address.get(6)));
        });
        return employeeDaoImpl
                .insertEmployee(new Employee(0, name, dob, salary, mobileNumber, employeeAddresses));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getEmployees() throws ClassNotFoundException, SQLException {
        List<String> employees = new ArrayList<String>();

        for (Employee employee : employeeDaoImpl.getEmployees()) {
            String employeeDetails = employee.toString();

            for (Address address : employee.getAddresses()) {
                employeeDetails = employeeDetails + address.toString();
            }

            employeeDetails = employeeDetails + "--------------------\n";

            employees.add(employeeDetails);
        }

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        Employee employee = employeeDaoImpl.getEmployee(employeeId);
        String employeeDetails = employee.toString();

        for (Address address : employee.getAddresses()) {
            employeeDetails = employeeDetails + address.toString();
        }

        employeeDetails = employeeDetails + "--------------------\n";

        return employeeDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAddresses(int employeeId) throws ClassNotFoundException, SQLException {
        List<String> addresses = new ArrayList<String>();

        employeeDaoImpl.getAddresses(employeeId).forEach((address) -> {
            addresses.add(address.toString());
        });

        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateName(int employeeId, String name) throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.updateName(employeeId, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateDob(int employeeId, Date dob) throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.updateDob(employeeId, dob);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateSalary(int employeeId, float salary) throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.updateSalary(employeeId, salary);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMobileNumber(int employeeId, String mobileNumber)
            throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.updateMobileNumber(employeeId, mobileNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAddress(int employeeId, int addressId, List<String> address)
            throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.updateAddress(employeeId, addressId, address);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.deleteEmployee(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.isEmployeePresent(employeeId);
    }
}