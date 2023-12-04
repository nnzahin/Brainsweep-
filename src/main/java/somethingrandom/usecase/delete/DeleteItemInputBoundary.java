package somethingrandom.usecase.delete;

import somethingrandom.usecase.DataAccessException;

import java.util.UUID;

public interface DeleteItemInputBoundary {

    /**
     * Delete an item from the database.
     *
     * @param id The ID of the item to delete.
     */
    void execute(UUID id) throws DataAccessException;
}
