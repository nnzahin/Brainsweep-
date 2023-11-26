package somethingrandom.usecase;

public interface AddItemInputBoundary {


    void execute(AddActionableItemDataInput inputData) throws DataAccessException;
}
