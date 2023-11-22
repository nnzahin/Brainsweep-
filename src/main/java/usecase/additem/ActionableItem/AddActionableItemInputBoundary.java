package usecase.additem.ActionableItem;

import usecase.DataAccessException;

public interface AddActionableItemInputBoundary {
    void execute(AddActionableItemInputData inputData) throws DataAccessException;
}
