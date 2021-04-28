package com.ideas2it.employeemanagement.project.dao;

import java.util.List;

import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.exceptions.EmployeeManagementException;

/**
 * this interface class is used as DAO layer to
 * perform operations on project details
 * 
 * @author sibi
 * @created 2021-03-24
 */
public interface ProjectDao {
    
    /**
     * stores the project details
     *
     * @param project object of project details
     *
     * @return true if project details stored
     *         successfully else false
     * @throws EmployeeManagementException
     */
    public void insertProject(Project project) throws EmployeeManagementException;
    
    /**
     * get the details of all projects
     *
     * @return list consisting all project details
     * @throws EmployeeManagementException
     */
    public List<Project> getProjects() throws EmployeeManagementException;
    
    /**
     * get the details of a project
     *
     * @param projectId whose details need to be
     *                  retrieved from the database
     *                 
     * @return project details along with employees
     *         assigned
     * @throws EmployeeManagementException
     */
    public Project getProject(int projectId) throws EmployeeManagementException;
    
    /**
     * get the details of project
     *
     * @param projectId which need to be extracted
     *
     * @return project details
     * @throws EmployeeManagementException
     */
    public Project getSpecifiedProject(int projectId) throws EmployeeManagementException;
    
    /**
     * updates the project details
     *
     * @param project object of updated project
     *                details
     *
     * @return true if updated successfully else false
     */
    public void updateProject(Project project);
    
    /**
     * get the deleted projects list
     * 
     * @return list of deleted projects
     * @throws EmployeeManagementException
     */
    public List<Project> getDeletedProjects() throws EmployeeManagementException;
    
    /**
     * used to check whether the project present or
     * not
     *
     * @param projectId Project Id to verify the
     *                  existence
     *
     * @return returns true if project present else
     *         false
     * @throws EmployeeManagementException
     */
    boolean isProjectPresent(int projectId) throws EmployeeManagementException;
    
    /**
     * used to check whether the project deleted
     * already or not
     *
     * @param projectId Project Id to verify deletion
     *
     * @return returns true if project deleted already
     *         else false
     * @throws EmployeeManagementException
     */
    boolean isProjectDeleted(int projectId) throws EmployeeManagementException;
}