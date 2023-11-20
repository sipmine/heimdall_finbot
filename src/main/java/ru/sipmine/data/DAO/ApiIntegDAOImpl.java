package ru.sipmine.data.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.data.tables.UsersTable;

public class ApiIntegDAOImpl implements ApiIntegDAO {
    private SessionFactory sessionFactory;
    public ApiIntegDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public  void addApiInteg(UsersTable usersTable, String name_service , String tokenApi) {
        Session session =  sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            ApiIngegratioTable apiIngegratioTable = new ApiIngegratioTable();
            apiIngegratioTable.setUserId(usersTable);
            apiIngegratioTable.setNameApi(name_service);
            apiIngegratioTable.setTokenApi(tokenApi);
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

}
