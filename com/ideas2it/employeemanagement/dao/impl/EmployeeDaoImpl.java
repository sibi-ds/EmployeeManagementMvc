package com.ideas2it.employeemanagement.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        } else {
            return false;
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

        return (1 == addressInserted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT id from employee WHERE is_deleted = false");

        ResultSet employeeIds = preparedStatement.executeQuery();

        List<Employee> employees = new ArrayList<Employee>();

        while (employeeIds.next()) {
            employees.add(getEmployee(employeeIds.getInt("id")));            
        }

        preparedStatement.close();
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
                .prepareStatement("SELECT name, date_of_birth, salary, mobile_number FROM employee"
                 + " WHERE id = ? AND is_deleted = false");
        
        preparedStatement.setInt(1,employeeId);

        ResultSet employee = preparedStatement.executeQuery();

        if (employee.next()) {
            String name = employee.getString("name");
            Date dob = employee.getDate("date_of_birth");
            float salary = employee.getFloat("salary");
            String mobileNumber = employee.getString("mobile_number");

            List<Address> addresses = new ArrayList<Address>();

            getAddresses(employeeId).forEach((addressId, address) -> {
                addresses.add(address);
            });

            return new Employee(employeeId, name, dob, salary, mobileNumber, addresses);
        }

        preparedStatement.close();
        connection.close();

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Address> getAddresses(int employeeId) throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT address_id, address_type, door_number, street, village, district, state, pincode "
                + "FROM address WHERE employee_id = ? AND is_deleted = false");
        preparedStatement.setInt(1,employeeId);

        ResultSet addressesAvailable = preparedStatement.executeQuery();

        Map<Integer, Address> addresses = new LinkedHashMap<Integer, Address>();

        int addressId = 1;

        while (addressesAvailable.next()) {
            addresses.put(addressesAvailable.getInt("address_id"), new Address(addressId, addressesAvailable.getString("address_type")
                    , addressesAvailable.getString("door_number"), addressesAvailable.getString("street")
                    , addressesAvailable.getString("village"), addressesAvailable.getString("district")
                    , addressesAvailable.getString("state"), addressesAvailable.getString("pincode")));
            addressId++;
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

        return (1 == updated);
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

        return (1 == inserted);
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

        return (1 == inserted);
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

        return (1 == inserted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAddress(int employeeId, Address address) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

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

        return (1 == addressInserted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAddressValues(int employeeId, int addressId, Address address)
            throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT address_id from address WHERE employee_id = ? AND is_deleted = false");
        preparedStatement.setInt(1,employeeId);

        ResultSet addressIds = preparedStatement.executeQuery();

        preparedStatement = connection.prepareStatement("UPDATE address"
                + " SET address_type = ?, door_number = ?, street = ?"
                + ", village = ?, district = ?, state = ?, pincode = ?"
                + " WHERE address_id = ? AND employee_id = ?");

        preparedStatement.setString(1,address.getAddressType());
        preparedStatement.setString(2,address.getDoorNumber());
        preparedStatement.setString(3,address.getStreet());
        preparedStatement.setString(4,address.getVillage());
        preparedStatement.setString(5,address.getDistrict());
        preparedStatement.setString(6,address.getState());
        preparedStatement.setString(7,address.getPincode());
        preparedStatement.setInt(8,addressId);
        preparedStatement.setInt(9,employeeId);

        int updated = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (0 != updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAddress(int employeeId, int addressId) throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE address SET is_deleted = true WHERE employee_id = ? AND address_id = ?");

        preparedStatement.setInt(1, employeeId);
        preparedStatement.setInt(2, addressId);

        int deleted = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (1 == deleted);
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

        return (0 != deleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getDeleted() throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        Statement statement = connection.createStatement();
        ResultSet deletedEmployees = statement
                .executeQuery("SELECT id, name, date_of_birth, salary, mobile_number FROM employee WHERE is_deleted = true");

        List<Employee> deletedEmployeesList = new ArrayList<Employee>();

        while (deletedEmployees.next()) {
            deletedEmployeesList.add(new Employee(deletedEmployees.getInt("id"), deletedEmployees.getString("name")
                    , deletedEmployees.getDate("date_of_birth"), deletedEmployees.getFloat("salary")
                    , deletedEmployees.getString("mobile_number"), null));
        }

        statement.close();
        connection.close();

        return deletedEmployeesList;
    }

    /**
     * {@inheritDoc}
     */
    public boolean restoreDeleted(int employeeId) throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE employee, address SET employee.is_deleted = false"
                + ", address.is_deleted = false WHERE employee.id = ?");
        preparedStatement.setInt(1,employeeId);

        int isRestored = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return (0 != isRestored);
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