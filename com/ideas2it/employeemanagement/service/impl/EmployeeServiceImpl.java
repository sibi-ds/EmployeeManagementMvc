package com.ideas2it.employeemanagement.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.service.EmployeeService;
import com.ideas2it.employeemanagement.sessionfactoy.DatabaseConnection;

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
    public String getEmployee(int employeeId) {
        Employee employee = employeeDaoImpl.getEmployee(employeeId);
        String employeeDetails = employee.toString();

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
    public boolean updateEmployee(int employeeId, List<String> employeeBasicDetails) {
        String name = employeeBasicDetails.get(0);
        Date dob = null;
        if (null != employeeBasicDetails.get(1)) {
            dob = Date.valueOf(employeeBasicDetails.get(1));
        }
        float salary = 0.0f;
        if (null != employeeBasicDetails.get(2)) {
            salary = Float.parseFloat(employeeBasicDetails.get(2));
        }
        String mobileNumber = employeeBasicDetails.get(3);

        return employeeDaoImpl.updateEmployee(employeeId, new Employee(name, dob, salary, mobileNumber));
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
}