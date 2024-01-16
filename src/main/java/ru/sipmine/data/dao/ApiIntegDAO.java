package ru.sipmine.data.dao;

import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.data.tables.UsersTable;

public interface ApiIntegDAO {

    public void addApiInteg(UsersTable usersTable,String name_service,String tokenApi, String secretToken);

    public void deleteApiInteg(int Id);

    public void updateApiInteg(int Id, String tokenApi);

    public int findIdByTokenApi(String tokenApi);

    public int findIdByName(String name);

    public String getTokenApiById(int Id);

    public ApiIngegratioTable gIngegratioTablebyId(int id);

    
} 