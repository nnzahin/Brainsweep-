package somethingrandom.usecase.delete;

import somethingrandom.usecase.DataAccessException;

import java.util.UUID;

public class DeleteItemInteractor implements DeleteItemInputBoundary{
    private final DeleteItemDataAccessInterface dao;

    public DeleteItemInteractor(DeleteItemDataAccessInterface dao) {
        this.dao = dao;
    }

    public void execute(UUID id) throws DataAccessException {
        dao.delete(id);
    }
}
