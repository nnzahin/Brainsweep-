package somethingrandom.dataaccess.google.tasks;

import org.json.JSONException;
import org.json.JSONObject;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.dataaccess.google.APIRequestBody;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;
import java.util.*;

public class TaskList {
    private final APIProvider provider;
    private final String identifier;
    private final String title;

    private final Map<UUID, String> uuidsToIds = new HashMap<>();
    private final Map<String, UUID> idsToUUIDs = new HashMap<>();

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

        APIRequestBody body = new APIRequestBody.JSONBody("POST", request);
        JSONObject response = provider.request(body, "https://tasks.googleapis.com/tasks/v1/lists/" + identifier + "/tasks");

        String id;
        try {
            id = response.getString("id");
        } catch (JSONException e) {
            throw new DataAccessException(e);
        }

        uuidsToIds.put(item.getID(), id);
        idsToUUIDs.put(id, item.getID());
    }

    /**
     * Deletes a task from the task list.
     * Return an empty string if the item was not found
     * @param id The UUID of the task to delete.
     * @return The name of the task that was deleted.
     * @throws DataAccessException
     * @throws IOException
     */
    public Boolean delete(UUID id) throws DataAccessException, IOException {
        String taskId = uuidsToIds.get(id);
        if (taskId == null) {
            return false;
        }
        provider.request(new APIRequestBody.JSONBody("DELETE", new JSONObject()), "https://tasks.googleapis.com/tasks/v1/lists/" + identifier + "/tasks/" + taskId);
        uuidsToIds.remove(id);
        idsToUUIDs.remove(taskId);
        return true;
    }

    public Collection<Item> getAll() throws DataAccessException, IOException {
        JSONObject response = provider.request(new APIRequestBody.JSONBody("GET", new JSONObject()), "https://tasks.googleapis.com/tasks/v1/lists/" + identifier + "/tasks");
        Collection<Item> allItems = new ArrayList<>();
        for (Object item: response.getJSONArray("items")) {
            JSONObject jsonItem = (JSONObject) item;
            if (!idsToUUIDs.containsKey(jsonItem.getString("id"))) {
                UUID id = UUID.randomUUID();
                idsToUUIDs.put(jsonItem.getString("id"), id);
                uuidsToIds.put(id, jsonItem.getString("id"));
            }
            allItems.add(JsonItemFactory.createItem(idsToUUIDs.get(jsonItem.getString("id")), jsonItem));
        }
        return allItems;

    }
}
