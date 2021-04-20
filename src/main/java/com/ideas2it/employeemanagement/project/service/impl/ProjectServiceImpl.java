package com.ideas2it.employeemanagement.project.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;
import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.dao.impl.ProjectDaoImpl;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.project.service.ProjectService;

/**
 * this class receives the request from the controller to store the details and
 * send back the details as per the request of the controller
 * 
 * @author sibi
 * @created 2021-03-24
 */
public class ProjectServiceImpl implements ProjectService {
    
    private ProjectDao projectDaoImpl = new ProjectDaoImpl();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertProject(String projectTitle, String clientName, int managerId, Date startDate, Date endDate) {
        return projectDaoImpl
                .insertProject(new Project(projectTitle, clientName, managerId, startDate, endDate, false));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignEmployees(int projectId, List<Integer> employeeIds) {
        Project project = projectDaoImpl.getProject(projectId);
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        List<Employee> employees = employeeServiceImpl.getSpecifiedEmployees(employeeIds);
        
        project.setEmployees(employees);
        return projectDaoImpl.updateProject(project);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() {
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        return employeeServiceImpl.getEmployees();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getProjects() {
        return projectDaoImpl.getProjects();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Project getProject(int projectId) {
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
    public Project getSpecifiedProject(int projectId) {
        return projectDaoImpl.getSpecifiedProject(projectId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateProject(int projectId, String projectTitle, String clientName, int managerId, Date startDate,
            Date endDate) {
        Project project = projectDaoImpl.getProject(projectId);
        
        project.setTitle(projectTitle);
        project.setManagerId(managerId);
        project.setClientName(clientName);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        
        return projectDaoImpl.updateProject(project);
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
    public boolean deleteProject(int projectId) {
        return deleteOrRestoreProject(projectId, true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getDeletedProjects() {
        return projectDaoImpl.getDeletedProjects();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreProject(int projectId) {
        return deleteOrRestoreProject(projectId, false);
    }
    
    /**
     * deletes or restores an employee details
     *
     * @param employeeId which need to be extracted from database
     * @param changed    determines whether to delete or restore
     *
     * @return true if successfull else false
     */
    public boolean deleteOrRestoreProject(int projectId, boolean changed) {
        Project project = projectDaoImpl.getProject(projectId);
        project.setIsDeleted(changed);
        
        if (changed) {
            project.setEmployees(null);
        }
        
        return projectDaoImpl.updateProject(project);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) {
        return projectDaoImpl.isProjectPresent(projectId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectDeleted(int projectId) {
        return projectDaoImpl.isProjectDeleted(projectId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) {
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        return employeeServiceImpl.isEmployeePresent(employeeId);
    }
}