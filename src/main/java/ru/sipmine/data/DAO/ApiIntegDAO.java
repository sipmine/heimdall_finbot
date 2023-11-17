package ru.sipmine.data.DAO;

import ru.sipmine.data.tables.UsersTable;

public interface ApiIntegDAO {

    public void addApiInteg(UsersTable usersTable,String tokenApi);

    public void deleteApiInteg(int Id);

    public void updateApiInteg(int Id, String tokenApi);

    public int findIdByTokenApi(String tokenApi);

    public String getTokenApiById(int Id);

    
} 