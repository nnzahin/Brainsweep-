package somethingrandom.interfaceadapters.additem;
import somethingrandom.usecase.add.AddItemInputBoundary;
import somethingrandom.usecase.add.AddItemInputData;

public class AddItemController {

    final AddItemInputBoundary addItemUseCaseInteractor;
    public AddItemController(AddItemInputBoundary addItemUseCaseInteractor) {
        this.addItemUseCaseInteractor = addItemUseCaseInteractor;
    }
    public void execute(String item, String description) {
        AddItemInputData addItemInputData = new AddItemInputData(item, description);
        addItemUseCaseInteractor.execute(addItemInputData);
    }
}
