package somethingrandom.usecase;

public interface AddItemInputBoundary {
    void execute(AddItemInputData addItemInputData);

    void execute(AddActionableItemDataInput inputData) throws DataAccessException;
}
