/**
 * The UserService class provides methods for managing users in the system.
 * It interacts with the UsersDAOImpl class to perform CRUD operations on the users.
 */
package ru.sipmine.data.services;


import java.util.Set;

import javax.annotation.Nonnull;

import org.hibernate.SessionFactory;

import ru.sipmine.data.dao.UsersDAOImpl;
import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.data.tables.UsersTable;


public class UserService {
    private UsersDAOImpl usersDAOImp;
    public UserService() {

    }

    /**
     * Constructs a new UserService object with the given SessionFactory.
     *
     * @param sessionFactory the SessionFactory to be used by the UserService
     */
    public UserService(SessionFactory sessionFactory){
        this.usersDAOImp = new UsersDAOImpl(sessionFactory);
    }  

    

    public UsersTable getUserById(int Id){
        return this.usersDAOImp.getUserById(Id);
    }
    
    public void createUser(@Nonnull Long long1,  String telegramName){
        this.usersDAOImp.addUser(long1, telegramName);
    }
    
    public void deleteUser(int Id) {
        this.usersDAOImp.deleteUser(Id);
    }

    public void updateUser(Long telegramId, String telegramName) {
        this.usersDAOImp.updateUser(telegramId, telegramName);
    }


    public int findIdByTelegramUserName(String telegramUserName) {
        return this.usersDAOImp.findIdByTelegramUserName(telegramUserName);
    }

    public Set<ApiIngegratioTable> getAllApiIngegratioTables(int id){
        return this.usersDAOImp.getAllApiIngegratioTables(id);
    }

}
