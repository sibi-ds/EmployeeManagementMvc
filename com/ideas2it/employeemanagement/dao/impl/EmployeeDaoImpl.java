package com.ideas2it.employeemanagement.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.sessionfactoy.DatabaseConnection;

/**
 * this class implements
 * dao interface class to perfome
 * operations on employee details
 * 
 * @author  sibi
 * @created 2021-03-08
 */
public class EmployeeDaoImpl implements EmployeeDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO employee"
                + "(name,date_of_birth,salary,mobile_number) VALUES (?,?,?,?)");

        preparedStatement.setString(1,employee.getName());
        preparedStatement.setDate(2,employee.getDob());
        preparedStatement.setFloat(3,employee.getSalary());
        preparedStatement.setString(4,employee.getMobileNumber());

        int success = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (success == 1 ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertAddress(Address address) throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT MAX(employee_id) from employee");
        resultSet.next();
        int employeeId = resultSet.getInt(1);
        statement.close();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO address"
                + "(address_type, employee_id, door_number, street, village, district, state, pincode) VALUES (?,?,?,?,?,?,?,?)");

        preparedStatement.setString(1,String.valueOf(address.getAddressType()));
        preparedStatement.setInt(2,employeeId);
        preparedStatement.setString(3,address.getDoorNumber());
        preparedStatement.setString(4,address.getStreet());
        preparedStatement.setString(5,address.getVillage());
        preparedStatement.setString(6,address.getDistrict());
        preparedStatement.setString(7,address.getState());
        preparedStatement.setInt(8,address.getPincode());

        int success = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (success == 1 ? true : false);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, String> getEmployees() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

        Map<Integer,String> employees = new LinkedHashMap<Integer, String>();

        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getString("name"), resultSet.getDate("date_of_birth")
                    , resultSet.getFloat("salary"), resultSet.getString("mobile_number"));
            employees.put(resultSet.getInt("employee_id"), employee.toString());
        }

        statement.close();
        connection.close();

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM employee WHERE employee_id = ?");

        preparedStatement.setInt(1,employeeId);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Employee employee = new Employee(resultSet.getString("name"), resultSet.getDate("date_of_birth")
                , resultSet.getFloat("salary"), resultSet.getString("mobile_number"));

        preparedStatement.close();
        connection.close();

        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateName(int employeeId, String name) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET name = ? WHERE employee_id = ?");

        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,employeeId);

        int success = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (success == 1 ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateDob(int employeeId, Date dob) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET date_of_birth = ? WHERE employee_id = ?");

        preparedStatement.setDate(1,dob);
        preparedStatement.setInt(2,employeeId);

        int success = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (success == 1 ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateSalary(int employeeId, float salary) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET salary = ? WHERE employee_id = ?");

        preparedStatement.setFloat(1,salary);
        preparedStatement.setInt(2,employeeId);

        int success = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (success == 1 ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMobileNumber(int employeeId, String mobileNumber) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET mobile_number = ? WHERE employee_id = ?");

        preparedStatement.setString(1,mobileNumber);
        preparedStatement.setInt(2,employeeId);

        int success = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (success == 1 ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int employeeId)  throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM employee WHERE employee_id = ?");

        preparedStatement.setInt(1,employeeId);

        int success = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (success == 1 ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.getConnectionObject();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT employee_id FROM employee WHERE employee_id = ?");
        preparedStatement.setInt(1,employeeId);

        boolean isPresent = preparedStatement.executeQuery().next();

        preparedStatement.close();
        connection.close();

        return isPresent;
    }
}