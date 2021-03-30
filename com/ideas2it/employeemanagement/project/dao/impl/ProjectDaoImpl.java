package com.ideas2it.employeemanagement.project.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.common.SqlQueries;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.sessionfactoy.DatabaseConnection;

/**
 * this class implements
 * dao interface class to perform
 * operations on project details
 * 
 * @author  sibi
 * @created 2021-03-24
 */
public class ProjectDaoImpl implements ProjectDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertProject(Project project) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int insertionCount = 0;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(SqlQueries.INSERT_PROJECT_QUERY);

            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getClientName());
            preparedStatement.setInt(3, project.getManagerId());
            preparedStatement.setDate(4, project.getStartDate());
            preparedStatement.setDate(5, project.getEndDate());

            insertionCount = preparedStatement.executeUpdate();

            preparedStatement.close();

            if (1 == insertionCount) {
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

        return (1 == insertionCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignEmployees(int projectId, List<Employee> employees) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int assigned = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ASSIGN_EMPLOYEE_QUERY);

            for (Employee employee : employees) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, employee.getId());
                preparedStatement.addBatch();
            }

            assigned = preparedStatement.executeBatch().length;
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
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
    public List<Project> getProjects() {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        List<Project> projects = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_PROJECTS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            projects = getProjectsDetails(resultSet);
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

        return projects;
    }

    /**
     * process the resultset of projects table
     *
     * @param resultSet    resultSet of project details
     *
     * @return    list containing all project details
     */
    private List<Project> getProjectsDetails(ResultSet resultSet) {
        List<Project> projects = new ArrayList<Project>();

        try {
            while (resultSet.next()) {
                projects.add(new Project(resultSet.getInt("id"), resultSet.getString("project_title")
                        , resultSet.getString("client_name"), resultSet.getInt("manager_id")
                        , resultSet.getDate("start_date"), resultSet.getDate("end_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project getProject(int projectId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        Project project = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_PROJECT_QUERY);
            preparedStatement.setInt(1,projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            project = getProjectDetails(resultSet);

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

        return project;
    }

    /**
     * process the resultset and convert it into project object
     *
     * @param resultSet    containing a project details
     *
     * @return    project object
     */
    private Project getProjectDetails(ResultSet resultSet) {
        Project project = null;
        List<Employee> employees = new ArrayList<Employee>();

        try {
            if (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String projectTitle = resultSet.getString("project_title");
                String clientName = resultSet.getString("client_name");
                int managerId = resultSet.getInt("manager_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");

                do {
                    if (0 != resultSet.getInt("employee_id")) {
                        employees.add(new Employee(resultSet.getInt("employee_id")));
                    }
                } while (resultSet.next());

                project = new Project(projectId, projectTitle
                        , clientName, managerId, startDate, endDate, employees);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateProject(Project project) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int updated = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_PROJECT_QUERY);

            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getClientName());
            Integer managerId = null;
            if (0 != project.getManagerId()) {
                managerId = new Integer(project.getManagerId());
            }
            preparedStatement.setObject(3, managerId);
            preparedStatement.setDate(4, project.getStartDate());
            preparedStatement.setDate(5, project.getEndDate());
            preparedStatement.setInt(6, project.getId());

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
    public boolean addEmployee(int projectId, int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int assigned = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ASSIGN_EMPLOYEE_QUERY);

            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, employeeId);

            assigned = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
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
    public boolean removeEmployee(int projectId, int employeeId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int deleted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UNASSIGN_EMPLOYEE_QUERY);

            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, employeeId);

            deleted = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
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
     * {@inheritDoc}
     */
    @Override
    public boolean deleteProject(int projectId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int deleted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_PROJECT_QUERY);
            preparedStatement.setInt(1, projectId);
            deleted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if ((0 != deleted) && unassignEmployees(projectId, connection)) {
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
     * unassign employees when project is deleted
     *
     * @param projectId           for which employees to be unassigned
     * @param connectionObject    connection object
     *
     * @return    true if all employees unassigned else false
     */
    private boolean unassignEmployees(int projectId, Connection connectionObject) {
        Connection connection = connectionObject;
        int unassigned = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UNASSIGN_EMPLOYEES_QUERY);

            preparedStatement.setInt(1, projectId);

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
    public List<Project> getDeletedProjects() {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        List<Project> deletedProjects = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SqlQueries.GET_DELETED_PROJECTS_QUERY);

            deletedProjects = getProjectsDetails(resultSet);

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

        return deletedProjects;
    }

    /**
     * {@inheritDoc}
     */
    public boolean restoreDeleted(int projectId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        int restored = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.RESTORE_PROJECT_QUERY);

            preparedStatement.setInt(1, projectId);

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
    public boolean isProjectPresent(int projectId) {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        boolean isPresent = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.IS_PROJECT_PRESENT_QUERY);
            preparedStatement.setInt(1, projectId);
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