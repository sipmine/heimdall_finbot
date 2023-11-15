/**
 * This interface represents the Data Access Object (DAO) for the Users table.
 * It provides CRUD (Create, Read, Update, Delete) operations for the Users table.
 */
package ru.sipmine.data.DAO;


import ru.sipmine.data.tables.Users;

public interface UsersDAO {
    // CRUD operations
    public void addUser(long telegramId, String telegramName);

    public void deleteUser(int Id);

    public void updateUser(long telegramId, String telegramName);

    public Users getUserById(int Id);

    public int findIdByTelegramUserName(String username);
}
