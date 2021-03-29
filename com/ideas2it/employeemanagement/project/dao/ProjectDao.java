package com.ideas2it.employeemanagement.project.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;

/**
 * this interface class is used as DAO layer
 * to perform operations on project deatils
 * 
 * @author  sibi
 * @created 2021-03-24
 */
public interface ProjectDao {

    /**
     * used to store project in employee database
     *
     * @param  project    object of project details
     *
     * @return    true if project details stored successfully else false 
     */
    public boolean insertProject(Project project);

    /**
     * used to assign employees to a project
     *
     * @param projectId     for which employees need to be assigned
     * @param  employees    list of employees
     *
     * @return    true if employees assigned successfully else false 
     */
    public boolean assignEmployees(int projectId, List<Employee> employees);

    /**
     * requests details of all projects
     *
     * @return    list consisting all project details
     */
    public List<Project> getProjects();

    /**
     * requests database to give the details of a project
     *
     * @param projectId    whose details need to be retrieved from the database
     *
     * @return    object of project details
     */
    public Project getProject(int projectId);

    /**
     * requests database to update the project
     *
     * @param project    object of updated project details
     *
     * @return    true if updated successfully else false
     */
    public boolean updateProject(Project project);

    /**
     * requests database to add employee to a project
     *
     * @param projectId     for which employee should be added
     * @param employeeId    which employee should be added
     *
     * @return    true if employee added to a project successfully else false
     */
    public boolean addEmployee(int projectId, int employeeId);

    /**
     * requests database to remove employee from a project
     *
     * @param projectId     for which employee should be removed
     * @param employeeId    which employee should be removed
     *
     * @return    true if employee removed from a project successfully else false
     */
    public boolean removeEmployee(int projectId, int employeeId);

    /**
     * request database to delete the project details
     *
     * @param  projectId    which project should be removed
     *
     * @return    true if removed successfully else false
     */
    boolean deleteProject(int projectId);

    /**
     * requests database to get deleted projects list
     * 
     * @return    list of deleted projects
     */
    public List<Project> getDeletedProjects();

    /**
     * requests database to restore a deleted project
     * 
     * @param projectId    which need to be restored
     * 
     * @return    true if restoration successful else false
     */
    public boolean restoreDeleted(int projectId);

    /**
     * used to check whether the project present or not
     *
     * @param projectId    Project Id to verify the existence
     *
     * @return    returns true if project present else false
     */
    boolean isProjectPresent(int projectId);
}