package somethingrandom.usecase;

public interface AddDelayedItemInputBoundary {
    void execute(AddDelayedItemInputData inputData) throws DataAccessException;
}
