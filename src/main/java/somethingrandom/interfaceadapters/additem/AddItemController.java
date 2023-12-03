package somethingrandom.interfaceadapters.additem;

import somethingrandom.interfaceadapters.ItemDialogController;
import somethingrandom.usecase.AddItemInputBoundary;
import somethingrandom.usecase.AddItemInputData;
public class AddItemController implements ItemDialogController {
    private final AddItemInputBoundary addItemUseCaseInteractor;

    public AddItemController(AddItemInputBoundary addItemUseCaseInteractor) {
        this.addItemUseCaseInteractor = addItemUseCaseInteractor;
    }

    @Override
    public void finished(String title, String description) {
        if (title.isEmpty()) {
            return;
        }

        AddItemInputData addItemInputData = new AddItemInputData(title, description);
        addItemUseCaseInteractor.execute(addItemInputData);
    }
}
