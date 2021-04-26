package com.ideas2it.employeemanagement.employee.service;

import java.sql.Date;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.exceptions.EmployeeManagementException;

/**
 * interface class of the employee service
 *
 * @author sibi
 * @created 2021-03-03
 */
public interface EmployeeService {
    
    /**
     * stores the employee details
     * 
     * @param name         name of the employee
     * @param dob          Date Of Birth of the
     *                     employee
     * @param salary       salary of the employee
     * @param mobileNumber mobile number of the
     *                     employee
     * @param addresses    list containing addresses
     *
     * @return true if employee details stored
     *         successfully else false
     *         
     * @throws EmployeeManagementException
     */
    public void insertEmployee(String name, Date dob, float salary, String mobileNumber
            , List<List<String>> addresses) throws EmployeeManagementException;
    
    /**
     * assign project to an employee
     *
     * @param employeeId for which project need to be
     *                   assigned
     * @param projectId  which project to be assigned
     *
     * @return true if project assigned successfully
     *         else false
     * 
     * @throws EmployeeManagementException
     */
    public String assignProject(int employeeId, int projectId) throws EmployeeManagementException;
    
    /**
     * unassign project of an employee
     *
     * @param employeeId for which project to be
     *                   unassigned
     * @param projectId  which project to be
     *                   unassigned
     *
     * @return true if project assigned successfully
     *         else false
     * 
     * @throws EmployeeManagementException
     */
    public String unassignProject(int employeeId, int projectId) throws EmployeeManagementException;
    
    /**
     * get the details of all employees
     *
     * @return list containing all employees details
     * 
     * @throws EmployeeManagementException
     */
    public List<Employee> getEmployees() throws EmployeeManagementException;
    
    /**
     * get the details of the employee
     *
     * @param employeeId which need to be retrieved
     *                   from the database
     *                  
     * @return list containing employee details
     *                   
     * @throws EmployeeManagementException
     */
    public Employee getEmployee(int employeeId) throws EmployeeManagementException;
    
    /**
     * get the basic details and corresponding
     * addresses of an employee
     *
     * @param employeeId whose details need to be
     *                   retrieved from the database
     *                  
     * @return employee details along with projects
     *                   
     * @throws EmployeeManagementException
     */
    public Employee getEmployeeAndAddresses(int employeeId) throws EmployeeManagementException;
    
    /**
     * get the details of the employees in the list
     *
     * @param employeeIds list of employee Ids
     *
     * @return list containing employees
     * 
     * @throws EmployeeManagementException
     */
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds)
            throws EmployeeManagementException;
    
    /**
     * get the details of the employee
     *
     * @param employeeId whose details need to be
     *                   retrieved
     *
     * @return employee details
     * 
     * @throws EmployeeManagementException
     */
//    public Employee getSpecifiedEmployee(int employeeId) throws EmployeeManagementException;
    
    /**
     * get all addresses of an employee
     *
     * @param employeeId whose addresses need to be
     *                   retrieved
     *
     * @return map containing address IDs and
     *         corresponding addresses
     * 
     * @throws EmployeeManagementException
     */
//    public Map<Integer, String> getAddresses(int employeeId);
    
    /**
     * updates details of an employee
     *
     * @param employeeId   for which details should be
     *                     updated
     * @param name         name of the employee
     * @param dob          Date Of Birth of the
     *                     employee
     * @param salary       salary of the employee
     * @param mobileNumber mobile number of the
     *                     employee
     * @param addresses    list of addresses
     *
     * @return true if updation successful else false
     * 
     * @throws EmployeeManagementException
     */
    public void updateEmployee(int employeeId, String name, Date dob, float salary, String mobileNumber,
            List<List<String>> addresses) throws EmployeeManagementException;
    
    /**
     * add address to an employee
     *
     * @param employeeId for which address to be added
     * @param address    employee address details
     *
     * @return true if address insertion successful
     *         else false
     */
//    public boolean addAddress(int employeeId, List<String> address);
    
    /**
     * updates address of an employee
     *
     * @param employeeId   employee ID for which
     *                     details to be updated
     * @param addressIndex position of the address in
     *                     the address list
     * @param addressId    address ID for which
     *                     details to be updated
     * @param address      updated values of the
     *                     employee address
     *
     * @return true if address updation successful
     *         else false
     */
//    public boolean updateAddressValues(int employeeId, int addressIndex, int addressId, List<String> address);
    
    /**
     * removes an address of an employee
     *
     * @param employeeId   for which an address to be
     *                     deleted
     * @param addressIndex position of the address in
     *                     the address list
     *                    
     * @return true if address deletion successful
     *         else false
     */
//    public boolean deleteAddress(int employeeId, int addressIndex);
    
    /**
     * removes the details of an employee
     *
     * @param employeeId whose details need to be
     *                   deleted
     *
     * @return true if deletion successful else false
     * 
     * @throws EmployeeManagementException
     */
    public void deleteEmployee(int employeeId) throws EmployeeManagementException;
    
    /**
     * get the deleted employees list
     * 
     * @return list of deleted employees
     * 
     * @throws EmployeeManagementException
     */
    public List<Employee> getDeletedEmployees() throws EmployeeManagementException;
    
    /**
     * restores the deleted employee
     * 
     * @param employeeId whose details need to be
     *                   restored
     * 
     * @return true if restoration successful else
     *         false
     * 
     * @throws EmployeeManagementException
     */
    public void restoreEmployee(int employeeId) throws EmployeeManagementException;
    
    /**
     * checks whether the details of an employee
     * present or not
     *
     * @param employeeId employeeId to verify its
     *                   existence
     *
     * @return returns true if employee present else
     *         return false
     * 
     * @throws EmployeeManagementException
     */
    public boolean isEmployeePresent(int employeeId) throws EmployeeManagementException;
    
    /**
     * checks whether the details of an employee
     * deleted already or not
     *
     * @param employeeId employeeId to verify its
     *                   deletion
     *
     * @return returns true if employee deleted
     *         already else false
     *         
     * @throws EmployeeManagementException
     */
    public boolean isEmployeeDeleted(int employeeId) throws EmployeeManagementException;
    
    /**
     * checks whether project present or not
     *
     * @param projectId whose existence need to be
     *                  checked
     *
     * @return true if project present else false
     * 
     * @throws EmployeeManagementException
     */
    public boolean isProjectPresent(int projectId) throws EmployeeManagementException;
}