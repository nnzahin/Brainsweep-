package somethingrandom.dataaccess.google;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import somethingrandom.dataaccess.google.auth.Token;
import somethingrandom.dataaccess.google.tasks.GoogleTasksDataAccessObject;
import somethingrandom.dataaccess.google.tasks.TaskList;
import somethingrandom.dataaccess.google.tasks.TaskAccount;
import somethingrandom.entity.Item;
import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.delete.DeleteItemDataAccessInterface;
import somethingrandom.usecase.search.SearchItemsDataAccessInterface;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * GoogleDataAccessObject is a generic data access object to interact
 * with Google Tasks, or any other Google services that may be added.
 * <p>
 * Currently, Google Tasks is always used; adding Google Keep support,
 * for example, may be possible in an enterprise scenario.
 */
public class GoogleDataAccessObject implements AddItemDataAccessInterface, SearchItemsDataAccessInterface, DeleteItemDataAccessInterface {
    private final GoogleTasksDataAccessObject tasksDAO;

    /**
     * Creates a new data access object that connects to Google APIs.
     *
     *
     * @param client The HTTP client to use to connect.
     * @param token The token to authenticate with.
     * @param taskListName The identifier of the task list to use.
     * @throws DataAccessException if the task list does not exist.
     */
    public GoogleDataAccessObject(OkHttpClient client, Token token, String taskListName) throws DataAccessException {
        OkHttpAPIProvider provider = new OkHttpAPIProvider(client, token);
        this.tasksDAO = createTasksDAO(provider, taskListName);
    }

    public static String[] getScopes() {
        return new String[] {
            GoogleTasksDataAccessObject.getScope(),
        };
    }

    @NotNull
    private static GoogleTasksDataAccessObject createTasksDAO(APIProvider provider, String taskListName) throws DataAccessException {
        TaskAccount account = new TaskAccount(provider);

        Optional<TaskList> tl = account.findTaskListByIdentifier(taskListName);
        if (tl.isEmpty()) {
            throw new DataAccessException("Specified task list does not exist");
        }

        return new GoogleTasksDataAccessObject(tl.get());
    }

    @Override
    public void save(Item item) throws DataAccessException {
        tasksDAO.save(item);
    }

    @Override
    public Boolean delete(UUID id) throws DataAccessException {
        return tasksDAO.delete(id);
    }

    @Override
    public Boolean delete(String name) throws DataAccessException {
        return tasksDAO.delete(name);
    }

    @Override
    public Collection<Item> getAllItems() throws DataAccessException {
        return tasksDAO.getAllItems();
    }
}
