package somethingrandom.usecase.edititem;
import org.jetbrains.annotations.Nullable;
import somethingrandom.entity.Item;
import somethingrandom.entity.CommonItemFactory;
import somethingrandom.usecase.additem.AddItemOutputBoundary;

public class EditItemInteractor implements EditItemInputBoundary{
    final EditItemDataAccessInterface editItemDataAccessObject;
    final AddItemOutputBoundary editItemPresenter;
    private final CommonItemFactory factory;

    public EditItemInteractor(EditItemDataAccessInterface editItemDataAccessObject,
                              AddItemOutputBoundary editItemOutputBoundary, CommonItemFactory factory) {
        this.editItemPresenter = editItemOutputBoundary;
        this.editItemDataAccessObject = editItemDataAccessObject;
        this.factory = factory;
    }

    public void execute(EditItemInputData editItemInputData) {
        Item item = createItemForInputData(editItemInputData);
        if (item == null) {
            editItemPresenter.prepareFailView();

        }

    }
    private @Nullable Item createItemForInputData(EditItemInputData editItemInputData) {
        if (editItemInputData.getNeededTime() != null) {
            return factory.createActionableItem(editItemInputData.getName(), editItemInputData.getId(), editItemInputData.getCreationDate(), editItemInputData.getNeededTime());
        } else if (editItemInputData.getDescription() != null) {
            return factory.createReferenceItem(editItemInputData.getName(), editItemInputData.getId(), editItemInputData.getCreationDate(), editItemInputData.getDescription());
        } else if (editItemInputData.getRemindDate() != null) {
            return factory.createDelayedItem(editItemInputData.getName(), editItemInputData.getId(), editItemInputData.getCreationDate(), editItemInputData.getRemindDate());
        }
        return null;
    }
}
