package com.ideas2it.employeemanagement.sessionfactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ideas2it.loggers.EmployeeManagementLogger;

/**
 * class provide database connectivity
 * 
 * @author sibi
 * @created 14-03-2021
 */
public class HibernateUtility {
    private static EmployeeManagementLogger logger = new EmployeeManagementLogger(HibernateUtility.class);
    private static SessionFactory sessionFactory = null;
    
    private HibernateUtility() {
    }
    
    /**
     * It is used to establish connection to SQL
     * database
     */
    public static SessionFactory getSessionFactory() {
        if (null == sessionFactory) {
            Configuration configuration = new Configuration();
            configuration.configure("resources/hibernate/properties/hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        }
        
        return sessionFactory;
    }
    
    /**
     * closes the session
     * 
     * @param session
     */
    public static void closeSession(Session session) {
        try {
            if (null != session) {
                session.close();
            }
        } catch (HibernateException e) {
            logger.logError(e);
        }
    }
}