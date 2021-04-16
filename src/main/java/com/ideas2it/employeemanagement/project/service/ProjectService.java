package com.ideas2it.employeemanagement.project.service;

import java.sql.Date;
import java.util.List;

import com.ideas2it.employeemanagement.project.model.Project;

/**
 * interface class of the project service
 *
 * @author  sibi
 * @created 2021-03-24
 */
public interface ProjectService {

    /**
     * requests DAO to insert project details
     * 
     * @param projectTitle    project name
     * @param clientName      client name
     * @param managetId       project managers's ID
     * @param startDate       project start date    
     * @param endDate         project end date
     *
     * @return    true if project details stored successfully else false
     */
    public boolean insertProject(String projectTitle, String clientName
            , int managerId, Date startDate, Date endDate);

    /**
     * requests DAO to assign employees to a project
     *
     * @param projectId    for which employees need to be assigned
     * @param employees    list of employee IDs
     *
     * @return    true if assignation successful else false
     */
/*    public boolean assignEmployees(int projectId, List<Integer> employeeIds);*/

    /**
     * requests DAO to give the details of all projects
     *
     * @return    list containing all project details
     */
    public List<String> getProjects();

    /**
     * requests DAO to give the details of the project along with employees assigned
     *
     * @param projectId    which need to be retrieved from the database
     *
     * @return    list containing project details
     */
    public List<List<String>> getProject(int projectId);

    /**
     * requests DAO to give the details of projects
     *
     * @param projectIds    list of project IDs
     *
     * @return    list of projects
     */
/*    public List<Project> getSpecifiedProjects(List<Integer> projectIds);*/

    /**
     * requests DAO to give the details of project
     *
     * @param projectId    which need to be extracted
     *
     * @return    project details
     */
    public Project getSpecifiedProject(int projectId);
	
    /**
     * requests DAO to update the project
     *
     * @param projectId       for which details to be updated
     * @param projectTitle    updated project title
     * @param clientName      updated client name
     * @param managerId       updated managerId
     * @param startDate       updated start date
     * @param endDate         updated end date
     *
     * @return    true if updated successfully else false
     */
    public boolean updateProject(int projectId, String projectTitle
            , String clientName, int managerId, Date startDate, Date endDate);

    /**
     * requests DAO to add employee to a project
     *
     * @param projectId     for which employee should be added
     * @param employeeId    which employee should be added
     *
     * @return    true if employee added to a project successfully else false
     */
    public boolean assignEmployee(int projectId, int employeeId);

    /**
     * requests DAO to remove employee from a project
     *
     * @param projectId     for which employee should be added
     * @param employeeId    which employee should be added
     *
     * @return    true if employee removed from a project successfully else false
     */
    public boolean unassignEmployee(int projectId, int employeeId);

    /**
     * requesting DAO to remove the details of a project
     *
     * @param projectId    project ID for which project to be deleted
     *
     * @return    true if deletion successful else false
     */
    public boolean deleteProject(int projectId);

    /**
     * requests DAO to get deleted projects list
     * 
     * @return    list containing deleted projects
     */
    public List<String> getDeletedProjects();

    /**
     * requests DAO to restore deleted project
     * 
     * @param projectId    which need to be restored
     * 
     * @return    true if restoration successful else false
     */
    public boolean restoreProject(int projectId);

    /**
     * checks whether the project present or not
     *
     * @param projectId    projectId to verify the exixtence
     *
     * @return    true if project present else return false
     */
    public boolean isProjectPresent(int projectId); 

    /**
     * checks whether the project deleted already or not
     *
     * @param projectId    projectId to verify deletion
     *
     * @return    true if project deleted already else return false
     */
    public boolean isProjectDeleted(int projectId); 
    
    /**
     * checks existance of an employee by requesting the employee service
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    true if employee present in database else false
     */
    public boolean isEmployeePresent(int employeeId);
}