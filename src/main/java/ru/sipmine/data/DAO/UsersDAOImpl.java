package ru.sipmine.data.DAO;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import ru.sipmine.data.tables.Users;


/**
 * Implementation of the UsersDAO interface that provides methods for adding, deleting, updating, and retrieving users from a database.
 */
public class UsersDAOImpl implements UsersDAO {
    private SessionFactory sessionFactory;

    public UsersDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Adds a new user to the database with the given telegram ID and name.
     * 
     * @param telegramId the telegram ID of the user to be added
     * @param telegramName the telegram name of the user to be added
     */
    @Override
    public void addUser(long telegramId, String telegramName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Users user = new Users();
            user.setTelegramId(telegramId);
            user.setTelegramName(telegramName);
            user.setCreatedAt(LocalDateTime.now());
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    /**
     * Deletes a user with the specified ID from the database.
     * 
     * @param Id the ID of the user to be deleted
     */
    @Override
    public void deleteUser(int Id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(getUserById(Id));
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    /**
     * Updates the telegram name of a user with the given telegram ID.
     * 
     * @param telegramId the telegram ID of the user to update
     * @param telegramName the new telegram name to set for the user
     */
    @Override
    public void updateUser(long telegramId, String telegramName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Users user = getUserById(0);
            user.setTelegramName(telegramName);
            session.merge(user);
            session.flush();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a user Table object with the specified ID.
     * @param Id the ID of the user to return  
     */
    @Override
    public Users getUserById(int Id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Users user = null;
        try {
            transaction = session.beginTransaction();
            user = (Users) session.get(Users.class, Id);
            System.out.println(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println(user);
        return user;
    }



    /**
     * Finds the ID of a user by their Telegram username.
     * @param username the Telegram username to search for
     * @return the ID of the user with the given Telegram username, or 0 if not found
     */
    @Override
    public int findIdByTelegramUserName(String username){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int id = 0;
        try {

            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Users> cr = cb.createQuery(Users.class);
            Root<Users> root = cr.from(Users.class);
            cr.select(root).where(cb.equal(root.get("telegramName"), username));
            Query<Users> query = session.createQuery(cr);
            List<Users> users = query.getResultList();
            if (users.size() > 0) {
                id = users.get(0).getId();
            }
            transaction.commit();

        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();

        }
        return id;
    }
}
