package com.ideas2it.employeemanagement.employee.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.sessionfactory.HibernateUtility;
import com.ideas2it.employeemanagement.project.service.ProjectService;

/**
 * this class receives the request from the controller to store the details
 * and send back the details as per the request of the controller
 * 
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDaoImpl = new EmployeeDaoImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(String name, Date dob, float salary, String mobileNumber
            , List<List<String>> addresses) {
        List<Address> employeeAddresses = new ArrayList<Address>();
		
        addresses.forEach((address) -> {
            employeeAddresses.add(new Address(address.get(0), address.get(1), address.get(2)
                    , address.get(3), address.get(4), address.get(5), address.get(6), false));
        });

        Employee employee = new Employee(name, dob, salary, mobileNumber, employeeAddresses, false);

        return employeeDaoImpl.insertEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignProject(int employeeId, int projectId) {
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
	    return false;
	} else {
	    projects.add(projectServiceImpl.getSpecifiedProject(projectId));
            employee.setProjects(projects);
            return employeeDaoImpl.updateEmployee(employee);
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unassignProject(int employeeId, int projectId) {
        Employee employee = employeeDaoImpl.getEmployeeAndProjects(employeeId);
        List<Project> projects = employee.getProjects();

        for (Project project : projects) {
            if (projectId == project.getId()) {
                projects.remove(project);
                break;
            }
        }

        employee.setProjects(projects);

        return employeeDaoImpl.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getEmployees() {
        List<String> employees = new ArrayList<String>();

        employeeDaoImpl.getEmployees().forEach((employee) -> {
            employees.add(employee.getId() + "," + employee.getName()
                    + "," + employee.getDateOfBirth() + "," + employee.getSalary()
        	    + "," + employee.getMobileNumber());
        });

        return employees;
    }

    public List<List<String>> getEmployee(int employeeId) {
    	Employee employee = employeeDaoImpl.getEmployee(employeeId);
    	List<String> employeeBasicDetails = new ArrayList<String>();
    	List<String> addresses = new ArrayList<String>();
    	List<String> projects = new ArrayList<String>();
    	List<List<String>> employeeDetails = new ArrayList<List<String>>();
    	
    	employeeBasicDetails.add("" + employee.getId());
    	employeeBasicDetails.add(employee.getName());
    	employeeBasicDetails.add("" + employee.getDateOfBirth());
    	employeeBasicDetails.add("" + employee.getSalary());
    	employeeBasicDetails.add("" + employee.getMobileNumber());
    	
    	employee.getAddresses().forEach((address) -> {
    	    addresses.add(address.getAddressType() + "," + address.getDoorNumber()
    	            + "," + address.getStreet() + "," + address.getVillage() + ","
    		    + address.getDistrict() + "," + address.getState() + "," + address.getPincode());
    	});
    	
    	employee.getProjects().forEach((project) -> {
    	    projects.add(project.getId() + "," + project.getTitle()
    		    + "," + project.getClientName() + "," + project.getManagerId() + ","
    		    + project.getStartDate() + "," + project.getEndDate());
    	});

    	employeeDetails.add(employeeBasicDetails);
    	employeeDetails.add(addresses);
    	employeeDetails.add(projects);
    	
    	return employeeDetails;
    }

    public List<List<String>> getEmployeeAndAddresses(int employeeId) {
    	Employee employee = employeeDaoImpl.getEmployeeAndAddresses(employeeId);
    	List<String> employeeBasicDetails = new ArrayList<String>();
    	List<List<String>> employeeDetails = new ArrayList<List<String>>();
    	
    	employeeBasicDetails.add("" + employee.getId());
    	employeeBasicDetails.add(employee.getName());
    	employeeBasicDetails.add("" + employee.getDateOfBirth());
    	employeeBasicDetails.add("" + employee.getSalary());
    	employeeBasicDetails.add("" + employee.getMobileNumber());
    	
    	employeeDetails.add(employeeBasicDetails);
    	
    	for (Address address : employee.getAddresses()) {
            List<String> addressDetails = new ArrayList<String>();

            addressDetails.add("" + address.getAddressId());
            addressDetails.add(address.getAddressType());
            addressDetails.add(address.getDoorNumber());
            addressDetails.add(address.getStreet());
            addressDetails.add(address.getVillage());
            addressDetails.add(address.getDistrict());
            addressDetails.add(address.getState());
            addressDetails.add(address.getPincode());

            employeeDetails.add(addressDetails);
    	}

    	return employeeDetails;
    }
    
    /**
     * {@inheritDoc}
     */
