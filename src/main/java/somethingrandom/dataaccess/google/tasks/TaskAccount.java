package somethingrandom.dataaccess.google.tasks;

import org.json.JSONArray;
import org.json.JSONObject;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TaskAccount {
    private final APIProvider provider;

    public TaskAccount(APIProvider provider) {
        this.provider = provider;
    }

    public Iterator<TaskList> iterateLists() throws DataAccessException {
        JSONObject result;
        try {
            result = provider.request("https://tasks.googleapis.com/tasks/v1/users/@me/lists");
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
