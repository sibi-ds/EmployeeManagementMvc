package com.ideas2it.employeemanagement.project.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;
import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.dao.impl.ProjectDaoImpl;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.project.service.ProjectService;

/**
 * this class receives the request from the controller to store the details
 * and send back the details as per the request of the controller
 * 
 * @author  sibi
 * @created 2021-03-24
 */
public class ProjectServiceImpl implements ProjectService {

    private ProjectDao projectDaoImpl = new ProjectDaoImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertProject(String projectTitle, String clientName
            , int managerId, Date startDate, Date endDate) {
        return projectDaoImpl.insertProject(new Project(projectTitle
                , clientName, managerId, startDate, endDate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignEmployees(int projectId, List<Integer> employeeIds) {
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        List<Employee> employees = employeeServiceImpl
                .getSpecifiedEmployees(employeeIds);

        return projectDaoImpl.assignEmployees(projectId, employees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getProjects() {
        List<String> projects = new ArrayList<String>();

        projectDaoImpl.getProjects().forEach((project) -> {
            projects.add(project.toString());
        });

        return projects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProject(int projectId) {
        Project project = projectDaoImpl.getProject(projectId);
        String projectDetails = project.toString();
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();

        List<Integer> employeeIds = new ArrayList<Integer>();
        project.getEmployees().forEach((employee) -> {
            employeeIds.add(employee.getId());
        });



        if (employeeIds.isEmpty()) {
            projectDetails = projectDetails + "\nNo Employee assigned\n";
        } else {
            List<Employee> employees = employeeServiceImpl.getSpecifiedEmployees(employeeIds);

            projectDetails = projectDetails + "\n-----Employees Assigned-----\n";

            if (!employees.isEmpty()) {
                for (Employee employee : employees) {
                    projectDetails = projectDetails + employee.toString();
                }
            }

            projectDetails = projectDetails + "--------------------\n";
        }

        return projectDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getSpecifiedProjects(List<Integer> projectIds) {
        List<Project> projects = new ArrayList<Project>();

        projectIds.forEach((projectId) -> {
            Project project = projectDaoImpl.getProject(projectId);

            if (null != project) {
                projects.add(project);
            }
        });

        return projects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateProject(int projectId, String projectTitle
            , String clientName, int managerId, Date startDate, Date endDate) {
        return projectDaoImpl.updateProject(new Project(projectId, projectTitle
                , clientName, managerId, startDate, endDate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEmployee(int projectId, int employeeId) {
        return projectDaoImpl.addEmployee(projectId, employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEmployee(int projectId, int employeeId) {
        return projectDaoImpl.removeEmployee(projectId, employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteProject(int projectId) {
        return projectDaoImpl.deleteProject(projectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getDeletedProjects() {
        List<String> deletedProjects = new ArrayList<String>();

        projectDaoImpl.getDeletedProjects().forEach((project) -> {
            deletedProjects.add(project.toString());
        });

        return deletedProjects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreDeleted(int projectId) {
        return projectDaoImpl.restoreDeleted(projectId);
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
    public boolean isEmployeePresent(int employeeId) {
        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        return employeeServiceImpl.isEmployeePresent(employeeId);
    }
}