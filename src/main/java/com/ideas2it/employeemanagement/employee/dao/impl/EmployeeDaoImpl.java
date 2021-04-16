package com.ideas2it.employeemanagement.employee.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.sessionfactory.HibernateUtility;


/**
 * this class implements
 * dao interface class to perform
 * operations on employee details
 * 
 * @author  sibi
 * @created 2021-03-08
 */
public class EmployeeDaoImpl implements EmployeeDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(Employee employee) {
        boolean isInserted = false;
        Session session = null;
		
        try { 
            session = HibernateUtility.getSessionFactory().openSession(); 
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
            isInserted = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }

        return isInserted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() { 
        List<Employee> employees = null;
        Session session = null;
		
        try { 
            session = HibernateUtility.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted", false));
            employees = criteria.list();
			
            employees.forEach((employee) -> {
                employee.getAddresses().forEach((address) -> {
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) {
        Employee employee = null;
        Session session = null;
		
        try {
            session = HibernateUtility.getSessionFactory().openSession();  
            employee = (Employee) session.get(Employee.class, employeeId);
			
            employee.getAddresses().forEach((address) -> {
            });
			
            employee.getProjects().forEach((project) -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }
		
        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployeeAndAddresses(int employeeId) {  
        Employee employee = null;
        Session session = null;
		
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
			
            employee.getAddresses().forEach((address) -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }

        return employee;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployeeAndProjects(int employeeId) {  
        Employee employee = null;
        Session session = null;
		
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
			
            employee.getProjects().forEach((project) -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }

        return employee;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getSpecifiedEmployee(int employeeId) {
        Employee employee = null;
        Session session = null;
		
        try {
            session = HibernateUtility.getSessionFactory().openSession();  
            employee = (Employee) session.get(Employee.class, employeeId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }

        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        boolean isUpdated = false;  
        Session session = null;

        try {
            session = HibernateUtility.getSessionFactory().openSession();			
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
            isUpdated = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }

        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getDeletedEmployees() { 
        List<Employee> deletedEmployees = null;
        Session session = null;
		
        try { 
            session = HibernateUtility.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted", true));
            deletedEmployees = criteria.list();
        } catch (Exception e) {
            e.printStackTrace();	
        } finally {
            if (null != session) {
                session.close();
            }
        }
		
        return deletedEmployees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) {  	
        Employee employee = null;
        Session session = null;
		
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
        } catch (Exception e) {
            e.printStackTrace();	
        } finally {
            if (null != session) {
                session.close();
            }
        }
		
        if (null == employee) {
            return false;
        } else if (employee.getIsDeleted()) {
            return false;	
        } else {
            return true;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeeDeleted(int employeeId) {  	
        Employee employee = null;
        Session session = null;
		
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
        } catch (Exception e) {
            e.printStackTrace();	
        } finally {
            if (null != session) {
                session.close();
            }
        }

        if (null == employee) {
            return false;
        } else if (employee.getIsDeleted()) {
            return true;	
        } else {
            return false;
        }
    }
}