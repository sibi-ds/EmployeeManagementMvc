package com.ideas2it.employeemanagement.project.service.impl;

import java.sql.Date;
import java.util.List;

import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;
import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.dao.impl.ProjectDaoImpl;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.project.service.ProjectService;
import com.ideas2it.exceptions.EmployeeManagementException;
import com.ideas2it.loggers.EmployeeManagementLogger;

/**
 * this class receives the request from the
 * controller to store the details and send back
 * the details as per the request
 * 
 * @author sibi
 * @created 2021-03-24
 */
public class ProjectServiceImpl implements ProjectService {
    
    private ProjectDao projectDaoImpl = new ProjectDaoImpl();
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(ProjectServiceImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertProject(String projectTitle, String clientName, int managerId, Date startDate, Date endDate)
            throws EmployeeManagementException {
        projectDaoImpl.insertProject(new Project(projectTitle, clientName, managerId, startDate, endDate, false));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void assignEmployees(int projectId, List<Integer> employeeIds) throws EmployeeManagementException {
        try {
            Project project = projectDaoImpl.getProject(projectId);
            EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
            List<Employee> employees = employeeServiceImpl.getSpecifiedEmployees(employeeIds);
            project.setEmployees(employees);
            projectDaoImpl.updateProject(project);
        } catch (Exception e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employees Assignation Failure");
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() throws EmployeeManagementException {
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        return employeeServiceImpl.getEmployees();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getProjects() throws EmployeeManagementException {
        return projectDaoImpl.getProjects();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Project getProject(int projectId) throws EmployeeManagementException {
        return projectDaoImpl.getProject(projectId);
    }
    
    /**
     * {@inheritDoc}
     */
//     * @Override
//    public List<Project> getSpecifiedProjects(List<Integer> projectIds) {
//         List<Project> projects = new ArrayList<Project>();
//         projectIds.forEach((projectId) -> {
//         projects.add(projectDaoImpl.getSpecifiedProject(projectId)); });
//         return projects;
//     }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Project getSpecifiedProject(int projectId) throws EmployeeManagementException {
        return projectDaoImpl.getSpecifiedProject(projectId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProject(int projectId, String projectTitle, String clientName, int managerId, Date startDate,
            Date endDate) throws EmployeeManagementException {
        try {
            Project project = projectDaoImpl.getProject(projectId);
            
            project.setTitle(projectTitle);
            project.setManagerId(managerId);
            project.setClientName(clientName);
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            
            projectDaoImpl.updateProject(project);
        } catch (Exception e) {
            logger.logError(e);
            throw new EmployeeManagementException("Project Updation Failure");
        }
    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public boolean assignEmployee(int projectId, int employeeId) {
//        Project project = projectDaoImpl.getProject(projectId);
//        List<Employee> employees = project.getEmployees();
//        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
//        boolean isAlreadyAssigned = false;
//        
//        for (Employee employeeDetails : employees) {
//            if (employeeDetails.getId() == employeeId) {
//                isAlreadyAssigned = true;
//                break;
//            }
//        }
//        
//        if (isAlreadyAssigned) {
//            return true;
//        } else {
//            employees.add(employeeServiceImpl.getSpecifiedEmployee(employeeId));
//            project.setEmployees(employees);
//            return projectDaoImpl.updateProject(project);
//        }
//    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public boolean unassignEmployee(int projectId, int employeeId) {
//        Project project = projectDaoImpl.getProject(projectId);
//        List<Employee> employees = project.getEmployees();
//        
//        for (Employee employee : employees) {
//            if (employeeId == employee.getId()) {
//                employees.remove(employee);
//                break;
//            }
//        }
//        
//        project.setEmployees(employees);
//        return projectDaoImpl.updateProject(project);
//    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProject(int projectId) throws EmployeeManagementException {
        deleteOrRestoreProject(projectId, true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getDeletedProjects() throws EmployeeManagementException {
        return projectDaoImpl.getDeletedProjects();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreProject(int projectId) throws EmployeeManagementException {
        deleteOrRestoreProject(projectId, false);
    }
    
    /**
     * deletes or restores an employee details
     *
     * @param employeeId which need to be extracted
     *                   from database
     * @param changed    determines whether to delete
     *                   or restore
     *
     * @return true if successful else false
     * 
     * @throws EmployeeManagementException
     */
    private void deleteOrRestoreProject(int projectId, boolean status) throws EmployeeManagementException {
        try {
            Project project = projectDaoImpl.getProject(projectId);
            project.setIsDeleted(status);
            
            if (status) {
                project.setEmployees(null);
            }
            
            projectDaoImpl.updateProject(project);
        } catch (Exception e) {
            logger.logError(e);
            String failureMessage = status ? "Deletion failure" : "Restoration Failure";
            throw new EmployeeManagementException(failureMessage);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) throws EmployeeManagementException {
        return projectDaoImpl.isProjectPresent(projectId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectDeleted(int projectId) throws EmployeeManagementException {
        return projectDaoImpl.isProjectDeleted(projectId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) throws EmployeeManagementException {
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        return employeeServiceImpl.isEmployeePresent(employeeId);
    }
}