package com.ideas2it.employeemanagement.project.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.project.service.ProjectService;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;

/**
 * this class works as a mediater between view and service
 * by send and receive the requests
 *
 * @author  sibi
 * @created 2021-03-24
 */
public class ProjectController {

    private ProjectService projectServiceImpl = new ProjectServiceImpl();

    /**
     * requests service to create employee details object and store it in database
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
            , int managerId, Date startDate, Date endDate) {
        return projectServiceImpl
                .insertProject(projectTitle, clientName, managerId, startDate, endDate);
    }

    /**
     * requests service to assign employees to a project
     *
     * @param projectId    for which employees need to be assigned
     * @param employees    list of emploeey IDs
     *
     * @return    true if assignation successful else false
     */
    public boolean assignEmployees(int projectId, List<Integer> employeeIds) {
        return projectServiceImpl.assignEmployees(projectId, employeeIds);
    }

    /**
     * requests service to give the details of all projects 
     *
     * @return    list containing projects
     */
    public List<String> getProjects() {
        return projectServiceImpl.getProjects();
    }

    /**
     * requests service to give the details of a project
     *
     * @param projectId    which need to be extracted from the database
     *
     * @return    string containing project details
     */
    public String getProject(int projectId) {
        return projectServiceImpl.getProject(projectId);
    }

    /**
     * requests service to update the project
     *
     * @param projectId      for which details to be updated
     * @param projectTitle   updated project title
     * @param clientName     updated client name
     * @param managerId      updated managerId
     * @param startDate      updated start date
     * @param endDate        updated end date
     *
     * @return    true if updated successfully else false
     */
    public boolean updateProject(int projectId, String projectTitle
            , String clientName, int managerId, Date startDate, Date endDate) {
        return projectServiceImpl.updateProject(projectId, projectTitle
                , clientName, managerId, startDate, endDate);
    }

    /**
     * requests service to add employee to a project
     *
     * @param projectId     for which employee should be added
     * @param employeeId    which employee should be added
     *
     * @return    true if employee added to a project successfully else false
     */
    public boolean addEmployee(int projectId, int employeeId) {
        return projectServiceImpl.addEmployee(projectId, employeeId);
    }

    /**
     * requests service to remove employee from a project
     *
     * @param projectId     for which employee should be removed
     * @param employeeId    which employee should be removed
     *
     * @return    true if employee removed from a project successfully else false
     */
    public boolean removeEmployee(int projectId, int employeeId) {
        return projectServiceImpl.removeEmployee(projectId, employeeId);
    }

    /**
     * requests service to remove the details of a project
     *
     * @param projectId    project ID for which details to be deleted
     *
     * @return    true if deletion successful else false
     */
    public boolean deleteProject(int projectId) {
        return projectServiceImpl.deleteProject(projectId);
    }

    /**
     * requests service to get deleted projects list
     * 
     * @return    list containing deleted projects
     */
    public List<String> getDeletedProjects() {
        return projectServiceImpl.getDeletedProjects();
    }

    /**
     * requests service to restore deleted project
     * 
     * @param projectId    which need to be restored
     * 
     * @return    true if restoration successful else false
     */
    public boolean restoreProject(int projectId) {
        return projectServiceImpl.restoreProject(projectId);
    }

    /**
     * checks whether the project present or not
     *
     * @param projectId    projectId to verify the exixtence
     *
     * @return    true if project present else false
     */
    public boolean isProjectPresent(int projectId) {
        return projectServiceImpl.isProjectPresent(projectId);
    }

    /**
     * used to check whether the employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    true if employee present else false
     */
    public boolean isEmployeePresent(int employeeId) {
        return projectServiceImpl.isEmployeePresent(employeeId);
    }
}