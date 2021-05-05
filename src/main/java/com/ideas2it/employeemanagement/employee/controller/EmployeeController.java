package com.ideas2it.employeemanagement.employee.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.exceptions.EmployeeManagementException;
import com.ideas2it.loggers.EmployeeManagementLogger;

/**
 * this class works as a mediater between view and
 * service by send and receive the requests
 *
 * @author sibi
 * @created 2021-03-03
 */
@Controller
public class EmployeeController {
    
    private ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
    
    private EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");
    
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeController.class);
    
    /**
     * request dispatcher to redirect to index page
     * 
     * @return index page name
     */
    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }
    
    /**
     * request dispatcher to redirect to employee menu page
     * 
     * @return employee menu page name
     */
    @GetMapping("/employee")
    public String getEmployeePage() {
        return "employee";
    }
    
    /**
     * request dispatcher to redirect to employee creation page
     * 
     * @param employee employee details
     * 
     * @return employee creation page name
     */
    @GetMapping("/employeeCreationForm")
    public String getEmployeeCreationForm(@ModelAttribute("employee") Employee employee) {
        return "createEmployee";
    }
    
    /**
     * request dispatcher to redirect to display employee page
     * 
     * @return display employee page name
     */
    @GetMapping("/displayEmployee")
    public String getDisplayEmployeePage() {
        return "displayEmployee";
    }
    
    /**
     * stores employee details in database
     * 
     * @param employee employee details
     * 
     * @return modelAndView
     */
    @PostMapping(value = "/createEmployee")
    public ModelAndView insertEmployee(@ModelAttribute("employee") Employee employee,
            @RequestParam("isTemporaryAddressPresent") int status) {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            employeeService.insertEmployee(employee, status);
            modelAndView.addObject("message", "Employee Created Successfully");
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("message", e.getMessage());
        } finally {
            modelAndView.setViewName("/employee");
        }
        
        return modelAndView;
    }
    
    /**
     * get the details of all assigned projects
     * 
     * @param employeeId for which assigned projects
     *                   need to be retrieved
     *                   
     * @return modelAndView
     */
    @GetMapping(value = "/projectAssignPage/{employee_id}")
    public ModelAndView getAssignedProjects(@PathVariable("employee_id") int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            if (employeeService.isEmployeePresent(employeeId)) {
                Employee employee = employeeService.getEmployee(employeeId);
                List<Project> projects = employeeService.getProjects();
                
                modelAndView.addObject("projects", projects);
                modelAndView.addObject("assignedProjects", employee.getProjects());
                modelAndView.addObject("employee_id", employeeId);
            } else {
                modelAndView.addObject("message", "Something Went Wrong");
            }
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("message", e.getMessage());
        } finally {
            modelAndView.setViewName("/assignProjects");
        }
        
        return modelAndView;
    }
    
    /**
     * assign project to an employee
     * 
     * @param employeeId for which projects need to be
     *                   assigned
     * 
     * @return modelAndView
     */
    @PostMapping(value = "/assignProjects")
    public ModelAndView assignProjects(@RequestParam("employee_id") int employeeId,
            @RequestParam(value = "check", required = false) String projectIds[]) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            modelAndView.addObject("message"
                    , employeeService.assignProjects(employeeId, projectIds));
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("message", e.getMessage());
        } finally {
            modelAndView.setViewName("/displayEmployee");
        }
        
        return modelAndView;
    }
    
    /**
     * get the details of all employees
     * 
     * @return modelAndView
     */
    @GetMapping(value = "/displayAllEmployees")
    public ModelAndView getEmployees() {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            List<Employee> employees = employeeService.getEmployees(false);
            
            if (employees.isEmpty()) {
                modelAndView.addObject("message", "Empty Database");
            } else {
                modelAndView.addObject("employees", employees);
            }
        } catch (EmployeeManagementException e) {
            modelAndView.addObject("message", e.getMessage());
            logger.logError(e);
        } finally {
            modelAndView.setViewName("/displayEmployees");
        }
        
        return modelAndView;
    }
    
    /**
     * get the details of an employee
     * 
     * @param employeeId for which details need to be
     *                   retrieved
     * 
     * @return modelAndView
     */
    @PostMapping(value = "/displayEmployeeDetails")
    public ModelAndView getEmployee(@RequestParam("employee_id") int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            if (employeeService.isEmployeePresent(employeeId)) {
                Employee employee = employeeService.getEmployee(employeeId);
                modelAndView.addObject("employee", employee);
            } else {
                modelAndView.addObject("message", "Employee Not Present");
            }
        } catch (EmployeeManagementException e) {
            modelAndView.addObject("message", e.getMessage());
            logger.logError(e);
        } finally {
            modelAndView.setViewName("/displayEmployee");
        }
        
        return modelAndView;
    }
    
    /**
     * get the basic details and addresses of an
     * employee
     * 
     * @param employeeId for which details need to be
     *                   retrieved
     * 
     * @return modelAndView
     */
    @GetMapping(value = "/getEmployee/{employee_id}")
    public ModelAndView getEmployeeDetails(@PathVariable("employee_id") int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            if (employeeService.isEmployeePresent(employeeId)) {
                Employee employee = employeeService.getEmployeeAndAddresses(employeeId);
                modelAndView.addObject("employee", employee);
                modelAndView.addObject("permanentAddress", employee.getAddresses().get(0));
                
                if (employee.getAddresses().size() > 1) {
                    modelAndView.addObject("temporaryAddress", employee.getAddresses().get(1));
                }
                    
                modelAndView.setViewName("/createEmployee");
            } else {
                modelAndView.addObject("message", "Something Went Wrong");
                modelAndView.setViewName("/employee");
            }
        } catch (EmployeeManagementException e) {
            modelAndView.addObject("message", e.getMessage());
            logger.logError(e);
            modelAndView.setViewName("/employee");
        }
        
        return modelAndView;
    }
    
    /**
     * updates details of an employee
     * 
     * @param employee  employee details
     * @param isPresent to determine whether temporary
     *                  address present or not
     *                  
     * @return modelAndView
     */
    @PostMapping(value = "/updateEmployee")
    public ModelAndView updateEmployee(@ModelAttribute("employee") Employee employee,
            @RequestParam("isTemporaryAddressPresent") int status) {
        ModelAndView modelAndView = new ModelAndView();
        
        try {            
            employeeService.updateEmployee(employee, status);
            modelAndView.addObject("message", "Employee Updated Successfully");
        } catch (EmployeeManagementException e) {
            modelAndView.addObject("message", e.getMessage());
            logger.logError(e);
        } finally {
            modelAndView.setViewName("/employee");
        }
        
        return modelAndView;
    }
    
    /**
     * removes the details of an employee
     * 
     * @param employeeId which need to be deleted
     * 
     * @return modelAndView
     */
    @GetMapping(value = "/deleteEmployee/{employeeId}")
    public ModelAndView deleteEmployee(@PathVariable int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            modelAndView.addObject("message"
                    , employeeService.deleteEmployee(employeeId));
        } catch (EmployeeManagementException e) {
            modelAndView.addObject("message", e.getMessage());
            logger.logError(e);
        } finally {
            modelAndView.setViewName("/displayEmployee");
        }
        
        return modelAndView;
    }
    
    /**
     * get deleted employees list
     * 
     * @return modelAndView
     */
    @GetMapping(value = "/getDeletedEmployees")
    public ModelAndView getDeletedEmployees() {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            List<Employee> deletedEmployees = employeeService.getEmployees(true);
            
            if (deletedEmployees.isEmpty()) {
                modelAndView.addObject("message", "No Deleted Employees");
            } else {
                modelAndView.addObject("employees", deletedEmployees);
            }
        } catch (EmployeeManagementException e) {
            modelAndView.addObject("employees", e.getMessage());
            logger.logError(e);
        } finally {
            modelAndView.setViewName("/restoreEmployee");
        }
        
        return modelAndView;
    }
    
    /**
     * restores deleted employee's details
     * 
     * @param employeeId which need to be restored
     * 
     * @return modelAndView
     */
    @GetMapping(value = "/restoreEmployee/{employee_id}")
    public ModelAndView restoreEmployee(@PathVariable("employee_id") int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        
        try {
            modelAndView.addObject("message"
                    , employeeService.restoreEmployee(employeeId));
        } catch (EmployeeManagementException e) {
            modelAndView.addObject("message", e.getMessage());
            logger.logError(e);
        } finally {
            modelAndView.setViewName("/restoreEmployee");
        }
        
        return modelAndView;
    }
}