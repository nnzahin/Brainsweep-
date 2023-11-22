package usecase.additem.ReferenceItem;

import usecase.DataAccessException;

public interface AddReferenceItemInputBoundary {
    void execute(AddReferenceItemInputData inputData) throws DataAccessException;
}
