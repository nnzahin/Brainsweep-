package somethingrandom.dataaccess.google.tasks;

import org.json.JSONObject;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.dataaccess.google.APIRequestBody;
import somethingrandom.dataaccess.google.auth.AuthenticationException;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

    public String getTitle() { return title; }

    public void add(Item item) throws DataAccessException, IOException {
        JSONObject request = new JSONObject();
        request.put("kind", "tasks#task");
        request.put("id", item.getID());
        request.put("title" , item.getName());
        request.put("status" , "needsAction");
        request.put("notes" , item.toString());
        provider.request(new APIRequestBody.JSONBody("POST", request), "https://tasks.googleapis.com/tasks/v1/lists/" + identifier + "/tasks");
    }

    public Collection<Item> getAll() throws AuthenticationException, IOException {
        try {
            JSONObject response = provider.request(new APIRequestBody.JSONBody("GET", new JSONObject()), "https://tasks.googleapis.com/tasks/v1/lists/" + identifier + "/tasks");
            JSONObject items = response.getJSONObject("items");
            Collection<Item> allItems = new ArrayList<>();
            JsonItemFactory factory = new JsonItemFactory();
            for (Object item: items.toMap().values()) {
                JSONObject jsonItem = new JSONObject(item);
                allItems.add(factory.createItem(idsToUUIDs.get(jsonItem.get("id")), jsonItem));
            }
            return allItems;
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
