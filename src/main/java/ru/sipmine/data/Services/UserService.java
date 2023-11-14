package ru.sipmine.data.Services;

import org.hibernate.SessionFactory;
import ru.sipmine.data.DAO.UsersDAOImpl;
import ru.sipmine.data.tables.Users;

public class UserService {
    private UsersDAOImpl usersDAOImp;
    public UserService() {

    }
    public UserService(SessionFactory sessionFactory){
        this.usersDAOImp = new UsersDAOImpl(sessionFactory);
    }  

    public Users getUserById(int Id){
        return this.usersDAOImp.getUserById(Id);
    }

    public void creaateUser(Long telegramId,  String telegramName){
        this.usersDAOImp.addUser(telegramId, telegramName);
    }
    
    public void deleteUser(int Id) {
        this.usersDAOImp.deleteUser(Id);
    }

    public void updateUser(Long telegramId, String telegramName) {
        this.usersDAOImp.updateUser(telegramId, telegramName);
    }

    public boolean checkUser(int telegramId) {
        return this.usersDAOImp.isCreatedUser(telegramId);
    }
}
