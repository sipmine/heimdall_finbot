package ru.sipmine.data;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.sipmine.data.tables.Users;




public class PsqlConnector {
    private static SessionFactory sessionFactory;





    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                
                configuration.addAnnotatedClass(Users.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
