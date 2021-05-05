package com.ideas2it.employeemanagement.project.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.project.service.ProjectService;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.exceptions.EmployeeManagementException;
import com.ideas2it.loggers.EmployeeManagementLogger;

/**
 * this class works as a mediater between view and
 * service by send and receive the requests
 *
 * @author sibi
 * @created 2021-03-24
 */
public class ProjectController extends HttpServlet {
    
    private ProjectService projectServiceImpl = new ProjectServiceImpl();
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(ProjectController.class);
    
    /**
     * process the user request and gives the
     * corresponding response
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String choice = request.getParameter("action");
        
        try {
            switch (choice) {
                case "create_project":
                    insertProject(request, response);
                    break;
                case "display_project":
                    getProject(request, response);
                    break;
                case "delete_project":
                    deleteProject(request, response);
                    break;
                case "restore_project":
                    restoreProject(request, response);
                    break;
                case "assign_employees":
                    assignEmployees(request, response);
                    break;
                case "update_project":
                    updateProject(request, response);
                    break;
                default:
                    errorPage(request, response);
            }
        } catch (ServletException | IOException e) {
            logger.logError(e);
        }
    }
    
    /**
     * process the user request and gives the
     * corresponding response
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String choice = request.getParameter("action");
        
        try {
            switch (choice) {
                case "delete_project":
                    deleteProject(request, response);
                    break;
                case "restore_project":
                    restoreProject(request, response);
                    break;
                case "display_all_projects":
                    getProjects(request, response);
                    break;
                case "get_deleted_projects":
                    getDeletedProjects(request, response);
                    break;
                case "get_project_details":
                    getProjectDetails(request, response);
                    break;
                case "get_assigned_employees":
                    getAssignedEmployees(request, response);
                    break;
                default:
                    errorPage(request, response);
            }
        } catch (ServletException | IOException e) {
            logger.logError(e);
        }
    }
    
    /**
     * redirects to error page if error occurs
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void errorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("error_page.jsp");
    }
    
    /**
     * stores project details in the database
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void insertProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String clientName = request.getParameter("client_name");
        int managerId = Integer.parseInt(request.getParameter("manager_id"));
        Date startDate = Date.valueOf(request.getParameter("start_date"));
        Date endDate = Date.valueOf(request.getParameter("end_date"));
        
        try {
            projectServiceImpl.insertProject(title, clientName, managerId, startDate, endDate);
            request.setAttribute("message", "Project Created Successfully");
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/project.jsp").forward(request, response);
        }
    }
    
    /**
     * get the details of all projects
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     * @throws EmployeeManagementException
     */
    private void getAssignedEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        
        try {
            Project project = projectServiceImpl.getProject(projectId);
            List<Employee> employees = projectServiceImpl.getEmployees();
            
            request.setAttribute("employees", employees);
            request.setAttribute("assignedEmployees", project.getEmployees());
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/assign_employees.jsp").forward(request, response);
        }
    }
    
    /**
     * assign employees to a project
     *
     * @param projectId for which employees need to be
     *                  assigned
     * @param employees list of employee IDs
     *
     * @return true if assignation successful else
     *         false
     */
    /*
     * public void assignEmployees(HttpServletRequest
     * request, HttpServletResponse response) throws
     * ServletException, IOException { int projectId =
     * Integer.parseInt(request.getParameter(
     * "project_id")); String employeeIds[] =
     * request.getParameterValues("employee_id");
     * List<Integer> employees = new
     * ArrayList<Integer>();
     * 
     * for (String employeeId : employeeIds) {
     * employees.add(Integer.parseInt(employeeId)); }
     * 
     * projectServiceImpl.assignEmployees(projectId,
     * employees); }
     */
    
    /**
     * get the details of all projects
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getProjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Project> projects = projectServiceImpl.getProjects();
            
            if (projects.isEmpty()) {
                request.setAttribute("message", "Empty Database");
            } else {
                request.setAttribute("projects", projects);
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/display_projects.jsp").forward(request, response);
        }
    }
    
    /**
     * get the details of a project
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        
        try {
            if (projectServiceImpl.isProjectPresent(projectId)) {
                Project project = projectServiceImpl.getProject(projectId);
                request.setAttribute("project", project);
            } else {
                request.setAttribute("message", "Project Not Present");
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/display_project.jsp").forward(request, response);
        }
    }
    
    /**
     * get the details of a project
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getProjectDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        
        try {
            Project project = projectServiceImpl.getProject(projectId);
            request.setAttribute("project", project);
        } catch (EmployeeManagementException e) {
            request.setAttribute("project", e.getMessage());
        } finally {
            request.getRequestDispatcher("/create_project.jsp").forward(request, response);
        }
    }
    
    /**
     * updates project details
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void updateProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        String title = request.getParameter("title");
        String clientName = request.getParameter("client_name");
        int managerId = Integer.parseInt(request.getParameter("manager_id"));
        Date startDate = Date.valueOf(request.getParameter("start_date"));
        Date endDate = Date.valueOf(request.getParameter("end_date"));
        
        try {
            projectServiceImpl.updateProject(projectId, title, clientName, managerId, startDate, endDate);
            request.setAttribute("message", "Updation Successful");
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/display_project.jsp").forward(request, response);
        }
    }
    
    /**
     * assign employee to a project
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void assignEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        String employeeIds[] = request.getParameterValues("check");
        List<Integer> employees = new ArrayList<Integer>();
        String message;
        
        if (null != employeeIds) {
            for (String employeeId : employeeIds) {
                employees.add(Integer.parseInt(employeeId));
            }
            
            message = "Employees Assigned Successfully";
        } else {
            message = "No Employees Assigned";
        }
        
        try {
            projectServiceImpl.assignEmployees(projectId, employees);
            request.setAttribute("message", message);
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/display_project.jsp").forward(request, response);
        }
    }
    
    /**
     * unassign employee from a project
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
//    private void unassignEmployee(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int projectId = Integer.parseInt(request.getParameter("project_id"));
//        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
//        
//        if (projectServiceImpl.unassignEmployee(projectId, employeeId)) {
//            request.setAttribute("message", "Employee Unassigned Successfully");
//        } else {
//            request.setAttribute("message", "Employee Unassignation Failure");
//        }
//        
//        request.getRequestDispatcher("/display_project.jsp").forward(request, response);
//    }
    
    /**
     * removes the details of a project
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void deleteProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        
        try {
            if (projectServiceImpl.isProjectPresent(projectId)) {
                projectServiceImpl.deleteProject(projectId);
                request.setAttribute("message", "Project Deleted Successfully");
            } else {
                request.setAttribute("message", "Project Not Present");
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/delete_project.jsp").forward(request, response);
        }
    }
    
    /**
     * get deleted projects list
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getDeletedProjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Project> deletedProjects = projectServiceImpl.getDeletedProjects();
            
            if (deletedProjects.isEmpty()) {
                request.setAttribute("message", "No Deleted Projects");
            } else {
                request.setAttribute("projects", deletedProjects);
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/restore_project.jsp").forward(request, response);
        }
    }
    
    /**
     * restores the deleted project
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void restoreProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        
        try {
            if (projectServiceImpl.isProjectDeleted(projectId)) {
                projectServiceImpl.restoreProject(projectId);
                request.setAttribute("message", "Project Restored Successfully");
            } else {
                request.setAttribute("message", "Project ID Not In The Restoration List");
            }
        } catch (EmployeeManagementException e) {
            request.setAttribute("message", e.getMessage());
        } finally {
            request.getRequestDispatcher("/restore_project.jsp").forward(request, response);
        }
    }
}