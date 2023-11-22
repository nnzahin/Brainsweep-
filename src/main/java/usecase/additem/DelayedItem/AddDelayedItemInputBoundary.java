package usecase.additem.DelayedItem;

import usecase.DataAccessException;

public interface AddDelayedItemInputBoundary {
    void execute(AddDelayedItemInputData inputData) throws DataAccessException;
}
