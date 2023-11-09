package somethingrandom.usecase;

import somethingrandom.entity.CommonItemFactory;
import somethingrandom.entity.ReferenceItem;

public class AddReferenceItemInteracter implements AddReferenceItemInputBoundary {
    final AddItemDataAccessInterface dataAccessInterface;
    final CommonItemFactory factory;
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
