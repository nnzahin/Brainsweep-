package usecase.additem.ReferenceItem;

import entity.CommonItemFactory;
import entity.ReferenceItem;
import usecase.DataAccessException;
import usecase.additem.AddItemDataAccessInterface;
import usecase.additem.ReferenceItem.AddReferenceItemInputBoundary;
import usecase.additem.ReferenceItem.AddReferenceItemInputData;

public class AddReferenceItemInteracter implements AddReferenceItemInputBoundary {
    private final AddItemDataAccessInterface dataAccessInterface;
    private final CommonItemFactory factory;

    public AddReferenceItemInteracter(AddItemDataAccessInterface dataAccessInterface, CommonItemFactory factory) {
        this.dataAccessInterface = dataAccessInterface;
        this.factory = factory;
    }

    /**
     * Create a new item using inputData.
     *
     * @param inputData The details of a new item.
     */
    @Override
    public void execute(AddReferenceItemInputData inputData) throws DataAccessException {
        ReferenceItem item = factory.createReferenceItem(inputData.getName(), inputData.getId(), inputData.getCreationDate(), inputData.getDescription());
        dataAccessInterface.save(item);
    }
}
