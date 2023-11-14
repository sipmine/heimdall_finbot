package ru.sipmine.data.DAO;

import java.util.List;

import ru.sipmine.data.tables.Users;

public interface UsersDAO {
    public void addUser(long telegramId, String telegramName);

    public void deleteUser(int Id);

    public void updateUser(long telegramId, String telegramName);

    public Users getUserById(int Id);

    public Boolean isCreatedUser(int Id);

    public List<Users> findByTelegramUserName(String username);
}
