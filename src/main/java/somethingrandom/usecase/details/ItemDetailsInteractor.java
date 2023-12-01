package somethingrandom.usecase.details;

import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;
import somethingrandom.usecase.DataAccessException;

import java.util.Optional;
import java.util.UUID;

public class ItemDetailsInteractor implements ItemDetailsInputBoundary {
    private final ItemDetailsDataAccessInterface dataAccess;
    private final ItemDetailsOutputBoundary presenter;

    public ItemDetailsInteractor(ItemDetailsDataAccessInterface dataAccess, ItemDetailsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void showDetailsForId(UUID id) {
        Optional<Item> itemOptional;
        try {
            itemOptional = dataAccess.getItemById(id);
        } catch (DataAccessException e) {
            presenter.presentFailure(e.getMessage());
            return;
        }

        if (itemOptional.isEmpty()) {
            presenter.presentFailure("No such item");
            return;
        }

        Item item = itemOptional.get();
        if (item instanceof ReferenceItem) {
            presenter.presentDetails(new ItemDetailsOutputData(item.getName(), ((ReferenceItem) item).getDescription(), item.getCreationDate()));
        } else {
            presenter.presentDetails(new ItemDetailsOutputData(item.getName(), item.getCreationDate()));
        }
    }
}
