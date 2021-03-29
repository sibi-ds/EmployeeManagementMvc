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

    private final String insertProjectQuery = "INSERT INTO project (project_title, client_name"
            + ", manager_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

    private final String assignEmployeeQuery = "INSERT IGNORE INTO employee_project"
            + " (project_id, employee_id) VALUES (?, ?)";

    private final String getProjectsQuery = "SELECT id, project_title, client_name, manager_id"
            + ", start_date, end_date FROM project WHERE is_deleted = false";

    private final String getProjectQuery = "SELECT p.id, p.project_title, p.client_name, p.manager_id"
            + ", p.start_date, p.end_date, ep.employee_id FROM project p LEFT JOIN employee_project ep ON"
            + " p.id = ep.project_id WHERE p.id = ? AND p.is_deleted = false";

    private final String updateProjectQuery = "UPDATE project SET project_title = IFNULL(?, project_title)"
            + ", client_name = IFNULL(?, client_name), manager_id = IFNULL(?, manager_id)"
            + ", start_date = IFNULL(?, start_date), end_date = IFNULL(?, end_date)"
            + " WHERE id = ? AND is_deleted = false";

    private final String deleteEmployeeQuery = "DELETE from employee_project"
            + " WHERE project_id = ? AND employee_id = ?";

    private final String deleteProjectQuery = "UPDATE project SET is_deleted = true WHERE id = ?";

    private final String getDeletedProjectsQuery = "SELECT id, project_title, client_name, manager_id"
            + ", start_date, end_date FROM project WHERE is_deleted = true";

    private final String restoreProjectQuery = "UPDATE project SET is_deleted = false WHERE id = ?";

    private final String isProjectPresentQuery = "SELECT id FROM project WHERE id = ? AND is_deleted = false";

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
                    .prepareStatement(insertProjectQuery);

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
            PreparedStatement preparedStatement = connection.prepareStatement(assignEmployeeQuery);

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
            PreparedStatement preparedStatement = connection.prepareStatement(getProjectsQuery);
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
                int projectId = resultSet.getInt("id");
                String projectTitle = resultSet.getString("project_title");
                String clientName = resultSet.getString("client_name");
                int managerId = resultSet.getInt("manager_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");

                projects.add(new Project(projectId, projectTitle
                        , clientName, managerId, startDate, endDate));
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
            PreparedStatement preparedStatement = connection.prepareStatement(getProjectQuery);
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
            PreparedStatement preparedStatement = connection.prepareStatement(updateProjectQuery);

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
            PreparedStatement preparedStatement = connection.prepareStatement(assignEmployeeQuery);

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
            PreparedStatement preparedStatement = connection.prepareStatement(deleteEmployeeQuery);

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
            PreparedStatement preparedStatement = connection.prepareStatement(deleteProjectQuery);
            preparedStatement.setInt(1, projectId);
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

        return (0 != deleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getDeletedProjects() {
        DatabaseConnection databaseConnection = DatabaseConnection.connectDatabase();
        Connection connection = databaseConnection.getConnection();
        List<Project> deletedProjects = new ArrayList<Project>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getDeletedProjectsQuery);

            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String projectTitle = resultSet.getString("project_title");
                String clientName = resultSet.getString("client_name");
                int managerId = resultSet.getInt("manager_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");

                deletedProjects.add(new Project(projectId, projectTitle
                        , clientName, managerId, startDate, endDate));
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
            PreparedStatement preparedStatement = connection.prepareStatement(restoreProjectQuery);

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
            PreparedStatement preparedStatement = connection.prepareStatement(isProjectPresentQuery);
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