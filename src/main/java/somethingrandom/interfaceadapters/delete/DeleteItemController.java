package somethingrandom.interfaceadapters.delete;

import somethingrandom.usecase.delete.DeleteItemInputBoundary;

import java.util.UUID;

public class DeleteItemController {
    final DeleteItemInputBoundary deleteItemUseCaseInteractor;
    final DeleteItemPresenter presenter;
    public DeleteItemController(DeleteItemInputBoundary deleteItemUseCaseInteractor, DeleteItemPresenter presenter) {
        this.deleteItemUseCaseInteractor = deleteItemUseCaseInteractor;
        this.presenter = presenter;
    }

    public void execute(UUID id) {
        deleteItemUseCaseInteractor.execute(id);
    }
}
