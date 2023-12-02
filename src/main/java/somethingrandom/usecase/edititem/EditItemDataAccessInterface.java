package somethingrandom.usecase.edititem;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

public interface EditItemDataAccessInterface {
    void edit(Item item) throws DataAccessException;
}
