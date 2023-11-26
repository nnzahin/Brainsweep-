package somethingrandom.usecase;

public interface AddItemInputBoundary {


    void execute(AddItemInputData inputData) throws DataAccessException;
}
