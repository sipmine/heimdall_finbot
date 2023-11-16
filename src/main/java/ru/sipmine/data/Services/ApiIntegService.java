package ru.sipmine.data.Services;

import org.glassfish.grizzly.http.server.Session;
import org.hibernate.SessionFactory;

import ru.sipmine.data.DAO.ApiIntegDAOImpl;

public class ApiIntegService {
    private ApiIntegDAOImpl apiIntegDAOImpl;


    public ApiIntegService() {
    }

    public ApiIntegService(SessionFactory sessionFactory) {
        this.apiIntegDAOImpl = new ApiIntegDAOImpl(sessionFactory);
    }


    public void addApiInteg(String tokenApi) {
        this.apiIntegDAOImpl.addApiInteg(tokenApi);
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





}
