package somethingrandom.dataaccess.google.tasks;

import org.json.JSONObject;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.dataaccess.google.APIRequestBody;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;

public class TaskList {
    private final APIProvider provider;
    private final String identifier;
    private final String title;

    TaskList(APIProvider provider, String title, String identifier) {
        this.provider = provider;
        this.identifier = identifier;
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void add(JSONObject item) throws DataAccessException, IOException {
        provider.request(new APIRequestBody.JSONBody("POST", item), "https://tasks.googleapis.com/tasks/v1/lists/" + identifier + "/tasks");
    }
}
