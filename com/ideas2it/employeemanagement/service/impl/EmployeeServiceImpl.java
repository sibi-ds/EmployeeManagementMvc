
package com.ideas2it.employeemanagement.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ideas2it.employeemanagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.service.EmployeeService;

import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Address;

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
    public boolean createEmployee(String name, Date dob, float salary, String mobileNumber)
            throws ClassNotFoundException, SQLException {
        return employeeDaoImpl
                .createEmployee(new Employee(name, dob, salary, mobileNumber));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, String> getEmployees() throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.getEmployees();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.getEmployee(employeeId).toString();
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
    public boolean updateMobileNumber(int employeeId, String mobileNumber) throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.updateMobileNumber(employeeId, mobileNumber);
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
    public boolean isEmployeePresent(int employeeId)
            throws ClassNotFoundException, SQLException {
        return employeeDaoImpl.isEmployeePresent(employeeId);
    }
}