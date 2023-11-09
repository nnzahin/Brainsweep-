package somethingrandom.dataaccess.google.tasks;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import somethingrandom.dataaccess.google.auth.Token;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TaskList {
    private final Token token;
    private final String identifier;

    private TaskList(Token token, String identifier) {
        this.token = token;
        this.identifier = identifier;
    }

    static Iterator<TaskList> iterateLists(OkHttpClient client, Token token) throws DataAccessException {
        Request request = new Request.Builder()
            .url("https://tasks.googleapis.com/tasks/v1/users/@me/lists")
            .addHeader("Authorization", "Bearer " + token.getToken())
            .get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new DataAccessException("Failed to get task list: " + response.message());
            }

            return iterateLists(token, new JSONObject(response.body().string()));
        } catch (IOException e) {
            throw new DataAccessException(e);
        }
    }

    static Iterator<TaskList> iterateLists(Token token, JSONObject object) throws DataAccessException {
        if (!object.getString("kind").equals("tasks#taskLists")) {
            throw new DataAccessException("Task list has invalid format");
        }

        JSONArray items = object.getJSONArray("items");
        List<TaskList> found = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject taskList = items.getJSONObject(i);
            if (!taskList.optString("kind", "").equals("tasks#taskList")){
                throw new DataAccessException("Task list has invalid type");
            }

            Object id = taskList.opt("id");
            if (!(id instanceof String)) {
                throw new DataAccessException("Task list is missing ID");
            }

            found.add(new TaskList(token, (String)id));
        }

        return found.iterator();
    }

    public static Optional<TaskList> findTaskListByName(OkHttpClient client, Token token, String name) throws DataAccessException {
        for (Iterator<TaskList> it = iterateLists(client, token); it.hasNext(); ) {
            TaskList tl = it.next();
            if (tl.getIdentifier().equals(name)) {
                return Optional.of(tl);
            }
        }

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }
}
