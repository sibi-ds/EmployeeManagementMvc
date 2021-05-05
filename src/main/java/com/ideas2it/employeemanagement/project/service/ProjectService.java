package com.ideas2it.employeemanagement.project.service;

import java.sql.Date;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.exceptions.EmployeeManagementException;

/**
 * interface class of the project service
 *
 * @author sibi
 * @created 2021-03-24
 */
public interface ProjectService {
    
    /**
     * stores project details
     * 
     * @param projectTitle project name
     * @param clientName   client name
     * @param managetId    project managers's ID
     * @param startDate    project start date
     * @param endDate      project end date
     *
     * @return true if project details stored
     *         successfully else false
     *         
     * @throws EmployeeManagementException
     */
    public void insertProject(String projectTitle, String clientName, int managerId
            , Date startDate, Date endDate) throws EmployeeManagementException;
    
    /**
     * assign employees to a project
     *
     * @param projectId for which employees need to be
     *                  assigned
     * @param employees list of employee IDs
     *
     * @return true if assignation successful else
     *         false
     * 
     * @throws EmployeeManagementException
     */
    public void assignEmployees(int projectId, List<Integer> employeeIds)
            throws EmployeeManagementException;
    
    /**
     * get the details of all projects
     *
     * @return list containing all project details
     * 
     * @throws EmployeeManagementException
     */
    public List<Employee> getEmployees() throws EmployeeManagementException;
    
    /**
     * get the details of all projects
     *
     * @return list containing all project details
     * 
     * @throws EmployeeManagementException
     */
    public List<Project> getProjects() throws EmployeeManagementException;
    
    /**
     * get the details of the project along with
     * employees assigned
     *
     * @param projectId which need to be retrieved
     *                  from the database
     *                 
     * @return list containing project details
     *                  
     * @throws EmployeeManagementException
     */
    public Project getProject(int projectId) throws EmployeeManagementException;
    
    /**
     * get the details of projects
     *
     * @param projectIds list of project IDs
     *
     * @return list of projects
     */
    /*
     * public List<Project>
     * getSpecifiedProjects(List<Integer> projectIds);
     */
    
    /**
     * get the details of projects
     *
     * @param projectIds which are need to be extracted
     *
     * @return list of projects
     * 
     * @throws EmployeeManagementException
     */
    public List<Project> getSpecifiedProjects(List<Integer> projectIds) throws EmployeeManagementException;
    
    /**
     * get the details of project
     *
     * @param projectId which need to be extracted
     *
     * @return project details
     * 
     * @throws EmployeeManagementException
     */
    public Project getSpecifiedProject(int projectId) throws EmployeeManagementException;
    
    /**
     * updates the project details
     *
     * @param projectId    for which details to be
     *                     updated
     * @param projectTitle updated project title
     * @param clientName   updated client name
     * @param managerId    updated managerId
     * @param startDate    updated start date
     * @param endDate      updated end date
     *
     * @return true if updated successfully else false
     * 
     * @throws EmployeeManagementException
     */
    public void updateProject(int projectId, String projectTitle, String clientName
            , int managerId, Date startDate, Date endDate) throws EmployeeManagementException;
    
    /**
     * assign employee to a project
     *
     * @param projectId  for which employee should be
     *                   added
     * @param employeeId which employee should be
     *                   added
     *
     * @return true if employee added to a project
     *         successfully else false
     */
//    public boolean assignEmployee(int projectId, int employeeId);
    
    /**
     * remove employee from a project
     *
     * @param projectId  for which employee should be
     *                   added
     * @param employeeId which employee should be
     *                   added
     *
     * @return true if employee removed from a project
     *         successfully else false
     */
//    public boolean unassignEmployee(int projectId, int employeeId);
    
    /**
     * remove the details of a project
     *
     * @param projectId project ID for which project
     *                  to be deleted
     *                 
     * @return true if deletion successful else false
     *                  
     * @throws EmployeeManagementException
     */
    public void deleteProject(int projectId) throws EmployeeManagementException;
    
    /**
     * get the deleted projects list
     * 
     * @return list containing deleted projects
     * 
     * @throws EmployeeManagementException
     */
    public List<Project> getDeletedProjects() throws EmployeeManagementException;
    
    /**
     * restores the deleted project
     * 
     * @param projectId which need to be restored
     * 
     * @return true if restoration successful else
     *         false
     * 
     * @throws EmployeeManagementException
     */
    public void restoreProject(int projectId) throws EmployeeManagementException;
    
    /**
     * checks whether the project present or not
     *
     * @param projectId projectId to verify the
     *                  exixtence
     *
     * @return true if project present else return
     *         false
     * 
     * @throws EmployeeManagementException
     */
    public boolean isProjectPresent(int projectId) throws EmployeeManagementException;
    
    /**
     * checks whether the project deleted already or
     * not
     *
     * @param projectId projectId to verify deletion
     *
     * @return true if project deleted already else
     *         return false
     * 
     * @throws EmployeeManagementException
     */
    public boolean isProjectDeleted(int projectId) throws EmployeeManagementException;
    
    /**
     * checks whether the employee present or not
     *
     * @param employeeId employeeId to verify the
     *                   existence
     *
     * @return true if employee present in database
     *         else false
     * 
     * @throws EmployeeManagementException
     */
    public boolean isEmployeePresent(int employeeId) throws EmployeeManagementException;
}