package ru.sipmine.data.DAO;

import ru.sipmine.data.tables.Users;

public interface UsersDAO {
    public void addUser(long telegramId, String telegramName);

    public void deleteUser(long telegramId);

    public void updateUser(long telegramId, String telegramName);

    public Users getUserById(long telegramId);

}
