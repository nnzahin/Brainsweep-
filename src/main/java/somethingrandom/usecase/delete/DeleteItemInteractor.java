package somethingrandom.usecase.delete;

import somethingrandom.interfaceadapters.delete.DeleteItemPresenter;
import somethingrandom.usecase.DataAccessException;

import java.util.UUID;

public class DeleteItemInteractor implements DeleteItemInputBoundary{
    private final DeleteItemDataAccessInterface dao;
    private final DeleteItemPresenter presenter;

    public DeleteItemInteractor(DeleteItemDataAccessInterface dao, DeleteItemPresenter presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    public void execute(UUID id) {
        try {
            if(dao.delete(id)) {
                presenter.prepareSuccessView();
            } else {
                presenter.prepareFailView();
            }
        } catch (DataAccessException e) {
            presenter.prepareFailView();
        }
    }
}
