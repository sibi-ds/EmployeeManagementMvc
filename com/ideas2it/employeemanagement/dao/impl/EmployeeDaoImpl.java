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
import com.ideas2it.employeemanagement.sessionfactoy.DatabaseConnection;

/**
 * this class implements
 * dao interface class to perform
 * operations on employee details
 * 
 * @author  sibi
 * @created 2021-03-08
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private final String insertEmployeeQuery
            = "INSERT INTO employee (name, date_of_birth, salary, mobile_number) VALUES (?, ?, ?, ?)";

    private final String insertAddressQuery = "INSERT INTO address"
            + " (address_type, employee_id, door_number, street, village, district, state, pincode)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final String getEmployeeQuery = "SELECT employee.id, employee.name, employee.date_of_birth"
            + ", employee.salary, employee.mobile_number, address.address_id, address.address_type"
            + ", address.door_number, address.street, address.village, address.district, address.state"
            + ", address.pincode FROM employee LEFT JOIN address ON employee.id = address.employee_id"
            + " AND address.is_deleted = false WHERE employee.id = ? AND employee.is_deleted = false";

    private final String getEmployeesQuery = "SELECT employee.id, employee.name, employee.date_of_birth"
            + ", employee.salary, employee.mobile_number, address.address_id, address.address_type"
            + ", address.door_number, address.street, address.village, address.district, address.state"
            + ", address.pincode FROM employee LEFT JOIN address ON employee.id = address.employee_id"
            + " AND address.is_deleted = false WHERE employee.is_deleted = false";

    private final String getAddressesQuery = "SELECT address_id, address_type, door_number, street"
            + ", village, district, state, pincode FROM address WHERE employee_id = ? AND is_deleted = false";

    private final String getDeletedAddressesQuery
            = "SELECT address_id FROM address WHERE employee_id = ? AND is_deleted = true";

    private final String updateEmployeeQuery = "UPDATE employee SET name = IFNULL(?, name)"
            + ", date_of_birth = IFNULL(?, date_of_birth), salary = IFNULL(?, salary)"
            + ", mobile_number = IFNULL(?, mobile_number) WHERE id = ?";

    private final String updateAddressValuesQuery = "UPDATE address SET address_type = ?"
            + ", door_number = ?, street = ?, village = ?, district = ?, state = ?"
            + ", pincode = ? WHERE address_id = ? AND employee_id = ?";

    private final String deleteAddressQuery = "UPDATE address SET is_deleted = true"
            + " WHERE employee_id = ? AND address_id = ?";

    private final String deleteEmployeeQuery = "UPDATE employee, address SET employee.is_deleted = true"
            + ", address.is_deleted = true WHERE employee.id = ? AND employee.id = address.employee_id";

    private final String deleteEmployeeOnlyQuery = "UPDATE employee SET is_deleted = true WHERE id = ?";

    private final String getDeletedEmployeesQuery = "SELECT id, name, date_of_birth, salary"
            + ", mobile_number FROM employee WHERE is_deleted = true";

    private final String restoreEmployeeQuery = "UPDATE employee, address SET employee.is_deleted = false"
            + ", address.is_deleted = false WHERE employee.id = ? AND employee.id = address.employee_id";

    private final String restoreEmployeeOnlyQuery = "UPDATE employee SET is_deleted = false WHERE id = ?";

    private final String isEmployeePresentQuery = "SELECT id FROM employee WHERE id = ? AND is_deleted = false";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(Employee employee) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        boolean isEmployeeInserted = false;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertEmployeeQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setDate(2, employee.getDateOfBirth());
            preparedStatement.setFloat(3, employee.getSalary());
            preparedStatement.setString(4, employee.getMobileNumber());

            int insertionCount = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int employeeId = resultSet.getInt(1);

            preparedStatement.close();

            if ((1 == insertionCount) && insertAddresses(employeeId, employee.getAddresses(), connection)) {
                connection.commit();
                isEmployeeInserted = true;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isEmployeeInserted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertAddresses(int employeeId, List<Address> addresses, Connection connectionObject) {
        Connection connection = connectionObject;
        int insertedCount = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAddressQuery);

            for (Address address : addresses) {
                preparedStatement.setString(1, address.getAddressType());
                preparedStatement.setInt(2, employeeId);
                preparedStatement.setString(3, address.getDoorNumber());
                preparedStatement.setString(4, address.getStreet());
                preparedStatement.setString(5, address.getVillage());
                preparedStatement.setString(6, address.getDistrict());
                preparedStatement.setString(7, address.getState());
                preparedStatement.setString(8, address.getPincode());
                preparedStatement.addBatch();
            }

            insertedCount = preparedStatement.executeBatch().length;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (insertedCount == addresses.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        List<Employee> employees = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getEmployeesQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            employees = getEmployeesDetails(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employees;
    }

    /**
     * result set is converted into list of employees
     * 
     * @param resultSet    details of all employees which are stored in the database
     * 
     * @return    list of employees details
     */
    public List<Employee> getEmployeesDetails(ResultSet resultSet) {
        List<Employee> employees = new ArrayList<Employee>();

        try {
            if (resultSet.next()) {
                boolean isRowAvailable = true;

                while (isRowAvailable) {
                    List<Address> addresses = new ArrayList<Address>();
                    int employeeId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Date dob = resultSet.getDate("date_of_birth");
                    float salary = resultSet.getFloat("salary");
                    String mobileNumber = resultSet.getString("mobile_number");

                    while (resultSet.getInt("id") == employeeId) {
                        if (0 != resultSet.getInt("address_id")) {
                            addresses.add(new Address(resultSet.getInt("address_id"), resultSet.getString("address_type")
                                    , resultSet.getString("door_number"), resultSet.getString("street")
                                    , resultSet.getString("village"), resultSet.getString("district")
                                    , resultSet.getString("state"), resultSet.getString("pincode")));
                        }

                        if (!resultSet.next()) {
                            isRowAvailable = false;
                            break;
                        }
                    }
                    employees.add(new Employee(employeeId, name, dob, salary, mobileNumber, addresses));
                }        
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        Employee employee = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getEmployeeQuery);
            preparedStatement.setInt(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employee = getEmployeeDetails(resultSet);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employee;
    }

    /**
     * result set is converted into employee's details
     * 
     * @param resultSet    details of an employee which are stored in the database
     * 
     * @return    employee's details
     */
    public Employee getEmployeeDetails(ResultSet resultSet) {
        List<Address> addresses = new ArrayList<Address>();
        Employee employee = null;

        try {
            int employeeId = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Date dob = resultSet.getDate("date_of_birth");
            float salary = resultSet.getFloat("salary");
            String mobileNumber = resultSet.getString("mobile_number");
            do {
                if (0 != resultSet.getInt("address_id")) {
                addresses.add(new Address(resultSet.getInt("address_id"), resultSet.getString("address_type")
                        , resultSet.getString("door_number"), resultSet.getString("street")
                        , resultSet.getString("village"), resultSet.getString("district")
                        , resultSet.getString("state"), resultSet.getString("pincode")));
                }
            } while (resultSet.next());
            employee = new Employee(employeeId, name, dob, salary, mobileNumber, addresses);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Address> getAddresses(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        Map<Integer, Address> addresses = new LinkedHashMap<Integer, Address>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAddressesQuery);
            preparedStatement.setInt(1,employeeId);

            ResultSet addressesAvailable = preparedStatement.executeQuery();

            while (addressesAvailable.next()) {
                addresses.put(addressesAvailable.getInt("address_id"), new Address(addressesAvailable.getString("address_type")
                        , addressesAvailable.getString("door_number"), addressesAvailable.getString("street")
                        , addressesAvailable.getString("village"), addressesAvailable.getString("district")
                        , addressesAvailable.getString("state"), addressesAvailable.getString("pincode")));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(int employeeId, Employee employee) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        String name = employee.getName();
        Date dob = employee.getDateOfBirth();
        float salary = employee.getSalary();
        Float salaryValue = null;
        if (0.0f != salary) {
            salaryValue = new Float(salary);
        }
        String mobileNumber = employee.getMobileNumber();
        int updated = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateEmployeeQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, dob);
            preparedStatement.setObject(3, salaryValue);
            preparedStatement.setString(4, mobileNumber);
            preparedStatement.setInt(5, employeeId);

            updated = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (1 == updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAddress(int employeeId, Address address) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int addressInserted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAddressQuery);

            preparedStatement.setString(1, address.getAddressType());
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setString(3, address.getDoorNumber());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setString(5, address.getVillage());
            preparedStatement.setString(6, address.getDistrict());
            preparedStatement.setString(7, address.getState());
            preparedStatement.setString(8, address.getPincode());

            addressInserted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (1 == addressInserted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAddressValues(int employeeId, int addressId, Address address) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int updated = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateAddressValuesQuery);

            preparedStatement.setString(1, address.getAddressType());
            preparedStatement.setString(2, address.getDoorNumber());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setString(4, address.getVillage());
            preparedStatement.setString(5, address.getDistrict());
            preparedStatement.setString(6, address.getState());
            preparedStatement.setString(7, address.getPincode());
            preparedStatement.setInt(8, addressId);
            preparedStatement.setInt(9, employeeId);

            updated = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (0 != updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAddress(int employeeId, int addressId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int deleted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAddressQuery);

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, addressId);

            deleted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (1 == deleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int employeeId) {
        int employeeDeleted = 0;

        if (getAddresses(employeeId).isEmpty()) {
            employeeDeleted = deleteEmployeeOnly(employeeId);
        } else {
            employeeDeleted = deleteEmployeeAndAddress(employeeId);
        }

        return (0 != employeeDeleted);
    }

    /**
     * deletes employee which doesn't have address
     *
     * @param employeeId    for which details to be removed
     *
     * @return    deleted count
     */
    private int deleteEmployeeOnly(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int employeeDeleted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteEmployeeOnlyQuery);
            preparedStatement.setInt(1,employeeId);
            employeeDeleted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (1 == employeeDeleted) {
                connection.commit();
            } else {
                connection.rollback();
            }
         } catch (SQLException e) {
             try {
                 connection.rollback();
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
             e.printStackTrace();
         } finally {
             try {
                 connection.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }

        return employeeDeleted;
    }

    /**
     * deletes employee's details
     *
     * @param employeeId    for which details to be removed
     *
     * @return    deleted count
     */
    private int deleteEmployeeAndAddress(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int employeeDeleted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteEmployeeQuery);
            preparedStatement.setInt(1,employeeId);
            employeeDeleted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (0 != employeeDeleted) {
                connection.commit();
            } else { 
                connection.rollback();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employeeDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getDeletedEmployees() {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        List<Employee> deletedEmployeesList = new ArrayList<Employee>();

        try {
            Statement statement = connection.createStatement();
            ResultSet deletedEmployees = statement
                    .executeQuery(getDeletedEmployeesQuery);

            while (deletedEmployees.next()) {
                deletedEmployeesList.add(new Employee(deletedEmployees.getInt("id"), deletedEmployees.getString("name")
                        , deletedEmployees.getDate("date_of_birth"), deletedEmployees.getFloat("salary")
                        , deletedEmployees.getString("mobile_number"), null));
            }

            statement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return deletedEmployeesList;
    }

    /**
     * {@inheritDoc}
     */
    public boolean restoreDeleted(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int restored = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getDeletedAddressesQuery);
            preparedStatement.setInt(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                restored = restoreEmployeeOnly(employeeId);
            } else {
                restored = restoreEmployeeAndAddress(employeeId);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (0 != restored);
    }

    /**
     * restores employee which doesn't have address
     *
     * @param employeeId    for which details to be restored
     *
     * @return    restored count
     */
    private int restoreEmployeeOnly(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int employeeRestored = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(restoreEmployeeOnlyQuery);
            preparedStatement.setInt(1,employeeId);
            employeeRestored = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (0 != employeeRestored) {
                connection.commit();
            } else {
                connection.rollback();
            }
         } catch (SQLException e) {
             try {
                 connection.rollback();
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
             e.printStackTrace();
         } finally {
             try {
                 connection.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }

        return employeeRestored;
    }

    /**
     * restores employee's details
     *
     * @param employeeId    for which details to be restored
     *
     * @return    restored count
     */
    private int restoreEmployeeAndAddress(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int employeeRestored = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(restoreEmployeeQuery);
            preparedStatement.setInt(1,employeeId);
            employeeRestored = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (0 != employeeRestored) {
                connection.commit();
            } else { 
                connection.rollback();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employeeRestored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        boolean isPresent = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(isEmployeePresentQuery);
            preparedStatement.setInt(1, employeeId);

            isPresent = preparedStatement.executeQuery().next();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isPresent;
    }
}