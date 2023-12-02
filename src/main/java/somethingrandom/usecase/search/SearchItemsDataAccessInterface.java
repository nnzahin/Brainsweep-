package somethingrandom.usecase.search;

import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.util.Collection;

/**
 * The interface for databases that can be searched.
 */
public interface SearchItemsDataAccessInterface {
    /**
     * Gets all of the items in the database.
     * <p>
     * This may need to be changed in future, but for now this ensures
     * maximum flexibility on our side.
     *
     * @return A collection of all items.
     * @throws DataAccessException if an error occurs
     */
    Collection<Item> getAllItems() throws DataAccessException;
}
