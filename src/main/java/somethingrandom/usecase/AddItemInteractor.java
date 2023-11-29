package somethingrandom.usecase;

import org.jetbrains.annotations.Nullable;
import somethingrandom.entity.CommonItemFactory;
import somethingrandom.entity.Item;

import java.time.Instant;
import java.util.UUID;

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
        UUID uuid = UUID.randomUUID();
        Instant creationDate = Instant.now();

        if (addItemInputData.getNeededTime() != null) {
            return factory.createActionableItem(addItemInputData.getName(), uuid, creationDate, addItemInputData.getNeededTime());
        } else if (addItemInputData.getDescription() != null) {
            return factory.createReferenceItem(addItemInputData.getName(), uuid, creationDate, addItemInputData.getDescription());
        } else if (addItemInputData.getRemindDate() != null) {
            return factory.createDelayedItem(addItemInputData.getName(), uuid, creationDate, addItemInputData.getRemindDate());
        }

        return null;
    }
}
