package somethingrandom.usecase;

import somethingrandom.entity.CommonItemFactory;
import somethingrandom.entity.DelayedItem;

public class AddDelayedItemInteracter implements AddDelayedItemInputBoundary {
    final AddItemDataAccessInterface dataAccessInterface;
    final CommonItemFactory factory;
    public AddDelayedItemInteracter(AddItemDataAccessInterface dataAccessInterface, CommonItemFactory factory) {
        this.dataAccessInterface = dataAccessInterface;
        this.factory = factory;
    }

    /**
     * Create a new item using inputData.
     *
     * @param inputData The details of a new item.
     */

    @Override
    public void execute(AddDelayedItemInputData inputData) throws DataAccessException {
        DelayedItem item = factory.createDelayedItem(inputData.getName(), inputData.getId(), inputData.getCreationDate(), inputData.getRemindDate());
        dataAccessInterface.save(item);
    }
}
