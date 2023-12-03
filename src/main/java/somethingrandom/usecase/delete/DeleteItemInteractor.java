package somethingrandom.usecase.delete;

import somethingrandom.usecase.DataAccessException;

import java.util.UUID;

public class DeleteItemInteractor implements DeleteItemInputBoundary{
    private final DeleteItemDataAccessInterface dao;
    private final DeleteItemOutputBoundary presenter;

    public DeleteItemInteractor(DeleteItemDataAccessInterface dao, DeleteItemOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    public void execute(UUID id) {
        try {
            String message = dao.delete(id);
            if (message == null){
                presenter.presentFailureView("Item not found");
            } else {
                presenter.presentSuccessView(message);
            }
        } catch (DataAccessException e) {
               presenter.presentFailureView(e.getMessage());
        }
    }
}
