package somethingrandom.usecase;

import org.jetbrains.annotations.Nullable;
import somethingrandom.entity.CommonItemFactory;
import somethingrandom.entity.Item;

public class AddItemInteractor implements AddItemInputBoundary{
    final AddItemDataAccessInterface addItemDataAccessObject;

    final AddItemOutputBoundary addItemPresenter;

    private final CommonItemFactory factory;

    public AddItemInteractor(AddItemDataAccessInterface addItemDataAccessObject,
                             AddItemOutputBoundary addItemOutputBoundary,
                             CommonItemFactory factory){
        this.addItemDataAccessObject = addItemDataAccessObject;
        this.addItemPresenter = addItemOutputBoundary;
        this.factory = factory;
    }

    @Override
    public void execute(AddItemInputData addItemInputData) {
        Item item = createItemForInputData(addItemInputData);

        if (item == null) {
            addItemPresenter.prepareFailView();
        }

        try {
            addItemDataAccessObject.save(item);
        } catch (DataAccessException e) {
            addItemPresenter.prepareFailView();
        }
    }

    private @Nullable Item createItemForInputData(AddItemInputData addItemInputData) {
        if (addItemInputData.getNeededTime() != null) {
            return factory.createActionableItem(addItemInputData.getName(), addItemInputData.getId(), addItemInputData.getCreationDate(), addItemInputData.getNeededTime());
        } else if (addItemInputData.getDescription() != null) {
            return factory.createReferenceItem(addItemInputData.getName(), addItemInputData.getId(),addItemInputData.getCreationDate(), addItemInputData.getDescription());
        } else if (addItemInputData.getRemindDate() != null) {
            return factory.createDelayedItem(addItemInputData.getName(), addItemInputData.getId(), addItemInputData.getCreationDate(), addItemInputData.getRemindDate());
        }

        return null;
    }
}
