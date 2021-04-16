package com.ideas2it.employeemanagement.sessionfactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

/**
 * class provide database connectivity
 * 
 * @author sibi
 * @version 1.0 14-03-2021
 */
public class HibernateUtility {
      private static SessionFactory sessionFactory = null;

      private HibernateUtility() {
      }

      /**
       * It is used to  establish connection to SQL database
       */
      public static SessionFactory getSessionFactory() {
         if (null == sessionFactory) {
             Configuration configuration = new Configuration();
             configuration.configure("resources/hibernate/properties/hibernate.cfg.xml");
             sessionFactory = configuration.buildSessionFactory();
          }
          return sessionFactory;
      }
}