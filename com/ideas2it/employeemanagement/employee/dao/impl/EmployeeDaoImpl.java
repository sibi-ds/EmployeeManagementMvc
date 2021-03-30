package com.ideas2it.employeemanagement.employee.dao.impl;

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

import com.ideas2it.employeemanagement.common.SqlQueries;
import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;
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
                    .prepareStatement(SqlQueries.INSERT_EMPLOYEE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);

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
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.INSERT_ADDRESS_QUERY);

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
    public boolean assignProject(int employeeId, Project project) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int assigned = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ASSIGN_PROJECT_QUERY);
            preparedStatement.setInt(1, project.getId());
            preparedStatement.setInt(2, employeeId);
            assigned = preparedStatement.executeUpdate();
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

        return (0 != assigned);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unassignProject(int employeeId, Project project) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int unassigned = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UNASSIGN_PROJECT_QUERY);
            preparedStatement.setInt(1, project.getId());
            preparedStatement.setInt(2, employeeId);
            unassigned = preparedStatement.executeUpdate();
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

        return (0 != unassigned);
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
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_EMPLOYEES_QUERY);
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
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_EMPLOYEE_QUERY);
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
    public Employee getSpecifiedEmployee(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        Employee employee = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_SPECIFIED_EMPLOYEE_QUERY);
            preparedStatement.setInt(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Date dob = resultSet.getDate("date_of_birth");
                float salary = resultSet.getFloat("salary");
                String mobileNumber = resultSet.getString("mobile_number");
                List<Project> projects = new ArrayList<Project>();

                do {
                    if (0 != resultSet.getInt("project_id")) {
                        projects.add(new Project(resultSet.getInt("project_id")));
                    }
                } while (resultSet.next());

                employee = new Employee(employeeId, name, dob, salary, mobileNumber);
                employee.setProjects(projects);
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
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Address> getAddresses(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        Map<Integer, Address> addresses = new LinkedHashMap<Integer, Address>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_ADDRESSES_QUERY);
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
    public boolean updateEmployee(Employee employee) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();

        int employeeId = employee.getId();
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
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_EMPLOYEE_QUERY);

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
        int addressesInserted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.INSERT_ADDRESS_QUERY);

            preparedStatement.setString(1, address.getAddressType());
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setString(3, address.getDoorNumber());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setString(5, address.getVillage());
            preparedStatement.setString(6, address.getDistrict());
            preparedStatement.setString(7, address.getState());
            preparedStatement.setString(8, address.getPincode());

            addressesInserted = preparedStatement.executeUpdate();
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

        return (1 == addressesInserted);
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
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_ADDRESS_VALUES_QUERY);

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
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_ADDRESS_QUERY);

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
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int deleted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_EMPLOYEE_QUERY);

            preparedStatement.setInt(1, employeeId);

            deleted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if ((0 != deleted) && unassignProjects(employeeId, connection)) {
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

        return (0 != deleted);
    }

    /**
     * unaasign all projects when employee is deleted
     *
     * @param projectId           for which employees to be unassigned
     * @param connectionObject    connection object
     *
     * @return    true if all project unassigned else false
     */
    private boolean unassignProjects(int employeeId, Connection connectionObject) {
        Connection connection = connectionObject;
        int unassigned = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UNASSIGN_PROJECTS_QUERY);

            preparedStatement.setInt(1, employeeId);

            unassigned = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            unassigned = -1;
            e.printStackTrace();
        }

        return (-1 != unassigned);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getDeletedEmployees() {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        List<Employee> deletedEmployees = new ArrayList<Employee>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery(SqlQueries.GET_DELETED_EMPLOYEES_QUERY);

            while (resultSet.next()) {
                deletedEmployees.add(new Employee(resultSet.getInt("id"), resultSet.getString("name")
                        , resultSet.getDate("date_of_birth"), resultSet.getFloat("salary")
                        , resultSet.getString("mobile_number"), null));
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return deletedEmployees;
    }

    /**
     * {@inheritDoc}
     */
    public boolean restoreDeleted(int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int restored = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.RESTORE_EMPLOYEE_QUERY);

            preparedStatement.setInt(1, employeeId);

            restored = preparedStatement.executeUpdate();
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

        return (0 != restored);
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
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.IS_EMPLOYEE_PRESENT_QUERY);
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