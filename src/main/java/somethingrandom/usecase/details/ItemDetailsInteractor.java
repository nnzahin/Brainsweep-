package somethingrandom.usecase.details;

import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.DelayedItem;
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
            e.printStackTrace();
            presenter.presentFailure(e.getMessage());
            return;
        }

        if (itemOptional.isEmpty()) {
            presenter.presentFailure("No such item");
            return;
        }

        Item item = itemOptional.get();
        if (item instanceof ReferenceItem) {
            presenter.presentDetails(new ItemDetailsOutputData(item.getName(), item.getID(), ((ReferenceItem) item).getDescription(), item.getCreationDate()));
        } else if (item instanceof ActionableItem) {
            presenter.presentDetails(new ItemDetailsOutputData(item.getName(), item.getID(), ((ActionableItem) item).getNeededTime(), item.getCreationDate()));
        } else if (item instanceof DelayedItem) {
            presenter.presentDetails(new ItemDetailsOutputData(item.getName(), item.getID(), ((DelayedItem) item).getRemindDate(), item.getCreationDate()));
        } else {
            presenter.presentDetails(new ItemDetailsOutputData(item.getName(), item.getID(), item.getCreationDate()));
        }
    }
}
