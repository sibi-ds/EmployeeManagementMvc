package com.ideas2it.employeemanagement.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * this class works as a mediater between view and service by send and receive
 * the requests
 *
 * @author sibi
 * @created 2021-03-24
 */
public class ProjectController extends HttpServlet {
    
    private ProjectService projectServiceImpl = new ProjectServiceImpl();
    
    /**
     * process the user request and gives the correspoding response
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String choice = request.getParameter("action");
        
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
            case "unassign_employee":
                unassignEmployee(request, response);
                break;
            case "update_project":
                updateProject(request, response);
                break;
            default:
                errorPage(request, response);
        }
    }
    
    /**
     * process the user request and gives the correspoding response
     * 
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String choice = request.getParameter("action");
        
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
        
        if (projectServiceImpl.insertProject(title, clientName, managerId, startDate, endDate)) {
            request.setAttribute("message", "Project Created Successfully");
        } else {
            request.setAttribute("message", "Project Creation Failure");
        }
        
        request.getRequestDispatcher("/project.jsp").forward(request, response);
    }
    
    /**
     * get the details of all projects
     *
     * @param request  request from the user
     * @param response response to the request
     * 
     * @throws IOException
     * @throws ServletException
     */
    private void getAssignedEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        Project project = projectServiceImpl.getProject(projectId);
        List<Employee> employees = projectServiceImpl.getEmployees();
        
        request.setAttribute("employees", employees);
        request.setAttribute("assignedEmployees", project.getEmployees());
        
        request.getRequestDispatcher("/assign_employee.jsp").forward(request, response);
    }
    
    /**
     * assign employees to a project
     *
     * @param projectId for which employees need to be assigned
     * @param employees list of employee IDs
     *
     * @return true if assignation successful else false
     */
    /*
     * public void assignEmployees(HttpServletRequest request, HttpServletResponse
     * response) throws ServletException, IOException { int projectId =
     * Integer.parseInt(request.getParameter("project_id")); String employeeIds[] =
     * request.getParameterValues("employee_id"); List<Integer> employees = new
     * ArrayList<Integer>();
     * 
     * for (String employeeId : employeeIds) {
     * employees.add(Integer.parseInt(employeeId)); }
     * 
     * projectServiceImpl.assignEmployees(projectId, employees); }
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
        List<Project> projects = projectServiceImpl.getProjects();
        
        if (projects.isEmpty()) {
            request.setAttribute("message", "Empty Database");
        } else {
            request.setAttribute("projects", projects);
        }
        
        request.getRequestDispatcher("/display_projects.jsp").forward(request, response);
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
        
        if (isProjectPresent(projectId)) {
            Project project = projectServiceImpl.getProject(projectId);
            
            request.setAttribute("project", project);
        } else {
            request.setAttribute("message", "Project Not Present");
        }
        
        request.getRequestDispatcher("/display_project.jsp").forward(request, response);
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
        
        Project project = projectServiceImpl.getProject(projectId);
        
        request.setAttribute("project", project);
        
        request.getRequestDispatcher("/update_project.jsp").forward(request, response);
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
        
        if (projectServiceImpl.updateProject(projectId, title, clientName, managerId, startDate, endDate)) {
            request.setAttribute("message", "Updation Successful");
        } else {
            request.setAttribute("message", "Updation Failure");
        }
        
        request.getRequestDispatcher("/display_project.jsp").forward(request, response);
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
        
        if (null != employeeIds) {
            for (String employeeId : employeeIds) {
                employees.add(Integer.parseInt(employeeId));
            }
        }
        
        if (projectServiceImpl.assignEmployees(projectId, employees)) {
            request.setAttribute("message", "Employees Assigned Successfully");
        } else {
            request.setAttribute("message", "Employees Assignation Failure");
        }
        
        request.getRequestDispatcher("/display_project.jsp").forward(request, response);
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
    private void unassignEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        
        if (projectServiceImpl.unassignEmployee(projectId, employeeId)) {
            request.setAttribute("message", "Employee Unassigned Successfully");
        } else {
            request.setAttribute("message", "Employee Unassignation Failure");
        }
        
        request.getRequestDispatcher("/display_project.jsp").forward(request, response);
    }
    
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
        
        if (isProjectPresent(projectId)) {
            if (projectServiceImpl.deleteProject(projectId)) {
                request.setAttribute("message", "Project Deleted Successfully");
            } else {
                request.setAttribute("message", "Deletion Failure");
            }
        } else {
            request.setAttribute("message", "Project Not Exist");
        }
        
        request.getRequestDispatcher("/delete_project.jsp").forward(request, response);
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
        List<Project> deletedProjects = projectServiceImpl.getDeletedProjects();
        
        if (deletedProjects.isEmpty()) {
            request.setAttribute("message", "No Deleted Projects");
        } else {
            request.setAttribute("projects", deletedProjects);
        }
        
        request.getRequestDispatcher("/restore_project.jsp").forward(request, response);
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
        
        if (isProjectPresent(projectId)) {
            request.setAttribute("message", "Project Already Present");
        } else {
            if (!isProjectDeleted(projectId)) {
                request.setAttribute("message", "Project Never Exist");
            } else if (projectServiceImpl.restoreProject(projectId)) {
                request.setAttribute("message", "Project Restored Successfully");
            } else {
                request.setAttribute("message", "Restoration Failure");
            }
        }
        
        request.getRequestDispatcher("/restore_project.jsp").forward(request, response);
    }
    
    /**
     * checks whether the project present or not
     *
     * @param projectId projectId to verify the exixtence
     *
     * @return true if project present else false
     */
    private boolean isProjectPresent(int projectId) {
        return projectServiceImpl.isProjectPresent(projectId);
    }
    
    /**
     * checks whether the project deleted already or not
     *
     * @param projectId projectId to verify deletion
     *
     * @return true if project deleted already else false
     */
    private boolean isProjectDeleted(int projectId) {
        return projectServiceImpl.isProjectDeleted(projectId);
    }
    
    /**
     * checks whether the employee present or not
     *
     * @param employeeId employeeId to verify the exixtence
     *
     * @return true if employee present else false
     */
    private boolean isEmployeePresent(int employeeId) {
        return projectServiceImpl.isEmployeePresent(employeeId);
    }
}