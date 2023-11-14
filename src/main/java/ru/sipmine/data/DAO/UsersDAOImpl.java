package ru.sipmine.data.DAO;

import java.time.LocalDateTime;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.telegram.telegrambots.meta.api.objects.User;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
            user.setCreatedAt(LocalDateTime.now());
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
    public void deleteUser(int Id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(getUserById(Id));
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
            Users user = getUserById(0);
            user.setTelegramName(telegramName);
            session.merge(user);
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
    public Users getUserById(int Id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Users user = null;
        try {
            transaction = session.beginTransaction();
            user = (Users) session.get(Users.class, Id);
            System.out.println(user);
            transaction.commit();
        } catch (HibernateException e) {
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

    @Override
    public Boolean isCreatedUser(int Id) {
        Users users = getUserById(Id);

        return users != null;
    }


    @Override
    public List<Users> findByTelegramUserName(String username){
            Session session = sessionFactory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Users> cr = cb.createQuery(Users.class);
            Root<Users> root = cr.from(Users.class);
            cr.select(root);
            Query<Users> query = session.createQuery(cr);
            
            session.close();
            System.out.println(query.getResultList().size());
            return query.getResultList();

        }
}