/*    @Override
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds) {
        List<Employee> employees = new ArrayList<Employee>();

        employeeIds.forEach((employeeId) -> {
            employees.add(employeeDaoImpl.getSpecifiedEmployee(employeeId));
        });

        return employees;
    }*/

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getSpecifiedEmployee(int employeeId) {
        return employeeDaoImpl.getSpecifiedEmployee(employeeId);
    }
	
    /**
     * {@inheritDoc}
     */
/*    @Override
    public Map<Integer, String> getAddresses(int employeeId) {
	Employee employee = employeeDaoImpl.getEmployee(employeeId);
		
	Map<Integer, Address> employeeAddresses = new LinkedHashMap<Integer, Address>();

        employee.getAddresses().forEach((address) -> {
	    if (!address.getIsDeleted()) {
	        employeeAddresses.put(address.getAddressId(), address);
	    }
	});

        Map<Integer, String> addresses = new LinkedHashMap<Integer, String>();

        int serialNumber = 1;

        for (Integer addressId : employeeAddresses.keySet()) {
	    if (!employeeAddresses.get(addressId).getIsDeleted()) {
                String address = employeeAddresses.get(addressId).toString();
                String addressDetails = "Serial Number : " + serialNumber + address;
                addresses.put(addressId, addressDetails);
                serialNumber++;
            }
        }

        return addresses;
    }*/

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(int employeeId, String name, Date dob
            , float salary, String mobileNumber, List<List<String>> addresses) {
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
	} else if(1 < addresses.size()) {
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
		
        return employeeDaoImpl.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
/*    @Override
    public boolean addAddress(int employeeId, List<String> address) {
        Employee employee = employeeDaoImpl.getEmployee(employeeId);
        List<Address> addresses = employee.getAddresses();
        addresses.add(new Address(address.get(0), address.get(1), address.get(2)
                , address.get(3), address.get(4), address.get(5), address.get(6), false));
        employee.setAddresses(addresses);
        return employeeDaoImpl.updateEmployee(employee);
    }*/

    /**
     * {@inheritDoc}
     */
/*    @Override
    public boolean updateAddressValues(int employeeId, int addressIndex, int addressId, List<String> address) {
        Employee employee = employeeDaoImpl.getEmployee(employeeId);
        List<Address> addresses = employee.getAddresses();

        Address employeeAddress = new Address(address.get(0), address.get(1), address.get(2), address.get(3)
                , address.get(4), address.get(5), address.get(6), false);
        employeeAddress.setEmployee(employee);		
        employeeAddress.setAddressId(addressId);
        addresses.set(addressIndex, employeeAddress);
        employee.setAddresses(addresses);
        return employeeDaoImpl.updateEmployee(employee);
    }*/

    /**
     * {@inheritDoc}
     */
/*    @Override
    public boolean deleteAddress(int employeeId, int addressIndex) {
		Employee employee = employeeDaoImpl.getEmployee(employeeId);
		List<Address> addresses = employee.getAddresses();
		addresses.get(addressIndex).setIsDeleted(true);
		employee.setAddresses(addresses);
        return employeeDaoImpl.updateEmployee(employee);
    }*/

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int employeeId) {
        return deleteOrRestoreEmployee(employeeId, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getDeletedEmployees() {
        List<String> deletedEmployees = new ArrayList<String>();

        employeeDaoImpl.getDeletedEmployees().forEach((employee) -> {
            deletedEmployees.add(employee.getId() + "," + employee.getName()
                    + "," + employee.getDateOfBirth() + "," + employee.getSalary()
        	    + "," + employee.getMobileNumber());
        });

        return deletedEmployees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreEmployee(int employeeId) {
        return deleteOrRestoreEmployee(employeeId, false);
    }

    /**
     * deletes or restores an employee details
     *
     * @param employeeId    which need to be extracted from database
     * @param status        determines whether to delete or restore
     *
     * @return    true if successfull else false
     */
    public boolean deleteOrRestoreEmployee(int employeeId, boolean status) {
        Employee employee = employeeDaoImpl.getEmployee(employeeId);
		
        employee.setIsDeleted(status);
		
        employee.getAddresses().forEach((address) -> {
            address.setIsDeleted(status);
        });

        if (status) {
            employee.setProjects(null);
        }
		
        return employeeDaoImpl.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) {
        return employeeDaoImpl.isEmployeePresent(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeeDeleted(int employeeId) {
        return employeeDaoImpl.isEmployeeDeleted(employeeId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) {
        ProjectService projectServiceImpl = new ProjectServiceImpl();
        return projectServiceImpl.isProjectPresent(projectId);
    }
}