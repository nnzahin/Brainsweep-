package somethingrandom.usecase.add;

import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

public interface AddItemDataAccessInterface {

    /**
     * Saves the item to the data store.
     *
     * @param item                  The item to save.
     * @throws DataAccessException  If the saving failed due to an IO error.
     */
    void save(Item item) throws DataAccessException;
}
