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
    Boolean delete(UUID id) throws DataAccessException;

}
