package somethingrandom.dataaccess.google.tasks;

import somethingrandom.entity.Item;
import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.usecase.DataAccessException;

/**
 * Implements an interface to store items in the Google Tasks API.
 *
 * The API is
 * <a href="https://developers.google.com/tasks/reference/rest">published online</a>.
 */
public class GoogleTasksDataAccessObject implements AddItemDataAccessInterface {
    @Override
    public void save(Item item) throws DataAccessException {
        throw new RuntimeException("not implemented");
    }
}
