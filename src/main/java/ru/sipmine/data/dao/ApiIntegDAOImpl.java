package ru.sipmine.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.data.tables.UsersTable;

public class ApiIntegDAOImpl implements ApiIntegDAO {
    private SessionFactory sessionFactory;
    public ApiIntegDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public  void addApiInteg(UsersTable usersTable, String name_service , String tokenApi, String secretToken) {
        Session session =  sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            ApiIngegratioTable apiIngegratioTable = new ApiIngegratioTable();
            apiIngegratioTable.setUserId(usersTable);
            apiIngegratioTable.setNameApi(name_service);
            apiIngegratioTable.setTokenApi(tokenApi);
            apiIngegratioTable.setSecret(secretToken);
            session.persist(apiIngegratioTable);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally{
            session.close();
        }
    }

    @Override
    public void deleteApiInteg(int Id) {
        
    }

    @Override
    public void updateApiInteg(int Id, String tokenApi) {
        
    }

    @Override
    public int findIdByTokenApi(String tokenApi) {
        return 0;
    }

    @Override
    public String getTokenApiById(int Id) {
        return null;
    }

    @Override
    public int findIdByName(String name) {
    Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int id = 0;
        try {

            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ApiIngegratioTable> cr = cb.createQuery(ApiIngegratioTable.class);
            Root<ApiIngegratioTable> root = cr.from(ApiIngegratioTable.class);
            cr.select(root).where(cb.equal(root.get("nameApi"), name));
            Query<ApiIngegratioTable> query = session.createQuery(cr);
            List<ApiIngegratioTable> users = query.getResultList();
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

    @Override
    public ApiIngegratioTable gIngegratioTablebyId(int id) {
        Session session =  sessionFactory.openSession();
        Transaction transaction = null;
        ApiIngegratioTable apiIngegratioTable = null;

        try {
            transaction = session.beginTransaction();
            apiIngegratioTable = session.get(ApiIngegratioTable.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();

        }
        return apiIngegratioTable;
    }

}
