package somethingrandom.interfaceadapters.delete;

import somethingrandom.usecase.delete.DeleteItemInputBoundary;

import java.util.UUID;

public class DeleteItemController {
    private final DeleteItemInputBoundary deleteItemUseCaseInteractor;

    public DeleteItemController(DeleteItemInputBoundary deleteItemUseCaseInteractor) {
        this.deleteItemUseCaseInteractor = deleteItemUseCaseInteractor;
    }

    public void execute(UUID id) {
        deleteItemUseCaseInteractor.execute(id);
    }
}
