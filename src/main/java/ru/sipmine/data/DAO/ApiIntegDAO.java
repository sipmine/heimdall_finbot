package ru.sipmine.data.DAO;

public interface ApiIntegDAO {

    public void addApiInteg(String tokenApi);

    public void deleteApiInteg(int Id);

    public void updateApiInteg(int Id, String tokenApi);

    public int findIdByTokenApi(String tokenApi);

    public String getTokenApiById(int Id);

    
} 