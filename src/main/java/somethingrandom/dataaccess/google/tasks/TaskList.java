package somethingrandom.dataaccess.google.tasks;

import org.json.JSONObject;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.dataaccess.google.APIRequestBody;
import somethingrandom.entity.Item;
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

    public void add(Item item) throws DataAccessException, IOException {
        JSONObject request = new JSONObject();
        request.put("kind", "tasks#task");
        request.put("id", item.getID());
        request.put("title" , item.getName());
        request.put("status" , "needsAction");
        request.put("notes" , item.toString());
        provider.request(new APIRequestBody.JSONBody("POST", request), "https://tasks.googleapis.com/tasks/v1/lists/" + identifier + "/tasks");
    }
}
