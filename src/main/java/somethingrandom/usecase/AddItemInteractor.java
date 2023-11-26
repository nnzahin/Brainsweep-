package somethingrandom.usecase;

import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.CommonItemFactory;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.ReferenceItem;

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
    public void execute(AddItemInputData addItemInputData) throws DataAccessException {
        if (addItemInputData.getNeededTime() != null) {
            try {
                ActionableItem item = factory.createActionableItem(addItemInputData.getName(), addItemInputData.getId(), addItemInputData.getCreationDate(), addItemInputData.getNeededTime());
                addItemDataAccessObject.save(item);

            }catch (DataAccessException dataAccessException){
                throw new DataAccessException();
            }

        } else if (addItemInputData.getDescription() != null) {
            try {
                ReferenceItem item = factory.createReferenceItem(addItemInputData.getName(), addItemInputData.getId(),addItemInputData.getCreationDate(), addItemInputData.getDescription());
                addItemDataAccessObject.save(item);
            }catch (DataAccessException dataAccessException){
                throw new DataAccessException();
            }


        } else {
            try {
                DelayedItem item = factory.createDelayedItem(addItemInputData.getName(), addItemInputData.getId(), addItemInputData.getCreationDate(), addItemInputData.getRemindDate());
                addItemDataAccessObject.save(item);
            }catch (DataAccessException dataAccessException){
                throw new DataAccessException();

            }
        }

    }

}
