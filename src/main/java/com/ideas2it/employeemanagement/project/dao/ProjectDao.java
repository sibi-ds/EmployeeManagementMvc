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
     * stores the project details
     *
     * @param  project    object of project details
     *
     * @return    true if project details stored successfully else false 
     */
    public boolean insertProject(Project project);

    /**
     * get the details of all projects
     *
     * @return    list consisting all project details
     */
    public List<Project> getProjects();

    /**
     * get the details of a project
     *
     * @param projectId    whose details need to be retrieved from the database
     *
     * @return    project details along with employees assigned
     */
    public Project getProject(int projectId);

    /**
     * get the details of project
     *
     * @param projectId    which need to be extracted
     *
     * @return    project details
     */
    public Project getSpecifiedProject(int projectId);
	
    /**
     * updates the project details
     *
     * @param project    object of updated project details
     *
     * @return    true if updated successfully else false
     */
    public boolean updateProject(Project project);

    /**
     * get the deleted projects list
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
    
    /**
     * used to check whether the project deleted already or not
     *
     * @param projectId    Project Id to verify deletion
     *
     * @return    returns true if project deleted already else false
     */
    boolean isProjectDeleted(int projectId);
}