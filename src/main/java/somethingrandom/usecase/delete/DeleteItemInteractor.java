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
            if(dao.delete(id)) {
                presenter.presentSuccessView();
            } else {
                presenter.presentFailureView();
            }
        } catch (DataAccessException e) {
            presenter.presentFailureView();
        }
    }
}
