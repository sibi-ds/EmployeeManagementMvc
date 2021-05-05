package com.ideas2it.employeemanagement.employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
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
 * @created 2021-03-03
 */
public class EmployeeServiceImpl implements EmployeeService {
    
    private EmployeeDao employeeDao;
    private ProjectService projectService;
    
    public EmployeeServiceImpl(EmployeeDao employeeDao, ProjectService projectService) {
        this.employeeDao = employeeDao;
        this.projectService = projectService;
    }
    
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeServiceImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEmployee(Employee employee, int status) throws EmployeeManagementException {
        try {
            if (0 == status) {
                employee.getAddresses().remove(1);
            }
            
            employeeDao.insertEmployee(employee);
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee Insertion Failure");
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getProjects() throws EmployeeManagementException {
        return projectService.getProjects();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String assignProjects(int employeeId, String projectIds[]) throws EmployeeManagementException {
        String message = null;
        List<Integer> projectsIds = new ArrayList<Integer>();
        
        try {
            if (employeeDao.isEmployeePresent(employeeId)) {
                if (null != projectIds) {
                    for (String projectId : projectIds) {
                        projectsIds.add(Integer.parseInt(projectId));
                    }
                
                    message = "Projects Assigned Successfully";
                } else {
                    message = "Employee Is Not Assigned With Any Projects";
                }
            } else {
                message = "Something Went Wrong";
            }
            
            Employee employee = employeeDao.getEmployee(employeeId);
            ProjectService projectService = new ProjectServiceImpl();
            List<Project> projects = projectService.getSpecifiedProjects(projectsIds);
            employee.setProjects(projects);
            employeeDao.updateEmployee(employee);
        } catch (EmployeeManagementException | NumberFormatException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Projects Assignation Failure");
        }
        
        return message;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees(boolean activeStatus) throws EmployeeManagementException {
        return employeeDao.getEmployees(activeStatus);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) throws EmployeeManagementException {
        return employeeDao.getEmployee(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployeeAndAddresses(int employeeId) throws EmployeeManagementException {
        return employeeDao.getEmployeeAndAddresses(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds) throws EmployeeManagementException {
        return employeeDao.getSpecifiedEmployees(employeeIds);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEmployee(Employee employee, int status) throws EmployeeManagementException {
        try {
            if ((0 == status)) {
                if (0 == employee.getAddresses().get(1).getAddressId()) {
                    employee.getAddresses().remove(1);
                } else {
                    employee.getAddresses().get(1).setIsDeleted(true);
                }
            }
            
            Employee updatedEmployee = employeeDao.getEmployee(employee.getId());
            
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setDateOfBirth(employee.getDateOfBirth());
            updatedEmployee.setSalary(employee.getSalary());
            updatedEmployee.setMobileNumber(employee.getMobileNumber());
            updatedEmployee.setAddresses(employee.getAddresses());
            
            employeeDao.updateEmployee(updatedEmployee);
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee Updation Failure");
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteEmployee(int employeeId) throws EmployeeManagementException {
        return deleteOrRestoreEmployee(employeeId, true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String restoreEmployee(int employeeId) throws EmployeeManagementException {
        return deleteOrRestoreEmployee(employeeId, false);
    }
    
    /**
     * deletes or restores an employee details
     *
     * @param employeeId which need to be extracted
     *                   from database
     * @param status     determines whether to delete
     *                   or restore
     *
     * @return true if successful else false
     * 
     * @throws FetchFailureException
     */
    private String deleteOrRestoreEmployee(int employeeId, boolean status) throws EmployeeManagementException {
        String message;
        boolean check = false;
        
        try {
            if (status) {
                check = isEmployeePresent(employeeId);
            } else {
                check = isEmployeeDeleted(employeeId);
            }
            
            if (check) {
                Employee employee = employeeDao.getEmployee(employeeId);
                employee.setIsDeleted(status);
                
                employee.getAddresses().forEach((address) -> {
                    address.setIsDeleted(status);
                });
                
                if (status) {
                    employee.setProjects(null);
                }
                
                employeeDao.updateEmployee(employee);
                
                message = status ? "Deletion Successful" : "Restoration Successful";
            } else {
                message = "Something Went Wrong";
            }
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            throw new EmployeeManagementException(status ? "Deletion Failure" : "Restoration Failure");
        }
        
        return message;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) throws EmployeeManagementException {
        return employeeDao.isEmployeePresent(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeeDeleted(int employeeId) throws EmployeeManagementException {
        return employeeDao.isEmployeeDeleted(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) throws EmployeeManagementException {
        return projectService.isProjectPresent(projectId);
    }
}