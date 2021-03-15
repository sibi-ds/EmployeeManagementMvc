package com.ideas2it.employeemanagement.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.sessionfactoy.DatabaseConnection;

/**
 * this class implements
 * dao interface class to perform
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
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO employee"
                + "(name, date_of_birth, salary, mobile_number) VALUES (?, ?, ?, ?)");

        preparedStatement.setString(1,employee.getName());
        preparedStatement.setDate(2,employee.getDateOfBirth());
        preparedStatement.setFloat(3,employee.getSalary());
        preparedStatement.setString(4,employee.getMobileNumber());

        int employeeInserted = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        boolean areAddressesInserted = true;

        if (1 == employeeInserted) {
            for (Address address : employee.getAddresses()) {
                areAddressesInserted = areAddressesInserted && insertAddress(address);
            }
        }

        return areAddressesInserted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertAddress(Address address) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        Statement statement = connection.createStatement();
        ResultSet employeeIds = statement.executeQuery("select max(id) from employee");
        employeeIds.next();
        int employeeId = employeeIds.getInt(1);
        statement.close();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO address"
                + "(address_type, employee_id, door_number, street, village, district, state, pincode)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        preparedStatement.setString(1,address.getAddressType());
        preparedStatement.setInt(2,employeeId);
        preparedStatement.setString(3,address.getDoorNumber());
        preparedStatement.setString(4,address.getStreet());
        preparedStatement.setString(5,address.getVillage());
        preparedStatement.setString(6,address.getDistrict());
        preparedStatement.setString(7,address.getState());
        preparedStatement.setString(8,address.getPincode());

        int addressInserted = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (1 == addressInserted ? true : false);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        Statement statement = connection.createStatement();

        List<Employee> employees = new ArrayList<Employee>();

        ResultSet employeeIds = statement
                .executeQuery("select id from employee WHERE is_deleted = false");

        while(employeeIds.next()) {
            int employeeId = employeeIds.getInt(1);
            employees.add(getEmployee(employeeId));
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
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM employee WHERE id = ? AND is_deleted = false");
        preparedStatement.setInt(1,employeeId);
        ResultSet employeeDetails = preparedStatement.executeQuery();
        employeeDetails.next();

        Employee employee = new Employee(employeeDetails.getInt("id"), employeeDetails.getString("name")
                , employeeDetails.getDate("date_of_birth"), employeeDetails.getFloat("salary")
                , employeeDetails.getString("mobile_number"), getAddresses(employeeId));

        preparedStatement.close();
        connection.close();

        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Address> getAddresses(int employeeId) throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM address WHERE employee_id = ? AND is_deleted = false");
        preparedStatement.setInt(1,employeeId);
        ResultSet addressesAvailable = preparedStatement.executeQuery();

        List<Address> addresses = new ArrayList<Address>();

        while (addressesAvailable.next()) {
            addresses.add(new Address(addressesAvailable.getInt("address_id"), addressesAvailable.getString("address_type")
                    , addressesAvailable.getString("door_number"), addressesAvailable.getString("street")
                    , addressesAvailable.getString("village"), addressesAvailable.getString("district")
                    , addressesAvailable.getString("state"), addressesAvailable.getString("pincode")));
        }

        preparedStatement.close();
        connection.close();

        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateName(int employeeId, String name) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET name = ? WHERE id = ?");

        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,employeeId);

        int updated = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (1 == updated ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateDob(int employeeId, Date dob) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET date_of_birth = ? WHERE id = ?");

        preparedStatement.setDate(1,dob);
        preparedStatement.setInt(2,employeeId);

        int inserted = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (1 == inserted ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateSalary(int employeeId, float salary) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET salary = ? WHERE id = ?");

        preparedStatement.setFloat(1,salary);
        preparedStatement.setInt(2,employeeId);

        int inserted = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (1 == inserted ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMobileNumber(int employeeId, String mobileNumber) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee SET mobile_number = ? WHERE id = ?");

        preparedStatement.setString(1,mobileNumber);
        preparedStatement.setInt(2,employeeId);

        int inserted = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (1 == inserted ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAddress(int employeeId, int addressId, List<String> address)
            throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE address"
                + " SET address_type = ?, door_number = ?, street = ?"
                + ", village = ?, district = ?, state = ?, pincode = ?"
                + " WHERE address_id = ? AND employee_id = ?");

        preparedStatement.setString(1,address.get(0));
        preparedStatement.setString(2,address.get(1));
        preparedStatement.setString(3,address.get(2));
        preparedStatement.setString(4,address.get(3));
        preparedStatement.setString(5,address.get(4));
        preparedStatement.setString(6,address.get(5));
        preparedStatement.setString(7,address.get(6));
        preparedStatement.setInt(8,addressId);
        preparedStatement.setInt(9,employeeId);

        int updated = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (0 != updated ? true : false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int employeeId)  throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee, address SET employee.is_deleted = true"
                + ", address.is_deleted = true WHERE id = ? AND employee_id = ?");

        preparedStatement.setInt(1,employeeId);
        preparedStatement.setInt(2,employeeId);

        int deleted = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (0 == deleted ? false : true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT id FROM employee WHERE id = ? AND is_deleted = false");
        preparedStatement.setInt(1,employeeId);

        boolean isPresent = preparedStatement.executeQuery().next();

        preparedStatement.close();
        connection.close();

        return isPresent;
    }
}