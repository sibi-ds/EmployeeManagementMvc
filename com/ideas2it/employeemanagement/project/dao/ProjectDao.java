package com.ideas2it.employeemanagement.project.dao;

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
     * @return    project details along with employees assigned
     */
    public Project getProject(int projectId);

    /**
     * requests database to give the details of project
     *
     * @param projectId    which need to be extracted
     *
     * @return    project details
     */
    public Project getSpecifiedProject(int projectId);
	
    /**
     * requests database to update the project details
     *
     * @param project    object of updated project details
     *
     * @return    true if updated successfully else false
     */
    public boolean updateProject(Project project);

    /**
     * requests database to get deleted projects list
     * 
     * @return    list of deleted projects
     */
    public List<Project> getDeletedProjects();
	
    /**
     * used to check whether the project present or not
     *
     * @param projectId    Project Id to verify the existence
     *
     * @return    returns true if project present else false
     */
    boolean isProjectPresent(int projectId);
}