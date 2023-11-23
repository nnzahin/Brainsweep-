package usecase.additem.ActionableItem;

import entity.ActionableItem;
import entity.CommonItemFactory;
import entity.*;
import usecase.DataAccessException;
import usecase.additem.ActionableItem.AddActionableItemInputBoundary;
import usecase.additem.ActionableItem.AddActionableItemInputData;
import usecase.additem.AddItemDataAccessInterface;

public class AddActionableItemInteracter implements AddActionableItemInputBoundary {
    private final AddItemDataAccessInterface dataAccessInterface;
    private final CommonItemFactory factory;

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
    public void execute(AddActionableItemInputData inputData) throws DataAccessException {
        ActionableItem item = factory.createActionableItem(inputData.getName(), inputData.getId(), inputData.getCreationDate(), inputData.getNeededTime());
        dataAccessInterface.save(item);
    }
}
