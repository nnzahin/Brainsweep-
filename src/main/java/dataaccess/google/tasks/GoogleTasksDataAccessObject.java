package dataaccess.google.tasks;

import entity.Item;
import usecase.additem.AddItemDataAccessInterface;
import usecase.DataAccessException;

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

    @Override
    public void save(Item item) throws DataAccessException {
        throw new RuntimeException("not implemented");
    }
}
