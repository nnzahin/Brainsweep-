package somethingrandom.usecase.details;

import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.util.Optional;
import java.util.UUID;

/**
 * An interface for databases that allow item details to be queried.
 */
public interface ItemDetailsDataAccessInterface {
    /**
     * Queries for the provided UUID from the database.
     *
     * @param uuid The UUID to search for.
     * @return an empty Optional if the item was not found; otherwise, the item.
     * @throws DataAccessException upon data access failure
     */
    Optional<Item> getItemById(UUID uuid) throws DataAccessException;
}
