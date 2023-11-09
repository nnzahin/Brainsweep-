package somethingrandom.usecase;

import somethingrandom.entity.*;

public class AddActionableItemInteracter implements AddActionableItemDataInputBoundary {
    final AddItemDataAccessInterface dataAccessInterface;
    final CommonItemFactory factory;

    public AddActionableItemInteracter(AddItemDataAccessInterface dataAccessInterface, CommonItemFactory factory) {
        this.dataAccessInterface = dataAccessInterface;
        this.factory = factory;
    }

    /**
     * Create a new item using inputData.
     *
     * @param inputData The details of a new item.
     */

    @Override
    public void execute(AddActionableItemDataInput inputData) throws DataAccessException {
        ActionableItem item = factory.createActionableItem(inputData.getName(), inputData.getId(), inputData.getCreationDate(), inputData.getNeededTime());
        dataAccessInterface.save(item);
    }
}
