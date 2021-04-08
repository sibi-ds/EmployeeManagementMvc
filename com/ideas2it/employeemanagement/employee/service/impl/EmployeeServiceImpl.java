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
        Project project = projectServiceImpl.getSpecifiedProject(projectId);
		Employee employee = employeeDaoImpl.getEmployeeAndProjects(employeeId);
		List<Project> projects = employee.getProjects();
		projects.add(project);
		employee.setProjects(projects);
        return employeeDaoImpl.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unassignProject(int employeeId, int projectId) {
		Employee employee = employeeDaoImpl.getEmployeeAndProjects(employeeId);
		List<Project> projects = employee.getProjects();

		for(Project project : projects) {
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
            String employeeDetails = employee.toString();

            for (Address address : employee.getAddresses()) {
                employeeDetails = employeeDetails + address.toString();
            }

            employeeDetails = employeeDetails + "\n--------------------";

            employees.add(employeeDetails);
        });

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmployeeAndAddresses(int employeeId) {
        Employee employee = employeeDaoImpl.getEmployee(employeeId);
        String employeeDetails = employee.toString();
        List<Address> addresses = employee.getAddresses();

        if (addresses.isEmpty()) {
			employeeDetails = employeeDetails + "\nNo addresses found\n";
		} else {
            int serialNumber =1;
            employeeDetails = employeeDetails + "\n-----Addresses of employee-----\n";
			
            for (Address address : addresses) {
				if (!address.getIsDeleted()) {
                    employeeDetails = employeeDetails + "\nSerial Number : "
				            + serialNumber + address.toString();
			        serialNumber++;
				}
            }

            employeeDetails = employeeDetails + "--------------------\n";
        }
		
        return employeeDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmployeeAndProjects(int employeeId) {
        Employee employee = employeeDaoImpl.getEmployeeAndProjects(employeeId);
        String employeeDetails = employee.toString();
        List<Project> projects = employee.getProjects();

        if (projects.isEmpty()) {
            employeeDetails = employeeDetails + "\n-----No Project Assigned-----\n";
        } else {
			employeeDetails = employeeDetails + "\n-----Projects Assigned-----\n";

			for (Project project : projects) {
                employeeDetails = employeeDetails + project.toString();
            }
		}

        employeeDetails = employeeDetails + "--------------------\n";

        return employeeDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds) {
        List<Employee> employees = new ArrayList<Employee>();

        employeeIds.forEach((employeeId) -> {
            employees.add(employeeDaoImpl.getSpecifiedEmployee(employeeId));
        });

        return employees;
    }

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
    @Override
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(int employeeId, String name, Date dob
            , float salary, String mobileNumber) {
		Employee employee = employeeDaoImpl.getSpecifiedEmployee(employeeId);
		
		if (null != name) {
		    employee.setName(name);	
		}
		
		if (null != dob) {
		    employee.setDateOfBirth(dob);	
		}
		
		if (0.0f != salary) {
		    employee.setSalary(salary);	
		}
		
		if (null != mobileNumber) {
		    employee.setMobileNumber(mobileNumber);	
		}
		
        return employeeDaoImpl.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAddress(int employeeId, List<String> address) {
		Employee employee = employeeDaoImpl.getEmployee(employeeId);
		List<Address> addresses = employee.getAddresses();
        addresses.add(new Address(address.get(0), address.get(1), address.get(2)
                , address.get(3), address.get(4), address.get(5), address.get(6), false));
		employee.setAddresses(addresses);
		return employeeDaoImpl.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAddress(int employeeId, int addressIndex) {
		Employee employee = employeeDaoImpl.getEmployee(employeeId);
		List<Address> addresses = employee.getAddresses();
		addresses.get(addressIndex).setIsDeleted(true);
		employee.setAddresses(addresses);
        return employeeDaoImpl.updateEmployee(employee);
    }

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
            deletedEmployees.add(employee.toString());
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
    public boolean isProjectPresent(int projectId) {
        ProjectService projectServiceImpl = new ProjectServiceImpl();
        return projectServiceImpl.isProjectPresent(projectId);
    }
}