package somethingrandom.dataaccess.google.tasks;

import org.json.JSONObject;
import somethingrandom.dataaccess.google.APIRequestBody;
import somethingrandom.entity.*;
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
        JSONObject request = new JSONObject();
        request.put("kind", "tasks#task");
        request.put("id", item.getID());
        request.put("title" , item.getName());
        request.put("status" , "needsAction");
        request.put("notes" , item.toString());
        try {
            this.taskList.add(request);
        } catch (IOException e) {
            throw new DataAccessException(e);
        }
    }
}
