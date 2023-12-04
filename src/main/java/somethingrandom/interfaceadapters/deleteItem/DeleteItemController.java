package somethingrandom.interfaceadapters.deleteItem;

import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.delete.DeleteItemInputBoundary;

import java.util.UUID;

public class DeleteItemController {
    final DeleteItemInputBoundary deleteItemUseCaseInteractor;

    public DeleteItemController(DeleteItemInputBoundary deleteItemUseCaseInteractor) {
        this.deleteItemUseCaseInteractor = deleteItemUseCaseInteractor;
    }

    public void execute(UUID id) throws DataAccessException {
        deleteItemUseCaseInteractor.execute(id);
    }
}
