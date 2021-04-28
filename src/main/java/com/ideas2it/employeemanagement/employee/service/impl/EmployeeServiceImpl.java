package com.ideas2it.employeemanagement.employee.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.Address;
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
    
    private EmployeeDao employeeDaoImpl = new EmployeeDaoImpl();
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeServiceImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEmployee(String name, Date dob, float salary, String mobileNumber, List<List<String>> addresses)
            throws EmployeeManagementException {
        try {
            List<Address> employeeAddresses = new ArrayList<Address>();
            
            addresses.forEach((address) -> {
                employeeAddresses.add(new Address(address.get(0), address.get(1), address.get(2), address.get(3),
                        address.get(4), address.get(5), address.get(6), false));
            });
            
            Employee employee = new Employee(name, dob, salary, mobileNumber, employeeAddresses, false);
            employeeDaoImpl.insertEmployee(employee);
        } catch (Exception e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee Insertion Failure");
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String assignProject(int employeeId, int projectId) throws EmployeeManagementException {
        String message = null;
        
        try {
            ProjectService projectServiceImpl = new ProjectServiceImpl();
            Employee employee = employeeDaoImpl.getEmployeeAndProjects(employeeId);
            List<Project> projects = employee.getProjects();
            boolean isAlreadyAssigned = false;
            
            for (Project projectDetails : projects) {
                if (projectId == projectDetails.getId()) {
                    isAlreadyAssigned = true;
                    break;
                }
            }
            
            if (isAlreadyAssigned) {
                message = "Project Already Assigned";
            } else {
                projects.add(projectServiceImpl.getSpecifiedProject(projectId));
                employee.setProjects(projects);
                
                employeeDaoImpl.updateEmployee(employee);
                message = "Project Assigned Successfully";
            }
        } catch (Exception e) {
            logger.logError(e);
            throw new EmployeeManagementException("Project Assignation Failure");
        }
        
        return message;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String unassignProject(int employeeId, int projectId) throws EmployeeManagementException {
        String message = null;
        
        try {
            Employee employee = employeeDaoImpl.getEmployeeAndProjects(employeeId);
            List<Project> projects = employee.getProjects();
            
            for (Project project : projects) {
                if (projectId == project.getId()) {
                    projects.remove(project);
                    break;
                }
            }
            
            employee.setProjects(projects);
            employeeDaoImpl.updateEmployee(employee);
            message = "Project Unassigned Successfully";
        } catch (Exception e) {
            logger.logError(e);
            throw new EmployeeManagementException("Project Unassignation Failure");
        }
        
        return message;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() throws EmployeeManagementException {
        return employeeDaoImpl.getEmployees();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) throws EmployeeManagementException {
        return employeeDaoImpl.getEmployee(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployeeAndAddresses(int employeeId) throws EmployeeManagementException {
        return employeeDaoImpl.getEmployeeAndAddresses(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds) throws EmployeeManagementException {
        return employeeDaoImpl.getSpecifiedEmployees(employeeIds);
    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public Employee getSpecifiedEmployee(int employeeId) throws EmployeeManagementException {
//        return employeeDaoImpl.getSpecifiedEmployee(employeeId);
//    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public Map<Integer, String> getAddresses(int employeeId) {
//        Employee employee = employeeDaoImpl.getEmployee(employeeId);
//        
//        Map<Integer, Address> employeeAddresses = new LinkedHashMap<Integer, Address>();
//        
//        employee.getAddresses().forEach((address) -> {
//            if (!address.getIsDeleted()) {
//                employeeAddresses.put(address.getAddressId(), address);
//            }
//        });
//        
//        Map<Integer, String> addresses = new LinkedHashMap<Integer, String>();
//        
//        int serialNumber = 1;
//        
//        for (Integer addressId : employeeAddresses.keySet()) {
//            if (!employeeAddresses.get(addressId).getIsDeleted()) {
//                String address = employeeAddresses.get(addressId).toString();
//                String addressDetails = "Serial Number : " + serialNumber + address;
//                addresses.put(addressId, addressDetails);
//                serialNumber++;
//            }
//        }
//        
//        return addresses;
//    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEmployee(int employeeId, String name, Date dob, float salary, String mobileNumber,
            List<List<String>> addresses) throws EmployeeManagementException {
        try {
            Employee employee = employeeDaoImpl.getEmployeeAndAddresses(employeeId);
            
            employee.setName(name);
            employee.setDateOfBirth(dob);
            employee.setSalary(salary);
            employee.setMobileNumber(mobileNumber);
            
            List<Address> oldAddresses = employee.getAddresses();
            
            if (1 == addresses.size()) {
                List<String> address = addresses.get(0);
                oldAddresses.get(0).setAddressId(Integer.parseInt(address.get(0)));
                oldAddresses.get(0).setAddressType(address.get(1));
                oldAddresses.get(0).setDoorNumber(address.get(2));
                oldAddresses.get(0).setStreet(address.get(3));
                oldAddresses.get(0).setVillage(address.get(4));
                oldAddresses.get(0).setDistrict(address.get(5));
                oldAddresses.get(0).setState(address.get(6));
                oldAddresses.get(0).setPincode(address.get(7));
                oldAddresses.get(0).setEmployee(employee);
            } else if (1 < addresses.size()) {
                int addressIndex = 0;
                
                for (List<String> address : addresses) {
                    oldAddresses.get(addressIndex).setAddressId(Integer.parseInt(address.get(0)));
                    oldAddresses.get(addressIndex).setAddressType(address.get(1));
                    oldAddresses.get(addressIndex).setDoorNumber(address.get(2));
                    oldAddresses.get(addressIndex).setStreet(address.get(3));
                    oldAddresses.get(addressIndex).setVillage(address.get(4));
                    oldAddresses.get(addressIndex).setDistrict(address.get(5));
                    oldAddresses.get(addressIndex).setState(address.get(6));
                    oldAddresses.get(addressIndex).setPincode(address.get(7));
                    oldAddresses.get(addressIndex).setEmployee(employee);
                    addressIndex++;
                }
            }
            
            employee.setAddresses(oldAddresses);
            employeeDaoImpl.updateEmployee(employee);
        } catch (Exception e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee Updation Failure");
        }
        
        //return message;
    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public boolean addAddress(int employeeId, List<String> address) {
//        Employee employee = employeeDaoImpl.getEmployee(employeeId);
//        List<Address> addresses = employee.getAddresses();
//        addresses.add(new Address(address.get(0), address.get(1), address.get(2), address.get(3), address.get(4),
//                address.get(5), address.get(6), false));
//        employee.setAddresses(addresses);
//        return employeeDaoImpl.updateEmployee(employee);
//    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public boolean updateAddressValues(int employeeId, int addressIndex, int addressId, List<String> address) {
//        Employee employee = employeeDaoImpl.getEmployee(employeeId);
//        List<Address> addresses = employee.getAddresses();
//        
//        Address employeeAddress = new Address(address.get(0), address.get(1), address.get(2), address.get(3),
//                address.get(4), address.get(5), address.get(6), false);
//        employeeAddress.setEmployee(employee);
//        employeeAddress.setAddressId(addressId);
//        addresses.set(addressIndex, employeeAddress);
//        employee.setAddresses(addresses);
//        return employeeDaoImpl.updateEmployee(employee);
//    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public boolean deleteAddress(int employeeId, int addressIndex) {
//        Employee employee = employeeDaoImpl.getEmployee(employeeId);
//        List<Address> addresses = employee.getAddresses();
//        addresses.get(addressIndex).setIsDeleted(true);
//        employee.setAddresses(addresses);
//        return employeeDaoImpl.updateEmployee(employee);
//    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEmployee(int employeeId) throws EmployeeManagementException {
        deleteOrRestoreEmployee(employeeId, true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getDeletedEmployees() throws EmployeeManagementException {
        return employeeDaoImpl.getDeletedEmployees();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreEmployee(int employeeId) throws EmployeeManagementException {
        deleteOrRestoreEmployee(employeeId, false);
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
    private void deleteOrRestoreEmployee(int employeeId, boolean status) throws EmployeeManagementException {
        try {
            Employee employee = employeeDaoImpl.getEmployee(employeeId);
            employee.setIsDeleted(status);

            employee.getAddresses().forEach((address) -> {
                address.setIsDeleted(status);
            });
            
            if (status) {
                employee.setProjects(null);
            }
            
            employeeDaoImpl.updateEmployee(employee);
        } catch (Exception e) {
            logger.logError(e);
            String failureMessage = status ? "Deletion Failure" : "Restoration Failure";
            throw new EmployeeManagementException(failureMessage);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) throws EmployeeManagementException {
        return employeeDaoImpl.isEmployeePresent(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeeDeleted(int employeeId) throws EmployeeManagementException {
        return employeeDaoImpl.isEmployeeDeleted(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) throws EmployeeManagementException {
        ProjectService projectServiceImpl = new ProjectServiceImpl();
        return projectServiceImpl.isProjectPresent(projectId);
    }
}