package somethingrandom.usecase.delete;

import somethingrandom.usecase.DataAccessException;

import java.util.UUID;

public interface DeleteItemDataAccessInterface {
    /**
     * Delete an item from the database.
     *
     * @param id The ID of the item to delete.
     * @throws DataAccessException if an error occurs
     */
    String delete(UUID id) throws DataAccessException;

    /**
     * Delete an item from the database. If there are multiple items
     * with the same name, delete the first occurrence in the database.
     *
     * @param name The name of the item to delete.
     * @throws DataAccessException if an error occurs
     */
    String delete(String name) throws DataAccessException;
}
