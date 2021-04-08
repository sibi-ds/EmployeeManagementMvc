package com.ideas2it.employeemanagement.project.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.sessionfactory.HibernateUtility;

/**
 * this class implements
 * dao interface class to perform
 * operations on project details
 * 
 * @author  sibi
 * @created 2021-03-24
 */
public class ProjectDaoImpl implements ProjectDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertProject(Project project) {
		boolean isInserted = false;
		Session session = null;  
			
		try {
            session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(project);
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
    public List<Project> getProjects() {
		List<Project> projects = null;
		Session session = null; 
		
		try { 
		    session = HibernateUtility.getSessionFactory().openSession(); 
		    Criteria criteria = session.createCriteria(Project.class);
		    criteria.add(Restrictions.eq("isDeleted", false));
            projects = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != session) {
			    session.close();
			}
		}

        return projects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project getProject(int projectId) {
		Project project = null;
		Session session = null; 
		
		try {
		    session = HibernateUtility.getSessionFactory().openSession();  
            project = (Project) session.get(Project.class, projectId);
			
			project.getEmployees().forEach((employee) -> {
			});
		} catch (Exception e) {
		    e.printStackTrace();	
		} finally {
			if (null != session) {
			    session.close();
			}
		}
		
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project getSpecifiedProject(int projectId) {
		Project project = null;
		Session session = null; 
		
		try {
		    session = HibernateUtility.getSessionFactory().openSession();  
            project = (Project) session.get(Project.class, projectId);
		} catch (Exception e) {
		    e.printStackTrace();	
		} finally {
			if (null != session) {
			    session.close();
			}
		}
		
        return project;
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateProject(Project project) {
		boolean isUpdated = false;
		Session session = null; 
		
		try { 
		    session = HibernateUtility.getSessionFactory().openSession();  
            session.beginTransaction();
			session.update(project);
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
    public List<Project> getDeletedProjects() {
		List<Project> deletedProjects = null;
		Session session = null; 
		
		try { 
		    session = HibernateUtility.getSessionFactory().openSession(); 
	    	Criteria criteria = session.createCriteria(Project.class);
		    criteria.add(Restrictions.eq("isDeleted", true));
            deletedProjects = criteria.list();
		} catch (Exception e) {
		    e.printStackTrace();	
		} finally {
			if (null != session) {
			    session.close();
			}
		}
		
        return deletedProjects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectPresent(int projectId) {  	
		Project project = null;
		Session session = null; 
		
		try {
			session = HibernateUtility.getSessionFactory().openSession();
            project = (Project) session.get(Project.class, projectId);
		} catch (Exception e) {
		    e.printStackTrace();	
		} finally {
			if (null != session) {
			    session.close();
			}
		}
		
		if (null == project) {
			return false;
	    } else if (project.getIsDeleted()) {
		    return false;	
		} else {
			return true;
		}
	}
}