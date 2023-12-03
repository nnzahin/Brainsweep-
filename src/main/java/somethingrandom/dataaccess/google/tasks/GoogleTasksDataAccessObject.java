package somethingrandom.dataaccess.google.tasks;

import somethingrandom.dataaccess.google.auth.AuthenticationException;
import somethingrandom.entity.Item;
import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.details.ItemDetailsDataAccessInterface;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


/**
 * Implements an interface to store items in the Google Tasks API.
 * <p>
 * The API is
 * <a href="https://developers.google.com/tasks/reference/rest">published online</a>.
 */
public class GoogleTasksDataAccessObject implements AddItemDataAccessInterface, SearchItemsDataAccessInterface, ItemDetailsDataAccessInterface {
    private final TaskList taskList;

    public GoogleTasksDataAccessObject(TaskList list) {
        this.taskList = list;
    }

    public static String getScope() {
        return "https://www.googleapis.com/auth/tasks";
    }

    @Override
    public void save(Item item) throws DataAccessException {
        try {
            this.taskList.add(item);
        } catch (IOException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Collection<Item> getAllItems() throws DataAccessException {
        try {
            return this.taskList.getAll();
        } catch (IOException | AuthenticationException e) {
            throw new DataAccessException(e);
        }
    }

    public Optional<Item> getItemById(UUID uuid) throws DataAccessException {
        try {
            return this.taskList.getItem(uuid);
        } catch (IOException e) {
            throw new DataAccessException(e);
        }
    }
}
