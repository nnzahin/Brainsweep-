package somethingrandom.dataaccess.google.tasks;

import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.usecase.DataAccessException;

public class GoogleTasksDataAccessObject implements AddItemDataAccessInterface {
    @Override
    public void save(Item item) throws DataAccessException {
        throw new RuntimeException("not implemented");
    }
}
