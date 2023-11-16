/**
 * This interface represents the Data Access Object (DAO) for the Users table.
 * It provides CRUD (Create, Read, Update, Delete) operations for the Users table.
 */
package ru.sipmine.data.DAO;


import java.util.Set;

import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.data.tables.UsersTable;

public interface UsersDAO {
    // CRUD operations
    public void addUser(long telegramId, String telegramName);

    public void deleteUser(int Id);

    public void updateUser(long telegramId, String telegramName);

    public UsersTable getUserById(int Id);

    public Set<ApiIngegratioTable> getAllApiIngegratioTables(int id);

    public int findIdByTelegramUserName(String username);
}
