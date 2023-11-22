package dataaccess.google;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import dataaccess.google.auth.Token;
import dataaccess.google.tasks.GoogleTasksDataAccessObject;
import dataaccess.google.tasks.TaskList;
import dataaccess.google.tasks.TaskAccount;
import entity.Item;
import usecase.additem.AddItemDataAccessInterface;
import usecase.DataAccessException;

import java.util.Optional;

/**
 * GoogleDataAccessObject is a generic data access object to interact
 * with either Google Keep or Google Tasks.
 * <p>
 * It automatically selects the backend corresponding: Keep for reference
 * items, and Tasks for others.
 */
public class GoogleDataAccessObject implements AddItemDataAccessInterface {
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
}
