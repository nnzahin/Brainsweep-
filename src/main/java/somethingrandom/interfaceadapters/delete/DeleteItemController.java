package somethingrandom.interfaceadapters.delete;

import somethingrandom.usecase.DataAccessException;
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
        try {
            deleteItemUseCaseInteractor.execute(id);
            presenter.prepareSuccessView();
        } catch (DataAccessException e) {
            presenter.prepareFailView();
        }
    }
}
