package ru.sipmine.data.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sipmine.data.tables.Users;

public class UsersDAOImpl implements UsersDAO {
    private SessionFactory sessionFactory;

    public UsersDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void addUser(long telegramId, String telegramName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Users user = new Users();
            user.setTelegramId(telegramId);
            user.setTelegramName(telegramName);
            session.persist(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteUser(long telegramId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(getUserById(telegramId));
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateUser(long telegramId, String telegramName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Users user = getUserById(telegramId);
            user.setTelegramName(telegramName);
            session.merge(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Users getUserById(long telegramId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Users user = null;
        try {
            transaction = session.beginTransaction();
            user = session.get(Users.class, telegramId);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

}
