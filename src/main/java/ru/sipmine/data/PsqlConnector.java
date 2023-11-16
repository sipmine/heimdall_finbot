/**
 * This class represents a PostgreSQL connector that provides a session factory for Hibernate.
 */
package ru.sipmine.data;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.sipmine.data.tables.UsersTable;





public class PsqlConnector {
    private static SessionFactory sessionFactory;

    public PsqlConnector() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                
                configuration.addAnnotatedClass(UsersTable.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the session factory for Hibernate.
     * @return the session factory for Hibernate.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
