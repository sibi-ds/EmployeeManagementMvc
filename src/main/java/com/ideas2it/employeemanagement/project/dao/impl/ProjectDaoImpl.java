package com.ideas2it.employeemanagement.project.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.sessionfactory.HibernateUtility;
import com.ideas2it.exceptions.EmployeeManagementException;
import com.ideas2it.loggers.EmployeeManagementLogger;

/**
 * this class implements dao interface class to
 * perform operations on project details
 * 
 * @author sibi
 * @created 2021-03-24
 */
public class ProjectDaoImpl implements ProjectDao {
    
    private EmployeeManagementLogger logger = new EmployeeManagementLogger(ProjectDaoImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertProject(Project project) throws EmployeeManagementException {
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(project);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Project Creation Failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getProjects() throws EmployeeManagementException {
        List<Project> projects = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.eq("isDeleted", false));
            projects = criteria.list();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Projects Fetch failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return projects;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Project getProject(int projectId) throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            project = (Project) session.get(Project.class, projectId);
            
            project.getEmployees().forEach((employee) -> {
            });
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Project Fetch failure");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return project;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Project getSpecifiedProject(int projectId) throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            project = (Project) session.get(Project.class, projectId);
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Failed To Fetch Specified Project");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return project;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProject(Project project) {
        //boolean isUpdated = false;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(project);
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
    public List<Project> getDeletedProjects() throws EmployeeManagementException {
        List<Project> deletedProjects = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.eq("isDeleted", true));
            deletedProjects = criteria.list();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Failed To Fetch Deleted Projects");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        return deletedProjects;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            project = (Project) session.get(Project.class, projectId);
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Failed To Check Project Active Status");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        //return ((null == project) || project.getIsDeleted()) ? false : true;
        
        if (null == project) {
            return false;
        } else if (project.getIsDeleted()) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectDeleted(int projectId) throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        
        try {
            session = HibernateUtility.getSessionFactory().openSession();
            project = (Project) session.get(Project.class, projectId);
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Failed To Check Project Deleted Status");
        } finally {
            HibernateUtility.closeSession(session);
        }
        
        if (null == project) {
            return false;
        } else if (project.getIsDeleted()) {
            return true;
        } else {
            return false;
        }
    }
}