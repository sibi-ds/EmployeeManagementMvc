package com.ideas2it.employeemanagement.employee.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.sessionfactory.HibernateUtility;
import com.ideas2it.exceptions.EmployeeManagementException;
import com.ideas2it.loggers.EmployeeManagementLogger;

/**
 * this class implements dao interface class to
 * perform operations on employee details
 * 
 * @author sibi
 * @created 2021-03-08
 */
public class EmployeeDaoImpl implements EmployeeDao {
    
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeDaoImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEmployee(Employee employee) throws EmployeeManagementException {
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee Insertion Failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getEmployees() throws EmployeeManagementException {
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
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employees Fetch Failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return employees;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
            
            employee.getAddresses().forEach((address) -> {
            });
            
            employee.getProjects().forEach((project) -> {
            });
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee Fetch Failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return employee;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployeeAndAddresses(int employeeId) throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
            
            employee.getAddresses().forEach((address) -> {
            });
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee And Addresses Fetch Failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return employee;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployeeAndProjects(int employeeId) throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
            
            employee.getProjects().forEach((project) -> {
            });
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee And Projects Fetch Failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return employee;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getSpecifiedEmployees(List<Integer> employeeIds) throws EmployeeManagementException {
        List<Employee> employees = new ArrayList<Employee>();
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            for (Integer employeeId : employeeIds) {
                employees.add((Employee) session.get(Employee.class, employeeId));
            }
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Specified Employees Fetch Failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return employees;
    }
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public Employee getSpecifiedEmployee(int employeeId) throws EmployeeManagementException {
//        Employee employee = null;
//        Session session = null;
//        
//        try {
//            session = HibernateUtility.getSessionFactory().openSession();
//            employee = (Employee) session.get(Employee.class, employeeId);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != session) {
//                session.close();
//            }
//        }
//        
//        return employee;
//    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEmployee(Employee employee) {
        //boolean isUpdated = false;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
            //isUpdated = true;
        } catch (HibernateException e) {
            logger.logError(e);
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        //return isUpdated;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getDeletedEmployees() throws EmployeeManagementException {
        List<Employee> deletedEmployees = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted", true));
            deletedEmployees = criteria.list();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Failed To Fetch Deleted Employees");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return deletedEmployees;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmployeePresent(int employeeId) throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Failed To Check Employee Active Status");
        } finally {
            HibernateUtility.closeSession(session);
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
    public boolean isEmployeeDeleted(int employeeId) throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Failed To Check Employee Deleted Status");
        } finally {
            HibernateUtility.closeSession(session);
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