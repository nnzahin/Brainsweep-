package dataaccess.google.tasks;

import dataaccess.google.APIProvider;
import dataaccess.google.APIRequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import usecase.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * A TaskAccount represents a Google account with contained Google Tasks lists.
 * <p>
 * Google Tasks stores the tasks a user has in a series of lists. In order to
 * insert a task, we need to know which task to put it in.
 * <p>
 * The main/only reason to use this is to iterate the lists that the account
 * contains; it doesn't have anything else about the account.
 */
public class TaskAccount {
    private final APIProvider provider;

    /**
     * Creates a new TaskAccount for the API provider.
     * <p>
     * This will access the account of the user's token.
     *
     * @param provider The provider to connect with.
     */
    public TaskAccount(APIProvider provider) {
        this.provider = provider;
    }

    /**
     * Creates an iterator through the user's list of tasks.
     *
     * @return An iterator over the account's list of tasks.
     * @throws DataAccessException if the API call fails
     */
    public Iterator<TaskList> iterateLists() throws DataAccessException {
        JSONObject result;
        try {
            result = provider.request(new APIRequestBody.GetBody(), "https://tasks.googleapis.com/tasks/v1/users/@me/lists");
        } catch (IOException e) {
            throw new DataAccessException(e);
        }

        if (!result.getString("kind").equals("tasks#taskLists")) {
            throw new DataAccessException("Task list has invalid format");
        }

        JSONArray items = result.getJSONArray("items");
        List<TaskList> found = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject taskList = items.getJSONObject(i);
            if (!taskList.optString("kind", "").equals("tasks#taskList")) {
                throw new DataAccessException("Task list has invalid type");
            }

            Object id = taskList.opt("id");
            if (!(id instanceof String)) {
                throw new DataAccessException("Task list is missing ID");
            }

            found.add(new TaskList(provider, (String) id));
        }

        return found.iterator();
    }

    /**
     * Finds the task list in this account with the provided identifier.
     *
     * @param name The name to assign.
     * @return the task list, or an empty Optional if it was not found.
     * @throws DataAccessException if the API call fails
     */
    public Optional<TaskList> findTaskListByIdentifier(String name) throws DataAccessException {
        for (Iterator<TaskList> it = iterateLists(); it.hasNext(); ) {
            TaskList tl = it.next();
            if (tl.getIdentifier().equals(name)) {
                return Optional.of(tl);
            }
        }

        return Optional.empty();
    }
}
