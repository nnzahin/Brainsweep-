package usecase.additem.DelayedItem;

import entity.CommonItemFactory;
import entity.DelayedItem;
import usecase.DataAccessException;
import usecase.additem.AddItemDataAccessInterface;
import usecase.additem.DelayedItem.AddDelayedItemInputBoundary;
import usecase.additem.DelayedItem.AddDelayedItemInputData;

public class AddDelayedItemInteracter implements AddDelayedItemInputBoundary {
    private final AddItemDataAccessInterface dataAccessInterface;
    private final CommonItemFactory factory;

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
