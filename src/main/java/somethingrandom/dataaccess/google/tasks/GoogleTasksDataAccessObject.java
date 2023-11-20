package somethingrandom.dataaccess.google.tasks;

import org.json.JSONObject;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.dataaccess.google.APIRequestBody;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.Item;
import somethingrandom.entity.ItemKind;
import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;

/**
 * Implements an interface to store items in the Google Tasks API.
 * <p>
 * The API is
 * <a href="https://developers.google.com/tasks/reference/rest">published online</a>.
 */
public class GoogleTasksDataAccessObject implements AddItemDataAccessInterface {
    private final TaskList taskList;

    public GoogleTasksDataAccessObject(TaskList list) {
        this.taskList = list;
    }

    public static String getScope() {
        return "https://www.googleapis.com/auth/tasks";
    }

    @Override
    public void save(Item item) throws DataAccessException {
        String id = taskList.getIdentifier();
        try {
            JSONObject request = new JSONObject();
            request.put("kind", "tasks#task");
            request.put("id", item.getID());
            request.put("title" , item.getName());
            request.put("status" , "needsAction");

            //To keep track of creation date because Google tasks only tracks the last updated time
            String notes = "Created on " + item.getCreationDate();
            if (item.getItemKind() == ItemKind.ACTIONABLE){
                if (((ActionableItem) item).getNeededTime() != null) {
                    notes = notes + "\n" + "Needs " + ((ActionableItem) item).getNeededTime().toHours() + " hours to complete";
                } else {
                    notes = notes + "\n" + "Needs to be completed";
                }
            } else if (item.getItemKind() == ItemKind.DELAYED){
                notes = notes + "\n" + "Reminder set on " + ((DelayedItem) item).getReminderDate();
            }
            request.put("notes" , notes);

            APIProvider provider = new APIProvider.Constant("https://tasks.googleapis.com/tasks/v1/lists/" + id);
            provider.request(new APIRequestBody.JSONBody("POST",request), "https://tasks.googleapis.com/tasks/v1/lists/" + id + "/tasks");

        } catch (IOException e) {
            throw new DataAccessException(e);
        }
    }
}
