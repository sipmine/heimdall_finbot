package ru.sipmine.data.services;

import org.hibernate.SessionFactory;

import ru.sipmine.data.dao.ApiIntegDAOImpl;
import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.data.tables.UsersTable;

public class ApiIntegService {
    private ApiIntegDAOImpl apiIntegDAOImpl;


    public ApiIntegService() {
    }

    public ApiIntegService(SessionFactory sessionFactory) {
        this.apiIntegDAOImpl = new ApiIntegDAOImpl(sessionFactory);
    }


    public void addApiInteg(UsersTable user_id,String name_service,String tokenApi, String secretToken) {
        this.apiIntegDAOImpl.addApiInteg(user_id,name_service,tokenApi, secretToken);
        
    }

    public void deleteApiInteg(int Id) {
        this.apiIntegDAOImpl.deleteApiInteg(Id);
    }

    public void updateApiInteg(int Id, String tokenApi) {
        this.apiIntegDAOImpl.updateApiInteg(Id, tokenApi);
    }

    public int findIdByTokenApi(String tokenApi) {
        return this.apiIntegDAOImpl.findIdByTokenApi(tokenApi);
    }

    public String getTokenApiById(int Id) {
        return this.apiIntegDAOImpl.getTokenApiById(Id);
    }

    public ApiIngegratioTable gIngegratioTablebyId(int id ) {
        return this.apiIntegDAOImpl.gIngegratioTablebyId(id);
    }

    public int findIdByName(String name) {
        return this.apiIntegDAOImpl.findIdByName(name);
    }



}
